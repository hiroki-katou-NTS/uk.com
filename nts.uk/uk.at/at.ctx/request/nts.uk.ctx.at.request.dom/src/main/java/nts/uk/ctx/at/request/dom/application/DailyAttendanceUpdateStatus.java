package nts.uk.ctx.at.request.dom.application;

import java.util.Optional;

import lombok.Getter;
import nts.arc.time.GeneralDateTime;

/**
 * refactor 4
 * UKDesign.ドメインモデル."NittsuSystem.UniversalK".就業.contexts.申請承認.申請.日別勤怠の更新状態
 * @author Doan Duy Hung
 *
 */
@Getter
public class DailyAttendanceUpdateStatus {
	
	/**
	 * 実績反映日時
	 */
	private Optional<GeneralDateTime> actualResultDateTime;
	
	/**
	 * 予定反映日時
	 */
	private Optional<GeneralDateTime> scheduleReflectionDateTime;
	
	/**
	 * 実績反映不可理由
	 */
	private Optional<ReasonNotReflectDaily> opReasonResultsCantReflected;
	
	/**
	 * 予定反映不可理由
	 */
	private Optional<ReasonNotReflect> opReasonScheduleCantReflected;
	
}
