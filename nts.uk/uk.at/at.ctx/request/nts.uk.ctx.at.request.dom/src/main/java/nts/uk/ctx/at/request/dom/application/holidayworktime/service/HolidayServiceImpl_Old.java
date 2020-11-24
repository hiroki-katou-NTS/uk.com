package nts.uk.ctx.at.request.dom.application.holidayworktime.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationApprovalService;
import nts.uk.ctx.at.request.dom.application.ApplicationRepository;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.UseAtr;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.EmployeeRequestAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.SEmpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.shift.businesscalendar.specificdate.BusinessDayCalendarAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.shift.businesscalendar.specificdate.dto.BusinessDayCalendarImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.schedule.shift.businesscalendar.specificdate.dto.HolidayClsImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WkpHistImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.CommonOvertimeHoliday;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.PreActualColorCheck;
import nts.uk.ctx.at.request.dom.application.common.ovetimeholiday.PreActualColorResult;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.init.DetailAppCommonSetService;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.PreAppContentDisplay;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.AchievementOutput;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.holidayshipment.brkoffsupchangemng.BrkOffSupChangeMng;
import nts.uk.ctx.at.request.dom.application.holidayshipment.brkoffsupchangemng.BrkOffSupChangeMngRepository;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWork_Old;
import nts.uk.ctx.at.request.dom.application.holidayworktime.AppHolidayWorkRepository_Old;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.AppHdWorkDispInfoOutput_Old;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HdWorkBreakTimeSetOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HdWorkCheckRegisterOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HolidayWorkDetailOutput;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.HolidayWorkInstruction;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.InitWorkTypeWorkTime;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.WorkTimeHolidayWork;
import nts.uk.ctx.at.request.dom.application.holidayworktime.service.dto.WorkTypeHolidayWork;
import nts.uk.ctx.at.request.dom.application.overtime.AppOvertimeDetail;
import nts.uk.ctx.at.request.dom.application.overtime.service.CheckWorkingInfoResult;
import nts.uk.ctx.at.request.dom.application.overtime.service.IOvertimePreProcess;
import nts.uk.ctx.at.request.dom.application.overtime.service.OvertimeService;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.AppDateContradictionAtr;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdworkapplicationsetting.WithdrawalAppSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.overtimerestappcommon.OvertimeRestAppCommonSetRepository;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmployWorkType;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.WorkTypeObjAppHoliday;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngRegisterDateChange;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategory;
import nts.uk.ctx.at.shared.dom.workingcondition.SingleDaySchedule;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.worktime.common.DeductionTime;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.HolidayAtr;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.TimeWithDayAttr;
@Stateless
public class HolidayServiceImpl_Old implements HolidayService_Old {
	@Inject
	private EmployeeRequestAdapter employeeAdapter;
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	@Inject
	private WorkTimeSettingRepository workTimeRepository;
	@Inject
	private WorkTypeRepository workTypeRepository;
	@Inject
	private ApplicationApprovalService appRepository;
	@Inject
	private AppHolidayWorkRepository_Old appHolidayWorkRepository;
	@Inject
	private BusinessDayCalendarAdapter businessDayCalendarAdapter;
	@Inject
	private WorkplaceAdapter wkpAdapter;
	@Inject 
	private BrkOffSupChangeMngRepository brkOffSupChangeMngRepository;
	@Inject
	private ApplicationRepository applicationRepository;
	@Inject
	private InterimRemainDataMngRegisterDateChange interimRemainDataMngRegisterDateChange;
	
	@Inject
	private CommonAlgorithm commonAlgorithm;
	
	@Inject
	private CommonOvertimeHoliday commonOvertimeHoliday;
	
	@Inject
	private HolidayPreProcess holidayPreProcess;
	
	@Inject
	private WorkingConditionItemRepository workingConditionItemRepository;
	
	@Inject
	private HolidayService_Old holidayService;
	
	@Inject
	private PreActualColorCheck preActualColorCheck;
	
	@Inject
	private WithdrawalAppSetRepository withdrawalAppSetRepository;
	
	@Inject
	private IOvertimePreProcess iOvertimePreProcess;
	
	@Inject
	private OvertimeRestAppCommonSetRepository overtimeRestAppCommonSetRepository;
	
	@Inject
	private NewBeforeRegister newBeforeRegister;
	
	@Inject
	private DetailAppCommonSetService detailAppCommonSetService;
	
	@Inject
	private OvertimeService overtimeService;
	
	@Inject
	private DetailBeforeUpdate detailBeforeUpdate;
	
	@Override
	public WorkTypeHolidayWork getWorkTypes(String companyID, String employeeID, List<AppEmploymentSetting> appEmploymentSettings,
			GeneralDate baseDate,Optional<WorkingConditionItem> personalLablorCodition,boolean isChangeDate) {
		WorkTypeHolidayWork workTypeHolidayWorks = new WorkTypeHolidayWork();
		workTypeHolidayWorks = getListWorkType(companyID, employeeID, appEmploymentSettings, baseDate, personalLablorCodition);
		// 勤務種類初期選択 :4_c.初期選択 : TODO
		if(workTypeHolidayWorks.getWorkTypeCodes() == null){
			return workTypeHolidayWorks;
		}
		getWorkType(companyID,workTypeHolidayWorks,baseDate,employeeID,personalLablorCodition,isChangeDate);
		return workTypeHolidayWorks;
	}
	// 4_c.初期選択
	@Override
	public void getWorkType(String companyID,WorkTypeHolidayWork workTypes, GeneralDate appDate, String employeeID,Optional<WorkingConditionItem> personalLablorCodition,boolean isChangeDate){
		
		List<String> wptypes = workTypes.getWorkTypeCodes();
		if(!personalLablorCodition.isPresent() || personalLablorCodition.get().getWorkCategory().getHolidayWork() == null){
			// 先頭の勤務種類を選択する
			if(!CollectionUtil.isEmpty(workTypes.getWorkTypeCodes())){
				workTypes.setWorkTypeCode(workTypes.getWorkTypeCodes().get(0));
			}
		}else{
			
			
			// 「申請日－法定外・法定内休日区分」をチェック　→Imported(申請承認)「対象日法定休日区分.法定休日区分」を取得する - req 253
			String workTypeCode = personalLablorCodition.get().getWorkCategory().getHolidayWork().getWorkTypeCode().get().toString();
			if (!isChangeDate) {
                workTypeCode = getCode(wptypes, workTypeCode);
			}else{
				//Imported(申請承認)「職場ID」を取得する 
				//アルゴリズム「社員から職場を取得する」を実行する - req #30
				WkpHistImport wkp = wkpAdapter.findWkpBySid(employeeID, appDate);
				String workplaceID = "";
				if(wkp !=null){
					workplaceID = wkp.getWorkplaceId();
				}
			Optional<BusinessDayCalendarImport> buOptional = this.businessDayCalendarAdapter.acquiredHolidayClsOfTargetDate(companyID, workplaceID, appDate);
				if (buOptional.isPresent()) {
                    PersonalWorkCategory personCategory = personalLablorCodition.get().getWorkCategory();
                    switch (buOptional.get().holidayCls) {
                    case STATUTORY_HOLIDAYS:
                    	// 申請日＝＞法定内休日
                        workTypeCode = getWorkTypeCode(personCategory.getInLawBreakTime(), workTypeCode);
                        break;
                    case NON_STATUTORY_HOLIDAYS:
                        // 申請日＝＞法定外休日
                        workTypeCode = getWorkTypeCode(personCategory.getOutsideLawBreakTime(), workTypeCode);
                        break;
                    case PUBLIC_HOLIDAY:
                        // 申請日＝＞祝日
                        workTypeCode = getWorkTypeCode(personCategory.getHolidayAttendanceTime(), workTypeCode);
                        break;
                    }
                    workTypeCode = getInList(wptypes, workTypeCode);
				} else {
                    workTypeCode = getCode(wptypes, workTypeCode);
				}
			}
            workTypes.setWorkTypeCode(workTypeCode);
		}
		
		String wkTypeCD = workTypes.getWorkTypeCode();
		//12.マスタ勤務種類、就業時間帯データをチェック
		CheckWorkingInfoResult checkResult = otherCommonAlgorithm.checkWorkingInfo(companyID, wkTypeCD, null);
		if (checkResult.isWkTypeError() && !CollectionUtil.isEmpty(workTypes.getWorkTypeCodes())) {
			wkTypeCD = workTypes.getWorkTypeCodes().get(0);
            workTypes.setWorkTypeCode(wkTypeCD);
		}
		Optional<WorkType> workType = workTypeRepository.findByPK(companyID, wkTypeCD);
		if(workType.isPresent()){
			workTypes.setWorkTypeName(workType.get().getName().toString());
		}
	}
	
