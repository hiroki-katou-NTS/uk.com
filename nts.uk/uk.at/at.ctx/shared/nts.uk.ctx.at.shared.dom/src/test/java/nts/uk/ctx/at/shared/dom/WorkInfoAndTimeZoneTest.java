package nts.uk.ctx.at.shared.dom;

import static org.assertj.core.api.Assertions.assertThat;

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

	// if workTime == null;
	@Test
	public void testWorkInfoAndTimeZoneWorkType_1() {
		List<TimeZone> listTimeZone = new ArrayList<>();
		WorkType workType = new WorkType();
		listTimeZone.add(new TimeZone());
		WorkInfoAndTimeZone workInfoAndTimeZone = WorkInfoAndTimeZone.create(workType, // dummy
				null, listTimeZone);// dummy
		assertThat(workInfoAndTimeZone.getWorkType()).isEqualTo(workType);
		assertThat(workInfoAndTimeZone.getWorkTime().isPresent()).isFalse();
		assertThat(workInfoAndTimeZone.getListTimeZone().isEmpty()).isTrue();
	}

	// if workTime != null;
	@Test
	public void testWorkInfoAndTimeZoneWorkType_2() {
		List<TimeZone> listTimeZone = new ArrayList<>();
		WorkType workType = new WorkType();
		WorkTimeSetting workTime = new WorkTimeSetting();
		listTimeZone.add( new TimeZone( new TimeWithDayAttr(1),new TimeWithDayAttr(2)));
		listTimeZone.add( new TimeZone( new TimeWithDayAttr(3),new TimeWithDayAttr(4)));
		listTimeZone.add( new TimeZone( new TimeWithDayAttr(2),new TimeWithDayAttr(5)));
		WorkInfoAndTimeZone workInfoAndTimeZone = new WorkInfoAndTimeZone(workType, // dummy
				workTime, listTimeZone);// dummy
		assertThat(workInfoAndTimeZone.getWorkType()).isEqualTo(workType);
		assertThat(workInfoAndTimeZone.getWorkTime().get()).isEqualTo(workTime);
		assertThat(workInfoAndTimeZone.getListTimeZone()).isEqualTo(listTimeZone);
		
	}

	// if param is workType
	@Test
	public void testGetWorkType() {
		WorkType workType = new WorkType();

		WorkInfoAndTimeZone workInfoAndTimeZone = new WorkInfoAndTimeZone(workType);// dummy
		assertThat(workInfoAndTimeZone.getWorkType()).isEqualTo(workType);
		assertThat(workInfoAndTimeZone.getWorkTime().isPresent()).isFalse();
		assertThat(workInfoAndTimeZone.getListTimeZone().isEmpty()).isTrue();
	}

	@Test
	public void getters() {
		WorkType workType = new WorkType();
		WorkInfoAndTimeZone workInfoAndTimeZone = new WorkInfoAndTimeZone(workType);// dummy
		NtsAssert.invokeGetters(workInfoAndTimeZone);
	}

}
