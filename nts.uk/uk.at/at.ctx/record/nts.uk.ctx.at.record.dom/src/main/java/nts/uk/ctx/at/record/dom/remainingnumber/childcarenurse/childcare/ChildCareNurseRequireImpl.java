package nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.childcare;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmpEmployeeAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employee.EmployeeImport;
import nts.uk.ctx.at.shared.dom.adapter.employment.ShareEmploymentAdapter;
import nts.uk.ctx.at.shared.dom.adapter.employment.SharedSidPeriodDateEmploymentImport;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.CareUsedNumberData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.CareUsedNumberRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.interimdata.TempCareManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.care.interimdata.TempCareManagementRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareUsedNumberData;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.ChildCareUsedNumberRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareManagement;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.childcare.interimdata.TempChildCareManagementRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.data.CareManagementDate;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.CareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.CareLeaveRemainingInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemInfoRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.ChildCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.nursingcareleavemanagement.info.NursingCareLeaveRemainingInfo;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagement;
import nts.uk.ctx.at.shared.dom.scherec.closurestatus.ClosureStatusManagementRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.FamilyInfo;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItem;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.UseClassification;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.license.option.OptionLicense;
import nts.uk.ctx.at.record.dom.remainingnumber.childcarenurse.GetRemainingNumberChildCareNurseService;

public class ChildCareNurseRequireImpl implements GetRemainingNumberChildCareNurseService.Require{

	private WorkingConditionItemRepository workingConditionItemRepo;
	private AnnualPaidLeaveSettingRepository annualPaidLeaveSettingRepo;
	private ClosureStatusManagementRepository closureStatusManagementRepo;
	private ClosureEmploymentRepository closureEmploymentRepo;
	private ShareEmploymentAdapter shareEmploymentAdapter;
	private ClosureRepository closureRepo;
	private EmpEmployeeAdapter empEmployeeAdapter;
	private NursingLeaveSettingRepository nursingLeaveSettingRepo;
	private ChildCareUsedNumberRepository childCareUsedNumberRepository;
	private CareUsedNumberRepository careUsedNumberRepository;
	private TempChildCareManagementRepository tempChildCareManagementRepository;
	private TempCareManagementRepository tempCareManagementRepository;
	private ChildCareLeaveRemInfoRepository childCareLeaveRemInfoRepository;
	private CareLeaveRemainingInfoRepository careLeaveRemainingInfoRepository;

	public ChildCareNurseRequireImpl(
			WorkingConditionItemRepository workingConditionItemRepository,
			AnnualPaidLeaveSettingRepository annualPaidLeaveSettingRepository,
			ClosureStatusManagementRepository closureStatusManagementRepository,
			ClosureEmploymentRepository closureEmploymentRepository,
			ShareEmploymentAdapter shareEmploymentAdapterIn,
			ClosureRepository closureRepository,
			EmpEmployeeAdapter empEmployeeAdapterIn,
			NursingLeaveSettingRepository nursingLeaveSettingRepository,
			ChildCareUsedNumberRepository childCareUsedNumberRepositoryIn,
			CareUsedNumberRepository careUsedNumberRepositoryIn,
			TempChildCareManagementRepository tempChildCareManagementRepositoryIn,
			TempCareManagementRepository tempCareManagementRepositoryIn,
			ChildCareLeaveRemInfoRepository childCareLeaveRemInfoRepository,
			CareLeaveRemainingInfoRepository careLeaveRemainingInfoRepository
			) {

		this.workingConditionItemRepo=workingConditionItemRepository;
		this.annualPaidLeaveSettingRepo=annualPaidLeaveSettingRepository;
		this.closureStatusManagementRepo=closureStatusManagementRepository;
		this.closureEmploymentRepo=closureEmploymentRepository;
		this.shareEmploymentAdapter=shareEmploymentAdapterIn;
		this.closureRepo=closureRepository;
		this.empEmployeeAdapter=empEmployeeAdapterIn;
		this.nursingLeaveSettingRepo=nursingLeaveSettingRepository;
		this.childCareUsedNumberRepository=childCareUsedNumberRepositoryIn;
		this.careUsedNumberRepository=careUsedNumberRepositoryIn;
		this.tempChildCareManagementRepository = tempChildCareManagementRepositoryIn;
		this.tempCareManagementRepository = tempCareManagementRepositoryIn;
		this.childCareLeaveRemInfoRepository = childCareLeaveRemInfoRepository;
		this.careLeaveRemainingInfoRepository = careLeaveRemainingInfoRepository;
	}

