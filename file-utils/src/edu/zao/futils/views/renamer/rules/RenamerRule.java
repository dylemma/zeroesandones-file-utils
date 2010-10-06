package edu.zao.futils.views.renamer.rules;

import java.io.File;

public interface RenamerRule {
	/**
	 * @param file A file that this RenamerRule will be applied to.
	 * @return The new <i>full</i> name of the file 
	 */
	String getNewName(File file);
}
