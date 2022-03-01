package nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.require;

import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.WorkInformation;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeRecordImport;
import nts.uk.ctx.at.shared.dom.adapter.employee.SClsHistImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.EmploymentHistShareImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.SharedSidPeriodDateEmploymentImport;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyAdapter;
import nts.uk.ctx.at.shared.dom.adapter.holidaymanagement.CompanyDto;
import nts.uk.ctx.at.shared.dom.adapter.workplace.SharedAffWorkPlaceHisAdapter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbasMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecAbsMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.absencerecruitment.interim.InterimRecMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.algorithm.CheckCareResult;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.base.DigestionAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakDayOffMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.interimremain.primitive.DataManagementAtr;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutManagementDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.PayoutSubofHDManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SEmpHistoryImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SubstitutionOfHDManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SysEmploymentHisAdapter;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialholidaymng.interim.InterimSpecialHolidayMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.basicinfo.SpecialLeaveBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.specialleave.empinfo.grantremainingdata.SpecialLeaveGrantRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.ComDayOffManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.CompensatoryDayOffManaData;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveComDayOffManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.subhdmana.LeaveManagementData;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.RemainCreateInforByApplicationData;
import nts.uk.ctx.at.shared.dom.remainingnumber.work.service.RemainCreateInforByRecordData;
import nts.uk.ctx.at.shared.dom.schedule.WorkingDayCategory;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagement;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagementRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.UsageUnitSetting;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.UsageUnitSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeCom;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeComRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeEmpRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeSha;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeShaRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeWkp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.defor.DeforLaborTimeWkpRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeCom;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeComRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeEmp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeEmpRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeSha;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeShaRepo;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeWkp;
import nts.uk.ctx.at.shared.dom.scherec.statutory.worktime.week.regular.RegularLaborTimeWkpRepo;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayCode;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHolidayRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYear;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.ElapseYearRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTbl;
import nts.uk.ctx.at.shared.dom.specialholiday.grantinformation.GrantDateTblRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveEmSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.CheckCareService;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmptYearlyRetentionSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingCondition;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemService;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmpComHisAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmpEnrollPeriodImport;
import nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation.TimeSpecialLeaveManagementSetting;
import nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation.TimeSpecialLeaveMngSetRepository;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.difftimeset.DiffTimeWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.fixedset.FixedWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flexset.FlexWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSetting;
import nts.uk.ctx.at.shared.dom.worktime.flowset.FlowWorkSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.predset.PredetemineTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTbl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantHdTblSet;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.GrantYearHolidayRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceRepository;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.LengthServiceTbl;
import nts.uk.ctx.at.shared.dom.yearholidaygrant.YearHolidayRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.license.option.OptionLicense;

@AllArgsConstructor
public class RequireImp implements RemainNumberTempRequireService.Require {

	protected ComSubstVacationRepository comSubstVacationRepo;

	protected CompensLeaveComSetRepository compensLeaveComSetRepo;

	protected SpecialLeaveGrantRepository specialLeaveGrantRepo;

	protected EmpEmployeeAdapter empEmployeeAdapter;

	protected GrantDateTblRepository grantDateTblRepo;

	protected AnnLeaEmpBasicInfoRepository annLeaEmpBasicInfoRepo;

	protected SpecialHolidayRepository specialHolidayRepo;

	protected InterimSpecialHolidayMngRepository interimSpecialHolidayMngRepo;

	protected SpecialLeaveBasicInfoRepository specialLeaveBasicInfoRepo;

	protected InterimRecAbasMngRepository interimRecAbasMngRepo;

	protected EmpSubstVacationRepository empSubstVacationRepo;

	protected SubstitutionOfHDManaDataRepository substitutionOfHDManaDataRepo;

	protected PayoutManagementDataRepository payoutManagementDataRepo;

	protected InterimBreakDayOffMngRepository interimBreakDayOffMngRepo;

	protected ComDayOffManaDataRepository comDayOffManaDataRepo;

	protected CompanyAdapter companyAdapter;

	protected ShareEmploymentAdapter shareEmploymentAdapter;

	protected LeaveManaDataRepository leaveManaDataRepo;

	protected WorkingConditionItemRepository workingConditionItemRepo;

	protected WorkingConditionRepository workingConditionRepo;

	protected WorkTimeSettingRepository workTimeSettingRepo;

	protected FixedWorkSettingRepository fixedWorkSettingRepo;

	protected FlowWorkSettingRepository flowWorkSettingRepo;

	protected DiffTimeWorkSettingRepository diffTimeWorkSettingRepo;

	protected FlexWorkSettingRepository flexWorkSettingRepo;

	protected PredetemineTimeSettingRepository predetemineTimeSettingRepo;

	protected ClosureRepository closureRepo;

	protected ClosureEmploymentRepository closureEmploymentRepo;

	protected WorkTypeRepository workTypeRepo;

	protected RemainCreateInforByApplicationData remainCreateInforByApplicationData;

	protected CompensLeaveEmSetRepository compensLeaveEmSetRepo;

	protected EmploymentSettingRepository employmentSettingRepo;

	protected RetentionYearlySettingRepository retentionYearlySettingRepo;

	protected AnnualPaidLeaveSettingRepository annualPaidLeaveSettingRepo;

	protected OutsideOTSettingRepository outsideOTSettingRepo;

	protected WorkdayoffFrameRepository workdayoffFrameRepo;

	protected YearHolidayRepository yearHolidayRepo;

	protected UsageUnitSettingRepository usageUnitSettingRepo;

	protected RegularLaborTimeComRepo regularLaborTimeComRepo;

	protected DeforLaborTimeComRepo deforLaborTimeComRepo;

	protected RegularLaborTimeWkpRepo regularLaborTimeWkpRepo;

	protected DeforLaborTimeWkpRepo deforLaborTimeWkpRepo;

