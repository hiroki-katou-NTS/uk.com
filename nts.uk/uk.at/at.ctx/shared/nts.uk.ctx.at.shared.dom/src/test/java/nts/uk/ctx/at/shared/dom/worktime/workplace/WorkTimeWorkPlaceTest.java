package nts.uk.ctx.at.shared.dom.worktime.workplace;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.List;

import static org.assertj.core.api.AssertionsForClassTypes.assertThat;

@RunWith(JMockit.class)
public class WorkTimeWorkPlaceTest {

	private List<WorkTimeCode> workTimeCodes = Arrays.asList(new WorkTimeCode("01"),new WorkTimeCode("02"));

	@Test
	public void testGetter() {
		WorkTimeWorkplace data =
			new WorkTimeWorkplace("cid","wkpId", workTimeCodes);
		NtsAssert.invokeGetters(data);
	}

	@Test
	public void testCreate() {
		WorkTimeWorkplace actual = WorkTimeWorkplace.create("cid","wkpId", workTimeCodes);
		assertThat(actual.getCompanyID()).isEqualTo("cid");
		assertThat(actual.getWorkplaceID()).isEqualTo("wkpId");
		assertThat(actual.getWorkTimeCodes().size()).isEqualTo(2);
	}
}
