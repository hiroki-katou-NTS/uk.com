package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.ContractCode;
import nts.uk.ctx.at.record.dom.stamp.card.stampcard.StampNumber;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.RefectActualResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.EnterStampFromICCardService.Require;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonPositionNo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PageNo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampButton;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork.worktime.overtimedeclaration.OvertimeDeclaration;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;

/**
 * 
 * @author chungnt
 *
 */
@RunWith(JMockit.class)
public class EnterStampFromICCardServiceTest {

	@Injectable
	private Require require;

	@Test
	public void getters() {
		EnterStampFromICCardService cardService = new EnterStampFromICCardService();
		NtsAssert.invokeGetters(cardService);
	}

	/**
	 * if (!stampCardOpt.isPresent()) {
			throw new BusinessException("Msg_433");
		}
	 */
	@Test
	public void testCreate1() {
		EnterStampFromICCardService cardService = new EnterStampFromICCardService();
		
		ContractCode code = new ContractCode("DUMMY");
		StampNumber number = new StampNumber("DUMMY");
		GeneralDateTime dateTime = GeneralDateTime.now();
		StampButton stampButton = new StampButton(new PageNo(1), new ButtonPositionNo(1));
		OvertimeDeclaration declaration = new OvertimeDeclaration(new AttendanceTime(1), new AttendanceTime(1));
		RefectActualResult result = new RefectActualResult("DUMMY", new WorkLocationCD("DUMMY"), new WorkTimeCode("DUMMY"), declaration);
		
		new Expectations() {
			{
				require.getByCardNoAndContractCode("DUMMY", "DUMMY");
			}
		};
		
		NtsAssert.businessException("Msg_433", () -> cardService.create(require, code, number, dateTime, stampButton, result));
	}

}
