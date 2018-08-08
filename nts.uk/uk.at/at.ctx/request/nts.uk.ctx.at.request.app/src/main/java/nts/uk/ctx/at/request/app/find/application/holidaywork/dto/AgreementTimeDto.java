package nts.uk.ctx.at.request.app.find.application.holidaywork.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.agreement.AgreementTimeImport;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AgreementTimeDto {
	
	/** 社員ID */
	private String employeeId;
	/** 確定情報 */
	private AgreeTimeOfMonthDto confirmed;
	/** 申請反映後情報 */
	private AgreeTimeOfMonthDto afterAppReflect;
	/** エラーメッセージ */
	private String errorMessage;
	
	public static AgreementTimeDto fromDomain(AgreementTimeImport agreementTimeImport){
		if(agreementTimeImport==null){
			return new AgreementTimeDto(
					null, 
					AgreeTimeOfMonthDto.fromDomain(null), 
					AgreeTimeOfMonthDto.fromDomain(null),
					null);
		}
		return new AgreementTimeDto(
				agreementTimeImport.getEmployeeId(), 
				AgreeTimeOfMonthDto.fromDomain(agreementTimeImport.getConfirmed().orElse(null)), 
				AgreeTimeOfMonthDto.fromDomain(agreementTimeImport.getAfterAppReflect().orElse(null)), 
				agreementTimeImport.getErrorMessage().orElse(null));
	}
	
}	
