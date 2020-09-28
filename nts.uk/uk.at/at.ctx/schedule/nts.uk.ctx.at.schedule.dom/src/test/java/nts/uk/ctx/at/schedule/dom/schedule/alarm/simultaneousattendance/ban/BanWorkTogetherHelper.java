package nts.uk.ctx.at.schedule.dom.schedule.alarm.simultaneousattendance.ban;

import java.util.ArrayList;
import java.util.List;

import nts.gul.text.IdentifierUtil;

public class BanWorkTogetherHelper {
	/**
	 * creatEmpsCanNotSameHolidays
	 * @param size
	 * @return
	 */
	public static List<String> creatEmpBanWorkTogetherLst(int size){
		List<String> result = new ArrayList<>();
		
		for(int i = 0; i <= size; i++){
			result.add(IdentifierUtil.randomUniqueId());
		}
		return result;
	}
}
