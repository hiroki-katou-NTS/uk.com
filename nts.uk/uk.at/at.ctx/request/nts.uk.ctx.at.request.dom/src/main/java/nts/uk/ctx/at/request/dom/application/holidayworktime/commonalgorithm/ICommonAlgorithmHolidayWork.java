package nts.uk.ctx.at.request.dom.application.holidayworktime.commonalgorithm;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ActualContentDisplay;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HdWorkBreakTimeSetOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HdWorkDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.PrePostInitAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.HolidayWorkAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.WithdrawalAppSet;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSet;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.overtimeholidaywork.AppReflectOtHdWork;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.shr.com.time.TimeWithDayAttr;

/**
 * Refactor5
 * @author huylq
 *
 */
public interface ICommonAlgorithmHolidayWork {
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF010_休日出勤時間申請.アルゴリズム.休出申請の設定を取得する.ドメインモデル「休出申請設定」を取得する
	 * @return
	 */
	public HolidayWorkAppSet getHolidayWorkSetting(String companyId);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF010_休日出勤時間申請.アルゴリズム.休出申請の設定を取得する.ドメインモデル「残業休日出勤申請の反映」を取得する
	 * @param companyId
	 * @return
	 */
	public AppReflectOtHdWork getHdWorkOvertimeReflect(String companyId);
	
	/**
	 * 1-1.休日出勤申請（新規）起動時初期データを取得する
	 * @param companyId
	 * @param employeeId
	 * @param date
	 * @param baseDate
	 * @param prePostAtr
	 * @param employmentSet
	 * @param opWorkTimeList
	 * @param holidayWorkSetting
	 * @param hdWorkOvertimeReflect
	 * @param opActualContentDisplayLst
	 * @return
	 */
	public HdWorkDispInfoWithDateOutput getHdWorkDispInfoWithDateOutput(String companyId, String employeeId, 
			Optional<GeneralDate> date, GeneralDate baseDate, PrePostInitAtr prePostAtr, AppEmploymentSet employmentSet, 
			List<WorkTimeSetting> workTimeList, HolidayWorkAppSet holidayWorkSetting, AppReflectOtHdWork hdWorkOvertimeReflect, 
			List<ActualContentDisplay> actualContentDisplayList);

	/**
	 * 01-01_休憩時間を取得する
	 * @param companyID 会社ID
	 * @param appType 申請種類
	 * @param workTypeCD 勤務種類コード
	 * @param workTimeCD 就業時間帯コード
	 * @param startTime Optional＜開始時刻＞
	 * @param endTime Optional＜終了時刻＞
	 * @param timeCalUse 時刻計算利用区分
	 * @param breakTimeDisp 休憩入力欄を表示する
	 * @return
	 */
	public HdWorkBreakTimeSetOutput getBreakTime(String companyID, ApplicationType appType, String workTypeCD, String workTimeCD, 
			Optional<TimeWithDayAttr> startTime, Optional<TimeWithDayAttr> endTime, UseAtr timeCalUse, Boolean breakTimeDisp);
}
