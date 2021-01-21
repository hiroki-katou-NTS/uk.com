package nts.uk.screen.at.app.dailyperformance.correction.lock;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.ApprovalStatusAdapter;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApprovalRootOfEmployeeImport;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApproveRootStatusForEmpImport;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.enums.ApprovalActionByEmpl;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.enums.ApprovalStatusForEmployee;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.UseClassification;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.pub.workrule.closure.PresentClosingPeriodExport;
import nts.uk.ctx.at.shared.pub.workrule.closure.ShClosurePub;
import nts.uk.screen.at.app.dailyperformance.correction.DailyPerformanceScreenRepo;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ActualLockDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ApprovalUseSettingDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ClosureDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPDataDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DateRange;
import nts.uk.screen.at.app.dailyperformance.correction.dto.IdentityProcessUseSetDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ScreenMode;
import nts.uk.screen.at.app.dailyperformance.correction.dto.WorkFixedDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.checkapproval.ApproveRootStatusForEmpDto;
import nts.uk.screen.at.app.dailyperformance.correction.text.DPText;
import nts.arc.time.calendar.period.DatePeriod;

@Stateless
@TransactionAttribute(TransactionAttributeType.SUPPORTS)
public class DPLock {

	@Inject
	private ApprovalStatusAdapter approvalStatusAdapter;

	@Inject
	private DailyPerformanceScreenRepo repo;

	@Inject
	private ShClosurePub shClosurePub;
//	
//	@Inject
//	private FindClosureDateService findClosureDateService;

	@Inject
	private ClosureRepository closureRepository;
	@Inject
	private ClosureEmploymentRepository closureEmploymentRepository;

	public Map<String, DatePeriod> extractEmpAndRange(DateRange dateRange, String companyId,
			List<String> listEmployeeId, List<ClosureDto> closureDtos) {
		Map<String, DatePeriod> employeeAndDateRange = new HashMap<>();
		// Map<String, String> employmentWithSidMap = repo.getAllEmployment(companyId,
		// listEmployeeId, new DateRange(GeneralDate.today(), GeneralDate.today()));
		// List<ClosureDto> closureDtos = repo.getClosureId(employmentWithSidMap,
		// dateRange.getEndDate());
		if (!closureDtos.isEmpty()) {
			closureDtos.forEach(x -> {
				DatePeriod datePeriod = ClosureService.getClosurePeriod(
						ClosureService.createRequireM1(closureRepository, closureEmploymentRepository),
						x.getClosureId(),
						new YearMonth(x.getClosureMonth()));
				DatePeriod dateResult = periodLock(x.getDatePeriod(), datePeriod);
				Optional<ActualLockDto> actualLockDto = repo.findAutualLockById(companyId, x.getClosureId());
				if (actualLockDto.isPresent()) {
					if (actualLockDto.get().getDailyLockState() == 1) {
						employeeAndDateRange.put(mergeString(x.getSid(), "|", x.getClosureId().toString(), "|",
								DPText.LOCK_EDIT_CELL_DAY), dateResult);
					}
				}
				// アルゴリズム「表示項目を制御する」を実行する | Execute "control display items"
				List<WorkFixedDto> workFixeds = repo.findWorkFixed(x.getClosureId(), x.getClosureMonth());
				for (WorkFixedDto workFixedOp : workFixeds) {
					employeeAndDateRange.put(mergeString(x.getSid(), "|", x.getClosureId().toString(),
							"|" + workFixedOp.getWkpId(), "|", DPText.LOCK_EDIT_CELL_WORK), dateResult);
				}
			});
		}
		return employeeAndDateRange;
	}

	public Map<Integer, DatePeriod> lockHistMap(String companyId, List<String> lstEmployeeId,
			List<ClosureDto> closureDtos) {
		// Map<String, String> employmentWithSidMap = repo.getAllEmployment(companyId,
		// lstEmployeeId, new DateRange(GeneralDate.today(), GeneralDate.today()));
		// return findClosureDateService.findClosureDate(companyId,
		// employmentWithSidMap, GeneralDate.today());
		List<Integer> lstClosureIds = closureDtos.stream().map(x -> x.getClosureId()).distinct()
				.collect(Collectors.toList());
		Map<Integer, DatePeriod> mapClsPeriod = new HashMap<>();
		lstClosureIds.stream().forEach(x -> {
			Optional<PresentClosingPeriodExport> closingPeriod = shClosurePub.find(companyId, x);
			if (closingPeriod.isPresent())
				mapClsPeriod.put(x, new DatePeriod(closingPeriod.get().getClosureStartDate(),
						closingPeriod.get().getClosureEndDate()));
		});
		return mapClsPeriod;
	}

	public boolean islockHist(DPDataDto data, Map<String, DatePeriod> empHist) {
		if (empHist.isEmpty())
			return false;
		val datePeriod = empHist.get(data.getEmployeeId());
		if (datePeriod != null && data.getDate().after(datePeriod.end()))
			return false;
		if (datePeriod != null && (data.getDate().afterOrEquals(datePeriod.start())
				&& data.getDate().beforeOrEquals(datePeriod.end())))
			return false;
		return true;
	}