	protected RegularLaborTimeEmpRepo regularLaborTimeEmpRepo;

	protected DeforLaborTimeEmpRepo deforLaborTimeEmpRepo;

	protected RegularLaborTimeShaRepo regularLaborTimeShaRepo;

	protected DeforLaborTimeShaRepo deforLaborTimeShaRepo;

	protected SharedAffWorkPlaceHisAdapter sharedAffWorkPlaceHisAdapter;

	protected LengthServiceRepository lengthServiceRepo;

	protected GrantYearHolidayRepository grantYearHolidayRepo;

	protected PayoutSubofHDManaRepository payoutSubofHDManaRepo;

	protected LeaveComDayOffManaRepository leaveComDayOffManaRepo;

	protected RemainCreateInforByRecordData remainCreateInforByRecordData;
	
	protected ElapseYearRepository elapseYearRepository;
	
	private EmpComHisAdapter empComHisAdapter;
	
	private ClosureStatusManagementRepository closureStatusManagementRepo;
	
	private TimeSpecialLeaveMngSetRepository timeSpecialLeaveMngSetRepository;

	private Optional<OutsideOTSetting> outsideOTSettingCache = Optional.empty();

	private Map<String, Optional<FlowWorkSetting>>  flowWorkSetMap = new ConcurrentHashMap<String, Optional<FlowWorkSetting>>();

	private Map<String, Optional<FlexWorkSetting>>  flexWorkSetMap = new ConcurrentHashMap<String, Optional<FlexWorkSetting>>();

	private Map<String, Optional<FixedWorkSetting>>  fixedWorkSetMap = new ConcurrentHashMap<String, Optional<FixedWorkSetting>>();

	private Map<String, Optional<WorkTimeSetting>>  workTimeSetMap = new ConcurrentHashMap<String, Optional<WorkTimeSetting>>();

	private Map<String, Optional<WorkType>>  workTypeMap = new ConcurrentHashMap<String, Optional<WorkType>>();

	private Map<Integer, Optional<Closure>> closureMap = new ConcurrentHashMap<Integer, Optional<Closure>>();

	private CheckCareService checkCareService;

	private WorkingConditionItemService workingConditionItemService;
	
	private SysEmploymentHisAdapter sysEmploymentHisAdapter;

