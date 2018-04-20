package nts.uk.ctx.at.shared.dom.yearholidaygrant.service;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

/**
 * 
 * @author tanlv
 *
 */
public interface LengthServiceTblService {
	/**
	 * RequestList 年休付与年月日を計算
	 */
	List<NextAnnualLeaveGrant> calAnnualHdAwardDate(GeneralDate entryDate, GeneralDate standardDate, Period period, String yearHolidayCode, 
			Optional<GeneralDate> simultaneousGrandMD, Optional<Boolean> singleDayFlag);
}
