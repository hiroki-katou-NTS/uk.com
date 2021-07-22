package nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.request.app.command.application.holidayshipment.refactor5.command.AbsenceLeaveAppCmd;
import nts.uk.ctx.at.request.app.command.application.holidayshipment.refactor5.command.RecruitmentAppCmd;
import nts.uk.ctx.at.request.app.find.application.WorkInformationForApplicationDto;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoStartupDto;
import nts.uk.ctx.at.request.app.find.application.common.AppDispInfoWithDateDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.dto.DisplayInforWhenStarting;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.dto.DisplayInformationApplication;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.dto.LinkingManagementInforDto;
import nts.uk.ctx.at.request.app.find.application.holidayshipment.refactor5.dto.RemainingHolidayInforDto;
import nts.uk.ctx.at.request.dom.application.WorkInformationForApplication;
import nts.uk.ctx.at.request.dom.application.common.service.setting.CommonAlgorithm;
import nts.uk.ctx.at.request.dom.application.common.service.setting.WorkInfoListOutput;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveAppRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.AppHdsubRec;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.AppHdsubRecRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.recruitmentapp.RecruitmentAppRepository;
import nts.uk.ctx.at.shared.app.find.remainingnumber.paymana.PayoutSubofHDManagementDto;
import nts.uk.ctx.at.shared.app.find.remainingnumber.subhdmana.dto.LeaveComDayOffManaDto;
import nts.uk.ctx.at.shared.app.find.worktime.worktimeset.dto.WorkTimeSettingDto;
import nts.uk.ctx.at.shared.app.find.worktype.WorkTypeDto;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.EmploymentHistShareImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.SharedSidPeriodDateEmploymentImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.export.query.AbsenceReruitmentMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbasMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.TargetSelectionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakDayOffMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.common.empinfo.grantremainingdata.daynumber.LeaveRemainingDayNumber;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.ComDayOffManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManagementData;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveEmSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeClassification;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;

/**
 * @author ThanhPV
 */
/**
 * @author thanhpv
 *
 */
@Stateless
public class HolidayShipmentScreenBFinder {

	@Inject
	private ComSubstVacationRepository comSubrepo;
	
	@Inject
	private CompensLeaveComSetRepository compensLeaveComSetRepo;
	
	@Inject
	private RecruitmentAppRepository recRepo;
	
	@Inject
	private AbsenceLeaveAppRepository absRepo;
	
	@Inject
	private HolidayShipmentScreenAFinder aFinder;
	
	@Inject
	private WorkTypeRepository workTypeRepo;
	
	@Inject
	private LeaveComDayOffManaRepository leaveComDayOffManaRepository;
	
	@Inject
	private PayoutSubofHDManaRepository payoutSubofHDManaRepository;
	
	@Inject
	private AppHdsubRecRepository appHdsubRecRepository;
	
	@Inject
	private CommonAlgorithm commonAlgorithm;
	
	@Inject
    private ComDayOffManaDataRepository comDayOffManaDataRepo;
    
    @Inject
    private LeaveManaDataRepository leaveManaDataRepo;
    
    @Inject
    private ShareEmploymentAdapter shareEmploymentAdapter;
    
    @Inject
    private CompensLeaveEmSetRepository compensLeaveEmSetRepo;
    
    @Inject
    private InterimBreakDayOffMngRepository interimBreakDayOffMngRepo;
    
    @Inject
    private ClosureEmploymentRepository closureEmploymentRepo;
    
    @Inject
    private ClosureRepository closureRepo;
    
    @Inject
    private EmpEmployeeAdapter empEmployeeAdapter;
    
    @Inject
    private SubstitutionOfHDManaDataRepository substitutionOfHDManaDataRepo;
    
    @Inject
    private PayoutSubofHDManaRepository payoutSubofHDManaRepo;
    
    @Inject
    private PayoutManagementDataRepository payoutManagementDataRepo;
    
    @Inject
    private EmpSubstVacationRepository empSubstVacationRepo;
    
    @Inject
    private ComSubstVacationRepository comSubstVacationRepo;
    
    @Inject
    private InterimRecAbasMngRepository interimRecAbasMngRepo;
    
    @Inject
    private LeaveComDayOffManaRepository leaveComDayOffManaRepo;
    
    @Inject
    private PayoutSubofHDManaRepository payoutHdManaRepo;
	
