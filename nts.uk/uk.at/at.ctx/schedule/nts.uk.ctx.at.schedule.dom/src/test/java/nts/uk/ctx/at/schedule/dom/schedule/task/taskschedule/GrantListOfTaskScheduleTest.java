package nts.uk.ctx.at.schedule.dom.schedule.task.taskschedule;

import static org.assertj.core.api.Assertions.assertThat;
import java.util.Arrays;

import java.util.List;
import java.util.Optional;

import org.junit.Test;

import lombok.val;
import mockit.Expectations;
import mockit.Injectable;
import mockit.Verifications;
import nts.arc.testing.assertion.NtsAssert;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.schedule.support.supportschedule.SupportSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.support.supportschedule.SupportScheduleDetail;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.ConfirmedATR;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.workschedule.WorkScheduleHelper;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.ctx.at.shared.dom.supportmanagement.SupportType;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.workplace.TargetOrgIdenInfor;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class GrantListOfTaskScheduleTest {
	
	@Injectable
	GrantListOfTaskSchedule.Require require;
	
	@Test
	public void testGrant_exception (@Injectable TargetOrgIdenInfor recipient) {
		
		// Assign
		WorkSchedule workSchedule1 = WorkScheduleHelper.createWithParams(
				"emp-id1", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				TaskSchedule.createWithEmptyList(), 
				new SupportSchedule(Arrays.asList(
						new SupportScheduleDetail(
								recipient, 
								SupportType.TIMEZONE, 
								Optional.of( new TimeSpanForCalc(
										TimeWithDayAttr.hourMinute(13, 0), 
										TimeWithDayAttr.hourMinute(17, 0))))
						)));
		WorkSchedule workSchedule2 = WorkScheduleHelper.createWithParams(
				"emp-id2", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				TaskSchedule.createWithEmptyList(), 
				new SupportSchedule(Arrays.asList(
						new SupportScheduleDetail(
								recipient, 
								SupportType.ALLDAY, 
								Optional.empty())
						)));
		val workScheduleList = Arrays.asList(workSchedule1, workSchedule2);
		
		new Expectations() {{
			require.getWorkSchedule( (List<String>) any, (GeneralDate) any);
			result = workScheduleList;
		}};
		
		NtsAssert.businessException("Msg_3234", () -> {
			GrantListOfTaskSchedule.grant(require, 
					Arrays.asList("emp-id1", "emp-id2"), GeneralDate.ymd(2021, 12, 1), 
					new TaskCode("001"), Optional.empty());
		});
	}
	
	@Test
	public void testGrant_whole_day_ok (@Injectable TargetOrgIdenInfor recipient) {
		
		// Assign
		WorkSchedule workSchedule1 = WorkScheduleHelper.createWithParams(
				"emp-id1", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				TaskSchedule.createWithEmptyList(), 
				new SupportSchedule(Arrays.asList(
						new SupportScheduleDetail(
								recipient, 
								SupportType.ALLDAY, 
								Optional.empty())
						)));
		
		WorkSchedule workSchedule2 = WorkScheduleHelper.createWithParams(
				"emp-id2", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				TaskSchedule.createWithEmptyList(), 
				new SupportSchedule(Arrays.asList(
						new SupportScheduleDetail(
								recipient, 
								SupportType.ALLDAY, 
								Optional.empty())
						)));
		
		new Expectations( workSchedule1, workSchedule2 ) {{
			
			require.getWorkSchedule( (List<String>) any, (GeneralDate) any);
			result = Arrays.asList(workSchedule1, workSchedule2);
			
			workSchedule1.createTaskScheduleForWholeDay(require, (TaskCode) any);
			
			workSchedule2.createTaskScheduleForWholeDay(require, (TaskCode) any);
		}};
		
		val result = GrantListOfTaskSchedule.grant(require, 
					Arrays.asList("emp-id1", "emp-id2"), GeneralDate.ymd(2021, 12, 1), 
					new TaskCode("001"), Optional.empty());
		
		assertThat(result.size()).isEqualTo(2);

		new Verifications() {{
			require.updateWorkSchedule( (WorkSchedule) any);
			times = 0;  // まだ呼ばれていない
		}};
		
		result.get(0).run();  // AtomTaskを実行
		result.get(1).run();  // AtomTaskを実行
		
		new Verifications() {{
			require.updateWorkSchedule((WorkSchedule)any);
			times = 2;  // 2回呼ばれた
		}};
	}
	
	@Test
	public void testGrant_time_span_ok (@Injectable TargetOrgIdenInfor recipient) {
		
		// Assign
		WorkSchedule workSchedule1 = WorkScheduleHelper.createWithParams(
				"emp-id1", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				TaskSchedule.createWithEmptyList(), 
				new SupportSchedule(Arrays.asList(
						new SupportScheduleDetail(
								recipient, 
								SupportType.ALLDAY, 
								Optional.empty())
						)));
		
		WorkSchedule workSchedule2 = WorkScheduleHelper.createWithParams(
				"emp-id2", GeneralDate.ymd(2021, 12, 1), ConfirmedATR.UNSETTLED, 
				TaskSchedule.createWithEmptyList(), 
				new SupportSchedule(Arrays.asList(
						new SupportScheduleDetail(
								recipient, 
								SupportType.ALLDAY, 
								Optional.empty())
						)));
		
		new Expectations( workSchedule1, workSchedule2 ) {{
			
			require.getWorkSchedule( (List<String>) any, (GeneralDate) any);
			result = Arrays.asList(workSchedule1, workSchedule2);
			
			workSchedule1.addTaskScheduleWithTimeSpan(require, (TimeSpanForCalc) any, (TaskCode) any);
			
			workSchedule2.addTaskScheduleWithTimeSpan(require, (TimeSpanForCalc) any, (TaskCode) any);
		}};
		
		val result = GrantListOfTaskSchedule.grant(require, 
					Arrays.asList("emp-id1", "emp-id2"), 
					GeneralDate.ymd(2021, 12, 1), 
					new TaskCode("001"), 
					Optional.of(new TimeSpanForCalc(
							TimeWithDayAttr.hourMinute(9, 0), 
							TimeWithDayAttr.hourMinute(10, 0))));
		
		assertThat(result.size()).isEqualTo(2);

		new Verifications() {{
			require.updateWorkSchedule( (WorkSchedule) any);
			times = 0;  // まだ呼ばれていない
		}};
		
		result.get(0).run();  // AtomTaskを実行
		result.get(1).run();  // AtomTaskを実行
		
		new Verifications() {{
			require.updateWorkSchedule((WorkSchedule)any);
			times = 2;  // 2回呼ばれた
		}};	
		
	}

}
