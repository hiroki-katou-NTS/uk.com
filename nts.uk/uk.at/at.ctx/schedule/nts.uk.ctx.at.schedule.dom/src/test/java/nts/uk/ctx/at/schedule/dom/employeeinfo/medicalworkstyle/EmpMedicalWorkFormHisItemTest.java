package nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle;

import java.util.Optional;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

/**
 *
 * @author HieuLt
 *
 */
public class EmpMedicalWorkFormHisItemTest {
	
	

	public static EmpMedicalWorkFormHisItem createData() {

		EmpMedicalWorkFormHisItem result = new EmpMedicalWorkFormHisItem(
				"empID", 
				"historyID", true,
				Optional.ofNullable(new MedicalWorkFormInfor(MedicalCareWorkStyle.FULLTIME,
						new NurseClassifiCode("NurseClassifiCode"), true)),
				Optional.ofNullable(new NursingWorkFormInfor(
						MedicalCareWorkStyle.FULLTIME,
						true,
						new FulltimeRemarks("FulltimeRemarks"),
						new NightShiftRemarks("NightShiftRemarks"))));

		return result;
	}
	@Test
	public void insertSuccess (){
		EmpMedicalWorkFormHisItem target = createData();
		NtsAssert.invokeGetters(target);
	}
}
