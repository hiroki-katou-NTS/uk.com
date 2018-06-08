package nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.export;

import java.util.List;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialHolidayRemainData;

public interface SpecialHolidayRemainDataSevice {
	/**
	 * RequestList263: 社員の月毎の確定済み特別休暇を取得する
	 * @param sid
	 * @param startMonth
	 * @param endMonth
	 * @return
	 */
	public List<SpecialHolidayRemainDataOutput> getSpeHoliOfConfirmedMonthly(String sid, YearMonth startMonth, YearMonth endMonth);
}
