package edu.zao.fire.commands;

import org.eclipse.core.commands.AbstractHandler;
import org.eclipse.core.commands.HandlerEvent;
import org.eclipse.core.commands.IHandler;
import org.eclipse.jface.viewers.ISelection;
import org.eclipse.jface.viewers.IStructuredSelection;
import org.eclipse.ui.ISelectionListener;
import org.eclipse.ui.ISelectionService;
import org.eclipse.ui.IWorkbenchPart;
import org.eclipse.ui.PlatformUI;

import edu.zao.fire.Renamer;
import edu.zao.fire.filters.UserIgnoreFileFilter;

public abstract class UserSelectionAbstractHandler extends AbstractHandler {

	protected boolean alreadyInstalledSelectionListener = false;

	protected void installSelectionListener() {
		if (alreadyInstalledSelectionListener) {
			return;
		}

		/*
		 * NOTE: the first time this function is called will be before the
		 * active workbench window is instantiated Eclipse RCP causes this
		 * function to fail quietly, so we just don't set the already installed
		 * flag true until after it is successful
		 */
		ISelectionService selectionService = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService();
		final IHandler handler = this;
		selectionService.addSelectionListener(new ISelectionListener() {
			@Override
			public void selectionChanged(IWorkbenchPart part, ISelection selection) {
				fireHandlerChanged(new HandlerEvent(handler, true, false));
			}
		});

		// set the already installed flag after the listener is successfully
		// added
		alreadyInstalledSelectionListener = true;
	}

	@Override
	public void setEnabled(Object evaluationContext) {
		installSelectionListener();
	}

	protected static IStructuredSelection getSelection() {
		ISelectionService selectionService = PlatformUI.getWorkbench().getActiveWorkbenchWindow().getSelectionService();
		IStructuredSelection selection = (IStructuredSelection) selectionService.getSelection();
		return selection;
	}

	protected UserIgnoreFileFilter getFilter() {
		return Renamer.getDefault().getUserFilters().getIndividualFilter();
	}
}
