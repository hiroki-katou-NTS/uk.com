package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.workrule.shiftmaster.ShiftMaster.Require;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

@RunWith(JMockit.class)
public class ShiftMasterTest {
	@Injectable
	Require require;
	
	@Test
	public void getters() {
		String shiftMasterCode = "shiftMasterCode";
		String workTypeCode = "workTypeCode";
		String workTimeCode = "workTimeCode";
		ShiftMasterDisInfor displayInfor =  new ShiftMasterDisInfor(new ShiftMasterName("name"),new ColorCodeChar6("color"),new ColorCodeChar6("color"), null);
		ShiftMaster shiftMater = new ShiftMaster("companyId",new ShiftMasterCode(shiftMasterCode), displayInfor, workTypeCode,workTimeCode, new ShiftMasterImportCode(shiftMasterCode));
		NtsAssert.invokeGetters(shiftMater);
	}

	@Test
	public void testCheckError_throw_Msg_1608() {
		String shiftMasterCode = "shiftMasterCode";
		String workTypeCode = "workTypeCode";
		String workTimeCode = "workTimeCode";
		ShiftMasterDisInfor displayInfor =  new ShiftMasterDisInfor(new ShiftMasterName("name"),new ColorCodeChar6("color"),new ColorCodeChar6("color"), null);
		ShiftMaster shiftMater = new ShiftMaster("companyId",new ShiftMasterCode(shiftMasterCode), displayInfor, workTypeCode, workTimeCode, new ShiftMasterImportCode(shiftMasterCode));
		new Expectations() {
			{
				require.getWorkType(shiftMater.getWorkTypeCode().v());
			}
		};
		NtsAssert.businessException("Msg_1608", () -> shiftMater.checkError(require));
	}
	
	@Test
	public void testCheckError_throw_Msg_1609() {
		String shiftMasterCode = "shiftMasterCode";
		String workTypeCode = "workTypeCode";
		String workTimeCode = "workTimeCode";
		ShiftMasterDisInfor displayInfor =  new ShiftMasterDisInfor(new ShiftMasterName("name"),new ColorCodeChar6("color"),new ColorCodeChar6("color"), null);
		ShiftMaster shiftMater = new ShiftMaster("companyId",new ShiftMasterCode(shiftMasterCode), displayInfor, workTypeCode,workTimeCode, new ShiftMasterImportCode(shiftMasterCode));
		new Expectations() {
			{
				require.getWorkType(shiftMater.getWorkTypeCode().v());
				result = Optional.of(new WorkType());
				
				require.checkNeededOfWorkTimeSetting(shiftMater.getWorkTypeCode().v());
				result = SetupType.REQUIRED;
				
				require.getWorkTime(workTimeCode);
				
			}
		};
		
		NtsAssert.businessException("Msg_1609", () -> shiftMater.checkError(require));
	}
	
	@Test
	public void testCheckError_throw_Msg_435() {
		String shiftMasterCode = "shiftMasterCode";
		String workTypeCode = "workTypeCode";
		String workTimeCode = "workTimeCode";
		ShiftMasterDisInfor displayInfor =  new ShiftMasterDisInfor(new ShiftMasterName("name"),new ColorCodeChar6("color"),new ColorCodeChar6("color"), null);
		ShiftMaster shiftMater = new ShiftMaster("companyId",new ShiftMasterCode(shiftMasterCode), displayInfor, workTypeCode, workTimeCode, new ShiftMasterImportCode(shiftMasterCode));
		shiftMater.removeWorkTimeInHolydayWorkType();
		new Expectations() {
			{
				require.getWorkType(shiftMater.getWorkTypeCode().v());
				result = Optional.of(new WorkType());
				
				require.checkNeededOfWorkTimeSetting(shiftMater.getWorkTypeCode().v());
				result = SetupType.REQUIRED;
			}
		};
		NtsAssert.businessException("Msg_435", () -> shiftMater.checkError(require));
	}
	
