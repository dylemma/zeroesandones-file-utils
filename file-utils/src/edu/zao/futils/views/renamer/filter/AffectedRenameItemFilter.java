package edu.zao.futils.views.renamer.filter;

import edu.zao.futils.views.renamer.RenamerItem;

public class AffectedRenameItemFilter implements RenamerItemFilter {

	@Override
	public boolean accept(RenamerItem item) {
		return (item.getFile().getName().compareTo(item.getTarget()) != 0);
	}

}
