package nts.uk.ctx.at.request.app.command.application.overtime;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.NoArgsConstructor;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreeTimeOfMonthExport;

@AllArgsConstructor
@NoArgsConstructor
public class AgreeTimeOfMonthCommand {
	/** 36協定時間 */
	public Integer agreementTime;
	/** 限度エラー時間 */
	public Integer limitErrorTime;
	/** 限度アラーム時間 */
	public Integer limitAlarmTime;
	/** 特例限度エラー時間 */
	public Integer exceptionLimitErrorTime;
	/** 特例限度アラーム時間 */
	public Integer exceptionLimitAlarmTime;
	/** 状態 */
	public Integer status;
	
	public AgreeTimeOfMonthExport toDomain() {
		return new AgreeTimeOfMonthExport(
				agreementTime,
				limitErrorTime,
				limitAlarmTime,
				Optional.ofNullable(exceptionLimitErrorTime),
				Optional.ofNullable(exceptionLimitAlarmTime),
				status);
	}
}
