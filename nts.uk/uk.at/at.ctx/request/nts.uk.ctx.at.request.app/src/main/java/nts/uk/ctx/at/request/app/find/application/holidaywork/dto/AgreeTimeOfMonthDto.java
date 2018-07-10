package nts.uk.ctx.at.request.app.find.application.holidaywork.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreeTimeOfMonthExport;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AgreeTimeOfMonthDto {
	/** 36協定時間 */
	private Integer agreementTime;
	/** 限度エラー時間 */
	private Integer limitErrorTime;
	/** 限度アラーム時間 */
	private Integer limitAlarmTime;
	/** 特例限度エラー時間 */
	private Integer exceptionLimitErrorTime;
	/** 特例限度アラーム時間 */
	private Integer exceptionLimitAlarmTime;
	/** 状態 */
	private Integer status;
	
	public static AgreeTimeOfMonthDto fromDomain(AgreeTimeOfMonthExport agreeTimeOfMonthExport){
		if(agreeTimeOfMonthExport==null){
			return new AgreeTimeOfMonthDto(null, null, null, null, null, null);
		}
		return new AgreeTimeOfMonthDto(
				agreeTimeOfMonthExport.getAgreementTime(), 
				agreeTimeOfMonthExport.getLimitErrorTime(), 
				agreeTimeOfMonthExport.getLimitAlarmTime(), 
				agreeTimeOfMonthExport.getExceptionLimitErrorTime().orElse(null), 
				agreeTimeOfMonthExport.getExceptionLimitAlarmTime().orElse(null), 
				agreeTimeOfMonthExport.getStatus());
	}
}
