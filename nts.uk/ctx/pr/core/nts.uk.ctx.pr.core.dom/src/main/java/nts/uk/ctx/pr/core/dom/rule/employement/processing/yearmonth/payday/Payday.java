package nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth.payday;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.paymentdata.ProcessingNo;

@AllArgsConstructor
public class Payday extends AggregateRoot {
	@Getter
	private CompanyCode companyCode;

	@Getter
	private ProcessingNo processingNo;

	@Getter
	private YearMonth processingYm;

	@Getter
	private GeneralDate payDate;

	@Getter
	private GeneralDate stdDate;// baseDate

	@Getter
	private AccountingClosing accountingClosing;

	@Getter
	private SocialInsLevyMon socialInsLevyMon;

	@Getter
	private GeneralDate socialInsStdDate;

	@Getter
	private GeneralDate incomeTaxStdDate;

	@Getter
	private NeededWorkDay neededWorkDay;

	@Getter
	private GeneralDate empInsStdDate;

	@Getter
	private StmtOutputMon stmtOutputMon;

	public static Payday createSimpleFromJavaType(String companyCode, int processingNo, int processingYm,
			GeneralDate payDate, GeneralDate stdDate, int accountingClosing, int socialInsLevyMon,
			GeneralDate socialInsStdDate, GeneralDate incomeTaxStdDate, int neededWorkDay, GeneralDate empInsStdDate,
			int stmtOutputMon) {

		return new Payday(new CompanyCode(companyCode), new ProcessingNo(processingNo), YearMonth.of(processingYm),
				payDate, stdDate, new AccountingClosing(accountingClosing), new SocialInsLevyMon(socialInsLevyMon),
				socialInsStdDate, incomeTaxStdDate, new NeededWorkDay(neededWorkDay), empInsStdDate,
				new StmtOutputMon(stmtOutputMon));
	}
}
