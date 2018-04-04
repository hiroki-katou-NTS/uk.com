package nts.uk.ctx.at.shared.app.find.calculation.holiday;

import lombok.Data;

@Data
public class HourlyPaymentAdditionSetDto {
	/**  会社ID. */
	private String companyId;
	
	/*B8_4*/
	/**  実働のみで計算する. */
	private int calcPremiumVacation;
	
	/*B8_7*/
	/**  加算する. */
	private int addition1;
	
	/*B8_9*/
	/**  通常、変形の所定超過時. */
	private int deformatExcValue;
	
	/*B8_12*/
	/**  育児・介護時間を含めて計算する. */
	private int incChildNursingCare;
	
	/*B8_13*/
	/** The deduct. */
	/*控除する*/
	private int deduct;
	
	/*B8_14*/
	/** The calculate include interval exemption time. */
	/*インターバル免除時間を含めて計算する*/
	private int calculateIncludeIntervalExemptionTime1;
	
	/*B8_15*/
	/** The calculate actual operation. */
	/*実働のみで計算する*/
	private int calcWorkHourVacation;
	
	/*B8_18*/
	/** The addition 2. */
	/*加算する */
	private int addition2;
	
	/*B8_19*/
	/** The calculate includ care time. */
	/*育児・介護時間を含めて計算する*/
	private int calculateIncludCareTime;
	
	/*B8_20*/
	/** The not deduct late leave early. */
	/*遅刻・早退を控除する*/
	private int notDeductLateLeaveEarly;
	
	/*B8_21*/
	/** The calculate includ interval exemption time. */
	/*インターバル免除時間を含めて計算する*/
	private int calculateIncludeIntervalExemptionTime2;
	
	/*B8_22*/
	/** The enable set per work hour 1. */
	/*就業時間帯毎の設定を可能とする*/
	private int enableSetPerWorkHour1;
	
	/*B8_23*/
	/*就業時間帯毎の設定を可能とする*/
	private int enableSetPerWorkHour2;
}
