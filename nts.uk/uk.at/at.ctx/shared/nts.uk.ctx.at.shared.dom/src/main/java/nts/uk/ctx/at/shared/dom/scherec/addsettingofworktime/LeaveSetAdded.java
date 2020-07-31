/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.scherec.addsettingofworktime;

import java.io.Serializable;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.worktype.VacationCategory;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The Class LeaveSetAdded.
 */
// 加算する休暇設定
@Getter
public class LeaveSetAdded extends DomainObject implements Serializable{

	/** Serializable */
	private static final long serialVersionUID = 1L;
	
	/** The annual holiday. */
	// 年休
	private NotUseAtr annualHoliday;
	
	/** The yearly reserved. */
	// 積立年休
	private NotUseAtr yearlyReserved;
	
	/** The special holiday. */
	// 特別休暇
	private NotUseAtr specialHoliday;

	/**
	 * Instantiates a new leave set added.
	 *
	 * @param annualHoliday the annual holiday
	 * @param yearlyReserved the yearly reserved
	 * @param specialHoliday the special holiday
	 */
	public LeaveSetAdded(NotUseAtr annualHoliday, NotUseAtr yearlyReserved, NotUseAtr specialHoliday) {
		super();
		this.annualHoliday = annualHoliday;
		this.yearlyReserved = yearlyReserved;
		this.specialHoliday = specialHoliday;
	}
	
	
	/**
	 * 引数に一致する使用区分を持っているか
	 * && その使用区分が使用しない　であるか判定する
	 * 持っていない場合は、falseを返す
	 * @param vacationCategory
	 * @return
	 */
	public boolean dicisionExistAndNotUse(VacationCategory vacationCategory) {
		switch(vacationCategory) {
			case AnnualHoliday:
				return annualHoliday.equals(NotUseAtr.NOT_USE);
			case YearlyReserved:
				return yearlyReserved.equals(NotUseAtr.NOT_USE);
			case SpecialHoliday:
				return specialHoliday.equals(NotUseAtr.NOT_USE);
			case Absence:
			case Holiday:
			case Pause:
			case SubstituteHoliday:
			case TimeDigestVacation:
				return false;
			default:
				throw new RuntimeException("unknown vacationCategory");
		}
	}
}

