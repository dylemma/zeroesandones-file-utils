package edu.zao.fire.views.browser;

import java.io.File;

import org.eclipse.jface.viewers.ComboViewer;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import edu.zao.fire.Renamer;

public class BrowserView extends ViewPart {

	final Renamer renamer = new Renamer();

	public BrowserView() {
		String userHomePath = System.getProperty("user.home");
		File userHome = new File(userHomePath);
		renamer.setCurrentDirectory(userHome);
	}

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(1, false));

		// create the top toolbar area
		Composite toolbarTopArea = new Composite(parent, SWT.NONE);
		toolbarTopArea.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		GridLayout toolbarTopAreaLayout = new GridLayout(5, false);
		toolbarTopAreaLayout.marginHeight = 0;
		toolbarTopAreaLayout.marginWidth = 0;
		toolbarTopArea.setLayout(toolbarTopAreaLayout);

		// create the "Go Back" button
		Button historyBackButton = new Button(toolbarTopArea, SWT.PUSH);
		historyBackButton.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_BACK));

		// create the "Go Forward" button
		Button historyForwardButton = new Button(toolbarTopArea, SWT.PUSH);
		historyForwardButton.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_FORWARD));

		// create the "Up One Level" button
		Button browserUpLevelButton = new Button(toolbarTopArea, SWT.PUSH);
		browserUpLevelButton.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_UP));

		// create the url bar
		ComboViewer urlComboViewer = new ComboViewer(toolbarTopArea, SWT.DROP_DOWN | SWT.SINGLE);
		urlComboViewer.getCombo().setLayoutData(new GridData(SWT.FILL, SWT.CENTER, true, false));

		// eventually, the history will be fueled by the browse and up-one-level
		// buttons
		BrowserURLHistory sampleHistory = new BrowserURLHistory();
		sampleHistory.visitLocation("Nowhere in particular");
		sampleHistory.visitLocation("Over here!");
		sampleHistory.visitLocation("Over there!");

		urlComboViewer.setContentProvider(new BrowserURLContentProvider());
		urlComboViewer.setInput(sampleHistory);
		urlComboViewer.setLabelProvider(new BrowserURLLabelProvider());

		// create the Browse button
		Button browseButton = new Button(toolbarTopArea, SWT.PUSH);
		browseButton.setText("Browse...");
		browseButton.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false));

		// create the table viewer for the browser
		TableViewer browserTableViewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		browserTableViewer.getTable().setLayoutData(new GridData(SWT.FILL, SWT.FILL, true, true));
		browserTableViewer.setContentProvider(new BrowserTableContentProvider());
		browserTableViewer.setLabelProvider(new BrowserTableLabelProvider());
		browserTableViewer.setInput(renamer);

		// create the area for the bottom bar
		Composite bottomBarArea = new Composite(parent, SWT.NONE);
		GridLayout bottomBarLayout = new GridLayout(4, false);
		bottomBarLayout.marginHeight = 0;
		bottomBarLayout.marginWidth = 0;
		bottomBarArea.setLayout(bottomBarLayout);

		bottomBarArea.setLayoutData(new GridData(SWT.FILL, SWT.BOTTOM, true, false));

		// add undo/redo buttons
		Button undoButton = new Button(bottomBarArea, SWT.PUSH);
		undoButton.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_UNDO));

		Button redoButton = new Button(bottomBarArea, SWT.PUSH);
		redoButton.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_REDO));

		Button applyButton = new Button(bottomBarArea, SWT.PUSH);
		applyButton.setText("Apply Changes");
		applyButton.setLayoutData(new GridData(SWT.LEFT, SWT.BOTTOM, true, false));

		Button limitDisplayCheck = new Button(bottomBarArea, SWT.CHECK);
		limitDisplayCheck.setText("Only display changing items");
		limitDisplayCheck
				.setToolTipText("If checked, the browser will only show items whose names\n would be changed by applying the current renaming rule.");
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
