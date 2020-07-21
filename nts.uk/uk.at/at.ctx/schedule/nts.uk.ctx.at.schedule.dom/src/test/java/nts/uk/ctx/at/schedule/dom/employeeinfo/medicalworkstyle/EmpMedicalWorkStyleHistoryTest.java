package nts.uk.ctx.at.schedule.dom.employeeinfo.medicalworkstyle;

import static org.junit.Assert.assertNotNull;

import java.util.Collections;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;

/**
 * 
 * @author HieuLt
 *
 */
@RunWith(JMockit.class)
public class EmpMedicalWorkStyleHistoryTest {

	@Test
	public void getter(){
		EmpMedicalWorkStyleHistory result = new EmpMedicalWorkStyleHistory("empID-0001", // dummy
				EmpMedicalWorkHelper.getListDateHis());
		NtsAssert.invokeGetters(result);
	}
	
	@Test
	public void test_Buss(){
		NtsAssert.systemError(() -> {
			EmpMedicalWorkStyleHistory
			.get("11", // dummy 
				Collections.emptyList());
			});}
	
	@Test
	public void checkListDateHisItem(){
		EmpMedicalWorkStyleHistory history = EmpMedicalWorkStyleHistory.get("EMPLOYEEID01", // dummy 
				EmpMedicalWorkHelper.getListDateHis());
		assertNotNull(history);
	}
	
}