	@Test
	public void testCheckError_throw_Msg_434() {
		String shiftMasterCode = "shiftMasterCode";
		String workTypeCode = "workTypeCode";
		String workTimeCode = "workTimeCode";
		ShiftMasterDisInfor displayInfor =  new ShiftMasterDisInfor(new ShiftMasterName("name"),new ColorCodeChar6("color"),new ColorCodeChar6("color"), null);
		ShiftMaster shiftMater = new ShiftMaster("companyId",new ShiftMasterCode(shiftMasterCode), displayInfor, workTypeCode, workTimeCode, new ShiftMasterImportCode(shiftMasterCode));
		new Expectations() {
			{
				require.getWorkType(shiftMater.getWorkTypeCode().v());
				result = Optional.of(new WorkType());
				
				require.checkNeededOfWorkTimeSetting(shiftMater.getWorkTypeCode().v());
				result = SetupType.NOT_REQUIRED;
			}
		};

		NtsAssert.businessException("Msg_434", () -> shiftMater.checkError(require));
	}

	@Test
	public void testCreateAndChange() {
		/** */
		//勤務情報
		val workInfoBefore = new WorkInformation("WorkType01", "WorkTime01");
		val workInfoAfter = new WorkInformation("WorkType02", "WorkTime02");
		
		//表示情報
		val displayInforBefore = Helper.createDisplayInfo("Name_01", "000", "000", "Note_01");
		val displayInforAfter = Helper.createDisplayInfo("Name_02", "fff", "fff", "Note_02");
		
		//取込コード
		val impCdBefore = new ShiftMasterImportCode("ImportBefore");
		val impCdAfter = new ShiftMasterImportCode("ImportAfter");
		
		/** 作成  */
		val shiftMater = new ShiftMaster("cid"
				,	new ShiftMasterCode("shiftMaster01")
				,	displayInforBefore
				,	workInfoBefore.getWorkTypeCode().v()
				,	workInfoBefore.getWorkTimeCode().v()
				,	impCdBefore);
		//表示情報
		assertThat(shiftMater.getDisplayInfor().getName()).isEqualTo(displayInforBefore.getName());
		assertThat(shiftMater.getDisplayInfor().getColor()).isEqualTo(displayInforBefore.getColor());
		assertThat(shiftMater.getDisplayInfor().getColorSmartPhone()).isEqualTo(displayInforBefore.getColorSmartPhone());
		assertThat(shiftMater.getDisplayInfor().getRemarks()).isEqualTo(displayInforBefore.getRemarks());
		
		//勤務情報
		assertThat(shiftMater.getWorkTypeCode()).isEqualTo(workInfoBefore.getWorkTypeCode());
		assertThat(shiftMater.getWorkTimeCode()).isEqualTo(workInfoBefore.getWorkTimeCode());
		
		//取込コード
		assertThat(shiftMater.getImportCode()).isEqualTo(impCdBefore);
		
		
		/** 変更  */
		shiftMater.change(displayInforAfter, impCdAfter, workInfoAfter);
		
		//表示情報
		assertThat(shiftMater.getDisplayInfor().getName()).isEqualTo(displayInforAfter.getName());
		assertThat(shiftMater.getDisplayInfor().getColor()).isEqualTo(displayInforAfter.getColor());
		assertThat(shiftMater.getDisplayInfor().getColorSmartPhone()).isEqualTo(displayInforAfter.getColorSmartPhone());
		assertThat(shiftMater.getDisplayInfor().getRemarks()).isEqualTo(displayInforAfter.getRemarks());
		
		//勤務情報
		assertThat(shiftMater.getWorkTypeCode()).isEqualTo(workInfoAfter.getWorkTypeCode());
		assertThat(shiftMater.getWorkTimeCode()).isEqualTo(workInfoAfter.getWorkTimeCode());
		
		//取込コード
		assertThat(shiftMater.getImportCode()).isEqualTo(impCdAfter);

	}
	
	public static class Helper{
		public static ShiftMasterDisInfor createDisplayInfo(String shiftMasterName, String colorPC, String colorSP, String remark) {
			return new ShiftMasterDisInfor(new ShiftMasterName("shiftMasterName"),new ColorCodeChar6("colorPC"), new ColorCodeChar6("colorSP"), new Remarks(remark));
		}
		
	}

}
