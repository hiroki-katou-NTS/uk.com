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
	
    public static Optional<ShiftTableRule> get(Require require, String sid, GeneralDate date ) {
    	
    	val targetOrg = GetTargetIdentifiInforService.get(require, date, sid);
    	
    	return GetShiftTableRuleForOrganizationService.get(require, targetOrg);
    }
    

    public static interface Require extends GetTargetIdentifiInforService.Require, GetShiftTableRuleForOrganizationService.Require{
    	
    }
}
