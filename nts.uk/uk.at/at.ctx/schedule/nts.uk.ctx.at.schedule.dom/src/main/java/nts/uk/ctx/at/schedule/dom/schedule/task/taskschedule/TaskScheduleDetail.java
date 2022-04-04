package nts.uk.ctx.at.schedule.dom.schedule.task.taskschedule;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import lombok.val;
import nts.arc.layer.dom.objecttype.DomainValue;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanDuplication;
import nts.uk.ctx.at.shared.dom.common.time.TimeSpanForCalc;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskframe.TaskFrameNo;
import nts.uk.ctx.at.shared.dom.scherec.taskmanagement.taskmaster.TaskCode;

/**
 * 作業予定詳細
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.就業.contexts.勤務予定.勤務予定.作業.作業予定.作業予定詳細
 * @author dan_pv
 *
 */
@Value
public class TaskScheduleDetail implements DomainValue, Comparable<TaskScheduleDetail> {
	
	/**
	 * 作業コード
	 */
	private final TaskCode taskCode;
	
	/**
	 * 時間帯
	 */
	private final TimeSpanForCalc timeSpan;
	
	/**
	 * 作業枠NOを取得する
	 * @return
	 */
	public TaskFrameNo getTaskFrameNo() {
		return new TaskFrameNo(1);
	}
	
	@Override
	public int compareTo(TaskScheduleDetail other) {
		return this.timeSpan.getStart().compareTo( other.getTimeSpan().getStart() );
	}
	
	/**
	 * 対象の時間帯と重複してない部分を取得する
	 * @param otherTimeSpan 対象の時間帯
	 * @return
	 */
	public List<TaskScheduleDetail> getNotDuplicatedWith(TimeSpanForCalc otherTimeSpan) {
		
		return this.timeSpan.getNotDuplicationWith(otherTimeSpan).stream()
				.map( notDuplicatedTimeSpan -> new TaskScheduleDetail(this.taskCode, notDuplicatedTimeSpan ))
				.collect(Collectors.toList());
	}
	
	/**
	 * 連続した作業か
	 * @param other
	 * @return
	 */
	public boolean isContinuousTask(TaskScheduleDetail other) {
		
		return this.getTaskCode().equals(other.getTaskCode()) &&
				this.getTimeSpan().getStart().equals(other.getTimeSpan().getEnd());
	}
	
	/**
	 * 指定時間帯と重複するか
	 * @param otherTimeSpan 時間帯
	 * @return
	 */
	public boolean isDuplicateWith(TimeSpanForCalc otherTimeSpan) {
		
		return this.timeSpan.checkDuplication(otherTimeSpan) != TimeSpanDuplication.NOT_DUPLICATE;
	}
	
	/**
	 * 指定時間帯に収まるか
	 * @param targetTimeSpan 時間帯
	 * @return
	 */
	public boolean isContainedIn(TimeSpanForCalc targetTimeSpan) {
		
		val duplicateCheckResult =  this.timeSpan.checkDuplication(targetTimeSpan);
		
		return duplicateCheckResult == TimeSpanDuplication.SAME_SPAN
				|| duplicateCheckResult == TimeSpanDuplication.CONTAINED;
	}
}
