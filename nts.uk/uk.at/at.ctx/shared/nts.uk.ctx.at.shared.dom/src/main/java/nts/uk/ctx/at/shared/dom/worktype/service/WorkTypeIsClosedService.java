package nts.uk.ctx.at.shared.dom.worktype.service;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.worktype.WorkAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSet;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
import nts.uk.shr.com.context.AppContexts;

/**
 *
 * @author do_dt
 *
 */
public class WorkTypeIsClosedService {
	
	/**
	 * 勤務種類が休出振出かの判断
	 * @param workTimeCode
	 * @return
	 */
	public static boolean checkWorkTypeIsClosed(RequireM1 require, String workTypeCode) {
		boolean isFlag = false;
		String companyId = AppContexts.user().companyId();
		//ドメインモデル「勤務種類」を取得する
		Optional<WorkType> optWorkTypeData = require.workType(companyId, workTypeCode);
		if(!optWorkTypeData.isPresent()) {
			return false;
		}
		WorkType workTypeData = optWorkTypeData.get();
		//「1日の勤務」．1日 in (休日出勤, 振出) ||
		//「1日の勤務」．午前 = 振出 ||
		//「1日の勤務」．午後 = 振出
		if(workTypeData.getDailyWork().getOneDay() == WorkTypeClassification.HolidayWork
				||workTypeData.getDailyWork().getOneDay() ==WorkTypeClassification.Shooting
				||workTypeData.getDailyWork().getMorning() == WorkTypeClassification.Shooting
				||workTypeData.getDailyWork().getAfternoon() == WorkTypeClassification.Shooting) {
			isFlag = true;
		} else {
			isFlag = false;
		}
		return isFlag;
	}

	/**
	 * 打刻自動セット区分を取得する
	 * @param workTypeCode
	 * @param workTypeAtr
	 * @return
	 */
	public static boolean checkStampAutoSet(RequireM1 require, String workTypeCode, AttendanceOfficeAtr workTypeAtr) {
		String companyId = AppContexts.user().companyId();
		//ドメインモデル「勤務種類」を取得する
		Optional<WorkType> optWorkTypeData = require.workType(companyId, workTypeCode);
		if(!optWorkTypeData.isPresent()) {
			return false;
		}
		WorkType workTypeData = optWorkTypeData.get();
		final WorkAtr workAtrTmp;
		if(workTypeData.getDailyWork().getWorkTypeUnit() == WorkTypeUnit.OneDay
				&& workTypeData.getDailyWork().getOneDay() == WorkTypeClassification.Attendance) {
			workAtrTmp = WorkAtr.OneDay;
		} else if (workTypeData.getDailyWork().getWorkTypeUnit() == WorkTypeUnit.MonringAndAfternoon
				&& workTypeData.getDailyWork().getMorning() == WorkTypeClassification.Attendance) {
			workAtrTmp = WorkAtr.Monring;
		} else if(workTypeData.getDailyWork().getWorkTypeUnit() == WorkTypeUnit.MonringAndAfternoon
				&& workTypeData.getDailyWork().getAfternoon() == WorkTypeClassification.Attendance) {
			workAtrTmp = WorkAtr.Afternoon;
		} else {
			workAtrTmp = WorkAtr.OneDay;
		}
		List<WorkTypeSet> lstAttendance = workTypeData.getWorkTypeSetList();
		if(lstAttendance.isEmpty()) {
			return false;			
		}
		List<WorkTypeSet> lst1Day = lstAttendance.stream()
				.filter(x -> x.getWorkTypeCd().v().contains(workTypeCode) && x.getWorkAtr() == workAtrTmp)
				.collect(Collectors.toList());
		if(lst1Day.isEmpty()) {
			return false;
		}
		WorkTypeSet data1day = lst1Day.get(0);
		//INPUT．出勤退勤区分をチェックする
		//自動打刻セット区分=「所定勤務の設定」．出勤時刻を直行とする
		if(workTypeAtr == AttendanceOfficeAtr.ATTENDANCE) {
			return data1day.getAttendanceTime().value == 0 ? false : true; 
		} else {
			//自動打刻セット区分=「所定勤務の設定」．退勤時刻を直帰とする
			return data1day.getTimeLeaveWork().value == 0 ? false : true;
		}
	}

	/**
	 * 1日休日の判定
	 * @param sid
	 * @param ymd
	 * @return
	 */
	public static boolean checkHoliday(RequireM1 require, String workTypeCode) {
		boolean isHoliday = false;
		String companyId = AppContexts.user().companyId();
		//ドメインモデル「日別実績の勤務情報」を取得する
		Optional<WorkType> optWorkTypeInfor = require.workType(companyId, workTypeCode);
		if(!optWorkTypeInfor.isPresent()) {
			return isHoliday;
		}
		WorkType worktypeInfor = optWorkTypeInfor.get();
		//勤務区分チェック
		WorkTypeUnit workTypeUnit = worktypeInfor.getDailyWork().getWorkTypeUnit();
		if(workTypeUnit == WorkTypeUnit.OneDay) {
			//1日をチェックする
			WorkTypeClassification oneDay = worktypeInfor.getDailyWork().getOneDay();
			if(oneDay == WorkTypeClassification.Holiday) {
				return true;
			}
		}
		return isHoliday;
	}
	
	public static interface RequireM1 {

		Optional<WorkType> workType(String companyId, String workTypeCd);
	}
}
