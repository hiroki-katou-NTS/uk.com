package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employment;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.PublicHolidayMonthSetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;

/**
 * 
 * @author hayata_maekawa
 *年月リストから雇用月間日数日数設定を取得する
 */
public class GetEmploymentMonthDaySettingFromTheYearMonthList {

	/**
	 * 取得する
	 * @param companyID 会社ID
	 * @param employmentCode　雇用コード
	 * @param yearMonthList　年月リスト
	 * @param require
	 * @return List＜月間公休日数設定＞
	 */
	public static List<PublicHolidayMonthSetting> get(String companyID, String employmentCode
			,List<YearMonth> yearMonthList, Require require){
		
		List<Year> yearList = yearMonthList.stream()
								.map(x -> x.year())
								.distinct()
								.map(x -> new Year(x))
								.collect(Collectors.toList());
		
		List<EmploymentMonthDaySetting> employSettingList = 
				require.getEmploymentMonthDaySetting(companyID, employmentCode, yearList);
		
		List<PublicHolidayMonthSetting> publicHolidayMonthSetting = new ArrayList<>();
		
		for(EmploymentMonthDaySetting employSetting: employSettingList){
			publicHolidayMonthSetting.addAll(employSetting.getPublicHolidayMonthSetting(yearMonthList));
		}
		return publicHolidayMonthSetting;
	}
	
	
	public static interface Require{
		List<EmploymentMonthDaySetting> getEmploymentMonthDaySetting(
				String companyID, String employmentCode,List<Year> yearList);
	}
}
