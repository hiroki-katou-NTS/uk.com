package nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;
/**
 * 月別実績の36協定時間
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AgreeTimeOfMonthExport {
	/** 36協定時間 */
	@Setter
	private Integer agreementTime;
	/** 限度エラー時間 */
	private Integer limitErrorTime;
	/** 限度アラーム時間 */
	private Integer limitAlarmTime;
	/** 特例限度エラー時間 */
	private Optional<Integer> exceptionLimitErrorTime;
	/** 特例限度アラーム時間 */
	private Optional<Integer> exceptionLimitAlarmTime;
	/** 状態 */
	private Integer status;
}
