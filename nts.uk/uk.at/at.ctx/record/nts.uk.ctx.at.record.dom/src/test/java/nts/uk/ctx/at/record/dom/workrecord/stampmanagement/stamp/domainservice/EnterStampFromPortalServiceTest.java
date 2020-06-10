package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.Mock;
import mockit.MockUp;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.RefectActualResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.Relieve;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.StampLocationInfor;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonPositionNo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonType;

/**
 * 
 * @author chungnt
 *
 */
//TODO: Chungnt

@RunWith(JMockit.class)
public class EnterStampFromPortalServiceTest {

	@Injectable
	private nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.EnterStampFromPortalService.Require require;
	
	@Test
	public void getters() {
		
		EnterStampFromPortalService fromPortalService = new EnterStampFromPortalService();
		NtsAssert.invokeGetters(fromPortalService);
		
	}
	
	/**
	 * if (!settingStampPotal.isPresent()) {
			throw new BusinessException("Msg_1632");
		}
	 */
	@Test
	public void test_settingStampPotal_isPresent() {
		
		AtomTask atomTask = AtomTask.of(() -> {});// dummy

		TimeStampInputResult inputResult = new TimeStampInputResult(
				new StampDataReflectResult(Optional.of(GeneralDate.today()), atomTask), Optional.of(atomTask));

		new MockUp<CreateStampDataForEmployeesService>() {
			@Mock
			public TimeStampInputResult create(CreateStampDataForEmployeesService.Require require, ContractCode contractCode, String employeeId,
					Optional<StampNumber> stampNumber, GeneralDateTime stampDateTime, Relieve relieve, ButtonType buttonType,
					RefectActualResult refActualResults, Optional<StampLocationInfor> stampLocationInfor) {
				return inputResult;
			}
		};

		
		new Expectations() {
			{
				require.getPortalStampSetting("DUMMY");
			}
		};
		
		NtsAssert.businessException("Msg_1632",
				() -> EnterStampFromPortalService.create(require, 
						new ContractCode("DUMMY"), 
						"DUMMY", 
						GeneralDateTime.now(), 
						new ButtonPositionNo(1),
						null));
	}
}
