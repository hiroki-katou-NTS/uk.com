package nts.uk.ctx.at.request.dom.application.holidayworktime.service;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ActualContentDisplay;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoWithDateOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.AppHdWorkDispInfoOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.AppHdWorkDispInfoOutput_Old;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HdSelectWorkDispInfoOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HolidayWorkCalculationResult;
import nts.uk.ctx.at.request.dom.application.overtime.ApplicationTime;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkContent;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.PrePostInitAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.HolidayWorkAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeLeaveAppCommonSet;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
/**
 * UKDesign.UniversalK.就業.KAF_申請.KAF010_休日出勤時間申請.A：休日出勤時間申請（新規）.ユースケース.A：休日出勤時間申請（新規）
 * @author huylq
 * Refactor5
 */
public interface HolidayService {
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF010_休日出勤時間申請.A：休日出勤時間申請（新規）.ユースケース.起動する(khởi động).起動する
	 * @param companyId
	 * @param empList
	 * @param dateList
	 * @param appDispInfoStartupOutput
	 * @return
	 */
	public AppHdWorkDispInfoOutput startA(String companyId, Optional<List<String>> empList, 
			Optional<List<GeneralDate>> dateList, AppDispInfoStartupOutput appDispInfoStartupOutput);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF010_休日出勤時間申請.A：休日出勤時間申請（新規）.ユースケース.計算する(従業員).計算（従業員）
	 * @param companyId
	 * @param employeeId
	 * @param date
	 * @param prePostAtr
	 * @param overtimeLeaveAppCommonSet
	 * @param preApplicationTime
	 * @param actualApplicationTime
	 * @param workContent
	 * @return
	 */
	public HolidayWorkCalculationResult calculate(String companyId, String employeeId, 
			Optional<GeneralDate> date, PrePostInitAtr prePostAtr, 
			OvertimeLeaveAppCommonSet overtimeLeaveAppCommonSet, ApplicationTime preApplicationTime, 
			ApplicationTime actualApplicationTime, WorkContent workContent);
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF010_休日出勤時間申請.A：休日出勤時間申請（新規）.ユースケース.申請日付を変更する(Thay đổi AppDate)
	 * @param companyId
	 * @param dateList
	 * @param applicationType
	 * @param appHdWorkDispInfoOutput
	 * @return
	 */
	public AppHdWorkDispInfoOutput changeAppDate(String companyId, List<GeneralDate> dateList, ApplicationType applicationType,
			AppHdWorkDispInfoOutput appHdWorkDispInfoOutput);
	
	/**
	 * UKDesign.UniversalK.就業.KAF_申請.KAF010_休日出勤時間申請.アルゴリズム.勤務種類・就業時間帯選択時に表示するデータを取得する
	 * @param companyId
	 * @param date
	 * @param workType
	 * @param workTime
	 * @param actualContentDisplayList
	 * @param appDispInfoStartupOutput
	 * @param holidayWorkAppSet
	 * @return
	 */
	public HdSelectWorkDispInfoOutput selectWork(String companyId, GeneralDate date, WorkTypeCode workType, 
			WorkTimeCode workTime, List<ActualContentDisplay> actualContentDisplayList, AppDispInfoStartupOutput appDispInfoStartupOutput, 
			HolidayWorkAppSet holidayWorkAppSet);
}
