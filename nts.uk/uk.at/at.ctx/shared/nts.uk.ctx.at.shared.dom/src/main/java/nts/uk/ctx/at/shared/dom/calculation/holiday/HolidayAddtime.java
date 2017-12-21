package nts.uk.ctx.at.shared.dom.calculation.holiday;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.arc.time.GeneralDate;

@AllArgsConstructor
@Getter
public class HolidayAddtime extends AggregateRoot {

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
	private NotReferringAchAtr notReferringAch;

	/** 年休 */
	private int annualHoliday;

	/** 特別休暇 */
	private int specialHoliday;

	/** 積立年休 */
	private int yearlyReserved;

	private RegularWork regularWork;

	private FlexWork flexWork;

	private IrregularWork irregularWork;

	@Override
	public void validate() {
		super.validate();
	}

	public static HolidayAddtime createFromJavaType(String companyId, int referComHolidayTime, BigDecimal oneDay,
			BigDecimal morning, BigDecimal afternoon, int referActualWorkHours, int notReferringAch,
			int annualHoliday, int specialHoliday, int yearlyReserved, RegularWork regularWork, FlexWork flexWork,
			IrregularWork irregularWork) {
		return new HolidayAddtime(companyId, referComHolidayTime, oneDay, morning, afternoon, referActualWorkHours,
				EnumAdaptor.valueOf(notReferringAch, NotReferringAchAtr.class), annualHoliday, specialHoliday,
				yearlyReserved, regularWork, flexWork, irregularWork);
	}
}
