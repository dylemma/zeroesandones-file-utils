package edu.zao.fire.views.browser;

import java.util.ArrayList;
import java.util.Set;
import java.util.TreeSet;

public class BrowserURLHistory {
	private final ArrayList<String> currentHistorySequence = new ArrayList<String>();
	private final Set<String> visitedLocations = new TreeSet<String>();

	/**
	 * Add the given <code>url</code> to the url history. If the current history
	 * position has been regressed to any point before the <code>head</code>,
	 * the new <code>url</code> becomes the head, and any existing history
	 * beyond the new <code>url</code> is erased.
	 * 
	 * @param url
	 *            The location to be added to the history
	 */
	public void visitLocation(String url) {
		visitedLocations.add(url);

		// TODO: implement this for real
		currentHistorySequence.add(url);
	}

	/**
	 * Causes the history to "Go Back." The current history position goes back
	 * by one location. If the history has gone back as far as it can, this
	 * method should do nothing.
	 */
	public void regressHistory() {
		// TODO: implement this
	}

	/**
	 * Determines whether or not the history may be regressed.
	 * 
	 * @return <code>true</code> if the history may "Go Back,"
	 *         <code>false</code> otherwise.
	 */
	public boolean canRegressHistory() {
		// TODO: implement this
		return false;
	}

	/**
	 * Causes the history to "Go Forward." The current history position goes
	 * forward by one location. If the history has gone as far forward as it can
	 * (i.e. the current position is the head position), then this method should
	 * do nothing.
	 */
	public void progressHistory() {
		// TODO: implement this
	}

	/**
	 * Determines whether or not the history may be progressed.
	 * 
	 * @return <code>true</code> if the history may "Go Forward,"
	 *         <code>false</code> otherwise.
	 */
	public boolean canProgressHistory() {
		// TODO: implement this
		return false;
	}

	/**
	 * @return An object that can iterate over all of the locations that this
	 *         history has ever visited (regardless of whether they have been
	 *         cleared by a backwards navigational path.
	 */
	public Iterable<String> getVisitedLocations() {
		return visitedLocations;
	}
}