package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employee;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.PublicHolidayMonthSetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;

/**
 * 
 * @author hayata_maekawa
 *年月リストから社員月間日数日数設定を取得する
 */
public class GetEmployeeMonthDaySettingFromTheYearMonthList {

	/**
	 * 取得する
	 * @param companyID 会社ID
	 * @param employeeId 社員ID
	 * @param yearMonthList 年月リスト
	 * @param require
	 * @return List＜月間公休日数設定＞
	 */
	public static List<PublicHolidayMonthSetting> get(String companyID, String employeeId
			,List<YearMonth> yearMonthList, Require require){
		
		List<Year> yearList = yearMonthList.stream()
								.map(x -> x.year())
								.distinct()
								.map(x -> new Year(x))
								.collect(Collectors.toList());
		
		List<EmployeeMonthDaySetting> employeeSettingList = 
				require.getEmployeeMonthDaySetting(companyID, employeeId, yearList);
		
		List<PublicHolidayMonthSetting> publicHolidayMonthSetting = new ArrayList<>();
		
		for(EmployeeMonthDaySetting employeeSetting: employeeSettingList){
			publicHolidayMonthSetting.addAll(employeeSetting.getPublicHolidayMonthSetting(yearMonthList));
		}
		return publicHolidayMonthSetting;
	}
	
	
	public static interface Require{
		List<EmployeeMonthDaySetting>  getEmployeeMonthDaySetting(
				String companyID, String employeeId,List<Year> yearList);
	}
}
