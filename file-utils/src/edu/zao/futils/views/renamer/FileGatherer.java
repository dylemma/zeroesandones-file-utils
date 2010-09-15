package edu.zao.futils.views.renamer;

import java.io.File;
import java.io.FileFilter;
import java.util.Iterator;
import java.util.LinkedList;
import java.util.List;

public class FileGatherer implements Iterable<File> {
	private final File m_root;
	private final FileFilter m_filter;
	private final List<File> m_files = new LinkedList<File>();

	public FileGatherer(String rootPath) {
		this(new File(rootPath), null);
	}

	public FileGatherer(String rootPath, FileFilter filter) {
		this(new File(rootPath), filter);
	}

	public FileGatherer(File root) {
		this(root, null);
	}

	public FileGatherer(File root, FileFilter filter) {
		m_root = root;
		m_filter = filter;
		initializeFileList(root);
	}

	private void initializeFileList(File root) {
		File[] files = root.listFiles();
		if (files == null) {
			return;
		}
		for (File file : files) {
			if (m_filter != null) {
				if (m_filter.accept(file)) {
					m_files.add(file);
				}
			} else {
				m_files.add(file);
			}
		}
	}

	@Override
	public Iterator<File> iterator() {
		return m_files.iterator();
	}

}
