package nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.algorithmdailyper;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.WorkInfoOfDailyAttendance;
import nts.uk.ctx.at.shared.dom.workingcondition.SingleDaySchedule;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemService;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.service.WorkingConditionService;
import nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

/**
 * 勤務情報から打刻反映時間帯を取得する
 * @author tutk
 *
 */
@Stateless
public class TimeReflectFromWorkinfo {
	
	@Inject
	private WorkTypeRepository workTypeRepo;
	
	@Inject
	private WorkTimeSettingRepository workTimeSettingRepository;
	
	@Inject
	private WorkingConditionItemService workingConditionItemService;
	
	@Inject
	private WorkingConditionRepository workingConditionRepo;
	

	public OutputTimeReflectForWorkinfo get(String companyId, String employeeId, GeneralDate ymd,
			WorkInfoOfDailyAttendance workInformation) {
		OutputTimeReflectForWorkinfo outputTimeReflectForWorkinfo = new OutputTimeReflectForWorkinfo();

		// ドメインモデル「勤務種類」を取得する get 1 or 0
		List<WorkType> optWorkTypeData = workTypeRepo.findNotDeprecatedByListCode(companyId,
				Arrays.asList(workInformation.getRecordInfo().getWorkTypeCode().v()));
		if(optWorkTypeData.isEmpty()) {
			outputTimeReflectForWorkinfo.setEndStatus(EndStatus.NO_WORK_TYPE);
			return outputTimeReflectForWorkinfo;
		}
		//日別実績の勤務情報．就業時間帯コードを取得
		WorkTimeCode workTimeCode = workInformation.getRecordInfo().getWorkTimeCode();
		
		//就業時間帯コード = null の場合
		if(workTimeCode == null) {
			// 社員の労働条件を取得する
			Optional<WorkingConditionItem> workingConditionItem = WorkingConditionService
					.findWorkConditionByEmployee(new WorkingConditionService.RequireM1() {
						
						@Override
						public Optional<WorkingConditionItem> workingConditionItem(String historyId) {
							return workingConditionRepo.getWorkingConditionItem(historyId);
						}
						
						@Override
						public Optional<WorkingCondition> workingCondition(String companyId, String employeeId, GeneralDate baseDate) {
							return workingConditionRepo.getBySidAndStandardDate(companyId, employeeId, baseDate);
						}
					}, employeeId, ymd);
			if(!workingConditionItem.isPresent()) {
				outputTimeReflectForWorkinfo.setEndStatus(EndStatus.NO_WORK_CONDITION);
				return outputTimeReflectForWorkinfo;
			}
			WorkTypeClassification typeOneDay = optWorkTypeData.get(0).getDailyWork().getOneDay();
			WorkTypeClassification typeMorning = optWorkTypeData.get(0).getDailyWork().getMorning();
			WorkTypeClassification typeAfternoon = optWorkTypeData.get(0).getDailyWork().getAfternoon();
			// INPUT．「勤務種類」の勤務種類の分類をチェックする
			// 休日出勤の勤務情報を見る
			// ・勤務種類．１日の勤務．勤務区分 = 1日
			// ⇒勤務種類．１日の勤務．1日 = 休日、振休、休日出勤
			// ・勤務種類．１日の勤務．勤務区分 = 午前と午後
			// ⇒勤務種類．１日の勤務．午前 = 休日、振休、休日出勤 OR 勤務種類．１日の勤務．午後 = 休日、振休、休日出勤
			if (typeOneDay.checkHolidayNew() && (typeMorning.checkHolidayNew() || typeAfternoon.checkHolidayNew())) {
				// 休日出勤時の勤務情報を取得する
				Optional<SingleDaySchedule> singleDaySchedule = workingConditionItemService.getHolidayWorkSchedule(
						companyId, employeeId, ymd, workInformation.getRecordInfo().getWorkTypeCode().v());
				if(!singleDaySchedule.isPresent()) {
					outputTimeReflectForWorkinfo.setEndStatus(EndStatus.NO_HOLIDAY_SETTING);
					return outputTimeReflectForWorkinfo;
				}
			}
		}else {
			// ドメインモデル「就業時間帯の設定」を取得する
			Optional<WorkTimeSetting> workTimeOpt = this.workTimeSettingRepository.findByCodeAndAbolishCondition(companyId,
					workTimeCode.v(), AbolishAtr.NOT_ABOLISH);
			if(!workTimeOpt.isPresent()) {
				outputTimeReflectForWorkinfo.setEndStatus(EndStatus.NO_WORK_TIME);
				return outputTimeReflectForWorkinfo;
			}
		}
		outputTimeReflectForWorkinfo.setEndStatus(EndStatus.NORMAL);

		return outputTimeReflectForWorkinfo;

	}
	
	
}
