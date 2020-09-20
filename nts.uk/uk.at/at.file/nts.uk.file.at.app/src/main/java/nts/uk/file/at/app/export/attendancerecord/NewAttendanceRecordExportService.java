package nts.uk.file.at.app.export.attendancerecord;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.inject.Inject;

import nts.arc.error.BundledBusinessException;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.file.export.ExportService;
import nts.arc.layer.app.file.export.ExportServiceContext;
import nts.arc.task.data.TaskDataSetter;
import nts.arc.task.parallel.ManagedParallelWithContext;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.arc.time.calendar.period.YearMonthPeriod;
import nts.uk.ctx.at.function.dom.attendancerecord.export.AttendanceRecordExportRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSetting;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.AttendanceRecordExportSettingRepository;
import nts.uk.ctx.at.function.dom.attendancerecord.export.setting.ItemSelectionType;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecord;
import nts.uk.ctx.at.function.dom.attendancerecord.item.CalculateAttendanceRecordRepositoty;
import nts.uk.ctx.at.function.dom.attendancerecord.item.SingleAttendanceRecordRepository;
import nts.uk.ctx.at.function.dom.attendancetype.AttendanceTypeRepository;
import nts.uk.ctx.at.function.dom.dailyworkschedule.scrA.SEmpHistExportAdapter;
import nts.uk.ctx.at.function.dom.dailyworkschedule.scrA.SEmpHistExportImported;
import nts.uk.ctx.at.record.app.service.attendanceitem.value.AttendanceItemValueService;
import nts.uk.ctx.at.record.app.service.attendanceitem.value.AttendanceItemValueService.MonthlyAttendanceItemValueResult;
import nts.uk.ctx.at.record.dom.workrecord.manageactualsituation.approval.monthly.MonthlyApprovalProcess;
import nts.uk.ctx.at.record.dom.workrecord.managectualsituation.ApprovalStatus;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ApprovalProcess;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ApprovalProcessRepository;
import nts.uk.ctx.at.request.dom.application.common.adapter.workplace.WorkplaceAdapter;
import nts.uk.ctx.at.shared.app.service.workrule.closure.ClosureEmploymentService;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSettingRepository;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeRepository;
import nts.uk.ctx.bs.company.dom.company.CompanyRepository;
import nts.uk.ctx.bs.employee.dom.workplace.master.WorkplaceInformationRepository;
import nts.uk.ctx.bs.employee.pub.company.StatusOfEmployee;
import nts.uk.ctx.bs.employee.pub.company.SyCompanyPub;
import nts.uk.file.at.app.export.attendancerecord.data.AttendanceRecordReportEmployeeData;
import nts.uk.file.at.app.export.schedule.FileService;
import nts.uk.query.pub.employee.EmployeeInformationPub;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@TransactionAttribute(TransactionAttributeType.NOT_SUPPORTED)
public class NewAttendanceRecordExportService extends ExportService<AttendanceRecordRequest> {

	final static long UPPER_POSITION = 1;

	final static long LOWER_POSITION = 2;

	final static int PDF_MODE = 1;

	final static String ZERO = "0";

	/** The Constant MASTER_UNREGISTERED. */
	private static final String MASTER_UNREGISTERED = " マスタ未登録";

	@Inject
	private ClosureEmploymentService closureEmploymentService;

	@Inject
	private SingleAttendanceRecordRepository singleAttendanceRepo;

	@Inject
	private CalculateAttendanceRecordRepositoty calculateAttendanceRepo;

	@Inject
	private AttendanceItemValueService attendanceService;

	@Inject
	private AttendanceRecordExportSettingRepository attendanceRecExpSetRepo;

	@Inject
	private AttendanceRecordExportRepository attendanceRecExpRepo;

	@Inject
	private AttendanceRecordReportGenerator reportGenerator;

	@Inject
	private CompanyRepository companyRepo;

	@Inject
	private EmployeeInformationPub employeePub;

	@Inject
	private WorkplaceInformationRepository wplConfigInfoRepo;

	@Inject
	private WorkTypeRepository workTypeRepo;

	@Inject
	private WorkTimeSettingRepository workTimeRepo;

	@Inject
	private AttendanceTypeRepository attendanceRepo;

	@Inject
	private WorkplaceAdapter workplaceAdapter;

	@Inject
	private ManagedParallelWithContext parallel;

	@Inject
	private FileService service;

	@Inject
	private SEmpHistExportAdapter sEmpHistExportAdapter;

