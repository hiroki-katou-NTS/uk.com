package nts.uk.ctx.at.function.app.command.alarm.extractionrange;

import lombok.Data;
import nts.arc.enums.EnumAdaptor;
import nts.gul.text.IdentifierUtil;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.NumberOfMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.PreviousClassification;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.daily.StartSpecify;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.EndMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.ExtractionPeriodMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.SpecifyEndMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.SpecifyStartMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.StartMonth;
import nts.uk.ctx.at.function.dom.alarm.extractionrange.month.YearSpecifiedType;

/**
 * 期間選択（年間）
 *
 */
@Data
public class ExtractionPeriodECommand {

	private String extractionId;

	private int extractionRange;

	private int strSpecify;

	private Integer strSpecifyMonth;

	private Integer strMonth;

	private Integer strCurrentMonth;

	private Integer strYearSpecifiedType;

	private int endSpecify;

	private Integer endMonth;

	private Integer endFromStrMonth;

	private Integer endCurrentMonth;
	
	/**
	 * Save E3_4, E3_5, E3_7, E4_6
	 * @return
	 */
	public ExtractionPeriodMonth toDomainExtractionPeriodMonth() {
		if (this.extractionId == null || this.extractionId.equals("")) {
			this.extractionId = IdentifierUtil.randomUniqueId();
		}
		
		StartMonth startMonth = new StartMonth(SpecifyStartMonth.DESIGNATE_CLOSE_START_MONTH.value);

		if (this.strSpecify == SpecifyStartMonth.SPECIFY_FIXED_MOON_DEGREE.value) {
			startMonth = new StartMonth(SpecifyStartMonth.SPECIFY_FIXED_MOON_DEGREE.value);
			
			// E3_4 & E3_5
			startMonth.setFixedMonth(EnumAdaptor.valueOf(strYearSpecifiedType, YearSpecifiedType.class), strSpecifyMonth);
		} else {
			if (this.strMonth == 0) {
				this.strCurrentMonth = 1;
			}
			
			// E3_7
			startMonth.setStartMonth(PreviousClassification.BEFORE, strMonth, strCurrentMonth == 1);
		}
		
		EndMonth endMonth = new EndMonth(endSpecify, 12);
		if (this.endSpecify == SpecifyEndMonth.SPECIFY_PERIOD_FROM_START_MONTH.value) {
			// E4_5
			endMonth = new EndMonth(SpecifyEndMonth.SPECIFY_PERIOD_FROM_START_MONTH.value, endFromStrMonth);
		} else {
			if (this.endMonth == 0) {
				this.endCurrentMonth = 1;
			}
			
			// E4_6
			endMonth.setEndMonthNo(PreviousClassification.BEFORE, this.endMonth, endCurrentMonth == 1);
		}

		return new ExtractionPeriodMonth(this.extractionId, this.extractionRange, startMonth, endMonth, NumberOfMonth.OTHER);
	}
}
