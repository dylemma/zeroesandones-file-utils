package edu.zao.fire;

import java.io.File;
import java.io.IOException;

public class MatchReplaceRule implements RenamerRule {

	/**
	 * A generated serial ID
	 */
	private static final long serialVersionUID = 6189927401626899561L;

	private String matchString = " ";
	private String replaceString = " ";

	/**
	 * Finds each instance of the <code>matchString</code> within the
	 * <code>file</code>'s name, and replaces it with <code>replaceString</code>
	 */
	@Override
	public String getNewName(File file) throws IOException {
		String fileName = file.getName();
		String newName = fileName.replace(matchString, replaceString);
		String fullName = file.getCanonicalPath();
		int fileNameIndex = fullName.lastIndexOf(fileName);
		return fullName.substring(0, fileNameIndex) + newName;
	}

	/**
	 * Set the matchString to a desired value.
	 * 
	 * @param matchString
	 *            The new value of matchString
	 */
	public void setMatchString(String matchString) {
		this.matchString = matchString;
	}

	/**
	 * @return The current value of matchString
	 */
	public String getMatchString() {
		return matchString;
	}

	/**
	 * Set the replaceString to a desired value.
	 * 
	 * @param replaceString
	 */
	public void setReplaceString(String replaceString) {
		this.replaceString = replaceString;
	}

	/**
	 * @return The current value of replaceString
	 */
	public String getReplaceString() {
		return replaceString;
	}
}
