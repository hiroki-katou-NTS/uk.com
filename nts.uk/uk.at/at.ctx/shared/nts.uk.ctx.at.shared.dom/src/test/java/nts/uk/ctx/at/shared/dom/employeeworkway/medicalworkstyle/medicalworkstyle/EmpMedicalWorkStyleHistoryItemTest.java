package nts.uk.ctx.at.shared.dom.employeeworkway.medicalworkstyle.medicalworkstyle;

import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalworkstyle.MedicalCareWorkStyle;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalworkstyle.NurseClassifiCode;
import nts.uk.ctx.at.shared.dom.employeeworkway.medicalworkstyle.medicalworkstyle.EmpMedicalWorkStyleHistoryItem;

/**
 * 社員の医療勤務形態履歴項目のUTコード
 * @author HieuLt
 *
 */
public class EmpMedicalWorkStyleHistoryItemTest {
	
	@Test
	public void  getter(){
		val target = new EmpMedicalWorkStyleHistoryItem(
				"empID", 
				"historyID",
				new NurseClassifiCode("NurseClassifiCode"),
				true,
				MedicalCareWorkStyle.FULLTIME,
				true);
		NtsAssert.invokeGetters(target);
	}
}
