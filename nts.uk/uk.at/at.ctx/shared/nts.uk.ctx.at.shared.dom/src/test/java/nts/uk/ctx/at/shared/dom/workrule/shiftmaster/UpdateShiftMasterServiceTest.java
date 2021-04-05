package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import java.util.Optional;

import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
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
	private String shiftMasterCode;
	private static String workTypeCode;
	private static WorkInformation workInformation;
	private static ShiftMasterDisInfor displayInfor;
	private static ShiftMasterImportCode importCode;
	
	@Before
	public void initData() {
		workTypeCode = "workTypeCode";
		shiftMasterCode = "shiftMasterCode";
		importCode = new ShiftMasterImportCode("importCode");
		workInformation =ShiftMasterInstanceHelper.getWorkInformationWorkTimeIsNull();
		displayInfor =  new ShiftMasterDisInfor(new ShiftMasterName("name"),new ColorCodeChar6("color"),new ColorCodeChar6("color"), null);
	}
	
	@Test
	public void testUpdateShiftMater_throw_Msg_1610() {
		new Expectations() {
			{
				require.getByShiftMaterCd(anyString);
				result = Optional.of(ShiftMasterInstanceHelper.getShiftMaterEmpty());

				requireWorkInfo.getWorkType(anyString);
				result = Optional.of(new WorkType());
				
				requireWorkInfo.checkNeededOfWorkTimeSetting(anyString);
				result = SetupType.OPTIONAL;
				
				require.getByWorkTypeAndWorkTime(anyString, anyString);
				result = Optional.of(ShiftMasterInstanceHelper.getShiftMaterEmpty());
			}
		};

		NtsAssert.businessException("Msg_1610"
				, () -> UpdateShiftMasterService.updateShiftMater(requireWorkInfo, require, workTypeCode, displayInfor, workInformation, importCode));
	}

	@Test
	public void testUpdateShiftMater_throw_Msg_1608() {
		workInformation = new WorkInformation("workTypeCode", "workTimeCode");
		
		new Expectations() {
			{
				require.getByShiftMaterCd(anyString);
				result = Optional.of(ShiftMasterInstanceHelper.getShiftMaterEmpty());

				requireWorkInfo.getWorkType(workTypeCode);
			}
		};

		NtsAssert.businessException("Msg_1608"
				, () -> UpdateShiftMasterService.updateShiftMater(requireWorkInfo, require, shiftMasterCode, displayInfor, workInformation, importCode));
	}

	@Test
	public void testUpdateShiftMater_throw_Msg_1609() {
		workInformation = new WorkInformation("workTypeCode", "workTimeCode");

		new Expectations() {
			{
				require.getByShiftMaterCd(shiftMasterCode);
				result = Optional.of(ShiftMasterInstanceHelper.getShiftMaterEmpty());
				
				requireWorkInfo.getWorkType(workTypeCode);
				result = Optional.of(new WorkType());
				
				requireWorkInfo.checkNeededOfWorkTimeSetting(workTypeCode);
				result = SetupType.REQUIRED;
				
				requireWorkInfo.getWorkTime(workInformation.getWorkTimeCode().v());
			}
		};

		NtsAssert.businessException("Msg_1609",
				() -> UpdateShiftMasterService.updateShiftMater(requireWorkInfo, require, shiftMasterCode, displayInfor, workInformation, importCode));
	}

	@Test
	public void testUpdateShiftMater_throw_Msg_435() {
		workInformation = new WorkInformation("workTypeCode", null);
		
		new Expectations() {
			{
				require.getByShiftMaterCd(shiftMasterCode);
				result = Optional.of(ShiftMasterInstanceHelper.getShiftMaterEmpty());
				
				requireWorkInfo.getWorkType(workTypeCode);
				result = Optional.of(new WorkType());

				requireWorkInfo.checkNeededOfWorkTimeSetting(workTypeCode);
				result = SetupType.REQUIRED;
			}
		};

		NtsAssert.businessException("Msg_435"
				, () ->  UpdateShiftMasterService.updateShiftMater(requireWorkInfo, require, shiftMasterCode, displayInfor, workInformation, importCode));
	}

	@Test
	public void testUpdateShiftMater_throw_Msg_434() {
		workInformation = new WorkInformation("workTypeCode", "workTimeCode");

		new Expectations() {
			{
				require.getByShiftMaterCd(shiftMasterCode);
				result = Optional.of(ShiftMasterInstanceHelper.getShiftMaterEmpty());
				
				requireWorkInfo.getWorkType(workTypeCode);
				result = Optional.of(new WorkType());
				
				requireWorkInfo.checkNeededOfWorkTimeSetting(workTypeCode);
				result = SetupType.NOT_REQUIRED;

			}
		};

		NtsAssert.businessException("Msg_434"
				, () -> UpdateShiftMasterService.updateShiftMater(requireWorkInfo, require, shiftMasterCode, displayInfor, workInformation, importCode));
	}
	
	@Test
	public void testUpdateShiftMater_throw_Msg_2163() {
		ShiftMaster shiftMaster = ShiftMasterInstanceHelper.createShiftMasterByImportCode(new ShiftMasterImportCode("old"));
		ShiftMasterImportCode newInportCode = new ShiftMasterImportCode("newImportCode");
		new Expectations() {
			{
				require.getByShiftMaterCd(shiftMasterCode);
				result = Optional.of(shiftMaster);
				
				require.checkDuplicateImportCode(newInportCode);
				result = true;

			}
		};

		NtsAssert.businessException("Msg_2163"
				, () -> UpdateShiftMasterService.updateShiftMater(requireWorkInfo, require, shiftMasterCode, displayInfor, workInformation, newInportCode));
	}

	@Test
	public void testUpdateShiftMater() {
		workInformation = new WorkInformation("workTypeCode", null);
		ShiftMaster shiftMaster = new ShiftMaster("cid"
				, new ShiftMasterCode(shiftMasterCode)
				, displayInfor
				, workTypeCode
				, null, importCode);
		
		new Expectations() {
			{
				require.getByShiftMaterCd(anyString);
				result = Optional.of(ShiftMasterInstanceHelper.getShiftMaterEmpty());
				
				requireWorkInfo.getWorkType(anyString);
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
				displayInfor, workInformation, importCode), any -> require.update(shiftMaster));
	}
}
