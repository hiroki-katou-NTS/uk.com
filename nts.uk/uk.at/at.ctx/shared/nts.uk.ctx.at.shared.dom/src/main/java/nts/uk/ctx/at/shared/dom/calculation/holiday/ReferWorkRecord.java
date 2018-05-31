/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.calculation.holiday;

import java.util.Optional;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.workingcondition.BreakdownTimeDay;

/**
 * The Class ReferWorkRecord.
 */
// 勤務実績を参照する
@Getter
public class ReferWorkRecord extends DomainObject{
	// 所定時間参照先
	private Optional<ReferenceDestinationAbsenceWorkingHours> timeReferenceDestination;
	// 会社一律の加算時間
	private Optional<BreakdownTimeDay> additionTimeCompany;
	/**
	 * @param timeReferenceDestination
	 * @param additionTimeCompany
	 */
	public ReferWorkRecord(ReferenceDestinationAbsenceWorkingHours timeReferenceDestination,
			BreakdownTimeDay additionTimeCompany) {
		super();
		this.timeReferenceDestination = Optional.of(timeReferenceDestination);
		this.additionTimeCompany = Optional.of(additionTimeCompany);
	}
}

