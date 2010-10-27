package edu.zao.fire.editors;

import org.eclipse.ui.part.EditorPart;

import edu.zao.fire.RenamerRule;

/**
 * Common ground for the various renamer rule editors.
 * 
 * @author dylan
 * 
 */
public abstract class RenamerRuleEditor extends EditorPart {
	/**
	 * @return The RenamerRule that this editor makes changes to.
	 */
	public abstract RenamerRule getRule();
}
