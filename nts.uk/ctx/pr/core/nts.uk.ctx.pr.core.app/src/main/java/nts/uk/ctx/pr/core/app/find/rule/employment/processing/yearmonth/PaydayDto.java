package nts.uk.ctx.pr.core.app.find.rule.employment.processing.yearmonth;

import lombok.Value;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.dom.rule.employment.processing.yearmonth.payday.Payday;

@Value
public class PaydayDto {

	String companyCode;

	int processingNo;

	int processingYm;

	GeneralDate payDate;

	GeneralDate stdDate;

	GeneralDate accountingClosing;

	int socialInsLevyMon;

	GeneralDate socialInsStdDate;

	GeneralDate incomeTaxStdDate;

	int neededWorkDay;

	GeneralDate empInsStdDate;

	int stmtOutputMon;

	public static PaydayDto fromDomain(Payday domain) {
		return new PaydayDto(domain.getCompanyCode().v(), domain.getProcessingNo().v(), domain.getProcessingYm().v(),
				domain.getPayDate(), domain.getStdDate(), domain.getAccountingClosing(),
				domain.getSocialInsLevyMon().v(), domain.getSocialInsStdDate(), domain.getIncomeTaxStdDate(),
				domain.getNeededWorkDay().v(), domain.getEmpInsStdDate(), domain.getStmtOutputMon().v());
	}
}
