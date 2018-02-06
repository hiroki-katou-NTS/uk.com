/**
 * 1:57:38 PM Aug 21, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;
import java.util.function.Function;
import java.util.stream.Collectors;
import java.util.stream.IntStream;
import java.util.stream.Stream;

import javax.ejb.Stateless;
import javax.inject.Inject;

import org.apache.commons.lang3.StringUtils;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.workinformation.enums.CalculationState;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ConfirmOfManagerOrYouself;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.SettingUnit;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.UseSetting;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapterDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.DailyAttendanceAtr;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.screen.at.app.dailymodify.query.DailyModifyQueryProcessor;
import nts.uk.screen.at.app.dailymodify.query.DailyModifyResult;
import nts.uk.screen.at.app.dailyperformance.correction.datadialog.CodeName;
import nts.uk.screen.at.app.dailyperformance.correction.datadialog.DataDialogWithTypeProcessor;
import nts.uk.screen.at.app.dailyperformance.correction.datadialog.classification.EnumCodeName;
import nts.uk.screen.at.app.dailyperformance.correction.dto.AffEmploymentHistoryDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.AuthorityFomatDailyDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.AuthorityFormatInitialDisplayDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.AuthorityFormatSheetDto;
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
import nts.uk.screen.at.app.dailyperformance.correction.dto.DailyRecOpeFuncDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DateRange;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DisplayItem;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DivergenceTimeDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ErrorReferenceDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.FormatDPCorrectionDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.OperationOfDailyPerformanceDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.WorkInfoOfDailyPerformanceDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.checkshowbutton.DailyPerformanceAuthorityDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.reasondiscrepancy.ReasonCodeName;
import nts.uk.screen.at.app.dailyperformance.correction.dto.reasondiscrepancy.ShowColumnDependent;
import nts.uk.screen.at.app.dailyperformance.correction.dto.type.TypeLink;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * @author hungnm
 *
 */
@Stateless
public class DailyPerformanceCorrectionProcessor {

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

	private static final String CODE = "Code";
	private static final String NAME = "Name";
	private static final String NO = "NO";
	private static final String LOCK_DATE = "date";
	private static final String LOCK_EMP_CODE = "employeeCode";
	private static final String LOCK_EMP_NAME = "employeeName";
	private static final String LOCK_ERROR = "error";
	private static final String LOCK_SIGN = "sign";
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
	private static final String separator = "|";
	private final String NAME_EMPTY = TextResource.localize("KDW003_82");
	private final String NAME_NOT_FOUND = TextResource.localize("KDW003_81");

