package nts.uk.ctx.pr.core.app.find.rule.employement.processing.yearmonth;

import lombok.Value;
import nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth.paydayprocessing.PaydayProcessing;

@Value
public class PaydayProcessingDto {

	String companyCode;

	int processingNo;

	String processingName;

	int displayAtr;

	int currentProcessingYm;

	int bonusAtr;

	int bCurrentProcessingYm;

	public static PaydayProcessingDto fromDomain(PaydayProcessing domain) {
		return new PaydayProcessingDto(domain.getCompanyCode().v(), domain.getProcessingNo().v(),
				domain.getProcessingName().v(), domain.getDisplayAtr().value, domain.getCurrentProcessingYm().v(),
				domain.getBonusAtr().value, domain.getBCurrentProcessingYm().v());
	}
}
