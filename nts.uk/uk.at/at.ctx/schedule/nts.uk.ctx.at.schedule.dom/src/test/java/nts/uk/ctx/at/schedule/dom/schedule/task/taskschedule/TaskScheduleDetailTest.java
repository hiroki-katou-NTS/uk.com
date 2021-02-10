package nts.uk.ctx.at.schedule.dom.schedule.task.taskschedule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.List;

import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class TaskScheduleDetailTest {
	
	@Test
	public void testGetters() {
		TaskScheduleDetail target = TaskScheduleDetailTestHelper.create("code", 8, 0, 9, 0);
		NtsAssert.invokeGetters(target);
	}
	
	@Test
	public void testGetTaskFrameNo() {
		TaskScheduleDetail target = TaskScheduleDetailTestHelper.create("code", 8, 0, 9, 0);
		val result = target.getTaskFrameNo();
		
		assertThat( result.v() ).isEqualTo( 1 );
	}
	
	@Test
	public void testGetNotDuplicatedWith_OtherConnonateBegintime() {
		
		TaskScheduleDetail target = TaskScheduleDetailTestHelper.create("code1", 8, 0, 12, 0);
		
		TimeSpanForCalc otherTimeSpan = new TimeSpanForCalc(
				TimeWithDayAttr.hourMinute(7, 0), 
				TimeWithDayAttr.hourMinute(9, 0));
		
		List<TaskScheduleDetail> result = target.getNotDuplicatedWith(otherTimeSpan);
		
		assertThat( result )
			.extracting( 
				d -> d.getTaskCode().v(),
				d -> d.getTimeSpan().getStart(),
				d -> d.getTimeSpan().getEnd())
			.containsExactly(
				tuple( "code1", TimeWithDayAttr.hourMinute(9, 0), TimeWithDayAttr.hourMinute(12, 0)) );
	}
	
	@Test
	public void testGetNotDuplicatedWith_OtherIsInside() {
		
		TaskScheduleDetail target = TaskScheduleDetailTestHelper.create("code1", 8, 0, 12, 0);
		
		TimeSpanForCalc otherTimeSpan = new TimeSpanForCalc(
				TimeWithDayAttr.hourMinute(9, 0), 
				TimeWithDayAttr.hourMinute(10, 0));
		
		List<TaskScheduleDetail> result = target.getNotDuplicatedWith(otherTimeSpan);
		
		assertThat( result )
			.extracting( 
				d -> d.getTaskCode().v(),
				d -> d.getTimeSpan().getStart(),
				d -> d.getTimeSpan().getEnd())
			.containsExactly(
				tuple( "code1", TimeWithDayAttr.hourMinute(8, 0), TimeWithDayAttr.hourMinute(9, 0)),
				tuple( "code1", TimeWithDayAttr.hourMinute(10, 0), TimeWithDayAttr.hourMinute(12, 0)));
	}
	
	@Test
	public void testGetNotDuplicatedWith_TargetIsInside() {
		
		TaskScheduleDetail target = TaskScheduleDetailTestHelper.create("code1", 8, 0, 12, 0);
		
		TimeSpanForCalc otherTimeSpan = new TimeSpanForCalc(
				TimeWithDayAttr.hourMinute(8, 0), 
				TimeWithDayAttr.hourMinute(14, 0));
		
		List<TaskScheduleDetail> result = target.getNotDuplicatedWith(otherTimeSpan);
		
		assertThat( result ).isEmpty();
	}
	
	@Test
	public void testGetNotDuplicatedWith_NotDuplicate() {
		
		TaskScheduleDetail target = TaskScheduleDetailTestHelper.create("code1", 8, 0, 12, 0);
		
		TimeSpanForCalc otherTimeSpan = new TimeSpanForCalc(
				TimeWithDayAttr.hourMinute(13, 0), 
				TimeWithDayAttr.hourMinute(14, 0));
		
		List<TaskScheduleDetail> result = target.getNotDuplicatedWith(otherTimeSpan);
		
		assertThat( result )
			.extracting( 
				d -> d.getTaskCode().v(),
				d -> d.getTimeSpan().getStart(),
				d -> d.getTimeSpan().getEnd())
			.containsExactly(
				tuple( "code1", TimeWithDayAttr.hourMinute(8, 0), TimeWithDayAttr.hourMinute(12, 0)));
	}
	
}