	public DailyPerformanceCorrectionDto generateData(DateRange dateRange,
			List<DailyPerformanceEmployeeDto> lstEmployee, Integer initScreen, Integer displayFormat,
			CorrectionOfDailyPerformance correct, List<String> formatCodes) throws InterruptedException {
		long timeStart = System.currentTimeMillis();
		String sId = AppContexts.user().employeeId();
		String companyId = AppContexts.user().companyId();
		DailyPerformanceCorrectionDto screenDto = new DailyPerformanceCorrectionDto();

		/**
		 * システム日付を基準に1ヵ月前の期間を設定する | Set date range one month before system date
		 */
		screenDto.setDateRange(dateRange);

		/** 画面制御に関する情報を取得する | Acquire information on screen control */
		// アルゴリズム「社員の日別実績の権限をすべて取得する」を実行する | Execute "Acquire all permissions of
		// employee's daily performance"--
		screenDto.setAuthorityDto(getAuthority(screenDto, AppContexts.user().roles().forAttendance()));
		// get employmentCode
		screenDto.setEmploymentCode(getEmploymentCode(dateRange, sId));
		// アルゴリズム「休暇の管理状況をチェックする」を実行する | Get holiday setting data
		getHolidaySettingData(screenDto);

		/**
		 * アルゴリズム「表示形式に従って情報を取得する」を実行する | Execute "Get information according to
		 * display format"
		 */
		// アルゴリズム「対象者を抽出する」を実行する | Execute "Extract subject"
		screenDto.setLstEmployee(extractEmployeeList(lstEmployee, sId, dateRange));
		List<DailyPerformanceEmployeeDto> lstEmployeeData = extractEmployeeData(initScreen, sId,
				screenDto.getLstEmployee());
		// 表示形式をチェックする | Check display format => UI
		// Create lstData: Get by listEmployee & listDate
		// 日付別の情報を取得する + 個人別の情報を取得する + エラーアラームの情報を取得する | Acquire information by
		// date + Acquire personalized information + Acquire error alarm
		// information

		screenDto.setLstData(getListData(lstEmployeeData, dateRange));
		/// 対応する「日別実績」をすべて取得する | Acquire all corresponding "daily performance"
		List<String> listEmployeeId = lstEmployeeData.stream().map(e -> e.getId()).collect(Collectors.toList());

		/// アルゴリズム「対象日に対応する社員の実績の編集状態を取得する」を実行する | Execute "Acquire edit status
		/// of employee's record corresponding to target date"| lay ve trang
		/// thai sua cua thanh tich nhan vien tuong ung
		// --List<DailyRecEditSetDto> dailyRecEditSets =
		/// repo.getDailyRecEditSet(listEmployeeId, dateRange);
		/// アルゴリズム「実績エラーをすべて取得する」を実行する | Execute "Acquire all actual errors"
		if (displayFormat == 2) {
			listEmployeeId = getEmployees(screenDto, listEmployeeId);
		}

		// アルゴリズム「社員に対応する処理締めを取得する」を実行する | Execute "Acquire Process Tightening
		// Corresponding to Employees"--
		/// TODO : アルゴリズム「対象日に対応する承認者確認情報を取得する」を実行する | Execute "Acquire Approver
		/// Confirmation Information Corresponding to Target Date"
		// アルゴリズム「就業確定情報を取得する」を実行する
		/// アルゴリズム「日別実績のロックを取得する」を実行する (Tiến hành xử lý "Lấy về lock của thành
		// tích theo ngày")

		// check show column 本人
		DailyRecOpeFuncDto dailyRecOpeFun = findDailyRecOpeFun(screenDto, companyId).orElse(null);

		Map<String, DatePeriod> employeeAndDateRange = extractEmpAndRange(dateRange, companyId, listEmployeeId);
		System.out.println("time before get item" + (System.currentTimeMillis() - timeStart));
		long start = System.currentTimeMillis();
		DisplayItem disItem = getDisplayItems(correct, formatCodes, companyId, screenDto, listEmployeeId);
		DPControlDisplayItem dPControlDisplayItem = this.getItemIdNames(disItem);
		screenDto.setLstControlDisplayItem(dPControlDisplayItem);

		List<DailyModifyResult> results = new ArrayList<>();
		ExecutorService service = Executors.newFixedThreadPool(1);

		CountDownLatch latch = new CountDownLatch(1);
		Future<List<DailyModifyResult>> sResults = service.submit(
				new GetDataDaily(listEmployeeId, dateRange, disItem.getLstAtdItemUnique(), dailyModifyQueryProcessor));
		try {
			results = sResults.get();
			screenDto.getItemValues().addAll(results.isEmpty() ? new ArrayList<>() : results.get(0).getItems());
			latch.countDown();
		} catch (InterruptedException e1) {
			e1.printStackTrace();
			Thread.currentThread().interrupt();
		} catch (ExecutionException e1) {
			e1.printStackTrace();
			Thread.currentThread().interrupt();
		}
		latch.await();

		System.out.println("time get data and map name : " + (System.currentTimeMillis() - start));
		long startTime2 = System.currentTimeMillis();
		Map<String, DailyModifyResult> resultDailyMap = results.stream().collect(Collectors
				.toMap(x -> mergeStringWSeparator(x.getEmployeeId(), x.getDate().toString()), Function.identity()));
		//// 11. Excel: 未計算のアラームがある場合は日付又は名前に表示する
		// Map<Integer, Integer> typeControl =
		//// lstAttendanceItem.stream().collect(Collectors.toMap(DPAttendanceItem::
		//// getId, DPAttendanceItem::getAttendanceAtr));
		Map<String, WorkInfoOfDailyPerformanceDto> workInfoOfDaily = repo
				.getListWorkInfoOfDailyPerformance(listEmployeeId, dateRange).stream()
				.collect(Collectors.toMap(c -> mergeString(c.getEmployeeId(), c.getYmd().toString()), x -> x));
		// List<DPDataDto> lstData = new ArrayList<DPDataDto>();
		Map<Integer, DPAttendanceItem> mapDP;
		Map<Integer, Map<String, String>> mapGetName;
		if (dPControlDisplayItem.getLstAttendanceItem() != null) {
			mapDP = dPControlDisplayItem.getLstAttendanceItem().stream()
					.collect(Collectors.toMap(DPAttendanceItem::getId, x -> x));
			mapGetName = dataDialogWithTypeProcessor.getAllCodeName(dPControlDisplayItem.getLstAttendanceItem().stream()
					.map(x -> x.getTypeGroup()).filter(x -> x != null).collect(Collectors.toSet()), companyId);
		} else {
			mapDP = new HashMap<>();
			mapGetName = new HashMap<>();
		}
		System.out.println("time create HashMap: " + (System.currentTimeMillis() - startTime2));
		start = System.currentTimeMillis();
		screenDto.markLoginUser(sId);
		service = Executors.newFixedThreadPool(1);
		CountDownLatch latch1 = new CountDownLatch(1);
		// set disable cell
		service.submit(new Runnable() {
			@Override
			public void run() {
				screenDto.createAccessModifierCellState(mapDP, sId);
				screenDto.getLstFixedHeader().forEach(column -> {
					screenDto.getLstControlDisplayItem().getColumnSettings()
							.add(new ColumnSetting(column.getKey(), false));
				});
				latch1.countDown();
			}
		});
		processData(sId, screenDto, dailyRecOpeFun, employeeAndDateRange, dPControlDisplayItem, resultDailyMap,
				workInfoOfDaily, mapDP, mapGetName);

		System.out.println("time get data into cell : " + (System.currentTimeMillis() - start));
		start = System.currentTimeMillis();
		// screenDto.setLstData(lstData);
		latch1.await();
		System.out.println("time add  return : " + (System.currentTimeMillis() - start));
		System.out.println("All time :" + (System.currentTimeMillis() - timeStart));
		return screenDto;
	}

	private List<String> getEmployees(DailyPerformanceCorrectionDto screenDto, List<String> listEmployeeId) {
		// only filter data error
		Map<String, String> listEmployeeError = processErrorList(screenDto, listEmployeeId);
		screenDto.setLstData(screenDto.getLstData().stream()
				.filter(x -> listEmployeeError.containsKey(x.getEmployeeId())).collect(Collectors.toList()));
		return listEmployeeId.stream().filter(x -> listEmployeeError.containsKey(x))
				.collect(Collectors.toList());
	}

	private void processData(String sId, DailyPerformanceCorrectionDto screenDto, DailyRecOpeFuncDto dailyRecOpeFun,
			Map<String, DatePeriod> employeeAndDateRange, DPControlDisplayItem dPControlDisplayItem,
			Map<String, DailyModifyResult> resultDailyMap, Map<String, WorkInfoOfDailyPerformanceDto> workInfoOfDaily,
			Map<Integer, DPAttendanceItem> mapDP, Map<Integer, Map<String, String>> mapGetName) {
		// set cell data
		// List<DPDataDto> lstData,
		screenDto.getLstData().forEach(data -> {
			lockData(sId, screenDto, dailyRecOpeFun, data);

			boolean lock = checkLockAndSetState(employeeAndDateRange, data);

			if (lock) {
				lockCell(screenDto, data);
			}
			processRow(screenDto, dPControlDisplayItem, resultDailyMap, mapDP, mapGetName, data, lock);
			// lstData.add(data);
			// DPCellDataDto bPCellDataDto = new DPCellDataDto(columnKey, value,
			// dataType, type);
			WorkInfoOfDailyPerformanceDto wInfo = workInfoOfDaily
					.get(mergeString(data.getEmployeeId(), data.getDate().toString()));
			if (wInfo != null && wInfo.getState() == CalculationState.No_Calculated) {
				screenDto.setAlarmCellForFixedColumn(data.getId());
			}
		});
	}

