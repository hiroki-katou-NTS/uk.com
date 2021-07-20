package nts.uk.ctx.at.record.dom.daily.ouen;

import static org.assertj.core.api.Assertions.assertThat;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.junit.Test;
import org.junit.runner.RunWith;

import mockit.Expectations;
import mockit.Injectable;
import mockit.integration.junit4.JMockit;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.OuenWorkTimeSheetOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.work.WorkCode;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.Task;

/**
 * 
 * @author chungnt
 *
 */

@RunWith(JMockit.class)
public class CreateAttendanceTimeZoneForEachSupportWorkTest {

	@Injectable
	private CreateAttendanceTimeZoneForEachSupportWork.Require require;
	
	@Injectable
	private nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.timesheet.ouen.work.WorkGroup.Require require1;


	private String empId = "empId";
	private GeneralDate ymd = GeneralDate.today();
	
	private Task task = CreateAttendanceTimeZoneForEachSupportWorkHelper.getTask();

	List<WorkDetailsParam> workDetailsParams = CreateAttendanceTimeZoneForEachSupportWorkHelper.getWorkDetailsParams();
	
	// if $旧の作業時間帯.isNotPresent
	@Test
	public void test() {

		new Expectations() {
			{
				require.find(empId, ymd);
				
				require.getTask(new TaskFrameNo(1), new WorkCode("Dummy"));
				result = Optional.ofNullable(task);
				
				require.getAffWkpHistItemByEmpDate(empId, ymd);
				result = "QWERTY";
			}
		};

		List<OuenWorkTimeSheetOfDailyAttendance> result = CreateAttendanceTimeZoneForEachSupportWork.create(require,
				empId, ymd, workDetailsParams);

		assertThat(result.isEmpty()).isFalse();
		assertThat(result.get(0).getWorkContent().getWorkplace().getWorkplaceId().v()).isEqualTo("QWERTY");
		assertThat(result.get(1).getWorkContent().getWorkplace().getWorkplaceId().v()).isEqualTo("QWERTY");
		assertThat(result.get(2).getWorkContent().getWorkplace().getWorkplaceId().v()).isEqualTo("QWERTY");
		assertThat(result.size()).isEqualTo(3);
		assertThat(result.get(0).getWorkNo().v()).isEqualTo(1);
		assertThat(result.get(0).getTimeSheet().getStart().get().getTimeWithDay().get().v()).isEqualTo(100);
		assertThat(result.get(0).getTimeSheet().getEnd().get().getTimeWithDay().get().v()).isEqualTo(1000);
		assertThat(result.get(1).getWorkNo().v()).isEqualTo(2);
		assertThat(result.get(1).getTimeSheet().getStart().get().getTimeWithDay().get().v()).isEqualTo(200);
		assertThat(result.get(1).getTimeSheet().getEnd().get().getTimeWithDay().get().v()).isEqualTo(2000);
		assertThat(result.get(2).getWorkNo().v()).isEqualTo(3);
		assertThat(result.get(2).getTimeSheet().getStart().get().getTimeWithDay().get().v()).isEqualTo(300);
		assertThat(result.get(2).getTimeSheet().getEnd().get().getTimeWithDay().get().v()).isEqualTo(3000);
		
		assertThat(result.get(0).getTimeSheet().getWorkNo().v()).isEqualTo(1);
		assertThat(result.get(0).getWorkContent().getWorkRemarks().isPresent()).isFalse();
		assertThat(result.get(0).getWorkContent().getWork().isPresent()).isTrue();
		assertThat(result.get(0).getWorkContent().getWork().get().getWorkCD1().v()).isEqualTo("Dummy");
	}

	// if $旧の作業時間帯.isPresent
	@Test
	public void test1() {
		List<OuenWorkTimeSheetOfDailyAttendance> ouenTimeSheets = new ArrayList<>();
		
		OuenWorkTimeSheetOfDaily ofDaily = OuenWorkTimeSheetOfDailyHelper.getOuenWorkTimeSheetOfDailyDefault();

		OuenWorkTimeSheetOfDailyAttendance ouenTimeSheet = CreateAttendanceTimeZoneHelper.get();

		ouenTimeSheets.add(ouenTimeSheet);

//		OuenWorkTimeSheetOfDaily daily = new OuenWorkTimeSheetOfDaily(empId, ymd, ouenTimeSheets);

		new Expectations() {
			{
				require.find(empId, ymd);
				result = ofDaily;
				
				require.getTask(new TaskFrameNo(1), new WorkCode("Dummy"));
				result = Optional.ofNullable(task);
			}
		};

		List<OuenWorkTimeSheetOfDailyAttendance> result = CreateAttendanceTimeZoneForEachSupportWork.create(require,
				empId, ymd, workDetailsParams);

		assertThat(result.isEmpty()).isFalse();
		assertThat(result.get(0).getWorkContent().getWorkplace().getWorkplaceId().v()).isEqualTo("workplaceId");
		assertThat(result.get(1).getWorkContent().getWorkplace().getWorkplaceId().v()).isEqualTo("");
		assertThat(result.get(2).getWorkContent().getWorkplace().getWorkplaceId().v()).isEqualTo("");
		assertThat(result.size()).isEqualTo(3);
		assertThat(result.get(0).getWorkNo().v()).isEqualTo(1);
		assertThat(result.get(0).getTimeSheet().getStart().get().getTimeWithDay().get().v()).isEqualTo(100);
		assertThat(result.get(0).getTimeSheet().getEnd().get().getTimeWithDay().get().v()).isEqualTo(1000);
		assertThat(result.get(1).getWorkNo().v()).isEqualTo(2);
		assertThat(result.get(1).getTimeSheet().getStart().get().getTimeWithDay().get().v()).isEqualTo(200);
		assertThat(result.get(1).getTimeSheet().getEnd().get().getTimeWithDay().get().v()).isEqualTo(2000);
		assertThat(result.get(2).getWorkNo().v()).isEqualTo(3);
		assertThat(result.get(2).getTimeSheet().getStart().get().getTimeWithDay().get().v()).isEqualTo(300);
		assertThat(result.get(2).getTimeSheet().getEnd().get().getTimeWithDay().get().v()).isEqualTo(3000);
	}
}
