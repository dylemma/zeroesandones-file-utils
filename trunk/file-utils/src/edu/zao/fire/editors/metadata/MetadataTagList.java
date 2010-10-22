package edu.zao.fire.editors.metadata;

import java.util.ArrayList;

public class MetadataTagList {

	// TODO: instead of holding strings, this should eventually be holding some
	// kind of object that represents a metadata tag.
	private final ArrayList<String> tags = new ArrayList<String>();

	public Object[] getTags() {
		return tags.toArray();
	}

	public void addTag(String tag) {
		tags.add(tag);
	}
}
