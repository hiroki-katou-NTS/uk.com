package nts.uk.ctx.at.record.dom.attendanceitem.monthlyattditem;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.attendanceitem.monthlyattditem.NarrowDownListMonthlyAttdItem.Require;
import nts.uk.ctx.at.record.dom.workrecord.goout.OutManage;
import nts.uk.ctx.at.record.dom.workrecord.goout.OutManageRepository;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeRecordImport;
import nts.uk.ctx.at.shared.dom.adapter.employee.SClsHistImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.BsEmploymentHistoryImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySettingRepository;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrame;
import nts.uk.ctx.at.shared.dom.ot.frame.OvertimeWorkFrameRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnLeaEmpBasicInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SEmpHistoryImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.paymana.SysEmploymentHisAdapter;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTSettingRepository;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagement;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagementRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.BPTimeItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.timeitem.BonusPayTimeItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.deviationtime.deviationtimeframe.DivergenceTimeRoot;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.premiumitem.PremiumItem;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.personcostcalc.premiumitem.PremiumItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.OutsideOTSetting;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattendanceitem.service.NarrowDownListMonthlyAttdItemPub;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItem;
import nts.uk.ctx.at.shared.dom.scherec.optitem.OptionalItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimes;
import nts.uk.ctx.at.shared.dom.scherec.totaltimes.TotalTimesRepository;
import nts.uk.ctx.at.shared.dom.specialholiday.SpecialHoliday;
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
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrame;
import nts.uk.ctx.at.shared.dom.workdayoff.frame.WorkdayoffFrameRepository;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultiple;
import nts.uk.ctx.at.shared.dom.workmanagementmultiple.WorkManagementMultipleRepository;
import nts.uk.ctx.at.shared.dom.workrule.deformed.AggDeformedLaborSetting;
import nts.uk.ctx.at.shared.dom.workrule.deformed.AggDeformedLaborSettingRepository;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmpComHisAdapter;
import nts.uk.ctx.at.shared.dom.workrule.organizationmanagement.employeeinfor.employmenthistory.imported.EmpEnrollPeriodImport;
import nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation.TimeSpecialLeaveManagementSetting;
import nts.uk.ctx.at.shared.dom.workrule.vacation.specialvacation.timespecialvacation.TimeSpecialLeaveMngSetRepository;
import nts.uk.ctx.at.shared.dom.workrule.workform.FlexWorkMntSetRepository;
import nts.uk.ctx.at.shared.dom.workrule.workform.FlexWorkSet;
import nts.uk.ctx.at.shared.dom.workrule.workuse.TemporaryWorkUseManage;
import nts.uk.ctx.at.shared.dom.workrule.workuse.TemporaryWorkUseManageRepository;
import nts.uk.ctx.at.shared.dom.worktype.absenceframe.AbsenceFrame;
import nts.uk.ctx.at.shared.dom.worktype.absenceframe.AbsenceFrameRepository;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHolidayFrame;
import nts.uk.ctx.at.shared.dom.worktype.specialholidayframe.SpecialHolidayFrameRepository;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.license.option.OptionLicense;

@Stateless
public class NarrowDownListMonthlyAttdItemImpl implements NarrowDownListMonthlyAttdItemPub{

