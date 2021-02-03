package nts.uk.ctx.at.schedule.dom.schedule.alarm.worktogether.ban;

import java.util.ArrayList;
import java.util.List;

import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

public class BanWorkTogetherHelper {
	
	public static BanWorkTogether  banWorkTogether = BanWorkTogether.createByNightShift(
			TargetOrgIdenInfor.creatIdentifiWorkplace("DUMMY"),
			new BanWorkTogetherCode("001"),
			new BanWorkTogetherName("同時出勤禁1"),
			creatEmpBanWorkTogetherLst(5),
			3
			);
	
	/**
	 * creatEmpsCanNotSameHolidays
	 * @param size
	 * @return
	 */
	public static List<String> creatEmpBanWorkTogetherLst(int size){
		List<String> result = new ArrayList<>();
		
		for(int i = 1; i <= size; i++){
			result.add(IdentifierUtil.randomUniqueId());
		}
		return result;
	}
}
