package nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp;

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
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationCD;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.EnterStampForSharedStampService.Require;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.StampDataReflectResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.stamp.domainservice.TimeStampInputResult;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.ButtonPositionNo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.PageNo;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.StampButton;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.TimeStampSetShareTStamp;
import nts.uk.ctx.at.record.dom.workrecord.stampmanagement.timestampsetting.prefortimestaminput.TimeStampSetShareTStampHelper;
import nts.uk.ctx.at.shared.dom.common.time.AttendanceTime;
import nts.uk.ctx.at.shared.dom.dailyattdcal.dailywork.worktime.overtimedeclaration.OvertimeDeclaration;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;

/**
 * 
 * @author chungnt
 *
 */
//TODO : Chungnt
@RunWith(JMockit.class)
public class EnterStampForSharedStampServiceTest {

	@Injectable
	private Require require;
	
	@Test
	public void getters() {
		EnterStampForSharedStampService enterStampForSharedStampService = new EnterStampForSharedStampService();
		NtsAssert.invokeGetters(enterStampForSharedStampService);
	}
	
	/**
	 * if (!setShareTStamp.isPresent()) {
			throw new BusinessException("Msg_1632");
		}
	 */
	@Test
	public void test_TimeStampSetShareTStamp_empty() {
		
		ContractCode contractCode = new ContractCode("DUMMY");
		StampNumber stampNumber = new StampNumber("DUMMY");
		GeneralDateTime dateTime = GeneralDateTime.now();
		StampButton stampButton = new StampButton(new PageNo(1), new ButtonPositionNo(2));
		OvertimeDeclaration declaration = new OvertimeDeclaration(new AttendanceTime(1), new AttendanceTime(1));
		RefectActualResult result = new RefectActualResult("DUMMY", new WorkLocationCD("DUMMY"),
				new WorkTimeCode("DUMMY"), declaration);
		Relieve relieve = new Relieve(AuthcMethod.ID_AUTHC, StampMeans.INDIVITION);
		
		AtomTask atomTask = AtomTask.of(() -> {});// dummy

		TimeStampInputResult inputResult = new TimeStampInputResult(
				new StampDataReflectResult(Optional.of(GeneralDate.today()), atomTask), Optional.of(atomTask));

		new MockUp<EnterStampForSharedStampService>() {
			@Mock
			public TimeStampInputResult create(EnterStampForSharedStampService.Require require, String conteactCode,
					String employeeID, Optional<StampNumber> StampNumber, Relieve relieve,
					GeneralDateTime stmapDateTime, StampButton stampButton, RefectActualResult refActualResult) {
				return inputResult;
			}
		};
		
		new Expectations() {
			{
				require.gets("DUMMY");
			}
		};
		
		NtsAssert.businessException("Msg_1632", () -> EnterStampForSharedStampService.create(require ,contractCode.v(), "DUMMY", Optional.of(stampNumber),
				relieve, dateTime, stampButton , result));
	}
	
	/**
	 * if (!buttonSetting.isPresent()) {
			throw new BusinessException("Msg_1632");
		}
	 */
	@Test
	public void test_create() {
		
		ContractCode contractCode = new ContractCode("DUMMY");
		StampNumber stampNumber = new StampNumber("DUMMY");
		GeneralDateTime dateTime = GeneralDateTime.now();
		StampButton stampButton = new StampButton(new PageNo(1), new ButtonPositionNo(2));
		OvertimeDeclaration declaration = new OvertimeDeclaration(new AttendanceTime(1), new AttendanceTime(1));
		RefectActualResult result = new RefectActualResult("DUMMY", new WorkLocationCD("DUMMY"),
				new WorkTimeCode("DUMMY"), declaration);
		Relieve relieve = new Relieve(AuthcMethod.ID_AUTHC, StampMeans.INDIVITION);
		
		TimeStampSetShareTStamp timeStampSetShareTStamp = TimeStampSetShareTStampHelper.getDefault();
		
		AtomTask atomTask = AtomTask.of(() -> {});// dummy

		TimeStampInputResult inputResult = new TimeStampInputResult(
				new StampDataReflectResult(Optional.of(GeneralDate.today()), atomTask), Optional.of(atomTask));

		new MockUp<EnterStampForSharedStampService>() {
			@Mock
			public TimeStampInputResult create(EnterStampForSharedStampService.Require require, String conteactCode,
					String employeeID, Optional<StampNumber> StampNumber, Relieve relieve,
					GeneralDateTime stmapDateTime, StampButton stampButton, RefectActualResult refActualResult) {
				return inputResult;
			}
		};
		
		new Expectations() {
			{
				require.gets("DUMMY");
				result = Optional.of(timeStampSetShareTStamp);
			}
		};
		
		TimeStampInputResult timeStampInputResult = EnterStampForSharedStampService.create(require,
				contractCode.v(),
				"DUMMY",
				Optional.of(stampNumber),
				relieve,
				dateTime,
				stampButton,
				result);
		
//		assertThat(timeStampInputResult.getStampDataReflectResult().getReflectDate()).isEqualTo(GeneralDate.today());
	}

}