	@Inject
	private MonthlyApprovalProcess monthlyApprovalProcess;

	@Inject
	private ApprovalProcessRepository approvalRepo;

	@Inject
	private SyCompanyPub symCompany;

	@Override
	protected void handle(ExportServiceContext<AttendanceRecordRequest> context) {
		// get Dto
		AttendanceRecordRequest request = context.getQuery();
		AttendanceRecordOutputConditions condition = AttendanceRecordOutputConditions
				.createFromJavaType(request.getCondition());
		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();
		String layoutId = condition.getSelectionType() == ItemSelectionType.FREE_SETTING
				? condition.getFreeSettingLayoutId()
				: condition.getStandardSelectionLayoutId();
		Map<String, List<AttendanceRecordReportEmployeeData>> reportData = new LinkedHashMap<>();
		List<AttendanceRecordReportEmployeeData> attendanceRecRepEmpDataList = new ArrayList<AttendanceRecordReportEmployeeData>();
		BundledBusinessException exceptions = BundledBusinessException.newInstance();
		TaskDataSetter setter = context.getDataSetter();
		// Get layout info
		// ユーザ固有情報「出勤簿の出力条件」を取得する
		Optional<AttendanceRecordExportSetting> optionalAttendanceRecExpSet = this.attendanceRecExpSetRepo.findByCode(
				condition.getSelectionType(), companyId, Optional.of(employeeId), String.valueOf(request.getLayout()));
		if (!optionalAttendanceRecExpSet.isPresent()) {
			throw new BusinessException("msg_1141");
		}
		Set<Integer> singleId = new HashSet<>();
		Set<Integer> monthlyId = new HashSet<>();
		List<Integer> singleIdUpper = this.singleAttendanceRepo.getIdSingleAttendanceRecordByPosition(layoutId,
				UPPER_POSITION);
		// get lower-daily-singleItem list
		List<Integer> singleIdLower = this.singleAttendanceRepo.getIdSingleAttendanceRecordByPosition(layoutId,
				LOWER_POSITION);
		List<CalculateAttendanceRecord> calculateUpperDaily = this.calculateAttendanceRepo
				.getIdCalculateAttendanceRecordDailyByPosition(layoutId, UPPER_POSITION);
		List<CalculateAttendanceRecord> calculateLowerDaily = this.calculateAttendanceRepo
				.getIdCalculateAttendanceRecordDailyByPosition(layoutId, LOWER_POSITION);

		// get upper-monthly-Item list
		List<CalculateAttendanceRecord> calculateUpperMonthly = this.calculateAttendanceRepo
				.getIdCalculateAttendanceRecordMonthlyByPosition(layoutId, UPPER_POSITION);
		List<CalculateAttendanceRecord> calculateLowerMonthly = this.calculateAttendanceRepo
				.getIdCalculateAttendanceRecordMonthlyByPosition(layoutId, LOWER_POSITION);

		// Get all Daily attendance item
		singleId.addAll(singleIdUpper);
		singleId.addAll(singleIdLower);
		Stream.concat(calculateUpperDaily.stream(), calculateLowerDaily.stream()).forEach(item -> {
			if (item.getAddedItem() != null)
				singleId.addAll(item.getAddedItem());
			if (item.getSubtractedItem() != null)
				singleId.addAll(item.getSubtractedItem());
		});

		// Get all Monthly Attendanceitem
		Stream.concat(calculateUpperMonthly.stream(), calculateLowerMonthly.stream()).forEach(item -> {
			if (item.getAddedItem() != null)
				monthlyId.addAll(item.getAddedItem());
			if (item.getSubtractedItem() != null)
				singleId.addAll(item.getSubtractedItem());
		});

		// 対応する「月別実績」をすべて取得する - Param ( List <employee ID> , Period (year / month).
		// Start date ≤ year / month (YM) ≤ parameter. Period (year / month). End date ,
		YearMonthPeriod periodMonthly = new YearMonthPeriod(request.getStartDate().yearMonth(),
				request.getEndDate().yearMonth());

		List<String> empIDs = request.getEmployeeList().stream().map(e -> e.getEmployeeId())
				.collect(Collectors.toList());
		List<MonthlyAttendanceItemValueResult> monthlyValues = attendanceService.getMonthlyValueOf(empIDs,
				periodMonthly, monthlyId);

		int closureId = request.getClosureId() == 0 ? 1 : request.getClosureId();
		Optional<GeneralDate> baseDate = service.getProcessingYM(companyId, closureId);
		// 月の承認済を取得する
		MonthlyApprovalStatusAttendanceRecord monthlyApproval = new MonthlyApprovalStatusAttendanceRecord();
		Optional<ApprovalProcess> approvalProcOp = approvalRepo.getApprovalProcessById(companyId);
		monthlyValues.stream().forEach(i -> {
			ApprovalStatus approvalMonth = monthlyApprovalProcess.monthlyApprovalCheck(companyId, i.getEmployeeId(),
					i.getYearMonth().v(), i.getClosureId(), baseDate.get(), approvalProcOp, null);
			if (approvalMonth.equals(ApprovalStatus.APPROVAL)) {
				monthlyApproval.setYm(i.getYearMonth());
				monthlyApproval.setClosureId(i.getClosureId());
				monthlyApproval.setApprovedFlag(true);
				monthlyApproval.setEmployeeId(i.getEmployeeId());
				monthlyApproval.setYmd(i.getYearMonth());
			}
		});
		// 社員IDでループ - Loop by employee ID
		for (String empId : empIDs) {

			// 取得した月別実績の着目社員分をループ - Loop the acquired monthly results for the employees of
			// interest
			for (MonthlyAttendanceItemValueResult monthlyValue : monthlyValues) {

				// 月別実績の月初と月末の雇用コードをチェックする
				// TODO
				MonthlyResultCheck monthResultCheck = this.checkEmployeeCodeInMonth(employeeId, request.getStartDate(),
						request.getEndDate(), "", "");
				// 雇用取得結果：true
				if (!monthResultCheck.isEmployeeResult()) {
					// エラーリストに「社員コード」「社員名」を書き出す
				}
				if (!monthResultCheck.isCheckResult()) {
					// 雇用コードが一致しなかったのでこの月別実績は処理しない
					// →次の月別実績データの処理に移行(continue)

				}
				// 月別実績１ヶ月分の期間(YMD)と所属会社履歴の重複期間(YMD)を取得する
				// 社員の指定期間中の所属期間を取得する RequestList 588
				List<StatusOfEmployee> statusEmps = this.symCompany.GetListAffComHistByListSidAndPeriod(empIDs,
						new DatePeriod(request.getStartDate(), request.getEndDate()));
				if (!statusEmps.isEmpty()) {
					// 「日別実績」を取得する

					// 重複期間．開始年月日～重複期間．終了年月日のループ
					for (StatusOfEmployee stEmp : statusEmps) {

					}
				}

			}
		}

	}

