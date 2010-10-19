package edu.zao.fire.editors;

import org.eclipse.jface.action.Action;
import org.eclipse.jface.dialogs.MessageDialog;
import org.eclipse.ui.IWorkbenchWindow;

import edu.zao.fire.rcp.Activator;

public class MessagePopupAction extends Action {

	private final IWorkbenchWindow window;

	public MessagePopupAction(String text, IWorkbenchWindow window) {
		super(text);
		this.window = window;
		// The id is used to refer to the action in a menu or toolbar
		setId("message.popup.action");
		// Associate the action with a pre-defined command, to allow key
		// bindings.
		setActionDefinitionId("message.popup.action");
		setImageDescriptor(Activator.getImageDescriptor("/icons/flame-16.png"));
	}

	@Override
	public void run() {
		MessageDialog.openInformation(window.getShell(), "Open", "Open Message Dialog!");
	}
}
