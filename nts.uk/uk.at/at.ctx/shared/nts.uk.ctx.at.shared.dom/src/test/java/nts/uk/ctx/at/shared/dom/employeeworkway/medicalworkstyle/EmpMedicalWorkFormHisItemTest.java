package nts.uk.ctx.at.shared.dom.employeeworkway.medicalworkstyle;

import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 社員の医療勤務形態履歴項目のUTコード
 * @author HieuLt
 *
 */
public class EmpMedicalWorkFormHisItemTest {
	
	@Test
	public void  getter(){
		val target = new EmpMedicalWorkFormHisItem(
				"empID", 
				"historyID",
				new NurseClassifiCode("NurseClassifiCode"),
				NotUseAtr.USE,
				MedicalCareWorkStyle.FULLTIME,
				NotUseAtr.USE);
		NtsAssert.invokeGetters(target);
	}
}
