package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import java.util.Optional;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.GetTargetIdentifiInforService;
/**
 * 社員が使うシフト表のルールを取得する
 * @author lan_lt
 *
 */
public class GetUsingShiftTableRuleOfEmployeeService {
	
	/**
	 * 取得する
	 * @param require
	 * @param sid 社員ID
	 * @param date 基準日
	 * @return Optional<ShiftTableRule> 
	 */
    public static Optional<ShiftTableRule> get(Require require, String sid, GeneralDate date ) {
    	
    	val targetOrg = GetTargetIdentifiInforService.get(require, date, sid);
    	
    	return GetShiftTableRuleForOrganizationService.get(require, targetOrg);
    }
    

    public static interface Require extends GetTargetIdentifiInforService.Require, GetShiftTableRuleForOrganizationService.Require{
    	
    }
}
