package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Injectable;
import mockit.Verifications;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.GetTargetIdentifiInforService;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;

@RunWith(JMockit.class)
public class GetUsingShiftTableRuleOfEmployeeServiceTest {
	
	@Injectable
	GetUsingShiftTableRuleOfEmployeeService.Require require;
	
	@Test
	public void test() {
		String sid = "sid";
		GeneralDate baseDate = GeneralDate.today();
		
		new Verifications() {{
			GetTargetIdentifiInforService.get(require, baseDate, sid);
			times = 0;
			
			GetShiftTableRuleForOrganizationService.get(require, (TargetOrgIdenInfor) any);
			times = 0;
		}};
        
        GetUsingShiftTableRuleOfEmployeeService.get(require, sid, baseDate);
		
		new Verifications() {{
			GetTargetIdentifiInforService.get(require, baseDate, sid);
			times = 1;
			
			GetShiftTableRuleForOrganizationService.get(require, (TargetOrgIdenInfor) any);
			times = 1;
			
		}};
		
	}
}
