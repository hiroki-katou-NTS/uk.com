package nts.uk.ctx.at.request.dom.application.appabsence.service;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.logging.log4j.util.Strings;

import lombok.val;
import nts.arc.enums.EnumAdaptor;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.request.dom.application.Application;
import nts.uk.ctx.at.request.dom.application.ApplicationType;
import nts.uk.ctx.at.request.dom.application.EmploymentRootAtr;
import nts.uk.ctx.at.request.dom.application.appabsence.AppAbsence;
import nts.uk.ctx.at.request.dom.application.appabsence.ApplyForLeave;
import nts.uk.ctx.at.request.dom.application.appabsence.HolidayAppType;
import nts.uk.ctx.at.request.dom.application.appabsence.service.four.AppAbsenceFourProcess;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.AbsenceCheckRegisterOutput;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.AppAbsenceStartInfoOutput;
import nts.uk.ctx.at.request.dom.application.appabsence.service.output.SpecAbsenceDispInfo;
import nts.uk.ctx.at.request.dom.application.appabsence.service.three.AppAbsenceThreeProcess;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualleave.AnnLeaveRemainNumberAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualleave.ReNumAnnLeaReferenceDateImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.ReserveLeaveManagerApdater;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.rsvimport.RsvLeaGrantRemainingImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.rsvimport.RsvLeaManagerImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.ApprovalRootStateAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.workflow.dto.ApprovalRootStateImport_New;
import nts.uk.ctx.at.request.dom.application.common.service.detailscreen.before.DetailBeforeUpdate;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.before.NewBeforeRegister;
import nts.uk.ctx.at.request.dom.application.common.service.newscreen.output.ConfirmMsgOutput;
import nts.uk.ctx.at.request.dom.application.common.service.other.OtherCommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.PeriodCurrentMonth;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.setting.output.AppDispInfoStartupOutput;
import nts.uk.ctx.at.request.dom.application.common.service.smartphone.CommonAlgorithmMobile;
import nts.uk.ctx.at.request.dom.application.common.service.smartphone.output.AppReasonOutput;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.AppliedDate;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HdAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HdAppSetRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HolidayApplicationSetting;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.HolidayApplicationSettingRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.vacationapplicationsetting.UseAtr;
import nts.uk.ctx.at.request.dom.setting.company.request.applicationsetting.apptypesetting.DisplayReasonRepository;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSet;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.AppEmploymentSetting;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.HolidayType;
import nts.uk.ctx.at.request.dom.setting.employment.appemploymentsetting.WorkTypeObjAppHoliday;
import nts.uk.ctx.at.request.dom.vacation.history.service.PlanVacationRuleError;
import nts.uk.ctx.at.request.dom.vacation.history.service.PlanVacationRuleExport;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecMngInPeriodParamInput;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsRecRemainMngOfInPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsenceReruitmentMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.InterimRemainDataMngCheckRegister;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.require.RemainNumberTempRequireService;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffRemainMngOfInPeriod;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffRemainMngParam;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.BasicScheduleService;
import nts.uk.ctx.at.shared.dom.schedule.basicschedule.WorkStyle;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.MaxNumberDayType;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.SpecialHolidayEvent;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.service.CheckWkTypeSpecHdEventOutput;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.service.DateSpecHdRelationOutput;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.service.MaxDaySpecHdOutput;
import nts.uk.ctx.at.shared.dom.specialholiday.specialholidayevent.service.SpecialHolidayEventAlgorithm;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.SettingDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRule;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AcquisitionRuleRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.acquisitionrule.AnnualHoliday;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AbsenceTenProcess;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AbsenceTenProcessCommon;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AnnualHolidaySetOutput;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.LeaveSetOutput;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.SixtyHourSettingOutput;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.SubstitutionHolidayOutput;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication.VacationApplicationReflect;
import nts.uk.ctx.at.shared.dom.workcheduleworkrecord.appreflectprocess.appreflectcondition.vacationapplication.leaveapplication.VacationApplicationReflectRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.GetClosureStartForEmployee;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingService;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.internal.PredetermineTimeSetForCalc;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeUnit;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class AbsenceServiceProcessImpl implements AbsenceServiceProcess{
//	@Inject
//	private AppAbsenceRepository appAbsenceRepository;
//	@Inject
//	private ApplicationApprovalService appRepository;
	@Inject
	private PlanVacationRuleExport planVacationRuleExport;
//	@Inject
//	private AbsenceTenProcess absenceTenProcess;
//	@Inject
	@Inject
	private AcquisitionRuleRepository repoAcquisitionRule;
//	@Inject
//	private GetClosureStartForEmployee getClosureStartForEmp;
	@Inject
	private AbsenceReruitmentMngInPeriodQuery absRertMngInPeriod;
	@Inject
	private BreakDayOffMngInPeriodQuery breakDayOffMngInPeriod;
	@Inject
	private AnnLeaveRemainNumberAdapter annLeaRemNumberAdapter;
	@Inject
	private ReserveLeaveManagerApdater rsvLeaMngApdater;
	
	@Inject
	private HdAppSetRepository hdAppSetRepository;
	
	@Inject
	private DisplayReasonRepository displayRep;
	
	@Inject
	private SpecialHolidayEventAlgorithm specialHolidayEventAlgorithm;
	
	@Inject
	private WorkTypeRepository workTypeRepository;
	
	@Inject
	private BasicScheduleService basicScheduleService;
	
	@Inject
	private AppAbsenceFourProcess appAbsenceFourProcess;
	
	@Inject
	private AppAbsenceThreeProcess appAbsenceThreeProcess;
	
	@Inject
	private OtherCommonAlgorithm otherCommonAlgorithm;
	
	@Inject
	private DetailBeforeUpdate detailBeforeUpdate;
	
	@Inject
	private NewBeforeRegister newBeforeRegister;
	
	@Inject
	private InterimRemainDataMngCheckRegister interimRemainCheckReg;
	
	@Inject
	private CommonAlgorithm commonAlgorithm;
	
	@Inject
	private ApprovalRootStateAdapter approvalRootStateAdapter;
	
	@Inject 
	private WorkTimeSettingService weorkTimeSettingService;
	
	@Inject
	private VacationApplicationReflectRepository vacationAppReflectRepository;
	
	@Inject
	private HolidayApplicationSettingRepository hdSetRepo;
	
	@Inject
	private AbsenceTenProcessCommon absenceCommon;
	
	@Inject
	private NursingLeaveSettingRepository nursingLeaveSettingRepo;
	
	@Inject
	private ComSubstVacationRepository comSubstVacationRepository;
	
	@Inject
	private RemainNumberTempRequireService requireService;
	
	@Inject
	private CommonAlgorithmMobile commonAlg;
	
	@Override
	public SpecialLeaveInfor getSpecialLeaveInfor(String workTypeCode) {
		SpecialLeaveInfor specialLeaveInfor = new SpecialLeaveInfor();
//		boolean relationFlg = false;
//		boolean mournerDisplayFlg = false;
//		boolean displayRelationReasonFlg = false;
//		int maxDayRelate = 0;
		//指定した勤務種類に特別休暇に当てはまるかチェックする
		
		return specialLeaveInfor;
	}

	@Override
	public void createAbsence(AppAbsence domain, Application newApp, ApprovalRootStateImport_New approvalRootState) {
		// insert Application
		// error EA refactor 4
		/*this.appRepository.insert(newApp);*/
//		this.approvalRootStateAdapter.insertFromCache(
//				newApp.getCompanyID(), 
//				newApp.getAppID(), 
//				newApp.getAppDate(), 
//				newApp.getEmployeeID(), 
//				approvalRootState.getListApprovalPhaseState());
//		// insert Absence
//		this.appAbsenceRepository.insertAbsence(domain);
//		if(domain.getHolidayAppType().equals(HolidayAppType.SPECIAL_HOLIDAY) && domain.getAppForSpecLeave() != null){
//			repoSpecLeave.addSpecHd(domain.getAppForSpecLeave());
//		}
		
	}

	/**
	 * 13.計画年休上限チェック
	 */
	@Override
	public void checkLimitAbsencePlan(String cID, String sID, String workTypeCD, GeneralDate sDate, GeneralDate eDate, List<GeneralDate> lstDateIsHoliday) {
		//計画年休の上限チェック(check giới hạn trên của plan annual holidays)
		List<PlanVacationRuleError> check = planVacationRuleExport.checkMaximumOfPlan(cID, sID, workTypeCD, new DatePeriod(sDate, eDate), lstDateIsHoliday);
		if(!check.isEmpty()){
			if(check.contains(PlanVacationRuleError.OUTSIDEPERIOD)) {
				//Msg_1345を表示
				throw new BusinessException("Msg_1453");	
			} else {
				throw new BusinessException("Msg_1345");
			}
			
		}
	}
    @Override
    public AppAbsenceStartInfoOutput allHalfDayChangeProcess(String companyID,
            AppAbsenceStartInfoOutput appAbsenceStartInfoOutput, boolean displayHalfDayValue, Integer alldayHalfDay,
            Optional<HolidayAppType> holidayType) {
        // INPUT．「休暇種類」を確認する
        if(holidayType.isPresent()) {
            // 休暇種類変更時処理
//            return this.holidayTypeChangeProcess(companyID, appAbsenceStartInfoOutput, displayHalfDayValue, alldayHalfDay, holidayType.get());
        }
        // 返ってきた「休暇申請起動時の表示情報」を返す
        return appAbsenceStartInfoOutput;
    }
	/**
	 * @author hoatt
	 * 14.休暇種類表示チェック
	 * @param companyID
	 * @param sID
	 * @param baseDate
	 * @return
	 */
	@Override
	public CheckDispHolidayType checkDisplayAppHdType(String companyID, String sID, GeneralDate baseDate) {
	    val require = requireService.createRequire();
	    val cache = new CacheCarrier();
	    
	    // 10-1.年休の設定を取得する
	    AnnualHolidaySetOutput annualHolidaySetOutput = AbsenceTenProcess.getSettingForAnnualHoliday(require, companyID);
	    
	    // 10-4.積立年休の設定を取得する
	    boolean isYearlyReserve = AbsenceTenProcess.getSetForYearlyReserved(require, cache, companyID, sID, baseDate);
	    
	    // 10-2.代休の設定を取得する
	    SubstitutionHolidayOutput substituationHoliday =  AbsenceTenProcess.getSettingForSubstituteHoliday(require, cache, companyID, sID, baseDate);
	    
	    // 10-3.振休の設定を取得する
	    LeaveSetOutput leaveSet = AbsenceTenProcess.getSetForLeave(require, cache, companyID, sID, baseDate);
	    
	    // 10-5.60H超休の設定を取得する
	    SixtyHourSettingOutput setting60H = absenceCommon.getSixtyHourSetting(companyID, sID, baseDate);
	    
	    // 子看護介護の設定の取得
	    NursingLeaveSetting childNursingLeaveSetting = this.getNursingLeaveSetting(companyID, NursingCategory.ChildNursing);
	    
	    // 子看護介護の設定の取得
	    NursingLeaveSetting nursingLeaveSetting = this.getNursingLeaveSetting(companyID, NursingCategory.Nursing);
	    
	    // 代休の紐付け管理区分を取得する
	    // pending for refactor domain
	    CompensatoryLeaveComSetting compensatoryLeaveComSetting = this.getCompLeaveComSetting(companyID);
	    
	    // 振休の紐付け管理区分を取得する
        // pending for refactor domain
	    ComSubstVacation comSubstVacation = this.getComSubstVacation(companyID);
	    
	    // OUTPUTを作成して返す
	    AnualLeaveManagement annualLeaveManagement = new AnualLeaveManagement(
	            EnumAdaptor.valueOf(annualHolidaySetOutput.getTimeYearRest(), TimeDigestiveUnit.class), 
	            EnumAdaptor.valueOf(annualHolidaySetOutput.isSuspensionTimeYearFlg() ? 1 : 0, ManageDistinct.class),
	            EnumAdaptor.valueOf(annualHolidaySetOutput.isYearHolidayManagerFlg() ? 1 : 0, ManageDistinct.class));
	    
	    AccumulatedRestManagement accumulatedRestManagement = new AccumulatedRestManagement(
	            EnumAdaptor.valueOf(isYearlyReserve ? 1 : 0, ManageDistinct.class));
	    
	    SubstituteLeaveManagement substituteLeaveManagement = new SubstituteLeaveManagement(
	            EnumAdaptor.valueOf(substituationHoliday.getDigestiveUnit(), TimeDigestiveUnit.class), 
	            EnumAdaptor.valueOf(substituationHoliday.isTimeOfPeriodFlg() ? 1 : 0, ManageDistinct.class), 
//	            compensatoryLeaveComSetting.getIsManaged(),
	            ManageDistinct.YES,
	            EnumAdaptor.valueOf(substituationHoliday.isSubstitutionFlg() ? 1 : 0, ManageDistinct.class));
	    
	    HolidayManagement holidayManagement = new HolidayManagement(
//	            comSubstVacation.getSetting().getIsManage(),
	            ManageDistinct.YES,
	            EnumAdaptor.valueOf(leaveSet.isSubManageFlag() ? 1 : 0, ManageDistinct.class));
	    
	    Overtime60HManagement overtime60hManagement = new Overtime60HManagement(
	            EnumAdaptor.valueOf(setting60H.isSixtyHourOvertimeMngDistinction() ? 1 : 0, ManageDistinct.class), 
	            EnumAdaptor.valueOf(setting60H.getSixtyHourOverDigestion(), TimeDigestiveUnit.class));
	    
	    NursingCareLeaveManagement nursingCareLeaveManagement = new NursingCareLeaveManagement(
//	            childNursingLeaveSetting.getManageType(), 
	            ManageDistinct.YES,
	            // Mock data START
	            TimeDigestiveUnit.OneHour, 
	            ManageDistinct.YES, 
	            TimeDigestiveUnit.OneHour, 
	            ManageDistinct.YES, 
	            // Mock data END
//	            nursingLeaveSetting.getManageType());
	            ManageDistinct.YES);
	    
		return new CheckDispHolidayType(
		        annualLeaveManagement, 
		        accumulatedRestManagement, 
		        substituteLeaveManagement, 
		        holidayManagement, 
		        overtime60hManagement, 
		        nursingCareLeaveManagement);
	}
	
	private ComSubstVacation getComSubstVacation(String companyID) {
	    Optional<ComSubstVacation> comSubstVacationOpt = comSubstVacationRepository.findById(companyID);
        return comSubstVacationOpt.orElse(null);
    }

    private CompensatoryLeaveComSetting getCompLeaveComSetting(String companyID) {
        val require = requireService.createRequire();
	    CompensatoryLeaveComSetting setting = require.compensatoryLeaveComSetting(companyID);
	    
        return setting;
    }

    private NursingLeaveSetting getNursingLeaveSetting(String companyID, NursingCategory nursingType) {
	    // ドメインモデル「介護看護休暇設定」を取得する (Lấy domain NursingLeaveSetting)
//	    List<NursingLeaveSetting> nursingLeaveSettings = nursingLeaveSettingRepo.findByCompanyId(companyID)
//	            .stream().filter(setting -> setting.getNursingCategory().equals(nursingType))
//	            .collect(Collectors.toList());
//	    
//	    
//        return nursingLeaveSettings.size() > 0 ? nursingLeaveSettings.get(0) : null;
        return null;
    }

    @Override
	public List<ConfirmMsgOutput> checkDigestPriorityHd(boolean mode, HdAppSet hdAppSet, AppEmploymentSetting employmentSet, boolean subVacaManage,
			boolean subHdManage, Double subVacaRemain, Double subHdRemain) {
		List<ConfirmMsgOutput> result = new ArrayList<>();
		// INPUT．「画面モード」を確認する
		if(!mode) {
			return result;
		}
		//新規モード(new mode)
		//アルゴリズム「振休代休優先チェック」を実行する(Thực hiện thuật toán 「Check độ ưu tiên substituteHoliday và rest 」)
		boolean subVacaTypeUseFlg = false;
		boolean subHdTypeUseFlg = false;
		if(employmentSet != null && !CollectionUtil.isEmpty(employmentSet.getListWTOAH())) {
			WorkTypeObjAppHoliday item = employmentSet.getListWTOAH().get(0);
			if((item.getSwingOutAtr().isPresent() ? item.getSwingOutAtr().get().value : item.getHolidayAppType().isPresent() ? item.getHolidayAppType().get().value : 9 ) == HolidayType.RESTTIME.value) {
				subVacaTypeUseFlg = item.getHolidayTypeUseFlg().get();
			}
			
			if((item.getSwingOutAtr().isPresent() ? item.getSwingOutAtr().get().value : item.getHolidayAppType().isPresent() ? item.getHolidayAppType().get().value : 9 ) == HolidayType.SUBSTITUTEHOLIDAY.value) {
				subHdTypeUseFlg = item.getHolidayTypeUseFlg().get();
			}
		}
		result = this.checkPriorityHoliday(
				hdAppSet.getPridigCheck(), 
				subVacaManage, 
				subVacaTypeUseFlg, 
				subHdManage,
				subHdTypeUseFlg, 
				subHdRemain == null ? 0 : subHdRemain.intValue(), 
				subVacaRemain == null ? 0 : subVacaRemain.intValue());
		return result;
	}
	/**
	 * @author hoatt
	 * 振休代休優先チェック
	 * @param pridigCheck - 休暇申請設定．年休より優先消化チェック区分 - HdAppSet
	 * @param isSubVacaManage - 振休管理設定．管理区分
	 * @param subVacaTypeUseFlg - 休暇申請対象勤務種類．休暇種類を利用しない（振休） - AppEmploymentSetting
	 * @param isSubHdManage - 代休管理設定．管理区分
	 * @param subHdTypeUseFlg - 休暇申請対象勤務種類．休暇種類を利用しない（代休） - AppEmploymentSetting
	 * @param numberSubHd - 代休残数
	 * @param numberSubVaca - 振休残数
	 * @return エラーメッセージ - 確認メッセージ
	 */
	@Override
	public List<ConfirmMsgOutput> checkPriorityHoliday(AppliedDate pridigCheck, boolean isSubVacaManage, boolean subVacaTypeUseFlg,
			boolean isSubHdManage, boolean subHdTypeUseFlg, int numberSubHd, int numberSubVaca) {
		List<ConfirmMsgOutput> result = new ArrayList<>();
		//休暇申請設定．年休より優先消化チェック区分をチェックする(Check pridigCheck)
		if(pridigCheck.equals(AppliedDate.DONT_CHECK)){//チェックしないの場合
			return result;
		}
		//それ以外
		//ログインしている会社をもとにドメインモデル「休暇の取得ルール」を取得する (lấy dữ liệu domain 「AcquisitionRule」 dựa vào công ty đang đăng nhập)
		Optional<AcquisitionRule> acqRule = repoAcquisitionRule.findById(AppContexts.user().companyId());
		if(!acqRule.isPresent()){
			return result;
		}
		AcquisitionRule rule = acqRule.get();
		AnnualHoliday annualHoliday = rule.getAnnualHoliday();
		//振休使用フラグをチェックする (Check restFlag)
		//休暇の取得ルール．年休より優先する休暇．振休を優先＝ false OR 休暇申請対象勤務種類．休暇種類を利用しない（振休）＝ true OR 振休管理設定．管理区分＝管理しない
//		2018/09/07 muto upd EA#2660
//		条件の追加： 「休暇の取得ルール．取得する順番をチェックする＝設定しない」
		if(annualHoliday.isPrioritySubstitute() && !subVacaTypeUseFlg && isSubVacaManage 
				&& rule.getCategory().equals(SettingDistinct.YES)){
			//振休残数をチェックする (Check restRemaining)
			if(numberSubVaca > 0){//振休残数>0(restRemaining >0)
				if(pridigCheck.equals(AppliedDate.CHECK_IMPOSSIBLE)){//年休より優先消化チェック区分＝チェックする（登録不可）(pridigCheck == CHECK_IMPOSSIBLE)
					//エラーメッセージ（Msg_1392）を返す (Return errorMessage)
					throw new BusinessException("Msg_1392");
				}
				//年休より優先消化チェック区分＝チェックする（登録可）(pridigCheck == CHECK_AVAILABLE)
				//確認メッセージ（Msg_1393）を返す (Return confirmMessage)
				result.add(new ConfirmMsgOutput("Msg_1393", Collections.emptyList()));
			}
		}
		//休暇の取得ルール．年休より優先する休暇．代休を優先＝ false OR 休暇申請対象勤務種類．休暇種類を利用しない（代休）＝ true OR 代休管理設定．管理区分＝管理しない
		//代休使用フラグをチェックする (Check substituteHolidayFlag)
//		2018/09/07 muto upd EA#2660
//		条件の追加： 「休暇の取得ルール．取得する順番をチェックする＝設定しない」
		if(!annualHoliday.isPriorityPause() || subHdTypeUseFlg || !isSubHdManage || rule.getCategory().equals(SettingDistinct.NO)){
			return result;
		}
		if(numberSubHd <= 0){//代休残数<=0
			return result;
		}
		//代休残数>0(substituteHolidayRemaining > 0)
		if(pridigCheck.equals(AppliedDate.CHECK_IMPOSSIBLE)){//年休より優先消化チェック区分＝チェックする（登録不可）(pridigCheck == CHECK_IMPOSSIBLE)
			//エラーメッセージ（Msg_1394）を返す
			throw new BusinessException("Msg_1394");
		}
		//年休より優先消化チェック区分＝チェックする（登録可）(pridigCheck == CHECK_AVAILABLE)
		//確認メッセージ（Msg_1395）を返す
		result.add(new ConfirmMsgOutput("Msg_1395", Collections.emptyList()));
		return result;
	}
	/**
	 * @author hoatt
	 * 残数取得する
	 * @param companyID - 会社ID
	 * @param employeeID - 社員ID　＝申請者社員ID
	 * @param baseDate - 基準日
	 * @return 年休残数-代休残数-振休残数-ストック休暇残数
	 */
	@Override
	public NumberOfRemainOutput getNumberOfRemaining(String companyID, String employeeID, GeneralDate baseDate,
	        ManageDistinct annualLeaveManageDistinct, ManageDistinct accumulatedManage, ManageDistinct substituteLeaveManagement, 
	        ManageDistinct holidayManagement, ManageDistinct overrest60HManagement, ManageDistinct childNursingManagement, 
	        ManageDistinct longTermCareManagement) {
	    val require = requireService.createRequire();
	    val cache = new CacheCarrier();
	    
		//アルゴリズム「社員に対応する締め開始日を取得する」を実行する
		Optional<GeneralDate> closure = GetClosureStartForEmployee.algorithm(require, cache, employeeID);
		if(!closure.isPresent()){
			return new NumberOfRemainOutput(0, 0, 0, 0, 0, 0, 0, 0, 0, 0, 0);
		}
		//年休残数
		Double yearRemain = null;
		//代休残数
		Double subHdRemain = null;
		//振休残数
		Double subVacaRemain = null;
		//ストック休暇残数
		Double stockRemain = null;
		GeneralDate closureDate = closure.get();
		
		//1
		if(holidayManagement.equals(ManageDistinct.YES)){//output．振休管理区分が管理する
			//アルゴリズム「期間内の振出振休残数を取得する」を実行する - RQ204
			//・会社ID＝ログイン会社ID
//			・社員ID＝申請者社員ID
//			・集計開始日＝締め開始日
//			・集計終了日＝締め開始日.AddYears(1).AddDays(-1)
//			・モード＝その他モード
//			・基準日＝申請開始日
//			・上書きフラグ＝false
			AbsRecMngInPeriodParamInput paramInput = new AbsRecMngInPeriodParamInput(companyID, employeeID, new DatePeriod(closureDate, closureDate.addYears(1).addDays(-1)), 
					baseDate, false, false, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), Optional.empty(), Optional.empty(), Optional.empty());
			AbsRecRemainMngOfInPeriod subVaca = absRertMngInPeriod.getAbsRecMngInPeriod(require, cache, paramInput);
			//振休残数 ← 残日数　（アルゴリズム「期間内の振出振休残数を取得する」のoutput）
			subVacaRemain = subVaca.getRemainDays();//残日数
		}
		
		//2
		if(substituteLeaveManagement.equals(ManageDistinct.YES)){//output．代休管理区分が管理する
			//アルゴリズム「期間内の休出代休残数を取得する」を実行する - RQ203
			//・会社ID＝ログイン会社ID
//			・社員ID＝申請者社員ID
//			・集計開始日＝締め開始日
//			・集計終了日＝締め開始日.AddYears(1).AddDays(-1)
//			・モード＝その他モード
//			・基準日＝申請開始日
//			・上書きフラグ＝false
			BreakDayOffRemainMngParam inputParam = new BreakDayOffRemainMngParam(companyID, employeeID, new DatePeriod(closureDate, closureDate.addYears(1).addDays(-1)), 
					false, baseDate, false, new ArrayList<>(), new ArrayList<>(), new ArrayList<>(), Optional.empty(), Optional.empty(), Optional.empty());
			BreakDayOffRemainMngOfInPeriod subHd = breakDayOffMngInPeriod.getBreakDayOffMngInPeriod(require, cache, inputParam);
			//代休残数 ← 残日数　（アルゴリズム「期間内の代休残数を取得する」のoutput）
			subHdRemain = subHd.getRemainDays();
		}
		
		//3
		if(accumulatedManage.equals(ManageDistinct.YES)){//output．積休管理区分が管理する
			//基準日時点の積立年休残数を取得する - RQ201
			Optional<RsvLeaManagerImport> stock = rsvLeaMngApdater.getRsvLeaveManager(employeeID, baseDate);
			if(stock.isPresent()){
				//積休残数 ←  積立年休情報.残数.積立年休（マイナスあり）.残数.合計残日数 
				//reserveLeaveInfo.remainingNumber.reserveLeaveWithMinus.remainingNumber.totalRemainingDays
				if(stock.get().getGrantRemainingList().size() > 0){
					stockRemain = new Double(0L);
					for (RsvLeaGrantRemainingImport rsv : stock.get().getGrantRemainingList()) {
						stockRemain = stockRemain + rsv.getRemainingNumber();
					}
				}
			}
		}
		
		//4
		if(annualLeaveManageDistinct.equals(ManageDistinct.YES)){//output．年休管理区分が管理する
			//基準日時点の年休残数を取得する - RQ198
			ReNumAnnLeaReferenceDateImport year = annLeaRemNumberAdapter.getReferDateAnnualLeaveRemainNumber(employeeID, baseDate);
			//年休残数 ← 年休残数.年休残数（付与前）日数 annualLeaveRemainNumberExport.annualLeaveGrantPreDay
			yearRemain = year.getAnnualLeaveRemainNumberExport() == null ? null : 
				year.getAnnualLeaveRemainNumberExport().getAnnualLeaveGrantDay();
		}
		
		if (overrest60HManagement.equals(ManageDistinct.YES)) {
		    
		}
		
		if (childNursingManagement.equals(ManageDistinct.YES)) {
		    
		}
		
		if (longTermCareManagement.equals(ManageDistinct.YES)) {
		    
		}
//        return NumberOfRemainOutput.init(yearRemain, subHdRemain, subVacaRemain, stockRemain, yearManage, subHdManage, subVacaManage, retentionManage);
		return new NumberOfRemainOutput(1, 1, 1, 1, 1, 1, 1, 1, 1, 1, 1);
	}

	@Override
	public HolidayRequestSetOutput getHolidayRequestSet(String companyID) {
		// ドメインモデル「休暇申請設定」を取得する(lấy dữ liệu domain 「休暇申請設定」)
//		HdAppSet hdAppSet = hdAppSetRepository.getAll().get();
	    HolidayApplicationSetting hdAppSet = hdSetRepo.findSettingByCompanyId(companyID).orElseGet(null);
//		// ドメインモデル「申請理由表示」を取得する(lấy dữ liệu domain 「申請理由表示」)
//		List<DisplayReason> displayReasonLst = displayRep.findDisplayReason(companyID);
		
		// ドメインモデル「休暇申請の反映」を取得する
		VacationApplicationReflect vacationApplicationReflect = vacationAppReflectRepository.findReflectByCompanyId(companyID).orElse(null);
		// 取得した情報を返す
		return new HolidayRequestSetOutput(hdAppSet, vacationApplicationReflect);
	}

	@Override
	public RemainVacationInfo getRemainVacationInfo(String companyID, String employeeID, GeneralDate date) {
		// 各休暇の管理区分を取得する
		CheckDispHolidayType checkDispHolidayType = this.checkDisplayAppHdType(companyID, employeeID, date);
		// 各休暇の残数を取得する
		NumberOfRemainOutput numberOfRemainOutput = this.getNumberOfRemaining(
				companyID, 
				employeeID, 
				date, 
				checkDispHolidayType.getAnnAnualLeaveManagement().getAnnualLeaveManageDistinct(), 
				checkDispHolidayType.getAccumulatedRestManagement().getAccumulatedManage(), 
				checkDispHolidayType.getSubstituteLeaveManagement().getSubstituteLeaveManagement(), 
				checkDispHolidayType.getHolidayManagement().getHolidayManagement(), 
				checkDispHolidayType.getOvertime60hManagement().getOverrest60HManagement(), 
				checkDispHolidayType.getNursingCareLeaveManagement().getChildNursingManagement(), 
				checkDispHolidayType.getNursingCareLeaveManagement().getLongTermCareManagement());
		// 取得した情報もとに「休暇残数情報」にセットして返す
		return new RemainVacationInfo(
		        checkDispHolidayType.getAnnAnualLeaveManagement(), 
                checkDispHolidayType.getAccumulatedRestManagement(), 
                checkDispHolidayType.getSubstituteLeaveManagement(), 
                checkDispHolidayType.getHolidayManagement(), 
                checkDispHolidayType.getOvertime60hManagement(), 
                checkDispHolidayType.getNursingCareLeaveManagement(), 
                Optional.ofNullable(numberOfRemainOutput.getYearRemain()), 
                Optional.ofNullable(numberOfRemainOutput.getYearHourRemain()), 
                Optional.ofNullable(numberOfRemainOutput.getSubHdRemain()), 
                Optional.ofNullable(numberOfRemainOutput.getSubVacaRemain()), 
                Optional.ofNullable(numberOfRemainOutput.getSubVacaHourRemain()), 
                Optional.ofNullable(numberOfRemainOutput.getSubHdHourRemain()), 
                Optional.ofNullable(numberOfRemainOutput.getOver60HHourRemain()), 
                Optional.ofNullable(numberOfRemainOutput.getChildNursingRemain()), 
                Optional.ofNullable(numberOfRemainOutput.getChildNursingHourRemain()), 
                Optional.ofNullable(numberOfRemainOutput.getNursingRemain()), 
                Optional.ofNullable(numberOfRemainOutput.getNursingHourRemain()));
	}

	@Override
	public AppAbsenceStartInfoOutput getSpecAbsenceUpperLimit(String companyID,
			AppAbsenceStartInfoOutput appAbsenceStartInfoOutput, Optional<String> workTypeCD) {
		// 「休暇申請起動時の表示情報」の「特別休暇表示情報」をクリアする
		appAbsenceStartInfoOutput.setSpecAbsenceDispInfo(Optional.empty());
		if(!appAbsenceStartInfoOutput.getSelectedWorkTypeCD().isPresent() ||
				Strings.isBlank(appAbsenceStartInfoOutput.getSelectedWorkTypeCD().get())) {
			// 「休暇申請起動時の表示情報」を返す
			return appAbsenceStartInfoOutput;
		}
		// 指定する勤務種類が事象に応じた特別休暇かチェックする
		CheckWkTypeSpecHdEventOutput checkWkTypeSpecHdEventOutput = specialHolidayEventAlgorithm.checkWkTypeSpecHdForEvent(companyID, workTypeCD.get());
		if(!checkWkTypeSpecHdEventOutput.isSpecHdForEventFlag()) {
			return appAbsenceStartInfoOutput;
		}
		if(checkWkTypeSpecHdEventOutput.getSpecHdEvent().get().getMaxNumberDay() == MaxNumberDayType.REFER_RELATIONSHIP) {
			// 指定する特休枠の続柄に対する上限日数を取得する
			List<DateSpecHdRelationOutput> dateSpecHdRelationOutputLst = 
					specialHolidayEventAlgorithm.getMaxDaySpecHdByRelaFrameNo(companyID, checkWkTypeSpecHdEventOutput.getFrameNo().get());
			// 指定する特休枠の上限日数を取得する
			MaxDaySpecHdOutput maxDaySpecHdOutput = specialHolidayEventAlgorithm.getMaxDaySpecHd(
					companyID, 
					checkWkTypeSpecHdEventOutput.getFrameNo().get(), 
					checkWkTypeSpecHdEventOutput.getSpecHdEvent().get(), 
					dateSpecHdRelationOutputLst.stream().findFirst().map(x -> x.getRelationCD()));
			// INPUT．「休暇申請起動時の表示情報」に項目をセットして返す
			SpecAbsenceDispInfo specAbsenceDispInfo = new SpecAbsenceDispInfo(
					checkWkTypeSpecHdEventOutput.isSpecHdForEventFlag(), 
					checkWkTypeSpecHdEventOutput.getSpecHdEvent(), 
					checkWkTypeSpecHdEventOutput.getFrameNo(), 
					Optional.of(maxDaySpecHdOutput.getMaxDay()), 
					Optional.of(maxDaySpecHdOutput.getDayOfRela()),
					Optional.of(dateSpecHdRelationOutputLst));
			appAbsenceStartInfoOutput.setSpecAbsenceDispInfo(Optional.of(specAbsenceDispInfo));
		} else {
			// 指定する特休枠の上限日数を取得する
			MaxDaySpecHdOutput maxDaySpecHdOutput = specialHolidayEventAlgorithm.getMaxDaySpecHd(
					companyID, 
					checkWkTypeSpecHdEventOutput.getFrameNo().get(), 
					checkWkTypeSpecHdEventOutput.getSpecHdEvent().get(),
					Optional.empty());
			// INPUT．「休暇申請起動時の表示情報」に項目をセットして返す
			SpecAbsenceDispInfo specAbsenceDispInfo = new SpecAbsenceDispInfo(
					checkWkTypeSpecHdEventOutput.isSpecHdForEventFlag(), 
					checkWkTypeSpecHdEventOutput.getSpecHdEvent(), 
					checkWkTypeSpecHdEventOutput.getFrameNo(), 
					Optional.of(maxDaySpecHdOutput.getMaxDay()), 
					Optional.of(maxDaySpecHdOutput.getDayOfRela()),
					Optional.empty());
			appAbsenceStartInfoOutput.setSpecAbsenceDispInfo(Optional.of(specAbsenceDispInfo));
		}
		return appAbsenceStartInfoOutput;
	}

	@Override
	public AppAbsenceStartInfoOutput workTimesChangeProcess(String companyID,
			AppAbsenceStartInfoOutput appAbsenceStartInfoOutput, String workTypeCD, Optional<String> workTimeCD, HolidayAppType holidayType) {
		// INPUT．「休暇申請起動時の表示情報．勤務時間帯一覧」をクリアする
		appAbsenceStartInfoOutput.setWorkTimeLst(new ArrayList<>());
		// INPUT．「休暇申請起動時の表示情報．選択中の就業時間帯」を更新する
		appAbsenceStartInfoOutput.setSelectedWorkTimeCD(workTimeCD);
		// INPUT．「就業時間帯コード」を確認する
		if(!workTimeCD.isPresent() || 
				Strings.isBlank(workTimeCD.get())) {
			// 「休暇申請起動時の表示情報」を返す
			return appAbsenceStartInfoOutput;
		}
		// INPUT．「休暇種類」をチェックする(Check HolidayType được chọn)
		if (holidayType != null && holidayType == HolidayAppType.DIGESTION_TIME) {
			// アルゴリズム「必要な時間を算出する」を実行する(Thực hiện [Tính toán thời gian cần thiết])
			// 9.必要な時間を算出する
			// pending / chưa đối ứng
		}
		// 勤務時間初期値の取得
		PredetermineTimeSetForCalc prescribedTimezoneSet = this.initWorktimeCode(companyID, workTypeCD, workTimeCD.get());
		// 返ってきた「時間帯(使用区分付き)」を「休暇申請起動時の表示情報」にセットする
		if(prescribedTimezoneSet != null) {
			appAbsenceStartInfoOutput.setWorkTimeLst(prescribedTimezoneSet.getTimezones());
		}
		// 「休暇申請起動時の表示情報」を返す
		return appAbsenceStartInfoOutput;
	}
	
	/**
	 * 勤務時間初期値の取得
	 * 
	 * @param companyID
	 * @param workTypeCode
	 * @param workTimeCode
	 * @return
	 */
	public PredetermineTimeSetForCalc initWorktimeCode(String companyID, String workTypeCode, String workTimeCode) {
		Optional<WorkType> WkTypeOpt = workTypeRepository.findByPK(companyID, workTypeCode);
		if (WkTypeOpt.isPresent()) {
			// アルゴリズム「1日半日出勤・1日休日系の判定」を実行する
			WorkStyle workStyle = basicScheduleService.checkWorkDay(WkTypeOpt.get().getWorkTypeCode().toString());
			if (workStyle == null) {
				return null;
			}
			if (!workStyle.equals(WorkStyle.ONE_DAY_REST)) {
				// アルゴリズム「所定時間帯を取得する」を実行する
				// 所定時間帯を取得する
				return weorkTimeSettingService.getPredeterminedTimezone(companyID, workTimeCode, workTypeCode, null);
				
			}
		}
		return null;
	}

	@Override
	public AppAbsenceStartInfoOutput workTypeChangeProcess(String companyID, List<String> appDates,
			AppAbsenceStartInfoOutput appAbsenceStartInfoOutput, HolidayAppType holidayType, Optional<String> workTypeCD) {
		// INPUT．「休暇申請起動時の表示情報．選択中の勤務種類」にセットする
		appAbsenceStartInfoOutput.setSelectedWorkTypeCD(workTypeCD);
		// 就業時間帯の表示制御フラグを確認する
		boolean controlDispWorkingHours = appAbsenceFourProcess.getDisplayControlWorkingHours(
				workTypeCD, 
				appAbsenceStartInfoOutput.getVacationAppReflect(), 
				companyID);
		// 返ってきた「就業時間帯表示フラグ」を「休暇申請起動時の表示情報」にセットする
		appAbsenceStartInfoOutput.setWorkHoursDisp(controlDispWorkingHours);
		// INPUT．「休暇種類」をチェックする
		if(holidayType == HolidayAppType.SPECIAL_HOLIDAY) {
			// 特別休暇の上限情報取得する
			appAbsenceStartInfoOutput = this.getSpecAbsenceUpperLimit(companyID, appAbsenceStartInfoOutput, workTypeCD);
		}
		// 取得した「就業時間帯表示フラグ」を確認する
		if(controlDispWorkingHours) {
			// 就業時間帯変更時処理
			appAbsenceStartInfoOutput = this.workTimesChangeProcess(
					companyID, 
					appAbsenceStartInfoOutput, 
					workTypeCD.get(), 
					appAbsenceStartInfoOutput.getSelectedWorkTimeCD(), 
					holidayType);
		}
		// 返ってきた「休暇申請起動時の表示情報」を返す
		return appAbsenceStartInfoOutput;
	    
	}
	public WorkTypeObjAppHoliday geWorkTypeObjAppHoliday(AppEmploymentSetting x, int hdType) {
		return x.getListWTOAH().stream().filter(y -> y.getSwingOutAtr().isPresent() ? y.getSwingOutAtr().get().value == hdType : y.getHolidayAppType().isPresent() ? y.getHolidayAppType().get().value == hdType : false).findFirst().get();
	}
	@Override
	public AppAbsenceStartInfoOutput holidayTypeChangeProcess(String companyID, AppAbsenceStartInfoOutput appAbsenceStartInfoOutput, 
			List<String> appDates, HolidayAppType holidayType) {
//	    AppEmploymentSet employmentSet = appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpEmploymentSet().get();

		// INPUT．「休暇申請起動時の表示情報．勤務種類一覧」をクリアする
		appAbsenceStartInfoOutput.setWorkTypeLst(new ArrayList<>());
		
		// 申請理由表示区分を取得する
		AppReasonOutput appReason = commonAlg.getAppReasonDisplay(companyID, ApplicationType.ABSENCE_APPLICATION, Optional.of(holidayType));
		
		// 勤務種類を取得する
		List<WorkType> workTypes = appAbsenceThreeProcess.getWorkTypeDetails(
				companyID,
				holidayType,
				appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpEmploymentSet().isPresent() ? 
				        Optional.ofNullable(appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpEmploymentSet().get().getTargetWorkTypeByAppLst()) : Optional.empty()
				);
//		// 「休暇申請起動時の表示情報．選択中の勤務種類」を更新する
//		List<String> workTypeCDLst = workTypes.stream().map(x -> x.getWorkTypeCode().v()).collect(Collectors.toList());
//		Optional<String> selectedWorkTypeCD = appAbsenceStartInfoOutput.getSelectedWorkTypeCD();
//		if(!selectedWorkTypeCD.isPresent() || !workTypeCDLst.contains(selectedWorkTypeCD.get())) {
//			if(appAbsenceStartInfoOutput.getHdAppSet().getDisplayUnselect() == UseAtr.USE) {
//				appAbsenceStartInfoOutput.setSelectedWorkTypeCD(Optional.empty());
//			} else {
//				appAbsenceStartInfoOutput.setSelectedWorkTypeCD(workTypes.stream().findFirst().map(x -> x.getWorkTypeCode().v()));
//			}
//		}
		
		// 「休暇申請起動時の表示情報」を更新する
		appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().setDisplayAppReason(appReason.getDisplayAppReason());
		appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().setDisplayStandardReason(appReason.getDisplayStandardReason());
		appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().setReasonTypeItemLst(appReason.getReasonTypeItemLst());
		appAbsenceStartInfoOutput.setWorkTypeLst(workTypes);
		
		Optional<String> selectedWorkTypeCD = appAbsenceStartInfoOutput.getSelectedWorkTypeCD();
		if (selectedWorkTypeCD.isPresent()) {
		    appAbsenceStartInfoOutput.setSelectedWorkTypeCD(
		            appAbsenceStartInfoOutput.getWorkTypeLst().stream().filter(x -> x.getWorkTypeCode().v().equals(selectedWorkTypeCD)).collect(Collectors.toList()).size() > 0
		                    ? appAbsenceStartInfoOutput.getSelectedWorkTypeCD() : Optional.of(workTypes.get(0).getWorkTypeCode().v()));
		} else {
		    appAbsenceStartInfoOutput.setSelectedWorkTypeCD(Optional.of(workTypes.get(0).getWorkTypeCode().v()));
		}
		
		// 勤務種類変更時処理
		appAbsenceStartInfoOutput = this.workTypeChangeProcess(
				companyID, 
				appDates, 
				appAbsenceStartInfoOutput, 
				holidayType, 
				appAbsenceStartInfoOutput.getSelectedWorkTypeCD());
		// 返ってきた「休暇申請起動時の表示情報」を返す
		return appAbsenceStartInfoOutput;
	}
	/**
	 * 7-1_申請日の矛盾チェック
	 * @param companyID 会社ID
	 * @param employeeID 申請者
	 * @param appDate 申請日
	 * @param alldayHalfDay 終日半日休暇区分（終日休暇／半日休暇）
	 * @param hdAppSet 休暇申請設定
	 * @return
	 */
	private List<ConfirmMsgOutput> checkContradictionAppDate(String companyID, String employeeID, GeneralDate appDate, Integer alldayHalfDay, HdAppSet hdAppSet) {
		List<ConfirmMsgOutput> result = new ArrayList<>();
		// ドメインモデル「休暇申請設定」を取得する
		AppliedDate appliedDate = hdAppSet.getAppDateContra();
		//「申請日矛盾区分」をチェックする
		if (appliedDate == AppliedDate.DONT_CHECK) {
			return result;
		}
		//アルゴリズム「11.指定日の勤務実績（予定）の勤務種類を取得」を実行する
		WorkType wkType = otherCommonAlgorithm.getWorkTypeScheduleSpec(companyID, employeeID, appDate);
		//＜OUTPUT＞をチェックする
		if(wkType==null){
			//「申請日矛盾区分」をチェックする
			if (appliedDate == AppliedDate.CHECK_IMPOSSIBLE) {
				// 申請日矛盾区分＝「2: チェックする（登録不可）」
				throw new BusinessException("Msg_1519",appDate.toString("yyyy/MM/dd"));
			}
			
			if (appliedDate == AppliedDate.CHECK_AVAILABLE) {
				// 申請日矛盾区分＝「1: チェックする（登録可）」
				result.add(new ConfirmMsgOutput(
						"Msg_1520", 
						Arrays.asList(appDate.toString("yyyy/MM/dd"))));
			}
		} else {
			//アルゴリズム「7-1_01 休暇申請の勤務種類矛盾チェック」を実行する
			boolean error = workTypeCheckHolidayApp(wkType, alldayHalfDay);
			if (error) {
				String wkTypeName = wkType.getName().v();
				// 「申請日矛盾区分」をチェックする
				if (appliedDate == AppliedDate.CHECK_IMPOSSIBLE) {
					// 申請日矛盾区分＝「2: チェックする（登録不可）」
					throw new BusinessException("Msg_1521", appDate.toString("yyyy/MM/dd"), wkTypeName);
				}

				if (appliedDate == AppliedDate.CHECK_AVAILABLE) {
					// 申請日矛盾区分＝「1: チェックする（登録可）」
					result.add(new ConfirmMsgOutput(
							"Msg_1522", 
							Arrays.asList(appDate.toString("yyyy/MM/dd"), wkTypeName)));
				}
			}
		}
		return result;
	}

	/**
	 * 7-1_01 休暇申請の勤務種類矛盾チェック
	 * 
	 * @param wkType
	 * @param allDayHalfDayLeaveAtr 
	 */
	/**
	 * 7-1_01 休暇申請の勤務種類矛盾チェック
	 * @param wkType ドメインモデル「勤務種類」(1 Row) 
	 * @param alldayHalfDay 終日半日休暇区分（終日休暇／半日休暇）
	 * @return
	 */
	private boolean workTypeCheckHolidayApp(WorkType wkType, Integer alldayHalfDay) {
		boolean error = false;
		WorkTypeUnit wkClass = wkType.getDailyWork().getWorkTypeUnit();
		if(wkClass == WorkTypeUnit.OneDay){
			WorkTypeClassification oneDayClass = wkType.getDailyWork().getOneDay();
			boolean isHoliday = oneDayClass.equals(WorkTypeClassification.Holiday)
					|| oneDayClass.equals(WorkTypeClassification.HolidayWork);

			if (isHoliday) {
				error = true;
			}
		}

		return error;
	}

	@Override
	public List<ConfirmMsgOutput> inconsistencyCheck(String companyID, String employeeID, GeneralDate startDate,
			GeneralDate endDate, Integer alldayHalfDay, HdAppSet hdAppSet, boolean mode) {
		List<ConfirmMsgOutput> result = new ArrayList<>();
		// INPUT．モードをチェックする
		if(!mode) {
			return result;
		}
		// INPUT．「申請開始日」からINPUT．「申請終了日」までループする
		for(GeneralDate loopDate = startDate; loopDate.beforeOrEquals(endDate); loopDate = loopDate.addDays(1)) {
			// 7-1_申請日の矛盾チェック
			List<ConfirmMsgOutput> loopResult = this.checkContradictionAppDate(companyID, employeeID, loopDate, alldayHalfDay, hdAppSet);
			// 返ってきた確認メッセージをOUTPUTの「確認メッセージリスト」に追加する
			result.addAll(loopResult);
		}
		// 「確認メッセージリスト」を返す
		return result;
	}

	@Override
	public void checkRemainVacation(String companyID, AppAbsence appAbsence,
			GeneralDate closureStartDate, HdAppSet hdAppSet, HolidayAppType holidayType) {
		/**	・代休チェック区分 - HolidayType: 1*/
		boolean chkSubHoliday = false;
		/**	・振休チェック区分  - HolidayType: 7*/
		boolean chkPause = false;
		/**	・年休チェック区分 - HolidayType: 0*/
		boolean chkAnnual = false;
		/**	・積休チェック区分 - HolidayType: 4*/
		boolean chkFundingAnnual = false;
		/**	・特休チェック区分 - HolidayType: 3*/
		boolean chkSpecial = true;
		/**	・公休チェック区分 */
		boolean chkPublicHoliday = false;
		/**	・超休チェック区分*/
		boolean chkSuperBreak = true;
		
		chkSubHoliday = hdAppSet.getRegisShortLostHd().value == 1 && holidayType == HolidayAppType.SUBSTITUTE_HOLIDAY ? true : false;//休暇申請設定．代休残数不足登録できる
		// chkPause = hdAppSet.getRegisInsuff().value == 1 && holidayType == HolidayAppType.REST_TIME ? true : false;//休暇申請設定．振休残数不足登録できる
		chkAnnual = hdAppSet.getRegisNumYear().value == 1 && holidayType == HolidayAppType.ANNUAL_PAID_LEAVE ? true : false;//休暇申請設定．年休残数不足登録できる
		chkFundingAnnual = hdAppSet.getRegisShortReser().value == 1 && holidayType == HolidayAppType.YEARLY_RESERVE ? true : false;//休暇申請設定．積立年休残数不足登録できる
		
//		Optional<GeneralDate> startDate = appAbsence.getApplication().getStartDate();
//		Optional<GeneralDate> endDate = appAbsence.getApplication().getEndDate();
//		List<GeneralDate> lstDateIsHoliday = otherCommonAlgorithm.lstDateIsHoliday(
//				companyID, 
//				appAbsence.getApplication().getEmployeeID(), 
//				new DatePeriod(startDate.orElse(null), endDate.orElse(null)));
//		List<AppRemainCreateInfor> appData = new ArrayList<>();
//		appData.add(new AppRemainCreateInfor(
//				appAbsence.getApplication().getEmployeeID(), 
//				appAbsence.getApplication().getAppID(), 
//				GeneralDateTime.now(), 
//				startDate.orElse(null), 
//				EnumAdaptor.valueOf(appAbsence.getApplication().getPrePostAtr().value, nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.PrePostAtr.class) , 
//				ApplicationType.ABSENCE_APPLICATION, 
//				appAbsence.getWorkTypeCode() == null ? Optional.empty() : Optional.of(appAbsence.getWorkTypeCode().v()), 
//				appAbsence.getWorkTimeCode() == null ? Optional.empty() : Optional.of(appAbsence.getWorkTimeCode().v()), 
//				Optional.empty(), 
//				Optional.empty(), 
//				Optional.empty(), 
//				startDate, 
//				endDate, 
//				lstDateIsHoliday));
//		
//		InterimRemainCheckInputParam inputParam = new InterimRemainCheckInputParam(
//				companyID, 
//				appAbsence.getApplication().getEmployeeID(), 
//				new DatePeriod(closureStartDate, closureStartDate.addYears(1).addDays(-1)), 
//				false, 
//				startDate.orElse(null), 
//				new DatePeriod(startDate.orElse(null), endDate.orElse(null)),
//				true, 
//				new ArrayList<>(), 
//				new ArrayList<>(), 
//				appData, 
//				chkSubHoliday, 
//				chkPause, 
//				chkAnnual, 
//				chkFundingAnnual,
//				chkSpecial, 
//				chkPublicHoliday, 
//				chkSuperBreak); 
		// 登録時の残数チェック
		// EarchInterimRemainCheck checkResult = interimRemainCheckReg.checkRegister(inputParam);
		//EA.2577
		//代休不足区分 or 振休不足区分 or 年休不足区分 or 積休不足区分 or 特休不足区分 = true（残数不足）
//		if(checkResult.isChkSubHoliday() || checkResult.isChkPause() || checkResult.isChkAnnual() 
//				|| checkResult.isChkFundingAnnual() || checkResult.isChkSpecial()){
//			//QA#100887
//			String name = "";
//			String nametmp = "";
//			if(checkResult.isChkSubHoliday()){
//				//代表者名 - HdAppType.TEMP_HD
//				nametmp = hdAppSet.getObstacleName() == null ? "" : hdAppSet.getObstacleName().v();
//				name = name != "" && name != "" ? name + "," + nametmp : name + nametmp;
//			}
//			if(checkResult.isChkPause()){
//				//振休名称 - HdAppType.SHIFT
//				nametmp = hdAppSet.getFurikyuName() == null ? "" : hdAppSet.getFurikyuName().v();
//				name = name != "" && name != "" ? name + "," + nametmp : name + nametmp;
//			}
//			if(checkResult.isChkAnnual()){
//				//年休名称 - HdAppType.ANNUAL_HD
//				nametmp = hdAppSet.getYearHdName() == null ? "" : hdAppSet.getYearHdName().v();
//				name = name != "" && name != "" ? name + "," + nametmp : name + nametmp;
//			}
//			if(checkResult.isChkFundingAnnual()){
//				//積休名称 - HdAppType.YEARLY_RESERVED
//				nametmp = hdAppSet.getYearResig() == null ? "" : hdAppSet.getYearResig().v();
//				name = name != "" && name != "" ? name + "," + nametmp : name + nametmp;
//			}
//			if(checkResult.isChkSpecial()){
//				//特別休暇名称 - HdAppType.SPECIAL_VACATION
//				nametmp = hdAppSet.getSpecialVaca() == null ? "" : hdAppSet.getSpecialVaca().v();
//				name = name != "" && name != "" ? name + "," + nametmp : name + nametmp;
//			}
//			//エラーメッセージ（Msg_1409）
//			throw new BusinessException("Msg_1409", name);
//		}
	}

	@Override
	public List<ConfirmMsgOutput> holidayCommonCheck(String companyID, AppAbsence appAbsence,
			GeneralDate closureStartDate, HdAppSet hdAppSet, HolidayAppType holidayType, Integer alldayHalfDay, boolean mode) {
		List<ConfirmMsgOutput> result = new ArrayList<>();
		// 申請日の矛盾チェック
//		List<ConfirmMsgOutput> confirmMsgLst1 = this.inconsistencyCheck(
//				companyID, 
//				appAbsence.getApplication().getEmployeeID(), 
//				appAbsence.getApplication().getStartDate().orElse(null), 
//				appAbsence.getApplication().getEndDate().orElse(null), 
//				alldayHalfDay, 
//				hdAppSet,
//				mode);
//		result.addAll(confirmMsgLst1);
		// 休暇残数チェック
		this.checkRemainVacation(
				companyID, 
				appAbsence,
				closureStartDate, 
				hdAppSet, 
				holidayType);
		// 返ってきた確認メッセージリストを返す
		return result;
	}

	@Override
	public List<ConfirmMsgOutput> annualLeaveCheck(boolean mode, String companyID, String employeeID,
			GeneralDate startDate, GeneralDate endDate, String workTypeCD, List<GeneralDate> lstDateIsHoliday,
			AppAbsenceStartInfoOutput appAbsenceStartInfoOutput) {
		List<ConfirmMsgOutput> result = new ArrayList<>();
		// 代休振休優先消化チェック
//		AppEmploymentSetting employmentSet = appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getEmploymentSet()
//				.stream().filter(x -> x.getHolidayOrPauseType() == HolidayAppType.ANNUAL_PAID_LEAVE.value).findFirst().orElse(null);
		// AppEmploymentSetting employmentSet = appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getEmploymentSet();
//		Optional<AppEmploymentSetting> setting = employmentSetLst.stream().filter(x -> 
//		(CollectionUtil.isEmpty(x.getListWTOAH())) ? false : 
//			geWorkTypeObjAppHoliday(x,HolidayAppType.ANNUAL_PAID_LEAVE.value).getSwingOutAtr().isPresent() ? geWorkTypeObjAppHoliday(x,HolidayAppType.ANNUAL_PAID_LEAVE.value).getSwingOutAtr().get().value == HolidayAppType.ANNUAL_PAID_LEAVE.value : geWorkTypeObjAppHoliday(x,HolidayAppType.ANNUAL_PAID_LEAVE.value).getHolidayAppType().isPresent() ? geWorkTypeObjAppHoliday(x,HolidayAppType.ANNUAL_PAID_LEAVE.value).getHolidayAppType().get().value == HolidayAppType.ANNUAL_PAID_LEAVE.value : false
//				
//				).findFirst();
//		AppEmploymentSetting employmentSet = setting.get();
//		List<ConfirmMsgOutput> confirmLst1 = this.checkDigestPriorityHd(
//				mode, 
//				appAbsenceStartInfoOutput.getHdAppSet(), 
//				employmentSet, 
//				appAbsenceStartInfoOutput.getRemainVacationInfo().isSubVacaManage(), 
//				appAbsenceStartInfoOutput.getRemainVacationInfo().isSubHdManage(), 
//				appAbsenceStartInfoOutput.getRemainVacationInfo().getSubVacaRemain(), 
//				appAbsenceStartInfoOutput.getRemainVacationInfo().getSubHdRemain());
//		// 計画年休上限チェック
//		checkLimitAbsencePlan(companyID, employeeID, workTypeCD, startDate, endDate, lstDateIsHoliday);
//		// OUTPUTの確認メッセージを返す
//		result.addAll(confirmLst1);
		return result;
	}

	@Override
	public void checkSpecHoliday(String companyID, GeneralDate startDate, GeneralDate endDate, Boolean mournerAtr,
			SpecAbsenceDispInfo specAbsenceDispInfo, List<GeneralDate> lstDateIsHoliday) {
		SpecialHolidayEvent spHdEv = specAbsenceDispInfo.getSpecHdEvent().get();
		int appDay = 0;//申請する日数
		if(spHdEv.getIncludeHolidays().value == UseAtr.USE.value){ 
			// ・INPUT．「特別休暇表示情報．事象に対する特別休暇」．休日を取得日に含めるがtrue：
			// 申請する日数 = INPUT．「申請終了日」 - INPUT．「申請開始日」 + 1
			appDay = startDate.daysTo(endDate) + 1;
		} else { 
			// ・INPUT．「特別休暇表示情報．事象に対する特別休暇」．休日を取得日に含めるがfalse：
			// 申請する日数 = INPUT．「申請終了日」 - INPUT．「申請開始日」 + 1 - INPUT．「・休日の申請日<List>」
			appDay = startDate.daysTo(endDate) + 1 - lstDateIsHoliday.size();
		}
		int maxDaySpec = 0; // 上限日数
		if(mournerAtr) {
			// ・INPUT．「喪主区分」= TRUE：
			// 上限日数= INPUT．「特別休暇表示情報．上限日数」 + INPUT．「特別休暇表示情報．喪主加算日数」
			maxDaySpec = specAbsenceDispInfo.getMaxDay().orElse(0) + specAbsenceDispInfo.getDayOfRela().orElse(0);
		} else {
			// 上限日数= INPUT．「特別休暇表示情報．上限日数」
			maxDaySpec = specAbsenceDispInfo.getMaxDay().orElse(0);
		}
		if(appDay > maxDaySpec){//申請する日数 > 上限日数 がtrue(appDay > maxDaySpec)
			//エラーメッセージ(Msg_632)(error message)
			throw new BusinessException("Msg_632", Integer.toString(maxDaySpec));
		}
	}

	@Override
	public void checkSpecLeaveProcess(String companyID, GeneralDate startDate, GeneralDate endDate,
			List<GeneralDate> holidayDateLst, Boolean mournerAtr, SpecAbsenceDispInfo specAbsenceDispInfo) {
		if(specAbsenceDispInfo==null) {
			return;
		}
		// 特別休暇の上限チェック
		this.checkSpecHoliday(companyID, startDate, endDate, mournerAtr, specAbsenceDispInfo, holidayDateLst);
	}

	@Override
	public void checkTimeDigestProcess(GeneralDate startDate, GeneralDate endDate) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public List<ConfirmMsgOutput> errorCheckByHolidayType(boolean mode, String companyID, String employeeID,
			GeneralDate startDate, GeneralDate endDate, HolidayAppType holidayType, String workTypeCD, List<GeneralDate> holidayDateLst,
			AppAbsenceStartInfoOutput appAbsenceStartInfoOutput, Optional<Boolean> mournerAtr) {
		List<ConfirmMsgOutput> result = new ArrayList<>();
		switch (holidayType) {
		case ANNUAL_PAID_LEAVE: 
			// INPUT．「休暇種類」 = 年次有給
			// 年休のチェック処理
			List<ConfirmMsgOutput> confirmMsgLst1 = this.annualLeaveCheck(
					mode, 
					companyID, 
					employeeID, 
					startDate, 
					endDate,
					workTypeCD, 
					holidayDateLst, 
					appAbsenceStartInfoOutput);
			result.addAll(confirmMsgLst1);
			break;
		case SPECIAL_HOLIDAY:
			// INPUT．「休暇種類」 = 特別休暇
			// 特別休暇のチェック処理
			this.checkSpecLeaveProcess(
					companyID, 
					startDate, 
					endDate, 
					holidayDateLst,
					mournerAtr.get(),
					appAbsenceStartInfoOutput.getSpecAbsenceDispInfo().orElse(null));
			break;
		case DIGESTION_TIME:
			// INPUT．「休暇種類」 = 時間消化
			// 時間消化のチェック処理
			this.checkTimeDigestProcess(startDate, endDate);
			break;
		default:
			break;
		}
		// 返ってきた確認メッセージをOUTPUT「確認メッセージリスト」として返す
		return result;
	}

	@Override
	public AbsenceCheckRegisterOutput checkAppAbsenceRegister(boolean mode, String companyID, AppAbsenceStartInfoOutput appAbsenceStartInfoOutput,
			ApplyForLeave appBeforeChange,ApplyForLeave newAbsence) {
		AbsenceCheckRegisterOutput result = new AbsenceCheckRegisterOutput();
		//4.社員の当月の期間を算出する
		PeriodCurrentMonth periodCurrentMonth = otherCommonAlgorithm.employeePeriodCurrentMonthCalculate(companyID,newAbsence.getEmployeeID(),GeneralDate.today());
		// 変更後の申請期間をチェックする
		this.changeAbScenePeriodCheck(appBeforeChange, newAbsence, periodCurrentMonth.getStartDate());
		// 申請期間から休日の申請日を取得する
		List<GeneralDate> lstDates = otherCommonAlgorithm.lstDateIsHoliday(newAbsence.getApplication().getEmployeeID()
				, new DatePeriod(newAbsence.getOpAppStartDate().get().getApplicationDate(),newAbsence.getOpAppEndDate().get().getApplicationDate())
				, appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get());
		// 2-1.新規画面登録前の処理
		List<ConfirmMsgOutput> lstConfirmMsg = newBeforeRegister.processBeforeRegister_New(companyID
				, EmploymentRootAtr.APPLICATION
				, false // KAF006: -PhuongDV domain fix pending- confirm input
				, newAbsence.getApplication()
				, null
				, appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpErrorFlag().get() // KAF006: -PhuongDV domain fix pending- confirm input
				, lstDates
				, appAbsenceStartInfoOutput.getAppDispInfoStartupOutput());
		// 申請の矛盾チェック
		List<GeneralDate> dateLst = new ArrayList<GeneralDate>();
		GeneralDate currentDate = newAbsence.getOpAppStartDate().get().getApplicationDate();
		while(currentDate.beforeOrEquals(newAbsence.getOpAppStartDate().get().getApplicationDate())) {
			dateLst.add(currentDate);
			currentDate = currentDate.addDays(1);
		}
		// 勤務種類リスト作成
		List<String> lstWorkType = new ArrayList<String>();
		// KAF006: -PhuongDV domain fix pending- confirm input -> ・申請する勤務種類リスト = INPUT．「休暇申請．反映情報．勤務情報．勤務種類コード」 -> ko co trong input EAP
		lstWorkType.add(newAbsence.getReflectFreeTimeApp().getWorkInfo().getWorkTypeCode().v());
		commonAlgorithm.appConflictCheck(companyID
				, appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0) // KAF006: -PhuongDV domain fix pending- confirm input -> auto lấy ở đầu danh sách
				, dateLst
				, lstWorkType
				, appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getOpActualContentDisplayLst().get());
		// 1日分の取消処理 KAF006: -PhuongDV domain fix pending-
		// Chưa tìm thấy xử lý 
		// 指定期間の暫定残数管理データを作成する
		// Xử lý bên JP đang pending đến tháng 1 KAF006: -PhuongDV domain fix pending-
		
		// 期間内の休出代休残数を取得する
		
		// 期間内の振出振休残数を取得する
		
		// 期間中の年休残数を求める
		
		// 期間中の年休積休残数を取得
		
		// 帰ってきた全ての「確認メッセージ」と取得した「休日の申請日<List>」を返す
		
		// 社員の当月の期間を算出する
