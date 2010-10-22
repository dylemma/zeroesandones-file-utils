package edu.zao.fire.editors;

import java.util.ArrayList;
import java.util.List;

public class RenamerRuleEditorManager {

	private RenamerRuleEditor activeEditor;

	public static interface ActiveEditorListener {
		public void activeEditorChanged(RenamerRuleEditor newActiveEditor);
	}

	private final List<ActiveEditorListener> listeners = new ArrayList<ActiveEditorListener>();

	public RenamerRuleEditor getActiveEditor() {
		return activeEditor;
	}

	public void addListener(ActiveEditorListener listener) {
		listeners.add(listener);
	}

	public void removeListener(ActiveEditorListener listener) {
		listeners.remove(listener);
	}

	private void fireEditorChanged(RenamerRuleEditor editor) {
		for (ActiveEditorListener listener : listeners) {
			listener.activeEditorChanged(editor);
		}
	}

	public void setActiveEditor(RenamerRuleEditor editor) {
		if (editor != activeEditor) {
			fireEditorChanged(editor);
		}
		activeEditor = editor;
	}
}