	@Inject 
	private AnnualPaidLeaveSettingRepository annualPaidLeaveSettingRepository;
	@Inject 
	private TimeSpecialLeaveMngSetRepository timeSpecialLeaveMngSetRepository;
	@Inject 
	private OvertimeWorkFrameRepository overtimeWorkFrameRepository;
	@Inject 
	private WorkdayoffFrameRepository workdayoffFrameRepository;
	@Inject 
	private DivergenceTimeRepository divergenceTimeRepository;
	@Inject 
	private BPTimeItemRepository bPTimeItemRepository;
	@Inject 
	private OptionalItemRepository optionalItemRepository;
	@Inject 
	private PremiumItemRepository premiumItemRepository;
	@Inject 
	private OutManageRepository outManageRepository;
	@Inject 
	private AggDeformedLaborSettingRepository aggDeformedLaborSettingRepository;
	@Inject 
	private FlexWorkMntSetRepository flexWorkMntSetRepository;
	@Inject 
	private RetentionYearlySettingRepository retentionYearlySettingRepository;
	@Inject 
	private CompensLeaveComSetRepository compensLeaveComSetRepository;
	@Inject 
	private ComSubstVacationRepository comSubstVacationRepository;
	@Inject 
	private SpecialHolidayRepository specialHolidayRepository;
	@Inject 
	private AbsenceFrameRepository absenceFrameRepository;
	@Inject 
	private SpecialHolidayFrameRepository specialHolidayFrameRepository;
	@Inject 
	private NursingLeaveSettingRepository nursingLeaveSettingRepository;
	@Inject 
	private PublicHolidaySettingRepository publicHolidaySettingRepository;
	@Inject 
	private TotalTimesRepository totalTimesRepository;
	@Inject 
	private OutsideOTSettingRepository outsideOTSettingRepository;
	@Inject 
	private WorkManagementMultipleRepository workManagementMultipleRepository;
	@Inject 
	private TemporaryWorkUseManageRepository temporaryWorkUseManageRepository;
	@Inject
	private ElapseYearRepository elapseYearRepository;
	@Inject
	private EmpComHisAdapter empComHisAdapter;
	@Inject
	private EmpEmployeeAdapter empEmployeeAdapter;
	@Inject
	private GrantDateTblRepository grantDateTblRepo;
	@Inject
	private AnnLeaEmpBasicInfoRepository annLeaEmpBasicInfoRepo;
	@Inject
	private ClosureStatusManagementRepository closureStatusManagementRepo;
	@Inject
	private ShareEmploymentAdapter shareEmploymentAdapter;
	@Inject
	private SysEmploymentHisAdapter sysEmploymentHisAdapter;
	@Inject
	private CompensLeaveEmSetRepository compensLeaveEmSetRepo;
	
	@Override
	public List<Integer> get(String companyId, List<Integer> listAttdId) {
		Require require = new NarrowDownMonthlyAttdItemImpl();
		return NarrowDownListMonthlyAttdItem.get(require, companyId, listAttdId);
	}
	
	private class NarrowDownMonthlyAttdItemImpl implements NarrowDownListMonthlyAttdItem.Require {

		@Override
		public OptionLicense getOptionLicense() {
			return AppContexts.optionLicense();
		}

		@Override
		public AnnualPaidLeaveSetting findByCid(String companyId) {
			return annualPaidLeaveSettingRepository.findByCompanyId(companyId);
		}

		@Override
		public Optional<TimeSpecialLeaveManagementSetting> findByCompany(String companyId) {
			return timeSpecialLeaveMngSetRepository.findByCompany(companyId);
		}

		@Override
		public List<OvertimeWorkFrame> getAllOvertimeWorkFrame(String companyId) {
			return overtimeWorkFrameRepository.getAllOvertimeWorkFrame(companyId);
		}

		@Override
		public List<WorkdayoffFrame> getAllWorkdayoffFrame(String companyId) {
			return workdayoffFrameRepository.getAllWorkdayoffFrame(companyId);
		}

		@Override
		public List<DivergenceTimeRoot> getAllDivTime(String companyId) {
			return divergenceTimeRepository.getAllDivTime(companyId);
		}

		@Override
		public List<BonusPayTimeItem> getListBonusPayTimeItem(String companyId) {
			return bPTimeItemRepository.getListBonusPayTimeByCid(companyId);
		}

		@Override
		public List<OptionalItem> findAllOptionalItem(String companyId) {
			return optionalItemRepository.findAll(companyId);
		}

		@Override
		public List<PremiumItem> findPremiumItemByCompanyID(String companyID) {
			return premiumItemRepository.findByCompanyID(companyID);
		}

		@Override
		public Optional<OutManage> findOutManageByID(String companyID) {
			return outManageRepository.findByID(companyID);
		}

		@Override
		public Optional<AggDeformedLaborSetting> findAggDeformedLaborSettingByCid(String companyId) {
			return aggDeformedLaborSettingRepository.findByCid(companyId);
		}

		@Override
		public Optional<FlexWorkSet> findFlexWorkSet(String companyId) {
			return flexWorkMntSetRepository.find(companyId);
		}

		
		@Override
		public AnnualPaidLeaveSetting findByCompanyId(String companyId) {
			return annualPaidLeaveSettingRepository.findByCompanyId(companyId);
		}

		@Override
		public Optional<RetentionYearlySetting> findRetentionYearlySettingByCompanyId(String companyId) {
			return retentionYearlySettingRepository.findByCompanyId(companyId);
		}

		@Override
		public CompensatoryLeaveComSetting findCompensatoryLeaveComSetting(String companyId) {
			return compensLeaveComSetRepository.find(companyId);
		}
		
		@Override
		public Optional<ComSubstVacation> findComSubstVacationById(String companyId) {
			return comSubstVacationRepository.findById(companyId);
		}
		