	public RequireImp(ComSubstVacationRepository comSubstVacationRepo,
			CompensLeaveComSetRepository compensLeaveComSetRepo, SpecialLeaveGrantRepository specialLeaveGrantRepo,
			EmpEmployeeAdapter empEmployeeAdapter, GrantDateTblRepository grantDateTblRepo,
			AnnLeaEmpBasicInfoRepository annLeaEmpBasicInfoRepo, SpecialHolidayRepository specialHolidayRepo,
			InterimSpecialHolidayMngRepository interimSpecialHolidayMngRepo,
			SpecialLeaveBasicInfoRepository specialLeaveBasicInfoRepo,
			InterimRecAbasMngRepository interimRecAbasMngRepo, EmpSubstVacationRepository empSubstVacationRepo,
			SubstitutionOfHDManaDataRepository substitutionOfHDManaDataRepo,
			PayoutManagementDataRepository payoutManagementDataRepo,
			InterimBreakDayOffMngRepository interimBreakDayOffMngRepo,
			ComDayOffManaDataRepository comDayOffManaDataRepo, CompanyAdapter companyAdapter,
			ShareEmploymentAdapter shareEmploymentAdapter, LeaveManaDataRepository leaveManaDataRepo,
			WorkingConditionItemRepository workingConditionItemRepo,
			WorkingConditionRepository workingConditionRepo, WorkTimeSettingRepository workTimeSettingRepo,
			FixedWorkSettingRepository fixedWorkSettingRepo, FlowWorkSettingRepository flowWorkSettingRepo,
			DiffTimeWorkSettingRepository diffTimeWorkSettingRepo, FlexWorkSettingRepository flexWorkSettingRepo,
			PredetemineTimeSettingRepository predetemineTimeSettingRepo, ClosureRepository closureRepo,
			ClosureEmploymentRepository closureEmploymentRepo, WorkTypeRepository workTypeRepo,
			RemainCreateInforByApplicationData remainCreateInforByApplicationData,
			CompensLeaveEmSetRepository compensLeaveEmSetRepo, EmploymentSettingRepository employmentSettingRepo,
			RetentionYearlySettingRepository retentionYearlySettingRepo,
			AnnualPaidLeaveSettingRepository annualPaidLeaveSettingRepo,
			OutsideOTSettingRepository outsideOTSettingRepo, WorkdayoffFrameRepository workdayoffFrameRepo,
			YearHolidayRepository yearHolidayRepo, UsageUnitSettingRepository usageUnitSettingRepo,
			RegularLaborTimeComRepo regularLaborTimeComRepo, DeforLaborTimeComRepo deforLaborTimeComRepo,
			RegularLaborTimeWkpRepo regularLaborTimeWkpRepo, DeforLaborTimeWkpRepo deforLaborTimeWkpRepo,
			RegularLaborTimeEmpRepo regularLaborTimeEmpRepo, DeforLaborTimeEmpRepo deforLaborTimeEmpRepo,
			RegularLaborTimeShaRepo regularLaborTimeShaRepo, DeforLaborTimeShaRepo deforLaborTimeShaRepo,
			SharedAffWorkPlaceHisAdapter sharedAffWorkPlaceHisAdapter, LengthServiceRepository lengthServiceRepo,
			GrantYearHolidayRepository grantYearHolidayRepo, PayoutSubofHDManaRepository payoutSubofHDManaRepo,
			LeaveComDayOffManaRepository leaveComDayOffManaRepo, CheckCareService checkChildCareService,
			WorkingConditionItemService workingConditionItemService, RemainCreateInforByRecordData remainCreateInforByRecordData,
			SysEmploymentHisAdapter sysEmploymentHisAdapter,
			ElapseYearRepository elapseYearRepository, EmpComHisAdapter empComHisAdapter, ClosureStatusManagementRepository closureStatusManagementRepo,
			TimeSpecialLeaveMngSetRepository timeSpecialLeaveMngSetRepository) {
		this.comSubstVacationRepo = comSubstVacationRepo;
		this.compensLeaveComSetRepo = compensLeaveComSetRepo;
		this.specialLeaveGrantRepo = specialLeaveGrantRepo;
		this.empEmployeeAdapter = empEmployeeAdapter;
		this.grantDateTblRepo = grantDateTblRepo;
		this.annLeaEmpBasicInfoRepo = annLeaEmpBasicInfoRepo;
		this.specialHolidayRepo = specialHolidayRepo;
		this.interimSpecialHolidayMngRepo = interimSpecialHolidayMngRepo;
		this.specialLeaveBasicInfoRepo = specialLeaveBasicInfoRepo;
		this.interimRecAbasMngRepo = interimRecAbasMngRepo;
		this.empSubstVacationRepo = empSubstVacationRepo;
		this.substitutionOfHDManaDataRepo = substitutionOfHDManaDataRepo;
		this.payoutManagementDataRepo = payoutManagementDataRepo;
		this.interimBreakDayOffMngRepo = interimBreakDayOffMngRepo;
		this.comDayOffManaDataRepo = comDayOffManaDataRepo;
		this.companyAdapter = companyAdapter;
		this.shareEmploymentAdapter = shareEmploymentAdapter;
		this.leaveManaDataRepo = leaveManaDataRepo;
		this.workingConditionItemRepo = workingConditionItemRepo;
		this.workingConditionRepo = workingConditionRepo;
		this.workTimeSettingRepo = workTimeSettingRepo;
		this.fixedWorkSettingRepo = fixedWorkSettingRepo;
		this.flowWorkSettingRepo = flowWorkSettingRepo;
		this.diffTimeWorkSettingRepo = diffTimeWorkSettingRepo;
		this.flexWorkSettingRepo = flexWorkSettingRepo;
		this.predetemineTimeSettingRepo = predetemineTimeSettingRepo;
		this.closureRepo = closureRepo;
		this.closureEmploymentRepo = closureEmploymentRepo;
		this.workTypeRepo = workTypeRepo;
		this.remainCreateInforByApplicationData = remainCreateInforByApplicationData;
		this.compensLeaveEmSetRepo = compensLeaveEmSetRepo;
		this.employmentSettingRepo = employmentSettingRepo;
		this.retentionYearlySettingRepo = retentionYearlySettingRepo;
		this.annualPaidLeaveSettingRepo = annualPaidLeaveSettingRepo;
		this.outsideOTSettingRepo = outsideOTSettingRepo;
		this.workdayoffFrameRepo = workdayoffFrameRepo;
		this.yearHolidayRepo = yearHolidayRepo;
		this.usageUnitSettingRepo = usageUnitSettingRepo;
		this.regularLaborTimeComRepo = regularLaborTimeComRepo;
		this.deforLaborTimeComRepo = deforLaborTimeComRepo;
		this.regularLaborTimeWkpRepo = regularLaborTimeWkpRepo;
		this.deforLaborTimeWkpRepo = deforLaborTimeWkpRepo;
		this.regularLaborTimeEmpRepo = regularLaborTimeEmpRepo;
		this.deforLaborTimeEmpRepo = deforLaborTimeEmpRepo;
		this.regularLaborTimeShaRepo = regularLaborTimeShaRepo;
		this.deforLaborTimeShaRepo = deforLaborTimeShaRepo;
		this.sharedAffWorkPlaceHisAdapter = sharedAffWorkPlaceHisAdapter;
		this.lengthServiceRepo = lengthServiceRepo;
		this.grantYearHolidayRepo = grantYearHolidayRepo;
		this.leaveComDayOffManaRepo = leaveComDayOffManaRepo;
		this.payoutSubofHDManaRepo = payoutSubofHDManaRepo;
		this.checkCareService = checkChildCareService;
		this.workingConditionItemService = workingConditionItemService;
		this.remainCreateInforByRecordData = remainCreateInforByRecordData;
		this.sysEmploymentHisAdapter = sysEmploymentHisAdapter;
		this.elapseYearRepository = elapseYearRepository;
		this.empComHisAdapter = empComHisAdapter;
		this.closureStatusManagementRepo = closureStatusManagementRepo;
		this.timeSpecialLeaveMngSetRepository = timeSpecialLeaveMngSetRepository;
	}
	
	RequireImpCache cache = new RequireImpCache();

	@Override
	public Optional<ComSubstVacation> comSubstVacation(String companyId) {
		if(!cache.getComSubstVacationCache().isPresent()){
			cache.setComSubstVacationCache(comSubstVacationRepo.findById(companyId));
		}
		return cache.getComSubstVacationCache();
	}

	@Override
	public List<InterimAbsMng> interimAbsMng(String absId , DatePeriod period) {
		return interimRecAbasMngRepo.getAbsBySidDatePeriod(absId, period);
	}

	@Override
	public List<InterimRecAbsMng> interimRecAbsMng(String interimId, boolean isRec, DataManagementAtr mngAtr) {
        return interimRecAbasMngRepo.getRecOrAbsMng(interimId, isRec, mngAtr);
	}

	@Override
	public List<InterimRecMng> interimRecMng(String recId, DatePeriod period) {
		return interimRecAbasMngRepo.getRecBySidDatePeriod(recId, period);
	}

	@Override
	public List<SubstitutionOfHDManagementData> substitutionOfHDManagementData(String cid, String sid,
			GeneralDate ymd, double unOffseDays) {
		return substitutionOfHDManaDataRepo.getByYmdUnOffset(cid, sid, ymd, unOffseDays);
	}