	// 1.振休振出申請（詳細）起動処理(đơn xin nghỉ bù làm bù(detail) Xử lý khởi động)
	public DisplayInforWhenStarting startPageBRefactor(String companyId, String appId, AppDispInfoStartupDto appDispInfoStartup) {
		DisplayInforWhenStarting result = new DisplayInforWhenStarting();
		result.appDispInfoStartup = appDispInfoStartup;
		RequireM11Imp requireM11Imp = new RequireM11Imp(comDayOffManaDataRepo, leaveComDayOffManaRepo, leaveManaDataRepo, shareEmploymentAdapter, compensLeaveEmSetRepo, compensLeaveComSetRepo, interimBreakDayOffMngRepo, closureEmploymentRepo, closureRepo, empEmployeeAdapter, substitutionOfHDManaDataRepo, payoutSubofHDManaRepo, payoutManagementDataRepo, empSubstVacationRepo, comSubstVacationRepo, interimRecAbasMngRepo, payoutHdManaRepo);

		//ドメインモデル「振休振出申請」を取得する(Lấy domain[đơn xin nghi bu lam bu])
		Optional<RecruitmentApp> rec = recRepo.findByAppId(appId);
		Optional<AbsenceLeaveApp> abs = absRepo.findByAppId(appId);
		Optional<AppHdsubRec> appHdsubRec = appHdsubRecRepository.findByAppId(appId);
		if(appHdsubRec.isPresent() && rec.isPresent()) {
			abs = absRepo.findByAppId(appHdsubRec.get().getAbsenceLeaveAppID());
		}else if(appHdsubRec.isPresent()){
			rec = recRepo.findByAppId(appHdsubRec.get().getRecAppID());
		}
		
		result.setRec(rec.map(c-> RecruitmentAppCmd.fromDomain(c)).orElse(null));
		result.setAbs(abs.map(c-> AbsenceLeaveAppCmd.fromDomain(c)).orElse(null));
		
		String employeeID = null;
		if(result.existRec()) {
			employeeID = result.rec.getApplication().getEmployeeID();
		}else {
			employeeID = result.abs.getApplication().getEmployeeID();
		}
		
		WorkInfoListOutput workInfos = null;
		// 振休振出申請設定の取得(get setting đơn xin nghỉ bù làm bù)
		aFinder.getWithDrawalReqSet(companyId, result);
		if(result.existAbs()) {
			DisplayInformationApplication applicationForHoliday = new DisplayInformationApplication();
			//振休用勤務種類の取得(Lấy worktype nghỉ bù)
			List<WorkType> workTypeListAbs = aFinder.getWorkTypeForHoliday(companyId, appDispInfoStartup.getAppDispInfoWithDateOutput().getEmpHistImport().getEmploymentCode(), result.abs.workInformation.getWorkType());
			//振休申請起動時の表示情報．勤務種類リスト=取得した振休用勤務種類(List) /(DisplayInfo khi khởi động đơn xin nghỉ bù. WorktypeList= worktype nghỉ bù(List) đã lấy)
			applicationForHoliday.setWorkTypeList(workTypeListAbs.stream().map(c->WorkTypeDto.fromDomain(c)).collect(Collectors.toList()));
			// 申請中の振休用の就業時間帯を取得する
			workInfos = commonAlgorithm.getWorkInfoList(companyId, 
    			        result.getAbs().getWorkInformation().getWorkType(), 
    			        Optional.ofNullable(result.getAbs().getWorkInformation().getWorkTime()),
    			        workTypeListAbs, 
    			        appDispInfoStartup.getAppDispInfoWithDateOutput().getOpWorkTimeLst()
    			            .stream().map(x -> AppDispInfoWithDateDto.toDomainWorkTime(x)).collect(Collectors.toList()));
			
			WorkInformationForApplication workInformationForApplication = null;
	        if (result.getAbs().getWorkInformation().getWorkTime() != null || result.getAbs().getWorkInformation().getWorkType() != null) {
	            workInformationForApplication = new WorkInformationForApplication(
	                    new WorkTimeCode(result.getAbs().getWorkInformation().getWorkTime()), 
	                    new WorkTypeCode(result.getAbs().getWorkInformation().getWorkType()));
	        }
			// 「振休申請起動時の表示情報」にセットする
			applicationForHoliday.setWorkInformationForApplication(WorkInformationForApplicationDto.fromDomain(workInformationForApplication));
			result.applicationForHoliday = applicationForHoliday;
		}
		if(result.existRec()) {
			DisplayInformationApplication applicationForWorkingDay = new DisplayInformationApplication();
			//振出用勤務種類の取得(Lấy worktype làm bù)
			List<WorkType> workTypeListRec = aFinder.getWorkTypeForWorkingDay(companyId, appDispInfoStartup.getAppDispInfoWithDateOutput().getEmpHistImport().getEmploymentCode(), result.rec.workInformation.getWorkType());
			//振出申請起動時の表示情報．勤務種類リスト=取得した振出用勤務種類(List)/ (DisplayInfo khi khởi động đơn xin làm bù. WorktypeList = worktype làm bù(list đã lấy))
			applicationForWorkingDay.setWorkTypeList(workTypeListRec.stream().map(c->WorkTypeDto.fromDomain(c)).collect(Collectors.toList()));
			// 申請中の振出用の就業時間帯を取得する
			workInfos = commonAlgorithm.getWorkInfoList(companyId, 
                    result.getRec().getWorkInformation().getWorkType(), 
                    Optional.ofNullable(result.getRec().getWorkInformation().getWorkTime()),
                    workTypeListRec, 
                    workInfos == null ? appDispInfoStartup.getAppDispInfoWithDateOutput().getOpWorkTimeLst()
                        .stream().map(x -> AppDispInfoWithDateDto.toDomainWorkTime(x)).collect(Collectors.toList()) : workInfos.getWorkTimes());
			
			WorkInformationForApplication workInformationForApplication = null;
            if (result.getRec().getWorkInformation().getWorkTime() != null || result.getAbs().getWorkInformation().getWorkType() != null) {
                workInformationForApplication = new WorkInformationForApplication(
                        new WorkTimeCode(result.getRec().getWorkInformation().getWorkTime()), 
                        new WorkTypeCode(result.getRec().getWorkInformation().getWorkType()));
            }
			// 「振休振出申請起動時の表示情報」にセットする
			applicationForWorkingDay.setWorkInformationForApplication(WorkInformationForApplicationDto.fromDomain(workInformationForApplication));
			
			result.applicationForWorkingDay = applicationForWorkingDay;
		}
		result.appDispInfoStartup.getAppDispInfoWithDateOutput().setOpWorkTimeLst(
		        workInfos.getWorkTimes().stream().map(x -> WorkTimeSettingDto.fromDomain(x)).collect(Collectors.toList()));
		//[No.506]振休残数を取得する ([No.506]Lấy số ngày nghỉ bù còn lại)y
		LeaveRemainingDayNumber leaveRemainingDayNumber = AbsenceReruitmentMngInPeriodQuery.getAbsRecMngRemain(
				requireM11Imp, new CacheCarrier(), employeeID, GeneralDate.today());

		RemainingHolidayInforDto remainingHolidayInfor = new RemainingHolidayInforDto();
		remainingHolidayInfor.setRemainDays(leaveRemainingDayNumber.v());
		
		result.setRemainingHolidayInfor(remainingHolidayInfor);
		
		//振休の紐付け管理区分を取得する get phân loại quản lý liên kết của nghỉ bù
		Optional<ComSubstVacation> comSubstVacation = comSubrepo.findById(companyId);
		if(comSubstVacation.isPresent()) {
			result.setHolidayManage(comSubstVacation.get().getLinkingManagementATR().value);
		}else {
			result.setHolidayManage(ManageDistinct.NO.value);
		}
		
		//代休の紐付け管理区分を取得する get phân loại quản lý liên kết của nghỉ thay thế
		CompensatoryLeaveComSetting compensatoryLeaveComSetting = compensLeaveComSetRepo.find(companyId);
		if(compensatoryLeaveComSetting == null) {
			result.setSubstituteManagement(ManageDistinct.NO.value);
		}else {
			result.setSubstituteManagement(compensatoryLeaveComSetting.getIsManaged().value);
		}
		
		LinkingManagementInforDto linkingManagementInfor = this.getLinkingManagementInfor(companyId, employeeID, rec, abs, result.substituteManagement == 1, result.holidayManage == 1);
		if(result.existRec()) {
			result.rec.leaveComDayOffMana = linkingManagementInfor.recLeaveComDayOffMana;
		}
		if(result.existAbs()){
			result.abs.leaveComDayOffMana = linkingManagementInfor.absLeaveComDayOffMana;
			result.abs.payoutSubofHDManagements = linkingManagementInfor.absPayoutSubofHDManagements;
		}
		
		return result;
	}
	
	
	/**
	 * @name 紐付管理情報を取得する
	 * @param companyId 会社ID
	 * @param appId 社員ID
	 * @param rec 振出申請
	 * @param abs 振休申請
	 * @param substituteManagement 代休紐付け管理区分
	 * @param holidayManage 振休紐付け管理区分
	 * @return
	 */
	public LinkingManagementInforDto getLinkingManagementInfor(String companyId, String employeeID, Optional<RecruitmentApp> rec, Optional<AbsenceLeaveApp> abs, boolean substituteManagement, boolean holidayManage) {
		LinkingManagementInforDto result = new LinkingManagementInforDto();
		result.recLeaveComDayOffMana = new ArrayList<>();
		result.absLeaveComDayOffMana = new ArrayList<>();
		result.absPayoutSubofHDManagements = new ArrayList<>();
		//INPUT．振出申請をチェックする
		if(rec.isPresent()) {
			//<<Public>> 指定した勤務種類をすべて取得する
			Optional<WorkType> workType = workTypeRepo.findByDeprecated(companyId, rec.get().getWorkInformation().getWorkTypeCode().v());
			if(workType.isPresent()) {
				result.recLeaveComDayOffMana = this.getLinkingManagement(employeeID, rec.get().getAppDate().getApplicationDate(), workType.get(), substituteManagement).stream().map(c-> LeaveComDayOffManaDto.fromDomain(c)).collect(Collectors.toList());
			}
		}
		
		if(abs.isPresent()) {
			//<<Public>> 指定した勤務種類をすべて取得する
			Optional<WorkType> workType = workTypeRepo.findByDeprecated(companyId, abs.get().getWorkInformation().getWorkTypeCode().v());
			if(workType.isPresent()) {
				result.absLeaveComDayOffMana = this.getLinkingManagement(employeeID, abs.get().getAppDate().getApplicationDate(), workType.get(), substituteManagement).stream().map(c-> LeaveComDayOffManaDto.fromDomain(c)).collect(Collectors.toList());
				result.absPayoutSubofHDManagements = this.getLinkingManagementAbs(employeeID, abs.get().getAppDate().getApplicationDate(), workType.get(), holidayManage).stream().map(c-> PayoutSubofHDManagementDto.fromDomain(c)).collect(Collectors.toList());
			}
		}
		
		return result;
	}
	
