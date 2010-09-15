package edu.zao.futils.views.renamer;

import java.io.File;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.regex.PatternSyntaxException;

public class RenamerBackend {
	private String rootPath;
	private boolean useRegularExpressions;
	private String sourceReplacementString = "";
	private String targetReplacementString = "";
	private final List<RenamerItem> items = new ArrayList<RenamerItem>();

	public static interface StatusListener {
		void status(String statusType);
	}

	public final static String TARGET_NAMES_CONFLICT = "t_name_conflict";
	public final static String TARGET_NAMES_OK = "t_name_ok";

	public final static String SOURCE_REGEX_ERROR = "s_regex_error";
	public final static String TARGET_REGEX_ERROR = "t_regex_error";
	public final static String NORMAL_STATUS = "norm_status";

	private final List<StatusListener> statusListeners = new LinkedList<StatusListener>();

	public RenamerBackend() {

	}

	public void useRegex(boolean useRegex) {
		useRegularExpressions = useRegex;
		update();
	}

	public void setRootPath(String path) {
		rootPath = path;
		gatherFiles(rootPath);
		update();
	}

	public String getRootPath() {
		return rootPath;
	}

	private void gatherFiles(String path) {
		items.clear();
		for (File file : new FileGatherer(path)) {
			items.add(new RenamerItem(file));
		}
	}

	public void setSourceReplacementString(String sourceString) {
		sourceReplacementString = sourceString;
		update();
	}

	public void setTargetReplacementString(String targetString) {
		targetReplacementString = targetString;
		update();
	}

	public List<RenamerItem> getItems() {
		return items;
	}

	private void update() {
		boolean gotSourceError = false;

		for (RenamerItem item : items) {
			String oldName = item.getFile().getName();

			if (sourceReplacementString.isEmpty()) {
				item.setTarget(oldName);
				continue;
			}

			if (!useRegularExpressions) {
				String newName = oldName.replace(sourceReplacementString, targetReplacementString);
				item.setTarget(newName);
			} else {
				try {
					Pattern sourceRegex = Pattern.compile(sourceReplacementString);
					Matcher matcher = sourceRegex.matcher(oldName);
					String newName = matcher.replaceAll(targetReplacementString);
					item.setTarget(newName);
				} catch (PatternSyntaxException pse) {
					gotSourceError = true;
				} catch (Exception e) {
				}
			}
		}

		if (gotSourceError) {
			fireStatus(SOURCE_REGEX_ERROR);
		} else {
			fireStatus(NORMAL_STATUS);
		}
	}

	public void applyChanges() {
		if (!verifyNoNameConflicts()) {
			fireStatus(TARGET_NAMES_CONFLICT);
			return;
		} else {
			fireStatus(TARGET_NAMES_OK);
		}

		// actually apply the changes...
		for (RenamerItem item : items) {
			File newNamedFile = new File(item.getFile().getParentFile(), item.getTarget());
			item.getFile().renameTo(newNamedFile);
			item.setFile(newNamedFile);
		}
	}

	private boolean verifyNoNameConflicts() {
		Set<String> targetNames = new TreeSet<String>();
		for (RenamerItem item : items) {
			String targetName = item.getTarget();
			if (targetNames.contains(targetName)) {
				return false;
			} else {
				targetNames.add(targetName);
			}
		}
		return true;
	}

	public void addStatusListener(StatusListener listener) {
		statusListeners.add(listener);
	}

	public void removeStatusListener(StatusListener listener) {
		statusListeners.remove(listener);
	}

	private void fireStatus(String statusType) {
		for (StatusListener listener : statusListeners) {
			listener.status(statusType);
		}
	}
}
