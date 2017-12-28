package nts.uk.ctx.at.request.dom.application.common.datawork;

import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SEmpHistImport;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.AppCommonSettingOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmployWorkType;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.requestofeach.RequestAppDetailSetting;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborCondition;
import nts.uk.ctx.at.shared.dom.personallaborcondition.PersonalLaborConditionRepository;
import nts.uk.ctx.at.shared.dom.worktime_old.WorkTime;
import nts.uk.ctx.at.shared.dom.worktime_old.WorkTimeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

@Stateless
public class DataWorkServiceImpl implements IDataWorkService {
	@Inject
	private EmployeeRequestAdapter employeeAdapter;
	@Inject
	private WorkTypeRepository workTypeRepository;
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	@Inject
	private WorkTimeRepository workTimeRepository;
	@Inject
	private PersonalLaborConditionRepository personalLaborConditionRepository;	
	@Override
	public DataWork getDataWork(String companyId, String sId, GeneralDate appDate, AppCommonSettingOutput appCommonSetting) {
		DataWork dataWork = new DataWork();
		//アルゴリズム「社員所属雇用履歴を取得」を実行する
		List<RequestAppDetailSetting> requestAppDetailSettings = appCommonSetting.requestOfEachCommon
				.getRequestAppDetailSettings();
		if (CollectionUtil.isEmpty(requestAppDetailSettings)) {
			return null;
		}
		List<RequestAppDetailSetting> requestAppDetailSetting = requestAppDetailSettings.stream()
				.filter(c -> c.appType == appCommonSetting.appTypeDiscreteSettings.get(0).getAppType()).collect(Collectors.toList());
		if (CollectionUtil.isEmpty(requestAppDetailSetting)) {
			return null;
		}
		List<AppEmploymentSetting> appEmploymentWorkType = appCommonSetting.appEmploymentWorkType;
		//勤務種類取得
		List<String> workTypeCodes = getWorkType(companyId, sId, requestAppDetailSetting.get(0), appEmploymentWorkType);
		dataWork.setWorkTypeCodes(workTypeCodes);
		//就業時間帯取得
		List<String> workTimeCodes = getSiftType(companyId, sId, requestAppDetailSetting.get(0));
		dataWork.setWorkTimeCodes(workTimeCodes);
		
		//勤務種類就業時間帯の初期選択をセットする
		getSelectedDataWork(companyId, sId, appDate, workTypeCodes, workTimeCodes, dataWork);
		
		return dataWork;
	}
	/**
	 * 勤務種類取得
	 * @param companyID
	 * @param employeeID
	 * @param requestAppDetailSetting
	 * @param appEmploymentSettings
	 * @return
	 */
	public List<String> getWorkType(String companyID, String employeeID,
			RequestAppDetailSetting requestAppDetailSetting, List<AppEmploymentSetting> appEmploymentSettings) {
		List<String> result = new ArrayList<>();
		if (requestAppDetailSetting == null) {
			return result;
		}
		// 時刻計算利用チェック
		// アルゴリズム「社員所属雇用履歴を取得」を実行する 
		SEmpHistImport sEmpHistImport = employeeAdapter.getEmpHist(companyID, employeeID, GeneralDate.today());
		
		if (sEmpHistImport != null 
				&& !CollectionUtil.isEmpty(appEmploymentSettings)) {
			//ドメインモデル「申請別対象勤務種類」.勤務種類リストを表示する
			List<AppEmployWorkType> lstEmploymentWorkType = appEmploymentSettings.get(0).getLstWorkType();
			if(CollectionUtil.isEmpty(lstEmploymentWorkType)) {
				return result;
			}
			Collections.sort(lstEmploymentWorkType, Comparator.comparing(AppEmployWorkType :: getWorkTypeCode));
			List<String> workTypeCodes = new ArrayList<>();
			lstEmploymentWorkType.forEach(x -> {workTypeCodes.add(x.getWorkTypeCode());});			
			result = this.workTypeRepository.findNotDeprecatedByListCode(companyID, workTypeCodes).stream()
					.map(x -> x.getName().v()).collect(Collectors.toList());
			return result;
		}
		List<Integer> allDayAtrs = allDayAtrs();
		List<Integer> halfAtrs = halfAtrs();
		//ドメインモデル「勤務種類」を取得
		result = workTypeRepository.findWorkType(companyID, 0, allDayAtrs, halfAtrs).stream()
				.map(x -> x.getName().v()).collect(Collectors.toList());
		return result;
	}
	/**
	 * 就業時間帯取得
	 * @param companyID
	 * @param employeeID
	 * @param requestAppDetailSetting
	 * @return
	 */
	public List<String> getSiftType(String companyID, String employeeID,
			RequestAppDetailSetting requestAppDetailSetting) {
		if (requestAppDetailSetting == null) {
			return null;
		}
		// 1.職場別就業時間帯を取得
		List<String> listWorkTimeCodes = otherCommonAlgorithm.getWorkingHoursByWorkplace(companyID, employeeID, GeneralDate.today());
		
		if(!CollectionUtil.isEmpty(listWorkTimeCodes)){
			List<WorkTime> workTimes =  workTimeRepository.findByCodes(companyID,listWorkTimeCodes);
			return workTimes.stream().map(item->item.getSiftCD().v()).collect(Collectors.toList());
		}
		return null;
	}
	/**
	 * 勤務種類就業時間帯の初期選択をセットする
	 * @param companyId : 社員ID
	 * @param employeeId
	 * @param baseDate : 基準日
	 * @param workTypes
	 * @param workTimeTypes
	 * @param selectedData
	 */
	private void getSelectedDataWork(String companyId,String employeeId, GeneralDate baseDate,
			List<String> workTypes, List<String> workTimeTypes, DataWork selectedData) {

		//ドメインモデル「個人労働条件」を取得する
		Optional<PersonalLaborCondition> personalLablorCodition = personalLaborConditionRepository.findById(employeeId,baseDate);
		
		if(!personalLablorCodition.isPresent() 
				|| personalLablorCodition.get().getWorkCategory().getWeekdayTime() == null){
			if(!CollectionUtil.isEmpty(workTypes)){
				//先頭の勤務種類を選択する
				selectedData.setSelectedWorkTypeCd(workTypes.get(0));
			}
			if(!CollectionUtil.isEmpty(workTimeTypes)){
				//先頭の就業時間帯を選択する
				selectedData.setSelectedWorkTimeCd(workTimeTypes.get(0));
			}
		}else{
			//ドメインモデル「個人勤務日区分別勤務」．平日時．勤務種類コードを選択する
			Optional<WorkType> workType = workTypeRepository.findByPK(companyId, personalLablorCodition.get().getWorkCategory().getWeekdayTime().getWorkTypeCode().toString());
			selectedData.setSelectedWorkTypeCd(personalLablorCodition.get().getWorkCategory().getWeekdayTime().getWorkTypeCode().v());
			if(workType.isPresent()){
				selectedData.setSelectedWorkTypeName(workType.get().getName().v());
			}
			//ドメインモデル「個人勤務日区分別勤務」．平日時．就業時間帯コードを選択する
			Optional<WorkTime> workTime =  workTimeRepository.findByCode(companyId,personalLablorCodition.get().getWorkCategory().getWeekdayTime().getWorkTimeCode().get().toString());
			selectedData.setSelectedWorkTimeCd(personalLablorCodition.get().getWorkCategory().getWeekdayTime().getWorkTimeCode().get().v());
			selectedData.setSelectedWorkTimeName(workTime.get().getWorkTimeDisplayName().getWorkTimeName().v());
		}
	}
	/**
	 * // １日の勤務＝以下に該当するもの
	 * 　出勤、休出、振出、連続勤務
	 * @return
	 */
	private List<Integer> allDayAtrs(){
		
		List<Integer> allDayAtrs = new ArrayList<>();
		//出勤
		allDayAtrs.add(0);
		//休出
		allDayAtrs.add(11);
		//振出
		allDayAtrs.add(7);
		// 連続勤務
		allDayAtrs.add(10);
		return allDayAtrs;
	}
	/**
	 * 午前 また 午後 in (休日, 振出, 年休, 出勤, 特別休暇, 欠勤, 代休, 時間消化休暇)
	 * @return
	 */
	private List<Integer> halfAtrs(){
		List<Integer> halfAtrs = new ArrayList<>();
		// 休日
		halfAtrs.add(1);
		// 振出
		halfAtrs.add(7);
		// 年休
		halfAtrs.add(8);
		// 出勤
		halfAtrs.add(0);
		//特別休暇
		halfAtrs.add(4);
		// 欠勤
		halfAtrs.add(5);
		// 代休
		halfAtrs.add(6);
		//時間消化休暇
		halfAtrs.add(9);
		return halfAtrs;
	}
}
