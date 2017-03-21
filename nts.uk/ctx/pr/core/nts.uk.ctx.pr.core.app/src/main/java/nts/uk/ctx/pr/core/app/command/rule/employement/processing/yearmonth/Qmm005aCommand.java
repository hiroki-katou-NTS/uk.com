package nts.uk.ctx.pr.core.app.command.rule.employement.processing.yearmonth;

import lombok.Getter;
import nts.uk.ctx.pr.core.dom.rule.employement.processing.yearmonth.paydayprocessing.PaydayProcessing;

@Getter
public class Qmm005aCommand {
	
	private int processingNo;
	
	private String processingName;
	
	private int dispSet;
	
	private int currentProcessingYm;
	
	private int bonusAtr;
	
	private int bCurrentProcessingYm;

	public PaydayProcessing toDomain(String companyCode) {
		return PaydayProcessing.createSimpleFromJavaType(companyCode, getProcessingNo(), getProcessingName(),
				getDispSet(), getCurrentProcessingYm(), getBonusAtr(), getBCurrentProcessingYm());
	}
	
}
