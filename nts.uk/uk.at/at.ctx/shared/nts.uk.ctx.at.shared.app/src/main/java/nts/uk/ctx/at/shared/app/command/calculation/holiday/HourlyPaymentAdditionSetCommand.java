package nts.uk.ctx.at.shared.app.command.calculation.holiday;

import lombok.Data;

@Data
public class HourlyPaymentAdditionSetCommand {
	/**  会社ID. */
	private String companyId;
	
	/**  実働のみで計算する. */
	private int calcPremiumVacation;
	
	/**  加算する. */
	private int addition1;
	
	/**  通常、変形の所定超過時. */
	private int deformatExcValue;
	
	/**  育児・介護時間を含めて計算する. */
	private int incChildNursingCare;
	
	/** The deduct. */
	/*控除する*/
	private int deduct;
	
	/** The calculate include interval exemption time. */
	/*インターバル免除時間を含めて計算する*/
	private int calculateIncludeIntervalExemptionTime1;
	
	/** The calculate actual operation. */
	/*実働のみで計算する*/
	private int calcWorkHourVacation;
	
	/** The addition 2. */
	/*加算する */
	private int addition2;
	
	/** The calculate includ care time. */
	/*育児・介護時間を含めて計算する*/
	private int calculateIncludCareTime;
	
	/*B8_20*/
	/** The not deduct late leave early. */
	/*遅刻・早退を控除する*/
	private int notDeductLateLeaveEarly;
	
	/** The calculate includ interval exemption time. */
	/*インターバル免除時間を含めて計算する*/
	private int calculateIncludeIntervalExemptionTime2;
	
	/** The enable set per work hour 1. */
	/*就業時間帯毎の設定を可能とする*/
	private int enableSetPerWorkHour1;
	
	/** The enable set per work hour 2. */
	/*就業時間帯毎の設定を可能とする*/
	private int enableSetPerWorkHour2;
}
