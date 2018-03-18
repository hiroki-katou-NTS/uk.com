package nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.overtime;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ReasonNotReflectRecord;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.appreflect.ReflectedStateRecord;
@AllArgsConstructor
@Getter
@Setter
public class OvertimeAppParameter {
	/**
	 * 反映状態
	 */
	private ReflectedStateRecord reflectedState;
	/**
	 * 反映不可理由
	 */
	private ReasonNotReflectRecord reasonNotReflect;
	/**
	 * 勤務種類コード
	 */
	private String workTypeCode;
			
	/**就業時間帯コード	 */
	private String workTimeCode;

}
