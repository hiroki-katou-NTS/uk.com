package nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.ReasonNotReflect_New;
import nts.uk.ctx.at.request.dom.application.ReflectedState_New;
@AllArgsConstructor
@Setter
@Getter
public class OvertimeAppParameter {
	/**
	 * 反映状態
	 */
	private ReflectedState_New reflectedState;
	/**
	 * 反映不可理由
	 */
	private ReasonNotReflect_New reasonNotReflect;
	/**
	 * 勤務種類コード
	 */
	private String workTypeCode;
			
	/**就業時間帯コード	 */
	private String workTimeCode;
	/**
	 * 開始時刻１
	 */
	private Integer startTime1;
	/**
	 * 終了時刻１
	 */
	private Integer endTime1;
	/**
	 * 開始時刻２
	 */
	private Integer startTime2;
	/**
	 * 終了時刻２
	 */
	private Integer endTime2;
}