	/**
	 * @name 振出の紐付管理情報を取得する
	 * @param employeeID 社員ID
	 * @param applicationDate 対象日
	 * @param workType 勤務種類
	 * @param substituteManagement 代休紐付け管理区分
	 * @return
	 */
	public List<LeaveComDayOffManagement> getLinkingManagement(String employeeID, GeneralDate applicationDate, WorkType workType, boolean substituteManagement) {
		//INPUT．代休紐付け管理区分をチェックする
		if(substituteManagement && (
				(workType.getDailyWork().getWorkTypeUnit().isOneDay() && workType.getDailyWork().getOneDay() == WorkTypeClassification.SubstituteHoliday) || 
				(workType.getDailyWork().getWorkTypeUnit().isMonringAndAfternoon() && (workType.getDailyWork().getMorning() == WorkTypeClassification.SubstituteHoliday || workType.getDailyWork().getAfternoon() == WorkTypeClassification.SubstituteHoliday))
				)
		){
			return leaveComDayOffManaRepository.getByListDate(employeeID, Arrays.asList(applicationDate))
					.stream().filter(c-> { 
						return c.getAssocialInfo().getTargetSelectionAtr() == TargetSelectionAtr.REQUEST 
								&& c.getAssocialInfo().getDayNumberUsed().v().compareTo(workType.getDailyWork().getWorkTypeUnit().isOneDay() ? 1 : 0.5) == 0;
						}
					).collect(Collectors.toList());
		}
		return new ArrayList<>();
	}
	
	
	/**
	 * @name 振休の紐付管理情報を取得する
	 * @param employeeID 社員ID
	 * @param applicationDate 対象日
	 * @param workType 勤務種類
	 * @param substituteManagement 代休紐付け管理区分
	 * @param holidayManage 振休紐付け管理区分
	 * @return
	 */
	public List<PayoutSubofHDManagement> getLinkingManagementAbs(String employeeID, GeneralDate applicationDate, WorkType workType, boolean holidayManage) {
		
		//INPUT．「勤務種類」が代休の勤務種類かチェックする
		//INPUT．代休紐付け管理区分をチェックする
		//ドメインモデル「休出代休紐付け管理」を取得する
		// get form this.getLinkingManagementRec()
		
		//INPUT．「勤務種類」が振休の勤務種類かチェックする
		if(holidayManage && (
				(workType.getDailyWork().getWorkTypeUnit().isOneDay() && workType.getDailyWork().getOneDay() == WorkTypeClassification.Pause) || 
				(workType.getDailyWork().getWorkTypeUnit().isMonringAndAfternoon() && (workType.getDailyWork().getMorning() == WorkTypeClassification.Pause || workType.getDailyWork().getAfternoon() == WorkTypeClassification.Pause))
				)
		){
			return payoutSubofHDManaRepository.getByListDate(employeeID, Arrays.asList(applicationDate))
					.stream().filter(c-> { 
						return c.getAssocialInfo().getTargetSelectionAtr() == TargetSelectionAtr.REQUEST 
								&& c.getAssocialInfo().getDayNumberUsed().v().compareTo(workType.getDailyWork().getWorkTypeUnit().isOneDay() ? 1 : 0.5) == 0;
						}
					).collect(Collectors.toList());
		}
		return new ArrayList<>();
	}
	
