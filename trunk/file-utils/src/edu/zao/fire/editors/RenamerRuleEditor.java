package edu.zao.fire.editors;

import org.eclipse.ui.part.EditorPart;

import edu.zao.fire.RenamerRule;

public abstract class RenamerRuleEditor extends EditorPart {
	public abstract RenamerRule getRule();
}
