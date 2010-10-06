package edu.zao.fire.views.renamer;

import java.io.File;

import org.eclipse.jface.fieldassist.ControlDecoration;
import org.eclipse.jface.viewers.TableViewer;
import org.eclipse.jface.viewers.TableViewerColumn;
import org.eclipse.jface.viewers.ViewerCell;
import org.eclipse.swt.SWT;
import org.eclipse.swt.events.ModifyEvent;
import org.eclipse.swt.events.ModifyListener;
import org.eclipse.swt.events.MouseEvent;
import org.eclipse.swt.events.MouseListener;
import org.eclipse.swt.events.SelectionAdapter;
import org.eclipse.swt.events.SelectionEvent;
import org.eclipse.swt.graphics.Color;
import org.eclipse.swt.graphics.Point;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.CoolBar;
import org.eclipse.swt.widgets.CoolItem;
import org.eclipse.swt.widgets.DirectoryDialog;
import org.eclipse.swt.widgets.Display;
import org.eclipse.swt.widgets.Table;
import org.eclipse.swt.widgets.TableColumn;
import org.eclipse.swt.widgets.Text;
import org.eclipse.swt.widgets.ToolBar;
import org.eclipse.swt.widgets.ToolItem;
import org.eclipse.ui.ISharedImages;
import org.eclipse.ui.PlatformUI;
import org.eclipse.ui.part.ViewPart;

import edu.zao.fire.Renamer;
import edu.zao.fire.Renamer.StatusListener;
import edu.zao.fire.views.renamer.filter.AffectedRenameItemFilter;
import edu.zao.fire.views.renamer.filter.CompoundRenamerItemFilter;
import edu.zao.fire.views.renamer.filter.DeactivatableRenamerItemFilter;

public class RenamerView extends ViewPart {
	private Text renameSourceText;
	private Text renameTargetText;
	private Button regexCheck;
	private Button browseButton;
	private Button applyButton;
	private Button displayAffectedCheck;
	private TableViewer tableViewer;

	private final Color fieldErrorColor = new Color(Display.getCurrent(), 255, 174, 174);

	// item filter whose activation state is controlled by the
	// displayAffectedCheck button
	private final DeactivatableRenamerItemFilter affectedItemFilter = new DeactivatableRenamerItemFilter(new AffectedRenameItemFilter());

	// the item display filter can have filters added or removed from it later
	private final CompoundRenamerItemFilter itemDisplayFilter = new CompoundRenamerItemFilter(affectedItemFilter);

	private final RenamerItemContentProvider tableContentProvider = new RenamerItemContentProvider();

	private final Renamer renamer = new Renamer();

