package nts.uk.screen.at.app.dailyperformance.correction;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import lombok.AllArgsConstructor;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualholidaymanagement.AnnualHolidayManagementAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualholidaymanagement.NextAnnualLeaveGrantImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualleave.AnnLeaveRemainNumberAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.annualleave.ReNumAnnLeaReferenceDateImport;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.ReserveLeaveManagerApdater;
import nts.uk.ctx.at.request.dom.application.common.adapter.record.remainingnumber.rsvleamanager.rsvimport.RsvLeaManagerImport;
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
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.BreakDayOffMngInPeriodQuery;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.export.query.numberremainrange.procwithbasedate.NumberConsecutiveVacation;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakDayOffMngRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimBreakMng;
import nts.uk.ctx.at.shared.dom.remainingnumber.breakdayoffmng.interim.InterimDayOffMng;
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
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AbsenceTenProcess;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AnnualHolidaySetOutput;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.LeaveSetOutput;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.SubstitutionHolidayOutput;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveEmSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.ComSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacation;
import nts.uk.ctx.at.shared.dom.vacation.setting.subst.EmpSubstVacationRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.screen.at.app.dailyperformance.correction.dto.Com60HVacationDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.CompensLeaveComDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.HolidayRemainNumberDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ReserveLeaveDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.SubstVacationDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.YearHolidaySettingDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT - 休暇残数を表示する
 *
 */

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DisplayRemainingHolidayNumber {

	@Inject
	private AnnLeaveRemainNumberAdapter annLeaveRemainAdapter;
	
	@Inject
	private ReserveLeaveManagerApdater rsvLeaveRemainAdapter;
	
	@Inject
	private AnnualHolidayManagementAdapter annualHolidayMng;
	
	@Inject
	private RecordDomRequireService requireService;
	
	@Inject
    private CompensLeaveComSetRepository compensLeaveComSetRepo;
	
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
    
    @Inject
    private NursingLeaveSettingRepository nursingLeaveSettingRepo;

	public YearHolidaySettingDto getAnnualLeaveSetting(String companyId, String employeeId, GeneralDate date) {
		AnnualHolidaySetOutput output = AbsenceTenProcess.getSettingForAnnualHoliday(
				requireService.createRequire(), companyId);
		
		if (output.isYearHolidayManagerFlg()) {
			//RequestList198
			ReNumAnnLeaReferenceDateImport remainNum = annLeaveRemainAdapter
					.getReferDateAnnualLeaveRemainNumber(employeeId, date);
			return new YearHolidaySettingDto(output.isYearHolidayManagerFlg(), output.isSuspensionTimeYearFlg(),
					remainNum.getAnnualLeaveRemainNumberExport() != null
							? remainNum.getAnnualLeaveRemainNumberExport().getAnnualLeaveGrantDay() : 0,
					remainNum.getAnnualLeaveRemainNumberExport() != null
							? remainNum.getAnnualLeaveRemainNumberExport().getAnnualLeaveGrantTime()
							: 0);
		} else {
			return new YearHolidaySettingDto(false, false, null, null);
		}
	}

	public ReserveLeaveDto getReserveLeaveSetting(String companyId, String employeeId, GeneralDate date) {
		boolean manageAtr = AbsenceTenProcess.getSetForYearlyReserved(
				requireService.createRequire(),
				new CacheCarrier(),
				companyId, employeeId, date);
		if (manageAtr) {
			// call requestlist201
			Optional<RsvLeaManagerImport> optOutput = rsvLeaveRemainAdapter.getRsvLeaveManager(employeeId, date);
			return new ReserveLeaveDto(manageAtr, optOutput.isPresent() && optOutput.get().getReserveLeaveInfo() != null
					? optOutput.get().getReserveLeaveInfo().getRemainingDays() : 0);
		} else
			return new ReserveLeaveDto(false, null);
	}
	
	public SubstVacationDto getSubsitutionVacationSetting(String companyId, String employeeId, GeneralDate date) {
		LeaveSetOutput output = AbsenceTenProcess.getSetForLeave(
				requireService.createRequire(), new CacheCarrier(),
				companyId, employeeId, date);
		RequireM11Imp requireM11Imp = new RequireM11Imp(comDayOffManaDataRepo, leaveComDayOffManaRepo, leaveManaDataRepo, shareEmploymentAdapter, compensLeaveEmSetRepo, compensLeaveComSetRepo, interimBreakDayOffMngRepo, closureEmploymentRepo, closureRepo, empEmployeeAdapter, substitutionOfHDManaDataRepo, payoutSubofHDManaRepo, payoutManagementDataRepo, empSubstVacationRepo, comSubstVacationRepo, interimRecAbasMngRepo, payoutHdManaRepo);

		if (output.isSubManageFlag()) {
			double remain = AbsenceReruitmentMngInPeriodQuery.getAbsRecMngRemain(
			        requireM11Imp, new CacheCarrier(),
					employeeId, date).v();
			return new SubstVacationDto(output.isSubManageFlag(), remain);
		}
		return new SubstVacationDto(false, null);
	}

	public CompensLeaveComDto getCompensatoryLeaveSetting(String companyId, String employeeId, GeneralDate date) {
		SubstitutionHolidayOutput output = AbsenceTenProcess.getSettingForSubstituteHoliday(
				requireService.createRequire(), new CacheCarrier(),
				companyId, employeeId, date);
		RequireM11Imp requireM11Imp = new RequireM11Imp(comDayOffManaDataRepo, leaveComDayOffManaRepo, leaveManaDataRepo, shareEmploymentAdapter, compensLeaveEmSetRepo, compensLeaveComSetRepo, interimBreakDayOffMngRepo, closureEmploymentRepo, closureRepo, empEmployeeAdapter, substitutionOfHDManaDataRepo, payoutSubofHDManaRepo, payoutManagementDataRepo, empSubstVacationRepo, comSubstVacationRepo, interimRecAbasMngRepo, payoutHdManaRepo);

		if (output != null && output.isSubstitutionFlg()) {
		    NumberConsecutiveVacation remain = BreakDayOffMngInPeriodQuery.getBreakDayOffMngRemain(
			        requireM11Imp, new CacheCarrier(),
					employeeId, date);
		    Double days = 0.0;
		    int time = 0;
		    if (remain != null) {
		        if (remain.getDays() != null) {
		            days = remain.getDays().v();
		        }
		        if (remain.getRemainTime() != null) {
		            time = remain.getRemainTime().v();
		        }
		    }
			return new CompensLeaveComDto(output.isSubstitutionFlg(), output.isTimeOfPeriodFlg(), days, time);
		} else {
			return new CompensLeaveComDto(false, false, 0.0, 0);
		}
	}

	public Com60HVacationDto getCom60HVacationSetting(String companyId, String employeeId, GeneralDate date) {
		Com60HVacationDto output = new Com60HVacationDto("", false, null, null);
		return output;
	}
	
	public void getNursingSetting(String companyId, String employeeId, GeneralDate date) {
	    // TODO: childCare + longTerm
	    // 子看護介護の設定の取得
	    NursingLeaveSetting nursingLeaveSettings = nursingLeaveSettingRepo.findByCompanyIdAndNursingCategory(companyId, NursingCategory.ChildNursing.value);
        // 子看護介護の設定の取得
        NursingLeaveSetting nursingLeaveSetting = nursingLeaveSettingRepo.findByCompanyIdAndNursingCategory(companyId, NursingCategory.Nursing.value);
	}
	
	private GeneralDate getNextGrantDate(String companyId, String employeeId, GeneralDate date) {
		List<NextAnnualLeaveGrantImport> lstOutput = this.annualHolidayMng.acquireNextHolidayGrantDate(companyId, employeeId, date);
		return lstOutput.isEmpty() ? null : lstOutput.get(0).grantDate;
	}
	
	public HolidayRemainNumberDto getRemainingHolidayNumber(String employeeId) {
		String companyId = AppContexts.user().companyId();
		GeneralDate baseDate = GeneralDate.today();
		HolidayRemainNumberDto result = new HolidayRemainNumberDto();
		result.setAnnualLeave(this.getAnnualLeaveSetting(companyId, employeeId, baseDate));
		result.setCompensatoryLeave(this.getCompensatoryLeaveSetting(companyId, employeeId, baseDate));
		result.setReserveLeave(this.getReserveLeaveSetting(companyId, employeeId, baseDate));
		result.setSubstitutionLeave(this.getSubsitutionVacationSetting(companyId, employeeId, baseDate));
		result.setCom60HVacation(this.getCom60HVacationSetting(companyId, employeeId, baseDate));
		if (result.getAnnualLeave().isManageYearOff())
			result.setNextGrantDate(this.getNextGrantDate(companyId, employeeId, baseDate));
		return result;
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
    }
}
