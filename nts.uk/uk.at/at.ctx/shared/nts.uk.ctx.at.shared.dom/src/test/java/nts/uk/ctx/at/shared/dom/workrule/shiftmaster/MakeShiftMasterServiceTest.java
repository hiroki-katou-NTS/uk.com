package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.MakeShiftMasterService.Require;

@RunWith(JMockit.class)
public class MakeShiftMasterServiceTest {

	@Injectable
	private Require require;

	@Injectable
	private nts.uk.ctx.at.shared.dom.WorkInformation.Require requireWorkinfo;

	@Test
	public void testMakeShiftMater_throw_Msg_1610() {
		ShiftMaster shiftMater = ShiftMasterInstanceHelper.getShiftMaterEmpty();
		shiftMater.setSiftCode(null);
		new Expectations() {
			{
				requireWorkinfo.checkNeededOfWorkTimeSetting(shiftMater.getWorkTypeCode().v());
				result = SetupType.OPTIONAL;
				
				require.checkExists(shiftMater.getCompanyId(), // dummy
						shiftMater.getWorkTypeCode().v(), // dummy
						null);// dummy;
				result = true;
			}
		};


		NtsAssert.businessException("Msg_1610", () -> {
			AtomTask persist = MakeShiftMasterService.makeShiftMater(requireWorkinfo, require, shiftMater.getCompanyId(),
					shiftMater.getShiftMasterCode().v(), shiftMater.getWorkTypeCode().v(),
					Optional.empty(),//worktime = null 
					shiftMater.getDisplayInfor());
			persist.run();
		});
	}

	@Test
	public void testMakeShiftMater_throw_Msg_1608() {
		ShiftMaster shiftMater = ShiftMasterInstanceHelper.getShiftMaterEmpty();
		shiftMater.setWorkTypeCode(null);

		NtsAssert.businessException("Msg_1608", () -> {
			AtomTask persist = MakeShiftMasterService.makeShiftMater(
					requireWorkinfo, require, 
					shiftMater.getCompanyId(),//dummy
					shiftMater.getShiftMasterCode().v(), //dummy
					null, //workType = null
					Optional.of(shiftMater.getWorkTimeCode().v()), shiftMater.getDisplayInfor());//dummy
			persist.run();
		});
	}

	@Test
	public void testMakeShiftMater_throw_Msg_1609() {
		ShiftMaster shiftMater = ShiftMasterInstanceHelper.getShiftMaterEmpty();
		NtsAssert.businessException("Msg_1609", () -> {
			AtomTask persist = MakeShiftMasterService.makeShiftMater(requireWorkinfo, require, shiftMater.getCompanyId(),
					shiftMater.getShiftMasterCode().v(), shiftMater.getWorkTypeCode().v(),
					Optional.of(shiftMater.getWorkTimeCode().v()), shiftMater.getDisplayInfor());
			persist.run();
		});
	}

	@Test
	public void testMakeShiftMater_throw_Msg_435() {
		ShiftMaster shiftMater = ShiftMasterInstanceHelper.getShiftMaterEmpty();
		shiftMater.setSiftCode(null);

		new Expectations() {
			{
				requireWorkinfo.checkNeededOfWorkTimeSetting(shiftMater.getWorkTypeCode().v());
				result = SetupType.REQUIRED;
			}
		};

		NtsAssert.businessException("Msg_435", () -> {
			AtomTask persist = MakeShiftMasterService.makeShiftMater(requireWorkinfo, require, shiftMater.getCompanyId(),
					shiftMater.getShiftMasterCode().v(), shiftMater.getWorkTypeCode().v(),
					Optional.empty(), shiftMater.getDisplayInfor());
			persist.run();
		});
	}

	@Test
	public void testMakeShiftMater_throw_Msg_434() {
		ShiftMaster shiftMater = ShiftMasterInstanceHelper.getShiftMaterEmpty();

		new Expectations() {
			{
				requireWorkinfo.checkNeededOfWorkTimeSetting(shiftMater.getWorkTypeCode().v());
				result = SetupType.NOT_REQUIRED;
			}
		};

		NtsAssert.businessException("Msg_434", () -> {
			AtomTask persist = MakeShiftMasterService.makeShiftMater(requireWorkinfo, require, shiftMater.getCompanyId(),
					shiftMater.getShiftMasterCode().v(), shiftMater.getWorkTypeCode().v(),
					Optional.of(shiftMater.getWorkTimeCode().v()), shiftMater.getDisplayInfor());
			persist.run();
		});
	}

	@Test
	public void testMakeShiftMater() {
		ShiftMaster shiftMater = ShiftMasterInstanceHelper.getShiftMaterEmpty();
		shiftMater.setSiftCode(null);

		new Expectations() {
			{
				requireWorkinfo.checkNeededOfWorkTimeSetting(shiftMater.getWorkTypeCode().v());
				result = SetupType.OPTIONAL;

				require.checkExists(shiftMater.getCompanyId(), // dummy
						shiftMater.getWorkTypeCode().v(), // dummy
						null);// dummy;
				result = false;
			}
		};
		

		NtsAssert.atomTask(() -> MakeShiftMasterService.makeShiftMater(requireWorkinfo, require, shiftMater.getCompanyId(),
				shiftMater.getShiftMasterCode().v(), shiftMater.getWorkTypeCode().v(),
				Optional.empty(), shiftMater.getDisplayInfor()),
				any -> require.insert(shiftMater, shiftMater.getWorkTypeCode().v(), null));
	}

}