	@Override
	public List<PayoutManagementData> payoutManagementData(String cid, String sid, GeneralDate ymd,
			double unUse, DigestionAtr state) {
		return payoutManagementDataRepo.getByUnUseState(cid, sid, ymd, unUse, state);
	}

	@Override
	public List<InterimDayOffMng> interimDayOffMng(String dayOffManaId, DatePeriod period) {
		return interimBreakDayOffMngRepo.getDayOffBySidPeriod(dayOffManaId,period);
	}

	@Override
	public List<InterimBreakMng> interimBreakMng(String breakManaId, DatePeriod period) {
		return interimBreakDayOffMngRepo.getBySidPeriod(breakManaId,period);
	}

	@Override
	public List<CompensatoryDayOffManaData> compensatoryDayOffManaData(String cid, String sid,
			GeneralDate ymd) {
		return comDayOffManaDataRepo.getBySidDate(cid, sid, ymd);
	}

	@Override
	public List<InterimBreakDayOffMng> interimBreakDayOffMng(String mngId, boolean breakDay,
			DataManagementAtr mngAtr) {
        return interimBreakDayOffMngRepo.getBreakDayOffMng(mngId, breakDay, mngAtr);
	}

	@Override
	public CompanyDto firstMonth(CacheCarrier cacheCarrier, String companyId) {
		return companyAdapter.getFirstMonthRequire(cacheCarrier, companyId);
	}

	@Override
	public Optional<BsEmploymentHistoryImport> employmentHistory(CacheCarrier cacheCarrier, String companyId,
			String employeeId, GeneralDate baseDate) {
		return shareEmploymentAdapter.findEmploymentHistoryRequire(cacheCarrier, companyId, employeeId, baseDate);
	}

	@Override
	public List<SharedSidPeriodDateEmploymentImport> employmentHistory(CacheCarrier cacheCarrier,
			List<String> sids, DatePeriod datePeriod) {
		return shareEmploymentAdapter.getEmpHistBySidAndPeriodRequire(cacheCarrier, sids, datePeriod);
	}

	@Override
	public List<LeaveManagementData> leaveManagementData(String cid, String sid, GeneralDate ymd,
			DigestionAtr state) {
		return leaveManaDataRepo.getBySidYmd(cid, sid, ymd, state);
	}

	@Override
	public Optional<WorkingConditionItem> workingConditionItem(String historyId) {
		if(!cache.getWorkingConditionItemMap().containsKey(historyId)){
			cache.getWorkingConditionItemMap().put(historyId, workingConditionItemRepo.getByHistoryId(historyId));
		}
		return cache.getWorkingConditionItemMap().get(historyId);
	}

	@Override
	public Optional<WorkingCondition> workingCondition(String companyId, String employeeId,
			GeneralDate baseDate) {
		return workingConditionRepo.getBySidAndStandardDate(companyId, employeeId, baseDate);
	}


	@Override
	public Optional<FlowWorkSetting> flowWorkSetting(String companyId, String workTimeCode) {
		if(!cache.getFlowWorkSetMap().containsKey(workTimeCode)) {
			cache.getFlowWorkSetMap().put(workTimeCode, flowWorkSettingRepo.find(companyId, workTimeCode));
		}
		return cache.getFlowWorkSetMap().get(workTimeCode);
	}

	@Override
	public Optional<FlexWorkSetting> flexWorkSetting(String companyId, String workTimeCode) {
		if(!cache.getFlexWorkSetMap().containsKey(workTimeCode)) {
			cache.getFlexWorkSetMap().put(workTimeCode, flexWorkSettingRepo.find(companyId, workTimeCode));
		}
		return cache.getFlexWorkSetMap().get(workTimeCode);
	}

	@Override
	public Optional<FixedWorkSetting> fixedWorkSetting(String companyId, String workTimeCode) {
		if(!cache.getFixedWorkSetMap().containsKey(workTimeCode)) {
			cache.getFixedWorkSetMap().put(workTimeCode, fixedWorkSettingRepo.findByKey(companyId, workTimeCode));
		}
		return cache.getFixedWorkSetMap().get(workTimeCode);
	}

	@Override
	public Optional<DiffTimeWorkSetting> diffTimeWorkSetting(String companyId, String workTimeCode) {
		if(!cache.getDiffTimeWorkSettingMap().containsKey(workTimeCode)){
			cache.getDiffTimeWorkSettingMap().put(workTimeCode, diffTimeWorkSettingRepo.find(companyId, workTimeCode));
		}
		return cache.getDiffTimeWorkSettingMap().get(workTimeCode);
	}

	@Override
	public Optional<WorkTimeSetting> workTimeSetting(String companyId, String workTimeCode) {
		if(!cache.getWorkTimeSetMap().containsKey(workTimeCode)) {
			cache.getWorkTimeSetMap().put(workTimeCode, workTimeSettingRepo.findByCode(companyId, workTimeCode));
		}
		return cache.getWorkTimeSetMap().get(workTimeCode);
	}

	@Override
	public CompensatoryLeaveComSetting compensatoryLeaveComSetting(String companyId) {
		if(cache.getCompensatoryLeaveComSettingCache() == null){
			cache.setCompensatoryLeaveComSettingCache(compensLeaveComSetRepo.find(companyId));
		}
		return cache.getCompensatoryLeaveComSettingCache();
	}

	@Override
	public Optional<PredetemineTimeSetting> predetemineTimeSetting(String companyId, String workTimeCode) {
		if(!cache.getPredetemineTimeSetting().containsKey(workTimeCode)) {
			cache.getPredetemineTimeSetting().put(workTimeCode,
					predetemineTimeSettingRepo.findByWorkTimeCode(companyId, workTimeCode));
		}
		return cache.getPredetemineTimeSetting().get(workTimeCode);
	}