    private String getCode(List<String> list, String defaultCode) {
        if (!StringUtil.isNullOrEmpty(defaultCode, true)) {
            return getInList(list, defaultCode);
        } else {
            return list.get(0);
        }
    }

    private String getWorkTypeCode(Optional<SingleDaySchedule> singleDay, String wkTypeCode) {
        if (singleDay.isPresent()) {
            Optional<WorkTypeCode> wkTypeCodeOpt = singleDay.get().getWorkTypeCode();
            return wkTypeCodeOpt.isPresent() ? wkTypeCodeOpt.get().v().toString() : wkTypeCode;
        }
        return wkTypeCode;
    }

	/** 5.就業時間帯を取得する */
	@Override
	public WorkTimeHolidayWork getWorkTimeHolidayWork(String companyID, String employeeID,
			GeneralDate baseDate,Optional<WorkingConditionItem> personalLablorCodition,boolean isChangeDate) {
		WorkTimeHolidayWork workTimeHolidayWork = new WorkTimeHolidayWork();
		// 1.職場別就業時間帯を取得
		List<String> listWorkTimeCodes = otherCommonAlgorithm.getWorkingHoursByWorkplace(companyID, employeeID,baseDate)
				.stream().map(x -> x.getWorktimeCode().v()).collect(Collectors.toList());
		List<String> workTimes = new ArrayList<>();
		if(!CollectionUtil.isEmpty(listWorkTimeCodes)){
			listWorkTimeCodes.forEach(x -> workTimes.add(x));
		}
		workTimeHolidayWork.setWorkTimeCodes(workTimes);
		if(!personalLablorCodition.isPresent() || personalLablorCodition.get().getWorkCategory().getHolidayWork() == null){
			// 先頭の勤務種類を選択する
			if(!CollectionUtil.isEmpty(workTimeHolidayWork.getWorkTimeCodes())){
				workTimeHolidayWork.setWorkTimeCode(workTimeHolidayWork.getWorkTimeCodes().get(0));
			}
		}else{


			// ドメインモデル「個人勤務日区分別勤務.休日出勤時.就業時間帯コード」を選択する
			String wkTimeCode = personalLablorCodition.get().getWorkCategory().getHolidayWork().getWorkTimeCode().get()
					.toString();
			if (!isChangeDate) {
				if (!StringUtil.isNullOrEmpty(wkTimeCode, true)) {
					boolean isInList = workTimes.indexOf(wkTimeCode) != -1;
					if (isInList) {
						workTimeHolidayWork.setWorkTimeCode(wkTimeCode);
					} else {
						workTimeHolidayWork.setWorkTimeCode(workTimes.get(0));
					}

				} else {
					workTimeHolidayWork.setWorkTimeCode(workTimes.get(0));
				}
			} else {

				wkTimeCode = personalLablorCodition.get().getWorkCategory().getHolidayWork().getWorkTimeCode().get()
						.toString();
				// Imported(申請承認)「職場ID」を取得する
				// アルゴリズム「社員から職場を取得する」を実行する - req #30
				WkpHistImport wkp = wkpAdapter.findWkpBySid(employeeID, baseDate);
				String workplaceID = "";
				if (wkp != null) {
					workplaceID = wkp.getWorkplaceId();
				}
				Optional<BusinessDayCalendarImport> buOptional = this.businessDayCalendarAdapter
						.acquiredHolidayClsOfTargetDate(companyID, workplaceID, baseDate);
				if (buOptional.isPresent()) {
					
                    PersonalWorkCategory personCategory = personalLablorCodition.get().getWorkCategory();
                    switch (buOptional.get().holidayCls) {
                    case STATUTORY_HOLIDAYS:
                        // 申請日＝＞法定内休日
                        wkTimeCode = getWorkTimeCode(personCategory.getInLawBreakTime(), wkTimeCode);
                        break;
                    case NON_STATUTORY_HOLIDAYS:
                        // 申請日＝＞法定外休日
                        wkTimeCode = getWorkTimeCode(personCategory.getOutsideLawBreakTime(), wkTimeCode);
                        break;
                    case PUBLIC_HOLIDAY:
                        // 申請日＝＞祝日
                        wkTimeCode = getWorkTimeCode(personCategory.getHolidayAttendanceTime(), wkTimeCode);
                        break;

                    }
                    wkTimeCode = getInList(workTimes, wkTimeCode);

				} else {
                    wkTimeCode = getCode(workTimes, wkTimeCode);
				}
			}
            workTimeHolidayWork.setWorkTimeCode(wkTimeCode);
		}

		if (workTimeHolidayWork.getWorkTimeCode() != null) {
			WorkTimeSetting workTime = workTimeRepository.findByCode(companyID, workTimeHolidayWork.getWorkTimeCode())
					.orElseGet(() -> {
						return workTimeRepository.findByCompanyId(companyID).get(0);
					});
			if (workTime != null) {
				workTimeHolidayWork.setWorkTimeName(workTime.getWorkTimeDisplayName().getWorkTimeName().toString());
			}

			String wkTimeCode = workTimeHolidayWork.getWorkTimeCode();
			// 12.マスタ勤務種類、就業時間帯データをチェック
			CheckWorkingInfoResult checkResult = otherCommonAlgorithm.checkWorkingInfo(companyID, wkTimeCode, null);
			if (checkResult.isWkTimeError()) {
				workTimeHolidayWork.setWorkTimeCode(workTimeHolidayWork.getWorkTimeCodes().get(0));
			}
			if (workTimeHolidayWork.getWorkTimeCode() != null) {
				workTimeRepository.findByCode(companyID, workTimeHolidayWork.getWorkTimeCode()).ifPresent(wkTime -> {
					workTimeHolidayWork.setWorkTimeName(wkTime.getWorkTimeDisplayName().getWorkTimeName().toString());
				});
			}
		}
		return workTimeHolidayWork;
		
	}
    private String getInList(List<String> list, String defaultCode) {
        if(CollectionUtil.isEmpty(list)){
            return defaultCode;
        }
        boolean isInList = list.indexOf(defaultCode) != -1;
        if (isInList) {
            return defaultCode;
        } else {
            return list.get(0);
        }

    }
    private String getWorkTimeCode(Optional<SingleDaySchedule> singleDay, String defaultCode) {
        if (singleDay.isPresent()) {
            Optional<nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode> wkTimeCodeOpt = singleDay.get()
                    .getWorkTimeCode();
            return wkTimeCodeOpt.isPresent() ? wkTimeCodeOpt.get().v().toString() : defaultCode;
        }
        return defaultCode;
    }

