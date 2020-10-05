package nts.uk.screen.at.app.dailyperformance.correction.mobile;

import java.math.BigDecimal;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.tuple.Pair;

import lombok.val;
import nts.arc.layer.app.cache.CacheCarrier;
import nts.arc.time.GeneralDate;
import nts.arc.time.calendar.period.DatePeriod;
import nts.uk.ctx.at.record.app.find.dailyperform.DailyRecordDto;
import nts.uk.ctx.at.record.app.find.workrecord.operationsetting.DaiPerformanceFunDto;
import nts.uk.ctx.at.record.app.find.workrecord.operationsetting.DaiPerformanceFunFinder;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.ApprovalStatusActualResult;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.ConfirmStatusActualResult;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.change.approval.ApprovalStatusActualDayChange;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.change.confirm.ConfirmStatusActualDayChange;
import nts.uk.ctx.at.record.dom.require.RecordDomRequireService;
import nts.uk.ctx.at.request.app.find.application.applicationlist.AppGroupExportDto;
import nts.uk.ctx.at.request.app.find.application.applicationlist.ApplicationExportDto;
import nts.uk.ctx.at.request.app.find.application.applicationlist.ApplicationListForScreen;
import nts.uk.ctx.at.request.dom.application.ReflectedState_New;
import nts.uk.ctx.at.schedule.dom.shift.businesscalendar.holiday.PublicHolidayRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.workinfomation.CalculationState;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.DailyAttendanceAtr;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AbsenceTenProcess;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.AnnualHolidaySetOutput;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.LeaveSetOutput;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.processten.SubstitutionHolidayOutput;
import nts.uk.screen.at.app.dailymodify.query.DailyModifyQueryProcessor;
import nts.uk.screen.at.app.dailymodify.query.DailyModifyResult;
import nts.uk.screen.at.app.dailyperformance.correction.DailyPerformanceScreenRepo;
import nts.uk.screen.at.app.dailyperformance.correction.GetDataDaily;
import nts.uk.screen.at.app.dailyperformance.correction.datadialog.CodeName;
import nts.uk.screen.at.app.dailyperformance.correction.datadialog.CodeNameType;
import nts.uk.screen.at.app.dailyperformance.correction.datadialog.DataDialogWithTypeProcessor;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ApprovalConfirmCache;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ApprovalUseSettingDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPAttendanceItem;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPCellDataDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPControlDisplayItem;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPCorrectionInitParam;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPCorrectionMenuDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPDataDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPErrorDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPErrorSettingDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DailyPerformanceCorrectionDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DailyPerformanceEmployeeDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DailyRecEditSetDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DatePeriodInfo;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DateRange;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DisplayItem;
import nts.uk.screen.at.app.dailyperformance.correction.dto.IdentityProcessUseSetDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ObjectShare;
import nts.uk.screen.at.app.dailyperformance.correction.dto.OperationOfDailyPerformanceDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.WorkInfoOfDailyPerformanceDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.cache.DPCorrectionStateParam;
import nts.uk.screen.at.app.dailyperformance.correction.dto.checkapproval.ApproveRootStatusForEmpDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.checkshowbutton.DailyPerformanceAuthorityDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.companyhist.AffComHistItemAtScreen;
import nts.uk.screen.at.app.dailyperformance.correction.dto.type.TypeLink;
import nts.uk.screen.at.app.dailyperformance.correction.dto.workplacehist.WorkPlaceHistTemp;
import nts.uk.screen.at.app.dailyperformance.correction.error.DCErrorInfomation;
import nts.uk.screen.at.app.dailyperformance.correction.lock.DPLock;
import nts.uk.screen.at.app.dailyperformance.correction.lock.DPLockDto;
import nts.uk.screen.at.app.dailyperformance.correction.searchemployee.FindAllEmployee;
import nts.uk.screen.at.app.dailyperformance.correction.text.DPText;
import nts.uk.screen.at.app.monthlyperformance.correction.dto.FormatDailyDto;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;

@Stateless
public class InitScreenMob {

	@Inject
	private DailyPerformanceScreenRepo repo;

	@Inject
	private DPCorrectionProcessorMob processor;

	@Inject
	private DailyModifyQueryProcessor dailyModifyQueryProcessor;

	@Inject
	private DPLock findLock;

	@Inject
	private ApprovalStatusActualDayChange approvalStatusActualDayChange;

	@Inject
	private ConfirmStatusActualDayChange confirmStatusActualDayChange;

	@Inject
	private ApplicationListForScreen applicationListFinder;

	@Inject
	private DataDialogWithTypeProcessor dataDialogWithTypeProcessor;

	@Inject
	private PublicHolidayRepository publicHolidayRepository;

	@Inject
	private FindAllEmployee findAllEmployee;

	@Inject
	private MonthlyPerfomanceMob monthlyPerfomanceMob;

