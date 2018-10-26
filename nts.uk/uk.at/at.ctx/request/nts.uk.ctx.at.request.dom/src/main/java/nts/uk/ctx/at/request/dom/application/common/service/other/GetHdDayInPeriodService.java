package nts.uk.ctx.at.request.dom.application.common.service.other;


import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementOutput;
import nts.uk.ctx.at.shared.dom.worktype.AttendanceHolidayAttr;
import nts.uk.ctx.at.shared.dom.worktype.algorithm.JudgeHdSystemOneDayService;
import nts.uk.ctx.at.shared.dom.worktype.algorithm.SpecHdFrameForWkTypeSetService;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;
@Stateless
public class GetHdDayInPeriodService {
	
	@Inject
	private CollectAchievement collectAchievement;
	@Inject
	private SpecHdFrameForWkTypeSetService specHdWkpTypeSv;
	/**
	 * 19.指定する期間での休日日数を取得する
	 * 20.指定する期間での休日日数を取得する
	 * @author hoatt
	 * @param 社員ID //employeeID
	 * @param 期間//period
	 * @return 休日日数 //holidayNumber
	 */
	public int getHolidayDayInPeriod(String employeeID, DatePeriod period){
		int hdDays = 0;
		String companyID = AppContexts.user().companyId();
		//休日日数=0(初期化)-(holidayNumber = 0(Khởi tạo ban đầu))
		//INPUT．期間．開始日から期間．終了日までループする-(Loop từ INPUT．period．startDate đến period．endDate)
		GeneralDate date = period.start();
		int a = period.start().daysTo(period.end()) + 1;
		for (int i = 1; i <= a; i ++) {
			//実績の取得-(lấy Performance-kết quả thực tế) 13.実績を取得する
			AchievementOutput ach = collectAchievement.getAchievement(companyID, employeeID, date);
			if(Strings.isBlank(ach.getWorkType().getWorkTypeCode())){
				date = period.start().addDays(i);
				continue;
			}
			//#100574 - #100575 2018.10.02
			//1日半日出勤・1日休日系の判定
//			AttendanceHolidayAttr checkOneDay = judgeHdSysOneDaySv.judgeHdOnDayWorkPer(ach.getWorkType().getWorkTypeCode());
			//#102184 - 2018.10.25
			//1日休日の判定
			boolean checkOneDay = specHdWkpTypeSv.jubgeHdOneDay(companyID, ach.getWorkType().getWorkTypeCode());
			//outputがTrue：1日休日系 (True)
			//休日日数+=1-(holidayNumber + =1)
			hdDays = checkOneDay ? hdDays + 1 : hdDays;
			date = period.start().addDays(i);
		}
		return hdDays;
	};
}
