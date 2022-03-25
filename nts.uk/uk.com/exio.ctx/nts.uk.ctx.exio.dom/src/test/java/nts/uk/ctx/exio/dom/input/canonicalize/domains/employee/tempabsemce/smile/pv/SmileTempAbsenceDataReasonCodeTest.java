package nts.uk.ctx.exio.dom.input.canonicalize.domains.employee.tempabsemce.smile.pv;

import static org.junit.Assert.*;

import org.junit.Test;

import lombok.val;

public class SmileTempAbsenceDataReasonCodeTest {

	@Test
	public void test() {
		val a = "456";
		val result = new SmileTempAbsenceDataReasonCode(a);
		val bb = result.equals(new SmileTempAbsenceDataReasonCode("000456"));
		
		result.toString();
	}

}
