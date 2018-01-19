package nts.uk.ctx.pereg.app.command.person.info.category;

public class CheckNameSpace {
         public static boolean checkName(String name){
        	 String ctgName = name.trim();
     		if (ctgName.equals("") || ctgName.replace("　", "").equals("")) {
     			return true;     		}
        	return false;
         }
}
