package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.company;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.PublicHolidayMonthSetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;


/**
 * 
 * @author hayata_maekawa
 *年月リストから会社月間日数日数設定を取得する
 */
public class GetCompanyMonthDaySettingFromTheYearMonthList {
	
	/**
	 * 取得する
	 * @param companyID 会社ID
	 * @param yearMonthList 年月リスト
	 * @param require List＜月間公休日数設定＞
	 * @return
	 */
	public static List<PublicHolidayMonthSetting> get(String companyID,List<YearMonth> yearMonthList, Require require){
		
		List<Year> yearList = yearMonthList.stream()
								.map(x -> x.year())
								.distinct()
								.map(x -> new Year(x))
								.collect(Collectors.toList());
		
		List<CompanyMonthDaySetting> companySettingList = 
				require.getCompanyMonthDaySetting(companyID, yearList);
		
		List<PublicHolidayMonthSetting> publicHolidayMonthSetting = new ArrayList<>();
		
		for(CompanyMonthDaySetting companySetting: companySettingList){
			publicHolidayMonthSetting.addAll(companySetting.getPublicHolidayMonthSetting(yearMonthList));
		}
		return publicHolidayMonthSetting;
	}
	
	
	public static interface Require{
		List<CompanyMonthDaySetting>  getCompanyMonthDaySetting(
				String companyID ,List<Year> yearList);
	}
}