	@Override
	public List<Integer> getSpecialHolidayNumber(String cid, int sphdSpecLeaveNo) {
		if(!cache.getSpecialHolidayCodeBySpecLeaveNoMap().containsKey(sphdSpecLeaveNo)){
			cache.getSpecialHolidayCodeBySpecLeaveNoMap().put(sphdSpecLeaveNo, specialHolidayRepo.findBySphdSpecLeave(cid, sphdSpecLeaveNo));
		}
		return cache.getSpecialHolidayCodeBySpecLeaveNoMap().get(sphdSpecLeaveNo);
	}
	@Override
	public List<Integer> getAbsenceNumber(String cid, int absenseNo) {
		if(!cache.getSpecialHolidayCodeByAbsenseNoMap().containsKey(absenseNo)){
			cache.getSpecialHolidayCodeByAbsenseNoMap().put(absenseNo, specialHolidayRepo.findByAbsframeNo(cid, absenseNo));
		}
		return cache.getSpecialHolidayCodeByAbsenseNoMap().get(absenseNo);
	}

	@Override
	public Optional<Closure> closure(String companyId, int closureId) {
		if(!cache.getClosureMap().containsKey(closureId)) {
			cache.getClosureMap().put(closureId, closureRepo.findById(companyId, closureId));
		}
		return cache.getClosureMap().get(closureId);
	}

	@Override
	public Optional<ClosureEmployment> employmentClosure(String companyID, String employmentCD) {
		if(!cache.getClosureEmploymentMap().containsKey(employmentCD)){
			cache.getClosureEmploymentMap().put(employmentCD, closureEmploymentRepo.findByEmploymentCD(companyID, employmentCD));
		}
		return cache.getClosureEmploymentMap().get(employmentCD);
	}

	@Override
	public Optional<WorkType> workType(String companyId, String workTypeCd) {
		if(!cache.getWorkTypeMap().containsKey(workTypeCd)) {
			cache.getWorkTypeMap().put(workTypeCd, workTypeRepo.findByPK(companyId, workTypeCd));
		}
		return cache.getWorkTypeMap().get(workTypeCd);
	}

//	@Override
//	public Integer excludeHolidayAtr(CacheCarrier cacheCarrier, String cid, String appID) {
//		return remainCreateInforByApplicationData.excludeHolidayAtr(cacheCarrier, cid, appID);
//	}
	@Override
	public Optional<EmpSubstVacation> empSubstVacation(String companyId, String contractTypeCode) {
		if(!cache.getEmpSubstVacationMap().containsKey(contractTypeCode)){
			cache.getEmpSubstVacationMap().put(contractTypeCode, empSubstVacationRepo.findById(companyId, contractTypeCode));
		}
		return cache.getEmpSubstVacationMap().get(contractTypeCode);
	}

	@Override
	public CompensatoryLeaveEmSetting compensatoryLeaveEmSetting(String companyId,
			String employmentCode) {
		if(!cache.getCompensatoryLeaveEmSettingMap().containsKey(employmentCode)){
			cache.getCompensatoryLeaveEmSettingMap().put(employmentCode, Optional.ofNullable(compensLeaveEmSetRepo.find(companyId, employmentCode)));
		}
		return cache.getCompensatoryLeaveEmSettingMap().get(employmentCode).orElse(null);
	}

	@Override
	public List<Closure> closure(String companyId) {
		if(cache.getClosureCache().isEmpty()){
			cache.setClosureCache(closureRepo.findAll(companyId));
		}
		return cache.getClosureCache();
	}
	
	@Override
	public List<Closure> closureActive(String companyId, UseClassification useAtr) {
		if(!cache.getClosurebyUseClassificationMap().containsKey(useAtr)){
			cache.getClosurebyUseClassificationMap().put(useAtr, closureRepo.findAllActive(companyId, useAtr));
		}
		return cache.getClosurebyUseClassificationMap().get(useAtr);
	}

	@Override
	public EmployeeImport employee(CacheCarrier cacheCarrier, String empId) {
		return empEmployeeAdapter.findByEmpIdRequire(cacheCarrier, empId);
	}

	@Override
	public Optional<OutsideOTSetting> outsideOTSetting(String companyId) {
		if(!cache.getOutsideOTSettingCache().isPresent()) {
			cache.setOutsideOTSettingCache(outsideOTSettingRepo.findById(companyId));
		}
		return cache.getOutsideOTSettingCache();
	}

	@Override
	public List<WorkdayoffFrame> workdayoffFrames(String companyId) {
		if(cache.getWorkdayoffFrameCache().isEmpty()){
			cache.setWorkdayoffFrameCache(workdayoffFrameRepo.getAllWorkdayoffFrame(companyId));
		}
		return cache.getWorkdayoffFrameCache();
	}

	@Override
	public AnnualPaidLeaveSetting annualPaidLeaveSetting(String companyId) {
		if(cache.getAnnualPaidLeaveSettingCache() == null){
			cache.setAnnualPaidLeaveSettingCache(annualPaidLeaveSettingRepo.findByCompanyId(companyId));
		}
		return cache.getAnnualPaidLeaveSettingCache();
	}

	@Override
	public Optional<EmptYearlyRetentionSetting> employmentYearlyRetentionSetting(String companyId,
			String employmentCode) {
		if(!cache.getEmptYearlyRetentionSettingMap().containsKey(employmentCode)){
			cache.getEmptYearlyRetentionSettingMap().put(employmentCode, employmentSettingRepo.find(companyId, employmentCode));
		}
		return cache.getEmptYearlyRetentionSettingMap().get(employmentCode);
	}

