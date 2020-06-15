/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.dom.workrecord.monthcal;

import java.io.Serializable;

import lombok.Getter;

/**
 * The Class ExcessOutsideTimeSetReg.
 */
// 割増集計方法
@Getter
public class ExcessOutsideTimeSetReg implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** The legal over time work. */
	// 法定内残業を含める
	private Boolean legalOverTimeWork;

	/** The legal holiday. */
	// 法定外休出を含める
	private Boolean legalHoliday;

	/** The surcharge week month. */
	// 週、月割増時間を集計する
	private Boolean surchargeWeekMonth;

	/** The except legal holidaywork. */
	// 勤務種類が法内休出の日を除く
	private Boolean exceptLegalHdwk;
	
	/**
	 * Instantiates a new excess outside time set reg.
	 *
	 * @param legalOverTimeWork
	 *            the legal over time work
	 * @param legalHoliday
	 *            the legal holiday
	 * @param surchargeWeekMonth
	 *            the surcharge week month
	 * @param exceptLegalHdwk
	 *            the except legal holidaywork
	 */
	public ExcessOutsideTimeSetReg(Boolean legalOverTimeWork, Boolean legalHoliday,
			Boolean surchargeWeekMonth, Boolean exceptLegalHdwk) {
		super();
		this.legalOverTimeWork = legalOverTimeWork;
		this.legalHoliday = legalHoliday;
		this.surchargeWeekMonth = surchargeWeekMonth;
		this.exceptLegalHdwk = exceptLegalHdwk;
	}

}