	@Override
	public void createHolidayWork(AppHolidayWork_Old domain, Application newApp) {
		//Register application
		// error EA refactor 4
		/*appRepository.insert(newApp);*/
		// insert appHolidayWork,HolidayWorkInput
		appHolidayWorkRepository.Add(domain);
	}
	@Override
	public WorkTypeHolidayWork getListWorkType(String companyID, String employeeID,
			List<AppEmploymentSetting> appEmploymentSettings, GeneralDate appDate,
			Optional<WorkingConditionItem> personalLablorCodition) {
		WorkTypeHolidayWork workTypeHolidayWorks = new WorkTypeHolidayWork();
		// アルゴリズム「社員所属雇用履歴を取得」を実行する 
		SEmpHistImport sEmpHistImport = employeeAdapter.getEmpHist(companyID, employeeID, GeneralDate.today());
		List<String> workTypeCodes = new ArrayList<>();
		if(sEmpHistImport != null && !CollectionUtil.isEmpty(appEmploymentSettings) && appEmploymentSettings.get(0) != null){
			// ドメインモデル「申請別対象勤務種類」.勤務種類リストを表示する
			AppEmploymentSetting appSet =  appEmploymentSettings.get(0);
			List<AppEmployWorkType> lstEmploymentWorkType = CollectionUtil.isEmpty(appEmploymentSettings.get(0).getListWTOAH()) ? null : 
				CollectionUtil.isEmpty(appEmploymentSettings.get(0).getListWTOAH().get(0).getWorkTypeList()) ? null :
					appEmploymentSettings.get(0).getListWTOAH().get(0).getWorkTypeList()
					.stream().map(x -> new AppEmployWorkType(companyID, employeeID, appEmploymentSettings.get(0).getListWTOAH().get(0).getAppType(),
							appEmploymentSettings.get(0).getListWTOAH().get(0).getAppType().value == 10 ? appEmploymentSettings.get(0).getListWTOAH().get(0).getSwingOutAtr().get().value : appEmploymentSettings.get(0).getListWTOAH().get(0).getAppType().value == 1 ? appEmploymentSettings.get(0).getListWTOAH().get(0).getHolidayAppType().get().value : 9, x))
					.collect(Collectors.toList());
			boolean isDisplay = false;
			if(!CollectionUtil.isEmpty(appSet.getListWTOAH())) {
				isDisplay = appSet.getListWTOAH().get(0).getWorkTypeSetDisplayFlg();				
			}
			if(!CollectionUtil.isEmpty(lstEmploymentWorkType) && isDisplay) {
                List<String> sortedCodes = this.workTypeRepository
                        .getPossibleWorkTypeAndOrder(companyID,
                                lstEmploymentWorkType.stream().map(x -> x.getWorkTypeCode())
                                        .collect(Collectors.toList()))
                        .stream().map(x -> x.getWorkTypeCode()).collect(Collectors.toList());
                //Collections.sort(lstEmploymentWorkType, Comparator.comparing(AppEmployWorkType :: getWorkTypeCode));
                sortedCodes.forEach(x -> {
                    workTypeCodes.add(x);
                });
				workTypeHolidayWorks.setWorkTypeCodes(workTypeCodes);
				return workTypeHolidayWorks;
			}
		}
		////休出
		int breakDay = 11;
		// ドメインモデル「勤務種類」を取得
		List<WorkType> workrTypes = this.workTypeRepository.findWorkOneDay(companyID, 0, breakDay);
		List<String> sortedCodes = this.workTypeRepository
				.getPossibleWorkTypeAndOrder(companyID,
						workrTypes.stream().map(x -> x.getWorkTypeCode().v()).collect(Collectors.toList()))
				.stream().map(x -> x.getWorkTypeCode()).collect(Collectors.toList());
		if (!CollectionUtil.isEmpty(sortedCodes)) {
			sortedCodes.forEach(x -> {
				workTypeCodes.add(x);
			});
			workTypeHolidayWorks.setWorkTypeCodes(workTypeCodes);
			return workTypeHolidayWorks;
		}
		return workTypeHolidayWorks;
	}
	@Override
	// 4_a.勤務種類を取得する（法定内外休日）
	public WorkTypeHolidayWork getWorkTypeForLeaverApp(String companyID, String employeeID,
			List<AppEmploymentSetting> appEmploymentSettings, GeneralDate appDate,
			Optional<WorkingConditionItem> personalLablorCodition,Integer paramholidayCls ) {
		WorkTypeHolidayWork workTypeHolidayWorks = new WorkTypeHolidayWork();
		workTypeHolidayWorks = this.getListWorkType(companyID, employeeID, appEmploymentSettings, appDate, personalLablorCodition);
		if(CollectionUtil.isEmpty(workTypeHolidayWorks.getWorkTypeCodes())){
			return workTypeHolidayWorks;
		}
		//アルゴリズム「社員から職場を取得する」を実行する - req #30
		WkpHistImport wkp = wkpAdapter.findWkpBySid(employeeID, appDate);
		String workplaceID = "";
		if(wkp !=null){
			workplaceID = wkp.getWorkplaceId();
		}
		List<WorkType> worktypes = this.workTypeRepository.findNotDeprecatedByListCode(companyID, workTypeHolidayWorks.getWorkTypeCodes());
		// 「申請日－法定外・法定内休日区分」をチェック →Imported(申請承認)「対象日法定休日区分.法定休日区分」を取得する - req253
		Optional<BusinessDayCalendarImport> buOptional = this.businessDayCalendarAdapter
				.acquiredHolidayClsOfTargetDate(companyID, workplaceID, appDate);
		List<WorkType> workTypeFilter  = new ArrayList<>();
		if(buOptional.isPresent()) {
			if (HolidayClsImport.STATUTORY_HOLIDAYS.equals(buOptional.get().holidayCls)) {
				// 法定内休日 : filter for STATUTORY_HOLIDAYS
				workTypeFilter = worktypes.stream().filter(x -> x.getWorkTypeSet().getHolidayAtr().equals(HolidayAtr.STATUTORY_HOLIDAYS)).collect(Collectors.toList());
			} else if (HolidayClsImport.NON_STATUTORY_HOLIDAYS.equals(buOptional.get().holidayCls)) {
				// 法定外休日
				workTypeFilter = worktypes.stream().filter(x -> x.getWorkTypeSet().getHolidayAtr().equals(HolidayAtr.NON_STATUTORY_HOLIDAYS)).collect(Collectors.toList());
			} else if (HolidayClsImport.PUBLIC_HOLIDAY.equals(buOptional.get().holidayCls)) {
				// 祝日
				workTypeFilter = worktypes.stream().filter(x -> x.getWorkTypeSet().getHolidayAtr().equals(HolidayAtr.PUBLIC_HOLIDAY)).collect(Collectors.toList());
			}else{
				// 取得できない場合
				return getWorkTypeForLeaveApp(workTypeHolidayWorks,companyID);
			}
		}else {
			// 取得できない場合
			return getWorkTypeForLeaveApp(workTypeHolidayWorks,companyID);
		}
		WorkTypeHolidayWork result = new WorkTypeHolidayWork();
		if(!CollectionUtil.isEmpty(workTypeFilter)){
			List<String> workTypeCodes = new ArrayList<>();
			workTypeCodes.forEach(x -> workTypeCodes.add(x));
			result.setWorkTypeCodes(workTypeCodes);
		}
		if(!personalLablorCodition.isPresent() || personalLablorCodition.get().getWorkCategory().getHolidayWork() == null){
			return getWorkTypeForLeaveApp(result,companyID);
		}
		if(paramholidayCls == null){
			return getWorkTypeForLeaveApp(result,companyID);
		}
		// 「元の振出日－法定外・法定内休日区分」をチェック
		String workTypeCode = personalLablorCodition.get().getWorkCategory().getHolidayWork().getWorkTypeCode().toString();
		if (HolidayClsImport.STATUTORY_HOLIDAYS.value == paramholidayCls.intValue()) {
			// 申請日＝＞法定内休日
			if(personalLablorCodition.get().getWorkCategory().getInLawBreakTime().isPresent()){
				workTypeCode = personalLablorCodition.get().getWorkCategory().getInLawBreakTime().get().getWorkTypeCode().toString();
			}
		} else if (HolidayClsImport.NON_STATUTORY_HOLIDAYS.value == paramholidayCls.intValue()) {
			// 申請日＝＞法定外休日
			if(personalLablorCodition.get().getWorkCategory().getOutsideLawBreakTime().isPresent()){
				workTypeCode = personalLablorCodition.get().getWorkCategory().getOutsideLawBreakTime().get().getWorkTypeCode().toString();
			}
		} else if (HolidayClsImport.PUBLIC_HOLIDAY.value == paramholidayCls.intValue()) {
			// 申請日＝＞祝日
			if(personalLablorCodition.get().getWorkCategory().getHolidayAttendanceTime().isPresent()){
				workTypeCode = personalLablorCodition.get().getWorkCategory().getHolidayAttendanceTime().get().getWorkTypeCode().toString();
			}
		}
		result.setWorkTypeCode(workTypeCode);
		Optional<WorkType> workType = workTypeRepository.findByPK(companyID, workTypeCode);
		if(workType.isPresent()){
			result.setWorkTypeName(workType.get().getName().toString());
		}
		return result;
	}
	private WorkTypeHolidayWork getWorkTypeForLeaveApp(WorkTypeHolidayWork workTypeHoliday,String companyID){
		if(CollectionUtil.isEmpty(workTypeHoliday.getWorkTypeCodes())){
			return workTypeHoliday;
		}
		workTypeHoliday.setWorkTypeCode(workTypeHoliday.getWorkTypeCodes().get(0));
		Optional<WorkType> workType = workTypeRepository.findByPK(companyID, workTypeHoliday.getWorkTypeCode());
		if(workType.isPresent()){
			workTypeHoliday.setWorkTypeName(workType.get().getName().toString());
		}
		return workTypeHoliday;
	}
	@Override
	public void delHdWorkByAbsLeaveChange(String appID) {
		String companyID = AppContexts.user().companyId();
		
		// ドメインモデル「振休申請休出変更管理」を取得する
		Optional<BrkOffSupChangeMng> opBrkOffSupChangeMng = brkOffSupChangeMngRepository.findHolidayAppID(appID);
		if(!opBrkOffSupChangeMng.isPresent()){
			return;
		}
		BrkOffSupChangeMng brkOffSupChangeMng = opBrkOffSupChangeMng.get();
		
		// アルゴリズム「振休申請復活」を実行する (9.振休申請復活)
		Application application = applicationRepository.findByID(companyID, appID).get();
		// 「振休振出申請.反映情報.実績反映状態(stateReflectionReal)」を「未反映(notReflected)」に更新する
		// application.getReflectionInformation().setStateReflectionReal(ReflectedState_New.NOTREFLECTED);
		applicationRepository.update(application);
		
		// ドメインモデル「振休申請休出変更管理」を削除する
		brkOffSupChangeMngRepository.remove(brkOffSupChangeMng.getRecAppID(), brkOffSupChangeMng.getAbsenceLeaveAppID());
		
		// 暫定データの登録
		/*
		interimRemainDataMngRegisterDateChange.registerDateChange(
				companyID, 
				application.getEmployeeID(), 
				Arrays.asList(application.getAppDate()));
		*/
	}
	@Override
	public AppHdWorkDispInfoOutput_Old getStartNew(String companyID, List<String> employeeIDLst, List<GeneralDate> dateLst) {
		// error EA refactor 4
		/*AppHdWorkDispInfoOutput result = new AppHdWorkDispInfoOutput();
		// 起動時の申請表示情報を取得する
		AppDispInfoStartupOutput_Old appDispInfoStartupOutput = commonAlgorithm.getAppDispInfoStart( 
				companyID, 
				ApplicationType_Old.BREAK_TIME_APPLICATION, 
				employeeIDLst, 
				dateLst, 
				true);
		// ドメインモデル「休出申請設定」を取得する
		WithdrawalAppSet withdrawalAppSet = withdrawalAppSetRepository.getWithDraw().get();
		
		// 01-02_時間外労働を取得
		Optional<AgreeOverTimeOutput> opAgreeOverTimeOutput = commonOvertimeHoliday.getAgreementTime(
				companyID,
				appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0).getSid(), 
				ApplicationType_Old.BREAK_TIME_APPLICATION);
		// 1-1.休日出勤申請（新規）起動時初期データを取得する
		HdWorkDispInfoWithDateOutput hdWorkDispInfoWithDateOutput = this.initDataNew(
				companyID, 
				appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0).getSid(), 
				dateLst.stream().findFirst(), 
				appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getBaseDate(), 
				appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getPrePostAtr(), 
				appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getEmploymentSet(), 
				appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getWorkTimeLst(), 
				appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getApprovalFunctionSet(), 
				appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getRequestSetting(), 
				appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getAchievementOutputLst());
		// 01-03_休出時間枠を取得
		List<WorkdayoffFrame> breaktimeFrames = iOvertimePreProcess.getBreaktimeFrame(companyID);
		// 01-08_乖離定型理由を取得
		Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet = overtimeRestAppCommonSetRepository
				.getOvertimeRestAppCommonSetting(companyID, ApplicationType_Old.BREAK_TIME_APPLICATION.value);
		List<DivergenceReason> divergenceReasonLst = commonOvertimeHoliday.getDivergenceReasonForm(
				companyID, 
				PrePostAtr_Old.POSTERIOR, 
				overtimeRestAppCommonSet.get().getDivergenceReasonFormAtr(), 
				ApplicationType_Old.BREAK_TIME_APPLICATION);
		
		result.setAppDispInfoStartupOutput(appDispInfoStartupOutput);
		result.setWithdrawalAppSet(withdrawalAppSet);
		result.setAgreeOverTimeOutput(opAgreeOverTimeOutput.orElse(null));
		result.setHdWorkDispInfoWithDateOutput(hdWorkDispInfoWithDateOutput);
		result.setBreaktimeFrames(breaktimeFrames);
		result.setComboDivergenceReason(CollectionUtil.isEmpty(divergenceReasonLst) ? Optional.empty() : Optional.of(divergenceReasonLst));
		result.setOvertimeRestAppCommonSetting(overtimeRestAppCommonSet.get());
		return result;*/
		return null;
	}
//	@Override
//	public HdWorkDispInfoWithDateOutput initDataNew(String companyID, String employeeID, Optional<GeneralDate> appDate,
//			GeneralDate baseDate, PrePostAtr prePostAtr, AppEmploymentSetting appEmploymentSetting,
//			List<WorkTimeSetting> workTimeLst, ApprovalFunctionSetting approvalFunctionSet,
//			RequestSetting requestSetting, List<AchievementOutput> achievementOutputLst) {
//		HdWorkDispInfoWithDateOutput result = new HdWorkDispInfoWithDateOutput();
//		// 01-01_休出通知情報を取得
//		HolidayWorkInstruction holidayWorkInstruction = holidayPreProcess.getHolidayInstructionInformation(
//				approvalFunctionSet.getInstructionUseSetting().getInstructionUseDivision(), 
//				appDate.orElse(null), 
//				employeeID);
//		// 1-2.起動時勤務種類リストを取得する
//		List<WorkType> workTypeLst = this.getWorkTypeLstStart(companyID, appEmploymentSetting);
//		// 1-3.起動時勤務種類・就業時間帯の初期選択
//		InitWorkTypeWorkTime initWorkTypeWorkTime = this.initWorkTypeWorkTime(
//				companyID, 
//				employeeID, 
//				appDate, 
//				baseDate, 
//				workTypeLst, 
//				workTimeLst, 
//				achievementOutputLst, 
//				appEmploymentSetting);
//		// 01-14_勤務時間取得(01 - 14 _ Working hours acquired)
//		RecordWorkOutput recordWorkOutput = commonOvertimeHoliday.getWorkingHours(
//				companyID, 
//				employeeID, 
//				appDate.orElse(null),
//				approvalFunctionSet.getApplicationDetailSetting().get().getTimeCalUse(),
//				approvalFunctionSet.getApplicationDetailSetting().get().getAtworkTimeBeginDisp(),
//				ApplicationType.HOLIDAY_WORK_APPLICATION,
//				initWorkTypeWorkTime.getWorkTimeCD(),
//				Optional.empty(),
//				Optional.empty(),
//				approvalFunctionSet);
//		// 01-01_休憩時間を取得する
//		HdWorkBreakTimeSetOutput hdWorkBreakTimeSetOutput = this.getBreakTime(
//				companyID, 
//				ApplicationType.HOLIDAY_WORK_APPLICATION, 
//				initWorkTypeWorkTime.getWorkTypeCD(), 
//				initWorkTypeWorkTime.getWorkTimeCD(), 
//				Optional.empty(), 
//				Optional.empty(), 
//				approvalFunctionSet.getApplicationDetailSetting().get().getTimeCalUse(), 
//				approvalFunctionSet.getApplicationDetailSetting().get().getBreakInputFieldDisp());
//		/*// 01-04_加給時間を取得
//		Optional<OvertimeRestAppCommonSetting> overtimeRestAppCommonSet = overtimeRestAppCommonSetRepository
//				.getOvertimeRestAppCommonSetting(companyID, ApplicationType.BREAK_TIME_APPLICATION.value);
//		List<BonusPayTimeItem> bonusPayTimeItems = commonOvertimeHoliday.getBonusTime(companyID,
//				employeeID,
//				appDate.orElse(null),
//				overtimeRestAppCommonSet.get().getBonusTimeDisplayAtr());*/
//		// INPUT．申請対象日をチェックする
//		ActualStatusCheckResult actualStatusCheckResult = null;
//		if(appDate.isPresent()) {
//			// 07-02_実績取得・状態チェック
//			WithdrawalAppSet withdrawalAppSet = withdrawalAppSetRepository.getWithDraw().get();
//			actualStatusCheckResult = preActualColorCheck.actualStatusCheck(
//					companyID, 
//					employeeID, 
//					appDate.get(), 
//					ApplicationType.HOLIDAY_WORK_APPLICATION, 
//					initWorkTypeWorkTime.getWorkTypeCD(), 
//					initWorkTypeWorkTime.getWorkTimeCD(), 
//					withdrawalAppSet.getOverrideSet(), 
//					Optional.of(withdrawalAppSet.getCalStampMiss()),
//					hdWorkBreakTimeSetOutput.getDeductionTimeLst());
//		}
//		
//		result.setAppHdWorkInstruction(holidayWorkInstruction);
//		result.setWorkTypeLst(CollectionUtil.isEmpty(workTypeLst) ? Optional.empty() : Optional.of(workTypeLst));
//		result.setWorkTypeCD(initWorkTypeWorkTime.getWorkTypeCD());
//		result.setWorkTypeName(initWorkTypeWorkTime.getWorkTypeName());
//		result.setWorkTimeCD(initWorkTypeWorkTime.getWorkTimeCD());
//		result.setWorkTimeName(initWorkTypeWorkTime.getWorkTimeName());
//		result.setStartTime(recordWorkOutput.getStartTime1());
//		result.setEndTime(recordWorkOutput.getEndTime1());
//		result.setDeductionTimeLst(CollectionUtil.isEmpty(hdWorkBreakTimeSetOutput.getDeductionTimeLst()) ? Optional.empty() : Optional.of(hdWorkBreakTimeSetOutput.getDeductionTimeLst()));
//		result.setActualStatus(actualStatusCheckResult == null ? null : actualStatusCheckResult.actualStatus);
//		return result;
//	}
	@Override
	public List<WorkType> getWorkTypeLstStart(String companyID, AppEmploymentSetting appEmploymentSetting) {
		if(appEmploymentSetting == null) {
			// ドメインモデル「勤務種類」を取得
			return workTypeRepository.findNotDeprecated(companyID);
		}
		// INPUT．雇用別申請承認設定．申請別対象勤務種類をチェックする
		Optional<WorkTypeObjAppHoliday> opWorkTypeObjAppHoliday = appEmploymentSetting.getListWTOAH().stream()
				.filter(x -> x.getAppType() == ApplicationType.HOLIDAY_WORK_APPLICATION).findAny();
		if(!opWorkTypeObjAppHoliday.isPresent()) {
			// ドメインモデル「勤務種類」を取得して返す
			return workTypeRepository.findNotDeprecated(companyID);
		}
		if(!opWorkTypeObjAppHoliday.get().getWorkTypeSetDisplayFlg() || CollectionUtil.isEmpty(opWorkTypeObjAppHoliday.get().getWorkTypeList())) {
			// ドメインモデル「勤務種類」を取得して返す
			return workTypeRepository.findNotDeprecated(companyID);
		}
		// INPUT．雇用別申請承認設定．申請別対象勤務種類．勤務種類リストを取得する
		List<String> workTypeCDLst = opWorkTypeObjAppHoliday.get().getWorkTypeList();
		// ドメインモデル「勤務種類」を取得
		return workTypeRepository.findNotDeprecatedByListCode(companyID, workTypeCDLst);
	}
	@Override
	public InitWorkTypeWorkTime initWorkTypeWorkTime(String companyID, String employeeID, Optional<GeneralDate> appDate,
			GeneralDate baseDate, List<WorkType> workTypeLst, List<WorkTimeSetting> workTimeLst,
			List<AchievementOutput> achievementOutputLst, AppEmploymentSetting appEmploymentSetting) {
		InitWorkTypeWorkTime result = new InitWorkTypeWorkTime();
		Optional<WorkingConditionItem> personalLablorCodition = workingConditionItemRepository.getBySidAndStandardDate(employeeID,baseDate);
		// 4_c.初期選択
		WorkTypeHolidayWork workTypes = holidayService.getWorkTypes(companyID, employeeID, Arrays.asList(appEmploymentSetting), baseDate,
				personalLablorCodition, false);
		if (workTypes != null) {
			result.setWorkTypeCD(workTypes.getWorkTypeCode());
			result.setWorkTypeName(workTypes.getWorkTypeName());
		}
		// 5.就業時間帯を取得する
		WorkTimeHolidayWork workTimes = holidayService.getWorkTimeHolidayWork(companyID, employeeID, baseDate, 
				personalLablorCodition, false);
		if (workTimes != null) {
			result.setWorkTimeCD(workTimes.getWorkTimeCode());
			result.setWorkTimeName(workTimes.getWorkTimeName());
		}
		return result;
	}
	@Override
	public HdWorkBreakTimeSetOutput getBreakTime(String companyID, ApplicationType appType, String workTypeCD,
			String workTimeCD, Optional<TimeWithDayAttr> startTime, Optional<TimeWithDayAttr> endTime,
			UseAtr timeCalUse, Boolean breakTimeDisp) {
		HdWorkBreakTimeSetOutput result = new HdWorkBreakTimeSetOutput(false, Collections.emptyList());
		// 01-17_休憩時間帯を表示するか判断
		boolean displayRestTime = commonOvertimeHoliday.getRestTime(
				companyID,
				timeCalUse,
				breakTimeDisp,
				ApplicationType.HOLIDAY_WORK_APPLICATION);
		result.setDisplayRestTime(displayRestTime);
		// 	休憩時間帯表示区分をチェック 
		if(displayRestTime) {
			//	休憩時間帯を取得する
			List<DeductionTime> deductionTimeLst = commonOvertimeHoliday.getBreakTimes(companyID, workTypeCD, workTimeCD, startTime, endTime);
			result.setDeductionTimeLst(deductionTimeLst);
		}
		return result;
	}
	
