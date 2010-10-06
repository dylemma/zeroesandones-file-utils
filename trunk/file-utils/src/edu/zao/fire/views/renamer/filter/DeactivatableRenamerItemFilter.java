package edu.zao.fire.views.renamer.filter;

import edu.zao.fire.views.renamer.RenamerItem;

public class DeactivatableRenamerItemFilter implements RenamerItemFilter {
	private final RenamerItemFilter innerFilter;
	private boolean active;

	public DeactivatableRenamerItemFilter(RenamerItemFilter innerFilter) {
		this.innerFilter = innerFilter;
	}

	public void setActive(boolean active) {
		this.active = active;
	}

	@Override
	public boolean accept(RenamerItem item) {
		if (active) {
			return innerFilter.accept(item);
		} else {
			return true;
		}
	}

}