	@Override
	public Optional<RetentionYearlySetting> retentionYearlySetting(String companyId) {
		if(!cache.getRetentionYearlySettingCache().isPresent()){
			cache.setRetentionYearlySettingCache(retentionYearlySettingRepo.findByCompanyId(companyId));
		}
		return cache.getRetentionYearlySettingCache();
	}

	@Override
	public Optional<UsageUnitSetting> usageUnitSetting(String companyId) {
		if(!cache.getUsageUnitSettingCache().isPresent()){
			cache.setUsageUnitSettingCache(usageUnitSettingRepo.findByCompany(companyId));
		}
		return cache.getUsageUnitSettingCache();
	}

	@Override
	public Optional<RegularLaborTimeCom> regularLaborTimeByCompany(String companyId) {
		if(!cache.getRegularLaborTimeComCache().isPresent()){
			cache.setRegularLaborTimeComCache(regularLaborTimeComRepo.find(companyId));
		}
		return cache.getRegularLaborTimeComCache();
	}

	@Override
	public Optional<DeforLaborTimeCom> deforLaborTimeByCompany(String companyId) {
		if(!cache.getDeforLaborTimeComCache().isPresent()){
			cache.setDeforLaborTimeComCache(deforLaborTimeComRepo.find(companyId));
		}
		return cache.getDeforLaborTimeComCache();
	}

	@Override
	public Optional<RegularLaborTimeWkp> regularLaborTimeByWorkplace(String cid, String wkpId) {
		if(!cache.getRegularLaborTimeWkpMap().containsKey(wkpId)){
			cache.getRegularLaborTimeWkpMap().put(wkpId, regularLaborTimeWkpRepo.find(cid, wkpId));
		}
		return cache.getRegularLaborTimeWkpMap().get(wkpId);
	}

	@Override
	public Optional<DeforLaborTimeWkp> deforLaborTimeByWorkplace(String cid, String wkpId) {
		if(!cache.getDeforLaborTimeWkpMap().containsKey(wkpId)){
			cache.getDeforLaborTimeWkpMap().put(wkpId, deforLaborTimeWkpRepo.find(cid, wkpId));
		}
		return cache.getDeforLaborTimeWkpMap().get(wkpId);
	}

	@Override
	public Optional<RegularLaborTimeEmp> regularLaborTimeByEmployment(String cid, String employmentCode) {
		if(!cache.getRegularLaborTimeEmpMap().containsKey(employmentCode)){
			cache.getRegularLaborTimeEmpMap().put(employmentCode, regularLaborTimeEmpRepo.findById(cid, employmentCode));
		}
		return cache.getRegularLaborTimeEmpMap().get(employmentCode);
	}

	@Override
	public Optional<DeforLaborTimeEmp> deforLaborTimeByEmployment(String cid, String employmentCode) {
		if(!cache.getDeforLaborTimeEmpMap().containsKey(employmentCode)){
			cache.getDeforLaborTimeEmpMap().put(employmentCode, deforLaborTimeEmpRepo.find(cid, employmentCode));
		}
		return cache.getDeforLaborTimeEmpMap().get(employmentCode);
	}

	@Override
	public Optional<RegularLaborTimeSha> regularLaborTimeByEmployee(String Cid, String EmpId) {
		if(!cache.getRegularLaborTimeShaMap().containsKey(EmpId)){
			cache.getRegularLaborTimeShaMap().put(EmpId, regularLaborTimeShaRepo.find(Cid, EmpId));
		}
		return cache.getRegularLaborTimeShaMap().get(EmpId);
	}

	@Override
	public Optional<DeforLaborTimeSha> deforLaborTimeByEmployee(String cid, String empId) {
		if(!cache.getDeforLaborTimeShaMap().containsKey(empId)){
			cache.getDeforLaborTimeShaMap().put(empId, deforLaborTimeShaRepo.find(cid, empId));
		}
		return cache.getDeforLaborTimeShaMap().get(empId);
	}

	@Override
	public List<String> getCanUseWorkplaceForEmp(CacheCarrier cacheCarrier, String companyId, String employeeId,
			GeneralDate baseDate) {
		return sharedAffWorkPlaceHisAdapter.findAffiliatedWorkPlaceIdsToRootRequire(cacheCarrier, companyId, employeeId, baseDate);
	}

	@Override
	public CheckCareResult checkCare(WorkTypeSet wkSet, String cid) {
		if(!cache.getCheckCareResultMap().containsKey(wkSet)){
			cache.getCheckCareResultMap().put(wkSet, Optional.ofNullable(this.checkCareService.checkCare(wkSet, cid)));
		}
		return cache.getCheckCareResultMap().get(wkSet).orElse(null);
	}
	@Override
	public Optional<AnnualLeaveEmpBasicInfo> employeeAnnualLeaveBasicInfo(String employeeId) {
		if(!cache.getAnnualLeaveEmpBasicInfoMap().containsKey(employeeId)){
			cache.getAnnualLeaveEmpBasicInfoMap().put(employeeId, this.annLeaEmpBasicInfoRepo.get(employeeId));
		}
		return cache.getAnnualLeaveEmpBasicInfoMap().get(employeeId);
	}

	@Override
	public Optional<GrantHdTblSet> grantHdTblSet(String companyId, String yearHolidayCode) {
		if(!cache.getGrantHdTblSetMap().containsKey(yearHolidayCode)){
			cache.getGrantHdTblSetMap().put(yearHolidayCode, yearHolidayRepo.findByCode(companyId, yearHolidayCode));
		}
		return cache.getGrantHdTblSetMap().get(yearHolidayCode);
	}

	@Override
	public List<LengthServiceTbl> lengthServiceTbl(String companyId, String yearHolidayCode) {
		if(!cache.getLengthServiceTblMap().containsKey(yearHolidayCode)){
			cache.getLengthServiceTblMap().put(yearHolidayCode,lengthServiceRepo.findByCode(companyId, yearHolidayCode));
		}
		return cache.getLengthServiceTblMap().get(yearHolidayCode);
	}

