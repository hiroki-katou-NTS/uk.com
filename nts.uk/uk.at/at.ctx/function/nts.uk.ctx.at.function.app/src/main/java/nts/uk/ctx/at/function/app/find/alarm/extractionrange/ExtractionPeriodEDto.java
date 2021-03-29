package nts.uk.ctx.at.function.app.find.alarm.extractionrange;

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
public class ExtractionPeriodEDto {

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
	public void fromDomain(ExtractionPeriodMonth domain) {
		this.extractionId = domain.getExtractionId();
		this.strSpecify = domain.getStartMonth().getSpecifyStartMonth().value;
		this.endSpecify = domain.getEndMonth().getSpecifyEndMonth().value;

		if (this.strSpecify == SpecifyStartMonth.SPECIFY_FIXED_MOON_DEGREE.value) {
			// E3_4 & E3_5
			this.strSpecifyMonth = domain.getStartMonth().getFixedMonthly().get().getDesignatedMonth();
			this.strYearSpecifiedType = domain.getStartMonth().getFixedMonthly().get().getYearSpecifiedType().value;
		} else {
			// E3_7
			this.strMonth = domain.getStartMonth().getStrMonthNo().get().getMonthNo();
		}
		
		if (this.endSpecify == SpecifyEndMonth.SPECIFY_PERIOD_FROM_START_MONTH.value) {
			// E4_5
			this.endFromStrMonth = domain.getEndMonth().getExtractFromStartMonth().value;
		} else {
			// E4_6
			this.endMonth = domain.getEndMonth().getEndMonthNo().get().getMonthNo();
		}
	}
}