	private void processRow(DailyPerformanceCorrectionDto screenDto, DPControlDisplayItem dPControlDisplayItem,
			Map<String, DailyModifyResult> resultDailyMap, Map<Integer, DPAttendanceItem> mapDP,
			Map<Integer, Map<String, String>> mapGetName, DPDataDto data, boolean lock) {
		Map<String, ItemValue> itemValueMap = new HashMap<>();
		DailyModifyResult resultOfOneRow = getRow(resultDailyMap, data.getEmployeeId(), data.getDate());
		if (resultOfOneRow != null) {
			// List<ItemValue> attendanceTimes =
			// resultOfOneRow.getItems().get("AttendanceTimeOfDailyPerformance");
			itemValueMap = resultOfOneRow.getItems().stream()
					.collect(Collectors.toMap(x -> mergeStringWSeparator(String.valueOf(x.getItemId()),
							data.getEmployeeId(), data.getDate().toString()), x -> x));
		}
		processCellData(screenDto, dPControlDisplayItem, mapDP, mapGetName, itemValueMap, data, lock);
	}

	public List<ErrorReferenceDto> getListErrorRefer(DateRange dateRange,
			List<DailyPerformanceEmployeeDto> lstEmployee) {
		List<ErrorReferenceDto> lstErrorRefer = new ArrayList<>();
		List<DPErrorDto> lstError = this.repo.getListDPError(dateRange,
				ConvertHelper.mapTo(lstEmployee, e -> e.getId()));
		if (lstError.size() > 0) {
			// 対応するドメインモデル「勤務実績のエラーアラーム」をすべて取得する
			// Get list error setting
			List<DPErrorSettingDto> lstErrorSetting = this.repo
					.getErrorSetting(ConvertHelper.mapTo(lstError, e -> e.getErrorCode()));
			// convert to list error reference
			IntStream.range(0, lstError.size()).forEach(idx -> {
				DPErrorDto e = lstError.get(idx);
				lstErrorSetting.stream().filter(eS -> e.getErrorCode().equals(eS.getErrorAlarmCode())).findFirst()
						.ifPresent(eS -> {
							lstErrorRefer.add(new ErrorReferenceDto(String.valueOf(idx), e.getEmployeeId(), "", "",
									e.getProcessingDate(), e.getErrorCode(), eS.getMessageDisplay(),
									e.getAttendanceItemId(), "", eS.isBoldAtr(), eS.getMessageColor()));
						});
			});
			// get list item to add item name
			List<DPAttendanceItem> lstAttendanceItem = this.repo
					.getListAttendanceItem(ConvertHelper.mapTo(lstError, f -> f.getAttendanceItemId()));
			// add employee code & name
			lstErrorRefer.stream().forEach(eR -> {
				lstAttendanceItem.stream().filter(item -> eR.getEmployeeId().equals(item.getId())).findFirst()
						.ifPresent(item -> {
							eR.setItemName(item.getName());
						});
				lstEmployee.stream().filter(e -> eR.getEmployeeId().equals(e.getId())).findFirst().ifPresent(e -> {
					eR.setEmployeeCode(e.getCode());
					eR.setEmployeeName(e.getBusinessName());
				});
			});
		}
		return lstErrorRefer;
	}

	private void processCellData(DailyPerformanceCorrectionDto screenDto, DPControlDisplayItem dPControlDisplayItem,
			Map<Integer, DPAttendanceItem> mapDP, Map<Integer, Map<String, String>> mapGetName,
			Map<String, ItemValue> itemValueMap, DPDataDto data, boolean lock) {
		List<DPCellDataDto> cellDatas = new ArrayList<>();
		if (dPControlDisplayItem.getLstAttendanceItem() != null) {
			for (DPAttendanceItem item : dPControlDisplayItem.getLstAttendanceItem()) {
				DPAttendanceItem dpAttenItem = mapDP.get(item.getId());
				String itemIdAsString = item.getId().toString();
				// int a = 1;
				int attendanceAtr = dpAttenItem.getAttendanceAtr();
				String attendAtrAsString = String.valueOf(item.getAttendanceAtr());
				String key = mergeStringWSeparator(itemIdAsString, data.getEmployeeId(), data.getDate().toString());
				String value = itemValueMap.get(key) != null && itemValueMap.get(key).value() != null
						? itemValueMap.get(key).value().toString() : "";
				if (attendanceAtr == DailyAttendanceAtr.Code.value
						|| attendanceAtr == DailyAttendanceAtr.Classification.value) {
					String nameColKey = mergeString(NAME, itemIdAsString);
					if (attendanceAtr == DailyAttendanceAtr.Code.value) {
						String codeColKey = mergeString(CODE, itemIdAsString);
						if (lock) {
							screenDto.setLock(data.getId(), codeColKey);
							screenDto.setLock(data.getId(), nameColKey);
						}
						if (value.isEmpty()) {
							cellDatas.add(new DPCellDataDto(mergeString(CODE, itemIdAsString), value, attendAtrAsString,
									TYPE_LABEL));
							value = NAME_EMPTY;
						} else {
							Integer groupType = dpAttenItem.getTypeGroup();
							if (groupType != null) {
								if (groupType == TypeLink.WORKPLACE.value || groupType == TypeLink.POSSITION.value) {
									Optional<CodeName> optCodeName = dataDialogWithTypeProcessor
											.getCodeNameWithId(groupType, data.getDate(), value);
									if (optCodeName.isPresent()) {
										cellDatas.add(new DPCellDataDto(codeColKey, optCodeName.get().getCode(),
												attendAtrAsString, TYPE_LABEL));
										value = optCodeName.get().getName();
									} else {
										cellDatas.add(
												new DPCellDataDto(codeColKey, value, attendAtrAsString, TYPE_LABEL));
										value = NAME_NOT_FOUND;
									}
								} else {
									cellDatas.add(new DPCellDataDto(codeColKey, value, attendAtrAsString, TYPE_LABEL));
									value = mapGetName.get(groupType).containsKey(value)
											? mapGetName.get(groupType).get(value) : NAME_NOT_FOUND;
								}
							}
						}
						cellDatas.add(new DPCellDataDto(nameColKey, value, attendAtrAsString, TYPE_LINK));
					} else {
						String noColKey = mergeString(NO, itemIdAsString);
						if (lock) {
							screenDto.setLock(data.getId(), noColKey);
							screenDto.setLock(data.getId(), nameColKey);
						}
						cellDatas.add(new DPCellDataDto(noColKey, value, attendAtrAsString, TYPE_LABEL));
						cellDatas.add(new DPCellDataDto(nameColKey, value, attendAtrAsString, TYPE_LINK));
					}
				} else {
					String anyChar = mergeString(ADD_CHARACTER, itemIdAsString);
					if (lock) {
						screenDto.setLock(data.getId(), anyChar);
					}
					if (attendanceAtr == DailyAttendanceAtr.Time.value
							|| attendanceAtr == DailyAttendanceAtr.TimeOfDay.value) {
						if (!value.isEmpty()) {
							cellDatas
									.add(new DPCellDataDto(anyChar, formatHours(value), attendAtrAsString, TYPE_LABEL));
						} else {
							cellDatas.add(new DPCellDataDto(anyChar, value, attendAtrAsString, TYPE_LABEL));
						}
					} else {
						cellDatas.add(new DPCellDataDto(anyChar, value, attendAtrAsString, TYPE_LABEL));
					}
				}
			}
		}
		data.setCellDatas(cellDatas);
	}