	@Override
	public Optional<GrantHdTbl> grantHdTbl(String companyId, int conditionNo, String yearHolidayCode, int grantNum) {
		String key = companyId + "-" + conditionNo + "-" + yearHolidayCode + "-" + grantNum;
		if(!cache.getGrantHdTblMap().containsKey(key)){
			cache.getGrantHdTblMap().put(key, grantYearHolidayRepo.find(companyId, conditionNo, yearHolidayCode, grantNum));
		}
		return cache.getGrantHdTblMap().get(key);
	}
	
	@Override
	public List<PayoutSubofHDManagement> getOccDigetByListSid(String sid, DatePeriod date) {
		return payoutSubofHDManaRepo.getOccDigetByListSid(sid, date);
	}

	@Override
	public List<SubstitutionOfHDManagementData> getByYmdUnOffset(String sid) {
		if(!cache.getSubstitutionOfHDManagementDataMap().containsKey(sid)){
			cache.getSubstitutionOfHDManagementDataMap().put(sid, substitutionOfHDManaDataRepo.getBysiD(AppContexts.user().companyId(), sid));
		}
		return cache.getSubstitutionOfHDManagementDataMap().get(sid);
	}

	@Override
	public List<PayoutManagementData> getPayoutMana(String sid) {
		if(!cache.getPayoutManagementDataMap().containsKey(sid)){
			cache.getPayoutManagementDataMap().put(sid, payoutManagementDataRepo.getSid(AppContexts.user().companyId(), sid));
		}
		return cache.getPayoutManagementDataMap().get(sid);
	}

	@Override
	public List<EmploymentHistShareImport> findByEmployeeIdOrderByStartDate(String employeeId) {
		if(!cache.getEmploymentHistShareImportMap().containsKey(employeeId)){
			cache.getEmploymentHistShareImportMap().put(employeeId, shareEmploymentAdapter.findByEmployeeIdOrderByStartDate(employeeId));
		}
		return cache.getEmploymentHistShareImportMap().get(employeeId);
	}

	@Override
	public Optional<EmpSubstVacation> findEmpById(String companyId, String contractTypeCode) {
		return this.empSubstVacation(companyId, contractTypeCode);
	}