	@Override
	public void createPartControl(Composite parent) {
		parent.setLayout(new GridLayout(2, false));

		// create a composite where the actual controls will go into
		Composite controlArea = new Composite(parent, SWT.NONE);
		GridLayout controlLayout = new GridLayout(2, false);
		controlLayout.marginHeight = 0;
		controlLayout.marginWidth = 0;
		controlLayout.horizontalSpacing = 7;
		controlArea.setLayout(controlLayout);
		GridData controlGridData = new GridData(SWT.RIGHT, SWT.TOP, false, true);
		controlGridData.widthHint = 250;
		controlArea.setLayoutData(controlGridData);

		// two text fields, one for the rename source, the other for the rename
		// target
		renameSourceText = new LabelledTextControl(controlArea, "Rename").getText();
		renameTargetText = new LabelledTextControl(controlArea, "To").getText();

		// check box for whether or not to use regular expressions
		regexCheck = new Button(controlArea, SWT.CHECK);
		regexCheck.setText("Use Regular Expressions");

		// have the check box take up the row, and be aligned right
		GridData regexCheckGridData = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
		regexCheckGridData.horizontalSpan = 2;
		regexCheck.setLayoutData(regexCheckGridData);

		// create a composite for the buttons, so they don't have to conform to
		// the column rules
		Composite buttonArea = new Composite(controlArea, SWT.NONE);
		GridLayout buttonAreaLayout = new GridLayout(2, false);
		buttonAreaLayout.marginWidth = 0;
		buttonAreaLayout.marginHeight = 0;
		buttonAreaLayout.horizontalSpacing = 7;
		buttonArea.setLayout(buttonAreaLayout);

		// make the button area encompass both columns, and take up the whole
		// row
		GridData buttonAreaData = new GridData(SWT.FILL, SWT.FILL, true, true);
		buttonAreaData.horizontalSpan = 2;
		buttonArea.setLayoutData(buttonAreaData);

		// make a browse button
		browseButton = new Button(buttonArea, SWT.PUSH);
		browseButton.setText("Browse...");

		GridData browseGridData = new GridData(SWT.RIGHT, SWT.CENTER, true, false);
		browseButton.setLayoutData(browseGridData);

		applyButton = new Button(buttonArea, SWT.PUSH);
		applyButton.setText("Apply Changes");

		Composite tableArea = new Composite(parent, SWT.NONE);
		GridData tableAreaData = new GridData(SWT.FILL, SWT.FILL, true, true);
		tableAreaData.verticalSpan = 2;
		tableArea.setLayoutData(tableAreaData);

		GridLayout tableAreaLayout = new GridLayout(1, false);
		tableAreaLayout.marginHeight = 0;
		tableAreaLayout.marginWidth = 0;
		tableArea.setLayout(tableAreaLayout);

		Composite navigationArea = new Composite(tableArea, SWT.NONE);
		GridLayout navigationLayout = new GridLayout(1, false);
		navigationLayout.marginHeight = 0;
		navigationLayout.marginWidth = 0;
		navigationArea.setLayout(navigationLayout);
		navigationArea.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		createNavigation(navigationArea);

		Composite fillerComposite = new Composite(parent, SWT.NONE);
		fillerComposite.setLayout(new GridLayout(1, false));
		fillerComposite.setLayoutData(new GridData(SWT.FILL, SWT.FILL, false, true));

		displayAffectedCheck = new Button(fillerComposite, SWT.CHECK);
		displayAffectedCheck.setText("Only display items affected by the renaming");
		GridData dacData = new GridData(SWT.RIGHT, SWT.BOTTOM, false, true);
		displayAffectedCheck.setLayoutData(dacData);

		createTableViewer(tableArea);

		addListeners();

	}