	private String formatHours(String value) {
		// convert HH:mm
		int minute = Integer.parseInt(value);
		int hours = minute / 60;
		int minutes = Math.abs(minute) % 60;
		return String.format(FORMAT_HH_MM, hours, minutes);
	}

	private DailyModifyResult getRow(Map<String, DailyModifyResult> resultDailyMap, String sId, GeneralDate date) {
		return resultDailyMap.isEmpty() ? null : resultDailyMap.get(mergeStringWSeparator(sId, date.toString()));
	}

	private void lockCell(DailyPerformanceCorrectionDto screenDto, DPDataDto data) {
		screenDto.setLock(data.getId(), LOCK_DATE);
		screenDto.setLock(data.getId(), LOCK_EMP_CODE);
		screenDto.setLock(data.getId(), LOCK_EMP_NAME);
		screenDto.setLock(data.getId(), LOCK_ERROR);
		screenDto.setLock(data.getId(), LOCK_SIGN);
		screenDto.setLock(data.getId(), LOCK_PIC);
	}

	private boolean checkLockAndSetState(Map<String, DatePeriod> employeeAndDateRange, DPDataDto data) {
		boolean lock = false;
		if (!employeeAndDateRange.isEmpty()) {
			for (int i = 1; i <= 5; i++) {
				String idxAsString = String.valueOf(i);
				DatePeriod dateD = employeeAndDateRange
						.get(mergeStringWSeparator(data.getEmployeeId(), idxAsString, LOCK_EDIT_CELL_DAY));
				DatePeriod dateM = employeeAndDateRange
						.get(mergeStringWSeparator(data.getEmployeeId(), idxAsString, LOCK_EDIT_CELL_MONTH));
				DatePeriod dateC = employeeAndDateRange.get(mergeStringWSeparator(data.getEmployeeId(), idxAsString,
						data.getWorkplaceId(), LOCK_EDIT_CELL_WORK));
				String lockD = "", lockM = "", lockC = "";
				if (dateD != null && inRange(data, dateD)) {
					lockD = mergeString(separator, LOCK_EDIT_CELL_DAY);
				}
				if (dateM != null && inRange(data, dateM)) {
					lockM = mergeString(separator, LOCK_EDIT_CELL_MONTH);
				}
				if (dateC != null && inRange(data, dateC)) {
					lockC = mergeString(separator, LOCK_EDIT_CELL_WORK);
				}
				if (!lockD.isEmpty() || !lockM.isEmpty() || !lockC.isEmpty()) {
					data.setState(mergeString("lock", lockD, lockM, lockC));
					lock = true;
				}
			}
		}
		return lock;
	}

	private boolean inRange(DPDataDto data, DatePeriod dateM) {
		return data.getDate().afterOrEquals(dateM.start()) && data.getDate().beforeOrEquals(dateM.end());
	}

	private void lockData(String sId, DailyPerformanceCorrectionDto screenDto, DailyRecOpeFuncDto dailyRecOpeFun,
			DPDataDto data) {
		// disable, enable check sign no 10
		if (dailyRecOpeFun != null) {
			if (!sId.equals(data.getEmployeeId())) {
				screenDto.setLock(data.getId(), LOCK_SIGN);
			} else if (data.getError().contains("ER")) {
				int selfConfirmError = dailyRecOpeFun.getYourselfConfirmError();
				if (selfConfirmError == ConfirmOfManagerOrYouself.CANNOT_CHECKED_WHEN_ERROR.value) {
					screenDto.setLock(data.getId(), LOCK_SIGN);
					// thieu check khi co data
				} else if (selfConfirmError == ConfirmOfManagerOrYouself.CANNOT_REGISTER_WHEN_ERROR.value) {
					// thieu tra ve message khi dang ky
				}
			}
		}
	}

	private DisplayItem getDisplayItems(CorrectionOfDailyPerformance correct, List<String> formatCodes,
			String companyId, DailyPerformanceCorrectionDto screenDto, List<String> listEmployeeId) {
		OperationOfDailyPerformanceDto dailyPerformanceDto = repo.findOperationOfDailyPerformance();
		screenDto.setComment(dailyPerformanceDto != null ? dailyPerformanceDto.getComment() : null);
		return this.getItemIds(listEmployeeId, screenDto.getDateRange(), correct, formatCodes, dailyPerformanceDto,
				companyId);
	}

