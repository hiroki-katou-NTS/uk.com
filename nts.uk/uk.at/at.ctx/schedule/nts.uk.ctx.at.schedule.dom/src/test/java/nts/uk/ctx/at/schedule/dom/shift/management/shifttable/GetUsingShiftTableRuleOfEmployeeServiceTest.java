package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mocked;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.GetTargetIdentifiInforService;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@RunWith(JMockit.class)
public class GetUsingShiftTableRuleOfEmployeeServiceTest {
	
	@Injectable
	GetUsingShiftTableRuleOfEmployeeService.Require require;
	
	@SuppressWarnings({ "static-access" })
	@Test
	public void get_ShiftTable_Workplace(@Mocked GetTargetIdentifiInforService orgService, @Mocked GetShiftTableRuleForOrganizationService shiftTableRule) {
		GeneralDate baseDate = GeneralDate.today();
		// mock
		TargetOrgIdenInfor targetOrg = TargetOrgIdenInfor.creatIdentifiWorkplace("wp-id");
		
		ShiftTableRule rule =  ShiftTableRule.create(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, 
				Optional.empty(), Collections.emptyList(), Optional.empty());
		
		new Expectations() {
            {
            	orgService.get(require, baseDate, "sid");
            	result = targetOrg;
            	
            	shiftTableRule.get(require, targetOrg);
            	result = Optional.of(rule);
            	
            }
        };
        
        // Act
		Optional<ShiftTableRule> result = GetUsingShiftTableRuleOfEmployeeService.get(require, "sid", baseDate);
		
		// Assert
		assertThat(result.get()).isEqualTo(rule);
		
	}
}
