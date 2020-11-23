package nts.uk.ctx.at.request.dom.application.holidayworktime.service;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.AppHdWorkDispInfoOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.AppHdWorkDispInfoOutput_Old;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HolidayWorkCalculationResult;
import nts.uk.ctx.at.request.dom.application.overtime.ApplicationTime;
import nts.uk.ctx.at.request.dom.application.overtime.service.WorkContent;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.applicationtypesetting.PrePostInitAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeLeaveAppCommonSet;
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
	public HolidayWorkCalculationResult calculate(String companyId, String employeeId, Optional<GeneralDate> date, PrePostInitAtr prePostAtr, 
			OvertimeLeaveAppCommonSet overtimeLeaveAppCommonSet, ApplicationTime preApplicationTime, ApplicationTime actualApplicationTime, WorkContent workContent);
}
