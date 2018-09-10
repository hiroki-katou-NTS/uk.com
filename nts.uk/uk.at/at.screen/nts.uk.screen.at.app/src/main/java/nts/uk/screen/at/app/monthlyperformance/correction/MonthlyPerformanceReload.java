package nts.uk.screen.at.app.monthlyperformance.correction;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.error.BusinessException;
import nts.arc.time.YearMonth;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.record.app.find.monthly.root.common.ClosureDateDto;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffAtWorkplaceImport;
import nts.uk.ctx.at.record.dom.adapter.workplace.affiliate.AffWorkplaceAdapter;
import nts.uk.ctx.at.record.dom.workrecord.actuallock.LockStatus;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerError;
import nts.uk.ctx.at.record.dom.workrecord.erroralarm.EmployeeDailyPerErrorRepository;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.Identification;
import nts.uk.ctx.at.record.dom.workrecord.identificationstatus.repository.IdentificationRepository;
import nts.uk.ctx.at.record.dom.workrecord.managectualsituation.AcquireActualStatus;
import nts.uk.ctx.at.record.dom.workrecord.managectualsituation.ApprovalStatus;
import nts.uk.ctx.at.record.dom.workrecord.managectualsituation.EmploymentFixedStatus;
import nts.uk.ctx.at.record.dom.workrecord.managectualsituation.MonthlyActualSituationOutput;
import nts.uk.ctx.at.record.dom.workrecord.managectualsituation.MonthlyActualSituationStatus;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ApprovalProcess;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ApprovalProcessRepository;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.IdentityProcess;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.IdentityProcessRepository;
import nts.uk.ctx.at.shared.dom.adapter.jobtitle.SharedAffJobTitleHisImport;
import nts.uk.ctx.at.shared.dom.adapter.jobtitle.SharedAffJobtitleHisAdapter;
import nts.uk.ctx.at.shared.dom.workrule.closure.Closure;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureHistory;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureId;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DateRange;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.ActualTime;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.ActualTimeState;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.EditStateOfMonthlyPerformanceDto;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MPCellDataDto;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MPCellStateDto;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MPDataDto;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MonthlyPerformanceCorrectionDto;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.MonthlyPerformanceEmployeeDto;
import nts.uk.screen.at.app.monthlyperformance.correction.param.MonthlyPerformaceLockStatus;
import nts.uk.screen.at.app.monthlyperformance.correction.param.MonthlyPerformanceParam;
import nts.uk.screen.at.app.monthlyperformance.correction.param.PAttendanceItem;
import nts.uk.screen.at.app.monthlyperformance.correction.param.ReloadMonthlyPerformanceParam;
import nts.uk.screen.at.app.monthlyperformance.correction.query.MonthlyModifyQueryProcessor;
import nts.uk.screen.at.app.monthlyperformance.correction.query.MonthlyModifyResult;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class MonthlyPerformanceReload {

	@Inject
	private ClosureRepository closureRepository;

	@Inject
	private ClosureService closureService;

	@Inject
	private MonthlyModifyQueryProcessor monthlyModifyQueryProcessor;

	@Inject
	private AffWorkplaceAdapter affWorkplaceAdapter;

	@Inject
	private MonthlyActualSituationStatus monthlyActualStatus;

	@Inject
	private MonthlyPerformanceCheck monthlyCheck;

	@Inject
	private MonthlyPerformanceScreenRepo repo;
	
	private static final String ADD_CHARACTER = "A";
	private static final String STATE_DISABLE = "ntsgrid-disable";
	private static final String HAND_CORRECTION_MYSELF = "ntsgrid-manual-edit-target";
	private static final String HAND_CORRECTION_OTHER = "ntsgrid-manual-edit-other";

	public MonthlyPerformanceCorrectionDto reloadScreen(ReloadMonthlyPerformanceParam param) {

		String companyId = AppContexts.user().companyId();
		String employeeId = AppContexts.user().employeeId();

		MonthlyPerformanceCorrectionDto screenDto = new MonthlyPerformanceCorrectionDto();

		// set processing Date
		screenDto.setProcessDate(param.getProcessDate());
		
		// set closureId
		screenDto.setClosureId(param.getClosureId());
		
		// set lstAtdItemUnique
		MonthlyPerformanceParam monthlyPerformanceParam = new MonthlyPerformanceParam();
		monthlyPerformanceParam.setLstAtdItemUnique(param.getLstAtdItemUnique());

		// アルゴリズム「締め情報の表示」を実行する
		this.displayClosure(screenDto, companyId, param.getClosureId(), param.getProcessDate());

		DateRange dateRange = new DateRange(screenDto.getSelectedActualTime().getStartDate(), screenDto.getSelectedActualTime().getEndDate());
		
		screenDto.setLstEmployee(extractEmployeeList(param.getLstEmployees(), employeeId, new DateRange(
				screenDto.getSelectedActualTime().getEndDate(), screenDto.getSelectedActualTime().getEndDate())));		
		
		List<String> employeeIds = screenDto.getLstEmployee().stream().map(e -> e.getId())
				.collect(Collectors.toList());
		
		// アルゴリズム「ロック状態をチェックする」を実行する - set lock
		List<MonthlyPerformaceLockStatus> lstLockStatus = checkLockStatus(companyId, employeeIds,
				screenDto.getProcessDate(), param.getClosureId(),
				new DatePeriod(dateRange.getStartDate(), dateRange.getEndDate()), param.getInitScreenMode());
		
		monthlyPerformanceParam.setLstLockStatus(lstLockStatus);
		monthlyPerformanceParam.setInitScreenMode(param.getInitScreenMode());
		screenDto.setParam(monthlyPerformanceParam);

		// アルゴリズム「月別実績を表示する」を実行する(Hiển thị monthly actual result)
		this.displayMonthlyResult(screenDto, param.getProcessDate(), param.getClosureId());
		screenDto.createAccessModifierCellState();
		return screenDto;
	}

	/**
	 * 締め情報の表示
	 * 
	 * @param screenDto
	 *            return result to DTO
	 * @param companyId
	 * @param closureId
	 * @param processYM
	 */
	private void displayClosure(MonthlyPerformanceCorrectionDto screenDto, String companyId, Integer closureId,
			Integer processYM) {
		// アルゴリズム「締めの名称を取得する」を実行する
		Optional<Closure> closure = closureRepository.findById(companyId, closureId);
		if (!closure.isPresent()) {
			return;
		}
		Optional<ClosureHistory> closureHis = closure.get().getHistoryByYearMonth(YearMonth.of(processYM));
		if (closureHis.isPresent()) {
			// 締め名称 → 画面項目「A4_2：対象締め日」
			screenDto.setClosureName(closureHis.get().getClosureName().v());
			screenDto.setClosureDate(ClosureDateDto.from(closureHis.get().getClosureDate()));
		}
		// アルゴリズム「実績期間の取得」を実行する
		List<ActualTime> actualTimes = closure.get().getPeriodByYearMonth(YearMonth.of(processYM)).stream()
				.map(time -> {
					return new ActualTime(time.start(), time.end());
				}).collect(Collectors.toList());
		if (CollectionUtil.isEmpty(actualTimes))
			return;
		// 実績期間 → 画面項目「A4_5：実績期間選択肢」
		screenDto.setLstActualTimes(actualTimes);
		// 実績期間の件数をチェックする
		if (actualTimes.size() == 1) {
			screenDto.setSelectedActualTime(
					new ActualTime(actualTimes.get(0).getStartDate(), actualTimes.get(0).getEndDate()));
		} else if (actualTimes.size() == 2) {
			// 当月の期間を算出する
			DatePeriod datePeriod = closureService.getClosurePeriod(closureId, new YearMonth(processYM));
			// 画面項目「A4_4：実績期間選択」の選択状態を変更する
			screenDto.setSelectedActualTime(new ActualTime(datePeriod.start(), datePeriod.end()));
		}
	}

	/**
	 * 月別実績を表示する
	 */
	private void displayMonthlyResult(MonthlyPerformanceCorrectionDto screenDto, Integer yearMonth, Integer closureId) {

		MonthlyPerformanceParam param = screenDto.getParam();

		/**
		 * Get Data
		 */
		// アルゴリズム「対象年月に対応する月別実績を取得する」を実行する Lấy monthly result ứng với năm tháng
		if (param.getLstAtdItemUnique() == null || param.getLstAtdItemUnique().isEmpty()) {
			throw new BusinessException("Msg_1261");
		}
		List<MonthlyModifyResult> results = new ArrayList<>();
		List<String> listEmployeeIds = screenDto.getLstEmployee().stream().map(e -> e.getId())
				.collect(Collectors.toList());
		List<Integer> attdanceIds = screenDto.getParam().getLstAtdItemUnique().keySet().stream()
				.collect(Collectors.toList());
		results = new GetDataMonthly(listEmployeeIds, new YearMonth(yearMonth), ClosureId.valueOf(closureId),
				screenDto.getClosureDate().toDomain(), attdanceIds, monthlyModifyQueryProcessor).call();
		if (results.size() > 0) {
			screenDto.getItemValues().addAll(results.get(0).getItems());
		}
		Map<String, MonthlyModifyResult> employeeDataMap = results.stream()
				.collect(Collectors.toMap(x -> x.getEmployeeId(), Function.identity(), (x, y) -> x));

		List<MPDataDto> lstData = new ArrayList<>(); // List all data
		List<MPCellStateDto> lstCellState = new ArrayList<>(); // List cell
																// state
		screenDto.setLstData(lstData);
		screenDto.setLstCellState(lstCellState);

		Map<String, MonthlyPerformaceLockStatus> lockStatusMap = param.getLstLockStatus().stream()
				.collect(Collectors.toMap(x -> x.getEmployeeId(), Function.identity(), (x, y) -> x));
		String employeeIdLogin = AppContexts.user().employeeId();

		List<EditStateOfMonthlyPerformanceDto> editStateOfMonthlyPerformanceDtos = this.repo
				.findEditStateOfMonthlyPer(new YearMonth(screenDto.getProcessDate()), listEmployeeIds, attdanceIds);

		for (int i = 0; i < screenDto.getLstEmployee().size(); i++) {
			MonthlyPerformanceEmployeeDto employee = screenDto.getLstEmployee().get(i);
			String employeeId = employee.getId();
			// lock check box1 identify
			if (!employeeIdLogin.equals(employeeId)) {
				lstCellState.add(new MPCellStateDto(employeeId, "identify", Arrays.asList(STATE_DISABLE)));
			}
			String lockStatus = lockStatusMap.isEmpty() || !lockStatusMap.containsKey(employee.getId()) ? ""
					: lockStatusMap.get(employee.getId()).getLockStatusString();

			MPDataDto mpdata = new MPDataDto(employeeId, lockStatus, "", employee.getCode(), employee.getBusinessName(),
					employeeId, "", false, false, "〇", "");

			List<EditStateOfMonthlyPerformanceDto> newList = editStateOfMonthlyPerformanceDtos.stream()
					.filter(item -> item.getEmployeeId().equals(employeeId)).collect(Collectors.toList());

			// Setting data for dynamic column
			MonthlyModifyResult rowData = employeeDataMap.get(employeeId);
			if (null != rowData) {
				if (null != rowData.getItems()) {
					rowData.getItems().forEach(item -> {
						// Cell Data
						String attendanceAtrAsString = String.valueOf(item.getValueType());
						String attendanceKey = mergeString(ADD_CHARACTER, "" + item.getItemId());
						PAttendanceItem pA = param.getLstAtdItemUnique().get(item.getItemId());
						List<String> cellStatus = new ArrayList<>();

						if (pA.getAttendanceAtr() == 1) {
							int minute = 0;
							if (item.getValue() != null) {
								if (Integer.parseInt(item.getValue()) >= 0) {
									minute = Integer.parseInt(item.getValue());
								} else {
									minute = (Integer.parseInt(item.getValue())
											+ (1 + -Integer.parseInt(item.getValue()) / (24 * 60)) * (24 * 60));
								}
							}
							int hours = minute / 60;
							int minutes = Math.abs(minute) % 60;
							String valueConvert = (minute < 0 && hours == 0)
									? "-" + String.format("%d:%02d", hours, minutes)
									: String.format("%d:%02d", hours, minutes);

							mpdata.addCellData(
									new MPCellDataDto(attendanceKey, valueConvert, attendanceAtrAsString, "label"));
						}
						mpdata.addCellData(new MPCellDataDto(attendanceKey,
								item.getValue() != null ? item.getValue() : "", "String", ""));
						if (!StringUtil.isNullOrEmpty(lockStatus, true)) {
							cellStatus.add(STATE_DISABLE);
						}
						// Cell Data
						lstCellState.add(new MPCellStateDto(employeeId, attendanceKey, cellStatus));

						Optional<EditStateOfMonthlyPerformanceDto> dto = newList.stream()
								.filter(item2 -> item2.getAttendanceItemId().equals(item.getItemId())).findFirst();
						if (dto.isPresent()) {
							if (dto.get().getStateOfEdit() == 0) {
								screenDto.setStateCell(attendanceKey, employeeId, HAND_CORRECTION_MYSELF);
							} else {
								screenDto.setStateCell(attendanceKey, employeeId, HAND_CORRECTION_OTHER);
							}
						}
					});
				}
			}
			lstData.add(mpdata);
		}
	}

	/**
	 * ロック状態をチェックする
	 * 
	 * @param empIds
	 *            社員一覧：List＜社員ID＞
	 * @param processDateYM
	 *            処理年月：年月
	 * @param closureId
	 *            締めID：締めID
	 * @param dateRange
	 *            締め期間：期間
	 * 
	 *            ロック状態一覧：List＜月の実績のロック状態＞
	 */
	@Inject
	private SharedAffJobtitleHisAdapter affJobTitleAdapter;
	
	@Inject
	private IdentityProcessRepository identityProcessRepo;
	
	@Inject
	private IdentificationRepository identificationRepository;
	
	@Inject 
	private EmployeeDailyPerErrorRepository employeeDailyPerErrorRepo;
	
	@Inject 
	private ApprovalProcessRepository approvalRepo;
	
	public List<MonthlyPerformaceLockStatus> checkLockStatus(String cid, List<String> empIds, Integer processDateYM,
			Integer closureId, DatePeriod closureTime, int intScreenMode) {
		List<MonthlyPerformaceLockStatus> monthlyLockStatusLst = new ArrayList<MonthlyPerformaceLockStatus>();
		// ロック解除モード の場合
		if (intScreenMode == 1) {
			return monthlyLockStatusLst;
		}
		// 社員ID（List）と基準日から所属職場IDを取得
		// 基準日：パラメータ「締め期間」の終了日
		List<AffAtWorkplaceImport> affWorkplaceLst = affWorkplaceAdapter.findBySIdAndBaseDate(empIds,
				closureTime.end());
		if (CollectionUtil.isEmpty(affWorkplaceLst)) {
			return monthlyLockStatusLst;
		}
		// 「List＜所属職場履歴項目＞」の件数ループしてください
		MonthlyPerformaceLockStatus monthlyLockStatus = null;
		
		/**
		 * Fix response kmw003
		 */
		List<SharedAffJobTitleHisImport> listShareAff = affJobTitleAdapter.findAffJobTitleHisByListSid(empIds, closureTime.end());
		
		Optional<IdentityProcess> identityOp = identityProcessRepo.getIdentityProcessById(cid);
		boolean checkIdentityOp = false;
		//対応するドメインモデル「本人確認処理の利用設定」を取得する
		if(!identityOp.isPresent()) {
			checkIdentityOp = true;
		}else {
			//取得したドメインモデル「本人確認処理の利用設定．日の本人確認を利用する」チェックする
			if(identityOp.get().getUseDailySelfCk() == 0){
				checkIdentityOp = true;
			}
		}
		
		List<Identification> listIdentification = identificationRepository.findByListEmployeeID(empIds, closureTime.start(), closureTime.end());
		
		List<EmployeeDailyPerError> listEmployeeDailyPerError =  employeeDailyPerErrorRepo.finds(empIds, new DatePeriod(closureTime.start(), closureTime.end()));
		
		Optional<ApprovalProcess> approvalProcOp = approvalRepo.getApprovalProcessById(cid);
		
		for (AffAtWorkplaceImport affWorkplaceImport : affWorkplaceLst) {
			List<Identification> listIdenByEmpID = new ArrayList<>();
			for(Identification iden : listIdentification) {
				if(iden.getEmployeeId().equals(affWorkplaceImport.getEmployeeId())) {
					listIdenByEmpID.add(iden);
				}
			}
			boolean checkExistRecordErrorListDate = false;
			for(EmployeeDailyPerError employeeDailyPerError : listEmployeeDailyPerError) {
				if(employeeDailyPerError.getEmployeeID().equals(affWorkplaceImport.getEmployeeId())) {
					checkExistRecordErrorListDate = true;
					break;
				}
			}
			
			
			// 月の実績の状況を取得する
			AcquireActualStatus param = new AcquireActualStatus(cid, affWorkplaceImport.getEmployeeId(), processDateYM,
					closureId, closureTime.end(), closureTime, affWorkplaceImport.getWorkplaceId());
			MonthlyActualSituationOutput monthlymonthlyActualStatusOutput = monthlyActualStatus
					.getMonthlyActualSituationStatus(param,approvalProcOp,listShareAff,checkIdentityOp,listIdenByEmpID,checkExistRecordErrorListDate);
			// Output「月の実績の状況」を元に「ロック状態一覧」をセットする
			monthlyLockStatus = new MonthlyPerformaceLockStatus(
					monthlymonthlyActualStatusOutput.getEmployeeClosingInfo().getEmployeeId(),
					// TODO
					LockStatus.UNLOCK,
					// 職場の就業確定状態
					monthlymonthlyActualStatusOutput.getEmploymentFixedStatus().equals(EmploymentFixedStatus.CONFIRM)
							? LockStatus.LOCK : LockStatus.UNLOCK,
					// 月の承認状況
					monthlymonthlyActualStatusOutput.getApprovalStatus().equals(ApprovalStatus.APPROVAL)
							? LockStatus.LOCK : LockStatus.UNLOCK,
					// 月別実績のロック状態
					monthlymonthlyActualStatusOutput.getMonthlyLockStatus(),
					// 本人確認が完了している
					monthlymonthlyActualStatusOutput.getDailyActualSituation().isIdentificationCompleted()
							? LockStatus.UNLOCK : LockStatus.LOCK,
					// 日の実績が存在する
					monthlymonthlyActualStatusOutput.getDailyActualSituation().isDailyAchievementsExist()
							? LockStatus.UNLOCK : LockStatus.LOCK,
					// TODO
					LockStatus.UNLOCK);
			monthlyLockStatusLst.add(monthlyLockStatus);
		}
		// 過去実績の修正ロック
		LockStatus pastLockStatus = editLockOfPastResult(processDateYM, closureId,
				new ActualTime(closureTime.start(), closureTime.end()));
		// Output「ロック状態」を「ロック状態一覧.過去実績のロック」にセットする
		monthlyLockStatusLst = monthlyLockStatusLst.stream().map(item -> {
			item.setPastPerformaceLock(pastLockStatus);
			return item;
		}).collect(Collectors.toList());

		return monthlyLockStatusLst;
	}

	/**
	 * 過去実績の修正ロック
	 * 
	 * @param processDateYM
	 * @param closureId
	 * @param actualTime
	 * @return ロック状態：ロック or アンロック
	 */
	private LockStatus editLockOfPastResult(Integer processDateYM, Integer closureId, ActualTime actualTime) {
		ActualTimeState actualTimeState = monthlyCheck.checkActualTime(closureId, processDateYM, actualTime);
		if (actualTimeState.equals(ActualTimeState.Past)) {
			return LockStatus.LOCK;
		}
		return LockStatus.UNLOCK;
	}

	private String mergeString(String... x) {
		return StringUtils.join(x);
	}
	
	
	
	/**
	 * Get id of employee list.
	 */
	private List<MonthlyPerformanceEmployeeDto> extractEmployeeList(List<MonthlyPerformanceEmployeeDto> lstEmployee,
			String sId, DateRange range) {
		if (!lstEmployee.isEmpty()) {
			return lstEmployee;
		} else {
			return getListEmployee(sId, range);
		}
	}

	/** アルゴリズム「対象者を抽出する」を実行する */
	private List<MonthlyPerformanceEmployeeDto> getListEmployee(String sId, DateRange dateRange) {
		// アルゴリズム「自職場を取得する」を実行する
		// List<String> lstJobTitle = this.repo.getListJobTitle(dateRange);
		// List<String> lstEmployment = this.repo.getListEmployment();
		/// 対応するドメインモデル「所属職場」を取得する + 対応するドメインモデル「職場」を取得する
		Map<String, String> lstWorkplace = this.repo.getListWorkplace(sId, dateRange);
		// List<String> lstClassification = this.repo.getListClassification();
		// 取得したドメインモデル「所属職場．社員ID」に対応するImported「（就業）社員」を取得する
		if (lstWorkplace.isEmpty()) {
			return new ArrayList<>();
		}
		return this.repo.getListEmployee(null, null, lstWorkplace, null);
	}
}
