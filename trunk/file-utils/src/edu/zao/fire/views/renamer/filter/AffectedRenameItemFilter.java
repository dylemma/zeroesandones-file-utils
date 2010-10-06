package edu.zao.fire.views.renamer.filter;

import edu.zao.fire.views.renamer.RenamerItem;

public class AffectedRenameItemFilter implements RenamerItemFilter {

	@Override
	public boolean accept(RenamerItem item) {
		return (item.getFile().getName().compareTo(item.getTarget()) != 0);
	}

}
