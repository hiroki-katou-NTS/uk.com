package nts.uk.ctx.at.schedule.dom.shift.management.shifttable;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Collections;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.shr.com.enumcommon.NotUseAtr;

@RunWith(JMockit.class)
public class GetShiftTableRuleForOrganizationServiceTest {
	
	@Injectable
	GetShiftTableRuleForOrganizationService.Require require;
	
	@Test
	public void get_OrganizationRule() {
		
		TargetOrgIdenInfor targetOrg = TargetOrgIdenInfor.creatIdentifiWorkplace("wp-id");
		
		// mock
		ShiftTableRule rule =  ShiftTableRule.create(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, 
				Optional.empty(), Collections.emptyList(), Optional.empty());;
		ShiftTableRuleForOrganization ogrRule = new ShiftTableRuleForOrganization(targetOrg, rule);
		
		new Expectations() {
            {
            	require.getOrganizationShiftTable( (TargetOrgIdenInfor) any );
            	result = Optional.of(ogrRule);
            }
        };
		
        // Act
		Optional<ShiftTableRule> result = GetShiftTableRuleForOrganizationService.get(require, targetOrg);
		
		// Assert
		assertThat(result.get()).isEqualTo(rule);
		
	}
	
	@Test
	public void get_CompanyRule() {
		
		TargetOrgIdenInfor targetOrg = TargetOrgIdenInfor.creatIdentifiWorkplace("wp-id");
		
		// mock
		ShiftTableRule rule =  ShiftTableRule.create(NotUseAtr.NOT_USE, NotUseAtr.NOT_USE, 
				Optional.empty(), Collections.emptyList(), Optional.empty());;
		ShiftTableRuleForCompany comRule = new ShiftTableRuleForCompany(rule);
		
		new Expectations() {
            {
            	require.getOrganizationShiftTable( (TargetOrgIdenInfor) any );
            	// result = Optional.empty
            	
            	require.getCompanyShiftTable();
            	result = Optional.of(comRule);
            }
        };
		
        // Act
		Optional<ShiftTableRule> result = GetShiftTableRuleForOrganizationService.get(require, targetOrg);
		
		// Assert
		assertThat(result.get()).isEqualTo(rule);
		
	}
	
	
	@Test
	public void get_empty() {
		
		TargetOrgIdenInfor targetOrg = TargetOrgIdenInfor.creatIdentifiWorkplace("wp-id");
		
		// mock
		new Expectations() {
            {
            	require.getOrganizationShiftTable( (TargetOrgIdenInfor) any );
            	// result = Optional.empty
            	
            	require.getCompanyShiftTable();
            	//result = Optional.empty()
            }
        };
		
        // Act
		Optional<ShiftTableRule> result = GetShiftTableRuleForOrganizationService.get(require, targetOrg);
		
		// Assert
		assertThat(result).isEmpty();
		
	}
	

}
