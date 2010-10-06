package edu.zao.fire.views.renamer;

import java.io.File;

public class RenamerItem {
	private File m_file;
	private String m_target;

	public RenamerItem(File file) {
		m_file = file;
		m_target = file.getName();
	}

	public File getFile() {
		return m_file;
	}

	public void setFile(File file) {
		m_file = file;
	}

	public String getTarget() {
		return m_target;
	}

	public void setTarget(String target) {
		m_target = target;
	}

	@Override
	public String toString() {
		return m_file.getName() + " -> " + m_target;
	}
}