		@Override
		public List<SpecialHoliday> findSpecialHolidayByCompanyId(String companyId) {
			return specialHolidayRepository.findByCompanyId(companyId);
		}
		
		@Override
		public List<AbsenceFrame> findAllAbsenceFrame(String companyId) {
			return absenceFrameRepository.findAll(companyId);
		}
		
		@Override
		public List<SpecialHolidayFrame> findAllSpecialHolidayFrame(String companyId) {
			return specialHolidayFrameRepository.findAll(companyId);
		}
		
		@Override
		public List<NursingLeaveSetting> findNursingLeaveSetting(String companyId) {
			return nursingLeaveSettingRepository.findByCompanyId(companyId);
		}
		
		@Override
		public Optional<PublicHolidaySetting> getPublicHolidaySetting(String companyId) {
			return publicHolidaySettingRepository.get(companyId);
		}
		
		@Override
		public List<TotalTimes> getAllTotalTimes(String companyId) {
			return totalTimesRepository.getAllTotalTimes(companyId);
		}
		
		@Override
		public Optional<OutsideOTSetting> reportById(String companyId) {
			return outsideOTSettingRepository.reportById(companyId);
		}
		
		@Override
		public Optional<WorkManagementMultiple> findWorkManagementMultiple(String companyID) {
			return workManagementMultipleRepository.findByCode(companyID);
		}
		
		@Override
		public Optional<TemporaryWorkUseManage> findTemporaryWorkUseManage(String companyId) {
			return temporaryWorkUseManageRepository.findByCid(companyId);
		}

		@Override
		public Optional<ElapseYear> elapseYear(String companyId, int specialHolidayCode) {

			return elapseYearRepository.findByCode(new CompanyId(companyId), new SpecialHolidayCode(specialHolidayCode));
		}

		@Override
		public Optional<EmpEnrollPeriodImport> getLatestEnrollmentPeriod(String lstEmpId, DatePeriod datePeriod) {
			return empComHisAdapter.getLatestEnrollmentPeriod(lstEmpId, datePeriod);
		}

		@Override
		public EmployeeRecordImport employeeFullInfo(CacheCarrier cacheCarrier, String empId) {
			return empEmployeeAdapter.findByAllInforEmpId(cacheCarrier, empId);
		}

		@Override
		public List<SClsHistImport> employeeClassificationHistoires(CacheCarrier cacheCarrier, String companyId,
				List<String> employeeIds, DatePeriod datePeriod) {
			return empEmployeeAdapter.lstClassByEmployeeId(cacheCarrier, companyId, employeeIds, datePeriod);
		}

		@Override
		public List<GrantDateTbl> grantDateTbl(String companyId, int specialHolidayCode) {

			return grantDateTblRepo.findBySphdCd(companyId, specialHolidayCode);
		}

		@Override
		public Optional<GrantDateTbl> grantDateTbl(String companyId, int specialHolidayCode, String grantDateCode) {

			return grantDateTblRepo.findByCode(companyId, specialHolidayCode, grantDateCode);
		}

		@Override
		public Optional<AnnualLeaveEmpBasicInfo> employeeAnnualLeaveBasicInfo(String employeeId) {

			return annLeaEmpBasicInfoRepo.get(employeeId);
		}

		@Override
		public Optional<ClosureStatusManagement> latestClosureStatusManagement(String employeeId) {
			return closureStatusManagementRepo.getLatestByEmpId(employeeId);
		}

		@Override
		public Optional<BsEmploymentHistoryImport> employmentHistory(CacheCarrier cacheCarrier, String companyId,
				String employeeId, GeneralDate baseDate) {
			return shareEmploymentAdapter.findEmploymentHistoryRequire(cacheCarrier, companyId, employeeId, baseDate);
		}

		@Override
		public Optional<CompensatoryLeaveComSetting> compensatoryLeaveComSetting(String companyId) {
			return Optional.ofNullable(compensLeaveComSetRepository.find(companyId));
		}

		@Override
		public Optional<CompensatoryLeaveEmSetting> compensatoryLeaveEmSetting(String companyId,
				String employmentCode) {
			return Optional.ofNullable(compensLeaveEmSetRepo.find(companyId, employmentCode));
		}

		@Override
		public Optional<SEmpHistoryImport> getSEmpHistoryImport(String employeeId, GeneralDate baseDate) {
			return sysEmploymentHisAdapter.findSEmpHistBySid(AppContexts.user().companyId(), employeeId, baseDate);
		}
		
	}
}
