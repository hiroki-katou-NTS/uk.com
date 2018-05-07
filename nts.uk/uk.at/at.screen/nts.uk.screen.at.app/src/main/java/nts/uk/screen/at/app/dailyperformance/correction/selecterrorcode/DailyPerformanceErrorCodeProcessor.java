package nts.uk.screen.at.app.dailyperformance.correction.selecterrorcode;

import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.enums.ApproverEmployeeState;
import nts.uk.ctx.at.record.dom.workinformation.enums.CalculationState;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.SettingUnitType;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.YourselfConfirmError;
import nts.uk.ctx.at.request.app.find.application.applicationlist.ApplicationExportDto;
import nts.uk.ctx.at.request.app.find.application.applicationlist.ApplicationListForScreen;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapterDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.DailyAttendanceAtr;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.screen.at.app.dailymodify.query.DailyModifyQueryProcessor;
import nts.uk.screen.at.app.dailymodify.query.DailyModifyResult;
import nts.uk.screen.at.app.dailyperformance.correction.DailyPerformanceScreenRepo;
import nts.uk.screen.at.app.dailyperformance.correction.GetDataDaily;
import nts.uk.screen.at.app.dailyperformance.correction.datadialog.CodeName;
import nts.uk.screen.at.app.dailyperformance.correction.datadialog.DataDialogWithTypeProcessor;
import nts.uk.screen.at.app.dailyperformance.correction.datadialog.classification.EnumCodeName;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ActualLockDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.AffEmploymentHistoryDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ApprovalUseSettingDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.AuthorityFomatDailyDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.AuthorityFormatInitialDisplayDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.AuthorityFormatSheetDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ClosureDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ColumnSetting;
import nts.uk.screen.at.app.dailyperformance.correction.dto.CorrectionOfDailyPerformance;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPAttendanceItem;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPAttendanceItemControl;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPBusinessTypeControl;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPCellDataDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPControlDisplayItem;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPDataDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPErrorDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPErrorSettingDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPHeaderDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPSheetDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DailyPerformanceCorrectionDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DailyPerformanceEmployeeDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DailyRecEditSetDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DailyRecOpeFuncDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DateRange;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DisplayItem;
import nts.uk.screen.at.app.dailyperformance.correction.dto.FormatDPCorrectionDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.IdentityProcessUseSetDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ObjectShare;
import nts.uk.screen.at.app.dailyperformance.correction.dto.OperationOfDailyPerformanceDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.WorkFixedDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.WorkInfoOfDailyPerformanceDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.checkshowbutton.DailyPerformanceAuthorityDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.type.TypeLink;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class DailyPerformanceErrorCodeProcessor {

	@Inject
	private DailyPerformanceScreenRepo repo;

	@Inject
	private ClosureService closureService;

	@Inject
	private DailyModifyQueryProcessor dailyModifyQueryProcessor;

	@Inject
	private DailyAttendanceItemNameAdapter dailyAttendanceItemNameAdapter;

	@Inject
	private DataDialogWithTypeProcessor dataDialogWithTypeProcessor;

	@Inject
	private ApplicationListForScreen applicationListFinder;
	
	private static final String CODE = "Code";
	private static final String NAME = "Name";
	private static final String NO = "NO";
	private static final String LOCK_DATE = "date";
	private static final String LOCK_EMP_CODE = "employeeCode";
	private static final String LOCK_EMP_NAME = "employeeName";
	private static final String LOCK_ERROR = "error";
	private static final String LOCK_SIGN = "sign";
	private static final String LOCK_APPROVAL = "approval";
	private static final String LOCK_PIC = "picture-person";
	private static final String SCREEN_KDW003 = "KDW/003/a";
	private static final String ADD_CHARACTER = "A";
	private static final String PX = "px";
	private static final String TYPE_LABEL = "label";
	private static final String FORMAT_HH_MM = "%d:%02d";
	private static final String TYPE_LINK = "Link2";
	private static final String LOCK_EDIT_CELL_DAY = "D";
	private static final String LOCK_EDIT_CELL_MONTH = "M";
	private static final String LOCK_EDIT_CELL_WORK = "C";
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final String LOCK_APPLICATION = "Application";
	private static final String COLUMN_SUBMITTED = "Submitted";
	public static final int MINUTES_OF_DAY = 24 * 60;
	private static final String STATE_DISABLE = "ntsgrid-disable";
	private static final String HAND_CORRECTION_MYSELF = "ntsgrid-manual-edit-target";
	private static final String HAND_CORRECTION_OTHER = "ntsgrid-manual-edit-other";
	private static final String REFLECT_APPLICATION = "ntsgrid-reflect";
	private static final String STATE_ERROR ="ntsgrid-error";

	public DailyPerformanceCorrectionDto generateData(DateRange dateRange,
			List<DailyPerformanceEmployeeDto> lstEmployee, Integer initScreen, Integer displayFormat, CorrectionOfDailyPerformance correct,
			List<String> errorCodes, List<String> formatCodes) {
		String sId = AppContexts.user().employeeId();
		String companyId = AppContexts.user().companyId();
		DailyPerformanceCorrectionDto screenDto = new DailyPerformanceCorrectionDto();
		String NAME_EMPTY = TextResource.localize("KDW003_82");
		String NAME_NOT_FOUND = TextResource.localize("KDW003_81");
		// identityProcessDto show button A2_6
		// アルゴリズム「本人確認処理の利用設定を取得する」を実行する
		Optional<IdentityProcessUseSetDto> identityProcessDtoOpt = repo.findIdentityProcessUseSet(companyId);
		screenDto.setIdentityProcessDto(identityProcessDtoOpt.isPresent() ? identityProcessDtoOpt.get()
				: new IdentityProcessUseSetDto(false, false, null));
		Optional<ApprovalUseSettingDto> approvalUseSettingDtoOpt = repo.findApprovalUseSettingDto(companyId);

		/**
		 * システム日付を基準に1ヵ月前の期間を設定する | Set date range one month before system date
		 */
		screenDto.setDateRange(dateRange);

		/** 画面制御に関する情報を取得する | Acquire information on screen control */
		// アルゴリズム「社員の日別実績の権限をすべて取得する」を実行する | Execute "Acquire all permissions of
		// employee's daily performance"--
		String roleId = AppContexts.user().roles().forAttendance();
		List<DailyPerformanceAuthorityDto> dailyPerformans = new ArrayList<>();
		if (roleId != null) {
			dailyPerformans = repo.findDailyAuthority(roleId);
		}
		if (dailyPerformans.isEmpty()) {
			throw new BusinessException("Msg_671");
		} else {
			// NO.15
			screenDto.setAuthorityDto(dailyPerformans);
		}

		// get employmentCode
		AffEmploymentHistoryDto employment = repo.getAffEmploymentHistory(sId, dateRange);
		screenDto.setEmploymentCode(employment == null ? "" : employment.getEmploymentCode());
		/**
		 * アルゴリズム「表示形式に従って情報を取得する」を実行する | Execute "Get information according to
		 * display format"
		 */
		// アルゴリズム「対象者を抽出する」を実行する | Execute "Extract subject"

		List<String> listEmployeeId = lstEmployee.stream().map(e -> e.getId()).collect(Collectors.toList());
		List<DPErrorDto> lstError = this.repo.getListDPError(screenDto.getDateRange(), listEmployeeId, errorCodes);
		Map<String, String> mapIdError = new HashMap<>();
		for (DPErrorDto dto : lstError) {
			mapIdError.put(dto.getEmployeeId(), "");
		}
		lstEmployee = lstEmployee.stream().filter(x -> mapIdError.get(x.getId()) != null).collect(Collectors.toList());
		if (lstEmployee.isEmpty()) {
			throw new BusinessException("Msg_672");
		}
		screenDto.setLstEmployee(lstEmployee);
		// 表示形式をチェックする | Check display format => UI
		// Create lstData: Get by listEmployee & listDate
		// 日付別の情報を取得する + 個人別の情報を取得する + エラーアラームの情報を取得する | Acquire information by
		// date + Acquire personalized information + Acquire error alarm
		// information
		screenDto.setLstData(getListData(screenDto.getLstEmployee(), dateRange));
		// Lấy thành tích nhân viên theo ngày 
		/// アルゴリズム「対象日に対応する社員の実績の編集状態を取得する」を実行する | Execute "Acquire edit status
		/// of employee's record corresponding to target date"| lay ve trang
		/// thai sua cua thanh tich nhan vien tuong ung
		List<DailyRecEditSetDto> dailyRecEditSets = repo.getDailyRecEditSet(listEmployeeId, dateRange);
		Map<String, Integer> dailyRecEditSetsMap = dailyRecEditSets.stream()
				.collect(Collectors.toMap(x -> mergeString(String.valueOf(x.getAttendanceItemId()), "|",
						x.getEmployeeId(), "|", converDateToString(x.getProcessingYmd())), x -> x.getEditState()));
		
		// check show column 本人
		// check show column 承認
		DailyRecOpeFuncDto dailyRecOpeFun = findDailyRecOpeFun(screenDto, companyId).orElse(null);
		Map<String, DatePeriod> employeeAndDateRange = extractEmpAndRange(dateRange, companyId, listEmployeeId);
		long start = System.currentTimeMillis();
		// No 19, 20 show/hide button
		boolean showButton = true;
		if (displayFormat == 0) {
			if (!listEmployeeId.isEmpty() && !sId.equals(listEmployeeId.get(0))) {
				showButton = false;
			}
		}
		DisplayItem disItem = getDisplayItems(correct, formatCodes, companyId, screenDto, listEmployeeId, showButton);
		List<DailyModifyResult> results = new ArrayList<>();
		results = new GetDataDaily(listEmployeeId, dateRange, disItem.getLstAtdItemUnique(), dailyModifyQueryProcessor).call();
		DPControlDisplayItem dPControlDisplayItem = this.getItemIdNames(disItem, showButton);
		/// アルゴリズム「対象日に対応する社員の実績の編集状態を取得する」を実行する | Execute "Acquire edit status
		/// of employee's record corresponding to target date"| lay ve trang
		/// アルゴリズム「実績エラーをすべて取得する」を実行する | Execute "Acquire all actual errors"
		Map<Integer, DPAttendanceItem> mapDP = dPControlDisplayItem.getLstAttendanceItem() != null
				? dPControlDisplayItem.getLstAttendanceItem().stream()
						.collect(Collectors.toMap(DPAttendanceItem::getId, x -> x))
				: new HashMap<>();
		if (screenDto.getLstEmployee().size() > 0) {
			/// ドメインモデル「社員の日別実績エラー一覧」をすべて取得する +
			/// 対応するドメインモデル「勤務実績のエラーアラーム」をすべて取得する
			/// Acquire all domain model "employee's daily performance error
			/// list" + "work error error alarm" | lay loi thanh tich trong
			/// khoang thoi gian
			if (lstError.size() > 0) {
				// Get list error setting
				List<DPErrorSettingDto> lstErrorSetting = this.repo
						.getErrorSetting(lstError.stream().map(e -> e.getErrorCode()).collect(Collectors.toList()));
				// Seperate Error and Alarm
				screenDto.addErrorToResponseData(lstError, lstErrorSetting, mapDP);
			}
		}
		screenDto.setLstControlDisplayItem(dPControlDisplayItem);
		screenDto.getItemValues().addAll(results.isEmpty() ? new ArrayList<>() : results.get(0).getItems());

		System.out.println("time get data and map name : " + (System.currentTimeMillis() - start));
		long startTime2 = System.currentTimeMillis();
		Map<String, DailyModifyResult> resultDailyMap = results.stream().collect(Collectors
				.toMap(x -> mergeString(x.getEmployeeId(), "|", x.getDate().toString()), Function.identity(), (x, y) -> x));
		//// 11. Excel: 未計算のアラームがある場合は日付又は名前に表示する
		// Map<Integer, Integer> typeControl =
		//// lstAttendanceItem.stream().collect(Collectors.toMap(DPAttendanceItem::
		//// getId, DPAttendanceItem::getAttendanceAtr));
		List<WorkInfoOfDailyPerformanceDto> workInfoOfDaily = repo.getListWorkInfoOfDailyPerformance(listEmployeeId,
				dateRange);
		List<DPDataDto> lstData = new ArrayList<DPDataDto>();
		// set error, alarm
		if (screenDto.getLstEmployee().size() > 0) {
			if (lstError.size() > 0) {
				// Get list error setting
				List<DPErrorSettingDto> lstErrorSetting = this.repo
						.getErrorSetting(lstError.stream().map(e -> e.getErrorCode()).collect(Collectors.toList()));
				// Seperate Error and Alarm
				screenDto.addErrorToResponseData(lstError, lstErrorSetting, mapDP);
			}
		}

		Set<Integer> types = dPControlDisplayItem.getLstAttendanceItem() == null ? new HashSet<>()
				: dPControlDisplayItem.getLstAttendanceItem().stream().map(x -> x.getTypeGroup()).filter(x -> x != null)
						.collect(Collectors.toSet());
		Map<Integer, Map<String, String>> mapGetName = dataDialogWithTypeProcessor
				.getAllCodeName(new ArrayList<>(types), companyId);
		// No 20 get submitted application
		List<ApplicationExportDto> appplication = listEmployeeId.isEmpty() ? Collections.emptyList() : applicationListFinder.getApplicationBySID(listEmployeeId,
				dateRange.getStartDate(), dateRange.getEndDate());
		Map<String, String> appMapDateSid = new HashMap<>();
		appplication.forEach(x -> {
			String key = x.getEmployeeID() + "|" + x.getAppDate();
			if (appMapDateSid.containsKey(key)) {
				appMapDateSid.put(key, appMapDateSid.get(key) + "  " + x.getAppTypeName());
			} else {
				appMapDateSid.put(key, x.getAppTypeName());
			}
		});
		//get  check box sign(Confirm day)
		Map<String, Boolean> signDayMap = repo.getConfirmDay(companyId, listEmployeeId, dateRange);
		
		System.out.println("time create HashMap: " + (System.currentTimeMillis() - startTime2));
		start = System.currentTimeMillis();
		screenDto.markLoginUser();
		long start1 = System.currentTimeMillis();
		screenDto.createAccessModifierCellState(mapDP);
		screenDto.getLstFixedHeader().forEach(column -> {
			screenDto.getLstControlDisplayItem().getColumnSettings().add(new ColumnSetting(column.getKey(), false));
		});
		System.out.println("time disable : " + (System.currentTimeMillis() - start1));
		// set cell data
		Map<String, ItemValue> itemValueMap = new HashMap<>();
		for (DPDataDto data : screenDto.getLstData()) {
			data.setEmploymentCode(screenDto.getEmploymentCode());
			if (!sId.equals(data.getEmployeeId())) {
				screenDto.setLock(data.getId(), LOCK_APPLICATION, STATE_DISABLE);
			}
			// map name submitted into cell
			if (appMapDateSid.containsKey(data.getEmployeeId() + "|" + data.getDate())) {
				data.addCellData(new DPCellDataDto(COLUMN_SUBMITTED,
						appMapDateSid.get(data.getEmployeeId() + "|" + data.getDate()), "", ""));
			} else {
				data.addCellData(new DPCellDataDto(COLUMN_SUBMITTED, "", "", ""));
			}
			data.addCellData(new DPCellDataDto(COLUMN_SUBMITTED, "", "", ""));
			data.addCellData(new DPCellDataDto(LOCK_APPLICATION, "", "", ""));
			//set checkbox sign
			data.setSign(signDayMap.containsKey(data.getEmployeeId() + "|" + data.getDate()));
			DailyModifyResult resultOfOneRow = getRow(resultDailyMap, data.getEmployeeId(), data.getDate());
			if (resultOfOneRow != null && !data.getError().equals("")) {
				lockData(sId, screenDto, data, identityProcessDtoOpt, approvalUseSettingDtoOpt);

				boolean lock = checkLockAndSetState(employeeAndDateRange, data);

				if (lock) {
					lockCell(screenDto, data);
				}
				if (resultOfOneRow != null) {
					// List<ItemValue> attendanceTimes =
					// resultOfOneRow.getItems().get("AttendanceTimeOfDailyPerformance");
					itemValueMap = resultOfOneRow.getItems().stream()
							.collect(Collectors.toMap(x -> mergeString(String.valueOf(x.getItemId()), "|",
									data.getEmployeeId(), "|", data.getDate().toString()), x -> x));
				}
				processCellData(NAME_EMPTY, NAME_NOT_FOUND, screenDto, dPControlDisplayItem, mapDP, mapGetName,
						itemValueMap, data, lock, dailyRecEditSetsMap, null);
				lstData.add(data);
				// DPCellDataDto bPCellDataDto = new DPCellDataDto(columnKey,
				// value,
				// dataType, type);
				Optional<WorkInfoOfDailyPerformanceDto> optWorkInfoOfDailyPerformanceDto = workInfoOfDaily.stream()
						.filter(w -> w.getEmployeeId().equals(data.getEmployeeId())
								&& w.getYmd().equals(data.getDate()))
						.findFirst();
				if (optWorkInfoOfDailyPerformanceDto.isPresent()
						&& optWorkInfoOfDailyPerformanceDto.get().getState() == CalculationState.No_Calculated)
					screenDto.setAlarmCellForFixedColumn(data.getId());
			}
		}
		screenDto.setLstData(lstData);
		return screenDto;
	}

	/**
	 * Get List Data include:<br/>
	 * Employee and Date
	 **/
	private List<DPDataDto> getListData(List<DailyPerformanceEmployeeDto> listEmployee, DateRange dateRange) {
		List<DPDataDto> result = new ArrayList<>();
		if (listEmployee.size() > 0) {
			List<GeneralDate> lstDate = dateRange.toListDate();
			int dataId = 0;
			for (int j = 0; j < listEmployee.size(); j++) {
				DailyPerformanceEmployeeDto employee = listEmployee.get(j);
				for (int i = 0; i < lstDate.size(); i++) {
					GeneralDate filterDate = lstDate.get(i);
					result.add(new DPDataDto(employee.getId()+"_"+dataId, "", "", filterDate, false, false, employee.getId(), employee.getCode(),
							employee.getBusinessName(), employee.getWorkplaceId(), "", ""));
					dataId++;
				}
			}
		}
		return result;
	}

	private Map<String, DatePeriod> extractEmpAndRange(DateRange dateRange, String companyId,
			List<String> listEmployeeId) {
		Map<String, DatePeriod> employeeAndDateRange = new HashMap<>();
		List<ClosureDto> closureDtos = repo.getClosureId(listEmployeeId, dateRange.getEndDate());
		if (!closureDtos.isEmpty()) {
			closureDtos.forEach(x -> {
				DatePeriod datePeriod = closureService.getClosurePeriod(x.getClosureId(),
						new YearMonth(x.getClosureMonth()));
				Optional<ActualLockDto> actualLockDto = repo.findAutualLockById(companyId, x.getClosureId());
				if (actualLockDto.isPresent()) {
					if (actualLockDto.get().getDailyLockState() == 1 || actualLockDto.get().getMonthlyLockState() == 1) {
						employeeAndDateRange.put(
								mergeString(x.getSid(), "|", x.getClosureId().toString(), "|", LOCK_EDIT_CELL_DAY),
								datePeriod);
					}

//					if (actualLockDto.get().getMonthlyLockState() == 1) {
//						employeeAndDateRange.put(
//								mergeString(x.getSid(), "|", x.getClosureId().toString(), "|", LOCK_EDIT_CELL_MONTH),
//								datePeriod);
//					}
				}
				// アルゴリズム「表示項目を制御する」を実行する | Execute "control display items"
				Optional<WorkFixedDto> workFixedOp = repo.findWorkFixed(x.getClosureId(), x.getClosureMonth());
				if (workFixedOp.isPresent()) {
					employeeAndDateRange.put(mergeString(x.getSid(), "|", x.getClosureId().toString(),
							"|" + workFixedOp.get().getWkpId(), "|", LOCK_EDIT_CELL_WORK), datePeriod);
				}
			});
		}
		return employeeAndDateRange;
	}
	
	private DisplayItem getDisplayItems(CorrectionOfDailyPerformance correct, List<String> formatCodes,
			String companyId, DailyPerformanceCorrectionDto screenDto, List<String> listEmployeeId,
			boolean showButton) {
		OperationOfDailyPerformanceDto dailyPerformanceDto = repo.findOperationOfDailyPerformance();
		screenDto.setComment(dailyPerformanceDto != null ? dailyPerformanceDto.getComment() : null);
		screenDto.setTypeBussiness(dailyPerformanceDto != null ? dailyPerformanceDto.getSettingUnit().value : 1);
		DisplayItem disItem = this.getItemIds(listEmployeeId, screenDto.getDateRange(), correct, formatCodes,
				dailyPerformanceDto, companyId, showButton);
		return disItem;
	}
	
	/**
	 * アルゴリズム「表示項目を制御する」を実行する | Execute the algorithm "control display items"
	 */
	private DPControlDisplayItem getItemIdNames(DisplayItem disItem, boolean showButton) {
		DPControlDisplayItem result = new DPControlDisplayItem();
		result.setFormatCode(disItem.getFormatCode());
		result.setSettingUnit(disItem.isSettingUnit());
		List<DPAttendanceItem> lstAttendanceItem = new ArrayList<>();
		Map<Integer, DPAttendanceItem> mapDP = new HashMap<>();
		List<Integer> lstAtdItemUnique = disItem.getLstAtdItemUnique();
		List<FormatDPCorrectionDto> lstFormat = disItem.getLstFormat();
		if (!lstAtdItemUnique.isEmpty()) {
			Map<Integer, String> itemName = dailyAttendanceItemNameAdapter.getDailyAttendanceItemName(lstAtdItemUnique)
					.stream().collect(Collectors.toMap(DailyAttendanceItemNameAdapterDto::getAttendanceItemId,
							x -> x.getAttendanceItemName())); // 9s
			lstAttendanceItem = lstAtdItemUnique.isEmpty() ? Collections.emptyList()
					: this.repo.getListAttendanceItem(lstAtdItemUnique).stream().map(x -> {
						x.setName(itemName.get(x.getId()));
						return x;
					}).collect(Collectors.toList());
		}
		result.createSheets(disItem.getLstSheet());
		mapDP = lstAttendanceItem.stream().collect(Collectors.toMap(DPAttendanceItem::getId, x -> x));
		result.addColumnsToSheet(lstFormat, mapDP, showButton);
		List<DPHeaderDto> lstHeader = new ArrayList<>();
		for (FormatDPCorrectionDto dto : lstFormat) {
			lstHeader.add(DPHeaderDto.createSimpleHeader(
					mergeString(ADD_CHARACTER, String.valueOf(dto.getAttendanceItemId())),
					String.valueOf(dto.getColumnWidth()) + PX, mapDP));
		}
		if (showButton) {
			lstHeader.add(DPHeaderDto.addHeaderSubmitted());
			lstHeader.add(DPHeaderDto.addHeaderApplication());
		}
		result.setLstHeader(lstHeader);
		if (!disItem.isSettingUnit()) {
			if (disItem.getLstBusinessTypeCode().size() > 0) {
				// set header access modifier
				// only user are login can edit or others can edit
				result.setColumnsAccessModifier(disItem.getLstBusinessTypeCode());
			}
		}
		for (DPHeaderDto key : result.getLstHeader()) {
			ColumnSetting columnSetting = new ColumnSetting(key.getKey(), false);
			if (!key.getKey().equals("Application") && !key.getKey().equals("Submitted")) {
				if (!key.getGroup().isEmpty()) {
					result.getColumnSettings().add(new ColumnSetting(key.getGroup().get(0).getKey(), false));
					result.getColumnSettings().add(new ColumnSetting(key.getGroup().get(1).getKey(), false));
				} else {
					/*
					 * 時間 - thoi gian hh:mm 5, 回数: so lan 2, 金額 : so tien 3, 日数:
					 * so ngay -
					 */
					DPAttendanceItem dPItem = mapDP
							.get(Integer.parseInt(key.getKey().substring(1, key.getKey().length()).trim()));
					columnSetting.setTypeFormat(dPItem.getAttendanceAtr());
				}
			}
			result.getColumnSettings().add(columnSetting);

		}
		if (!lstAttendanceItem.isEmpty()) {
			// set text to header
			result.setHeaderText(lstAttendanceItem);
			// set color to header
			List<DPAttendanceItemControl> lstAttendanceItemControl = this.repo
					.getListAttendanceItemControl(lstAtdItemUnique);
			result.setLstAttendanceItem(lstAttendanceItem);
			result.getLstAttendanceItem().stream().forEach(c -> c.setName(""));
			result.setHeaderColor(lstAttendanceItemControl);
		} else {
			result.setLstAttendanceItem(lstAttendanceItem);
		}
		// set combo box
		result.setComboItemCalc(EnumCodeName.getCalcHours());
		result.setComboItemDoWork(EnumCodeName.getDowork());
		result.setComboItemReason(EnumCodeName.getReasonGoOut());
		result.setItemIds(lstAtdItemUnique);
		return result;
	}

	private String mergeString(String... x) {
		return StringUtils.join(x);
	}
	
	private DailyModifyResult getRow(Map<String, DailyModifyResult> resultDailyMap, String sId, GeneralDate date) {
		return resultDailyMap.isEmpty() ? null : resultDailyMap.get(mergeString(sId, "|", date.toString()));
	}
	
	private Optional<DailyRecOpeFuncDto> findDailyRecOpeFun(DailyPerformanceCorrectionDto screenDto, String companyId) {
		Optional<DailyRecOpeFuncDto> findDailyRecOpeFun = repo.findDailyRecOpeFun(companyId);
		if (findDailyRecOpeFun.isPresent()) {
			screenDto.setShowPrincipal(findDailyRecOpeFun.get().getUseConfirmByYourself() == 0 ? false : true);
			screenDto.setShowSupervisor(findDailyRecOpeFun.get().getUseSupervisorConfirm() == 0 ? false : true);
		} else {
			screenDto.setShowPrincipal(false);
			screenDto.setShowSupervisor(false);
		}
		
		return findDailyRecOpeFun;
	}
	
	private void lockData(String sId, DailyPerformanceCorrectionDto screenDto,
			DPDataDto data, Optional<IdentityProcessUseSetDto> identityProcessUseSetDto, Optional<ApprovalUseSettingDto> approvalUseSettingDto) {
		// disable, enable check sign no 10
				if (!sId.equals(data.getEmployeeId())) {
					screenDto.setLock(data.getId(), LOCK_SIGN, STATE_DISABLE);
					// screenDto.setLock(data.getId(), LOCK_APPROVAL,
					// STATE_DISABLE);
				} else {
					if (identityProcessUseSetDto.isPresent()) {
						int selfConfirmError = identityProcessUseSetDto.get().getYourSelfConfirmError();
						// lock sign
						if (selfConfirmError == YourselfConfirmError.CANNOT_CHECKED_WHEN_ERROR.value) {
							if (data.getError().contains("ER") && data.isSign()) {
								screenDto.setLock(data.getId(), LOCK_SIGN, STATE_ERROR);
							} else if (data.getError().contains("ER") && !data.isSign()) {
								screenDto.setLock(data.getId(), LOCK_SIGN, STATE_DISABLE);
							}
							// thieu check khi co data
						} else if (selfConfirmError == YourselfConfirmError.CANNOT_REGISTER_WHEN_ERROR.value) {
							// co the dang ky data nhưng ko đăng ký được check box
						}
					}
				}

				if (!approvalUseSettingDto.isPresent()) {
					// lock approval
					int supervisorConfirmError = approvalUseSettingDto.get().getSupervisorConfirmErrorAtr();
					if (supervisorConfirmError == YourselfConfirmError.CANNOT_CHECKED_WHEN_ERROR.value) {
						if (data.getError().contains("ER") && data.isApproval()) {
							screenDto.setLock(data.getId(), LOCK_APPROVAL, STATE_ERROR);
						} else if (data.getError().contains("ER") && !data.isApproval()) {
							screenDto.setLock(data.getId(), LOCK_APPROVAL, STATE_DISABLE);
						}
						// thieu check khi co data
					} else if (supervisorConfirmError == YourselfConfirmError.CANNOT_REGISTER_WHEN_ERROR.value) {
						// co the dang ky data nhưng ko đăng ký được check box
					}

					// disable, enable checkbox with approveRootStatus
//					if (approveRootStatus == null)
//						return;
//					if (approveRootStatus.getApproverEmployeeState() != null
//							&& approveRootStatus.getApproverEmployeeState() != ApproverEmployeeState.PHASE_DURING) {
//						screenDto.setLock(data.getId(), LOCK_APPROVAL, STATE_DISABLE);
//					}
				}
	}
	
	private boolean checkLockAndSetState(Map<String, DatePeriod> employeeAndDateRange, DPDataDto data) {
		boolean lock = false;
		if (!employeeAndDateRange.isEmpty()) {
			for (int i = 1; i <= 5; i++) {
				String idxAsString = String.valueOf(i);
				DatePeriod dateD = employeeAndDateRange
						.get(mergeString(data.getEmployeeId(), "|", idxAsString, "|", LOCK_EDIT_CELL_DAY));
				DatePeriod dateM = employeeAndDateRange
						.get(mergeString(data.getEmployeeId(), "|", idxAsString, "|", LOCK_EDIT_CELL_MONTH));
				DatePeriod dateC = employeeAndDateRange.get(mergeString(data.getEmployeeId(), "|", idxAsString, "|",
						data.getWorkplaceId(), "|", LOCK_EDIT_CELL_WORK));
				String lockD = "", lockM = "", lockC = "";
				if (dateD != null && inRange(data, dateD)) {
					lockD = mergeString("|", LOCK_EDIT_CELL_DAY);
				}
				if (dateM != null && inRange(data, dateM)) {
					lockM = mergeString("|", LOCK_EDIT_CELL_MONTH);
				}
				if (dateC != null && inRange(data, dateC)) {
					lockC = mergeString("|", LOCK_EDIT_CELL_WORK);
				}
				if (!lockD.isEmpty() || !lockM.isEmpty() || !lockC.isEmpty()) {
					data.setState(mergeString("lock", lockD, lockM, lockC));
					lock = true;
				}
			}
		}
		return lock;
	}
	
	private void lockCell(DailyPerformanceCorrectionDto screenDto, DPDataDto data) {
		screenDto.setLock(data.getId(), LOCK_DATE, STATE_DISABLE);
		screenDto.setLock(data.getId(), LOCK_EMP_CODE, STATE_DISABLE);
		screenDto.setLock(data.getId(), LOCK_EMP_NAME, STATE_DISABLE);
		screenDto.setLock(data.getId(), LOCK_ERROR, STATE_DISABLE);
		screenDto.setLock(data.getId(), LOCK_SIGN, STATE_DISABLE);
		screenDto.setLock(data.getId(), LOCK_PIC, STATE_DISABLE);
		screenDto.setLock(data.getId(), LOCK_APPLICATION, STATE_DISABLE);
		screenDto.setLock(data.getId(), COLUMN_SUBMITTED, STATE_DISABLE);
	}
	
	private String converDateToString(GeneralDate genDate) {
		Format formatter = new SimpleDateFormat(DATE_FORMAT);
		return formatter.format(genDate.date());
	}
	
	private void processCellData(String NAME_EMPTY, String NAME_NOT_FOUND, DailyPerformanceCorrectionDto screenDto,
			DPControlDisplayItem dPControlDisplayItem, Map<Integer, DPAttendanceItem> mapDP,
			Map<Integer, Map<String, String>> mapGetName, Map<String, ItemValue> itemValueMap, DPDataDto data,
			boolean lock, Map<String, Integer> dailyRecEditSetsMap, ObjectShare share) {
		Set<DPCellDataDto> cellDatas = data.getCellDatas();
		String typeGroup = "";
		Integer cellEdit;
		if (dPControlDisplayItem.getLstAttendanceItem() != null) {
			for (DPAttendanceItem item : dPControlDisplayItem.getLstAttendanceItem()) {
				DPAttendanceItem dpAttenItem = mapDP.get(item.getId());
				String itemIdAsString = item.getId().toString();
				// int a = 1;
				int attendanceAtr = dpAttenItem.getAttendanceAtr();
				String attendanceAtrAsString = String.valueOf(item.getAttendanceAtr());
				Integer groupType = dpAttenItem.getTypeGroup();
				String key = mergeString(itemIdAsString, "|", data.getEmployeeId(), "|" + data.getDate().toString());
				String value = itemValueMap.get(key) != null && itemValueMap.get(key).value() != null
						? itemValueMap.get(key).value().toString() : "";
				cellEdit = dailyRecEditSetsMap.get(mergeString(itemIdAsString, "|", data.getEmployeeId(), "|" + converDateToString(data.getDate())));
				
				if (attendanceAtr == DailyAttendanceAtr.Code.value
						|| attendanceAtr == DailyAttendanceAtr.Classification.value) {
					String nameColKey = mergeString(NAME, itemIdAsString);
					if (attendanceAtr == DailyAttendanceAtr.Code.value) {
						String codeColKey = mergeString(CODE, itemIdAsString);
						typeGroup = typeGroup
								+ mergeString(String.valueOf(item.getId()), ":", String.valueOf(groupType), "|");
						if (lock) {
							screenDto.setLock(data.getId(), codeColKey, STATE_DISABLE);
							screenDto.setLock(data.getId(), nameColKey, STATE_DISABLE);
						}
						if (value.isEmpty()) {
							cellDatas.add(new DPCellDataDto(mergeString(CODE, itemIdAsString), value,
									attendanceAtrAsString, TYPE_LABEL));
							value = NAME_EMPTY;
						} else {
							if (groupType != null) {
								if (groupType == TypeLink.WORKPLACE.value || groupType == TypeLink.POSSITION.value) {
									Optional<CodeName> optCodeName = dataDialogWithTypeProcessor
											.getCodeNameWithId(groupType, data.getDate(), value);
									cellDatas.add(new DPCellDataDto(codeColKey,
											optCodeName.isPresent() ? optCodeName.get().getCode() : value,
											attendanceAtrAsString, TYPE_LABEL));
									value = !optCodeName.isPresent() ? NAME_NOT_FOUND : optCodeName.get().getName();
								} else {
									cellDatas.add(
											new DPCellDataDto(codeColKey, value, attendanceAtrAsString, TYPE_LABEL));
									value = mapGetName.get(groupType).containsKey(value)
											? mapGetName.get(groupType).get(value) : NAME_NOT_FOUND;
								}
							}
						}
						cellDatas.add(new DPCellDataDto(nameColKey, value, attendanceAtrAsString, TYPE_LINK));
						// set color edit
						cellEditColor(screenDto, data.getId(), nameColKey, cellEdit);
						cellEditColor(screenDto, data.getId(), codeColKey, cellEdit);
					} else {
						String noColKey = mergeString(NO, itemIdAsString);
						if (lock) {
							screenDto.setLock(data.getId(), noColKey, STATE_DISABLE);
							screenDto.setLock(data.getId(), nameColKey, STATE_DISABLE);
						}
						cellDatas.add(new DPCellDataDto(noColKey, value, attendanceAtrAsString, TYPE_LABEL));
						cellDatas.add(new DPCellDataDto(nameColKey, value, attendanceAtrAsString, TYPE_LINK));
						cellEditColor(screenDto, data.getId(), nameColKey, cellEdit);
						cellEditColor(screenDto, data.getId(), noColKey, cellEdit);
					}

				} else {
					String anyChar = mergeString(ADD_CHARACTER, itemIdAsString);
					// set color edit
					cellEditColor(screenDto, data.getId(), anyChar, cellEdit);
					if (lock) {
						screenDto.setLock(data.getId(), anyChar, STATE_DISABLE);
					}
					if (attendanceAtr == DailyAttendanceAtr.Time.value
							|| attendanceAtr == DailyAttendanceAtr.TimeOfDay.value) {
						//set SPR
						if(share != null && share.getInitClock() != null && share.getDisplayFormat() == 0){
							if(item.getId() == 31 && data.getEmployeeId().equals(share.getInitClock().getEmployeeId()) && data.getDate().equals(share.getInitClock().getDateSpr())){
								value = share.getInitClock().getGoOut() != null ?  share.getInitClock().getGoOut() : "";
							}else if(item.getId() == 41 && data.getEmployeeId().equals(share.getInitClock().getEmployeeId()) && data.getDate().equals(share.getInitClock().getDateSpr())){
								value = share.getInitClock().getLiveTime() != null ?  share.getInitClock().getLiveTime() : "";
							}
						}
						if (!value.isEmpty()) {
							// convert HH:mm
							int minute =0 ;
							if(Integer.parseInt(value) >= 0){
								minute = Integer.parseInt(value);
							}else{
								if (attendanceAtr == DailyAttendanceAtr.TimeOfDay.value) {
									minute = (Integer.parseInt(value)+ (1 + -Integer.parseInt(value) / MINUTES_OF_DAY) * MINUTES_OF_DAY);
								} else {
									minute = Integer.parseInt(value);
								}
							}
							int hours = minute / 60;
							int minutes = Math.abs(minute) % 60;
							cellDatas.add(new DPCellDataDto(anyChar, String.format(FORMAT_HH_MM, hours, minutes),
									attendanceAtrAsString, TYPE_LABEL));
						} else {
							cellDatas.add(new DPCellDataDto(anyChar, value, attendanceAtrAsString, TYPE_LABEL));
						}
					} else {
						cellDatas.add(new DPCellDataDto(anyChar, value, attendanceAtrAsString, TYPE_LABEL));
					}
				}
			}
		}
		data.setTypeGroup(typeGroup);
		data.setCellDatas(cellDatas);
	}
	
	/**
	 * アルゴリズム「表示項目を制御する」を実行する | Execute the algorithm "control display items"
	 */
	private DisplayItem getItemIds(List<String> lstEmployeeId, DateRange dateRange,
			CorrectionOfDailyPerformance correct, List<String> formatCodeSelects,
			OperationOfDailyPerformanceDto dailyPerformanceDto, String companyId, boolean showButton) {
		DisplayItem result = new DisplayItem();
		if (lstEmployeeId.size() > 0) {
			// 取得したドメインモデル「日別実績の運用」をチェックする | Check the acquired domain model
			// "Operation of daily performance"
			List<FormatDPCorrectionDto> lstFormat = new ArrayList<FormatDPCorrectionDto>();
			List<DPSheetDto> lstSheet = new ArrayList<DPSheetDto>();
			List<Integer> lstAtdItem = new ArrayList<>();
			List<Integer> lstAtdItemUnique = new ArrayList<>();
			List<DPBusinessTypeControl> lstDPBusinessTypeControl = new ArrayList<>();
			if (dailyPerformanceDto != null && dailyPerformanceDto.getSettingUnit() == SettingUnitType.AUTHORITY) {
				// setting button A2_4
				result.setSettingUnit(true);
				List<AuthorityFomatDailyDto> authorityFomatDailys = new ArrayList<>();
				List<AuthorityFormatSheetDto> authorityFormatSheets = new ArrayList<>();
				// アルゴリズム「社員の権限に対応する表示項目を取得する」を実行する
				// kiem tra thong tin rieng biet user
				if (correct == null) {
					if (formatCodeSelects.isEmpty()) {
						List<AuthorityFormatInitialDisplayDto> initialDisplayDtos = repo
								.findAuthorityFormatInitialDisplay(companyId);
						if (!initialDisplayDtos.isEmpty()) {
							List<String> formatCodes = initialDisplayDtos.stream()
									.map(x -> x.getDailyPerformanceFormatCode()).collect(Collectors.toList());
							result.setFormatCode(formatCodes.stream().collect(Collectors.toSet()));
							// Lấy về domain model "会社の日別実績の修正のフォーマット" tương ứng
							authorityFomatDailys = repo.findAuthorityFomatDaily(companyId, formatCodes);
							List<BigDecimal> sheetNos = authorityFomatDailys.stream().map(x -> x.getSheetNo())
									.collect(Collectors.toList());
							authorityFormatSheets = sheetNos.isEmpty() ? Collections.emptyList()
									: repo.findAuthorityFormatSheet(companyId, formatCodes, sheetNos);
						} else {
							// アルゴリズム「表示項目の選択を起動する」を実行する
							/// 画面「表示フォーマットの選択」をモーダルで起動する(Chạy màn hình "Select
							// display format" theo cách thức) -- chay man hinh
							// C
							throw new BusinessException(SCREEN_KDW003);
						}
					} else {
						result.setFormatCode(formatCodeSelects.stream().collect(Collectors.toSet()));
						authorityFomatDailys = repo.findAuthorityFomatDaily(companyId, formatCodeSelects);
						List<BigDecimal> sheetNos = authorityFomatDailys.stream().map(x -> x.getSheetNo())
								.collect(Collectors.toList());
						authorityFormatSheets = repo.findAuthorityFormatSheet(companyId, formatCodeSelects, sheetNos);
					}
				} else {
					// Lấy về domain model "会社の日別実績の修正のフォーマット" tương ứng
					List<String> formatCodes = Arrays.asList(correct.getPreviousDisplayItem());
					result.setFormatCode(formatCodes.stream().collect(Collectors.toSet()));
					authorityFomatDailys = repo.findAuthorityFomatDaily(companyId, formatCodes);
					List<BigDecimal> sheetNos = authorityFomatDailys.stream().map(x -> x.getSheetNo())
							.collect(Collectors.toList());
					authorityFormatSheets = sheetNos.isEmpty() ? Collections.emptyList()
							: repo.findAuthorityFormatSheet(companyId, formatCodes, sheetNos);
				}
				if (!authorityFomatDailys.isEmpty()) {
					// set FormatCode for button A2_4
					lstSheet = authorityFormatSheets.stream()
							.map(x -> new DPSheetDto(x.getSheetNo().toString(), x.getSheetName().toString()))
							.collect(Collectors.toList());
					Set<String> lstSheetNo = lstSheet.stream().map(DPSheetDto::getName).collect(Collectors.toSet());
					if (lstSheetNo.size() != lstSheet.size()) {
						lstSheet = lstSheet.stream().map(x -> new DPSheetDto(x.getName(), x.getName()))
								.collect(Collectors.toList());
					}
					lstFormat = authorityFomatDailys.stream()
							.map(x -> new FormatDPCorrectionDto(companyId, x.getDailyPerformanceFormatCode(),
									x.getAttendanceItemId(), x.getSheetNo().toString(), x.getDisplayOrder(),
									x.getColumnWidth().intValue()))
							.collect(Collectors.toList());
					lstAtdItem = lstFormat.stream().map(f -> f.getAttendanceItemId()).collect(Collectors.toList());
					lstAtdItemUnique = new HashSet<Integer>(lstAtdItem).stream().collect(Collectors.toList());

				}
			} else {
				// setting button A2_4
				result.setSettingUnit(false);
				// アルゴリズム「社員の勤務種別に対応する表示項目を取得する」を実行する
				/// アルゴリズム「社員の勤務種別をすべて取得する」を実行する
				List<String> lstBusinessTypeCode = this.repo.getListBusinessType(lstEmployeeId, dateRange);
				// Create header & sheet
				if (lstBusinessTypeCode.size() > 0) {
                    // List item hide 
					lstFormat = this.repo.getListFormatDPCorrection(lstBusinessTypeCode).stream().collect(Collectors.toList()); // 10s
					lstAtdItem = lstFormat.stream().map(f -> f.getAttendanceItemId()).collect(Collectors.toList());
					lstAtdItemUnique = new HashSet<Integer>(lstAtdItem).stream().collect(Collectors.toList());//.filter(x -> !itemHide.containsKey(x))
					lstSheet = this.repo.getFormatSheets(lstBusinessTypeCode);
					Set<String> lstSheetNo = lstSheet.stream().map(DPSheetDto::getName).collect(Collectors.toSet());
					if (lstSheetNo.size() != lstSheet.size()) {
						lstSheet = lstSheet.stream().map(x -> new DPSheetDto(x.getName(), x.getName()))
								.collect(Collectors.toList());
					}
				}	
			}
			/// 対応するドメインモデル「勤務種別日別実績の修正のフォーマット」を取得する
			if (lstFormat.size() > 0) {
				String authorityDailyID =  AppContexts.user().roles().forAttendance(); 
				lstDPBusinessTypeControl = this.repo.getListBusinessTypeControl(companyId, authorityDailyID,
						lstAtdItemUnique, true);
			}
			Map<Integer, String> itemHide = lstDPBusinessTypeControl.stream().filter(x -> x.isUseAtr()).collect(Collectors.toMap(DPBusinessTypeControl :: getAttendanceItemId, x -> "", (x, y) -> x));
			lstFormat = lstFormat.stream().filter(x -> itemHide.containsKey(x.getAttendanceItemId())).collect(Collectors.toList()); // 10s
			
			result.setLstBusinessTypeCode(lstDPBusinessTypeControl);
			result.setLstFormat(lstFormat);
			result.setLstSheet(lstSheet);
			result.setLstAtdItemUnique(lstAtdItemUnique);
			result.setBussiness(dailyPerformanceDto.getSettingUnit().value);
		}
		return result;
	}

	private boolean inRange(DPDataDto data, DatePeriod dateM) {
		return data.getDate().afterOrEquals(dateM.start()) && data.getDate().beforeOrEquals(dateM.end());
	}
	
	private void cellEditColor(DailyPerformanceCorrectionDto screenDto, String rowId, String columnKey, Integer cellEdit ){
		// set color edit
		if(cellEdit != null){
			if(cellEdit == 0){
				screenDto.setLock(rowId, columnKey, HAND_CORRECTION_MYSELF);
			}else if(cellEdit == 1){
				screenDto.setLock(rowId, columnKey, HAND_CORRECTION_OTHER);
			}else{
				screenDto.setLock(rowId, columnKey, REFLECT_APPLICATION);
			}
		}
	}
	
}
