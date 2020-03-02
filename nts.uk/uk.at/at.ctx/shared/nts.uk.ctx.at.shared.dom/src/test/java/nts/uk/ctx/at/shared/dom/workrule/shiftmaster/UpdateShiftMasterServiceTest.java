package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.task.tran.AtomTask;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.UpdateShiftMasterService.Require;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

@RunWith(JMockit.class)
public class UpdateShiftMasterServiceTest {

	@Injectable
	private Require require;

	@Injectable
	private nts.uk.ctx.at.shared.dom.WorkInformation.Require requireWorkInfo;

	@Test
	public void testUpdateShiftMater_throw_Msg_1610() {
		ShiftMaster shiftMaster = ShiftMasterInstanceHelper.getShiftMaterEmpty();
		WorkInformation workInformation = new WorkInformation(null, "workTypeCode1");
		ShiftMasterDisInfor displayInfor = shiftMaster.getDisplayInfor();
		new Expectations() {
			{
				require.getByShiftMaterCd(anyString);
				result = Optional.of(ShiftMasterInstanceHelper.getShiftMaterEmpty());

				requireWorkInfo.findByPK(anyString);
				result = Optional.of(new WorkType());
				
				requireWorkInfo.checkNeededOfWorkTimeSetting(anyString);
				result = SetupType.OPTIONAL;
				
				require.getByWorkTypeAndWorkTime(anyString, anyString);
				result = Optional.of(ShiftMasterInstanceHelper.getShiftMaterEmpty());
			}
		};

		NtsAssert.businessException("Msg_1610", () -> {
			AtomTask persist = UpdateShiftMasterService.updateShiftMater(requireWorkInfo, require, shiftMaster.getWorkTypeCode().v(),
					displayInfor, workInformation);
			persist.run();
		});
	}

	@Test
	public void testUpdateShiftMater_throw_Msg_1608() {
		ShiftMaster shiftMaster = ShiftMasterInstanceHelper.getShiftMaterEmpty();
		ShiftMasterDisInfor displayInfor = shiftMaster.getDisplayInfor();
		WorkInformation workInformation = new WorkInformation("workTimeCode", "workTypeCode");

		new Expectations() {
			{
				require.getByShiftMaterCd(anyString);
				result = Optional.of(ShiftMasterInstanceHelper.getShiftMaterEmpty());

				requireWorkInfo.findByPK(shiftMaster.getWorkTypeCode().v());
				result = Optional.empty();
			}
		};

		NtsAssert.businessException("Msg_1608", () -> {
			AtomTask persist = UpdateShiftMasterService.updateShiftMater(requireWorkInfo, require, shiftMaster.getShiftMasterCode().v(),
					displayInfor, workInformation);
			persist.run();
		});
	}

	@Test
	public void testUpdateShiftMater_throw_Msg_1609() {
		ShiftMaster shiftMaster = ShiftMasterInstanceHelper.getShiftMaterEmpty();
		ShiftMasterDisInfor displayInfor = shiftMaster.getDisplayInfor();
		WorkInformation workInformation = new WorkInformation("workTimeCode", "workTypeCode");

		new Expectations() {
			{
				require.getByShiftMaterCd(shiftMaster.getShiftMasterCode().v());
				result = Optional.of(ShiftMasterInstanceHelper.getShiftMaterEmpty());
				
				requireWorkInfo.findByPK(shiftMaster.getWorkTypeCode().v());
				result = Optional.of(new WorkType());
				
				requireWorkInfo.checkNeededOfWorkTimeSetting(shiftMaster.getWorkTypeCode().v());
				result = SetupType.REQUIRED;
			}
		};

		NtsAssert.businessException("Msg_1609", () -> {
			AtomTask persist = UpdateShiftMasterService.updateShiftMater(requireWorkInfo, require, shiftMaster.getShiftMasterCode().v(),
					displayInfor, workInformation);
			persist.run();
		});
	}

	@Test
	public void testUpdateShiftMater_throw_Msg_435() {
		ShiftMaster shiftMaster = ShiftMasterInstanceHelper.getShiftMaterEmpty();
		ShiftMasterDisInfor displayInfor = shiftMaster.getDisplayInfor();
		WorkInformation workInformation = new WorkInformation(null, "workTypeCode");

		new Expectations() {
			{
				require.getByShiftMaterCd(shiftMaster.getShiftMasterCode().v());
				result = Optional.of(ShiftMasterInstanceHelper.getShiftMaterEmpty());
				
				requireWorkInfo.findByPK(shiftMaster.getWorkTypeCode().v());
				result = Optional.of(new WorkType());

				requireWorkInfo.checkNeededOfWorkTimeSetting(shiftMaster.getWorkTypeCode().v());
				result = SetupType.REQUIRED;
			}
		};

		NtsAssert.businessException("Msg_435", () -> {
			AtomTask persist = UpdateShiftMasterService.updateShiftMater(requireWorkInfo, require, shiftMaster.getShiftMasterCode().v(),
					displayInfor, workInformation);
			persist.run();
		});
	}

	@Test
	public void testUpdateShiftMater_throw_Msg_434() {
		ShiftMaster shiftMaster = ShiftMasterInstanceHelper.getShiftMaterEmpty();
		ShiftMasterDisInfor displayInfor = shiftMaster.getDisplayInfor();
		WorkInformation workInformation = new WorkInformation("workTimeCode", "workTypeCode");

		new Expectations() {
			{
				require.getByShiftMaterCd(shiftMaster.getShiftMasterCode().v());
				result = Optional.of(ShiftMasterInstanceHelper.getShiftMaterEmpty());
				
				requireWorkInfo.findByPK(shiftMaster.getWorkTypeCode().v());
				result = Optional.of(new WorkType());
				
				requireWorkInfo.checkNeededOfWorkTimeSetting(shiftMaster.getWorkTypeCode().v());
				result = SetupType.NOT_REQUIRED;

			}
		};

		NtsAssert.businessException("Msg_434", () -> {
			AtomTask persist = UpdateShiftMasterService.updateShiftMater(requireWorkInfo, require, shiftMaster.getShiftMasterCode().v(),
					displayInfor, workInformation);
			persist.run();
		});
	}

	@Test
	public void testUpdateShiftMater() {
		ShiftMaster shiftMaster = ShiftMasterInstanceHelper.getShiftMaterEmpty();
		ShiftMasterDisInfor displayInfor = shiftMaster.getDisplayInfor();
		WorkInformation workInformation = new WorkInformation(null, "workTypeCode");

		new Expectations() {
			{
				require.getByShiftMaterCd(anyString);
				result = Optional.of(ShiftMasterInstanceHelper.getShiftMaterEmpty());
				
				requireWorkInfo.findByPK(anyString);
				result = Optional.of(new WorkType());
				
				requireWorkInfo.checkNeededOfWorkTimeSetting(anyString);
				result = SetupType.OPTIONAL;
				
				require.getByWorkTypeAndWorkTime(anyString, anyString);
				result = Optional.of(ShiftMasterInstanceHelper.getShiftMaterEmpty());

				require.getByWorkTypeAndWorkTime(workInformation.getWorkTypeCode().v(),
						null);
			}
		};

		NtsAssert.atomTask(() -> UpdateShiftMasterService.updateShiftMater(requireWorkInfo, require, shiftMaster.getShiftMasterCode().v(),
				displayInfor, workInformation), any -> require.update(shiftMaster));
	}
}
