package nts.uk.ctx.at.schedule.dom.schedule.task.taskschedule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.shr.com.time.TimeWithDayAttr;

public class TaskScheduleTest {
	
	@Test
	public void testCheckDuplicate_size0() {
		
		List<TaskScheduleDetail> details = new ArrayList<>();
		
		boolean result = NtsAssert.Invoke.staticMethod(TaskSchedule.class, "checkDuplicate", details);
		
		assertThat( result ).isFalse();
	}
	
	@Test
	public void testCheckDuplicate_size1() {
		
		List<TaskScheduleDetail> details = Arrays.asList(
				
				new TaskScheduleDetail( new TaskCode("code1"), 
						new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(8, 0), 
						TimeWithDayAttr.hourMinute(9, 0)) ));
		
		boolean result = NtsAssert.Invoke.staticMethod(TaskSchedule.class, "checkDuplicate", details);
		
		assertThat( result ).isFalse();
	}
	
	@Test
	public void testCheckDuplicate_sizeBiggerThan1_false() {
		
		List<TaskScheduleDetail> details = new ArrayList<>(Arrays.asList(
				
				new TaskScheduleDetail( new TaskCode("code1"), 
						new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(8, 0), 
						TimeWithDayAttr.hourMinute(9, 0))),
				new TaskScheduleDetail( new TaskCode("code2"), 
						new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(9, 0), 
						TimeWithDayAttr.hourMinute(10, 0))),
				new TaskScheduleDetail( new TaskCode("code3"), 
						new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(10, 0), 
						TimeWithDayAttr.hourMinute(11, 0)))
				));
		
		boolean result = NtsAssert.Invoke.staticMethod(TaskSchedule.class, "checkDuplicate", details);
		
		assertThat( result ).isFalse();
	}
	
	@Test
	public void testCheckDuplicate_sizeBiggerThan1_true() {
		
		List<TaskScheduleDetail> details = new ArrayList<>(Arrays.asList(
				
				new TaskScheduleDetail( new TaskCode("code1"), 
						new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(8, 0), 
						TimeWithDayAttr.hourMinute(9, 0))),
				new TaskScheduleDetail( new TaskCode("code2"), 
						new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(9, 0), 
						TimeWithDayAttr.hourMinute(10, 0))),
				new TaskScheduleDetail( new TaskCode("code3"), 
						new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(9, 30), 
						TimeWithDayAttr.hourMinute(11, 0)))
				));
		
		boolean result = NtsAssert.Invoke.staticMethod(TaskSchedule.class, "checkDuplicate", details);
		
		assertThat( result ).isTrue();
	}
	
	@Test
	public void testCreate_exception() {
		
		List<TaskScheduleDetail> details = new ArrayList<>(Arrays.asList(
				
				new TaskScheduleDetail( new TaskCode("code1"), 
						new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(8, 0), 
						TimeWithDayAttr.hourMinute(9, 0))),
				new TaskScheduleDetail( new TaskCode("code2"), 
						new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(9, 0), 
						TimeWithDayAttr.hourMinute(10, 0))),
				new TaskScheduleDetail( new TaskCode("code3"), 
						new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(9, 30), 
						TimeWithDayAttr.hourMinute(11, 0)))
				));
		
		NtsAssert.systemError( () -> TaskSchedule.create( details ) );
		
	}
	
	@Test
	public void testCreate_sort_success() {
		
		List<TaskScheduleDetail> details = new ArrayList<>(Arrays.asList(
				new TaskScheduleDetail( new TaskCode("code2"), 
						new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(9, 0), 
						TimeWithDayAttr.hourMinute(10, 0))),
				new TaskScheduleDetail( new TaskCode("code1"), 
						new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(8, 0), 
						TimeWithDayAttr.hourMinute(9, 0))),
				new TaskScheduleDetail( new TaskCode("code3"), 
						new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(10, 0), 
						TimeWithDayAttr.hourMinute(11, 0)))
				));
		
		TaskSchedule result = TaskSchedule.create( details ) ;
		
		assertThat( result.getDetails() )
			.extracting(
				d -> d.getTaskCode().v(),
				d -> d.getTimeSpan().getStart(),
				d -> d.getTimeSpan().getEnd())
			.containsExactly(
				tuple( "code1", TimeWithDayAttr.hourMinute(8, 0), TimeWithDayAttr.hourMinute(9, 0) ),
				tuple( "code2", TimeWithDayAttr.hourMinute(9, 0), TimeWithDayAttr.hourMinute(10, 0) ),
				tuple( "code3", TimeWithDayAttr.hourMinute(10, 0), TimeWithDayAttr.hourMinute(11, 0) )
			);
		
	}
	
	@Test
	public void testCreate_merge_success() {
		
		// 8:00        9:00    9:30     10:00          12:00          13:00       14:00       15:00
		// code1-------code2---code2----code2---------------␣␣␣␣␣␣␣␣␣␣code2-------code3------------
		List<TaskScheduleDetail> details = new ArrayList<>(Arrays.asList(
				new TaskScheduleDetail( new TaskCode("code1"), 
						new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(8, 0), 
						TimeWithDayAttr.hourMinute(9, 0))),
				new TaskScheduleDetail( new TaskCode("code2"), 
						new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(9, 0), 
						TimeWithDayAttr.hourMinute(9, 30))),
				new TaskScheduleDetail( new TaskCode("code2"), 
						new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(9, 30), 
						TimeWithDayAttr.hourMinute(10, 0))),
				new TaskScheduleDetail( new TaskCode("code2"), 
						new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(10, 0), 
						TimeWithDayAttr.hourMinute(12, 0))),
				new TaskScheduleDetail( new TaskCode("code2"), 
						new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(13, 0), 
						TimeWithDayAttr.hourMinute(14, 0))),
				new TaskScheduleDetail( new TaskCode("code3"), 
						new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(14, 0), 
						TimeWithDayAttr.hourMinute(15, 0)))
				));
		
		TaskSchedule result = TaskSchedule.create( details ) ;
		
		assertThat( result.getDetails() )
			.extracting(
				d -> d.getTaskCode().v(),
				d -> d.getTimeSpan().getStart(),
				d -> d.getTimeSpan().getEnd())
			.containsExactly(
				tuple( "code1", TimeWithDayAttr.hourMinute(8, 0), TimeWithDayAttr.hourMinute(9, 0) ),
				tuple( "code2", TimeWithDayAttr.hourMinute(9, 0), TimeWithDayAttr.hourMinute(12, 0) ),
				tuple( "code2", TimeWithDayAttr.hourMinute(13, 0), TimeWithDayAttr.hourMinute(14, 0) ),
				tuple( "code3", TimeWithDayAttr.hourMinute(14, 0), TimeWithDayAttr.hourMinute(15, 0) )
			);
		
	}
	
	@Test
	public void testAddTaskScheduleDetail_before_notDuplicate_sameCode() {
		
		List<TaskScheduleDetail> details = new ArrayList<>(Arrays.asList(
				new TaskScheduleDetail( new TaskCode("code1"), 
						new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(14, 0), 
						TimeWithDayAttr.hourMinute(15, 0)))
				));
		
		TaskSchedule taskSchedule = new TaskSchedule(details);
		
		TaskScheduleDetail newTask = new TaskScheduleDetail(
				new TaskCode("code1"), 
				new TimeSpanForCalc(
					TimeWithDayAttr.hourMinute(13, 00), 
					TimeWithDayAttr.hourMinute(14, 00) ));
		
		TaskSchedule result = taskSchedule.addTaskScheduleDetail( newTask );
		
		assertThat( result.getDetails() )
			.extracting(
				d -> d.getTaskCode().v(),
				d -> d.getTimeSpan().getStart().hour(),
				d -> d.getTimeSpan().getStart().minute(),
				d -> d.getTimeSpan().getEnd().hour(),
				d -> d.getTimeSpan().getEnd().minute())
			.containsExactly(
				tuple( "code1", 13, 0, 15, 0)
			);
	} 
	
	@Test
	public void testAddTaskScheduleDetail_before_notDuplicate_differentCode() {
		
		List<TaskScheduleDetail> details = new ArrayList<>(Arrays.asList(
				new TaskScheduleDetail( new TaskCode("code1"), 
						new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(14, 0), 
						TimeWithDayAttr.hourMinute(15, 0)))
				));
		
		TaskSchedule taskSchedule = new TaskSchedule(details);
		
		TaskScheduleDetail newTask = new TaskScheduleDetail(
				new TaskCode("code-new"), 
				new TimeSpanForCalc(
					TimeWithDayAttr.hourMinute(13, 00), 
					TimeWithDayAttr.hourMinute(14, 00) ));
		
		TaskSchedule result = taskSchedule.addTaskScheduleDetail( newTask );
		
		assertThat( result.getDetails() )
			.extracting(
				d -> d.getTaskCode().v(),
				d -> d.getTimeSpan().getStart().hour(),
				d -> d.getTimeSpan().getStart().minute(),
				d -> d.getTimeSpan().getEnd().hour(),
				d -> d.getTimeSpan().getEnd().minute())
			.containsExactly(
				tuple( "code-new", 13, 0, 14, 0),
				tuple( "code1", 14, 0, 15, 0)
			);
	} 
	
	@Test
	public void testAddTaskScheduleDetail_before_duplicate_sameCode() {
		
		List<TaskScheduleDetail> details = new ArrayList<>(Arrays.asList(
				new TaskScheduleDetail( new TaskCode("code1"), 
						new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(14, 0), 
						TimeWithDayAttr.hourMinute(15, 0)))
				));
		
		TaskSchedule taskSchedule = new TaskSchedule(details);
		
		TaskScheduleDetail newTask = new TaskScheduleDetail(
				new TaskCode("code1"), 
				new TimeSpanForCalc(
					TimeWithDayAttr.hourMinute(13, 00), 
					TimeWithDayAttr.hourMinute(14, 30) ));
		
		TaskSchedule result = taskSchedule.addTaskScheduleDetail( newTask );
		
		assertThat( result.getDetails() )
			.extracting(
				d -> d.getTaskCode().v(),
				d -> d.getTimeSpan().getStart().hour(),
				d -> d.getTimeSpan().getStart().minute(),
				d -> d.getTimeSpan().getEnd().hour(),
				d -> d.getTimeSpan().getEnd().minute())
			.containsExactly(
				tuple( "code1", 13, 0, 15, 0)
			);
	} 
	
	@Test
	public void testAddTaskScheduleDetail_before_duplicate_differentCode() {
		
		List<TaskScheduleDetail> details = new ArrayList<>(Arrays.asList(
				new TaskScheduleDetail( new TaskCode("code1"), 
						new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(14, 0), 
						TimeWithDayAttr.hourMinute(15, 0)))
				));
		
		TaskSchedule taskSchedule = new TaskSchedule(details);
		
		TaskScheduleDetail newTask = new TaskScheduleDetail(
				new TaskCode("code-new"), 
				new TimeSpanForCalc(
					TimeWithDayAttr.hourMinute(13, 00), 
					TimeWithDayAttr.hourMinute(14, 30) ));
		
		TaskSchedule result = taskSchedule.addTaskScheduleDetail( newTask );
		
		assertThat( result.getDetails() )
			.extracting(
				d -> d.getTaskCode().v(),
				d -> d.getTimeSpan().getStart().hour(),
				d -> d.getTimeSpan().getStart().minute(),
				d -> d.getTimeSpan().getEnd().hour(),
				d -> d.getTimeSpan().getEnd().minute())
			.containsExactly(
					tuple( "code-new", 13, 0, 14, 30),
					tuple( "code1", 14, 30, 15, 0)
			);
	} 
	
	
	@Test
	public void testAddTaskScheduleDetail_inside_sameCode() {
		
		List<TaskScheduleDetail> details = new ArrayList<>(Arrays.asList(
				new TaskScheduleDetail( new TaskCode("code1"), 
						new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(13, 0), 
						TimeWithDayAttr.hourMinute(16, 0)))
				));
		
		TaskSchedule taskSchedule = new TaskSchedule(details);
		
		TaskScheduleDetail newTask = new TaskScheduleDetail(
				new TaskCode("code1"), 
				new TimeSpanForCalc(
					TimeWithDayAttr.hourMinute(14, 0), 
					TimeWithDayAttr.hourMinute(15, 0) ));
		
		TaskSchedule result = taskSchedule.addTaskScheduleDetail( newTask );
		
		assertThat( result.getDetails() )
			.extracting(
				d -> d.getTaskCode().v(),
				d -> d.getTimeSpan().getStart().hour(),
				d -> d.getTimeSpan().getStart().minute(),
				d -> d.getTimeSpan().getEnd().hour(),
				d -> d.getTimeSpan().getEnd().minute())
			.containsExactly(
				tuple( "code1", 13, 0, 16, 0)
			);
	}
	
	@Test
	public void testAddTaskScheduleDetail_inside_differentCode() {
		
		List<TaskScheduleDetail> details = new ArrayList<>(Arrays.asList(
				new TaskScheduleDetail( new TaskCode("code1"), 
						new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(13, 0), 
						TimeWithDayAttr.hourMinute(16, 0)))
				));
		
		TaskSchedule taskSchedule = new TaskSchedule(details);
		
		TaskScheduleDetail newTask = new TaskScheduleDetail(
				new TaskCode("code-new"), 
				new TimeSpanForCalc(
					TimeWithDayAttr.hourMinute(14, 0), 
					TimeWithDayAttr.hourMinute(15, 0) ));
		
		TaskSchedule result = taskSchedule.addTaskScheduleDetail( newTask );
		
		assertThat( result.getDetails() )
			.extracting(
				d -> d.getTaskCode().v(),
				d -> d.getTimeSpan().getStart().hour(),
				d -> d.getTimeSpan().getStart().minute(),
				d -> d.getTimeSpan().getEnd().hour(),
				d -> d.getTimeSpan().getEnd().minute())
			.containsExactly(
				tuple( "code1", 13, 0, 14, 0),
				tuple( "code-new", 14, 0, 15, 0),
				tuple( "code1", 15, 0, 16, 0)
			);
	} 
	
	@Test
	public void testAddTaskScheduleDetail_after_duplicate_sameCode() {
		
		List<TaskScheduleDetail> details = new ArrayList<>(Arrays.asList(
				new TaskScheduleDetail( new TaskCode("code1"), 
						new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(14, 0), 
						TimeWithDayAttr.hourMinute(15, 0)))
				));
		
		TaskSchedule taskSchedule = new TaskSchedule(details);
		
		TaskScheduleDetail newTask = new TaskScheduleDetail(
				new TaskCode("code1"), 
				new TimeSpanForCalc(
					TimeWithDayAttr.hourMinute(14, 30), 
					TimeWithDayAttr.hourMinute(16, 0) ));
		
		TaskSchedule result = taskSchedule.addTaskScheduleDetail( newTask );
		
		assertThat( result.getDetails() )
			.extracting(
				d -> d.getTaskCode().v(),
				d -> d.getTimeSpan().getStart().hour(),
				d -> d.getTimeSpan().getStart().minute(),
				d -> d.getTimeSpan().getEnd().hour(),
				d -> d.getTimeSpan().getEnd().minute())
			.containsExactly(
				tuple( "code1", 14, 0, 16, 0)
			);
	}
	
	@Test
	public void testAddTaskScheduleDetail_after_duplicate_differentCode() {
		
		List<TaskScheduleDetail> details = new ArrayList<>(Arrays.asList(
				new TaskScheduleDetail( new TaskCode("code1"), 
						new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(14, 0), 
						TimeWithDayAttr.hourMinute(15, 0)))
				));
		
		TaskSchedule taskSchedule = new TaskSchedule(details);
		
		TaskScheduleDetail newTask = new TaskScheduleDetail(
				new TaskCode("code-new"), 
				new TimeSpanForCalc(
					TimeWithDayAttr.hourMinute(14, 30), 
					TimeWithDayAttr.hourMinute(16, 0) ));
		
		TaskSchedule result = taskSchedule.addTaskScheduleDetail( newTask );
		
		assertThat( result.getDetails() )
			.extracting(
				d -> d.getTaskCode().v(),
				d -> d.getTimeSpan().getStart().hour(),
				d -> d.getTimeSpan().getStart().minute(),
				d -> d.getTimeSpan().getEnd().hour(),
				d -> d.getTimeSpan().getEnd().minute())
			.containsExactly(
					tuple( "code1", 14, 0, 14, 30),
					tuple( "code-new", 14, 30, 16, 0)
			);
	} 
	
	@Test
	public void testAddTaskScheduleDetail_after_notDuplicate_sameCode() {
		
		List<TaskScheduleDetail> details = new ArrayList<>(Arrays.asList(
				new TaskScheduleDetail( new TaskCode("code1"), 
						new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(14, 0), 
						TimeWithDayAttr.hourMinute(15, 0)))
				));
		
		TaskSchedule taskSchedule = new TaskSchedule(details);
		
		TaskScheduleDetail newTask = new TaskScheduleDetail(
				new TaskCode("code1"), 
				new TimeSpanForCalc(
					TimeWithDayAttr.hourMinute(15, 00), 
					TimeWithDayAttr.hourMinute(16, 00) ));
		
		TaskSchedule result = taskSchedule.addTaskScheduleDetail( newTask );
		
		assertThat( result.getDetails() )
			.extracting(
				d -> d.getTaskCode().v(),
				d -> d.getTimeSpan().getStart().hour(),
				d -> d.getTimeSpan().getStart().minute(),
				d -> d.getTimeSpan().getEnd().hour(),
				d -> d.getTimeSpan().getEnd().minute())
			.containsExactly(
				tuple( "code1", 14, 0, 16, 0)
			);
	} 
	
	@Test
	public void testAddTaskScheduleDetail_after_notDuplicate_differentCode() {
		
		List<TaskScheduleDetail> details = new ArrayList<>(Arrays.asList(
				new TaskScheduleDetail( new TaskCode("code1"), 
						new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(14, 0), 
						TimeWithDayAttr.hourMinute(15, 0)))
				));
		
		TaskSchedule taskSchedule = new TaskSchedule(details);
		
		TaskScheduleDetail newTask = new TaskScheduleDetail(
				new TaskCode("code-new"), 
				new TimeSpanForCalc(
					TimeWithDayAttr.hourMinute(15, 00), 
					TimeWithDayAttr.hourMinute(16, 00) ));
		
		TaskSchedule result = taskSchedule.addTaskScheduleDetail( newTask );
		
		assertThat( result.getDetails() )
			.extracting(
				d -> d.getTaskCode().v(),
				d -> d.getTimeSpan().getStart().hour(),
				d -> d.getTimeSpan().getStart().minute(),
				d -> d.getTimeSpan().getEnd().hour(),
				d -> d.getTimeSpan().getEnd().minute())
			.containsExactly(
				tuple( "code1", 14, 0, 15, 0),
				tuple( "code-new", 15, 0, 16, 0)
				
			);
	} 
	
	@Test
	public void testAddTaskScheduleDetail_multi() {
		
		List<TaskScheduleDetail> details = new ArrayList<>(Arrays.asList(
				new TaskScheduleDetail( new TaskCode("code1"), 
						new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(13, 0), 
						TimeWithDayAttr.hourMinute(14, 0))),
				new TaskScheduleDetail( new TaskCode("code2"), 
						new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(14, 0), 
						TimeWithDayAttr.hourMinute(15, 0))),
				new TaskScheduleDetail( new TaskCode("code3"), 
						new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(15, 0), 
						TimeWithDayAttr.hourMinute(16, 0)))
				));
		
		TaskSchedule taskSchedule = new TaskSchedule(details);
		
		TaskScheduleDetail newTask = new TaskScheduleDetail(
				new TaskCode("code-new"), 
				new TimeSpanForCalc(
					TimeWithDayAttr.hourMinute(13, 30), 
					TimeWithDayAttr.hourMinute(15, 30) ));
		
		TaskSchedule result = taskSchedule.addTaskScheduleDetail( newTask );
		
		assertThat( result.getDetails() )
			.extracting(
				d -> d.getTaskCode().v(),
				d -> d.getTimeSpan().getStart().hour(),
				d -> d.getTimeSpan().getStart().minute(),
				d -> d.getTimeSpan().getEnd().hour(),
				d -> d.getTimeSpan().getEnd().minute())
			.containsExactly(
				tuple( "code1", 13, 0, 13, 30),
				tuple( "code-new", 13, 30, 15, 30),
				tuple( "code3", 15, 30, 16, 0)
			);
	} 

}
