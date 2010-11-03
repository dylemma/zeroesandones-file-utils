package edu.zao.fire.editors.list;

import org.eclipse.jface.resource.ImageDescriptor;

import edu.zao.fire.ListRule;
import edu.zao.fire.editors.RenamerRuleEditorInput;

public class ListRuleEditorInput extends RenamerRuleEditorInput {

	private final ListRule rule;

	public ListRuleEditorInput() {
		rule = new ListRule();
	}

	public ListRuleEditorInput(ListRule rule) {
		this.rule = rule;
	}

	public ListRule getRule() {
		return rule;
	}

	@Override
	public String getDefaultName() {
		return "new List rule";
	}

	@Override
	public ImageDescriptor getImageDescriptor() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public String getToolTipText() {
		// TODO Auto-generated method stub
		return "";
	}

}
