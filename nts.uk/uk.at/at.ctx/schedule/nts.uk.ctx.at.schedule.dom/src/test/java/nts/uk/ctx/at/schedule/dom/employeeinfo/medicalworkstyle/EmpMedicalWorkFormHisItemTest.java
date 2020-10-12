package nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;

/**
 *
 * @author HieuLt
 *
 */
public class EmpMedicalWorkFormHisItemTest {
	
	@Test
	public void  getter(){
		EmpMedicalWorkFormHisItem target = EmpMedicalWorkHelper.createData();
		NtsAssert.invokeGetters(target);
	}
}
