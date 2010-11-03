package edu.zao.fire.editors.metadata;

import org.eclipse.jface.viewers.IStructuredContentProvider;
import org.eclipse.jface.viewers.Viewer;

public class SelectedTagsContentProvider implements IStructuredContentProvider {

	MetadataTagList input;

	@Override
	public Object[] getElements(Object inputElement) {
		return input.getTags();
	}

	@Override
	public void dispose() {
		// TODO Auto-generated method stub

	}

	@Override
	public void inputChanged(Viewer viewer, Object oldInput, Object newInput) {
		if (newInput instanceof MetadataTagList) {
			input = (MetadataTagList) newInput;
		} else {
			throw new IllegalArgumentException("newInput must be a MetadataTagList");
		}
	}
}