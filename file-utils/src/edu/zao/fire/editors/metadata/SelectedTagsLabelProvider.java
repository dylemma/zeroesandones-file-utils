package edu.zao.fire.editors.metadata;

import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.ViewerCell;

public class SelectedTagsLabelProvider extends StyledCellLabelProvider {
	@Override
	public void update(ViewerCell cell) {
		cell.setText(cell.getElement().toString());
	}
}