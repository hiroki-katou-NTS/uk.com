package nts.uk.ctx.at.shared.dom.workrule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.Assert.*;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.SecondSituation;

public class ErrorStatusWorkInfoTest {

	@Test
	public void getters() {
		ErrorStatusWorkInfo errorStatusWorkInfo = ErrorStatusWorkInfo.valueOf(1);
		NtsAssert.invokeGetters(errorStatusWorkInfo);
	}
	
	@Test
	public void test() {
		ErrorStatusWorkInfo errorStatusWorkInfo = ErrorStatusWorkInfo.valueOf(1);
		assertThat(errorStatusWorkInfo).isEqualTo(ErrorStatusWorkInfo.NORMAL);
		errorStatusWorkInfo = ErrorStatusWorkInfo.valueOf(2);
		assertThat(errorStatusWorkInfo).isEqualTo(ErrorStatusWorkInfo.WORKTIME_ARE_REQUIRE_NOT_SET);
		errorStatusWorkInfo = ErrorStatusWorkInfo.valueOf(3);
		assertThat(errorStatusWorkInfo).isEqualTo(ErrorStatusWorkInfo.WORKTIME_ARE_SET_WHEN_UNNECESSARY);
		errorStatusWorkInfo = ErrorStatusWorkInfo.valueOf(4);
		assertThat(errorStatusWorkInfo).isEqualTo(ErrorStatusWorkInfo.WORKTYPE_WAS_DELETE);
		errorStatusWorkInfo = ErrorStatusWorkInfo.valueOf(5);
		assertThat(errorStatusWorkInfo).isEqualTo(ErrorStatusWorkInfo.WORKTIME_WAS_DELETE);
		errorStatusWorkInfo = ErrorStatusWorkInfo.valueOf(6);
		assertThat(errorStatusWorkInfo).isEqualTo(ErrorStatusWorkInfo.WORKTYPE_WAS_ABOLISHED);
		errorStatusWorkInfo = ErrorStatusWorkInfo.valueOf(7);
		assertThat(errorStatusWorkInfo).isEqualTo(ErrorStatusWorkInfo.WORKTIME_HAS_BEEN_ABOLISHED);
		
	}
}
