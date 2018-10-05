package nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.export;

import java.util.List;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.monthly.vacation.specialholiday.monthremaindata.SpecialHolidayRemainData;

public interface SpecialHolidayRemainDataSevice {
	/**
	 * RequestList263: 社員の月毎の確定済み特別休暇を取得する
	 * @param sid 社員ID
	 * @param startMonth 年月期間
	 * @param endMonth 年月期間
	 * @return
	 */
	public List<SpecialHolidayRemainDataOutput> getSpeHoliOfConfirmedMonthly(String sid, YearMonth startMonth, YearMonth endMonth);
	/**
	 * 社員の月毎の確定済み特別休暇を取得する
	 * @param sid 社員ID
	 * @param startMonth 年月期間
	 * @param endMonth 年月期間
	 * @param speCodes List<特別休暇コード>
	 * @return
	 */
	public List<SpecialHolidayRemainDataOutput> getSpeHoliOfPeriodAndCodes(String sid, YearMonth startMonth, YearMonth endMonth, List<Integer> speCodes);
	
	public List<SpecialHolidayRemainDataOutput> getSpeHoliOfConfirmedMonthly(String sid, YearMonth startMonth, YearMonth endMonth, Integer speCode);
}
