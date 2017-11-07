package nts.uk.ctx.at.request.dom.application.overtime.service;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SEmpHistImport;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmployWorkType;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSettingRepository;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestAppDetailSetting;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborCondition;
import nts.uk.ctx.at.shared.dom.worktime.WorkTime;
import nts.uk.ctx.at.shared.dom.worktime.WorkTimeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

@Stateless
public class OvertimeServiceImpl implements OvertimeService {
	
	@Inject
	private EmployeeRequestAdapter employeeAdapter;
	@Inject
	private AppEmploymentSettingRepository appEmploymentSettingRepository;
	@Inject
	private WorkTypeRepository workTypeRepository;
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	@Inject
	private WorkTimeRepository workTimeRepository;

	@Override
	public int checkOvertime(String url) {
		
		return 0;
	}

	@Override
	public WorkTypeOvertime getWorkType(String companyID, String employeeID,
			Optional<PersonalLaborCondition> personalLablorCodition,
			Optional<RequestAppDetailSetting> requestAppDetailSetting) {
		WorkTypeOvertime result = new WorkTypeOvertime();
		if (requestAppDetailSetting.isPresent()) {
			// 時刻計算利用チェック
			if (requestAppDetailSetting.get().getTimeCalUseAtr().value == UseAtr.USE.value) {
				// アルゴリズム「社員所属雇用履歴を取得」を実行する 
				SEmpHistImport sEmpHistImport = employeeAdapter.getEmpHist(companyID, employeeID, GeneralDate.today());
				if (sEmpHistImport != null) {
					// ドメインモデル「申請別対象勤務種類」を取得
					List<AppEmployWorkType> employWorkTypes = this.appEmploymentSettingRepository.getEmploymentWorkType(
							companyID, sEmpHistImport.getEmploymentCode(), ApplicationType.OVER_TIME_APPLICATION.value);
					if (employWorkTypes != null) {
						// ドメインモデル「申請別対象勤務種類」.勤務種類リストを表示する(hien thi list(申請別対象勤務種類))
					} else {
						/*
						 * ドメインモデル「勤務種類」を取得 anh chinh lam
						 */
						
						/*
						 * ドメインモデル「個人労働条件」を取得する(lay dieu kien lao dong ca nhan(個人労働条件))
						 * personalLablorCodition
						 */
						if(personalLablorCodition.isPresent()){
							//ドメインモデル「個人勤務日区分別勤務」．平日時．勤務種類コードを選択する(chọn cai loai di lam)
							Optional<WorkType> workType = workTypeRepository.findByPK(companyID, personalLablorCodition.get().getWorkCategory().getWeekdayTime().getWorkTypeCode().toString());
							result.setWorkTypeCode(personalLablorCodition.get().getWorkCategory().getWeekdayTime().getWorkTypeCode().toString());
							result.setWorkTypeName(workType.get().getName().toString());
						}
						//先頭の勤務種類を選択する(chon cai dau tien trong list loai di lam)
					}
				}
			}
		}
		
		return result;
	}

	@Override
	public SiftType getSiftType(String companyID, String employeeID,
			Optional<PersonalLaborCondition> personalLablorCodition,
			Optional<RequestAppDetailSetting> requestAppDetailSetting) {
		SiftType result = new SiftType();
		if (requestAppDetailSetting.isPresent()) {
			// 時刻計算利用チェック
			if (requestAppDetailSetting.get().getTimeCalUseAtr().value == UseAtr.USE.value) {
				// 1.職場別就業時間帯を取得
				List<String> listWorkTimeCodes = otherCommonAlgorithm.getWorkingHoursByWorkplace(companyID, employeeID, GeneralDate.today());
				/*
				 * ドメインモデル「個人労働条件」を取得する(lay dieu kien lao dong ca nhan(個人労働条件))
				 * personalLaborConditionRepository
				 */
				if(personalLablorCodition.isPresent()){
					Optional<WorkTime> workTime =  workTimeRepository.findByCode(companyID,personalLablorCodition.get().getWorkCategory().getWeekdayTime().getWorkTimeCode().get().toString());
					result.setSiftCode(personalLablorCodition.get().getWorkCategory().getWeekdayTime().getWorkTimeCode().get().toString());
					result.setSiftName(workTime.get().getWorkTimeDisplayName().toString());
				}else{
					Optional<WorkTime> workTime =  workTimeRepository.findByCode(companyID,listWorkTimeCodes.get(0));
					result.setSiftCode(listWorkTimeCodes.get(0));
					result.setSiftName(workTime.get().getWorkTimeDisplayName().toString());
				}
			}
		}
		return result;
	}

	

	

}