	@Override
	public Optional<ComSubstVacation> findComById(String companyId) {
		return this.comSubstVacation(companyId);
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
	public Optional<BsEmploymentHistoryImport> findEmploymentHistory(String companyId, String employeeId, GeneralDate baseDate) {
		return shareEmploymentAdapter.findEmploymentHistory(companyId, employeeId, baseDate);
	}

	@Override
	public CompensatoryLeaveEmSetting findComLeavEmpSet(String companyId, String employmentCode) {
		return this.compensatoryLeaveEmSetting(companyId, employmentCode);
	}

	@Override
	public CompensatoryLeaveComSetting findComLeavComSet(String companyId) {
		return this.compensatoryLeaveComSetting(companyId);
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
		if(!cache.getCompensatoryDayOffManaDataMap().containsKey(sid)){
			cache.getCompensatoryDayOffManaDataMap().put(sid, comDayOffManaDataRepo.getBySid(AppContexts.user().companyId(), sid));
		}
		return cache.getCompensatoryDayOffManaDataMap().get(sid);
	}

	@Override
	public List<InterimBreakMng> getTempBreakBySidPeriod(String sid, DatePeriod period) {
		return interimBreakDayOffMngRepo.getBySidPeriod(sid, period);
	}

	@Override
	public List<LeaveManagementData> getFixLeavByDayOffDatePeriod(String sid) {
		if(!cache.getLeaveManagementDataMap().containsKey(sid)){
			cache.getLeaveManagementDataMap().put(sid, leaveManaDataRepo.getBySid(AppContexts.user().companyId(), sid));
		}
		return cache.getLeaveManagementDataMap().get(sid);
	}

	@Override
	public CompanyDto getFirstMonth(String companyId) {
		if(cache.getCompanyDtoCache() == null){
			cache.setCompanyDtoCache(companyAdapter.getFirstMonth(companyId));
		}
		return cache.getCompanyDtoCache();
	}

	@Override
	public Optional<WorkInformation> getHolidayWorkSchedule(String companyId, String employeeId, GeneralDate baseDate,
			String workTypeCode) {
		return this.workingConditionItemService.getHolidayWorkSchedule(companyId, employeeId, baseDate, workTypeCode);
	}

	@Override
	public Optional<WorkingConditionItem> workingConditionItem(String employeeId, GeneralDate baseDate) {
		return workingConditionItemRepo.getBySidAndStandardDate(employeeId, baseDate);
	}

	@Override
	public List<ClosureEmployment> employmentClosureClones(String companyID, List<String> employmentCD) {
		return closureEmploymentRepo.findListEmployment(companyID, employmentCD);
	}

	@Override
	public List<Closure> closureClones(String companyId, List<Integer> closureId) {
		return closureRepo.findByListId(companyId, closureId);
	}

	@Override
	public Map<String, BsEmploymentHistoryImport> employmentHistoryClones(String companyId, List<String> employeeId,
			GeneralDate baseDate) {
		return shareEmploymentAdapter.findEmpHistoryVer2(companyId, employeeId, baseDate);
	}
	
    @Override
    public Optional<WorkInformation> getHolidayWorkScheduleNew(String companyId, String employeeId,
            GeneralDate baseDate, String workTypeCode, WorkingDayCategory workingDayCategory) {
        return this.workingConditionItemService.getHolidayWorkScheduleNew(companyId, employeeId, baseDate, workTypeCode,
                workingDayCategory);
    }

	@Override
	public Optional<WorkTimeSetting> getWorkTime(String cid, String workTimeCode) {
		return this.workTimeSetting(cid, workTimeCode);
	}
	@Override
	public CompensatoryLeaveComSetting findCompensatoryLeaveComSet(String companyId) {
		return this.compensatoryLeaveComSetting(companyId);
	}

	@Override
	public FixedWorkSetting getWorkSettingForFixedWork(WorkTimeCode code) {
		if(!cache.getFixedWorkSettingMap().containsKey(code)){
			cache.getFixedWorkSettingMap().put(code, fixedWorkSettingRepo.findByKey(AppContexts.user().companyId(), code.v()));
		}
		return cache.getFixedWorkSettingMap().get(code).orElse(null);
	}

	@Override
	public FlowWorkSetting getWorkSettingForFlowWork(WorkTimeCode code) {
		if(!cache.getFlowWorkSettingMap().containsKey(code)){
			cache.getFlowWorkSettingMap().put(code, flowWorkSettingRepo.find(AppContexts.user().companyId(), code.v()));
		}
		return cache.getFlowWorkSettingMap().get(code).orElse(null);
	}

	@Override
	public FlexWorkSetting getWorkSettingForFlexWork(WorkTimeCode code) {
		if(!cache.getFlexWorkSettingMap().containsKey(code)){
			cache.getFlexWorkSettingMap().put(code, flexWorkSettingRepo.find(AppContexts.user().companyId(), code.v()));
		}
		return cache.getFlexWorkSettingMap().get(code).orElse(null);
	}

	@Override
	public Optional<SEmpHistoryImport> getEmploymentHis(String employeeId, GeneralDate baseDate) {
		return sysEmploymentHisAdapter.findSEmpHistBySid(AppContexts.user().companyId(), employeeId, baseDate);
	}

	@Override
	public Optional<CompensatoryLeaveComSetting> getCmpLeaveComSet(String companyId){
		return Optional.ofNullable(this.compensatoryLeaveComSetting(companyId));
	}

	@Override
	public Optional<CompensatoryLeaveEmSetting> getCmpLeaveEmpSet(String companyId, String employmentCode){
		return Optional.ofNullable(this.compensatoryLeaveEmSetting(companyId, employmentCode));
	}
	
	@Override
	public Optional<ElapseYear> elapseYear(String companyId, int specialHolidayCode) {
		return this.elapseYearRepository.findByCode(new CompanyId(companyId), new SpecialHolidayCode(specialHolidayCode));
	}

	@Override
	public Optional<EmpEnrollPeriodImport> getLatestEnrollmentPeriod(String lstEmpId, DatePeriod datePeriod) {
		return empComHisAdapter.getLatestEnrollmentPeriod(lstEmpId, datePeriod);
	}

	@Override
	public EmployeeRecordImport employeeFullInfo(CacheCarrier cacheCarrier, String empId) {
		return this.empEmployeeAdapter.findByAllInforEmpId(cacheCarrier, empId);
	}

	@Override
	public List<SClsHistImport> employeeClassificationHistoires(CacheCarrier cacheCarrier, String companyId,
			List<String> employeeIds, DatePeriod datePeriod) {
		return this.empEmployeeAdapter.lstClassByEmployeeId(cacheCarrier, companyId, employeeIds, datePeriod);
	}

	@Override
	public Optional<GrantDateTbl> grantDateTbl(String companyId, int specialHolidayCode, String grantDateCode) {
		return this.grantDateTblRepo.findByCode(companyId, specialHolidayCode, grantDateCode);
	}
	
	@Override
	public List<GrantDateTbl> grantDateTbl(String companyId, int specialHolidayCode) {
		return this.grantDateTblRepo.findBySphdCd(companyId, specialHolidayCode);
	}
	
	@Override
	public Optional<ClosureStatusManagement> latestClosureStatusManagement(String employeeId) {
		return closureStatusManagementRepo.getLatestByEmpId(employeeId);
	}

	@Override
	public Optional<TimeSpecialLeaveManagementSetting> findByCompany(String companyId) {
		return timeSpecialLeaveMngSetRepository.findByCompany(companyId);
	}

	@Override
	public OptionLicense getOptionLicense() {
		return AppContexts.optionLicense();
	}

	@Override
	public List<PayoutSubofHDManagement> getByListDate(String sid, List<GeneralDate> lstDate) {
		return payoutSubofHDManaRepo.getByListDate(sid, lstDate);
	}

	@Override
	public List<PayoutSubofHDManagement> getByListOccDate(String sid, List<GeneralDate> lstDate) {
		return payoutSubofHDManaRepo.getByListOccDate(sid, lstDate);
	}

	@Override
	public List<InterimAbsMng> getAbsBySidDateList(String sid, List<GeneralDate> lstDate) {
		return interimRecAbasMngRepo.getAbsBySidDateList(sid, lstDate);
	}

	@Override
	public List<InterimRecMng> getRecBySidDateList(String sid, List<GeneralDate> lstDate) {
		return interimRecAbasMngRepo.getRecBySidDateList(sid, lstDate);
	}

	@Override
	public List<LeaveComDayOffManagement> getLeavByListDate(String sid, List<GeneralDate> lstDate) {
		return leaveComDayOffManaRepo.getByListDate(sid, lstDate);
	}

	@Override
	public List<LeaveComDayOffManagement> getLeavByListOccDate(String sid, List<GeneralDate> lstDate) {
		return leaveComDayOffManaRepo.getLeavByListOccDate(sid, lstDate);
	}

	@Override
	public List<InterimBreakMng> getBreakBySidDateList(String sid, List<GeneralDate> lstDate) {
		return interimBreakDayOffMngRepo.getBreakBySidDateList(sid, lstDate);
	}

	@Override
	public List<InterimDayOffMng> getDayOffDateList(String sid, List<GeneralDate> lstDate) {
		return interimBreakDayOffMngRepo.getDayOffDateList(sid, lstDate);
	}

}
