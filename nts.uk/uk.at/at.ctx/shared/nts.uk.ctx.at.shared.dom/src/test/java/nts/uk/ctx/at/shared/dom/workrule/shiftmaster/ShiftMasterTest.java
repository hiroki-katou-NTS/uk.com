package nts.uk.ctx.at.shared.dom.workrule.shiftmaster;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.WorkInformation.Require;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.SetupType;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

@RunWith(JMockit.class)
public class ShiftMasterTest {
	@Injectable
	Require requireWorkInfo;
	
	@Test
	public void getters() {
		String shiftMasterCode = "shiftMasterCode";
		String workTypeCode = "workTypeCode";
		String workTimeCode = "workTimeCode";
		ShiftMasterDisInfor displayInfor =  new ShiftMasterDisInfor(new ShiftMasterName("name"),new ColorCodeChar6("color"), null);
		ShiftMaster shiftMater = new ShiftMaster("companyId",new ShiftMasterCode(shiftMasterCode), displayInfor, workTypeCode,workTimeCode);
		NtsAssert.invokeGetters(shiftMater);
	}

	@Test
	public void testCheckError_throw_Msg_1608() {
		String shiftMasterCode = "shiftMasterCode";
		String workTypeCode = "workTypeCode";
		String workTimeCode = "workTimeCode";
		ShiftMasterDisInfor displayInfor =  new ShiftMasterDisInfor(new ShiftMasterName("name"),new ColorCodeChar6("color"), null);
		ShiftMaster shiftMater = new ShiftMaster("companyId",new ShiftMasterCode(shiftMasterCode), displayInfor, workTypeCode,workTimeCode);
		new Expectations() {
			{
				requireWorkInfo.getWorkType(shiftMater.getWorkTypeCode().v());
			}
		};
		NtsAssert.businessException("Msg_1608", () -> shiftMater.checkError(requireWorkInfo));
	}
	
	@Test
	public void testCheckError_throw_Msg_1609() {
		String shiftMasterCode = "shiftMasterCode";
		String workTypeCode = "workTypeCode";
		String workTimeCode = "workTimeCode";
		ShiftMasterDisInfor displayInfor =  new ShiftMasterDisInfor(new ShiftMasterName("name"),new ColorCodeChar6("color"), null);
		ShiftMaster shiftMater = new ShiftMaster("companyId",new ShiftMasterCode(shiftMasterCode), displayInfor, workTypeCode,workTimeCode);
		new Expectations() {
			{
				requireWorkInfo.getWorkType(shiftMater.getWorkTypeCode().v());
				result = Optional.of(new WorkType());
				
				requireWorkInfo.checkNeededOfWorkTimeSetting(shiftMater.getWorkTypeCode().v());
				result = SetupType.REQUIRED;
				
				requireWorkInfo.getWorkTime(workTimeCode);
				
			}
		};
		
		NtsAssert.businessException("Msg_1609", () -> shiftMater.checkError(requireWorkInfo));
	}
	
	@Test
	public void testCheckError_throw_Msg_435() {
		String shiftMasterCode = "shiftMasterCode";
		String workTypeCode = "workTypeCode";
		String workTimeCode = "workTimeCode";
		ShiftMasterDisInfor displayInfor =  new ShiftMasterDisInfor(new ShiftMasterName("name"),new ColorCodeChar6("color"), null);
		ShiftMaster shiftMater = new ShiftMaster("companyId",new ShiftMasterCode(shiftMasterCode), displayInfor, workTypeCode,workTimeCode);
		shiftMater.removeWorkTimeInHolydayWorkType();
		new Expectations() {
			{
				requireWorkInfo.getWorkType(shiftMater.getWorkTypeCode().v());
				result = Optional.of(new WorkType());
				
				requireWorkInfo.checkNeededOfWorkTimeSetting(shiftMater.getWorkTypeCode().v());
				result = SetupType.REQUIRED;
			}
		};
		NtsAssert.businessException("Msg_435", () -> shiftMater.checkError(requireWorkInfo));
	}
	
	@Test
	public void testCheckError_throw_Msg_434() {
		String shiftMasterCode = "shiftMasterCode";
		String workTypeCode = "workTypeCode";
		String workTimeCode = "workTimeCode";
		ShiftMasterDisInfor displayInfor =  new ShiftMasterDisInfor(new ShiftMasterName("name"),new ColorCodeChar6("color"), null);
		ShiftMaster shiftMater = new ShiftMaster("companyId",new ShiftMasterCode(shiftMasterCode), displayInfor, workTypeCode,workTimeCode);
		new Expectations() {
			{
				requireWorkInfo.getWorkType(shiftMater.getWorkTypeCode().v());
				result = Optional.of(new WorkType());
				
				requireWorkInfo.checkNeededOfWorkTimeSetting(shiftMater.getWorkTypeCode().v());
				result = SetupType.NOT_REQUIRED;
			}
		};

		NtsAssert.businessException("Msg_434", () -> shiftMater.checkError(requireWorkInfo));
	}

	@Test
	public void testChange() {
		String shiftMasterCode = "shiftMasterCode";
		String workTypeCode = "workTypeCode";
		String workTimeCode = "workTimeCode";
		WorkInformation workInfor =new WorkInformation("workTypeCode123", "workTimeCode123");
		ShiftMasterDisInfor displayInfor = new ShiftMasterDisInfor(
				new ShiftMasterName("name1"),//dummy
				new ColorCodeChar6("color1"),//dummy 
				new Remarks("Remarks1231232132"));//dummy
		ShiftMaster shiftMater = new ShiftMaster("companyId",new ShiftMasterCode(shiftMasterCode), displayInfor, workTypeCode,workTimeCode);
		shiftMater.change(displayInfor, workInfor);
		assertThat(shiftMater.getDisplayInfor().getColor()).isEqualTo(displayInfor.getColor());
		assertThat(shiftMater.getDisplayInfor().getName()).isEqualTo(displayInfor.getName());
		assertThat(shiftMater.getDisplayInfor().getRemarks()).isEqualTo(displayInfor.getRemarks());
		assertThat(shiftMater.getWorkTypeCode()).isEqualTo(workInfor.getWorkTypeCode());
		assertThat(shiftMater.getWorkTimeCode()).isEqualTo(workInfor.getWorkTimeCode());

	}

}