	@Override
	public HdWorkCheckRegisterOutput checkBeforeRegister(String companyID, AppHdWorkDispInfoOutput_Old appHdWorkDispInfoOutput,
			Application application, boolean agentAtr, AppHolidayWork_Old holidayWorkDomain, int calculateFlg) {
		HdWorkCheckRegisterOutput result = new HdWorkCheckRegisterOutput();
		List<ConfirmMsgOutput> outputLst = new ArrayList<>();
		// 申請全般登録時チェック処理
		// error EA refactor 4
		/*List<ConfirmMsgOutput> confirmMsgLst1 = newBeforeRegister.processBeforeRegister_New(
				companyID, 
				EmploymentRootAtr.APPLICATION, 
				agentAtr, 
				application, 
				null, 
				appHdWorkDispInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getErrorFlag(), 
				Collections.emptyList());
		outputLst.addAll(confirmMsgLst1);*/
		// 3.個別エラーチェック
//		HdWorkCheckRegisterOutput output = this.individualErrorCheck(
//				companyID, 
//				application.getEmployeeID(), 
//				application.getAppDate(), 
//				appHdWorkDispInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getBaseDate(), 
//			 	ApplicationType.HOLIDAY_WORK_APPLICATION, 
//				application, 
//				appHdWorkDispInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getApprovalFunctionSet().getApplicationDetailSetting().get().getTimeCalUse(), 
//				appHdWorkDispInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getApprovalFunctionSet().getApplicationDetailSetting().get().getTimeInputUse(), 
//				appHdWorkDispInfoOutput.getOvertimeRestAppCommonSetting().getAppDateContradictionAtr(), 
//				agentAtr, 
//				true, 
//				appHdWorkDispInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getAchievementOutputLst(), 
//				appHdWorkDispInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getAppDetailContentLst(), 
//				appHdWorkDispInfoOutput.getHdWorkDispInfoWithDateOutput().getAppHdWorkInstruction(), 
//				holidayWorkDomain, 
//				calculateFlg, 
//				appHdWorkDispInfoOutput);
//		outputLst.addAll(output.getConfirmMsgLst());
//		result.setConfirmMsgLst(outputLst);
//		result.setAppOvertimeDetailOtp(output.getAppOvertimeDetailOtp());
		return result;
	}
	
