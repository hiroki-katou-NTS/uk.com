package nts.uk.ctx.at.schedule.dom.schedule.task.taskschedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Value;
import lombok.val;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;

/**
 * 作業予定
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.作業.作業予定.作業予定
 * @author dan_pv
 *
 */
@Value
public class TaskSchedule implements DomainValue {
	
	/**
	 * 詳細リスト
	 */
	private final List<TaskScheduleDetail> details;
	
	/**
	 * 作業なしで作る
	 * @return
	 */
	public static TaskSchedule createWithEmptyList() {
		return new TaskSchedule( new ArrayList<>() );
	}
	
	/**
	 * 作る
	 * @param details 詳細リスト
	 * @return
	 */
	public static TaskSchedule create(List<TaskScheduleDetail> details) {
		
		TaskScheduleDetailList detailList = new TaskScheduleDetailList(details);
		
		if ( detailList.checkDuplicate() ) {
			throw new RuntimeException("Creating Task Schedule: The time spans of task schedule details is duplicated!!");
		}
		
		detailList.sort();
		detailList.merge();
		
		return new TaskSchedule( detailList.getDetails() );
	}
	
	/**
	 * 作業予定詳細を追加する
	 * @param newDetail 対象の作業予定詳細
	 * @return
	 */
	public TaskSchedule addTaskScheduleDetail(TaskScheduleDetail newDetail) {
		
		List<TaskScheduleDetail> notDuplicatedDetailList = this.details.stream()
				.map( detail -> detail.getNotDuplicatedWith( newDetail.getTimeSpan() ))
				.flatMap( detailList -> detailList.stream() )
				.collect(Collectors.toList());
		
		notDuplicatedDetailList.add( newDetail );
		return TaskSchedule.create(notDuplicatedDetailList);
	}
	
	/**
	 * 作業が付与されているか
	 * @return
	 */
	public boolean isTaskScheduleGranted() {
		
		return !this.details.isEmpty();
	}
	
	/**
	 * 指定時間帯内に作業が付与されているか
	 * @param timeSpan 時間帯
	 * @return
	 */
	public boolean isTaskScheduleGrantedIn(TimeSpanForCalc timeSpan) {
		
		return this.details.stream()
				.anyMatch(detail -> detail.isDuplicateWith(timeSpan));
	}
	
	/**
	 * 指定時間帯に重複する作業を削除する
	 * @param timeSpan 時間帯
	 * @return
	 */
	public TaskSchedule removeTaskScheduleDetailIn(TimeSpanForCalc timeSpan) {
		
		val tasksWhichAreNotDuplicated = this.details.stream()
				.filter(detail -> ! detail.isDuplicateWith(timeSpan))
				.collect(Collectors.toList());
		
		return TaskSchedule.create(tasksWhichAreNotDuplicated);
	}
	
	@AllArgsConstructor
	private static class TaskScheduleDetailList {
		
		@Getter
		private List<TaskScheduleDetail> details;
		
		/**
		 * initial TaskScheduleDetailList with one element
		 * @param detail
		 * @return
		 */
		private static TaskScheduleDetailList init( TaskScheduleDetail detail ) {
			return new TaskScheduleDetailList( new ArrayList<>( Arrays.asList( detail ) ) );
		}
		
		/**
		 * return the first element of @details
		 * @return
		 */
		public TaskScheduleDetail first() {
			return details.get(0);
		}
		
		/**
		 * return the last element of @details
		 * @return
		 */
		public TaskScheduleDetail last() {
			return details.get( details.size() - 1 );
		}
		
		/**
		 * add a new element to @details
		 * @param e
		 */
		public void add(TaskScheduleDetail e) {
			this.details.add(e);
		}
		
		/**
		 * 重複チェックする
		 * true: if any time spans in @details is duplicate 対象の詳細リストの中の要素が重複
		 * false: all time spans in @details are not duplicate 対象の詳細リストの中の要素が全て非重複  
		 */
		public boolean checkDuplicate() {
			
			List<TimeSpanForCalc> timeSpans = this.details.stream()
					.map(d -> d.getTimeSpan())
					.collect(Collectors.toList());
			
			AtomicInteger index = new AtomicInteger(0);
			
			return timeSpans.stream().anyMatch( base -> {
				return timeSpans.subList(index.incrementAndGet(), timeSpans.size()).stream()
						.anyMatch( target -> base.checkDuplication( target ).isDuplicated() );
			});
		}
		
		/**
		 * sort @details by start-time
		 */
		public void sort() {
			Collections.sort(this.details);
		}
		
		/**
		 * マージ
		 * merge @details if 2 element are same task code and continuous
		 * @return
		 */
		public void merge() {
			
			List<TaskScheduleDetailList> listOfDetailList = new ArrayList<>();
			
			this.details.forEach( detail -> {
				
				if ( listOfDetailList.isEmpty() ) {
					
					listOfDetailList.add( TaskScheduleDetailList.init(detail) );
					return;
				}
				
				TaskScheduleDetailList theLastList = listOfDetailList.get( listOfDetailList.size() - 1 );
				
				if ( detail.isContinuousTask( theLastList.last())) {
					theLastList.add(detail);
				} else {
					listOfDetailList.add( TaskScheduleDetailList.init(detail) );
				}
				
			});
			
			this.details =  listOfDetailList.stream().map( list -> 
				new TaskScheduleDetail(
						list.first().getTaskCode(),
						new TimeSpanForCalc(
								list.first().getTimeSpan().getStart(),
								list.last().getTimeSpan().getEnd() ))
			).collect(Collectors.toList());
			
		}
		
	}

}