	@AllArgsConstructor
    public class RequireM11Imp implements BreakDayOffMngInPeriodQuery.RequireM11, AbsenceReruitmentMngInPeriodQuery.RequireM11 {
        private ComDayOffManaDataRepository comDayOffManaDataRepo;
        
        private LeaveComDayOffManaRepository leaveComDayOffManaRepo;
        
        private LeaveManaDataRepository leaveManaDataRepo;
        
        private ShareEmploymentAdapter shareEmploymentAdapter;
        
        private CompensLeaveEmSetRepository compensLeaveEmSetRepo;
        
        private CompensLeaveComSetRepository compensLeaveComSetRepo;
        
        private InterimBreakDayOffMngRepository interimBreakDayOffMngRepo;
        
        private ClosureEmploymentRepository closureEmploymentRepo;
        
        private ClosureRepository closureRepo;
        
        private EmpEmployeeAdapter empEmployeeAdapter;
        
        private SubstitutionOfHDManaDataRepository substitutionOfHDManaDataRepo;
        
        private PayoutSubofHDManaRepository payoutSubofHDManaRepo;
        
        private PayoutManagementDataRepository payoutManagementDataRepo;
        
        private EmpSubstVacationRepository empSubstVacationRepo;
        
        private ComSubstVacationRepository comSubstVacationRepo;
        
