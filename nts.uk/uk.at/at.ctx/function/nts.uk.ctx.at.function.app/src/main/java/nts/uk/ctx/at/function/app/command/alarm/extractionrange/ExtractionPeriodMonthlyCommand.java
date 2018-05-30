package nts.uk.ctx.at.function.app.command.alarm.extractionrange;

import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.NumberOfMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.PreviousClassification;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.EndMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.ExtractionPeriodMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.SpecifyStartMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.StartMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.YearSpecifiedType;

@Data
public class ExtractionPeriodMonthlyCommand {
	private String extractionId;

	private int extractionRange;

	private int unit;
	
	private int strSpecify;
	
	private int yearType;
	
	private int specifyMonth;
	
	private int strMonth;

	private int strCurrentMonth;

	private int strPreviousAtr;

	private int endSpecify;
	
	private int extractPeriod;

	private int endMonth;

	private int endCurrentMonth;
	
	private int endPreviousAtr;
	
	public ExtractionPeriodMonth toDomain() {
		
		if(this.extractionId == null || this.extractionId.equals("")){
			this.extractionId = IdentifierUtil.randomUniqueId();
		}
		if(this.strMonth==0) this.strCurrentMonth=1;
		if(this.endMonth ==0) this.endCurrentMonth =1;
		
		StartMonth startMonth = new StartMonth(strSpecify);
		
		if(this.strSpecify==SpecifyStartMonth.DESIGNATE_CLOSE_START_MONTH.value) {
			startMonth.setStartMonth(EnumAdaptor.valueOf(strPreviousAtr, PreviousClassification.class), strMonth, strCurrentMonth ==1);
		}else {
			startMonth.setFixedMonth(EnumAdaptor.valueOf(yearType, YearSpecifiedType.class), specifyMonth);
		}
		
		EndMonth endMonth = new EndMonth(endSpecify, extractPeriod);
		endMonth.setEndMonthNo(EnumAdaptor.valueOf(endPreviousAtr, PreviousClassification.class), this.endMonth, endCurrentMonth==1);
		
		return new ExtractionPeriodMonth(this.extractionId, this.extractionRange, startMonth, endMonth, EnumAdaptor.valueOf(this.unit, NumberOfMonth.class));
	}
}
