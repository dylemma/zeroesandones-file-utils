package edu.zao.fire.views.renamer;

import org.eclipse.swt.SWT;
import org.eclipse.swt.layout.GridData;
import org.eclipse.swt.widgets.Composite;
import org.eclipse.swt.widgets.Label;
import org.eclipse.swt.widgets.Text;

/**
 * Creates a labelled text box within a specified parent composite
 * 
 * @author Dylan
 */
public class LabelledTextControl {
	private final Label label;
	private final Text text;

	// private final Composite composite;

	public LabelledTextControl(Composite parent, String labelText) {
		// composite = new Composite(parent, SWT.NONE);
		// composite.setLayout(new GridLayout(2, false));

		label = new Label(parent, SWT.SINGLE);
		label.setText(labelText);
		GridData labelGridData = new GridData(SWT.RIGHT, SWT.CENTER, false, false);
		label.setLayoutData(labelGridData);

		text = new Text(parent, SWT.SINGLE | SWT.BORDER);
		GridData textGridData = new GridData(SWT.FILL, SWT.CENTER, true, true);
		textGridData.grabExcessHorizontalSpace = true;
		text.setLayoutData(textGridData);
	}

	public Label getLabel() {
		return label;
	}

	public Text getText() {
		return text;
	}
}
