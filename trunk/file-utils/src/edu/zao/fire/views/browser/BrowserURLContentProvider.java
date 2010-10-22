package edu.zao.fire.views.browser;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class BrowserURLContentProvider implements IStructuredContentProvider {

	private BrowserURLHistory input;

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (newInput instanceof BrowserURLHistory) {
			input = (BrowserURLHistory) newInput;
		} else {
			throw new IllegalArgumentException("The new input must be a BrowserURLHistory");
		}
	}

	@Override
	public Object[] getElements(Object inputElement) {
		List<String> elements = new LinkedList<String>();
		for (String url : input.getVisitedLocations()) {
			elements.add(url);
		}
		return elements.toArray();
	}

}
