package nts.uk.ctx.at.shared.dom.worktime.workplace.service;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.workplace.WorkTimeWorkplace;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import static org.assertj.core.api.Assertions.assertThat;

@RunWith(JMockit.class)
public class WorkTimeWorkplaceServiceTest {

	@Injectable
	WorkTimeWorkplaceService.Require require;

	@Test
	public void testGetByCid() {

		WorkTimeWorkplace workTimeWorkplace = new WorkTimeWorkplace(
			"CID","WkpId",Arrays.asList(new WorkTimeCode("workTimeCd1"),new WorkTimeCode("workTimeCode2")));
		// Mock up
		new Expectations() {{
			require.getByCId();
			result = Arrays.asList(workTimeWorkplace);
		}};

		WorkTimeWorkplaceService service = new WorkTimeWorkplaceService();
		List<String> data = service.getByCid(require);
		assertThat(data.size()).isEqualTo(1);
		assertThat(data.get(0)).isEqualTo("WkpId");
	}

	@Test
	public void testGetByCid_case2() {
		// Mock up
		new Expectations() {{
			require.getByCId();
			result = Collections.emptyList();
		}};

		WorkTimeWorkplaceService service = new WorkTimeWorkplaceService();
		List<String> data = service.getByCid(require);
		assertThat(data.size()).isEqualTo(0);
	}
}
