package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.record.dom.stamp.application.SettingsUsingEmbossing;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampMeans;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampFunctionAvailableService.Require;
/**
 * 
 * @author tutk
 *
 */
@RunWith(JMockit.class)
public class StampFunctionAvailableServiceTest{

	@Injectable
	private Require require;
	
	@Injectable nts.uk.ctx.at.record.dom.stamp.card.stampcard.AutoCreateStampCardNumberService.Require require1;
	
	/**
	 * cardCreate = require.get() !cardCreate.isPresent()
	 */
	@Test
	public void testStampFunctionAvailableService_1() {
		String employeeId = "employeeId";//dummy
		
		new Expectations() {
			{
				require.get();
				
			}
		};
		MakeUseJudgmentResults data = StampFunctionAvailableService.decide(require, employeeId, StampMeans.FINGER_AUTHC);
		assertThat(data.getUsed()).isEqualTo(CanEngravingUsed.ENGTAVING_FUNCTION_CANNOT_USED);
		assertThat(data.getCardResult()).isEqualTo(Optional.empty());
	}
	
	
	/**
	 *  require.get() cardCreate.get().canUsedStamping(stampMeans)
	 */
	@Test
	public void testStampFunctionAvailableService_2() {
		String employeeId = "employeeId";//dummy
		
		new Expectations() {
			{
				require.get();
				result = Optional.of(new SettingsUsingEmbossing("companyId", false, false, false, false, false, false));
				
			}
		};
		MakeUseJudgmentResults data = StampFunctionAvailableService.decide(require, employeeId, StampMeans.NAME_SELECTION);
		assertThat(data.getUsed()).isEqualTo(CanEngravingUsed.ENGTAVING_FUNCTION_CANNOT_USED);
		assertThat(data.getCardResult()).isEqualTo(Optional.empty());
	}
	
	
	/**
	 * if (!(stampMeans == StampMeans.INDIVITION || stampMeans == StampMeans.PORTAL
				|| stampMeans == StampMeans.SMART_PHONE)) {
			return MakeUseJudgmentResults.get();

		}
	 */
	@Test
	public void testStampFunctionAvailableService_3() {
		String employeeId = "employeeId";//dummy
		
		new Expectations() {
			{
				require.get();
				result = Optional.of(new SettingsUsingEmbossing("companyId", false, false, true, false, false, false));
				
			}
		};
		
		MakeUseJudgmentResults data = StampFunctionAvailableService.decide(require, employeeId, StampMeans.IC_CARD);
		assertThat(data.getUsed()).isEqualTo(CanEngravingUsed.AVAILABLE);
	}
	
//	
//	@Test
//	public void testStampFunctionAvailableService_4() {
//		String employeeId = "employeeId";//dummy
//		
//		new Expectations() {
//			{
//				require.get();
//				result = Optional.of(new SettingsUsingEmbossing("companyId", true, false, false, false, false, false));
//				
//			}
//		};
//		
//		MakeUseJudgmentResults data = StampFunctionAvailableService.decide(require, employeeId, StampMeans.SMART_PHONE);
//		assertThat(data.getUsed()).isNotEqualTo(CanEngravingUsed.AVAILABLE);
//	}
//	
//	@Test
//	public void testStampFunctionAvailableService_5() {
//		String employeeId = "employeeId";//dummy
//		
//		new Expectations() {
//			{
//				require.get();
//				result = Optional.of(new SettingsUsingEmbossing("companyId", true, false, false, false, false, false));
//				
//			}
//		};
//		
//		MakeUseJudgmentResults data = StampFunctionAvailableService.decide(require, employeeId, StampMeans.INDIVITION);
//		assertThat(data.getUsed()).isNotEqualTo(CanEngravingUsed.AVAILABLE);
//	}
	
	@Test
	public void testStampFunctionAvailableService_6() {
		String employeeId = "employeeId";//dummy
		
		new Expectations() {
			{
				require.get();
				result = Optional.of(new SettingsUsingEmbossing("companyId", true, false, false, false, false, false));
				
			}
		};
		
		MakeUseJudgmentResults data = StampFunctionAvailableService.decide(require, employeeId, StampMeans.PORTAL);
		assertThat(data.getUsed()).isNotEqualTo(CanEngravingUsed.AVAILABLE);
	}
	
	@Test
	public void testStampFunctionAvailableService_7() {
		String employeeId = "employeeId";//dummy
		
		new Expectations() {
			{
				require.get();
				result = Optional.of(new SettingsUsingEmbossing("companyId", true, false, false, false, false, false));
				
				require.getListStampCard(employeeId);
				result = StampFunctionAvailableServiceHelper.getStampCards();
				
			}
		};
		
		MakeUseJudgmentResults data = StampFunctionAvailableService.decide(require, employeeId, StampMeans.PORTAL);
		assertThat(data.getUsed()).isEqualTo(CanEngravingUsed.ENGTAVING_FUNCTION_CANNOT_USED);
	}
	
}
