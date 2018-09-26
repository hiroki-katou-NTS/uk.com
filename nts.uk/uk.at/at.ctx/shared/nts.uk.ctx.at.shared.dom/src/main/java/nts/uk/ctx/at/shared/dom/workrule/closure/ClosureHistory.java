/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.workrule.closure;

import java.util.Calendar;

import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.shr.com.time.calendar.date.ClosureDate;

/**
 * The Class ClosureHistorry.
 */
// 締め変更履歴
@Getter
public class ClosureHistory extends DomainObject {
	
	/** The company id. */
	private CompanyId companyId;

	/** The closure id. */
	// 締めＩＤ
	private ClosureId closureId;

	/** The closure name. */
	// 名称: 締め名称
	private ClosureName closureName;

	/** The end year month. */
	// 終了年月: 年月
	@Setter
	private YearMonth endYearMonth;

	/** The closure date. */
	// 締め日: 日付
	private ClosureDate closureDate;

	/** The start year month. */
	// 開始年月: 年月
	private YearMonth startYearMonth;

	/** The Constant ONE_DAY. */
	private static final int ONE_DAY = 1;

	
	/**
	 * Instantiates a new closure history.
	 *
	 * @param memento the memento
	 */
	public ClosureHistory(ClosureHistoryGetMemento memento) {
		this.closureName = memento.getClosureName();
		this.closureId = memento.getClosureId();
		this.companyId = memento.getCompanyId();
		this.endYearMonth = memento.getEndDate();
		this.closureDate = memento.getClosureDate();
		this.startYearMonth = memento.getStartDate();
	}

	/**
	 * Save to memento.
	 *
	 * @param memento the memento
	 */
	public void saveToMemento(ClosureHistorySetMemento memento) {
		memento.setClosureName(this.closureName);
		memento.setClosureId(this.closureId);
		memento.setCompanyId(this.companyId);
		memento.setEndDate(this.endYearMonth);
		memento.setClosureDate(this.closureDate);
		memento.setStartDate(this.startYearMonth);
	}

	/**
	 * To closure date.
	 *
	 * @return the int
	 */
	public int toClosureDate() {
		if (this.getClosureDate().getLastDayOfMonth()) {
			return 0;
		}
		return this.getClosureDate().getClosureDay().v();
	}

	/**
	 * Gets the closure YMD.
	 *
	 * @return the closure YMD
	 */
	public GeneralDate getClosureYMD() {
		// Case min ym
		if (GeneralDate.ymd(this.startYearMonth.year(), this.startYearMonth.month(), ONE_DAY).equals(GeneralDate.min())
				&& this.closureDate.getLastDayOfMonth()) {
			return GeneralDate.min();
		}
		
		// Case date is not exist
		if(!this.isDateOfMonth(this.startYearMonth.year(), this.startYearMonth.month(),
				this.closureDate.getClosureDay().v() + ONE_DAY)) {
			return GeneralDate.ymd(this.startYearMonth.year(), this.startYearMonth.month(), ONE_DAY);
		}
		
		return GeneralDate.ymd(this.startYearMonth.year(), this.startYearMonth.month(),
				this.closureDate.getClosureDay().v() + ONE_DAY);
	}
	
	/**
	 * Gets the last date of month.
	 *
	 * @param year the year
	 * @param month the month
	 * @return the last date of month
	 */
	private GeneralDate getLastDateOfMonth(int year, int month) {
		GeneralDate baseDate = GeneralDate.ymd(year, month, 1);
		Calendar c = Calendar.getInstance();
		c.setTime(baseDate.date());
		c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
		return GeneralDate.legacyDate(c.getTime());
	}

	/**
	 * Checks if is date of month.
	 *
	 * @param year the year
	 * @param month the month
	 * @param dayOfMonth the day of month
	 * @return true, if is date of month
	 */
	private boolean isDateOfMonth(int year, int month, int dayOfMonth) {
		GeneralDate baseDate = this.getLastDateOfMonth(year, month);
		return 0 < dayOfMonth && dayOfMonth <= baseDate.day();
	}
}
