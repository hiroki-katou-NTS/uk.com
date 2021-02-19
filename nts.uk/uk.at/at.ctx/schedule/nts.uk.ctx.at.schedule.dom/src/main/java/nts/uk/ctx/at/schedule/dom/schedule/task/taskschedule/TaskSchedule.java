package nts.uk.ctx.at.schedule.dom.schedule.task.taskschedule;

import java.util.ArrayList;
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
		details = TaskSchedule.mergeTaskScheduleDetail(details, 0);
		
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
	private static List<TaskScheduleDetail> mergeTaskScheduleDetail(List<TaskScheduleDetail> details, int startIndex) {
		
		for ( int index = startIndex; index < details.size() - 1; index++) {
			
			TaskScheduleDetail indexTask= details.get(index);
			TaskScheduleDetail indexPlus1Task= details.get(index + 1);
			
			if ( indexTask.getTaskCode().equals( indexPlus1Task.getTaskCode() ) 
					&& indexTask.getTimeSpan().getEnd().equals( indexPlus1Task.getTimeSpan().getStart())) {
				
				TaskScheduleDetail mergedTask = new TaskScheduleDetail(
						indexTask.getTaskCode(), 
						new TimeSpanForCalc(
								indexTask.getTimeSpan().getStart(), 
								indexPlus1Task.getTimeSpan().getEnd()) );
				
				List<TaskScheduleDetail> beforeIndex = details.subList(0, index);
				List<TaskScheduleDetail> afterIndexPlus1 = details.subList(index + 2, details.size());
				
				List<TaskScheduleDetail> newList = new ArrayList<>();
				newList.addAll(beforeIndex);
				newList.add(mergedTask);
				newList.addAll(afterIndexPlus1);
				
				return TaskSchedule.mergeTaskScheduleDetail(newList, index);
			}
		}
		
		return details;
		
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
