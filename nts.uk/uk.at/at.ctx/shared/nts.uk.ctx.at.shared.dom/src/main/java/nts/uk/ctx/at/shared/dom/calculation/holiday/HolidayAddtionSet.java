/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/

package nts.uk.ctx.at.shared.dom.calculation.holiday;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;


/**
 * The Class HolidayAddtionSet.
 */
@AllArgsConstructor
@Getter
// 休暇加算時間設定
public class HolidayAddtionSet extends AggregateRoot {

	/** The company id. */
	// 会社ID
	private String companyId;

	/** The refer com holiday time. */
	// 勤務実績を参照.所定時間参照先
	private int referComHolidayTime;

	/** The one day. */
	// 勤務実績を参照. 会社一律の加算時間.1日
	private BigDecimal oneDay;

	/** The morning. */
	// 勤務実績を参照. 会社一律の加算時間.午前
	private BigDecimal morning;

	/** The afternoon. */
	// 勤務実績を参照. 会社一律の加算時間.午後
	private BigDecimal afternoon;

	/** The refer actual work hours. */
	// 実績の就業時間帯を参照する
	private int referActualWorkHours;

	/** The not referring ach. */
	// 社員情報を参照.所定時間参照先
	private Optional<NotReferringAchAtr> notReferringAch;

	/** The annual holiday. */
	// 加算休暇設定.年休
	private int annualHoliday;

	/** The special holiday. */
	// 加算休暇設定.特別休暇
	private int specialHoliday;

	/** The yearly reserved. */
	// 加算休暇設定.積立年休
	private int yearlyReserved;

	/** The regular work. */
	// 通常勤務の加算設定
	private WorkRegularAdditionSet regularWork;
	
	/** The flex work. */
	// フレックス勤務の加算設定
	private WorkFlexAdditionSet flexWork;

	/** The work deform labor. */
	// 変形労働勤務の加算設定
	private WorkDeformedLaborAdditionSet workDeformLabor;
	
	/*B3_8*/
	/** The addition setting of overtime. */
	/*就業時間の加算設定管理*/
	private AddSetManageWorkHour additionSettingOfOvertime;
	
	/** The hour payment addition. */
	/*時給者の加算設定*/
	private HourlyPaymentAdditionSet hourPaymentAddition;
	
	/** The time holiday addition. */
	/*時間休暇加算*/
	private List<TimeHolidayAdditionSet> timeHolidayAddition;
	
	/* (non-Javadoc)
	 * @see nts.arc.layer.dom.DomainObject#validate()
	 */
	@Override
	public void validate() {
		super.validate();
	}

	
	/**
	 * Creates the from java type.
	 *
	 * @param companyId the company id
	 * @param referComHolidayTime the refer com holiday time
	 * @param oneDay the one day
	 * @param morning the morning
	 * @param afternoon the afternoon
	 * @param referActualWorkHours the refer actual work hours
	 * @param notReferringAch the not referring ach
	 * @param annualHoliday the annual holiday
	 * @param specialHoliday the special holiday
	 * @param yearlyReserved the yearly reserved
	 * @param regularWork the regular work
	 * @param flexWork the flex work
	 * @param workDeformLabor the work deform labor
	 * @param additionSettingOfOvertime the addition setting of overtime
	 * @param hourPaymentAddSet the hour payment add set
	 * @param timeHolidayAddition the time holiday addition
	 * @return the holiday addtion set
	 */
	public static HolidayAddtionSet createFromJavaType(String companyId, int referComHolidayTime, BigDecimal oneDay,
			BigDecimal morning, BigDecimal afternoon, int referActualWorkHours, int notReferringAch, int annualHoliday,
			int specialHoliday, int yearlyReserved, WorkRegularAdditionSet regularWork, WorkFlexAdditionSet flexWork,
			WorkDeformedLaborAdditionSet workDeformLabor, AddSetManageWorkHour additionSettingOfOvertime, HourlyPaymentAdditionSet hourPaymentAddSet,
			List<TimeHolidayAdditionSet> timeHolidayAddition) {
		return new HolidayAddtionSet(companyId, referComHolidayTime, oneDay, morning, afternoon, referActualWorkHours,
				Optional.of(EnumAdaptor.valueOf(notReferringAch, NotReferringAchAtr.class)), annualHoliday, specialHoliday,
				yearlyReserved, regularWork, flexWork, workDeformLabor, 
				additionSettingOfOvertime, hourPaymentAddSet, 
				timeHolidayAddition);
	}
}