	@Inject
	private DaiPerformanceFunFinder daiPerformanceFunFinder;
	
	@Inject
	private RecordDomRequireService requireService;

	private static final Integer[] DEVIATION_REASON = { 436, 438, 439, 441, 443, 444, 446, 448, 449, 451, 453, 454, 456,
			458, 459, 799, 801, 802, 804, 806, 807, 809, 811, 812, 814, 816, 817, 819, 821, 822 };
	private static final Map<Integer, Integer> DEVIATION_REASON_MAP = IntStream.range(0, DEVIATION_REASON.length - 1)
			.boxed().collect(Collectors.toMap(x -> DEVIATION_REASON[x], x -> x / 3 + 1));
	private static final DateTimeFormatter DATE_FORMATTER = DateTimeFormatter.ofPattern(DPText.DATE_FORMAT);

	public DailyPerformanceCorrectionDto initMOB(DPCorrectionInitParam param) {

		String NAME_EMPTY = TextResource.localize("KDW003_82");
		String NAME_NOT_FOUND = TextResource.localize("KDW003_81");
		String companyId = AppContexts.user().companyId();
		String sId = AppContexts.user().employeeId();
		DailyPerformanceCorrectionDto screenDto = new DailyPerformanceCorrectionDto();
		// List<DailyPerformanceEmployeeDto> lstEmployee = param.lstEmployee;
		Integer screenMode = param.screenMode;
		Integer displayFormat = param.displayFormat;
		DateRange dateRange = param.objectDateRange;
		String employeeID = param.employeeID;
		Integer closureId = param.closureId;

		// 起動に必要な情報の取得
		// アルゴリズム「実績修正画面で利用するフォーマットを取得する」を実行する
		OperationOfDailyPerformanceDto dailyPerformanceDto = repo.findOperationOfDailyPerformance();
		// アルゴリズム「本人確認処理の利用設定を取得する」を実行する
		Optional<IdentityProcessUseSetDto> identityProcessDtoOpt = repo.findIdentityProcessUseSet(companyId);
		screenDto.setIdentityProcessDtoOpt(identityProcessDtoOpt);
		screenDto.setIdentityProcessDto(identityProcessDtoOpt.isPresent() ? identityProcessDtoOpt.get()
				: new IdentityProcessUseSetDto(false, false, null));
		Optional<ApprovalUseSettingDto> approvalUseSettingDtoOpt = repo.findApprovalUseSettingDto(companyId);
		screenDto.setApprovalUseSettingDtoOpt(approvalUseSettingDtoOpt);
		processor.setHideCheckbox(screenDto, identityProcessDtoOpt, approvalUseSettingDtoOpt, companyId, screenMode);

		// 保持パラメータを生成する
		Pair<Integer, DateRange> resultIndentityPeriod = processor.identificationPeriod(closureId, screenMode, dateRange);
		screenDto.setClosureId(resultIndentityPeriod.getLeft());
		DateRange rangeInit = resultIndentityPeriod.getRight();

		// 期間を変更する
		DatePeriodInfo resultPeriod = processor.changeDateRange(dateRange, rangeInit, null, companyId, sId, screenDto, screenDto.getClosureId(), screenMode, displayFormat, false, param.dpStateParam);
		if(resultPeriod == null) {
			screenDto.setErrorInfomation(DCErrorInfomation.NOT_EMP_IN_HIST.value);
			return screenDto;
		}
		dateRange = resultPeriod.getTargetRange();
		screenDto.setDateRange(dateRange);
		screenDto.setPeriodInfo(resultPeriod);

		// 対象社員の特定
		List<String> allIds = new ArrayList<>();
		List<String> changeEmployeeIds = new ArrayList<>();

		allIds = processor.changeListEmployeeId(new ArrayList<>(), rangeInit, screenMode, false,
				screenDto.getClosureId(), screenDto);

		// ログイン社員の日別実績の権限を取得する
		screenDto.setAuthorityDto(processor.getAuthority(screenDto));

		screenDto.setLstEmployee(findAllEmployee.findAllEmployee(allIds, dateRange.getEndDate()));

		List<DailyPerformanceEmployeeDto> lstEmployeeData = new ArrayList<>();
		if (displayFormat == 0) {
			changeEmployeeIds.add(employeeID);
			lstEmployeeData = findAllEmployee.findAllEmployee(changeEmployeeIds, dateRange.getEndDate());			
		} else {
			changeEmployeeIds = allIds;
			lstEmployeeData = screenDto.getLstEmployee();
		}

		screenDto.setLstData(processor.getListData(lstEmployeeData, dateRange, displayFormat));

		Map<String, WorkPlaceHistTemp> WPHMap = repo.getWplByListSidAndPeriod(companyId, changeEmployeeIds,
				screenDto.getDateRange().getEndDate());
		screenDto.getLstEmployee().stream().map(x -> {
			val wph = WPHMap.get(x.getId());
			x.setWorkplaceName(wph == null ? "" : wph.getName());
			return x;
		}).collect(Collectors.toList());

		Map<String, List<AffComHistItemAtScreen>> affCompanyMap = repo
				.getAffCompanyHistoryOfEmployee(AppContexts.user().companyId(), changeEmployeeIds);
		screenDto.setLstData(processor.setWorkPlace(WPHMap, affCompanyMap, screenDto.getLstData()));

		List<DPDataDto> listData = new ArrayList<>();
		for (String employeeId : changeEmployeeIds) {
			screenDto.getLstData().stream().forEach(item -> {
				if (item.getEmployeeId().equals(employeeId)) {
					listData.add(item);
				}
			});
		}
		screenDto.setChangeEmployeeIds(changeEmployeeIds);

		screenDto.setLstData(
				displayFormat == 1
						? listData.stream().sorted((x, y) -> x.getEmployeeCode().compareTo(y.getEmployeeCode()))
								.collect(Collectors.toList())
						: listData);

		List<String> listEmployeeId = screenDto.getLstData().stream().map(e -> e.getEmployeeId())
				.collect(Collectors.toSet()).stream().collect(Collectors.toList());
		if (listEmployeeId.isEmpty()) {
			// screenDto.setLstEmployee(Collections.emptyList());
			screenDto.setErrorInfomation(DCErrorInfomation.NOT_EMP_IN_HIST.value);
			setStateParam(screenDto, resultPeriod, displayFormat, false);
			return screenDto;
		}

		// フォーマットの特定（スマホ）
		// 表示項目を制御する（スマホ）
		DisplayItem disItem = processor.getDisplayItems(null, new ArrayList<>(), companyId, screenDto, listEmployeeId,
				false, dailyPerformanceDto);
		if (disItem == null || !disItem.getErrors().isEmpty()) {
			if (disItem != null)
				screenDto.setErrors(disItem.getErrors());
			setStateParam(screenDto, resultPeriod, displayFormat, false);
			return screenDto;
		}

		// 日次項目の取得
		// 日別実績の取得
		List<DailyModifyResult> results = new ArrayList<>();
		Pair<List<DailyModifyResult>, List<DailyRecordDto>> resultPair = new GetDataDaily(listEmployeeId, dateRange,
				disItem.getLstAtdItemUnique(), dailyModifyQueryProcessor).getAllData();
		results = resultPair.getLeft();
		screenDto.setDomainOld(resultPair.getRight());
		screenDto.getItemValues().addAll(results.isEmpty() ? new ArrayList<>() : results.get(0).getItems());
		Map<String, DailyModifyResult> resultDailyMap = results.stream().collect(Collectors.toMap(
				x -> mergeString(x.getEmployeeId(), "|", x.getDate().toString()), Function.identity(), (x, y) -> x));
		
		if (displayFormat == 1) {
			listEmployeeId = new ArrayList<>();
			listEmployeeId = resultDailyMap.keySet().stream().map(e -> (e.substring(0, e.indexOf("|")))).collect(Collectors.toList());
		}
				
		DPControlDisplayItem dPControlDisplayItem = processor.getItemIdNames(disItem, false);
		screenDto.setLstControlDisplayItem(dPControlDisplayItem);
		screenDto.setAutBussCode(disItem.getAutBussCode());

		Map<Integer, DPAttendanceItem> mapDP = screenDto.getLstControlDisplayItem().getMapDPAttendance();
		// disable cell
		screenDto.markLoginUser(sId);
		screenDto.createAccessModifierCellState(mapDP);

		// set enable menu button
		DPCorrectionMenuDto dPCorrectionMenuDto = this.setMenuItem(disItem.getAutBussCode(),
				screenDto.getAuthorityDto(), employeeID, displayFormat);
		screenDto.setDPCorrectionMenuDto(dPCorrectionMenuDto);

		

		// ロック状態を更新する
		DPLockDto dpLockDto = findLock.checkLockAll(companyId, listEmployeeId, dateRange, sId, screenMode,
				identityProcessDtoOpt, approvalUseSettingDtoOpt);

		// 確認、承認状況の取得
		List<ConfirmStatusActualResult> confirmResults = new ArrayList<>();
		List<ApprovalStatusActualResult> approvalResults = new ArrayList<>();

		confirmResults = confirmStatusActualDayChange.processConfirmStatus(companyId, sId, listEmployeeId,
				Optional.of(new DatePeriod(dateRange.getStartDate(), dateRange.getEndDate())), Optional.empty());

		approvalResults = approvalStatusActualDayChange.processApprovalStatus(companyId, sId, listEmployeeId,
				Optional.of(new DatePeriod(dateRange.getStartDate(), dateRange.getEndDate())), Optional.empty(),
				screenMode);

		// screenDto.setApprovalConfirmCache(new ApprovalConfirmCache(sId,
		// listEmployeeId,
		// new DatePeriod(dateRange.getStartDate(), dateRange.getEndDate()), screenMode,
		// confirmResults,
		// approvalResults));

		Map<Pair<String, GeneralDate>, ConfirmStatusActualResult> mapConfirmResult = confirmResults.stream().
				collect(Collectors.toMap(x ->Pair.of(x.getEmployeeId(), x.getDate()), x -> x, (x, y) -> x));
		Map<Pair<String, GeneralDate>, ApprovalStatusActualResult> mapApprovalResults = approvalResults.stream()
				.collect(Collectors.toMap(x -> Pair.of(x.getEmployeeId(), x.getDate()), x -> x, (x, y) -> x));

		List<WorkInfoOfDailyPerformanceDto> workInfoOfDaily = repo.getListWorkInfoOfDailyPerformance(listEmployeeId,
				dateRange);

		// set error, alarm
		screenDto.setDPErrorDto(processor.getErrorList(screenDto, listEmployeeId));
		List<DPErrorDto> lstError = screenDto.getDPErrorDto();
		if (screenDto.getLstEmployee().size() > 0) {
			if (lstError.size() > 0) {
				// Get list error setting
				long timeT = System.currentTimeMillis();
				List<DPErrorSettingDto> lstErrorSetting = this.repo.getErrorSetting(companyId,
						lstError.stream().map(e -> e.getErrorCode()).collect(Collectors.toList()), true, true, false);
				// Seperate Error and Alarm
				if (lstErrorSetting.isEmpty()) {
					lstError = new ArrayList<>();
				}
				System.out.println("time load Error: " + (System.currentTimeMillis() - timeT));
				screenDto.addErrorToResponseData(lstError, lstErrorSetting, mapDP, false);
			}
		}
		// 提出済の申請の取得
		Map<String, Boolean> disableSignMap = new HashMap<>();
		Map<String, String> appMapDateSid = getApplication(listEmployeeId, dateRange, disableSignMap);

		// 実績の表示（スマホ）
		// マスタの取得
		Set<Integer> types = dPControlDisplayItem.getLstAttendanceItem() == null ? new HashSet<>()
				: dPControlDisplayItem.getLstAttendanceItem().stream().map(x -> x.getTypeGroup()).filter(x -> x != null)
						.collect(Collectors.toSet());
		Map<Integer, Map<String, CodeName>> mapGetName = dataDialogWithTypeProcessor
				.getAllCodeName(new ArrayList<>(types), companyId, dateRange.getEndDate());
		CodeNameType codeNameReason = dataDialogWithTypeProcessor.getReason(companyId);
		Map<String, CodeName> codeNameReasonMap = codeNameReason != null
				? codeNameReason.getCodeNames().stream()
						.collect(Collectors.toMap(x -> mergeString(x.getCode(), "|", x.getId()), x -> x))
				: Collections.emptyMap();

		List<GeneralDate> holidayDate = publicHolidayRepository
				.getpHolidayWhileDate(companyId, dateRange.getStartDate(), dateRange.getEndDate()).stream()
				.map(x -> x.getDate()).collect(Collectors.toList());
		// 社員の締めをチェックする
		// Map<String, List<EmploymentHisOfEmployeeImport>> mapClosingEmpResult =
		// checkClosingEmployee
		// .checkClosingEmployee(companyId, changeEmployeeIds,
		// new DatePeriod(dateRange.getStartDate(), dateRange.getEndDate()),
		// screenDto.getClosureId());

		List<DailyRecEditSetDto> dailyRecEditSets = repo.getDailyRecEditSet(listEmployeeId, dateRange);
		Map<String, Integer> dailyRecEditSetsMap = dailyRecEditSets.stream()
				.collect(Collectors.toMap(x -> mergeString(String.valueOf(x.getAttendanceItemId()), "|",
						x.getEmployeeId(), "|", converDateToString(x.getProcessingYmd())), x -> x.getEditState()));

		List<DPDataDto> lstData = new ArrayList<DPDataDto>();
		Map<String, ItemValue> itemValueMap = new HashMap<>();

		for (DPDataDto data : screenDto.getLstData()) {
			boolean textColorSpr = false;
			data.setEmploymentCode(screenDto.getEmploymentCode());
			if (!sId.equals(data.getEmployeeId())) {
				screenDto.setCellSate(data.getId(), DPText.LOCK_APPLICATION, DPText.STATE_DISABLE);
			}
			// map name submitted into cell
			if (appMapDateSid.containsKey(data.getEmployeeId() + "|" + data.getDate())) {
				data.addCellData(new DPCellDataDto(DPText.COLUMN_SUBMITTED,
						appMapDateSid.get(data.getEmployeeId() + "|" + data.getDate()), "", ""));
			} else {
				data.addCellData(new DPCellDataDto(DPText.COLUMN_SUBMITTED, "", "", ""));
			}
			data.addCellData(new DPCellDataDto(DPText.COLUMN_SUBMITTED, "", "", ""));
			data.addCellData(new DPCellDataDto(DPText.LOCK_APPLICATION, "", "", ""));
			data.addCellData(new DPCellDataDto(DPText.LOCK_APPLICATION_LIST, "", "", ""));

			// set checkbox sign
			ConfirmStatusActualResult dataSign = mapConfirmResult.get(Pair.of(data.getEmployeeId(), data.getDate()));
			data.setSign(dataSign == null ? false : dataSign.isStatus());
			// state check box sign
			// boolean disableSignApp = disableSignMap.containsKey(data.getEmployeeId() + "|" + data.getDate()) && 
					// disableSignMap.get(data.getEmployeeId() + "|" + data.getDate());

			ApprovalStatusActualResult dataApproval = mapApprovalResults
					.get(Pair.of(data.getEmployeeId(), data.getDate()));
			// set checkbox approval
			// data.setApproval(dataApproval == null ? false
			// : screenMode == ScreenMode.NORMAL.value ? dataApproval.isStatusNormal() :
			// dataApproval.isStatus());
			ApproveRootStatusForEmpDto approvalCheckMonth = dpLockDto.getLockCheckMonth()
					.get(data.getEmployeeId() + "|" + data.getDate());

			DailyModifyResult resultOfOneRow = getRow(resultDailyMap, data.getEmployeeId(), data.getDate());
			boolean lockDaykWpl = false, lockDay = false, lockWpl = false, lockHist = false, lockApprovalMonth = false,
					lockConfirmMonth = false;
			if (resultOfOneRow != null && (displayFormat == 2 ? !data.getError().equals("") : true)) {
				// set disable and lock
				processor.lockDataCheckbox(sId, screenDto, data, identityProcessDtoOpt, approvalUseSettingDtoOpt,
						screenMode, data.isApproval(), data.isSign());

				lockDay = processor.checkLockDay(dpLockDto.getLockDayAndWpl(), data);
				lockWpl = processor.checkLockWork(dpLockDto.getLockDayAndWpl(), data);
				lockHist = processor.lockHist(dpLockDto.getLockHist(), data);
				lockApprovalMonth = approvalCheckMonth == null ? false : approvalCheckMonth.isCheckApproval();
				lockConfirmMonth = processor.checkLockConfirmMonth(dpLockDto.getLockConfirmMonth(), data);
				lockDaykWpl = lockDay || lockWpl;
				lockDaykWpl = processor.lockAndDisable(screenDto, data, screenMode, lockDaykWpl,
						dataApproval == null ? false : dataApproval.isStatusNormal(), lockHist, data.isSign(),
						lockApprovalMonth, lockConfirmMonth);

				if (!textColorSpr) {
					setTextColorDay(screenDto, data.getDate(), "date", data.getId(), holidayDate);
				}
				itemValueMap = resultOfOneRow.getItems().stream()
						.collect(Collectors.toMap(x -> mergeString(String.valueOf(x.getItemId()), "|",
								data.getEmployeeId(), "|", data.getDate().toString()), x -> x));
				processCellData(NAME_EMPTY, NAME_NOT_FOUND, screenDto, dPControlDisplayItem, mapGetName,
						codeNameReasonMap, itemValueMap, data, lockDaykWpl, dailyRecEditSetsMap, null);
				lstData.add(data);
				Optional<WorkInfoOfDailyPerformanceDto> optWorkInfoOfDailyPerformanceDto = workInfoOfDaily.stream()
						.filter(w -> w.getEmployeeId().equals(data.getEmployeeId())
								&& w.getYmd().equals(data.getDate()))
						.findFirst();
				if (optWorkInfoOfDailyPerformanceDto.isPresent()
						&& optWorkInfoOfDailyPerformanceDto.get().getState() == CalculationState.No_Calculated)
					screenDto.setAlarmCellForFixedColumn(data.getId(), displayFormat);
			}
			if(lockDay || lockHist || dataSign == null || (!dataSign.isStatus() ? (!dataSign.notDisableForConfirm() ? true : false) : !dataSign.notDisableForConfirm())){
				screenDto.setCellSate(data.getId(), DPText.LOCK_SIGN, DPText.STATE_DISABLE);
			}
		}
		screenDto.setLstData(lstData);
		setStateParam(screenDto, resultPeriod, displayFormat, false);
		screenDto.setApprovalConfirmCache(new ApprovalConfirmCache(sId, listEmployeeId,
				new DatePeriod(dateRange.getStartDate(), dateRange.getEndDate()), 0, confirmResults, approvalResults));
		return screenDto;
	}