	@Override
	public Optional<WorkingConditionItem> workingConditionItem(String employeeId, GeneralDate baseDate) {
		return workingConditionItemRepo.getBySidAndStandardDate(employeeId, baseDate);
	}

	@Override
	public AnnualPaidLeaveSetting annualPaidLeaveSetting(String companyId) {
		return annualPaidLeaveSettingRepo.findByCompanyId(companyId);
	}

	@Override
	public Optional<ClosureStatusManagement> latestClosureStatusManagement(String employeeId) {
		return closureStatusManagementRepo.getLatestByEmpId(employeeId);
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
	public List<Closure> closureActive(String companyId, UseClassification useAtr) {
		return closureRepo.findAllActive(companyId, useAtr);
	}

	@Override
	public EmployeeImport employee(CacheCarrier cacheCarrier, String empId) {
		return empEmployeeAdapter.findByEmpIdRequire(cacheCarrier, empId);
	}

	@Override
	public EmployeeImport findByEmpId(String empId) {
		return empEmployeeAdapter.findByEmpId(empId);
	}

	@Override
	public List<FamilyInfo> familyInfo(String employeeId) {
		// 2021/03/22 時点では家族情報は取得できない
		return new ArrayList<>();
	}

	@Override
	public List<TempChildCareManagement> tempChildCareManagement(
			String employeeId, DatePeriod ymd) {
			return tempChildCareManagementRepository.findByPeriodOrderByYmd(employeeId, ymd);
	}

	@Override
	public List<TempCareManagement> tempCareManagement(
			String employeeId, DatePeriod ymd) {
			return tempCareManagementRepository.findByPeriodOrderByYmd(employeeId, ymd);
	}

	@Override
	public NursingLeaveSetting nursingLeaveSetting(String companyId, NursingCategory nursingCategory) {
		return nursingLeaveSettingRepo.findByCompanyIdAndNursingCategory(companyId, nursingCategory.value);
	}

	@Override
	public Optional<ChildCareUsedNumberData> childCareUsedNumber(String employeeId) {
		return childCareUsedNumberRepository.find(employeeId);
	}

	@Override
	public Optional<CareUsedNumberData> careUsedNumber(String employeeId) {
		return careUsedNumberRepository.find(employeeId);
	}

	@Override
	public Optional<CareManagementDate> careData(String familyID) {
		// 2021/03/22 時点では家族情報は取得できない
		return Optional.empty();
	}

	@Override
	public Optional<ChildCareLeaveRemainingInfo> childCareLeaveEmployeeInfo(String employeeId) {
		return childCareLeaveRemInfoRepository.getChildCareByEmpId(employeeId);
	}

	@Override
	public Optional<CareLeaveRemainingInfo> careLeaveEmployeeInfo(String employeeId) {
		return careLeaveRemainingInfoRepository.getCareByEmpId(employeeId);
	}

	@Override
	public Optional<NursingCareLeaveRemainingInfo> employeeInfo(String employeeId, NursingCategory nursingCategory) {
		if(nursingCategory.equals(NursingCategory.Nursing))
			return careLeaveRemainingInfoRepository.getCareByEmpId(employeeId).map(mapper->(NursingCareLeaveRemainingInfo)mapper);
		if(nursingCategory.equals(NursingCategory.ChildNursing))
			return childCareLeaveRemInfoRepository.getChildCareByEmpId(employeeId).map(mapper->(NursingCareLeaveRemainingInfo)mapper);
		return Optional.empty();
	}

	@Override
	public OptionLicense getOptionLicense() {
		return AppContexts.optionLicense();
	}


}