	private Map<String, DatePeriod> extractEmpAndRange(DateRange dateRange, String companyId,
			List<String> listEmployeeId) {
		Map<String, DatePeriod> empDateRange = new HashMap<>();
		repo.getClosureId(listEmployeeId, dateRange.getEndDate()).stream().forEach(x -> {
			String sid = x.getSid(), closure = x.getClosureId().toString();
			DatePeriod period = closureService.getClosurePeriod(x.getClosureId(), new YearMonth(x.getClosureMonth()));
			repo.findAutualLockById(companyId, x.getClosureId()).ifPresent(actualLock -> {
				if (actualLock.getDailyLockState() == 1) {
					empDateRange.put(mergeStringWSeparator(sid, closure, LOCK_EDIT_CELL_DAY), period);
				}
				if (actualLock.getMonthlyLockState() == 1) {
					empDateRange.put(mergeStringWSeparator(sid, closure, LOCK_EDIT_CELL_MONTH), period);
				}
			});
			// アルゴリズム「表示項目を制御する」を実行する | Execute "control display items"
			repo.findWorkFixed(x.getClosureId(), x.getClosureMonth()).ifPresent(wkp -> {
				empDateRange.put(mergeStringWSeparator(sid, closure, wkp.getWkpId(), LOCK_EDIT_CELL_WORK), period);
			});
		});
		return empDateRange;
	}

	private String mergeStringWSeparator(String... values) {
		return StringUtils.join(values, separator);
	}

	private String mergeString(String... x) {
		return StringUtils.join(x);
	}

	private Optional<DailyRecOpeFuncDto> findDailyRecOpeFun(DailyPerformanceCorrectionDto screenDto, String companyId) {
		Optional<DailyRecOpeFuncDto> findDailyRecOpeFun = repo.findDailyRecOpeFun(companyId);
		if (findDailyRecOpeFun.isPresent()) {
			screenDto.setShowPrincipal(findDailyRecOpeFun.get().getUseConfirmByYourself() == 0 ? false : true);
		} else {
			screenDto.setShowPrincipal(false);
		}
		return findDailyRecOpeFun;
	}

	private Map<String, String> processErrorList(DailyPerformanceCorrectionDto screenDto, List<String> listEmployeeId) {
		List<DPErrorDto> lstError = new ArrayList<>();
		if (screenDto.getLstEmployee().size() > 0) {
			/// ドメインモデル「社員の日別実績エラー一覧」をすべて取得する +
			/// 対応するドメインモデル「勤務実績のエラーアラーム」をすべて取得する
			/// Acquire all domain model "employee's daily performance error
			/// list" + "work error error alarm" | lay loi thanh tich trong
			/// khoang thoi gian
			lstError = listEmployeeId.isEmpty() ? new ArrayList<>()
					: repo.getListDPError(screenDto.getDateRange(), listEmployeeId);
			if (lstError.size() > 0) {
				// Get list error setting
				List<DPErrorSettingDto> lstErrorSetting = this.repo
						.getErrorSetting(lstError.stream().map(e -> e.getErrorCode()).collect(Collectors.toList()));
				// Seperate Error and Alarm
				screenDto.addErrorToResponseData(lstError, lstErrorSetting);
			}
		}
		return lstError.stream().collect(Collectors.toMap(e -> e.getEmployeeId(), e -> ""));
	}

	private List<DailyPerformanceEmployeeDto> extractEmployeeData(Integer initScreen, String sId,
			List<DailyPerformanceEmployeeDto> emps) {
		if (initScreen == 0) {
			return emps.stream().filter(x -> x.getId().equals(sId)).collect(Collectors.toList());
		}
		return emps;

	}

	private List<DailyPerformanceEmployeeDto> extractEmployeeList(List<DailyPerformanceEmployeeDto> lstEmployee,
			String sId, DateRange range) {
		if (!lstEmployee.isEmpty()) {
			return lstEmployee;
		} else {
			return getListEmployee(sId, range);
		}
	}

	private String getEmploymentCode(DateRange dateRange, String sId) {
		AffEmploymentHistoryDto employment = repo.getAffEmploymentHistory(sId, dateRange);
		return employment == null ? "" : employment.getEmploymentCode();
	}

	private List<DailyPerformanceAuthorityDto> getAuthority(DailyPerformanceCorrectionDto screenDto, String roleId) {
		if (roleId != null) {
			List<DailyPerformanceAuthorityDto> dailyPerformans = repo.findDailyAuthority(roleId);
			if (!dailyPerformans.isEmpty()) {
				return dailyPerformans;
			}
		}
		throw new BusinessException("Msg_671");
	}

