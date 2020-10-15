package nts.uk.ctx.at.request.dom.applicationreflect.service.workrecord;

import java.util.Map;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.request.dom.application.ReasonNotReflectDaily;
import nts.uk.ctx.at.request.dom.application.ReflectedState_New;
import nts.uk.ctx.at.request.dom.application.overtime.OverTimeAtr;
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
	private ReasonNotReflectDaily reasonNotReflect;
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
	/**
	 * 残業申請．残業時間（10枠）
	 */
	Map<Integer, Integer> mapOvertimeFrame;
	
	/**
	 * 就業時間外深夜時間
	 */
	private Integer overTimeShiftNight;
	/**
	 * フレックス超過時間
	 */
	private Integer flexExessTime;
	/**
	 * 残業区分
	 */
	private OverTimeAtr overTimeAtr;
	/**
	 * 申請理由
	 */
	private String appReason;
}