	private DPCorrectionMenuDto setMenuItem(Set<String> formatCode, List<DailyPerformanceAuthorityDto> authorityDtos,
			String employeeID, Integer displayFormat) {

		// 一括確認ボタン表示チェック
		Boolean allConfirmButtonDis = false;
		// エラー参照ボタン表示チェック
		Boolean errorReferButtonDis = false;
		// 休暇残数の参照ボタン表示チェック
		Boolean restReferButtonDis = false;
		// 月別実績の参照ボタン表示チェック
		Boolean monthActualReferButtonDis = false;
		// 時間外超過の参照ボタン表示チェック
		Boolean timeExcessReferButtonDis = false;

		String companyId = AppContexts.user().companyId();
		String sId = AppContexts.user().employeeId();
		// 休暇管理状況をチェックする
		// 10-1.年休の設定を取得する
		AnnualHolidaySetOutput annualHd = AbsenceTenProcess.getSettingForAnnualHoliday(
				requireService.createRequire(), companyId);
		// 10-2.代休の設定を取得する
		SubstitutionHolidayOutput subHd = AbsenceTenProcess.getSettingForSubstituteHoliday(
				requireService.createRequire(), new CacheCarrier(), companyId, sId,
				GeneralDate.today());
		// 10-3.振休の設定を取得する
		LeaveSetOutput leaveSet = AbsenceTenProcess.getSetForLeave(
				requireService.createRequire(), new CacheCarrier(), companyId, sId, GeneralDate.today());
		// 10-4.積立年休の設定を取得する
		boolean isRetentionManage = AbsenceTenProcess.getSetForYearlyReserved(
				requireService.createRequire(), new CacheCarrier(), companyId, sId, GeneralDate.today());

		if ((annualHd.isYearHolidayManagerFlg() || subHd.isSubstitutionFlg() || leaveSet.isSubManageFlag()
				|| isRetentionManage) && displayFormat == 0) {
			restReferButtonDis = true;
		}

		OperationOfDailyPerformanceDto dailyPerDto = repo.findOperationOfDailyPerformance();
		List<FormatDailyDto> formatDailyDto = monthlyPerfomanceMob.getFormatCode(formatCode,
				dailyPerDto.getSettingUnit(), companyId);
		monthActualReferButtonDis = formatDailyDto != null && !formatDailyDto.isEmpty() && displayFormat == 0 ? true : false;

		DaiPerformanceFunDto daiPerformanceFunDto = daiPerformanceFunFinder.getDaiPerformanceFunById(companyId);
		timeExcessReferButtonDis = daiPerformanceFunDto.getDisp36Atr() == 1 && displayFormat == 0 ? true : false;

		Optional<DailyPerformanceAuthorityDto> authorityDto = authorityDtos.stream().filter(x -> x.getFunctionNo().compareTo(new BigDecimal(25)) == 0).findFirst();
		if (authorityDto.isPresent()) {
			allConfirmButtonDis = authorityDto.get().isAvailability() && displayFormat == 0 && sId.equals(employeeID) ? true : false;
		}
		authorityDto = authorityDtos.stream().filter(x -> x.getFunctionNo().compareTo(new BigDecimal(24)) == 0).findFirst();
		if (authorityDto.isPresent()) {
			errorReferButtonDis = authorityDto.get().isAvailability() ? true : false;
		}

		return new DPCorrectionMenuDto(allConfirmButtonDis, errorReferButtonDis, restReferButtonDis,
				monthActualReferButtonDis, timeExcessReferButtonDis);
	}