	/**
	 * アルゴリズム「表示項目を制御する」を実行する | Execute the algorithm "control display items"
	 */
	private DisplayItem getItemIds(List<String> lstEmployeeId, DateRange dateRange,
			CorrectionOfDailyPerformance correct, List<String> formatCodeSelects,
			OperationOfDailyPerformanceDto dailyPerformanceDto, String companyId) {
		DisplayItem result = new DisplayItem();
		if (lstEmployeeId.size() > 0) {
			// 取得したドメインモデル「日別実績の運用」をチェックする | Check the acquired domain model
			// "Operation of daily performance"
			List<FormatDPCorrectionDto> lstFormat = new ArrayList<FormatDPCorrectionDto>();
			List<DPSheetDto> lstSheet = new ArrayList<DPSheetDto>();
			if (dailyPerformanceDto != null && dailyPerformanceDto.getSettingUnit() == SettingUnit.AUTHORITY) {
				// setting button A2_4
				result.setSettingUnit(true);
				Set<String> formatCodes = getFormatCodes(correct, formatCodeSelects, companyId);
				result.setFormatCode(formatCodes);
				// Lấy về domain model "会社の日別実績の修正のフォーマット" tương ứng
				List<AuthorityFomatDailyDto> authorityFormatDailys = repo.findAuthorityFomatDaily(companyId,
						formatCodes);
				List<AuthorityFormatSheetDto> authorityFormatSheets = repo.findAuthorityFormatSheet(companyId,
						formatCodes, ConvertHelper.mapTo(authorityFormatDailys, x -> x.getSheetNo()));
				if (!authorityFormatDailys.isEmpty()) {
					// set FormatCode for button A2_4
					lstSheet = getSheets(ConvertHelper.mapTo(authorityFormatSheets,
							x -> new DPSheetDto(x.getSheetNo().toString(), x.getSheetName())));
					lstFormat = ConvertHelper.mapTo(authorityFormatDailys,
							x -> FormatDPCorrectionDto.from(companyId, x));

				}
			} else {
				// setting button A2_4
				result.setSettingUnit(false);
				// アルゴリズム「社員の勤務種別に対応する表示項目を取得する」を実行する
				/// アルゴリズム「社員の勤務種別をすべて取得する」を実行する
				List<String> lstBusinessTypeCode = this.repo.getListBusinessType(lstEmployeeId, dateRange);

				// Create header & sheet
				if (lstBusinessTypeCode.size() > 0) {
					lstSheet = getSheets(this.repo.getFormatSheets(lstBusinessTypeCode));
					/// 対応するドメインモデル「勤務種別日別実績の修正のフォーマット」を取得する
					lstFormat = this.repo.getListFormatDPCorrection(lstBusinessTypeCode); // 10s
				}
				result.setLstBusinessTypeCode(lstBusinessTypeCode);
			}
			result.setLstFormat(lstFormat);
			result.setLstSheet(lstSheet);
			result.setLstAtdItemUnique(
					lstFormat.stream().map(f -> f.getAttendanceItemId()).distinct().collect(Collectors.toList()));
			result.setBussiness(dailyPerformanceDto.getSettingUnit().value);
		}
		return result;
	}

	private Set<String> getFormatCodes(CorrectionOfDailyPerformance correct, List<String> formatCodeSelects,
			String companyId) {
		// アルゴリズム「社員の権限に対応する表示項目を取得する」を実行する
		// kiem tra thong tin rieng biet user
		if (correct == null) {
			if (formatCodeSelects.isEmpty()) {
				List<AuthorityFormatInitialDisplayDto> initialDisplayDtos = repo
						.findAuthorityFormatInitialDisplay(companyId);
				if (!initialDisplayDtos.isEmpty()) {
					return initialDisplayDtos.stream().map(x -> x.getDailyPerformanceFormatCode())
							.collect(Collectors.toSet());
				} else {
					// アルゴリズム「表示項目の選択を起動する」を実行する
					/// 画面「表示フォーマットの選択」をモーダルで起動する(Chạy màn hình "Select
					// display format" theo cách thức) -- chay man hinh C
					throw new BusinessException(SCREEN_KDW003);
				}
			} else {
				return formatCodeSelects.stream().collect(Collectors.toSet());
			}
		}
		// Lấy về domain model "会社の日別実績の修正のフォーマット" tương ứng
		return toSet(correct.getPreviousDisplayItem());

	}

	@SuppressWarnings("unchecked")
	private <T> Set<T> toSet(T... items) {
		return Stream.of(items).collect(Collectors.toSet());
	}

	private List<DPSheetDto> getSheets(List<DPSheetDto> lstSheet) {
		// TODO: to Thanh: why must check as below
		Set<String> lstSheetNo = lstSheet.stream().map(DPSheetDto::getName).collect(Collectors.toSet());
		if (lstSheetNo.size() != lstSheet.size()) {
			return lstSheet.stream().map(x -> new DPSheetDto(x.getName(), x.getName())).collect(Collectors.toList());
		}
		return lstSheet;
	}

	/**
	 * アルゴリズム「表示項目を制御する」を実行する | Execute the algorithm "control display items"
	 */
	private DPControlDisplayItem getItemIdNames(DisplayItem disItem) {
		DPControlDisplayItem result = new DPControlDisplayItem();
		result.setFormatCode(disItem.getFormatCode());
		result.setSettingUnit(disItem.isSettingUnit());
		List<DPAttendanceItem> attendanceItems = getAttendanceItems(disItem.getLstAtdItemUnique());

		result.createSheets(disItem.getLstSheet());
		Map<Integer, DPAttendanceItem> mapDP = attendanceItems.stream()
				.collect(Collectors.toMap(x -> x.getId(), x -> x));
		result.addColumnsToSheet(disItem.getLstFormat(), mapDP);
		result.setLstHeader(getHeader(mapDP, disItem.getLstFormat()));
		setColumnAccessModifier(disItem.isSettingUnit(), disItem.getLstBusinessTypeCode(), result,
				disItem.getLstAtdItemUnique(), disItem.getLstFormat().size() > 0);
		setColumnSetting(result, mapDP);

		if (!attendanceItems.isEmpty()) {
			// set text to header
			result.setHeaderText(attendanceItems);
			// set color to header
			result.setLstAttendanceItem(attendanceItems);
			result.getLstAttendanceItem().stream().forEach(c -> c.setName(""));
			result.setHeaderColor(this.repo.getListAttendanceItemControl(disItem.getLstAtdItemUnique()));
		} else {
			result.setLstAttendanceItem(attendanceItems);
		}
		// set combo box
		result.setComboItemCalc(EnumCodeName.getCalcHours());
		result.setComboItemDoWork(EnumCodeName.getDowork());
		result.setComboItemReason(EnumCodeName.getReasonGoOut());
		result.setItemIds(disItem.getLstAtdItemUnique());
		return result;
	}

