package nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth.paydayprocessing;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.core.dom.company.CompanyCode;
import nts.uk.ctx.pr.core.dom.enums.DisplayAtr;
import nts.uk.ctx.pr.core.dom.paymentdata.ProcessingNo;
import nts.uk.ctx.pr.core.dom.paymentdata.paymentdatemaster.ProcessingName;

@AllArgsConstructor
public class PaydayProcessing extends AggregateRoot {

	@Getter
	private CompanyCode companyCode;

	@Getter
	private ProcessingNo processingNo;

	@Getter
	private ProcessingName processingName;

	@Getter
	private DisplayAtr displayAtr;

	@Getter
	private CurrentProcessingYm currentProcessingYm;

	@Getter
	private BonusAtr bonusAtr;

	@Getter
	private BCurrentProcessingYm bCurrentProcessingYm;

	public static PaydayProcessing createSimpleFromJavaType(String ccd, int processingNo, String processingName,
			int displayAtr, int currentProcessingYm, int bonusAtr, int bCurrentProcessingYm) {

		return new PaydayProcessing(new CompanyCode(ccd), new ProcessingNo(processingNo),
				new ProcessingName(processingName), EnumAdaptor.valueOf(displayAtr, DisplayAtr.class),
				new CurrentProcessingYm(currentProcessingYm), EnumAdaptor.valueOf(bonusAtr, BonusAtr.class),
				new BCurrentProcessingYm(bCurrentProcessingYm));
	}
}
