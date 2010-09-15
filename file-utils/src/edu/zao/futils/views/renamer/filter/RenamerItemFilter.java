package edu.zao.futils.views.renamer.filter;

import edu.zao.futils.views.renamer.RenamerItem;

public interface RenamerItemFilter {
	boolean accept(RenamerItem item);
}