        private InterimRecAbasMngRepository interimRecAbasMngRepo;
        
        private PayoutSubofHDManaRepository payoutHdManaRepo;

        @Override
        public List<CompensatoryDayOffManaData> getBySidYmd(String companyId, String employeeId,
                GeneralDate startDateAggr) {
            return comDayOffManaDataRepo.getBySidYmd(companyId, employeeId, startDateAggr);
        }

        @Override
        public List<LeaveComDayOffManagement> getBycomDayOffID(String sid, GeneralDate digestDate) {
            return leaveComDayOffManaRepo.getBycomDayOffID(sid, digestDate);
        }

        @Override
        public List<LeaveManagementData> getBySidYmd(String cid, String sid, GeneralDate ymd, DigestionAtr state) {
            return leaveManaDataRepo.getBySidYmd(cid, sid, ymd, state);
        }

        @Override
        public List<LeaveComDayOffManagement> getByLeaveID(String sid, GeneralDate occDate) {
            return leaveComDayOffManaRepo.getByLeaveID(sid, occDate);
        }

        @Override
        public Optional<BsEmploymentHistoryImport> findEmploymentHistory(String companyId, String employeeId,
                GeneralDate baseDate) {
            return shareEmploymentAdapter.findEmploymentHistory(companyId, employeeId, baseDate);
        }

        @Override
        public CompensatoryLeaveEmSetting findComLeavEmpSet(String companyId, String employmentCode) {
            return compensLeaveEmSetRepo.find(companyId, employmentCode);
        }

        @Override
        public CompensatoryLeaveComSetting findComLeavComSet(String companyId) {
            return compensLeaveComSetRepo.find(companyId);
        }

