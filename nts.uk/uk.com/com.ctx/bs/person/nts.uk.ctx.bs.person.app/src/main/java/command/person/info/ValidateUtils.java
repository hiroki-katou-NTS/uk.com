package command.person.info;

public class ValidateUtils {
	private static final String JP_SPACE = "　";
	
	public static boolean validateName(String name){
		if (name == null || name.trim().equals("")){
			return true;
		}
		if (name.endsWith(JP_SPACE) || name.startsWith(JP_SPACE)){
			return false;
		}
		if (name.contains(JP_SPACE)){
			return true;
		}
		return false;
	}
}
