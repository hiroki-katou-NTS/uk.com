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
		String workTypeCode = "workTypeCode";
		ShiftMasterDisInfor displayInfor =  new ShiftMasterDisInfor(new ShiftMasterName("name"),new ColorCodeChar6("color"), null);
		WorkInformation workInformation =ShiftMasterInstanceHelper.getWorkInformationWorkTimeIsNull();
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
			AtomTask persist = UpdateShiftMasterService.updateShiftMater(requireWorkInfo, require, workTypeCode,
					displayInfor, workInformation);
			persist.run();
		});
	}

	@Test
	public void testUpdateShiftMater_throw_Msg_1608() {
		String shiftMasterCode = "shiftMasterCode";
		String workTypeCode = "workTypeCode";
		ShiftMasterDisInfor displayInfor =  new ShiftMasterDisInfor(new ShiftMasterName("name"),new ColorCodeChar6("color"), null);
		WorkInformation workInformation = new WorkInformation("workTypeCode", "workTimeCode");

		new Expectations() {
			{
				require.getByShiftMaterCd(anyString);
				result = Optional.of(ShiftMasterInstanceHelper.getShiftMaterEmpty());

				requireWorkInfo.findByPK(workTypeCode);
			}
		};

		NtsAssert.businessException("Msg_1608", () -> {
			AtomTask persist = UpdateShiftMasterService.updateShiftMater(requireWorkInfo, require, shiftMasterCode,
					displayInfor, workInformation);
			persist.run();
		});
	}

	@Test
	public void testUpdateShiftMater_throw_Msg_1609() {
		String shiftMasterCode = "shiftMasterCode";
		String workTypeCode = "workTypeCode";
		ShiftMasterDisInfor displayInfor =  new ShiftMasterDisInfor(new ShiftMasterName("name"),new ColorCodeChar6("color"), null);
		WorkInformation workInformation = new WorkInformation("workTypeCode", "workTimeCode");

		new Expectations() {
			{
				require.getByShiftMaterCd(shiftMasterCode);
				result = Optional.of(ShiftMasterInstanceHelper.getShiftMaterEmpty());
				
				requireWorkInfo.findByPK(workTypeCode);
				result = Optional.of(new WorkType());
				
				requireWorkInfo.checkNeededOfWorkTimeSetting(workTypeCode);
				result = SetupType.REQUIRED;
				
				requireWorkInfo.findByCode(workInformation.getWorkTimeCode().v());
			}
		};

		NtsAssert.businessException("Msg_1609", () -> {
			AtomTask persist = UpdateShiftMasterService.updateShiftMater(requireWorkInfo, require, shiftMasterCode,
					displayInfor, workInformation);
			persist.run();
		});
	}

	@Test
	public void testUpdateShiftMater_throw_Msg_435() {
		WorkInformation workInformation = new WorkInformation("workTypeCode", null);
		String shiftMasterCode = "shiftMasterCode";
		String workTypeCode = "workTypeCode";
		ShiftMasterDisInfor displayInfor =  new ShiftMasterDisInfor(new ShiftMasterName("name"),new ColorCodeChar6("color"), null);
		new Expectations() {
			{
				require.getByShiftMaterCd(shiftMasterCode);
				result = Optional.of(ShiftMasterInstanceHelper.getShiftMaterEmpty());
				
				requireWorkInfo.findByPK(workTypeCode);
				result = Optional.of(new WorkType());

				requireWorkInfo.checkNeededOfWorkTimeSetting(workTypeCode);
				result = SetupType.REQUIRED;
			}
		};

		NtsAssert.businessException("Msg_435", () -> {
			AtomTask persist = UpdateShiftMasterService.updateShiftMater(requireWorkInfo, require, shiftMasterCode,
					displayInfor, workInformation);
			persist.run();
		});
	}

	@Test
	public void testUpdateShiftMater_throw_Msg_434() {
		String shiftMasterCode = "shiftMasterCode";
		String workTypeCode = "workTypeCode";
		ShiftMasterDisInfor displayInfor =  new ShiftMasterDisInfor(new ShiftMasterName("name"),new ColorCodeChar6("color"), null);
		WorkInformation workInformation = new WorkInformation("workTypeCode", "workTimeCode");

		new Expectations() {
			{
				require.getByShiftMaterCd(shiftMasterCode);
				result = Optional.of(ShiftMasterInstanceHelper.getShiftMaterEmpty());
				
				requireWorkInfo.findByPK(workTypeCode);
				result = Optional.of(new WorkType());
				
				requireWorkInfo.checkNeededOfWorkTimeSetting(workTypeCode);
				result = SetupType.NOT_REQUIRED;

			}
		};

		NtsAssert.businessException("Msg_434", () -> {
			AtomTask persist = UpdateShiftMasterService.updateShiftMater(requireWorkInfo, require, shiftMasterCode,
					displayInfor, workInformation);
			persist.run();
		});
	}

	@Test
	public void testUpdateShiftMater() {
		String shiftMasterCode = "shiftMasterCode";
		String workTypeCode = "workTypeCode";
		ShiftMasterDisInfor displayInfor =  new ShiftMasterDisInfor(new ShiftMasterName("name"),new ColorCodeChar6("color"), null);
		WorkInformation workInformation = new WorkInformation("workTypeCode", null);
		ShiftMaster shiftMaster = new ShiftMaster("companyId",new ShiftMasterCode(shiftMasterCode), displayInfor, workTypeCode, null);
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

		NtsAssert.atomTask(() -> UpdateShiftMasterService.updateShiftMater(requireWorkInfo, require, shiftMasterCode,
				displayInfor, workInformation), any -> require.update(shiftMaster));
	}
}
