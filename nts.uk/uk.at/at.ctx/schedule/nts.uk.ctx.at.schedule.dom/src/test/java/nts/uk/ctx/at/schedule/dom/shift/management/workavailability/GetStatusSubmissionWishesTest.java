package nts.uk.ctx.at.schedule.dom.shift.management.workavailability;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * 
 * @author dungbn
 *
 */

@RunWith(JMockit.class)
public class GetStatusSubmissionWishesTest {

	@Injectable
	GetStatusSubmissionWishes.Require require;
	
	// 希望なし
	@Test
	public void testGetStatusSubmissionWishes() {
		
		String cmpId = "cmpId";
		String empId = "empId";
		GeneralDate availabilityDate = GeneralDateTime.now().addDays(-1).toDate();
		
		new Expectations() {
			{
				require.get(empId, availabilityDate);
				result = Optional.empty();
			}
		};
		
		DesiredSubmissionStatus status = GetStatusSubmissionWishes.get(require, cmpId, empId, availabilityDate);
		
		assertThat(status).isEqualTo(DesiredSubmissionStatus.NO_HOPE);
	}
	
	// 休日希望 
	@Test
	public void testGetStatusSubmissionWishes1() {
		
		String cmpId = "cmpId";
		String empId = "empId";
		GeneralDate availabilityDate = GeneralDateTime.now().addDays(1).toDate();
		WorkAvailabilityMemo memo = new WorkAvailabilityMemo("memo");
		AssignmentMethod assignmentMethod = AssignmentMethod.HOLIDAY;
		
		Optional<WorkAvailabilityOfOneDay> workAvailability = Optional.of(WorkAvailabilityOfOneDay
				.create(require, empId, availabilityDate, memo, assignmentMethod, Collections.emptyList(), Collections.emptyList()));
		
		new Expectations() {
			{
				require.get(empId, availabilityDate);
				result = workAvailability;
			}
		};
		
		DesiredSubmissionStatus status = GetStatusSubmissionWishes.get(require, cmpId, empId, availabilityDate);
		
		assertThat(workAvailability.get().isHolidayAvailability(require, cmpId)).isTrue();
		assertThat(status).isEqualTo(DesiredSubmissionStatus.HOLIDAY_HOPE);
		
	}
	
	// 出勤希望
	@Test
	public void testGetStatusSubmissionWishes2() {
		
		String cmpId = "cmpId";
		String empId = "empId";
		GeneralDate availabilityDate = GeneralDateTime.now().addDays(1).toDate();
		WorkAvailabilityMemo memo = new WorkAvailabilityMemo("memo");
		AssignmentMethod assignmentMethod = AssignmentMethod.TIME_ZONE;
		List<TimeSpanForCalc> timeZoneList = Arrays.asList(
				new TimeSpanForCalc(new TimeWithDayAttr(100), new TimeWithDayAttr(200)),
				new TimeSpanForCalc(new TimeWithDayAttr(300), new TimeWithDayAttr(400)));
		
		Optional<WorkAvailabilityOfOneDay> workAvailability = Optional.of(WorkAvailabilityOfOneDay
				.create(require, empId, availabilityDate, memo, assignmentMethod, Collections.emptyList(), timeZoneList));
		
		new Expectations() {
			{
				require.get(empId, availabilityDate);
				result = workAvailability;
			}
		};
		
		DesiredSubmissionStatus status = GetStatusSubmissionWishes.get(require, cmpId, empId, availabilityDate);
		
		assertThat(workAvailability.get().isHolidayAvailability(require, cmpId)).isFalse();
		assertThat(status).isEqualTo(DesiredSubmissionStatus.COMMUTING_HOPE);
		 
	}
}
