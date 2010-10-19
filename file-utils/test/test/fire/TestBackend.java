package test.fire;

import java.io.File;

import edu.zao.fire.MatchReplaceRule;
import edu.zao.fire.Renamer;

public class TestBackend {

	/**
	 * @param args
	 */
	public static void main(String[] args) {
		Renamer renamer = new Renamer();
		MatchReplaceRule rule = new MatchReplaceRule();

		rule.setMatchString("s");
		rule.setReplaceString("sSs");

		File root = new File("../../");
		renamer.setCurrentDirectory(root);
		renamer.setCurrentRule(rule);

		renamer.applyChanges();
	}
}