//		PeriodCurrentMonth periodCurrentMonth = otherCommonAlgorithm.employeePeriodCurrentMonthCalculate(
//				companyID, 
//				appAbsence.getApplication().getEmployeeID(), 
//				GeneralDate.today());
//		// 休暇種類共通エラーチェック
//		List<ConfirmMsgOutput> confirmMsgLst1 = this.holidayCommonCheck(
//				companyID, 
//				appAbsence, 
//				periodCurrentMonth.getStartDate(), 
//				appAbsenceStartInfoOutput.getHdAppSet(), 
//				holidayType, 
//				alldayHalfDay,
//				mode);
//		result.addAll(confirmMsgLst1);
		// 休暇種類別エラーチェック
//		List<ConfirmMsgOutput> confirmMsgLst2 = this.errorCheckByHolidayType(
//				mode, 
//				companyID, 
//				appAbsence.getApplication().getEmployeeID(), 
//				appAbsence.getApplication().getStartDate().orElse(null), 
//				appAbsence.getApplication().getEndDate().orElse(null),
//				holidayType,
//				workTypeCD, 
//				holidayDateLst, 
//				appAbsenceStartInfoOutput, 
//				mournerAtr);
//		result.addAll(confirmMsgLst2);
		// 「確認メッセージリスト」を返す
		return result;
	}

	/**
	 * 変更後の申請期間をチェックする
	 * @param absceneBefore 元の休暇申請
	 * @param absceneNew 新の休暇申請
	 * @param endDate 締め開始日
	 */
	public void changeAbScenePeriodCheck(ApplyForLeave absceneBefore, ApplyForLeave absceneNew, GeneralDate endDate) {
		
		GeneralDate boforeStartDate = absceneBefore.getApplication().getOpAppStartDate().get().getApplicationDate();
		GeneralDate beforeEndDate = absceneBefore.getApplication().getOpAppEndDate().get().getApplicationDate();
		
		GeneralDate newStartDate = absceneNew.getApplication().getOpAppStartDate().get().getApplicationDate();
		GeneralDate newEndDate = absceneNew.getApplication().getOpAppEndDate().get().getApplicationDate();
		
		//INPUT．「元の休暇申請」とINPUT．「新の休暇申請」をチェックする
		if(boforeStartDate.equals(newStartDate)
			&&  beforeEndDate.equals(newEndDate)) {
			throw new BusinessException("Msg_1700");
		}
		//申請終了日と締め開始日をチェックする
		if(beforeEndDate.before(endDate) || newEndDate.before(endDate)) {
				throw new BusinessException("Msg_236");
		}
	}
	@Override
	public AppAbsenceStartInfoOutput getWorkTypeWorkTimeInfo(String companyID, AppAbsence appAbsence,
			AppAbsenceStartInfoOutput appAbsenceStartInfoOutput) {
//		AppEmploymentSetting employmentSet = appAbsenceStartInfoOutput.getAppDispInfoStartupOutput().getAppDispInfoWithDateOutput().getEmploymentSet();
////		AppEmploymentSetting employmentSet = employmentSetLst.stream().filter(x -> x.getHolidayOrPauseType() == appAbsence.getHolidayAppType().value)
////				.findFirst().orElse(null);
////		Optional<AppEmploymentSetting> setting = employmentSetLst.stream().filter(x -> 
////		(CollectionUtil.isEmpty(x.getListWTOAH())) ? false : 
////			geWorkTypeObjAppHoliday(x,appAbsence.getHolidayAppType().value).getSwingOutAtr().isPresent() ? geWorkTypeObjAppHoliday(x,appAbsence.getHolidayAppType().value).getSwingOutAtr().get().value == appAbsence.getHolidayAppType().value : geWorkTypeObjAppHoliday(x,appAbsence.getHolidayAppType().value).getHolidayAppType().isPresent() ? geWorkTypeObjAppHoliday(x,appAbsence.getHolidayAppType().value).getHolidayAppType().get().value == appAbsence.getHolidayAppType().value : false
////				
////				).findFirst();
////		AppEmploymentSetting employmentSet = employmentSetLst;
//		// 選択可能の勤務種類を取得する
//		List<WorkType> workTypeLst = appAbsenceThreeProcess.getWorkTypeDetails(
//				employmentSet,
//				companyID,
//				appAbsence.getHolidayAppType(),
//				appAbsence.getAllDayHalfDayLeaveAtr().value, 
//				appAbsence.isHalfDayFlg());
//		// 申請済み勤務種類の存在判定と取得
//		ApplyWorkTypeOutput applyWorkTypeOutput = commonAlgorithm.appliedWorkType(companyID, workTypeLst, appAbsence.getWorkTypeCode().v());
//		// INPUT．「休暇申請起動時の表示情報」を更新する
//		appAbsenceStartInfoOutput.setWorkTypeLst(applyWorkTypeOutput.getWkTypes());
//		appAbsenceStartInfoOutput.setSelectedWorkTypeCD(Optional.of(appAbsence.getWorkTypeCode().v()));
//		appAbsenceStartInfoOutput.setSelectedWorkTimeCD(appAbsence.getWorkTimeCode() == null ? Optional.empty() : Optional.of(appAbsence.getWorkTimeCode().v()));
//		appAbsenceStartInfoOutput.setWorkTypeNotRegister(applyWorkTypeOutput.isMasterUnregister());
//		// 勤務種類変更時処理
//		appAbsenceStartInfoOutput = this.workTypeChangeProcess(
//				companyID, 
//				appAbsenceStartInfoOutput, 
//				appAbsence.getHolidayAppType(), 
//				appAbsenceStartInfoOutput.getSelectedWorkTypeCD());
//		// 返ってきた「休暇申請起動時の表示情報」を返す
//		return appAbsenceStartInfoOutput;
		return null;
	}
    @Override
    public AppAbsenceStartInfoOutput getVacationActivation(String companyID,
            AppDispInfoStartupOutput appDispInfoStartupOutput) {
        
        // 休暇申請設定を取得する
        HolidayRequestSetOutput holidayRequestSetOutput = this.getHolidayRequestSet(companyID);
        
        // 休暇残数情報を取得する
        RemainVacationInfo remainInfo = this.getRemainVacationInfo(companyID, 
                appDispInfoStartupOutput.getAppDispInfoNoDateOutput().getEmployeeInfoLst().get(0).getSid(), 
                appDispInfoStartupOutput.getAppDispInfoWithDateOutput().getBaseDate());
        
        // 取得した情報もとに「休暇残数情報」にセットして返す
        AppAbsenceStartInfoOutput output = new AppAbsenceStartInfoOutput();
        output.setAppDispInfoStartupOutput(appDispInfoStartupOutput);
        output.setHdAppSet(holidayRequestSetOutput.getHdAppSet());
        output.setVacationAppReflect(holidayRequestSetOutput.getVacationAppReflect());
        output.setRemainVacationInfo(remainInfo);
        
        return output;
    }
}
