package edu.zao.futils.views.renamer;

import java.util.LinkedList;
import java.util.List;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

import edu.zao.futils.views.renamer.filter.RenamerItemFilter;

public class RenamerItemContentProvider implements IStructuredContentProvider {
	private RenamerItemFilter itemFilter;

	public void setItemFilter(RenamerItemFilter filter) {
		itemFilter = filter;
	}

	@Override
	public void dispose() {
	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
	}

	@Override
	public Object[] getElements(Object inputElement) {
		if (!(inputElement instanceof List<?>)) {
			throw new IllegalArgumentException("inputElement must be a list");
		}

		if (itemFilter == null) {
			return ((List<?>) inputElement).toArray();
		}

		List<Object> filteredObjects = new LinkedList<Object>();

		for (Object element : (List<?>) inputElement) {
			if (element instanceof RenamerItem) {
				if (itemFilter.accept((RenamerItem) element)) {
					filteredObjects.add(element);
				}
			} else {
				throw new IllegalArgumentException("each element in the inputElement list must be a FileRenamerItem");
			}
		}
		return filteredObjects.toArray();
	}

}
