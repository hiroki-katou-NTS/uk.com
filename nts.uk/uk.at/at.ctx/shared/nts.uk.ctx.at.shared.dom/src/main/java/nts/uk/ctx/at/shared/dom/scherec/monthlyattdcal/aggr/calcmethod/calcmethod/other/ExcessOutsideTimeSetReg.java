package nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.calcmethod.calcmethod.other;

import java.io.Serializable;

import lombok.Getter;

/**
 * 割増集計方法
 */
@Getter
public class ExcessOutsideTimeSetReg implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;

	/** 法定内残業を含める */
	private boolean legalOverTimeWork;

	/** 法定外休出を含める */
	private boolean legalHoliday;

	/** 週、月割増時間を集計する */
	private boolean surchargeWeekMonth;

	/** 勤務種類が法内休出の日を除く */
	private boolean exceptLegalHdwk;
	
	/**
	 * Instantiates a new excess outside time set reg.
	 *
	 * @param legalOverTimeWork the legal over time work
	 * @param legalHoliday the legal holiday
	 * @param surchargeWeekMonth the surcharge week month
	 * @param exceptLegalHdwk the except legal holidaywork
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
