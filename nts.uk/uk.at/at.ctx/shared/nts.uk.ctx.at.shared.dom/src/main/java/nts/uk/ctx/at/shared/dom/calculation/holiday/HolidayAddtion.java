package nts.uk.ctx.at.shared.dom.calculation.holiday;

import java.math.BigDecimal;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
/**
 * @author phongtq
 *  休暇加算時間設定
 */
@AllArgsConstructor
@Getter
public class HolidayAddtion extends AggregateRoot {

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

	/** 通常勤務の加算設定*/
	private RegularWork regularWork;
	
	/**フレックス勤務の加算設定*/
	private FlexWork flexWork;

	/**変形労働勤務の加算設定*/
	private WorkDepLabor irregularWork;

	@Override
	public void validate() {
		super.validate();
	}

	public static HolidayAddtion createFromJavaType(String companyId, int referComHolidayTime, BigDecimal oneDay,
			BigDecimal morning, BigDecimal afternoon, int referActualWorkHours, int notReferringAch, int annualHoliday,
			int specialHoliday, int yearlyReserved, RegularWork regularWork, FlexWork flexWork,
			WorkDepLabor irregularWork) {
		return new HolidayAddtion(companyId, referComHolidayTime, oneDay, morning, afternoon, referActualWorkHours,
				EnumAdaptor.valueOf(notReferringAch, NotReferringAchAtr.class), annualHoliday, specialHoliday,
				yearlyReserved, regularWork, flexWork, irregularWork);
	}
}
