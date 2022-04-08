package nts.uk.ctx.at.schedule.dom.schedule.task.taskschedule;

import static org.assertj.core.api.Assertions.assertThat;
import static org.assertj.core.api.Assertions.tuple;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Before;
import org.junit.Test;
import org.junit.experimental.runners.Enclosed;
import org.junit.runner.RunWith;

import mockit.Injectable;
import nts.arc.testing.assertion.NtsAssert;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;
import nts.uk.shr.com.time.TimeWithDayAttr;

@RunWith(Enclosed.class)
public class TaskScheduleTest {
	
	@Test
	public void testGetters(
			@Injectable TaskScheduleDetail detail1,
			@Injectable TaskScheduleDetail detail2) {
		
		TaskSchedule target = new TaskSchedule(Arrays.asList(detail1, detail2));
		NtsAssert.invokeGetters(target);
	}
	
	public static class TaskScheduleTest_create {
		
		@Test
		public void testCreate_duplicate_exception() {
			
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
	}
	
	
	public static class TaskScheduleTest_addTaskScheduleDetail_Single {
		
		TaskSchedule target;
		
		@Before
		public void initTaskSchedule() {
			
			List<TaskScheduleDetail> details = new ArrayList<>(Arrays.asList(
					new TaskScheduleDetail( new TaskCode("code1"), 
							new TimeSpanForCalc(
							TimeWithDayAttr.hourMinute(14, 0), 
							TimeWithDayAttr.hourMinute(16, 0)))
					));
			
			target = new TaskSchedule(details);
			
		}
		
		@Test
		public void testAddTaskScheduleDetail_before_notDuplicate_sameCode() {
			
			
			TaskScheduleDetail newTask = new TaskScheduleDetail(
					new TaskCode("code1"), 
					new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(13, 00), 
						TimeWithDayAttr.hourMinute(14, 00) ));
			
			TaskSchedule result = target.addTaskScheduleDetail( newTask );
			
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
		public void testAddTaskScheduleDetail_before_notDuplicate_differentCode() {
			
			TaskScheduleDetail newTask = new TaskScheduleDetail(
					new TaskCode("code-new"), 
					new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(13, 00), 
						TimeWithDayAttr.hourMinute(14, 00) ));
			
			TaskSchedule result = target.addTaskScheduleDetail( newTask );
			
			assertThat( result.getDetails() )
				.extracting(
					d -> d.getTaskCode().v(),
					d -> d.getTimeSpan().getStart().hour(),
					d -> d.getTimeSpan().getStart().minute(),
					d -> d.getTimeSpan().getEnd().hour(),
					d -> d.getTimeSpan().getEnd().minute())
				.containsExactly(
					tuple( "code-new", 13, 0, 14, 0),
					tuple( "code1", 14, 0, 16, 0)
				);
		} 
		
		@Test
		public void testAddTaskScheduleDetail_before_duplicate_sameCode() {
			
			TaskScheduleDetail newTask = new TaskScheduleDetail(
					new TaskCode("code1"), 
					new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(13, 00), 
						TimeWithDayAttr.hourMinute(14, 30) ));
			
			TaskSchedule result = target.addTaskScheduleDetail( newTask );
			
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
		public void testAddTaskScheduleDetail_before_duplicate_differentCode() {
			
			TaskScheduleDetail newTask = new TaskScheduleDetail(
					new TaskCode("code-new"), 
					new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(13, 00), 
						TimeWithDayAttr.hourMinute(14, 30) ));
			
			TaskSchedule result = target.addTaskScheduleDetail( newTask );
			
			assertThat( result.getDetails() )
				.extracting(
					d -> d.getTaskCode().v(),
					d -> d.getTimeSpan().getStart().hour(),
					d -> d.getTimeSpan().getStart().minute(),
					d -> d.getTimeSpan().getEnd().hour(),
					d -> d.getTimeSpan().getEnd().minute())
				.containsExactly(
						tuple( "code-new", 13, 0, 14, 30),
						tuple( "code1", 14, 30, 16, 0)
				);
		} 
		
		
		@Test
		public void testAddTaskScheduleDetail_inside_sameCode() {
			
			TaskScheduleDetail newTask = new TaskScheduleDetail(
					new TaskCode("code1"), 
					new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(14, 0), 
						TimeWithDayAttr.hourMinute(15, 0) ));
			
			TaskSchedule result = target.addTaskScheduleDetail( newTask );
			
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
		public void testAddTaskScheduleDetail_inside_differentCode() {
			
			TaskScheduleDetail newTask = new TaskScheduleDetail(
					new TaskCode("code-new"), 
					new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(15, 0), 
						TimeWithDayAttr.hourMinute(15, 30) ));
			
