package edu.zao.fire;

import java.util.ArrayList;
import java.util.Iterator;

public class FileHistory {
	private final ArrayList<String> visitedPaths = new ArrayList<String>();
	private int currentIndex = 0;

	public void visit(String location) {
		if (currentIndex >= visitedPaths.size()) {
			visitedPaths.add(location);
		} else {
			// TODO: figure out the logic here!
			// remove all history beyond the current index
			visitedPaths.set(currentIndex, location);
			Iterator<String> removeLoc = visitedPaths.listIterator(currentIndex + 1);
		}
		currentIndex++;
	}
}
