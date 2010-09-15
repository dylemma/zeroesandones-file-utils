package edu.zao.futils.views.renamer;

import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.jface.viewers.StyledString.Styler;
import org.eclipse.swt.SWT;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.graphics.TextStyle;
import org.eclipse.swt.widgets.Display;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;

import edu.zao.futils.views.renamer.filter.AffectedRenameItemFilter;
import edu.zao.futils.views.renamer.filter.RenamerItemFilter;

public class RenamerItemLabelProvider extends StyledCellLabelProvider {
	private final RenamerItemFilter renamedFilter = new AffectedRenameItemFilter();

	public final Color untouchedBackgroundColor = Display.getCurrent().getSystemColor(SWT.COLOR_LIST_BACKGROUND);
	public final Color renamedBackgroundColor = new Color(Display.getCurrent(), 221, 221, 236);
	public final Color renamedItemColor = Display.getCurrent().getSystemColor(SWT.COLOR_DARK_BLUE);
	private final Styler renamedStyler = new Styler() {
		@Override
		public void applyStyles(TextStyle textStyle) {
			textStyle.foreground = renamedItemColor;
		}
	};

	@Override
	public void update(ViewerCell cell) {
		Styler stylerToUse = null;
		if (cell.getElement() instanceof RenamerItem) {
			if (renamedFilter.accept((RenamerItem) cell.getElement())) {
				stylerToUse = renamedStyler;
			}
		}

		if (stylerToUse != null) {
			cell.setBackground(renamedBackgroundColor);
		} else {
			cell.setBackground(untouchedBackgroundColor);
		}

		String dullString = getColumnText(cell.getElement(), cell.getColumnIndex());
		StyledString styledString = new StyledString(dullString, stylerToUse);
		cell.setText(styledString.toString());
		cell.setImage(getColumnImage(cell.getElement(), cell.getColumnIndex()));
		cell.setStyleRanges(styledString.getStyleRanges());
		super.update(cell);
	}

	private Image getColumnImage(Object element, int columnIndex) {
		if (columnIndex != 0) {
			return null;
		}

		if (!(element instanceof RenamerItem)) {
			throw new IllegalArgumentException("Expected element to be a FileRenamerItem");
		}
		RenamerItem item = (RenamerItem) element;
		if (item.getFile().isDirectory()) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FOLDER);
		} else if (item.getFile().isFile()) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_FILE);
		} else {
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
	}

	private String getColumnText(Object element, int columnIndex) {
		if (!(element instanceof RenamerItem)) {
			throw new IllegalArgumentException("all elements for a FileRenamerItemLabelProvider must be FileRenamerItems");
		}
		RenamerItem item = (RenamerItem) element;
		switch (columnIndex) {
		case 0:
			return item.getFile().getName();
		case 1:
			return item.getTarget();
		default:
			return "?";
		}
	}
}
