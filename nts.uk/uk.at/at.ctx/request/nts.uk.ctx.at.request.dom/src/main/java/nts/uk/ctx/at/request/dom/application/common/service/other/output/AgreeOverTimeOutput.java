package nts.uk.ctx.at.request.dom.application.common.service.other.output;

import nts.arc.time.YearMonth;

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
public class AgreeOverTimeOutput {
	
	private AgreementTimeImport detailCurrentMonth;
	
	private AgreementTimeImport detailNextMonth;
	
	private YearMonth currentMonth;
	
	private YearMonth nextMonth;
}
