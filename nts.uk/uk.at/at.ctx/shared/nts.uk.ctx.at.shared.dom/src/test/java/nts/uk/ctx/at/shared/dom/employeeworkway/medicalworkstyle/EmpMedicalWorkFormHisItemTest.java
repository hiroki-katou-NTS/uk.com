package nts.uk.ctx.at.shared.dom.employeeworkway.medicalworkstyle;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalworkstyle.EmpMedicalWorkFormHisItem;

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
