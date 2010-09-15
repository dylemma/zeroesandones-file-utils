package edu.zao.futils.views;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.ITableLabelProvider;
import org.eclipse.jface.viewers.StyledCellLabelProvider;
import org.eclipse.jface.viewers.StyledString;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.Viewer;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.KeyEvent;
import org.eclipse.swt.events.KeyListener;
import org.eclipse.swt.graphics.Image;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

public class DemoView extends ViewPart {
	public static final String ID = "file-utils.view";

	private TableViewer viewer;

	/**
	 * The content provider class is responsible for providing objects to the
	 * view. It can wrap existing objects in adapters or simply return objects
	 * as-is. These objects may be sensitive to the current input of the view,
	 * or ignore it and always show the same content (like Task List, for
	 * example).
	 */
	class ViewContentProvider implements IStructuredContentProvider {
		@Override
		public void inputChanged(Viewer v, Object oldInput, Object newInput) {
		}

		@Override
		public void dispose() {
		}

		@Override
		public Object[] getElements(Object parent) {
			if (parent instanceof Object[]) {
				return (Object[]) parent;
			}
			return new Object[0];
		}
	}

	class ViewLabelProvider extends StyledCellLabelProvider implements ITableLabelProvider {
		@Override
		public void update(ViewerCell cell) {
			System.out.println("update...");
			String dullString = getColumnText(cell.getElement(), cell.getColumnIndex());
			StyledString styledString = new StyledString(dullString);
			styledString.append("(woot)", StyledString.COUNTER_STYLER);
			cell.setText(styledString.toString());
			cell.setStyleRanges(styledString.getStyleRanges());
			super.update(cell);
		}

		@Override
		public String getColumnText(Object obj, int index) {
			return obj.toString();
		}

		@Override
		public Image getColumnImage(Object obj, int index) {
			return getImage(obj);
		}

		public Image getImage(Object obj) {
			return PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_OBJ_ELEMENT);
		}
	}

	/**
	 * This is a callback that will allow us to create the viewer and initialize
	 * it.
	 */
	@Override
	public void createPartControl(Composite parent) {
		viewer = new TableViewer(parent, SWT.FULL_SELECTION | SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL);
		TableColumn c1 = new TableColumn(viewer.getTable(), SWT.RIGHT);// setWidth(100);
		c1.setText("hello");
		c1.setWidth(100);
		c1.setResizable(true);
		c1.setMoveable(true);

		TableColumn c2 = new TableColumn(viewer.getTable(), SWT.RIGHT);
		c2.setWidth(100);
		c2.setText("meh");
		c2.setResizable(true);
		c2.setMoveable(true);

		viewer.setContentProvider(new ViewContentProvider());
		viewer.setLabelProvider(new ViewLabelProvider());
		// Provide the input to the ContentProvider
		viewer.setInput(new String[] {
				"One", "Two", "Three"
		});
		viewer.setColumnProperties(new String[] {
				"Hello", "Goodbye"
		});

		viewer.getTable().setHeaderVisible(true);

		viewer.getTable().addKeyListener(new KeyListener() {

			@Override
			public void keyPressed(KeyEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void keyReleased(KeyEvent e) {
				System.out.println("key");
				viewer.refresh(true);
			}
		});
	}

	/**
	 * Passing the focus request to the viewer's control.
	 */
	@Override
	public void setFocus() {
		viewer.getControl().setFocus();
	}
}