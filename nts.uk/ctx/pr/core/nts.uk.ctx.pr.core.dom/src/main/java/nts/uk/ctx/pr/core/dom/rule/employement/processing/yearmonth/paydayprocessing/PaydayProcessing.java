package nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth.paydayprocessing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.enums.DisplayAtr;
import nts.uk.ctx.pr.core.dom.paymentdata.paymentdatemaster.ProcessingName;
import nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth.systemday.ProcessingNo;

@Getter
@AllArgsConstructor
public class PaydayProcessing extends AggregateRoot {

	private CompanyCode companyCode;

	private ProcessingNo processingNo;

	private ProcessingName processingName;

	private DisplayAtr dispSet;

	private CurrentProcessingYm currentProcessingYm;

	private BonusAtr bonusAtr;

	private BCurrentProcessingYm bCurrentProcessingYm;

	public static PaydayProcessing createSimpleFromJavaType(String ccd, int processingNo, String processingName,
			int displayAtr, int currentProcessingYm, int bonusAtr, int bCurrentProcessingYm) {

		return new PaydayProcessing(new CompanyCode(ccd), new ProcessingNo(processingNo),
				new ProcessingName(processingName), EnumAdaptor.valueOf(displayAtr, DisplayAtr.class),
				new CurrentProcessingYm(currentProcessingYm), EnumAdaptor.valueOf(bonusAtr, BonusAtr.class),
				new BCurrentProcessingYm(bCurrentProcessingYm));
	}
}
