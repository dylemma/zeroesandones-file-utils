package edu.zao.fire;

import java.io.File;
import java.io.IOException;
import java.io.Serializable;

public interface RenamerRule extends Serializable {
	/**
	 * @param file
	 *            A file that this RenamerRule will be applied to.
	 * @return The new <i>full</i> name of the file
	 * @throws IOException
	 */
	String getNewName(File file) throws IOException;
}
