package nts.uk.ctx.at.request.app.find.application.holidaywork.dto;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AgreeOverTimeOutput;
/**
 * 
 * @author Doan Duy Hung
 *
 */
@AllArgsConstructor
@Getter
public class AgreeOverTimeDto {
	
	private AgreementTimeDto detailCurrentMonth;
	
	private AgreementTimeDto detailNextMonth;
	
	private String currentMonth;
	
	private String nextMonth;
	
	public static AgreeOverTimeDto fromDomain(AgreeOverTimeOutput agreementTimeOutput){
		int currentYear = agreementTimeOutput.getCurrentMonth().year();
		int currentMonth = agreementTimeOutput.getCurrentMonth().month();
		int nextYear = agreementTimeOutput.getNextMonth().year();
		int nextMonth = agreementTimeOutput.getNextMonth().month();
		String currentYearMonth = GeneralDate.ymd(currentYear, currentMonth, 1).toString("yyyy/MM");
		String nextYearMonth = GeneralDate.ymd(nextYear, nextMonth, 1).toString("yyyy/MM");
		return new AgreeOverTimeDto(
				AgreementTimeDto.fromDomain(agreementTimeOutput.getDetailCurrentMonth()),
				AgreementTimeDto.fromDomain(agreementTimeOutput.getDetailNextMonth()), 
				currentYearMonth, 
				nextYearMonth);
	}
	
}