	public Map<String, Boolean> signDayMap(String companyId, List<String> employeeIds, DateRange dateRange) {
		return repo.getConfirmDay(companyId, employeeIds, dateRange);
	}

	public Map<String, ApproveRootStatusForEmpDto> getCheckApproval(List<String> employeeIds, DateRange dateRange,
			String employeeIdApproval, int mode) {
		// get check
		if (employeeIds.isEmpty())
			return Collections.emptyMap();

		// get disable
		if (mode == ScreenMode.APPROVAL.value) {
			long startTime = System.currentTimeMillis();
			ApprovalRootOfEmployeeImport approvalRoot = approvalStatusAdapter.getApprovalRootOfEmloyee(
					new DatePeriod(dateRange.getStartDate(), dateRange.getEndDate()), employeeIdApproval, 1);
			System.out.println("thoi gian getApp: " + (System.currentTimeMillis() - startTime));
			Map<String, ApproveRootStatusForEmpDto> approvalRootMap = approvalRoot == null ? Collections.emptyMap()
					: approvalRoot.getApprovalRootSituations().stream().collect(
							Collectors.toMap(x -> mergeString(x.getTargetID(), "|", x.getAppDate().toString()), x -> {
								ApproveRootStatusForEmpDto dto = new ApproveRootStatusForEmpDto();
								if (x.getApprovalStatus() == null
										|| x.getApprovalStatus().getApprovalActionByEmpl() == null) {
									dto.setCheckApproval(false);
								} else {
									if (x.getApprovalStatus()
											.getApprovalActionByEmpl() == ApprovalActionByEmpl.APPROVALED) {
										dto.setCheckApproval(true);
									} else {
										dto.setCheckApproval(false);
									}
								}
								dto.setApproverEmployeeState(x.getApprovalAtr());
								dto.setApprovalStatus(x.getApprovalStatus() == null ? null
										: x.getApprovalStatus().getReleaseDivision());
								return dto;
							}, (x, y) -> x));
			return approvalRootMap;
		} else {
			long startTime = System.currentTimeMillis();
			List<ApproveRootStatusForEmpImport> approvals = approvalStatusAdapter
					.getApprovalByListEmplAndListApprovalRecordDateNew(dateRange.toListDate(), employeeIds, 1);
			System.out.println("thoi gian getApp: " + (System.currentTimeMillis() - startTime));
			Map<String, ApproveRootStatusForEmpDto> approvalRootMap = approvals.stream().collect(
					Collectors.toMap(x -> mergeString(x.getEmployeeID(), "|", x.getAppDate().toString()), x -> {
						return new ApproveRootStatusForEmpDto(null,
								x.getApprovalStatus() != ApprovalStatusForEmployee.UNAPPROVED);
					}, (x, y) -> x));
			return approvalRootMap;
		}
	}

	public boolean islockApproval(boolean checkBoxAppval) {
		return checkBoxAppval;
	}

	public Map<String, ApproveRootStatusForEmpDto> lockCheckMonth(DateRange dateRange, List<String> employeeIds) {
		List<ApproveRootStatusForEmpImport> approvals = approvalStatusAdapter
				.getApprovalByListEmplAndListApprovalRecordDateNew(dateRange.toListDate(), employeeIds, 2);
		Map<String, ApproveRootStatusForEmpDto> approvalRootMap = approvals.stream()
				.collect(Collectors.toMap(x -> mergeString(x.getEmployeeID(), "|", x.getAppDate().toString()), x -> {
					return new ApproveRootStatusForEmpDto(null,
							x.getApprovalStatus() != ApprovalStatusForEmployee.UNAPPROVED);
				}, (x, y) -> x));
		return approvalRootMap;
	}

	public Pair<List<ClosureSidDto>, List<ConfirmationMonthDto>> lockConfirmMonth(String companyId,
			List<String> employeeIds, DateRange dateRange, List<ClosureDto> closureDtos) {

		Set<Integer> closureIds = closureDtos.stream().map(x -> x.getClosureId()).collect(Collectors.toSet());

		Map<Integer, Closure> closures = closureIds.isEmpty() ? Collections.emptyMap()
				: closureRepository.findByListId(companyId, new ArrayList<>(closureIds)).stream()
						.filter(x -> x.getUseClassification().value == UseClassification.UseClass_Use.value)
						.collect(Collectors.toMap(x -> x.getClosureId().value, x -> x));

		List<ClosureSidDto> closureSid = closureDtos.stream().filter(x -> closures.containsKey(x.getClosureId()))
				.map(x -> {
					return new ClosureSidDto(x.getSid(), closures.get(x.getClosureId()));
				}).collect(Collectors.toList());

		List<ConfirmationMonthDto> confirmMonths = closures.isEmpty() ? Collections.emptyList()
				: repo.confirmationMonth(companyId, closureDtos.stream()
						.collect(Collectors.toMap(x -> x.getSid(), x -> x.getClosureId(), (x, y) -> x)));

		return Pair.of(closureSid, confirmMonths);
	}