	@Override
	public HdWorkCheckRegisterOutput individualErrorCheck(String companyID, String employeeID, GeneralDate appDate,
			GeneralDate baseDate, ApplicationType appType, Application application, UseAtr timeCalUse,
			UseAtr timeInputUse, AppDateContradictionAtr appDateContradictionAtr, boolean agentAtr, boolean mode,
			List<AchievementOutput> achievementOutputLst, List<PreAppContentDisplay> appDetailContentLst, HolidayWorkInstruction appHdWorkInstruction, 
			AppHolidayWork_Old holidayWorkDomain, int calculateFlg, AppHdWorkDispInfoOutput_Old appHdWorkDispInfoOutput) {
		HdWorkCheckRegisterOutput output = new HdWorkCheckRegisterOutput();
		List<ConfirmMsgOutput> outputLst = new ArrayList<>();
		// 勤務種類、就業時間帯チェックのメッセージを表示
		CheckWorkingInfoResult checkWorkingInfoResult = otherCommonAlgorithm.checkWorkingInfo(
				companyID, 
				holidayWorkDomain.getWorkTypeCode().v(), 
				holidayWorkDomain.getWorkTimeCode().v());
		// 計算ボタン未クリックチェック
		commonOvertimeHoliday.calculateButtonCheck(calculateFlg, timeCalUse);
		// ノートの条件が満たすかチェックする
		PreActualColorResult preActualColorResult = null;
		if(timeCalUse != UseAtr.NOTUSE && timeInputUse == UseAtr.USE) {
//			// 07-01_事前申請状態チェック
//			PreAppCheckResult preAppCheckResult = preActualColorCheck.preAppStatusCheck(
//					companyID, 
//					application.getEmployeeID(), 
//					application.getAppDate(), 
//					ApplicationType.HOLIDAY_WORK_APPLICATION);
//			// 07-02_実績取得・状態チェック
//			ActualStatusCheckResult actualStatusCheckResult = preActualColorCheck.actualStatusCheck(
//					companyID, 
//					application.getEmployeeID(), 
//					application.getAppDate(), 
//					ApplicationType.HOLIDAY_WORK_APPLICATION, 
//					holidayWorkDomain.getWorkTypeCode() == null ? null : holidayWorkDomain.getWorkTypeCode().v(), 
//					holidayWorkDomain.getWorkTimeCode() == null ? null : holidayWorkDomain.getWorkTimeCode().v(), 
//					appHdWorkDispInfoOutput.getWithdrawalAppSet().getOverrideSet(), 
//					Optional.empty(),
//					Collections.emptyList());
//			// 07_事前申請・実績超過チェック(07_đơn xin trước. check vượt quá thực tế )
//			Map<AttendanceType, List<HolidayWorkInput>> findMap = holidayWorkDomain.getHolidayWorkInputs().stream()
//					.collect(groupingBy(HolidayWorkInput::getAttendanceType));
//			List<HolidayWorkInput> holidayWorkInputs = new ArrayList<>(); 
//			holidayWorkInputs.addAll(CollectionUtil.isEmpty(findMap.get(AttendanceType.BREAKTIME)) ? Collections.emptyList() : findMap.get(AttendanceType.BREAKTIME));
//			List<OvertimeColorCheck> holidayTimeLst = new ArrayList<>();
//			List<WorkdayoffFrame> breaktimeFrames = appHdWorkDispInfoOutput.getBreaktimeFrames();
//			for(WorkdayoffFrame breaktimeFrame :breaktimeFrames){
//				holidayTimeLst.add(OvertimeColorCheck.createApp(
//						AttendanceType.BREAKTIME.value, 
//						breaktimeFrame.getWorkdayoffFrNo().v().intValueExact(), 
//						null));
//			}
//			holidayTimeLst = holidayTimeLst.stream().map(x -> {
//				Integer value = holidayWorkInputs.stream()
//				.filter(y -> y.getAttendanceType().value==x.attendanceID && y.getFrameNo()==x.frameNo)
//				.findAny().map(z -> z.getApplicationTime().v()).orElse(null);
//				return OvertimeColorCheck.createApp(x.attendanceID, x.frameNo, value);
//			}).collect(Collectors.toList());
//			preActualColorResult = preActualColorCheck.preActualColorCheck(
//					appHdWorkDispInfoOutput.getOvertimeRestAppCommonSetting().getPreExcessDisplaySetting(), 
//					appHdWorkDispInfoOutput.getOvertimeRestAppCommonSetting().getPerformanceExcessAtr(), 
//					ApplicationType.HOLIDAY_WORK_APPLICATION, 
//					application.getPrePostAtr(), 
//					Collections.emptyList(), 
//					holidayTimeLst,
//					preAppCheckResult.opAppBefore,
//					preAppCheckResult.beforeAppStatus,
//					actualStatusCheckResult.actualLst,
//					actualStatusCheckResult.actualStatus);
		}
		if(preActualColorResult!=null) {
			// 03-01_事前申請超過チェック（＃108410）
			List<ConfirmMsgOutput> outputLst1 = commonOvertimeHoliday.preAppExcessCheckHdApp(
					appHdWorkDispInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().getEmployeeInfoLst().stream().findFirst().get().getBussinessName(), 
					appDate, 
					preActualColorResult, 
					appHdWorkDispInfoOutput.getBreaktimeFrames());
			outputLst.addAll(outputLst1);
			// 03-02_実績超過チェック（＃108410）
			List<ConfirmMsgOutput> outputLst2 = commonOvertimeHoliday.achievementCheckHdApp(
					appHdWorkDispInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().getEmployeeInfoLst().stream().findFirst().get().getBussinessName(), 
					appDate, 
					appHdWorkDispInfoOutput.getOvertimeRestAppCommonSetting().getPerformanceExcessAtr(), 
					preActualColorResult, 
					appHdWorkDispInfoOutput.getBreaktimeFrames());
			outputLst.addAll(outputLst2);
		}
		Optional<AppOvertimeDetail> appOvertimeDetailOtp = Optional.empty();
		// INPUT．入力モードをチェックする
//		if(mode) {
//			// ３６協定時間上限チェック（月間）
//			appOvertimeDetailOtp = commonOvertimeHoliday.registerHdWorkCheck36TimeLimit(
//					companyID, 
//					application.getEmployeeID(), 
//					application.getAppDate(), 
//					holidayWorkDomain.getHolidayWorkInputs());
//			List<ConfirmMsgOutput> outputLst3 = commonOvertimeHoliday.inconsistencyCheck(
//					companyID, 
//					application.getEmployeeID(), 
//					application.getAppDate(),
//					ApplicationType.HOLIDAY_WORK_APPLICATION,
//					appHdWorkDispInfoOutput.getOvertimeRestAppCommonSetting().getAppDateContradictionAtr());
//			outputLst.addAll(outputLst3);
//			
//		} else {
//			// ３６上限チェック(詳細)
//			appOvertimeDetailOtp = commonOvertimeHoliday.updateHdWorkCheck36TimeLimit(
//					companyID, 
//					application.getAppID(), 
//					application.getEnteredPersonID(), 
//					application.getEmployeeID(),
//					application.getAppDate(), 
//					holidayWorkDomain.getHolidayWorkInputs());
//		}
		output.setConfirmMsgLst(outputLst);
		output.setAppOvertimeDetailOtp(appOvertimeDetailOtp);
		return output;
	}
	@Override
	public HolidayWorkDetailOutput findDetailByID(String companyID, String appID) {
		// refactor 4 error
		/*HolidayWorkDetailOutput result = new HolidayWorkDetailOutput();
		AppHdWorkDispInfoOutput appHdWorkDispInfoOutput = new AppHdWorkDispInfoOutput();
		HdWorkDispInfoWithDateOutput hdWorkDispInfoWithDateOutput = new HdWorkDispInfoWithDateOutput();
		// 詳細画面起動前申請共通設定を取得する
		AppDispInfoStartupOutput_Old appDispInfoStartupOutput = detailAppCommonSetService.getCommonSetBeforeDetail(companyID, appID);
		// ドメインモデル「休日出勤申請」を取得する
		Optional<AppHolidayWork> opAppHolidayWork = appHolidayWorkRepository.getFullAppHolidayWork(companyID, appID);
		if(!opAppHolidayWork.isPresent()){
			throw new BusinessException("Msg_198");
		}
		AppHolidayWork appHolidayWork = opAppHolidayWork.get();
		// ドメインモデル「休出申請設定」を取得する
		WithdrawalAppSet withdrawalAppSet = withdrawalAppSetRepository.getWithDraw().get();
		OvertimeRestAppCommonSetting overtimeRestAppCommonSet = overtimeRestAppCommonSetRepository
				.getOvertimeRestAppCommonSetting(companyID, ApplicationType_Old.BREAK_TIME_APPLICATION.value).get();
		// 申請表示情報(基準日関係あり)．事前事後区分=取得した「申請」．事前事後区分
		appDispInfoStartupOutput.getAppDispInfoWithDateOutput().setPrePostAtr(appHolidayWork.getApplication().getPrePostAtr());
		// 01-03_休出時間枠を取得
		List<WorkdayoffFrame> breaktimeFrames = iOvertimePreProcess.getBreaktimeFrame(companyID);
		// 01-01_休出通知情報を取得
		HolidayWorkInstruction holidayWorkInstruction = holidayPreProcess.getHolidayInstructionInformation(
				appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getApprovalFunctionSet().getInstructionUseSetting().getInstructionUseDivision(), 
				appHolidayWork.getApplication().getAppDate(), 
				appHolidayWork.getApplication().getEmployeeID());
		// 1-2.起動時勤務種類リストを取得する
		List<WorkType> workTypeLst = this.getWorkTypeLstStart(
				companyID, 
				appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getEmploymentSet());
		// 起動時の36協定時間の状態を取得する
		AgreementTimeStatusOfMonthly agreementTimeStatusOfMonthly = null;
		if(appHolidayWork.getAppOvertimeDetail().isPresent()) {
			agreementTimeStatusOfMonthly = overtimeService.getTime36Detail(appHolidayWork.getAppOvertimeDetail().get());
		}
		// 取得した「申請」．事前事後区分をチェックする
		if(appHolidayWork.getApplication().getPrePostAtr() == PrePostAtr_Old.POSTERIOR) {
			List<DeductionTime> deductionTimeLst = appHolidayWork.getHolidayWorkInputs().stream()
					.filter(x -> x.getAttendanceType() == AttendanceType.RESTTIME)
					.map(x -> new DeductionTime(new TimeWithDayAttr(x.getStartTime().v()), new TimeWithDayAttr(x.getEndTime().v())))
					.collect(Collectors.toList());
			// 07-01_事前申請状態チェック
			PreAppCheckResult preAppCheckResult = preActualColorCheck.preAppStatusCheck(
					companyID, 
					appHolidayWork.getApplication().getEmployeeID(), 
					appHolidayWork.getApplication().getAppDate(), 
					ApplicationType_Old.BREAK_TIME_APPLICATION);
			ActualStatusCheckResult actualStatusCheckResult = null;
			// 取得した「申請詳細画面情報」．利用者をチェックする
			User user = appDispInfoStartupOutput.getAppDetailScreenInfo().get().getUser();
			if(user == User.APPROVER || user == User.OTHER) {
				// 07-02_実績取得・状態チェック
				actualStatusCheckResult = preActualColorCheck.actualStatusCheck(
						companyID, 
						appHolidayWork.getApplication().getEmployeeID(), 
						appHolidayWork.getApplication().getAppDate(), 
						ApplicationType_Old.BREAK_TIME_APPLICATION, 
						appHolidayWork.getWorkTypeCode() == null ? null : appHolidayWork.getWorkTypeCode().v(), 
						appHolidayWork.getWorkTimeCode() == null ? null : appHolidayWork.getWorkTimeCode().v(), 
						OverrideSet.TIME_OUT_PRIORITY, 
						Optional.of(CalcStampMiss.CAN_NOT_REGIS),
						deductionTimeLst);
			} else {
				// 07-02_実績取得・状態チェック
				actualStatusCheckResult = preActualColorCheck.actualStatusCheck(
						companyID, 
						appHolidayWork.getApplication().getEmployeeID(), 
						appHolidayWork.getApplication().getAppDate(), 
						ApplicationType_Old.BREAK_TIME_APPLICATION, 
						appHolidayWork.getWorkTypeCode() == null ? null : appHolidayWork.getWorkTypeCode().v(), 
						appHolidayWork.getWorkTimeCode() == null ? null : appHolidayWork.getWorkTimeCode().v(), 
						withdrawalAppSet.getOverrideSet(), 
						Optional.of(withdrawalAppSet.getCalStampMiss()),
						deductionTimeLst);
			}
			// 07_事前申請・実績超過チェック(07_đơn xin trước. check vượt quá thực tế )
			Map<AttendanceType, List<HolidayWorkInput>> findMap = appHolidayWork.getHolidayWorkInputs().stream()
					.collect(groupingBy(HolidayWorkInput::getAttendanceType));
			List<HolidayWorkInput> holidayWorkInputs = new ArrayList<>(); 
			holidayWorkInputs.addAll(CollectionUtil.isEmpty(findMap.get(AttendanceType.BREAKTIME)) ? Collections.emptyList() : findMap.get(AttendanceType.BREAKTIME));
			List<OvertimeColorCheck> holidayTimeLst = new ArrayList<>();
			for(WorkdayoffFrame breaktimeFrame :breaktimeFrames){
				holidayTimeLst.add(OvertimeColorCheck.createApp(
						AttendanceType.BREAKTIME.value, 
						breaktimeFrame.getWorkdayoffFrNo().v().intValueExact(), 
						null));
			}
			holidayTimeLst = holidayTimeLst.stream().map(x -> {
				Integer value = holidayWorkInputs.stream()
				.filter(y -> y.getAttendanceType().value==x.attendanceID && y.getFrameNo()==x.frameNo)
				.findAny().map(z -> z.getApplicationTime().v()).orElse(null);
				return OvertimeColorCheck.createApp(x.attendanceID, x.frameNo, value);
			}).collect(Collectors.toList());
			PreActualColorResult preActualColorResult = preActualColorCheck.preActualColorCheck(
					overtimeRestAppCommonSet.getPreExcessDisplaySetting(), 
					overtimeRestAppCommonSet.getPerformanceExcessAtr(), 
					ApplicationType_Old.BREAK_TIME_APPLICATION, 
					PrePostAtr_Old.POSTERIOR, 
					Collections.emptyList(), 
					holidayTimeLst,
					preAppCheckResult.opAppBefore,
					preAppCheckResult.beforeAppStatus,
					actualStatusCheckResult.actualLst,
					actualStatusCheckResult.actualStatus);
			boolean beforeAppStatus = preActualColorResult.beforeAppStatus;
			Integer actualStatus = preActualColorResult.actualStatus;
			List<CaculationTime> caculationTimes = preActualColorResult.resultLst.stream()
					.map(x -> new CaculationTime(companyID, appID, x.attendanceID, x.frameNo, 0, "", x.appTime,
							x.preAppTime == null ? null : x.preAppTime.toString(),
							x.actualTime == null ? null : x.actualTime.toString(),
							this.getErrorCode(x.calcError, x.preAppError, x.actualError), false,
							beforeAppStatus, actualStatus == 3))
					.collect(Collectors.toList());
			result.setCaculationTimes(caculationTimes);
			result.setPreActualColorResult(preActualColorResult);
		}
		
		// 1-3.起動時勤務種類・就業時間帯の初期選択
		InitWorkTypeWorkTime initWorkTypeWorkTime = this.initWorkTypeWorkTime(
				appHolidayWork.getCompanyID(), 
				appHolidayWork.getApplication().getEmployeeID(), 
				Optional.of(appHolidayWork.getApplication().getAppDate()), 
				appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getBaseDate(), 
				workTypeLst, 
				appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getWorkTimeLst(), 
				appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getAchievementOutputLst(), 
				appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getEmploymentSet());
		hdWorkDispInfoWithDateOutput.setWorkTypeCD(initWorkTypeWorkTime.getWorkTypeCD());
		hdWorkDispInfoWithDateOutput.setWorkTypeName(initWorkTypeWorkTime.getWorkTypeName());
		hdWorkDispInfoWithDateOutput.setWorkTimeCD(initWorkTypeWorkTime.getWorkTimeCD());
		hdWorkDispInfoWithDateOutput.setWorkTimeName(initWorkTypeWorkTime.getWorkTimeName());
		
		hdWorkDispInfoWithDateOutput.setAppHdWorkInstruction(holidayWorkInstruction);
		hdWorkDispInfoWithDateOutput.setWorkTypeLst(CollectionUtil.isEmpty(workTypeLst) ? Optional.empty() : Optional.of(workTypeLst));
		hdWorkDispInfoWithDateOutput.setAgreementTimeStatusOfMonthly(agreementTimeStatusOfMonthly);
		
		appHdWorkDispInfoOutput.setAppDispInfoStartupOutput(appDispInfoStartupOutput);
		appHdWorkDispInfoOutput.setWithdrawalAppSet(withdrawalAppSet);
		appHdWorkDispInfoOutput.setBreaktimeFrames(breaktimeFrames);
		appHdWorkDispInfoOutput.setOvertimeRestAppCommonSetting(overtimeRestAppCommonSet);
		appHdWorkDispInfoOutput.setHdWorkDispInfoWithDateOutput(hdWorkDispInfoWithDateOutput);
		
		result.setAppHdWorkDispInfoOutput(appHdWorkDispInfoOutput);
		result.setAppHolidayWork(appHolidayWork);
		return result;*/
		return null;
	}
	
