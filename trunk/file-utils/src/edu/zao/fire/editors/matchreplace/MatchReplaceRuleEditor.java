package edu.zao.fire.editors.matchreplace;

import org.eclipse.core.runtime.IProgressMonitor;
import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.layout.GridLayout;
import org.eclipse.swt.widgets.Button;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Group;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;
import org.eclipse.ui.IEditorInput;
import org.eclipse.ui.IEditorSite;
import org.eclipse.ui.PartInitException;

import edu.zao.fire.MatchReplaceRule;
import edu.zao.fire.RenamerRule;
import edu.zao.fire.editors.RenamerRuleEditor;
import edu.zao.fire.rcp.Activator;

public class MatchReplaceRuleEditor extends RenamerRuleEditor {
	public final static String ID = "file-utils.editors.matchreplace";

	private MatchReplaceRule input;

	public MatchReplaceRuleEditor() {
	}

	@Override
	public RenamerRule getRule() {
		return input;
	}

	@Override
	public void doSave(IProgressMonitor monitor) {
		// TODO Auto-generated method stub

	}

	@Override
	public void doSaveAs() {
		// TODO Auto-generated method stub

	}

	@Override
	public void init(IEditorSite site, IEditorInput input) throws PartInitException {
		setSite(site);
		setInput(input);
	}

	@Override
	public boolean isDirty() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean isSaveAsAllowed() {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public void createPartControl(Composite parent) {

		parent.setLayout(new GridLayout(1, false));

		// set up the area for the match/replace components
		Composite matchReplaceArea = new Composite(parent, SWT.BORDER);
		matchReplaceArea.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		matchReplaceArea.setLayout(new GridLayout(2, false));

		// set up the label for the "Match" field
		Label matchLabel = new Label(matchReplaceArea, SWT.SINGLE);
		matchLabel.setText("Match");
		matchLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false));

		// set up the text box for the "Match" field
		Text matchText = new Text(matchReplaceArea, SWT.SINGLE | SWT.BORDER);
		matchText.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		// set up the label for the "Replace" field
		Label replaceLabel = new Label(matchReplaceArea, SWT.SINGLE);
		replaceLabel.setText("Replace");
		replaceLabel.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false));

		// set up the text box for the "Replace" field
		Text replaceText = new Text(matchReplaceArea, SWT.SINGLE | SWT.BORDER);
		replaceText.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));

		// set up the Regular Expressions check box
		Button regexCheckBox = new Button(parent, SWT.CHECK);
		regexCheckBox.setText("Use Regular Expressions");
		regexCheckBox.setLayoutData(new GridData(SWT.RIGHT, SWT.TOP, false, false));

		// set up the Capitalization button area
		Group capitalizationGroup = new Group(parent, SWT.NONE);
		capitalizationGroup.setText("Capitalization");
		capitalizationGroup.setLayoutData(new GridData(SWT.FILL, SWT.TOP, true, false));
		capitalizationGroup.setLayout(new GridLayout(1, false));

		// set up the Capitalization radio buttons
		Button radioAllCaps = new Button(capitalizationGroup, SWT.RADIO);
		radioAllCaps.setText("ALL CAPS");

		Button radioAllLower = new Button(capitalizationGroup, SWT.RADIO);
		radioAllLower.setText("all lowercase");

		Button radioSentenceCaps = new Button(capitalizationGroup, SWT.RADIO);
		radioSentenceCaps.setText("Sentence capitalization");

		Button radioTitleCaps = new Button(capitalizationGroup, SWT.RADIO);
		radioTitleCaps.setText("Title Capitalization");

	}

	@Override
	public void setFocus() {
		Activator.getDefault().getEditorManager().setActiveEditor(this);
	}

}
