package nts.uk.ctx.basic.dom.organization.employment;

import lombok.AllArgsConstructor;
import lombok.Value;

@Value
@AllArgsConstructor
public class PaydayProcessing {

	private final String companyCode;

	private ProcessingNo processingNo;

	private ProcessingName processingName;

	private final int dispSet;

	private final int currentProcessingYm;

	private final int bonusAtr;

	private final int bCurrentProcessingYm;
}