	public DPLockDto checkLockAll(String companyId, List<String> employeeIds, DateRange dateRange,
			String employeeIdApproval, int mode, Optional<IdentityProcessUseSetDto> identityProcessUseSetDto,
			Optional<ApprovalUseSettingDto> approvalUseSettingDto) {
		DPLockDto dto = new DPLockDto();
		List<ClosureDto> closureDtos = repo.getAllClosureDto(companyId, employeeIds, dateRange);
		dto.setLockDayAndWpl(extractEmpAndRange(dateRange, companyId, employeeIds, closureDtos));
		if (identityProcessUseSetDto.isPresent()) {
			if (identityProcessUseSetDto.get().isUseConfirmByYourself())
				dto.setSignDayMap(signDayMap(companyId, employeeIds, dateRange));
			if (identityProcessUseSetDto.get().isUseIdentityOfMonth()) {
				// dto.setLockConfirmMonth(lockConfirmMonth(companyId, employeeIds, dateRange,
				// closureDtos));

			}
		}

//		if(approvalUseSettingDto.isPresent()){
//			if(approvalUseSettingDto.get().getUseMonthApproverConfirm()) {
//				//dto.setLockCheckMonth(lockCheckMonth(dateRange, employeeIds));
//			}
//			if(approvalUseSettingDto.get().getUseDayApproverConfirm()) dto.setLockCheckApprovalDay(getCheckApproval(employeeIds, dateRange, employeeIdApproval, mode));
//		}
		dto.setLockHist(Pair.of(closureDtos, lockHistMap(companyId, employeeIds, closureDtos)));
		return dto;
	}

	public DPLockDto checkLockAll(String companyId, String employeeId, GeneralDate date) {
		DPLockDto dto = new DPLockDto();
		DateRange range = new DateRange(date, date);
		List<String> empIds = Arrays.asList(employeeId);
		List<ClosureDto> closureDtos = repo.getAllClosureDto(companyId, empIds, range);
		dto.setLockDayAndWpl(extractEmpAndRange(range, companyId, empIds, closureDtos));
		dto.setLockHist(Pair.of(closureDtos, lockHistMap(companyId, empIds, closureDtos)));
		return dto;
	}

	public boolean checkLockDay(Map<String, DatePeriod> employeeAndDateRange, int closureId, String employeeId, GeneralDate date) {
		boolean lock = false;
		if (!employeeAndDateRange.isEmpty()) {
			String idxAsString = String.valueOf(closureId);
			DatePeriod dateD = employeeAndDateRange
					.get(mergeString(employeeId, "|", idxAsString, "|", DPText.LOCK_EDIT_CELL_DAY));
			String lockD = "";
			if (dateD != null && inRange(date, dateD)) {
				lockD = mergeString("|", DPText.LOCK_EDIT_CELL_DAY);
			}

			if (!lockD.isEmpty()) {
				lock = true;
			}
		}
		return lock;
	}

	public boolean checkLockWork(Map<String, DatePeriod> employeeAndDateRange, String wplId, String employeeId,
			GeneralDate date) {
		boolean lock = false;
		if (!employeeAndDateRange.isEmpty()) {
			for (int i = 1; i <= 5; i++) {
				String idxAsString = String.valueOf(i);
				DatePeriod dateC = employeeAndDateRange
						.get(mergeString(employeeId, "|", idxAsString, "|", wplId, "|", DPText.LOCK_EDIT_CELL_WORK));
				String lockC = "";
				if (dateC != null && inRange(date, dateC)) {
					lockC = mergeString("|", DPText.LOCK_EDIT_CELL_WORK);
				}
				if (!lockC.isEmpty()) {
					lock = true;
				}
			}
		}
		return lock;
	}

	public boolean lockHist(Pair<List<ClosureDto>, Map<Integer, DatePeriod>> empHist, String employeeId,
			GeneralDate date) {
		Integer closureId = empHist.getLeft().stream()
				.filter(x -> x.getSid().equals(employeeId) && inRange(date, x.getDatePeriod()))
				.map(x -> x.getClosureId()).findFirst().orElse(null);
		if (closureId == null)
			return false;
		val datePeriod = empHist.getRight().get(closureId);
		if (datePeriod != null && date.after(datePeriod.end()))
			return false;
		if (datePeriod != null && (date.afterOrEquals(datePeriod.start()) && date.beforeOrEquals(datePeriod.end())))
			return false;
		return true;
	}

	public String mergeString(String... x) {
		return StringUtils.join(x);
	}

	public boolean inRange(GeneralDate date, DatePeriod dateM) {
		return date.afterOrEquals(dateM.start()) && date.beforeOrEquals(dateM.end());
	}

	private DatePeriod periodLock(DatePeriod dateEmp, DatePeriod dateCls) {
		GeneralDate startDateResult = dateEmp.start();
		GeneralDate endDateResult = dateEmp.end();
		if (dateEmp.start().beforeOrEquals(dateCls.start())) {
			startDateResult = dateCls.start();
		}

		if (dateEmp.end().afterOrEquals(dateCls.end())) {
			endDateResult = dateCls.end();
		}

		return new DatePeriod(startDateResult, endDateResult);
	}
}
