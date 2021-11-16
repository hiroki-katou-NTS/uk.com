package nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.workplace;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import nts.arc.time.YearMonth;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.PublicHolidayMonthSetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;

/**
 * 
 * @author hayata_maekawa
 *年月リストから職場月間日数日数設定を取得する
 */
public class GetWorkplaceMonthDaySettingFromTheYearMonthList {

	/**
	 * 取得する
	 * @param companyID 会社ID
	 * @param workplaceId 職場ID
	 * @param yearMonthList 年月リスト
	 * @param require
	 * @return List＜月間公休日数設定＞
	 */
	public static List<PublicHolidayMonthSetting> get(String companyID, String workplaceId
			,List<YearMonth> yearMonthList, Require require){
		
		List<Year> yearList = yearMonthList.stream()
								.map(x -> x.year())
								.distinct()
								.map(x -> new Year(x))
								.collect(Collectors.toList());
		
		List<WorkplaceMonthDaySetting> workplaceSettingList = 
				require.getWorkplaceMonthDaySetting(companyID, workplaceId, yearList);
		
		List<PublicHolidayMonthSetting> publicHolidayMonthSetting = new ArrayList<>();
		
		for(WorkplaceMonthDaySetting workplaceSetting: workplaceSettingList){
			publicHolidayMonthSetting.addAll(workplaceSetting.getPublicHolidayMonthSetting(yearMonthList));
		}
		return publicHolidayMonthSetting;
	}
	
	
	public static interface Require{
		List<WorkplaceMonthDaySetting>  getWorkplaceMonthDaySetting(
				String companyID, String workplaceId,List<Year> yearList);
	}
	
}