	private List<DPAttendanceItem> getAttendanceItems(List<Integer> uniqueItems) {
		if (!uniqueItems.isEmpty()) {
			Map<Integer, String> itemName = dailyAttendanceItemNameAdapter
					.getDailyAttendanceItemNameAsMapName(uniqueItems); // 9s
			return this.repo.getListAttendanceItem(uniqueItems).stream().map(x -> {
				x.setName(itemName.get(x.getId()));
				return x;
			}).collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

	private List<DPHeaderDto> getHeader(Map<Integer, DPAttendanceItem> mapDP, List<FormatDPCorrectionDto> lstFormat) {
		return lstFormat.stream()
				.map(format -> DPHeaderDto.createSimpleHeader(
						mergeString(ADD_CHARACTER, String.valueOf(format.getAttendanceItemId())),
						String.valueOf(format.getColumnWidth()) + PX, mapDP))
				.collect(Collectors.toList());
	}

	private void setColumnAccessModifier(boolean isSettingUnit, List<String> businessTypeCodes,
			DPControlDisplayItem result, List<Integer> uniqueItems, boolean haveFormat) {
		if (!isSettingUnit && haveFormat) {
			// set header access modifier
			// only user are login can edit or others can edit
			result.setColumnsAccessModifier(this.repo.getListBusinessTypeControl(businessTypeCodes, uniqueItems));
		}
	}

	/** アルゴリズム「対象者を抽出する」を実行する */
	private List<DailyPerformanceEmployeeDto> getListEmployee(String sId, DateRange dateRange) {
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

	/** アルゴリズム「休暇の管理状況をチェックする」を実行する */
	private void getHolidaySettingData(DailyPerformanceCorrectionDto dailyPerformanceCorrectionDto) {
		// アルゴリズム「年休設定を取得する」を実行する
		dailyPerformanceCorrectionDto.setYearHolidaySettingDto(this.repo.getYearHolidaySetting());
		// アルゴリズム「振休管理設定を取得する」を実行する
		dailyPerformanceCorrectionDto.setSubstVacationDto(this.repo.getSubstVacationDto());
		// アルゴリズム「代休管理設定を取得する」を実行する
		dailyPerformanceCorrectionDto.setCompensLeaveComDto(this.repo.getCompensLeaveComDto());
		// アルゴリズム「60H超休管理設定を取得する」を実行する
		dailyPerformanceCorrectionDto.setCom60HVacationDto(this.repo.getCom60HVacationDto());
	}

	/**
	 * Get List Data include:<br/>
	 * Employee and Date
	 **/
	private List<DPDataDto> getListData(List<DailyPerformanceEmployeeDto> listEmployee, DateRange dateRange) {
		if (listEmployee.size() > 0) {
			List<GeneralDate> lstDate = dateRange.toListDate();
			Counter counter = new Counter();
			return listEmployee.stream().map(emp -> {
				return lstDate.stream().map(d -> {
					return new DPDataDto(counter.countUp(), "", "", d, false, emp.getId(), emp.getCode(),
							emp.getBusinessName(), emp.getWorkplaceId());
				}).collect(Collectors.toList());
			}).flatMap(List::stream).collect(Collectors.toList());
		}
		return new ArrayList<>();
	}

	/**
	 * アルゴリズム「表示項目を制御する」を実行する | Execute the algorithm "control display items"
	 */
	private DPControlDisplayItem getControlDisplayItems(List<String> lstEmployeeId, DateRange dateRange,
			CorrectionOfDailyPerformance correct, List<String> formatCodeSelects,
			OperationOfDailyPerformanceDto dailyPerformanceDto, String companyId) {
		DPControlDisplayItem result = new DPControlDisplayItem();
		if (lstEmployeeId.size() > 0) {
			// 取得したドメインモデル「日別実績の運用」をチェックする | Check the acquired domain model
			// "Operation of daily performance"
			List<FormatDPCorrectionDto> lstFormat = new ArrayList<FormatDPCorrectionDto>();
			List<DPSheetDto> lstSheet = new ArrayList<DPSheetDto>();
			List<Integer> lstAtdItem = new ArrayList<>();
			List<Integer> lstAtdItemUnique = new ArrayList<>();
			List<DPAttendanceItem> lstAttendanceItem = new ArrayList<>();
			Map<Integer, DPAttendanceItem> mapDP = new HashMap<>();
			if (dailyPerformanceDto != null && dailyPerformanceDto.getSettingUnit() == SettingUnit.AUTHORITY) {
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
					lstFormat = new ArrayList<FormatDPCorrectionDto>();
					lstSheet = new ArrayList<DPSheetDto>();
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
					if (!lstAtdItemUnique.isEmpty()) {
						Map<Integer, DailyAttendanceItemNameAdapterDto> itemName = dailyAttendanceItemNameAdapter
								.getDailyAttendanceItemName(lstAtdItemUnique).stream().collect(Collectors
										.toMap(DailyAttendanceItemNameAdapterDto::getAttendanceItemId, x -> x));
						lstAttendanceItem = lstAtdItemUnique.isEmpty() ? Collections.emptyList()
								: this.repo.getListAttendanceItem(lstAtdItemUnique).stream()
										.map(x -> new DPAttendanceItem(x.getId(),
												itemName.get(x.getId()).getAttendanceItemName(), x.getDisplayNumber(),
												x.isUserCanSet(), x.getLineBreakPosition(), x.getAttendanceAtr(),
												x.getTypeGroup()))
										.collect(Collectors.toList());
						mapDP = lstAttendanceItem.stream().collect(Collectors.toMap(DPAttendanceItem::getId, x -> x));
					}
					List<DPHeaderDto> lstHeader = new ArrayList<>();
					for (FormatDPCorrectionDto dto : lstFormat) {
						// chia cot con code name cua AttendanceItemId chinh va
						// set and create header
						lstHeader.add(DPHeaderDto.createSimpleHeader(
								ADD_CHARACTER + String.valueOf(dto.getAttendanceItemId()),
								String.valueOf(dto.getColumnWidth()) + PX, mapDP));
					}
					result.setLstHeader(lstHeader);
					// result.setLstSheet(lstSheet);
					result.createSheets(lstSheet);
					result.addColumnsToSheet(lstFormat, mapDP);
				}
			} else {
				// setting button A2_4
				result.setSettingUnit(false);
				// アルゴリズム「社員の勤務種別に対応する表示項目を取得する」を実行する
				/// アルゴリズム「社員の勤務種別をすべて取得する」を実行する
				List<String> lstBusinessTypeCode = this.repo.getListBusinessType(lstEmployeeId, dateRange);

				// Create header & sheet
				if (lstBusinessTypeCode.size() > 0) {

					lstSheet = this.repo.getFormatSheets(lstBusinessTypeCode);
					Set<String> lstSheetNo = lstSheet.stream().map(DPSheetDto::getName).collect(Collectors.toSet());
					if (lstSheetNo.size() != lstSheet.size()) {
						lstSheet = lstSheet.stream().map(x -> new DPSheetDto(x.getName(), x.getName()))
								.collect(Collectors.toList());
					}
					/// 対応するドメインモデル「勤務種別日別実績の修正のフォーマット」を取得する
					lstFormat = this.repo.getListFormatDPCorrection(lstBusinessTypeCode); // 10s
					lstAtdItem = lstFormat.stream().map(f -> f.getAttendanceItemId()).collect(Collectors.toList());
					lstAtdItemUnique = new HashSet<Integer>(lstAtdItem).stream().collect(Collectors.toList());
					if (!lstAtdItemUnique.isEmpty()) {
						Map<Integer, DailyAttendanceItemNameAdapterDto> itemName = dailyAttendanceItemNameAdapter
								.getDailyAttendanceItemName(lstAtdItemUnique).stream().collect(Collectors
										.toMap(DailyAttendanceItemNameAdapterDto::getAttendanceItemId, x -> x)); // 9s
						lstAttendanceItem = lstAtdItemUnique.isEmpty() ? Collections.emptyList()
								: this.repo.getListAttendanceItem(lstAtdItemUnique).stream()
										.map(x -> new DPAttendanceItem(x.getId(),
												itemName.get(x.getId()).getAttendanceItemName(), x.getDisplayNumber(),
												x.isUserCanSet(), x.getLineBreakPosition(), x.getAttendanceAtr(),
												x.getTypeGroup()))
										.collect(Collectors.toList());
						// mapDP =
						// lstAttendanceItem.stream().collect(Collectors.toMap(DPAttendanceItem::getId,
						// x -> x));
					}
					result.createSheets(lstSheet);
					mapDP = lstAttendanceItem.stream().collect(Collectors.toMap(DPAttendanceItem::getId, x -> x));
					result.addColumnsToSheet(lstFormat, mapDP);
					List<DPHeaderDto> lstHeader = new ArrayList<>();
					for (FormatDPCorrectionDto dto : lstFormat) {
						lstHeader.add(DPHeaderDto.createSimpleHeader(
								ADD_CHARACTER + String.valueOf(dto.getAttendanceItemId()),
								String.valueOf(dto.getColumnWidth()) + PX, mapDP));
					}
					result.setLstHeader(lstHeader);
				}
				List<DPBusinessTypeControl> lstDPBusinessTypeControl = new ArrayList<>();
				if (lstFormat.size() > 0) {
					lstDPBusinessTypeControl = this.repo.getListBusinessTypeControl(lstBusinessTypeCode,
							lstAtdItemUnique);
				}
				if (lstDPBusinessTypeControl.size() > 0) {
					// set header access modifier
					// only user are login can edit or others can edit
					result.setColumnsAccessModifier(lstDPBusinessTypeControl);
				}
			}
			setColumnSetting(result, mapDP);
			if (!lstAttendanceItem.isEmpty()) {
				// set text to header
				result.setHeaderText(lstAttendanceItem);
				// set color to header
				List<DPAttendanceItemControl> lstAttendanceItemControl = this.repo
						.getListAttendanceItemControl(lstAtdItemUnique);
				result.setLstAttendanceItem(lstAttendanceItem);
				result.setHeaderColor(lstAttendanceItemControl);
			}
			// set combo box
			result.setComboItemCalc(EnumCodeName.getCalcHours());
			result.setComboItemDoWork(EnumCodeName.getDowork());
			result.setComboItemReason(EnumCodeName.getReasonGoOut());
			result.setItemIds(lstAtdItemUnique);
		}
		return result;
	}

	// アルゴリズム「乖離理由を取得する」
	private ShowColumnDependent getReasonDiscrepancy(String companyId, int divTimeId) {
		ShowColumnDependent show = new ShowColumnDependent();
		Optional<DivergenceTimeDto> divOp = repo.findDivergenceTime(companyId, divTimeId);
		if (divOp.isPresent() && divOp.get().getDivTimeUseSet() == UseSetting.UseAtr_Use.value) {
			// ドメインモデル「乖離時間．乖離理由選択設定」をチェックする
			show.setColumnTimeUseSet(true);
			if (divOp.get().selectUseSet == UseSetting.UseAtr_Use.value) {
				List<ReasonCodeName> reasons = repo.findDivergenceReason(companyId, divTimeId);
				show.setColumnSelectUseSet(true);
				show.setReasons(reasons);
			}
			if (divOp.get().inputUseSet == UseSetting.UseAtr_Use.value) {
				show.setColumnInputUseSet(true);
			}
		}
		return show;
	}

	private void setColumnSetting(DPControlDisplayItem result, Map<Integer, DPAttendanceItem> mapDP) {
		result.getLstHeader().stream().forEach(key -> {
			ColumnSetting columnSetting = new ColumnSetting(key.getKey(), false);
			if (!key.getGroup().isEmpty()) {
				result.getColumnSettings().add(new ColumnSetting(key.getGroup().get(0).getKey(), false));
				result.getColumnSettings().add(new ColumnSetting(key.getGroup().get(1).getKey(), false));
			} else {
				/*
				 * 時間 - thoi gian hh:mm 5, 回数: so lan 2, 金額 : so tien 3, 日数: so
				 * ngay -
				 */
				DPAttendanceItem dPItem = mapDP.get(Integer.parseInt(getKey(key.getKey(), 1)));
				columnSetting.setTypeFormat(dPItem.getAttendanceAtr());
			}
			result.getColumnSettings().add(columnSetting);
		});
	}

	private String getKey(String key, int start) {
		String newKey = key.trim();
		return newKey.substring(start, newKey.length()).trim();
	}

	private class Counter {
		private int count;

		public Counter() {
			count = -1;
		}

		public int countUp() {
			this.count++;
			return this.count;
		}

		// public int countDown(){
		// if(this.count > 0){
		// this.count--;
		// }
		// return this.count;
		// }
	}
}