	// 月別実績の月初と月末の雇用コードをチェックする
	private MonthlyResultCheck checkEmployeeCodeInMonth(String employeeId, GeneralDate startDate, GeneralDate endDate,
			String firstEmpCode, String lastEmpCode) {
		String companyId = AppContexts.user().companyId();
		MonthlyResultCheck monthlyResultCheck = new MonthlyResultCheck();
		// step 1
		monthlyResultCheck.setEmployeeResult(true);
		monthlyResultCheck.setCheckResult(true);
		// Imported「（就業）所属雇用履歴」を取得する (Lấy Imported「（就業）所属雇用履歴」)
		Optional<SEmpHistExportImported> empStartDate = sEmpHistExportAdapter.getSEmpHistExport(companyId, employeeId,
				startDate);
		if (!empStartDate.isPresent()) {
			monthlyResultCheck.setEmployeeResult(false);
			monthlyResultCheck.setCheckResult(true);
			return monthlyResultCheck;
		}

		// Imported「（就業）所属雇用履歴」を取得する (Lấy Imported「（就業）所属雇用履歴」)
		Optional<SEmpHistExportImported> empEndDate = sEmpHistExportAdapter.getSEmpHistExport(companyId, employeeId,
				endDate);
		if (!empEndDate.isPresent()) {
			monthlyResultCheck.setEmployeeResult(false);
			monthlyResultCheck.setCheckResult(true);
			return monthlyResultCheck;
		}
		// パラメータ「月初雇用コード」と取得した現在の月初雇用コードが同じ
		// パラメータ「月末雇用コード」と取得した現在の月末雇用コードが同じ
		if (!firstEmpCode.equals(empStartDate.get().getEmploymentCode())
				|| !lastEmpCode.equals(empEndDate.get().getEmploymentCode())) {
			monthlyResultCheck.setEmployeeResult(true);
			monthlyResultCheck.setCheckResult(false);
			return monthlyResultCheck;
		}
		return monthlyResultCheck;
	}
}