        @Override
        public List<EmploymentHistShareImport> findByEmployeeIdOrderByStartDate(String employeeId) {
            return shareEmploymentAdapter.findByEmployeeIdOrderByStartDate(employeeId);
        }

        @Override
        public List<InterimDayOffMng> getDayOffBySidPeriod(String sid, DatePeriod period) {
            return interimBreakDayOffMngRepo.getDayOffBySidPeriod(sid, period);
        }

        @Override
        public List<InterimBreakMng> getBySidPeriod(String sid, DatePeriod period) {
            return interimBreakDayOffMngRepo.getBySidPeriod(sid, period);
        }

        @Override
        public Optional<ClosureEmployment> employmentClosure(String companyID, String employmentCD) {
            return closureEmploymentRepo.findByEmploymentCD(companyID, employmentCD);
        }

        @Override
        public List<SharedSidPeriodDateEmploymentImport> employmentHistory(CacheCarrier cacheCarrier, List<String> sids,
                DatePeriod datePeriod) {
            return shareEmploymentAdapter.getEmpHistBySidAndPeriodRequire(cacheCarrier, sids, datePeriod);
        }

        @Override
        public List<Closure> closure(String companyId) {
            return closureRepo.findAll(companyId);
        }

        @Override
        public EmployeeImport employee(CacheCarrier cacheCarrier, String empId) {
            return empEmployeeAdapter.findByEmpIdRequire(cacheCarrier, empId);
        }

        @Override
        public List<PayoutSubofHDManagement> getPayoutSubWithDateUse(String sid, GeneralDate dateOfUse,
                GeneralDate baseDate) {
            return payoutHdManaRepo.getWithDateUse(sid, dateOfUse, baseDate);
        }

        @Override
        public List<PayoutSubofHDManagement> getPayoutSubWithOutbreakDay(String sid, GeneralDate outbreakDay,
                GeneralDate baseDate) {
            return payoutHdManaRepo.getWithOutbreakDay(sid, outbreakDay, baseDate);
        }

        @Override
        public List<LeaveComDayOffManagement> getLeaveComWithDateUse(String sid, GeneralDate dateOfUse,
                GeneralDate baseDate) {
            return leaveComDayOffManaRepo.getLeaveComWithDateUse(sid, dateOfUse, baseDate);
        }

        @Override
        public List<LeaveComDayOffManagement> getLeaveComWithOutbreakDay(String sid, GeneralDate outbreakDay,
                GeneralDate baseDate) {
            return leaveComDayOffManaRepo.getLeaveComWithOutbreakDay(sid, outbreakDay, baseDate);
        }

        @Override
        public List<SubstitutionOfHDManagementData> getByYmdUnOffset(String cid, String sid, GeneralDate ymd,
                double unOffseDays) {
            return substitutionOfHDManaDataRepo.getByYmdUnOffset(cid, sid, ymd, unOffseDays);
        }

        @Override
        public List<PayoutSubofHDManagement> getBySubId(String sid, GeneralDate digestDate) {
            return payoutSubofHDManaRepo.getBySubId(sid, digestDate);
        }

        @Override
        public List<PayoutManagementData> getByUnUseState(String cid, String sid, GeneralDate ymd, double unUse,
                DigestionAtr state) {
            return payoutManagementDataRepo.getByUnUseState(cid, sid, ymd, unUse, state);
        }

        @Override
        public List<PayoutSubofHDManagement> getByPayoutId(String sid, GeneralDate occDate) {
            return payoutSubofHDManaRepo.getByPayoutId(sid, occDate);
        }

        @Override
        public Optional<EmpSubstVacation> findEmpById(String companyId, String contractTypeCode) {
            return empSubstVacationRepo.findById(companyId, contractTypeCode);
        }

        @Override
        public Optional<ComSubstVacation> findComById(String companyId) {
            return comSubstVacationRepo.findById(companyId);
        }

        @Override
        public List<InterimAbsMng> getAbsBySidDatePeriod(String sid, DatePeriod period) {
            return interimRecAbasMngRepo.getAbsBySidDatePeriod(sid, period);
        }

        @Override
        public List<InterimRecMng> getRecBySidDatePeriod(String sid, DatePeriod period) {
            return interimRecAbasMngRepo.getRecBySidDatePeriod(sid, period);
        }

        @Override
        public List<Closure> closureActive(String companyId, UseClassification useAtr) {
            return closureRepo.findAllActive(companyId, useAtr);
        }
    }
}
