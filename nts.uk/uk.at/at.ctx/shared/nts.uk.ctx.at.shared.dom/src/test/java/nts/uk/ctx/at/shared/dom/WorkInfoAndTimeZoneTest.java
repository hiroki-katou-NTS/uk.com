package nts.uk.ctx.at.shared.dom;

import static org.assertj.core.api.Assertions.*;

import java.util.ArrayList;
import java.util.List;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.integration.junit4.JMockit;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.worktime.common.TimeZone;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.shr.com.time.TimeWithDayAttr;

@RunWith(JMockit.class)
public class WorkInfoAndTimeZoneTest {

	@Test
	public void getters() {
		WorkType workType = new WorkType();
		WorkInfoAndTimeZone workInfoAndTimeZone = WorkInfoAndTimeZone.createWithoutWorkTime(workType);
		NtsAssert.invokeGetters(workInfoAndTimeZone);
	}

	// if workTime != null;
	@Test
	public void testWorkInfoAndTimeZoneWorkType() {
		List<TimeZone> listTimeZone = new ArrayList<>();
		WorkType workType = new WorkType();
		WorkTimeSetting workTime = new WorkTimeSetting();
		listTimeZone.add( new TimeZone( new TimeWithDayAttr(1),new TimeWithDayAttr(2)));
		listTimeZone.add( new TimeZone( new TimeWithDayAttr(3),new TimeWithDayAttr(4)));
		listTimeZone.add( new TimeZone( new TimeWithDayAttr(2),new TimeWithDayAttr(5)));
		WorkInfoAndTimeZone workInfoAndTimeZone = WorkInfoAndTimeZone.create(workType, workTime, listTimeZone);
		assertThat(workInfoAndTimeZone.getWorkType()).isEqualTo(workType);
		assertThat(workInfoAndTimeZone.getWorkTime().get()).isEqualTo(workTime);
		assertThat(workInfoAndTimeZone.getTimeZones()).isEqualTo(listTimeZone);

	}

	// if param is workType
	@Test
	public void testGetWorkType() {
		WorkType workType = new WorkType();

		WorkInfoAndTimeZone workInfoAndTimeZone = WorkInfoAndTimeZone.createWithoutWorkTime(workType);

		assertThat(workInfoAndTimeZone.getWorkType()).isEqualTo(workType);
		assertThat(workInfoAndTimeZone.getWorkTime().isPresent()).isFalse();
		assertThat(workInfoAndTimeZone.getTimeZones().isEmpty()).isTrue();
	}

}
