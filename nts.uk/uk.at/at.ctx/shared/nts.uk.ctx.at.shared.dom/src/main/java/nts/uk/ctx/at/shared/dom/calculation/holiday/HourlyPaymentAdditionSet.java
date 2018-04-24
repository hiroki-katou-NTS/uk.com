/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.calculation.holiday;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.shr.com.enumcommon.NotUseAtr;


/**
 * The Class HourlyPaymentAdditionSet.
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
/*時給者の加算設定*/
public class HourlyPaymentAdditionSet extends AggregateRoot{
	
	/**  会社ID. */
	private String companyId;
	
	/*B8_4*/
	/**  実働のみで計算する. */
	private CalcActualOperationAtr calcPremiumVacation;
	
	/*B8_7*/
	/**  加算する. */
	private NotUseAtr addition1;
	
	/*B8_9*/
	/**  通常、変形の所定超過時. */
	private DeformatExcValueAtr deformatExcValue;
	
	/*B8_12*/
	/**  育児・介護時間を含めて計算する. */
	private NotUseAtr incChildNursingCare;
	
	/*B8_13*/
	/** The deduct. */
	/*控除する*/
	private boolean deduct;
	
	/*B8_14*/
	/** The calculate include interval exemption time. */
	/*インターバル免除時間を含めて計算する*/
	private NotUseAtr calculateIncludeIntervalExemptionTime1;
	
	/*B8_15*/
	/** The calculate actual operation. */
	/*実働のみで計算する*/
	private CalcActualOperationAtr calcWorkHourVacation;
	
	/*B8_18*/
	/** The addition 2. */
	/*加算する */
	private NotUseAtr addition2;
	
	/*B8_19*/
	/** The calculate includ care time. */
	/*育児・介護時間を含めて計算する*/
	private NotUseAtr calculateIncludCareTime;
	
	/*B8_20*/
	/** The not deduct late leave early. */
	/*遅刻・早退を控除する*/
	private NotUseAtr notDeductLateLeaveEarly;
	
	/*B8_21*/
	/** The calculate includ interval exemption time. */
	/*インターバル免除時間を含めて計算する*/
	private NotUseAtr calculateIncludeIntervalExemptionTime2;
	
	/*B8_22*/
	/** The enable set per work hour 1. */
	/*就業時間帯毎の設定を可能とする*/
	private boolean enableSetPerWorkHour1;
	
	/*B8_23*/
	/** The enable set per work hour 2. */
	/*就業時間帯毎の設定を可能とする*/
	private boolean enableSetPerWorkHour2;
	
	public static HourlyPaymentAdditionSet createFromJavaType(String companyId, int calcPremiumVacation,
			int addition1, int deformatExcValue, int incChildNursingCare, int deduct, int calculateIncludeIntervalExemptionTime1,
			int calcWorkHourVacation, int addition2, int calculateIncludCareTime, int notDeductLateLeaveEarly,
			int calculateIncludeIntervalExemptionTime2, int enableSetPerWorkHour1, int enableSetPerWorkHour2) {
		return new HourlyPaymentAdditionSet(companyId, EnumAdaptor.valueOf(calcPremiumVacation, CalcActualOperationAtr.class),
				EnumAdaptor.valueOf(addition1, NotUseAtr.class), EnumAdaptor.valueOf(deformatExcValue, DeformatExcValueAtr.class),
				EnumAdaptor.valueOf(incChildNursingCare, NotUseAtr.class), deduct == 1 ? true : false, 
				EnumAdaptor.valueOf(calculateIncludeIntervalExemptionTime1, NotUseAtr.class), 
				EnumAdaptor.valueOf(calcWorkHourVacation, CalcActualOperationAtr.class),
				EnumAdaptor.valueOf(addition2, NotUseAtr.class),
				EnumAdaptor.valueOf(calculateIncludCareTime, NotUseAtr.class),
				EnumAdaptor.valueOf(notDeductLateLeaveEarly, NotUseAtr.class),
				EnumAdaptor.valueOf(calculateIncludeIntervalExemptionTime2, NotUseAtr.class),
				enableSetPerWorkHour1 == 1 ? true : false, enableSetPerWorkHour2 == 1 ? true : false
				);
	}
}
