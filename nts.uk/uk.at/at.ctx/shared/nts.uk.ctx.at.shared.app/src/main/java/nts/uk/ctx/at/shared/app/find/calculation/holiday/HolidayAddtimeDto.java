package nts.uk.ctx.at.shared.app.find.calculation.holiday;

import java.math.BigDecimal;

import lombok.Data;
import nts.arc.time.GeneralDate;
@Data
public class HolidayAddtimeDto {
	/** 会社ID */
	private String companyId;

	/** 会社単位の休暇時間を参照する */
	private int referComHolidayTime;

	/** 1日 */
	private BigDecimal oneDay;

	/** 午前 */
	private BigDecimal morning;

	/** 午後 */
	private BigDecimal afternoon;

	/** 実績の就業時間帯を参照する */
	private int referActualWorkHours;

	/** 実績を参照しない場合の参照先 */
	private int notReferringAch;

	/** 年休 */
	private int annualHoliday;

	/** 特別休暇 */
	private int specialHoliday;

	/** 積立年休 */
	private int yearlyReserved;

	private RegularWorkDto regularWork;

	private FlexWorkDto flexWork;

	private IrregularWorkDto irregularWork;
}
