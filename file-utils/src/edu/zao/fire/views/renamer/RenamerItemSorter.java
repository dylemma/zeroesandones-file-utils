package edu.zao.fire.views.renamer;

import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerSorter;

public class RenamerItemSorter extends ViewerSorter {
	private int columnIndex = 0;
	private boolean descending;

	public void setColumn(int column) {
		if (columnIndex != column) {
			descending = false;
		} else {
			descending = !descending;
		}
		columnIndex = column;
	}

	private int baseCompare(Viewer viewer, Object e1, Object e2) {
		RenamerItem item1 = (RenamerItem) e1;
		RenamerItem item2 = (RenamerItem) e2;

		int category1 = item1.getFile().isDirectory() ? 0 : 1;
		int category2 = item2.getFile().isDirectory() ? 0 : 1;

		if (category1 != category2) {
			return category1 - category2;
		}

		return item1.getFile().getName().compareToIgnoreCase(item2.getFile().getName());
	}

	@Override
	public int compare(Viewer viewer, Object e1, Object e2) {
		int baseCompare = baseCompare(viewer, e1, e2);
		if (descending) {
			baseCompare = -baseCompare;
		}
		return baseCompare;
	}

}
