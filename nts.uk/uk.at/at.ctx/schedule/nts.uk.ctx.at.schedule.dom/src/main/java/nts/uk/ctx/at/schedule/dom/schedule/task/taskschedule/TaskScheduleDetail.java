package nts.uk.ctx.at.schedule.dom.schedule.task.taskschedule;

import java.util.List;
import java.util.stream.Collectors;

import lombok.Value;
import nts.arc.layer.dom.objecttype.DomainValue;
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
	
	/**
	 * 時間帯が重複するか
	 * @param other 対象の作業予定詳細
	 * @return
	 */
	public boolean isTimeSpanDuplicated(TaskScheduleDetail other) {
		return this.timeSpan.checkDuplication( other.getTimeSpan() ).isDuplicated();
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
}
