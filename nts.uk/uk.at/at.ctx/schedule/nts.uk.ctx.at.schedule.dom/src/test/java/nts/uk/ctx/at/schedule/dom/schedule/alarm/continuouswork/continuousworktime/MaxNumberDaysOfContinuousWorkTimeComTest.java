package nts.uk.ctx.at.schedule.dom.schedule.alarm.continuouswork.continuousworktime;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;

@RunWith(JMockit.class)
public class MaxNumberDaysOfContinuousWorkTimeComTest {
	@Test
	public void getters() {
				
		MaxNumberDaysOfContinuousWorkTimeCom maxNoDaysOfContinuousAttOrg = MaxNumberDaysOfContinuousWorkTimeHelper.DUMMY;

		NtsAssert.invokeGetters(maxNoDaysOfContinuousAttOrg);

	}

	@Test
	public void create_maxNumberDaysOfContinuousWorkTimeCom_success() {
				
		MaxNumberDaysOfContinuousWorkTimeCom maxNoDaysOfContinuousAttOrg = MaxNumberDaysOfContinuousWorkTimeHelper.DUMMY;
		
		assertThat(maxNoDaysOfContinuousAttOrg).extracting(
				com -> com.getCompanyId(),
				com -> com.getCode().v(),
				com -> com.getName().v(),
				com -> com.getMaxNumberDaysOfContinuousWorktime().getNumberOfDays().v()
				).containsExactly(
						"000000000000-0315",
						"003",
						"通常勤務１",
						5);
		List<String> worktimeCdLst = maxNoDaysOfContinuousAttOrg.getMaxNumberDaysOfContinuousWorktime()
				.getWorktimeCodeLst().stream().map(c -> c.toString()).collect(Collectors.toList());
		assertThat(worktimeCdLst.equals(Arrays.asList("001", "002", "003")));
	
	}
}
