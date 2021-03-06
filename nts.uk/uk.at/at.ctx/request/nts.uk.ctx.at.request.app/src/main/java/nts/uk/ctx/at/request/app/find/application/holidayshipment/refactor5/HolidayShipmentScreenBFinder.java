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
import nts.uk.shr.com.context.AppContexts;

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
	
	// 1.??????????????????????????????????????????(????n xin ngh??? b?? l??m b??(detail) X??? l?? kh???i ?????ng)
	public DisplayInforWhenStarting startPageBRefactor(String companyId, String appId, AppDispInfoStartupDto appDispInfoStartup) {
		DisplayInforWhenStarting result = new DisplayInforWhenStarting();
		result.appDispInfoStartup = appDispInfoStartup;
		RequireM11Imp requireM11Imp = new RequireM11Imp(comDayOffManaDataRepo, leaveComDayOffManaRepo, leaveManaDataRepo, shareEmploymentAdapter, compensLeaveEmSetRepo, compensLeaveComSetRepo, interimBreakDayOffMngRepo, closureEmploymentRepo, closureRepo, empEmployeeAdapter, substitutionOfHDManaDataRepo, payoutSubofHDManaRepo, payoutManagementDataRepo, empSubstVacationRepo, comSubstVacationRepo, interimRecAbasMngRepo, payoutHdManaRepo);

		//????????????????????????????????????????????????????????????(L???y domain[????n xin nghi bu lam bu])
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
		// ?????????????????????????????????(get setting ????n xin ngh??? b?? l??m b??)
		aFinder.getWithDrawalReqSet(companyId, result);
		if(result.existAbs()) {
			DisplayInformationApplication applicationForHoliday = new DisplayInformationApplication();
			//??????????????????????????????(L???y worktype ngh??? b??)
			List<WorkType> workTypeListAbs = aFinder.getWorkTypeForHoliday(companyId, appDispInfoStartup.getAppDispInfoWithDateOutput().getEmpHistImport().getEmploymentCode(), result.abs.workInformation.getWorkType());
			//????????????????????????????????????????????????????????????=?????????????????????????????????(List) /(DisplayInfo khi kh???i ?????ng ????n xin ngh??? b??. WorktypeList= worktype ngh??? b??(List) ???? l???y)
			applicationForHoliday.setWorkTypeList(workTypeListAbs.stream().map(c->WorkTypeDto.fromDomain(c)).collect(Collectors.toList()));
			// ??????????????????????????????????????????????????????
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
			// ????????????????????????????????????????????????????????????
			applicationForHoliday.setWorkInformationForApplication(WorkInformationForApplicationDto.fromDomain(workInformationForApplication));
			result.applicationForHoliday = applicationForHoliday;
		}
		if(result.existRec()) {
			DisplayInformationApplication applicationForWorkingDay = new DisplayInformationApplication();
			//??????????????????????????????(L???y worktype l??m b??)
			List<WorkType> workTypeListRec = aFinder.getWorkTypeForWorkingDay(companyId, appDispInfoStartup.getAppDispInfoWithDateOutput().getEmpHistImport().getEmploymentCode(), result.rec.workInformation.getWorkType());
			//????????????????????????????????????????????????????????????=?????????????????????????????????(List)/ (DisplayInfo khi kh???i ?????ng ????n xin l??m b??. WorktypeList = worktype l??m b??(list ???? l???y))
			applicationForWorkingDay.setWorkTypeList(workTypeListRec.stream().map(c->WorkTypeDto.fromDomain(c)).collect(Collectors.toList()));
			// ??????????????????????????????????????????????????????
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
			// ??????????????????????????????????????????????????????????????????
			applicationForWorkingDay.setWorkInformationForApplication(WorkInformationForApplicationDto.fromDomain(workInformationForApplication));
			
			result.applicationForWorkingDay = applicationForWorkingDay;
		}
		result.appDispInfoStartup.getAppDispInfoWithDateOutput().setOpWorkTimeLst(
		        workInfos.getWorkTimes().stream().map(x -> WorkTimeSettingDto.fromDomain(x)).collect(Collectors.toList()));
		//[No.506]??????????????????????????? ([No.506]L???y s??? ng??y ngh??? b?? c??n l???i)y
		LeaveRemainingDayNumber leaveRemainingDayNumber = AbsenceReruitmentMngInPeriodQuery.getAbsRecMngRemain(
				requireM11Imp, new CacheCarrier(), employeeID, GeneralDate.today());

		RemainingHolidayInforDto remainingHolidayInfor = new RemainingHolidayInforDto();
		remainingHolidayInfor.setRemainDays(leaveRemainingDayNumber.v());
		
		result.setRemainingHolidayInfor(remainingHolidayInfor);
		
		//????????????????????????????????????????????? get ph??n lo???i qu???n l?? li??n k???t c???a ngh??? b??
		Optional<ComSubstVacation> comSubstVacation = comSubrepo.findById(companyId);
		if(comSubstVacation.isPresent()) {
			result.setHolidayManage(comSubstVacation.get().getLinkingManagementATR().value);
		}else {
			result.setHolidayManage(ManageDistinct.NO.value);
		}
		
		//????????????????????????????????????????????? get ph??n lo???i qu???n l?? li??n k???t c???a ngh??? thay th???
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
	 * @name ?????????????????????????????????
	 * @param companyId ??????ID
	 * @param appId ??????ID
	 * @param rec ????????????
	 * @param abs ????????????
	 * @param substituteManagement ???????????????????????????
	 * @param holidayManage ???????????????????????????
	 * @return
	 */
	public LinkingManagementInforDto getLinkingManagementInfor(String companyId, String employeeID, Optional<RecruitmentApp> rec, Optional<AbsenceLeaveApp> abs, boolean substituteManagement, boolean holidayManage) {
		LinkingManagementInforDto result = new LinkingManagementInforDto();
		result.recLeaveComDayOffMana = new ArrayList<>();
		result.absLeaveComDayOffMana = new ArrayList<>();
		result.absPayoutSubofHDManagements = new ArrayList<>();
		//INPUT????????????????????????????????????
		if(rec.isPresent()) {
			//<<Public>> ????????????????????????????????????????????????
			Optional<WorkType> workType = workTypeRepo.findByDeprecated(companyId, rec.get().getWorkInformation().getWorkTypeCode().v());
			if(workType.isPresent()) {
				result.recLeaveComDayOffMana = this.getLinkingManagement(employeeID, rec.get().getAppDate().getApplicationDate(), workType.get(), substituteManagement).stream().map(c-> LeaveComDayOffManaDto.fromDomain(c)).collect(Collectors.toList());
			}
		}
		
		if(abs.isPresent()) {
			//<<Public>> ????????????????????????????????????????????????
			Optional<WorkType> workType = workTypeRepo.findByDeprecated(companyId, abs.get().getWorkInformation().getWorkTypeCode().v());
			if(workType.isPresent()) {
				result.absLeaveComDayOffMana = this.getLinkingManagement(employeeID, abs.get().getAppDate().getApplicationDate(), workType.get(), substituteManagement).stream().map(c-> LeaveComDayOffManaDto.fromDomain(c)).collect(Collectors.toList());
				result.absPayoutSubofHDManagements = this.getLinkingManagementAbs(employeeID, abs.get().getAppDate().getApplicationDate(), workType.get(), holidayManage).stream().map(c-> PayoutSubofHDManagementDto.fromDomain(c)).collect(Collectors.toList());
			}
		}
		
		return result;
	}
	
	/**
	 * @name ??????????????????????????????????????????
	 * @param employeeID ??????ID
	 * @param applicationDate ?????????
	 * @param workType ????????????
	 * @param substituteManagement ???????????????????????????
	 * @return
	 */
	public List<LeaveComDayOffManagement> getLinkingManagement(String employeeID, GeneralDate applicationDate, WorkType workType, boolean substituteManagement) {
		//INPUT???????????????????????????????????????????????????
		if(substituteManagement && (
				(workType.getDailyWork().getWorkTypeUnit().isOneDay() && workType.getDailyWork().getOneDay() == WorkTypeClassification.SubstituteHoliday) || 
				(workType.getDailyWork().getWorkTypeUnit().isMonringAndAfternoon() && (workType.getDailyWork().getMorning() == WorkTypeClassification.SubstituteHoliday || workType.getDailyWork().getAfternoon() == WorkTypeClassification.SubstituteHoliday))
				)
		){
			return leaveComDayOffManaRepository.getByListDate(employeeID, Arrays.asList(applicationDate))
					.stream().filter(c-> { 
						return c.getAssocialInfo().getTargetSelectionAtr() == TargetSelectionAtr.REQUEST;
						}
					).collect(Collectors.toList());
		}
		return new ArrayList<>();
	}
	
	
	/**
	 * @name ??????????????????????????????????????????
	 * @param employeeID ??????ID
	 * @param applicationDate ?????????
	 * @param workType ????????????
	 * @param substituteManagement ???????????????????????????
	 * @param holidayManage ???????????????????????????
	 * @return
	 */
	public List<PayoutSubofHDManagement> getLinkingManagementAbs(String employeeID, GeneralDate applicationDate, WorkType workType, boolean holidayManage) {
		
		//INPUT??????????????????????????????????????????????????????????????????
		//INPUT???????????????????????????????????????????????????
		//?????????????????????????????????????????????????????????????????????
		// get form this.getLinkingManagementRec()
		
		//INPUT??????????????????????????????????????????????????????????????????
		if((workType.getDailyWork().getWorkTypeUnit().isOneDay() && workType.getDailyWork().getOneDay() == WorkTypeClassification.Pause) || 
				(workType.getDailyWork().getWorkTypeUnit().isMonringAndAfternoon() && (workType.getDailyWork().getMorning() == WorkTypeClassification.Pause || workType.getDailyWork().getAfternoon() == WorkTypeClassification.Pause))
				){
			return payoutSubofHDManaRepository.getByListDate(employeeID, Arrays.asList(applicationDate))
					.stream().filter(c-> { 
						return c.getAssocialInfo().getTargetSelectionAtr() == TargetSelectionAtr.REQUEST;
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
        
        @Override
        public List<LeaveComDayOffManagement> getDigestOccByListComId(String sid, DatePeriod period) {
            return leaveComDayOffManaRepo.getDigestOccByListComId(sid, period);
        }

        @Override
        public List<InterimDayOffMng> getTempDayOffBySidPeriod(String sid, DatePeriod period) {
            return interimBreakDayOffMngRepo.getDayOffBySidPeriod(sid, period);
        }

        @Override
        public List<CompensatoryDayOffManaData> getFixByDayOffDatePeriod(String sid) {
            return comDayOffManaDataRepo.getBySid(AppContexts.user().companyId(), sid);
        }

        @Override
        public List<InterimBreakMng> getTempBreakBySidPeriod(String sid, DatePeriod period) {
            return interimBreakDayOffMngRepo.getBySidPeriod(sid, period);
        }

        @Override
        public List<LeaveManagementData> getFixLeavByDayOffDatePeriod(String sid) {
            return leaveManaDataRepo.getBySid(AppContexts.user().companyId(), sid);
        }

        @Override
        public List<PayoutSubofHDManagement> getOccDigetByListSid(String sid, DatePeriod date) {
            return payoutSubofHDManaRepo.getOccDigetByListSid(sid, date);
        }

        @Override
        public List<SubstitutionOfHDManagementData> getByYmdUnOffset(String sid) {
            return substitutionOfHDManaDataRepo.getBysiD(AppContexts.user().companyId(), sid);
        }

        @Override
        public List<PayoutManagementData> getPayoutMana(String sid) {
            return payoutManagementDataRepo.getSid(AppContexts.user().companyId(), sid);
        }
    }
}