	public String mergeString(String... x) {
		return StringUtils.join(x);
	}

	public Map<String, String> getApplication(List<String> listEmployeeId, DateRange dateRange,
			Map<String, Boolean> disableSignMap) {
		// No 20 get submitted application
		Map<String, String> appMapDateSid = new HashMap<>();
		// requestlist26
		List<ApplicationExportDto> appplicationDisable = listEmployeeId.isEmpty() ? Collections.emptyList()
				: applicationListFinder.getApplicationBySID(listEmployeeId, dateRange.getStartDate(),
						dateRange.getEndDate());
		// requestlist542
		List<AppGroupExportDto> appplicationName = listEmployeeId.isEmpty() ? Collections.emptyList()
				: applicationListFinder.getApplicationGroupBySID(listEmployeeId, dateRange.getStartDate(),
						dateRange.getEndDate());
		appplicationName.forEach(x -> {
			String key = x.getEmployeeID() + "|" + x.getAppDate();
			if (appMapDateSid.containsKey(key)) {
				appMapDateSid.put(key, appMapDateSid.get(key) + "  " + x.getAppTypeName());
			} else {
				appMapDateSid.put(key, x.getAppTypeName());
			}
		});

		appplicationDisable.forEach(x -> {
			String key = x.getEmployeeID() + "|" + x.getAppDate();
			if (disableSignMap != null) {
				boolean disable = (x.getReflectState() == ReflectedState_New.NOTREFLECTED.value
						|| x.getReflectState() == ReflectedState_New.REMAND.value)
						&& x.getAppType() != nts.uk.ctx.at.request.dom.application.ApplicationType.OVER_TIME_APPLICATION.value;
				if (disableSignMap.containsKey(key)) {
					disableSignMap.put(key, disableSignMap.get(key) || disable);
				} else {
					disableSignMap.put(key, disable);
				}
			}
		});
		return appMapDateSid;
	}

