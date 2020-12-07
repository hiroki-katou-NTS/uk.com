package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Injectable;
import mockit.Mocked;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.GetTargetIdentifiInforService;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
@SuppressWarnings("static-access")
@RunWith(JMockit.class)
public class GetUsingShiftTableRuleOfEmployeeServiceTest {
	
	@Injectable
	GetUsingShiftTableRuleOfEmployeeService.Require require;
	
	@Test
	public void get_using_shift_table_employee(@Mocked GetTargetIdentifiInforService orgService
			, @Mocked GetShiftTableRuleForOrganizationService stRuleService) {
		String sid = "sid";
		GeneralDate baseDate = GeneralDate.today();
		
		new Verifications() {{
			orgService.get(require, baseDate, sid);
			times = 0;
			
			stRuleService.get(require, (TargetOrgIdenInfor) any);
			times = 0;
		}};
        
        GetUsingShiftTableRuleOfEmployeeService.get(require, sid, baseDate);
		
		new Verifications() {{
			orgService.get(require, baseDate, sid);
			times = 1;
			
			stRuleService.get(require, (TargetOrgIdenInfor) any);
			times = 1;
			
		}};
		
	}
}
