package edu.zao.fire.views.renamer.filter;

import java.util.LinkedList;
import java.util.List;

import edu.zao.fire.views.renamer.RenamerItem;

public class CompoundRenamerItemFilter implements RenamerItemFilter {
	private final List<RenamerItemFilter> innerFilters = new LinkedList<RenamerItemFilter>();

	public CompoundRenamerItemFilter(RenamerItemFilter... filters) {
		for (RenamerItemFilter filter : filters) {
			innerFilters.add(filter);
		}
	}

	public void add(RenamerItemFilter filter) {
		innerFilters.add(filter);
	}

	public void remove(RenamerItemFilter filter) {
		innerFilters.remove(filter);
	}

	@Override
	public boolean accept(RenamerItem item) {
		for (RenamerItemFilter filter : innerFilters) {
			if (!filter.accept(item)) {
				return false;
			}
		}
		return true;
	}

}