	public DailyModifyResult getRow(Map<String, DailyModifyResult> resultDailyMap, String sId, GeneralDate date) {
		return resultDailyMap.isEmpty() ? null : resultDailyMap.get(mergeString(sId, "|", date.toString()));
	}

	public void setTextColorDay(DailyPerformanceCorrectionDto screenDto, GeneralDate date, String columnKey,
			String rowId, List<GeneralDate> holidayDates) {

		boolean isHoliday = holidayDates.contains(date);
		if (isHoliday || date.dayOfWeek() == 7) {
			// Sunday
			screenDto.setCellSate(rowId, columnKey, DPText.COLOR_SUN);
		} else if (date.dayOfWeek() == 6) {
			// Saturday
			screenDto.setCellSate(rowId, columnKey, DPText.COLOR_SAT);
		}
	}

	public void processCellData(String NAME_EMPTY, String NAME_NOT_FOUND, DailyPerformanceCorrectionDto screenDto,
			DPControlDisplayItem dPControlDisplayItem, Map<Integer, Map<String, CodeName>> mapGetName,
			Map<String, CodeName> mapReasonName, Map<String, ItemValue> itemValueMap, DPDataDto data, boolean lock,
			Map<String, Integer> dailyRecEditSetsMap, ObjectShare share) {
		Set<DPCellDataDto> cellDatas = data.getCellDatas();
		String typeGroup = "";
		Integer cellEdit;
		if (dPControlDisplayItem.getLstAttendanceItem() != null) {
			for (DPAttendanceItem item : dPControlDisplayItem.getLstAttendanceItem()) {
				// DPAttendanceItem dpAttenItem1 = mapDP.get(item.getId());
				String itemIdAsString = item.getId().toString();
				// int a = 1;
				int attendanceAtr = item.getAttendanceAtr();
				String attendanceAtrAsString = String.valueOf(item.getAttendanceAtr());
				Integer groupType = item.getTypeGroup();
				String key = mergeString(itemIdAsString, "|", data.getEmployeeId(), "|" + data.getDate().toString());
				String value = itemValueMap.get(key) != null && itemValueMap.get(key).value() != null
						? itemValueMap.get(key).value().toString()
						: "";
				cellEdit = dailyRecEditSetsMap.get(mergeString(itemIdAsString, "|", data.getEmployeeId(),
						"|" + converDateToString(data.getDate())));

				if (attendanceAtr == DailyAttendanceAtr.Code.value
						|| attendanceAtr == DailyAttendanceAtr.Classification.value) {
					String nameColKey = mergeString(DPText.NAME, itemIdAsString);
					if (attendanceAtr == DailyAttendanceAtr.Code.value) {
						String codeColKey = mergeString(DPText.CODE, itemIdAsString);
						typeGroup = typeGroup
								+ mergeString(String.valueOf(item.getId()), ":", String.valueOf(groupType), "|");
						if (lock) {
							screenDto.setCellSate(data.getId(), codeColKey, DPText.STATE_DISABLE, true);
							screenDto.setCellSate(data.getId(), nameColKey, DPText.STATE_DISABLE, true);
						}
						if (value.isEmpty() || value.equals("null")) {
							cellDatas.add(new DPCellDataDto(mergeString(DPText.CODE, itemIdAsString), "",
									attendanceAtrAsString, DPText.TYPE_LABEL));
							value = NAME_EMPTY;
						} else {
							if (groupType != null) {
								if (groupType == TypeLink.WORKPLACE.value || groupType == TypeLink.POSSITION.value) {
//									Optional<CodeName> optCodeName = dataDialogWithTypeProcessor
//											.getCodeNameWithId(groupType, data.getDate(), value);
									val mapCodeNameAll = mapGetName.get(groupType).get(value);
									cellDatas.add(new DPCellDataDto(codeColKey,
											mapCodeNameAll != null ? mapCodeNameAll.getCode() : value,
											attendanceAtrAsString, DPText.TYPE_LABEL));
									value = mapCodeNameAll == null ? NAME_NOT_FOUND : mapCodeNameAll.getName();
								} else if (groupType == TypeLink.REASON.value) {
									int group = DEVIATION_REASON_MAP.get(item.getId());
									cellDatas.add(new DPCellDataDto(codeColKey, value, attendanceAtrAsString,
											DPText.TYPE_LABEL));
									value = mapReasonName.containsKey(value + "|" + group)
											? mapReasonName.get(value + "|" + group).getName()
											: NAME_NOT_FOUND;
								} else {
									cellDatas.add(new DPCellDataDto(codeColKey, value, attendanceAtrAsString,
											DPText.TYPE_LABEL));
									value = mapGetName.get(groupType).containsKey(value)
											? mapGetName.get(groupType).get(value).getName()
											: NAME_NOT_FOUND;
								}
							}
						}
						cellDatas.add(new DPCellDataDto(nameColKey, value, attendanceAtrAsString, DPText.TYPE_LINK));
						// set color edit
						cellEditColor(screenDto, data.getId(), nameColKey, cellEdit);
						cellEditColor(screenDto, data.getId(), codeColKey, cellEdit);
					} else {
						String noColKey = mergeString(DPText.NO, itemIdAsString);
						if (lock) {
							screenDto.setCellSate(data.getId(), noColKey, DPText.STATE_DISABLE, true);
							screenDto.setCellSate(data.getId(), nameColKey, DPText.STATE_DISABLE, true);
						}
						cellDatas.add(new DPCellDataDto(noColKey, Integer.parseInt(value), attendanceAtrAsString,
								DPText.TYPE_LABEL));
						cellDatas.add(new DPCellDataDto(nameColKey, Integer.parseInt(value), attendanceAtrAsString,
								DPText.TYPE_LINK));
						cellEditColor(screenDto, data.getId(), nameColKey, cellEdit);
						cellEditColor(screenDto, data.getId(), noColKey, cellEdit);
					}

				} else {
					String anyChar = mergeString(DPText.ADD_CHARACTER, itemIdAsString);
					// set color edit
					cellEditColor(screenDto, data.getId(), anyChar, cellEdit);
					if (lock) {
						screenDto.setCellSate(data.getId(), anyChar, DPText.STATE_DISABLE, true);
					}
					if (attendanceAtr == DailyAttendanceAtr.Time.value
							|| attendanceAtr == DailyAttendanceAtr.TimeOfDay.value) {
						if (!value.isEmpty()) {
							// convert HH:mm
							int minute = 0;
							if (Integer.parseInt(value) >= 0) {
								minute = Integer.parseInt(value);
							} else {
								if (attendanceAtr == DailyAttendanceAtr.TimeOfDay.value) {
									minute = 0 - ((Integer.parseInt(value)
											+ (1 + -Integer.parseInt(value) / DPText.MINUTES_OF_DAY)
													* DPText.MINUTES_OF_DAY));
								} else {
									minute = Integer.parseInt(value);
								}
							}
							int hours = minute / 60;
							int minutes = Math.abs(minute) % 60;
							String valueConvert = (minute < 0 && hours == 0)
									? "-" + String.format(DPText.FORMAT_HH_MM, hours, minutes)
									: String.format(DPText.FORMAT_HH_MM, hours, minutes);
							cellDatas.add(
									new DPCellDataDto(anyChar, valueConvert, attendanceAtrAsString, DPText.TYPE_LABEL));
						} else {
							cellDatas.add(new DPCellDataDto(anyChar, value, attendanceAtrAsString, DPText.TYPE_LABEL));
						}
					} else {
						cellDatas.add(new DPCellDataDto(anyChar, value, attendanceAtrAsString, DPText.TYPE_LABEL));
					}
				}
			}
		}
		data.setTypeGroup(typeGroup);
		data.setCellDatas(cellDatas);
	}