	private int getErrorCode(int calcError, int preAppError, int actualError){
        if(actualError > preAppError) {
            if(actualError > calcError) {
                return actualError;
            } else {
                return calcError;
            }
        } else {
            if(preAppError > calcError) {
                return preAppError;
            } else {
                return calcError;
            }
        }
    }
	@Override
	public HdWorkCheckRegisterOutput checkBeforeUpdate(String companyID, Application application, AppHdWorkDispInfoOutput_Old appHdWorkDispInfoOutput,
			int calculateFlg, AppHolidayWork_Old holidayWorkDomain) {
		HdWorkCheckRegisterOutput result = new HdWorkCheckRegisterOutput();
		List<ConfirmMsgOutput> outputLst = new ArrayList<>();
		// 4-1.詳細画面登録前の処理
		// error EA refactor 4
		/*detailBeforeUpdate.processBeforeDetailScreenRegistration(
				companyID, 
				application.getEmployeeID(), 
				application.getAppDate(), 
				1, 
				application.getAppID(), 
				application.getPrePostAtr(), 
				application.getVersion(),
				holidayWorkDomain.getWorkTypeCode() == null ? null : holidayWorkDomain.getWorkTypeCode().v(),
				holidayWorkDomain.getWorkTimeCode() == null ? null : holidayWorkDomain.getWorkTimeCode().v());*/
		// 3.個別エラーチェック
//		HdWorkCheckRegisterOutput output = this.individualErrorCheck(
//				companyID, 
//				application.getEmployeeID(), 
//				application.getAppDate(), 
//				appHdWorkDispInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getBaseDate(), 
//			 	ApplicationType.HOLIDAY_WORK_APPLICATION, 
//				application, 
//				appHdWorkDispInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getApprovalFunctionSet().getApplicationDetailSetting().get().getTimeCalUse(), 
//				appHdWorkDispInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getApprovalFunctionSet().getApplicationDetailSetting().get().getTimeInputUse(), 
//				appHdWorkDispInfoOutput.getOvertimeRestAppCommonSetting().getAppDateContradictionAtr(), 
//				false, 
//				false, 
//				appHdWorkDispInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getAchievementOutputLst(), 
//				appHdWorkDispInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getAppDetailContentLst(), 
//				appHdWorkDispInfoOutput.getHdWorkDispInfoWithDateOutput().getAppHdWorkInstruction(), 
//				holidayWorkDomain, 
//				calculateFlg, 
//				appHdWorkDispInfoOutput);
//		outputLst.addAll(output.getConfirmMsgLst());
//		result.setConfirmMsgLst(outputLst);
//		result.setAppOvertimeDetailOtp(output.getAppOvertimeDetailOtp());
		return result;
	}
}
