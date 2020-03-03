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
		ShiftMaster shiftMater = ShiftMasterInstanceHelper.getShiftMaterEmpty();
		NtsAssert.invokeGetters(shiftMater);
	}

	@Test
	public void testCheckError_throw_Msg_1608() {
		ShiftMaster shiftMater = ShiftMasterInstanceHelper.getShiftMaterEmpty();
		new Expectations() {
			{
				requireWorkInfo.findByPK(shiftMater.getWorkTypeCode().v());
				result = Optional.empty();
			}
		};
		NtsAssert.businessException("Msg_1608", () -> shiftMater.checkError(requireWorkInfo));
	}
	
	@Test
	public void testCheckError_throw_Msg_1609() {
		ShiftMaster shiftMater = ShiftMasterInstanceHelper.getShiftMaterEmpty();
		new Expectations() {
			{
				requireWorkInfo.findByPK(shiftMater.getWorkTypeCode().v());
				result = Optional.of(new WorkType());
				
				requireWorkInfo.checkNeededOfWorkTimeSetting(shiftMater.getWorkTypeCode().v());
				result = SetupType.REQUIRED;
				
			}
		};
		
		NtsAssert.businessException("Msg_1609", () -> shiftMater.checkError(requireWorkInfo));
	}
	
	@Test
	public void testCheckError_throw_Msg_435() {
		ShiftMaster shiftMater = ShiftMasterInstanceHelper.getShiftMaterEmpty();
		shiftMater.setWorkTimeCode(null);
		new Expectations() {
			{
				requireWorkInfo.findByPK(shiftMater.getWorkTypeCode().v());
				result = Optional.of(new WorkType());
				
				requireWorkInfo.checkNeededOfWorkTimeSetting(shiftMater.getWorkTypeCode().v());
				result = SetupType.REQUIRED;
			}
		};
		NtsAssert.businessException("Msg_435", () -> shiftMater.checkError(requireWorkInfo));
	}
	
	@Test
	public void testCheckError_throw_Msg_434() {
		ShiftMaster shiftMater = ShiftMasterInstanceHelper.getShiftMaterEmpty();
		new Expectations() {
			{
				requireWorkInfo.findByPK(shiftMater.getWorkTypeCode().v());
				result = Optional.of(new WorkType());
				
				requireWorkInfo.checkNeededOfWorkTimeSetting(shiftMater.getWorkTypeCode().v());
				result = SetupType.NOT_REQUIRED;
			}
		};

		NtsAssert.businessException("Msg_434", () -> shiftMater.checkError(requireWorkInfo));
	}

	@Test
	public void testChange() {
		ShiftMaster shiftMater = ShiftMasterInstanceHelper.getShiftMaterEmpty();
		WorkInformation workInfor =ShiftMasterInstanceHelper.getWorkInformationWorkTimeIsNull();
		ShiftMasterDisInfor displayInfor = new ShiftMasterDisInfor(
				new ShiftMasterName("name1"),//dummy
				new ColorCodeChar6("color1"),//dummy 
				null);//dummy
		shiftMater.change(displayInfor, workInfor);
		assertThat(shiftMater.getDisplayInfor()).isEqualTo(displayInfor);
		assertThat(shiftMater.getWorkTypeCode()).isEqualTo(workInfor.getWorkTypeCode());
		assertThat(shiftMater.getWorkTimeCode()).isEqualTo(workInfor.getWorkTimeCode());

	}

}