	public String converDateToString(GeneralDate genDate) {
		return DATE_FORMATTER.format(genDate.toLocalDate());
	}

	public void cellEditColor(DailyPerformanceCorrectionDto screenDto, String rowId, String columnKey,
			Integer cellEdit) {
		// set color edit
		if (cellEdit != null) {
			if (cellEdit == 0) {
				screenDto.setCellSate(rowId, columnKey, DPText.HAND_CORRECTION_MYSELF);
			} else if (cellEdit == 1) {
				screenDto.setCellSate(rowId, columnKey, DPText.HAND_CORRECTION_OTHER);
			} else {
				screenDto.setCellSate(rowId, columnKey, DPText.REFLECT_APPLICATION);
			}
		}
	}

	private void setStateParam(DailyPerformanceCorrectionDto screenDto, DatePeriodInfo info, int displayFormat,
			Boolean transferDesScreen) {
		DPCorrectionStateParam cacheParam = new DPCorrectionStateParam(
				new DatePeriod(screenDto.getDateRange().getStartDate(), screenDto.getDateRange().getEndDate()),
				screenDto.getEmployeeIds(), displayFormat, screenDto.getEmployeeIds(),
				screenDto.getLstControlDisplayItem(), info, transferDesScreen);
		screenDto.setStateParam(cacheParam);

	}
}
