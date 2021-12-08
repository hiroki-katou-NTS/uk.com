package nts.uk.ctx.at.schedule.dom.schedule.task.taskschedule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;

import lombok.val;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class TaskScheduleDetailTest {
	
	private TaskScheduleDetail target;
	
	@Before
	public void init() {
		target = TaskScheduleDetailTestHelper.create("code1", 8, 0, 12, 0);
	}
	
	@Test
	public void testGetters() {
		NtsAssert.invokeGetters(target);
	}
	
	@Test
	public void testGetTaskFrameNo() {
		val result = target.getTaskFrameNo();
		
		assertThat( result.v() ).isEqualTo( 1 );
	}
	
	@Test
	public void testGetNotDuplicatedWith_OtherConnonateBegintime() {
		
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
		
		TimeSpanForCalc otherTimeSpan = new TimeSpanForCalc(
				TimeWithDayAttr.hourMinute(8, 0), 
				TimeWithDayAttr.hourMinute(14, 0));
		
		List<TaskScheduleDetail> result = target.getNotDuplicatedWith(otherTimeSpan);
		
		assertThat( result ).isEmpty();
	}
	
	@Test
	public void testGetNotDuplicatedWith_NotDuplicate() {
		
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
	
	@Test
	public void testIsContinuousTask_false_deferent_code_not_continuous() {
		
		TaskScheduleDetail other = TaskScheduleDetailTestHelper.create("code2", 13, 0, 15, 0);
		
		boolean result = target.isContinuousTask(other);
		
		assertThat( result ).isFalse();
	}
	
	@Test
	public void testIsContinuousTask_false_deferent_code_continuous() {
		
		TaskScheduleDetail other = TaskScheduleDetailTestHelper.create("code2", 6, 0, 8, 0);
		
		boolean result = target.isContinuousTask(other);
		
		assertThat( result ).isFalse();
	}
	
	@Test
	public void testIsContinuousTask_false_same_code_not_continuous() {
		
		TaskScheduleDetail other = TaskScheduleDetailTestHelper.create("code1", 13, 0, 15, 0);
		
		boolean result = target.isContinuousTask(other);
		
		assertThat( result ).isFalse();
	}
	
	@Test
	public void testIsContinuousTask_true() {
		
		TaskScheduleDetail other = TaskScheduleDetailTestHelper.create("code1", 6, 0, 8, 0);
		
		boolean result = target.isContinuousTask(other);
		
		assertThat( result ).isTrue();
	}
	
	@Test
	public void testIsDuplicateWith() {
		
		List<TimeSpanForCalc> inputList = Arrays.asList( 
				new TimeSpanForCalc(TimeWithDayAttr.hourMinute(6, 0), TimeWithDayAttr.hourMinute(7, 0)), // NOT_DUPLICATE
				new TimeSpanForCalc(TimeWithDayAttr.hourMinute(7, 0), TimeWithDayAttr.hourMinute(9, 0)), // CONNOTATE_BEGINTIME
				new TimeSpanForCalc(TimeWithDayAttr.hourMinute(9, 0), TimeWithDayAttr.hourMinute(10, 0)), // CONTAINS
				new TimeSpanForCalc(TimeWithDayAttr.hourMinute(10, 0), TimeWithDayAttr.hourMinute(13, 0)), // CONNOTATE_ENDTIME
				new TimeSpanForCalc(TimeWithDayAttr.hourMinute(13, 0), TimeWithDayAttr.hourMinute(15, 0)), // NOT_DUPLICATE
				new TimeSpanForCalc(TimeWithDayAttr.hourMinute(7, 0), TimeWithDayAttr.hourMinute(13, 0)), // CONTAINED
				new TimeSpanForCalc(TimeWithDayAttr.hourMinute(8, 0), TimeWithDayAttr.hourMinute(12, 0)) // SAME_SPAN
				);
		
		
		assertThat( this.target.isDuplicateWith(inputList.get(0)) ).isFalse(); // NOT_DUPLICATE
		assertThat( this.target.isDuplicateWith(inputList.get(1)) ).isTrue(); // CONNOTATE_BEGINTIME
		assertThat( this.target.isDuplicateWith(inputList.get(2)) ).isTrue(); // CONTAINS
		assertThat( this.target.isDuplicateWith(inputList.get(3)) ).isTrue(); // CONNOTATE_ENDTIME
		assertThat( this.target.isDuplicateWith(inputList.get(4)) ).isFalse(); // NOT_DUPLICATE
		assertThat( this.target.isDuplicateWith(inputList.get(5)) ).isTrue(); // CONTAINED
		assertThat( this.target.isDuplicateWith(inputList.get(6)) ).isTrue(); // SAME_SPAN
	}
	
	@Test
	public void testIsContainedIn() {
		
		List<TimeSpanForCalc> inputList = Arrays.asList( 
				new TimeSpanForCalc(TimeWithDayAttr.hourMinute(6, 0), TimeWithDayAttr.hourMinute(7, 0)), // NOT_DUPLICATE
				new TimeSpanForCalc(TimeWithDayAttr.hourMinute(7, 0), TimeWithDayAttr.hourMinute(9, 0)), // CONNOTATE_BEGINTIME
				new TimeSpanForCalc(TimeWithDayAttr.hourMinute(9, 0), TimeWithDayAttr.hourMinute(10, 0)), // CONTAINS
				new TimeSpanForCalc(TimeWithDayAttr.hourMinute(10, 0), TimeWithDayAttr.hourMinute(13, 0)), // CONNOTATE_ENDTIME
				new TimeSpanForCalc(TimeWithDayAttr.hourMinute(13, 0), TimeWithDayAttr.hourMinute(15, 0)), // NOT_DUPLICATE
				new TimeSpanForCalc(TimeWithDayAttr.hourMinute(7, 0), TimeWithDayAttr.hourMinute(13, 0)), // CONTAINED
				new TimeSpanForCalc(TimeWithDayAttr.hourMinute(8, 0), TimeWithDayAttr.hourMinute(12, 0)) // SAME_SPAN
				);
		
		
		assertThat( this.target.isContainedIn(inputList.get(0)) ).isFalse(); // NOT_DUPLICATE
		assertThat( this.target.isContainedIn(inputList.get(1)) ).isFalse(); // CONNOTATE_BEGINTIME
		assertThat( this.target.isContainedIn(inputList.get(2)) ).isFalse(); // CONTAINS
		assertThat( this.target.isContainedIn(inputList.get(3)) ).isFalse(); // CONNOTATE_ENDTIME
		assertThat( this.target.isContainedIn(inputList.get(4)) ).isFalse(); // NOT_DUPLICATE
		assertThat( this.target.isContainedIn(inputList.get(5)) ).isTrue(); // CONTAINED
		assertThat( this.target.isContainedIn(inputList.get(6)) ).isTrue(); // SAME_SPAN
	}
	
	
}
