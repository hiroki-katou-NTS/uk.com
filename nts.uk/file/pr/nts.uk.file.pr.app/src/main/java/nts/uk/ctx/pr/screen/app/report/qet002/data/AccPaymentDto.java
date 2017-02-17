package nts.uk.ctx.pr.screen.app.report.qet002.data;

import lombok.Data;

@Data
public class AccPaymentDto {

	private int targetYear;
	private boolean isLowerLimit;
	private boolean isUpperLimit;
	private int lowerLimitValue;	
	private int upperLimitValue;
	
	public AccPaymentDto(int targetYear,boolean isLowerLimit,boolean isUpperLimit,int lowerLimitValue,int upperLimitValue){
		this.targetYear = targetYear;
		this.isLowerLimit = isLowerLimit;
		this.isUpperLimit = isUpperLimit;
		this.lowerLimitValue = lowerLimitValue;		
		this.upperLimitValue=upperLimitValue;
	}
}
