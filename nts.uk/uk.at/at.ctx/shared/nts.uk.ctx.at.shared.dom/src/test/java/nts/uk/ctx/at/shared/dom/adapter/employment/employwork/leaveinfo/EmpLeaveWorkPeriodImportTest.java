package nts.uk.ctx.at.shared.dom.adapter.employment.employwork.leaveinfo;

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
public class EmpLeaveWorkPeriodImportTest {

	@Test
	public void getter(){
		EmpLeaveWorkPeriodImport target = EmpLeaveWorkPeriodImportHelper.getData();
		NtsAssert.invokeGetters(target);
	}
}
