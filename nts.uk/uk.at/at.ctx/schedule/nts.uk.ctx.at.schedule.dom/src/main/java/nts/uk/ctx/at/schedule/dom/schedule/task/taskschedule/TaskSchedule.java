package nts.uk.ctx.at.schedule.dom.schedule.task.taskschedule;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.stream.Collectors;

import lombok.Value;
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
		
		if ( TaskSchedule.checkDuplicate(details) ) {
			throw new RuntimeException("Creating Task Schedule: The time spans of task schedule details is duplicated!!");
		}
		
		Collections.sort(details);
		details = TaskSchedule.mergeTaskScheduleDetail(details);
		
		return new TaskSchedule(details);
	}
	
	/**
	 * 重複チェックする
	 * @param details 対象の詳細リスト
	 * @return 
	 * true: if any time spans in list 'details' is duplicate 対象の詳細リストの中の要素が重複
	 * false: all time spans in list 'details' are not duplicate 対象の詳細リストの中の要素が全て非重複  
	 */
	private static boolean checkDuplicate(List<TaskScheduleDetail> details) {
		
		List<TimeSpanForCalc> timeSpans = details.stream()
				.map(d -> d.getTimeSpan())
				.collect(Collectors.toList());
		
		AtomicInteger index = new AtomicInteger(0);
		
		return timeSpans.stream().anyMatch( base -> {
			return timeSpans.subList(index.incrementAndGet(), timeSpans.size()).stream()
					.anyMatch( target -> base.checkDuplication( target ).isDuplicated() );
		});
	}
	
	/**
	 * マージ
	 * @param details 対象の詳細リスト
	 * @param startIndex チェックのスタート位置
	 * @return
	 */
	private static List<TaskScheduleDetail> mergeTaskScheduleDetail(List<TaskScheduleDetail> details) {
		
		List<List<TaskScheduleDetail>> listOfListDetail = new ArrayList<>();
		
		details.forEach( detail -> {
			
			// listOfListDetail is empty
			if ( listOfListDetail.isEmpty() ) {
				
				listOfListDetail.add( new ArrayList<>( Arrays.asList( detail ) ) );
				return;
			}
			
			List<TaskScheduleDetail> theLastList = listOfListDetail.get( listOfListDetail.size() - 1 );
			
			// the task code is not same
			if ( !detail.getTaskCode().equals( 
					theLastList.get(0).getTaskCode()) ) {
				
				listOfListDetail.add( new ArrayList<>( Arrays.asList( detail ) ) );
				return;
			}
			
			// the periods are not continuous
			if ( !detail.getTimeSpan().getStart().equals( 
					theLastList.get( theLastList.size() -1 ).getTimeSpan().getEnd() ) ) {
				
				listOfListDetail.add( new ArrayList<>( Arrays.asList( detail ) ) );
				return;
			}
			
			// the task code is same and the periods are continuous
			theLastList.add(detail);
		});
			
		
		return listOfListDetail.stream().map( list -> 
			new TaskScheduleDetail(
					list.get(0).getTaskCode(),
					new TimeSpanForCalc(
							list.get(0).getTimeSpan().getStart(),
							list.get( list.size() - 1).getTimeSpan().getEnd() ))
		).collect(Collectors.toList());
		
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

}
