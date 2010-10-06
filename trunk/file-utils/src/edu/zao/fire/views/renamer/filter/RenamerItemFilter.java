package edu.zao.fire.views.renamer.filter;

import edu.zao.fire.views.renamer.RenamerItem;

public interface RenamerItemFilter {
	boolean accept(RenamerItem item);
}