package edu.zao.fire.views.metarenamer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.custom.CTabFolder;
import org.eclipse.swt.custom.CTabItem;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.part.ViewPart;

public class MetaRenamerView extends ViewPart {

	public MetaRenamerView() {
		// TODO Auto-generated constructor stub
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		CTabFolder folder = new CTabFolder(parent, SWT.TOP | SWT.MULTI);
		for (int i = 0; i < 10; i++) {
			CTabItem item = new CTabItem(folder, SWT.CLOSE);
			item.setText("Item " + i);
			Text text = new Text(folder, SWT.MULTI);
			text.setText("Content for Item " + i);
			item.setControl(text);
		}

	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