	private void createNavigation(Composite navigationArea) {
		CoolBar coolBar = new CoolBar(navigationArea, SWT.FLAT);
		coolBar.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		ToolBar bfBar = new ToolBar(coolBar, SWT.FLAT);

		ToolItem upButton = new ToolItem(bfBar, SWT.PUSH);
		upButton.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_UP));
		upButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				File currentRoot = new File(renamer.getRootPath());
				File parent = currentRoot.getParentFile();
				if (parent != null) {
					renamer.setRootPath(parent.getAbsolutePath());
					tableViewer.refresh();
				}
			}
		});

		ToolItem backButton = new ToolItem(bfBar, SWT.PUSH);
		backButton.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_BACK));
		ToolItem forwardButton = new ToolItem(bfBar, SWT.PUSH);
		forwardButton.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_TOOL_FORWARD));

		bfBar.pack();
		Point bfSize = bfBar.getSize();
		CoolItem item = new CoolItem(coolBar, SWT.NONE);
		item.setControl(bfBar);
		item.setMinimumSize(item.computeSize(bfSize.x, bfSize.y));
	}

	private void createTableViewer(Composite parent) {
		tableViewer = new TableViewer(parent, SWT.MULTI | SWT.H_SCROLL | SWT.V_SCROLL | SWT.FULL_SELECTION | SWT.BORDER);
		GridData tableLayoutData = new GridData(SWT.FILL, SWT.FILL, true, true);
		// tableLayoutData.verticalSpan = 2;
		tableViewer.getTable().setLayoutData(tableLayoutData);
		String[] titles = { "Original Name", "New Name" };
		int[] bounds = { 180, 180 };

		for (int index = 0; index < titles.length; index++) {
			final TableViewerColumn column = new TableViewerColumn(tableViewer, SWT.LEFT);
			column.getColumn().setText(titles[index]);
			column.getColumn().setWidth(bounds[index]);
			column.getColumn().setResizable(true);
		}

		final Table table = tableViewer.getTable();
		table.setHeaderVisible(true);
		table.setLinesVisible(true);

		tableContentProvider.setItemFilter(itemDisplayFilter);
		tableViewer.setContentProvider(tableContentProvider);
		tableViewer.setLabelProvider(new RenamerItemLabelProvider());

		final RenamerItemSorter tableSorter = new RenamerItemSorter();
		tableViewer.setSorter(tableSorter);

		final TableColumn sortColumn = table.getColumn(0);
		sortColumn.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				tableSorter.setColumn(0);
				int dir = table.getSortDirection();
				dir = (dir == SWT.UP) ? SWT.DOWN : SWT.UP;
				table.setSortDirection(dir);
				table.setSortColumn(sortColumn);
				tableViewer.refresh();
			}
		});

		renamer.setRootPath("C:/code/eclipse/workspace/file-utils");

		tableViewer.setInput(renamer.getItems());

	}

	private void addListeners() {
		// when the user types in the "Rename" box, tell the backend
		renameSourceText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				renamer.setSourceReplacementString(renameSourceText.getText());
				tableViewer.refresh();
			}
		});

		// when the user types in the "To" box, tell the backend
		renameTargetText.addModifyListener(new ModifyListener() {
			@Override
			public void modifyText(ModifyEvent e) {
				renamer.setTargetReplacementString(renameTargetText.getText());
				tableViewer.refresh();
			}
		});

		// when the user clicks the check box for regex, tell the backend
		regexCheck.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				boolean useRegex = regexCheck.getSelection();
				renamer.useRegex(useRegex);
				tableViewer.refresh();
			}
		});

		// when the user clicks the browse button, open a file dialog
		browseButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				DirectoryDialog dialog = new DirectoryDialog(browseButton.getShell());
				dialog.setText("Selected a Directory");
				dialog.setMessage("Choose a Directory");
				String newRoot = dialog.open();
				if (newRoot != null) {
					renamer.setRootPath(newRoot);
					tableViewer.refresh();
				}
			}
		});

		displayAffectedCheck.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				affectedItemFilter.setActive(displayAffectedCheck.getSelection());
				tableViewer.refresh();
			}
		});

		final ControlDecoration regexErrorDecoration = new ControlDecoration(renameSourceText, SWT.LEFT | SWT.TOP);
		regexErrorDecoration.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_DEC_FIELD_ERROR));
		regexErrorDecoration.setDescriptionText("Malformed Regular Expression");
		regexErrorDecoration.hide();

		renamer.addStatusListener(new StatusListener() {

			@Override
			public void status(String statusType) {
				if (statusType == Renamer.NORMAL_STATUS) {
					regexErrorDecoration.hide();
					renameSourceText.setBackground(Display.getCurrent().getSystemColor(SWT.COLOR_LIST_BACKGROUND));
				} else if (statusType == Renamer.SOURCE_REGEX_ERROR) {
					regexErrorDecoration.show();
					renameSourceText.setBackground(fieldErrorColor);
				}
			}
		});

		final ControlDecoration nameConflictDecoration = new ControlDecoration(applyButton, SWT.LEFT | SWT.TOP);
		nameConflictDecoration.setImage(PlatformUI.getWorkbench().getSharedImages().getImage(ISharedImages.IMG_DEC_FIELD_ERROR));
		nameConflictDecoration.setDescriptionText("There are name conflicts with the current replacement. No changes will be applied");
		nameConflictDecoration.hide();

		renamer.addStatusListener(new StatusListener() {
			@Override
			public void status(String statusType) {
				if (statusType == Renamer.TARGET_NAMES_CONFLICT) {
					nameConflictDecoration.show();
				} else {
					nameConflictDecoration.hide();
				}
			}
		});

		applyButton.addSelectionListener(new SelectionAdapter() {
			@Override
			public void widgetSelected(SelectionEvent e) {
				renamer.applyChanges();
				tableViewer.refresh();
			}
		});

		tableViewer.getTable().addMouseListener(new MouseListener() {

			@Override
			public void mouseDoubleClick(MouseEvent e) {
				ViewerCell selectedCell = tableViewer.getCell(new Point(e.x, e.y));
				if (selectedCell == null) {
					return;
				}
				Object element = selectedCell.getElement();
				if (element == null || !(element instanceof RenamerItem)) {
					return;
				}
				RenamerItem item = (RenamerItem) element;
				if (item.getFile().isDirectory()) {
					renamer.setRootPath(item.getFile().getAbsolutePath());
					tableViewer.refresh();
				}
			}

			@Override
			public void mouseDown(MouseEvent e) {
				// TODO Auto-generated method stub

			}

			@Override
			public void mouseUp(MouseEvent e) {
				// TODO Auto-generated method stub

			}
		});
	}

	@Override
	public void setFocus() {
		// TODO Auto-generated method stub

	}

}