			TaskSchedule result = target.addTaskScheduleDetail( newTask );
			
			assertThat( result.getDetails() )
				.extracting(
					d -> d.getTaskCode().v(),
					d -> d.getTimeSpan().getStart().hour(),
					d -> d.getTimeSpan().getStart().minute(),
					d -> d.getTimeSpan().getEnd().hour(),
					d -> d.getTimeSpan().getEnd().minute())
				.containsExactly(
					tuple( "code1", 14, 0, 15, 0),
					tuple( "code-new", 15, 0, 15, 30),
					tuple( "code1", 15, 30, 16, 0)
				);
		} 
		
		@Test
		public void testAddTaskScheduleDetail_after_duplicate_sameCode() {
			
			TaskScheduleDetail newTask = new TaskScheduleDetail(
					new TaskCode("code1"), 
					new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(15, 0), 
						TimeWithDayAttr.hourMinute(17, 0) ));
			
			TaskSchedule result = target.addTaskScheduleDetail( newTask );
			
			assertThat( result.getDetails() )
				.extracting(
					d -> d.getTaskCode().v(),
					d -> d.getTimeSpan().getStart().hour(),
					d -> d.getTimeSpan().getStart().minute(),
					d -> d.getTimeSpan().getEnd().hour(),
					d -> d.getTimeSpan().getEnd().minute())
				.containsExactly(
					tuple( "code1", 14, 0, 17, 0)
				);
		}
		
		@Test
		public void testAddTaskScheduleDetail_after_duplicate_differentCode() {
			
			TaskScheduleDetail newTask = new TaskScheduleDetail(
					new TaskCode("code-new"), 
					new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(15, 0), 
						TimeWithDayAttr.hourMinute(17, 0) ));
			
			TaskSchedule result = target.addTaskScheduleDetail( newTask );
			
			assertThat( result.getDetails() )
				.extracting(
					d -> d.getTaskCode().v(),
					d -> d.getTimeSpan().getStart().hour(),
					d -> d.getTimeSpan().getStart().minute(),
					d -> d.getTimeSpan().getEnd().hour(),
					d -> d.getTimeSpan().getEnd().minute())
				.containsExactly(
						tuple( "code1", 14, 0, 15, 0),
						tuple( "code-new", 15, 0, 17, 0)
				);
		} 
		
		@Test
		public void testAddTaskScheduleDetail_after_notDuplicate_sameCode1() {
			
			TaskScheduleDetail newTask = new TaskScheduleDetail(
					new TaskCode("code1"), 
					new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(16, 0), 
						TimeWithDayAttr.hourMinute(17, 0) ));
			
			TaskSchedule result = target.addTaskScheduleDetail( newTask );
			
			assertThat( result.getDetails() )
				.extracting(
					d -> d.getTaskCode().v(),
					d -> d.getTimeSpan().getStart().hour(),
					d -> d.getTimeSpan().getStart().minute(),
					d -> d.getTimeSpan().getEnd().hour(),
					d -> d.getTimeSpan().getEnd().minute())
				.containsExactly(
					tuple( "code1", 14, 0, 17, 0)
				);
		} 
		
		@Test
		public void testAddTaskScheduleDetail_after_notDuplicate_sameCode2() {
			
			TaskScheduleDetail newTask = new TaskScheduleDetail(
					new TaskCode("code1"), 
					new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(16, 1), 
						TimeWithDayAttr.hourMinute(17, 0) ));
			
			TaskSchedule result = target.addTaskScheduleDetail( newTask );
			
			assertThat( result.getDetails() )
				.extracting(
					d -> d.getTaskCode().v(),
					d -> d.getTimeSpan().getStart().hour(),
					d -> d.getTimeSpan().getStart().minute(),
					d -> d.getTimeSpan().getEnd().hour(),
					d -> d.getTimeSpan().getEnd().minute())
				.containsExactly(
					tuple( "code1", 14, 0, 16, 0),
					tuple( "code1", 16, 1, 17, 0)
				);
		} 
		
		@Test
		public void testAddTaskScheduleDetail_after_notDuplicate_differentCode() {
			
			TaskScheduleDetail newTask = new TaskScheduleDetail(
					new TaskCode("code-new"), 
					new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(16, 00), 
						TimeWithDayAttr.hourMinute(17, 00) ));
			
			TaskSchedule result = target.addTaskScheduleDetail( newTask );
			
			assertThat( result.getDetails() )
				.extracting(
					d -> d.getTaskCode().v(),
					d -> d.getTimeSpan().getStart().hour(),
					d -> d.getTimeSpan().getStart().minute(),
					d -> d.getTimeSpan().getEnd().hour(),
					d -> d.getTimeSpan().getEnd().minute())
				.containsExactly(
					tuple( "code1", 14, 0, 16, 0),
					tuple( "code-new", 16, 0, 17, 0)
					
				);
		} 
		
	}
	
	public static class TaskScheduleTest_addTaskScheduleDetail_Multi {
		
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
		
		@Test
		public void testAddTaskScheduleDetail_overAll() {
			
			List<TaskScheduleDetail> details = new ArrayList<>(Arrays.asList(
					new TaskScheduleDetail( new TaskCode("code1"), 
							new TimeSpanForCalc(
							TimeWithDayAttr.hourMinute(14, 0), 
							TimeWithDayAttr.hourMinute(15, 0))),
					new TaskScheduleDetail( new TaskCode("code2"), 
							new TimeSpanForCalc(
							TimeWithDayAttr.hourMinute(16, 0), 
							TimeWithDayAttr.hourMinute(17, 0)))
					));
			
			TaskSchedule taskSchedule = new TaskSchedule(details);
			
			TaskScheduleDetail newTask = new TaskScheduleDetail(
					new TaskCode("code-new"), 
					new TimeSpanForCalc(
						TimeWithDayAttr.hourMinute(13, 0), 
						TimeWithDayAttr.hourMinute(18, 0) ));
			
			TaskSchedule result = taskSchedule.addTaskScheduleDetail( newTask );
			
			assertThat( result.getDetails() )
				.extracting(
					d -> d.getTaskCode().v(),
					d -> d.getTimeSpan().getStart().hour(),
					d -> d.getTimeSpan().getStart().minute(),
					d -> d.getTimeSpan().getEnd().hour(),
					d -> d.getTimeSpan().getEnd().minute())
				.containsExactly(
					tuple( "code-new", 13, 0, 18, 0)
				);
		} 
	}
	
	public static class TaskScheduleTest_isTaskScheduleGranted {

		@Test
		public void test_false() {
			
			TaskSchedule taskSchedule = TaskSchedule.createWithEmptyList();
			
			boolean result = taskSchedule.isTaskScheduleGranted();
			
			assertThat(result).isFalse();
		}
		
		@Test
		public void test_true() {
			
			TaskSchedule taskSchedule = TaskSchedule.create(
					Arrays.asList(TaskScheduleDetailTestHelper.create("001", 8, 0, 9, 0)) );
			
			boolean result = taskSchedule.isTaskScheduleGranted();
			
			assertThat(result).isTrue();
		}
		
	}
	
	public static class TaskScheduleTest_isTaskScheduleGrantedIn {
		
		TaskSchedule target;
		
		@Before
		public void initTaskSchedule() {
			
			target = TaskSchedule.create(
					Arrays.asList(
						TaskScheduleDetailTestHelper.create("001", 8, 0, 9, 0),
						TaskScheduleDetailTestHelper.create("002", 10, 0, 11, 0))
					);
		}
		
		@Test
		public void test_false_detailIsEmpty() {
			
			TaskSchedule taskSchedule = TaskSchedule.createWithEmptyList();
			
			TimeSpanForCalc timeSpan = new TimeSpanForCalc(
					TimeWithDayAttr.hourMinute(10, 0), 
					TimeWithDayAttr.hourMinute(12, 0));
			
			boolean result = taskSchedule.isTaskScheduleGrantedIn(timeSpan);
			
			assertThat(result).isFalse();
		}
		
		@Test
		public void test_false_notGrantIn() {
			
			TimeSpanForCalc timeSpan = new TimeSpanForCalc(
					TimeWithDayAttr.hourMinute(9, 0), 
					TimeWithDayAttr.hourMinute(10, 0));
			
			boolean result = target.isTaskScheduleGrantedIn(timeSpan);
			
			assertThat(result).isFalse();
		}
		
		@Test
		public void test_true_notGrantIn_partly() {
			
			TimeSpanForCalc timeSpan = new TimeSpanForCalc(
					TimeWithDayAttr.hourMinute(8, 30), 
					TimeWithDayAttr.hourMinute(9, 30));
			
			boolean result = target.isTaskScheduleGrantedIn(timeSpan);
			
			assertThat(result).isTrue();
		}
		
		@Test
		public void test_true_notGrantIn_full() {
			
			TimeSpanForCalc timeSpan = new TimeSpanForCalc(
					TimeWithDayAttr.hourMinute(8, 0), 
					TimeWithDayAttr.hourMinute(11, 0) );
			
			boolean result = target.isTaskScheduleGrantedIn(timeSpan);
			
			assertThat(result).isTrue();
		}
		
	}
	
	public static class TaskScheduleTest_removeTaskScheduleDetailIn {
		
		TaskSchedule target;
		
		@Before
		public void initTaskSchedule() {
			
			target = TaskSchedule.create(
					Arrays.asList(
						TaskScheduleDetailTestHelper.create("001", 8, 0, 9, 0),
						TaskScheduleDetailTestHelper.create("002", 10, 0, 11, 0))
					);
		}
		
		@Test
		public void test_detailIsEmpty() {
			
			TaskSchedule taskSchedule = TaskSchedule.createWithEmptyList();
			
			TimeSpanForCalc timeSpan = new TimeSpanForCalc(
					TimeWithDayAttr.hourMinute(8, 0), 
					TimeWithDayAttr.hourMinute(10, 0) );
			
			TaskSchedule result = taskSchedule.removeTaskScheduleDetailIn(timeSpan);
			
			assertThat(result.getDetails()).isEmpty();
		}
		
		@Test
		public void test_removeAll_case1() {
			
			TimeSpanForCalc timeSpan = new TimeSpanForCalc(
					TimeWithDayAttr.hourMinute(8, 0),
					TimeWithDayAttr.hourMinute(11, 0) );
			
			TaskSchedule result = target.removeTaskScheduleDetailIn(timeSpan);
			
			assertThat(result.getDetails()).isEmpty();
		}
		
		@Test
		public void test_removeAll_case2() {
			
			TimeSpanForCalc timeSpan = new TimeSpanForCalc(
					TimeWithDayAttr.hourMinute(7, 0),
					TimeWithDayAttr.hourMinute(12, 0) );
			
			TaskSchedule result = target.removeTaskScheduleDetailIn(timeSpan);
			
			assertThat(result.getDetails()).isEmpty();
		}
		
		@Test
		public void test_removePartly_case1() {
			
			TimeSpanForCalc timeSpan = new TimeSpanForCalc(
					TimeWithDayAttr.hourMinute(8, 0), 
					TimeWithDayAttr.hourMinute(9, 0) );
			
			TaskSchedule result = target.removeTaskScheduleDetailIn(timeSpan);
			
			assertThat(result.getDetails())
				.extracting(
						e -> e.getTaskCode().v(),
						e -> e.getTimeSpan().getStart().rawHour(),
						e -> e.getTimeSpan().getStart().minute(),
						e -> e.getTimeSpan().getEnd().rawHour(),
						e -> e.getTimeSpan().getEnd().minute()
						)
				.containsExactly(
						tuple("002", 10, 0, 11, 0) );
		}
		
		@Test
		public void test_removePartly_case2() {
			
			TimeSpanForCalc timeSpan = new TimeSpanForCalc(
					TimeWithDayAttr.hourMinute(8, 30), 
					TimeWithDayAttr.hourMinute(9, 30) );
			
			TaskSchedule result = target.removeTaskScheduleDetailIn(timeSpan);
			
			assertThat(result.getDetails())
				.extracting(
						e -> e.getTaskCode().v(),
						e -> e.getTimeSpan().getStart().rawHour(),
						e -> e.getTimeSpan().getStart().minute(),
						e -> e.getTimeSpan().getEnd().rawHour(),
						e -> e.getTimeSpan().getEnd().minute()
						)
				.containsExactly(
						tuple("002", 10, 0, 11, 0) );
		}
		
		@Test
		public void test_removeNothingy() {
			
			TimeSpanForCalc timeSpan = new TimeSpanForCalc(
					TimeWithDayAttr.hourMinute(12, 0), 
					TimeWithDayAttr.hourMinute(13, 0) );
			
			TaskSchedule result = target.removeTaskScheduleDetailIn(timeSpan);
			
			assertThat(result.getDetails())
				.extracting(
						e -> e.getTaskCode().v(),
						e -> e.getTimeSpan().getStart().rawHour(),
						e -> e.getTimeSpan().getStart().minute(),
						e -> e.getTimeSpan().getEnd().rawHour(),
						e -> e.getTimeSpan().getEnd().minute()
						)
				.containsExactly(
						tuple("001", 8, 0, 9, 0),
						tuple("002", 10, 0, 11, 0) );
		}
		
	}
	
}
