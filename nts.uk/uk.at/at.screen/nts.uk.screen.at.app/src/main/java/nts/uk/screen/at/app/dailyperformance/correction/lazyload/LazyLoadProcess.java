//package nts.uk.screen.at.app.dailyperformance.correction.lazyload;
//
//import java.math.BigDecimal;
//import java.text.Format;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Arrays;
//import java.util.Collections;
//import java.util.Date;
//import java.util.HashMap;
//import java.util.HashSet;
//import java.util.List;
//import java.util.Map;
//import java.util.Optional;
//import java.util.Set;
//import java.util.concurrent.CountDownLatch;
//import java.util.concurrent.ExecutionException;
//import java.util.concurrent.ExecutorService;
//import java.util.concurrent.Executors;
//import java.util.concurrent.Future;
//import java.util.function.Function;
//import java.util.stream.Collectors;
//
//import javax.ejb.Stateless;
//import javax.inject.Inject;
//
//import org.apache.commons.lang3.StringUtils;
//
//import nts.arc.error.BusinessException;
//import nts.arc.time.GeneralDate;
//import nts.arc.time.YearMonth;
//import nts.uk.ctx.at.record.dom.workrecord.operationsetting.SettingUnit;
//import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
//import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapter;
//import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapterDto;
//import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.DailyAttendanceAtr;
//import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
//import nts.uk.screen.at.app.dailymodify.query.DailyModifyQueryProcessor;
//import nts.uk.screen.at.app.dailymodify.query.DailyModifyResult;
//import nts.uk.screen.at.app.dailyperformance.correction.DailyPerformanceScreenRepo;
//import nts.uk.screen.at.app.dailyperformance.correction.GetDataDaily;
//import nts.uk.screen.at.app.dailyperformance.correction.datadialog.CodeName;
//import nts.uk.screen.at.app.dailyperformance.correction.datadialog.DataDialogWithTypeProcessor;
//import nts.uk.screen.at.app.dailyperformance.correction.datadialog.classification.EnumCodeName;
//import nts.uk.screen.at.app.dailyperformance.correction.dto.ActualLockDto;
//import nts.uk.screen.at.app.dailyperformance.correction.dto.AuthorityFomatDailyDto;
//import nts.uk.screen.at.app.dailyperformance.correction.dto.AuthorityFormatInitialDisplayDto;
//import nts.uk.screen.at.app.dailyperformance.correction.dto.AuthorityFormatSheetDto;
//import nts.uk.screen.at.app.dailyperformance.correction.dto.ClosureDto;
//import nts.uk.screen.at.app.dailyperformance.correction.dto.ColumnSetting;
//import nts.uk.screen.at.app.dailyperformance.correction.dto.CorrectionOfDailyPerformance;
//import nts.uk.screen.at.app.dailyperformance.correction.dto.DPAttendanceItem;
//import nts.uk.screen.at.app.dailyperformance.correction.dto.DPAttendanceItemControl;
//import nts.uk.screen.at.app.dailyperformance.correction.dto.DPBusinessTypeControl;
//import nts.uk.screen.at.app.dailyperformance.correction.dto.DPCellDataDto;
//import nts.uk.screen.at.app.dailyperformance.correction.dto.DPControlDisplayItem;
//import nts.uk.screen.at.app.dailyperformance.correction.dto.DPDataDto;
//import nts.uk.screen.at.app.dailyperformance.correction.dto.DPErrorDto;
//import nts.uk.screen.at.app.dailyperformance.correction.dto.DPErrorSettingDto;
//import nts.uk.screen.at.app.dailyperformance.correction.dto.DPHeaderDto;
//import nts.uk.screen.at.app.dailyperformance.correction.dto.DPSheetDto;
//import nts.uk.screen.at.app.dailyperformance.correction.dto.DailyPerformanceCorrectionDto;
//import nts.uk.screen.at.app.dailyperformance.correction.dto.DailyPerformanceEmployeeDto;
//import nts.uk.screen.at.app.dailyperformance.correction.dto.DailyRecOpeFuncDto;
//import nts.uk.screen.at.app.dailyperformance.correction.dto.DateRange;
//import nts.uk.screen.at.app.dailyperformance.correction.dto.DisplayItem;
//import nts.uk.screen.at.app.dailyperformance.correction.dto.FormatDPCorrectionDto;
//import nts.uk.screen.at.app.dailyperformance.correction.dto.OperationOfDailyPerformanceDto;
//import nts.uk.screen.at.app.dailyperformance.correction.dto.WorkFixedDto;
//import nts.uk.screen.at.app.dailyperformance.correction.dto.type.TypeLink;
//import nts.uk.shr.com.context.AppContexts;
//import nts.uk.shr.com.i18n.TextResource;
//import nts.arc.time.calendar.period.DatePeriod;
//
//@Stateless
//public class LazyLoadProcess {
//
//	@Inject
//	private DailyPerformanceScreenRepo repo;
//	
//	@Inject
//	private DailyModifyQueryProcessor dailyModifyQueryProcessor;
//	
//	@Inject
//	private DailyAttendanceItemNameAdapter dailyAttendanceItemNameAdapter;
//	
//	@Inject
//	private DataDialogWithTypeProcessor dataDialogWithTypeProcessor;
//	
//	private static final String CODE = "Code";
//	private static final String NAME = "Name";
//	private static final String NO = "NO";
//	private static final String ADD_CHARACTER = "A";
//	private static final String PX = "px";
//	private static final String TYPE_LABEL = "label";
//	private static final String FORMAT_HH_MM = "%d:%02d";
//	private static final String TYPE_LINK = "Link2";
//	private static final String LOCK_EDIT_CELL_DAY = "D";
//	private static final String LOCK_EDIT_CELL_MONTH = "M";
//	private static final String LOCK_EDIT_CELL_WORK = "C";
//	private static final String DATE_FORMAT = "yyyy-MM-dd";
//	
//	public List<String> keys() {
//		String sid = AppContexts.user().employeeId();
//		DateRange dateRan = new DateRange(GeneralDate.today().addMonths(-1).addDays(+1), GeneralDate.today());
//		int i =0;
//		List<String> keys = new ArrayList<>();
//		for(GeneralDate date : dateRan.toListDate()){
//			keys.add("0"+"_"+sid+"_"+converDateToString(date)+"_"+converDateToString(date)+"_"+i);
//			i++;
//		}
//		return keys;
//	}
//
//	public List<DPDataDto> getData(List<String> keys) throws InterruptedException {
//		//key = mode_employeeId_startDate_endDate_idrandom
//		//1_e478e3ab-4c94-436c-b9d2-0c9e5b6aba57_2017-01-01_2018-02-06_1_
//		Integer displayFormat = 0;
//		List<String> sids = new ArrayList<>();
//		DateRange dateRange = new DateRange(); 
//		int i =0;
//		for(String key : keys){
//			String [] keySplit = key.split("_");
//			displayFormat = Integer.parseInt(keySplit[0]);
//			sids.add(keySplit[1]);
//			//keyId = keySplit[4];
//			if(i == 0 ) dateRange.setStartDate(convertStringToDate(keySplit[2]));
//			if(i == (keys.size()-1)) dateRange.setEndDate(convertStringToDate(keySplit[3]));
//			i++;
//		}
//		sids = sids.stream().collect(Collectors.toSet()).stream().collect(Collectors.toList());
//		long timeStart = System.currentTimeMillis();
//		String NAME_EMPTY = TextResource.localize("KDW003_82");
//		String NAME_NOT_FOUND = TextResource.localize("KDW003_81");
//		String companyId = AppContexts.user().companyId();
//		DailyPerformanceCorrectionDto screenDto = new DailyPerformanceCorrectionDto();
//		screenDto.setDateRange(dateRange);
//		screenDto.setLstEmployee(extractEmployeeList(sids, dateRange));
//		List<DailyPerformanceEmployeeDto> lstEmployeeData = screenDto.getLstEmployee();
//		screenDto.setLstData(getListData(lstEmployeeData, dateRange));
//		List<String> listEmployeeId = lstEmployeeData.stream().map(e -> e.getId()).collect(Collectors.toList());
//
//		if (displayFormat == 2) {
//			// only filter data error
//			Map<String, String> listEmployeeError = processErrorList(screenDto, listEmployeeId);
//			listEmployeeId = listEmployeeId.stream().filter(x -> listEmployeeError.containsKey(x))
//					.collect(Collectors.toList());
//			screenDto.setLstData(screenDto.getLstData().stream()
//					.filter(x -> listEmployeeError.containsKey(x.getEmployeeId())).collect(Collectors.toList()));
//		}
//
//		// check show column 本人
//		Map<String, DatePeriod> employeeAndDateRange = extractEmpAndRange(dateRange, companyId, listEmployeeId);
//		System.out.println("time before get item" + (System.currentTimeMillis() - timeStart));
//		long start = System.currentTimeMillis();
//		DisplayItem disItem = getDisplayItems(null, null, companyId, screenDto, listEmployeeId);
//		DPControlDisplayItem dPControlDisplayItem = this.getItemIdNames(disItem);
//		screenDto.setLstControlDisplayItem(dPControlDisplayItem);
//
//		List<DailyModifyResult> results = new ArrayList<>();
//		ExecutorService service = Executors.newFixedThreadPool(1);
//
//		CountDownLatch latch = new CountDownLatch(1);
//		Future<List<DailyModifyResult>> sResults = service.submit(
//				new GetDataDaily(sids, dateRange, disItem.getLstAtdItemUnique(), dailyModifyQueryProcessor));
//		try {
//			results = sResults.get();
//			screenDto.getItemValues().addAll(results.isEmpty() ? new ArrayList<>() : results.get(0).getItems());
//			latch.countDown();
//		} catch (InterruptedException e1) {
//			e1.printStackTrace();
//			Thread.currentThread().interrupt();
//		} catch (ExecutionException e1) {
//			e1.printStackTrace();
//			Thread.currentThread().interrupt();
//		}
//		latch.await();
//
//		System.out.println("time get data and map name : " + (System.currentTimeMillis() - start));
//		long startTime2 = System.currentTimeMillis();
//		Map<String, DailyModifyResult> resultDailyMap = results.stream().collect(Collectors
//				.toMap(x -> mergeString(x.getEmployeeId(), "|", x.getDate().toString()), Function.identity()));
//		List<DPDataDto> lstData = new ArrayList<DPDataDto>();
//		Map<Integer, DPAttendanceItem> mapDP = dPControlDisplayItem.getLstAttendanceItem() != null
//				? dPControlDisplayItem.getLstAttendanceItem().stream()
//						.collect(Collectors.toMap(DPAttendanceItem::getId, x -> x))
//				: new HashMap<>();
//		Set<Integer> types = dPControlDisplayItem.getLstAttendanceItem() == null ? new HashSet<>()
//				: dPControlDisplayItem.getLstAttendanceItem().stream().map(x -> x.getTypeGroup()).filter(x -> x != null)
//						.collect(Collectors.toSet());
//		Map<Integer, Map<String, String>> mapGetName = dataDialogWithTypeProcessor
//				.getAllCodeName(new ArrayList<>(types), companyId);
//		Map<String, ItemValue> itemValueMap = new HashMap<>();
//		System.out.println("time create HashMap: " + (System.currentTimeMillis() - startTime2));
//		start = System.currentTimeMillis();
//		// set cell data
//		for (DPDataDto data : screenDto.getLstData()) {
//			boolean lock = checkLockAndSetState(employeeAndDateRange, data);
//			DailyModifyResult resultOfOneRow = getRow(resultDailyMap, data.getEmployeeId(), data.getDate());
//			if (resultOfOneRow != null) {
//				// List<ItemValue> attendanceTimes =
//				// resultOfOneRow.getItems().get("AttendanceTimeOfDailyPerformance");
//				itemValueMap = resultOfOneRow.getItems().stream()
//						.collect(Collectors.toMap(x -> mergeString(String.valueOf(x.getItemId()), "|",
//								data.getEmployeeId(), "|", data.getDate().toString()), x -> x));
//			}
//			processCellData(NAME_EMPTY, NAME_NOT_FOUND, screenDto, dPControlDisplayItem, mapDP, mapGetName,
//					itemValueMap, data, lock);
//			lstData.add(data);
//		}
//		System.out.println("time get data into cell : " + (System.currentTimeMillis() - start));
//		start = System.currentTimeMillis();
//		System.out.println("time add  return : " + (System.currentTimeMillis() - start));
//		System.out.println("All time :" + (System.currentTimeMillis() - timeStart));
//		return screenDto.getLstData();
//	}
//	
//	/** アルゴリズム「対象者を抽出する」を実行する */
//	private List<DailyPerformanceEmployeeDto> getListEmployee(List<String> sId, DateRange dateRange) {
//		return this.repo.getListEmployeeWithSid(sId);
//	}
//	
//	private String converDateToString(GeneralDate genDate){
//		Format formatter = new SimpleDateFormat(DATE_FORMAT);
//		return formatter.format(genDate.date());
//	}
//	
//	private GeneralDate convertStringToDate(String dateString){
//		 SimpleDateFormat formatter = new SimpleDateFormat(DATE_FORMAT);
//		 try {
//			Date date = formatter.parse(dateString);
//			return GeneralDate.legacyDate(date);
//		} catch (ParseException e) {
//			e.printStackTrace();
//		}
//		return null;
//	}
//	
//	private void processCellData(String NAME_EMPTY, String NAME_NOT_FOUND, DailyPerformanceCorrectionDto screenDto,
//			DPControlDisplayItem dPControlDisplayItem, Map<Integer, DPAttendanceItem> mapDP,
//			Map<Integer, Map<String, String>> mapGetName, Map<String, ItemValue> itemValueMap, DPDataDto data,
//			boolean lock) {
//		Set<DPCellDataDto> cellDatas = new HashSet<>();
//		if (dPControlDisplayItem.getLstAttendanceItem() != null) {
//			for (DPAttendanceItem item : dPControlDisplayItem.getLstAttendanceItem()) {
//				DPAttendanceItem dpAttenItem = mapDP.get(item.getId());
//				String itemIdAsString = item.getId().toString();
//				// int a = 1;
//				int attendanceAtr = dpAttenItem.getAttendanceAtr();
//				String attendanceAtrAsString = String.valueOf(item.getAttendanceAtr());
//				Integer groupType = dpAttenItem.getTypeGroup();
//				String key = mergeString(itemIdAsString, "|", data.getEmployeeId(),
//						"|" + data.getDate().toString());
//				String value = itemValueMap.get(key) != null && itemValueMap.get(key).value() != null ? itemValueMap.get(key).value().toString() : "";
//				if (attendanceAtr == DailyAttendanceAtr.Code.value
//						|| attendanceAtr == DailyAttendanceAtr.Classification.value) {
//					String nameColKey = mergeString(NAME, itemIdAsString);
//					if (attendanceAtr == DailyAttendanceAtr.Code.value) {
//						String codeColKey = mergeString(CODE, itemIdAsString);
//						if (lock) {
//							screenDto.setLock(data.getId(), codeColKey);
//							screenDto.setLock(data.getId(), nameColKey);
//						}
//						if (value.isEmpty()) {
//							cellDatas.add(new DPCellDataDto(mergeString(CODE, itemIdAsString), value,
//									attendanceAtrAsString, TYPE_LABEL));
//							value = NAME_EMPTY;
//						} else {
//							if (groupType != null) {
//								if (groupType == TypeLink.WORKPLACE.value || groupType == TypeLink.POSSITION.value) {
//									Optional<CodeName> optCodeName = dataDialogWithTypeProcessor
//											.getCodeNameWithId(groupType, data.getDate(), value);
//									cellDatas.add(new DPCellDataDto(codeColKey,
//											optCodeName.isPresent() ? optCodeName.get().getCode() : value,
//											attendanceAtrAsString, TYPE_LABEL));
//									value = !optCodeName.isPresent() ? NAME_NOT_FOUND : optCodeName.get().getName();
//								} else {
//									cellDatas.add(new DPCellDataDto(codeColKey, value, attendanceAtrAsString, TYPE_LABEL));
//									value = mapGetName.get(groupType).containsKey(value)
//											? mapGetName.get(groupType).get(value) : NAME_NOT_FOUND;
//								}
//							}
//						}
//						cellDatas.add(new DPCellDataDto(nameColKey, value, attendanceAtrAsString, TYPE_LINK));
//					} else {
//						String noColKey = mergeString(NO, itemIdAsString);
//						if (lock) {
//							screenDto.setLock(data.getId(), noColKey);
//							screenDto.setLock(data.getId(), nameColKey);
//						}
//						cellDatas.add(new DPCellDataDto(noColKey, value, attendanceAtrAsString, TYPE_LABEL));
//						cellDatas.add(new DPCellDataDto(nameColKey, value, attendanceAtrAsString, TYPE_LINK));
//					}
//
//				} else {
//					String anyChar = mergeString(ADD_CHARACTER, itemIdAsString);
//					if (lock) {
//						screenDto.setLock(data.getId(), anyChar);
//					}
//					if (attendanceAtr == DailyAttendanceAtr.Time.value || attendanceAtr == DailyAttendanceAtr.TimeOfDay.value) {
//						if (!value.isEmpty()) {
//							// convert HH:mm
//							int minute = Integer.parseInt(value);
//							int hours = minute / 60;
//							int minutes = Math.abs(minute) % 60;
//							cellDatas.add(new DPCellDataDto(anyChar, String.format(FORMAT_HH_MM, hours, minutes),
//									attendanceAtrAsString, TYPE_LABEL));
//						} else {
//							cellDatas.add(new DPCellDataDto(anyChar, value, attendanceAtrAsString, TYPE_LABEL));
//						}
//					} else {
//						cellDatas.add(new DPCellDataDto(anyChar, value, attendanceAtrAsString, TYPE_LABEL));
//					}
//				}
//			}
//		}
//		data.setCellDatas(cellDatas);
//	}
//
//	private DailyModifyResult getRow(Map<String, DailyModifyResult> resultDailyMap, String sId, GeneralDate date) {
//		return resultDailyMap.isEmpty() ? null : resultDailyMap.get(mergeString(sId, "|", date.toString()));
//	}
//
//	private boolean checkLockAndSetState(Map<String, DatePeriod> employeeAndDateRange, DPDataDto data) {
//		boolean lock = false;
//		if (!employeeAndDateRange.isEmpty()) {
//			for (int i = 1; i <= 5; i++) {
//				String idxAsString = String.valueOf(i);
//				DatePeriod dateD = employeeAndDateRange
//						.get(mergeString(data.getEmployeeId(), "|", idxAsString, "|", LOCK_EDIT_CELL_DAY));
//				DatePeriod dateM = employeeAndDateRange
//						.get(mergeString(data.getEmployeeId(), "|", idxAsString, "|", LOCK_EDIT_CELL_MONTH));
//				DatePeriod dateC = employeeAndDateRange.get(mergeString(data.getEmployeeId(), "|", idxAsString, "|",
//						data.getWorkplaceId(), "|", LOCK_EDIT_CELL_WORK));
//				String lockD = "", lockM = "", lockC = "";
//				if (dateD != null && inRange(data, dateD)) {
//					lockD = mergeString("|", LOCK_EDIT_CELL_DAY);
//				}
//				if (dateM != null && inRange(data, dateM)) {
//					lockM = mergeString("|", LOCK_EDIT_CELL_MONTH);
//				}
//				if (dateC != null && inRange(data, dateC)) {
//					lockC = mergeString("|", LOCK_EDIT_CELL_WORK);
//				}
//				if (!lockD.isEmpty() || !lockM.isEmpty() || !lockC.isEmpty()) {
//					data.setState(mergeString("lock", lockD, lockM, lockC));
//					lock = true;
//				}
//			}
//		}
//		return lock;
//	}
//
//	private boolean inRange(DPDataDto data, DatePeriod dateM) {
//		return data.getDate().afterOrEquals(dateM.start()) && data.getDate().beforeOrEquals(dateM.end());
//	}
//
//	private DisplayItem getDisplayItems(CorrectionOfDailyPerformance correct, List<String> formatCodes,
//			String companyId, DailyPerformanceCorrectionDto screenDto, List<String> listEmployeeId) {
//		OperationOfDailyPerformanceDto dailyPerformanceDto = repo.findOperationOfDailyPerformance();
//		screenDto.setComment(dailyPerformanceDto != null ? dailyPerformanceDto.getComment() : null);
//		DisplayItem disItem = this.getItemIds(listEmployeeId, screenDto.getDateRange(), correct, formatCodes,
//				dailyPerformanceDto, companyId);
//		return disItem;
//	}
//
//	private Map<String, DatePeriod> extractEmpAndRange(DateRange dateRange, String companyId,
//			List<String> listEmployeeId) {
//		Map<String, DatePeriod> employeeAndDateRange = new HashMap<>();
//		List<ClosureDto> closureDtos = repo.getClosureId(listEmployeeId, dateRange.getEndDate());
//		if (!closureDtos.isEmpty()) {
//			closureDtos.forEach(x -> {
//				DatePeriod datePeriod = closureService.getClosurePeriod(x.getClosureId(),
//						new YearMonth(x.getClosureMonth()));
//				Optional<ActualLockDto> actualLockDto = repo.findAutualLockById(companyId, x.getClosureId());
//				if (actualLockDto.isPresent()) {
//					if (actualLockDto.get().getDailyLockState() == 1) {
//						employeeAndDateRange.put(
//								mergeString(x.getSid(), "|", x.getClosureId().toString(), "|", LOCK_EDIT_CELL_DAY),
//								datePeriod);
//					}
//
//					if (actualLockDto.get().getMonthlyLockState() == 1) {
//						employeeAndDateRange.put(
//								mergeString(x.getSid(), "|", x.getClosureId().toString(), "|", LOCK_EDIT_CELL_MONTH),
//								datePeriod);
//					}
//				}
//				// アルゴリズム「表示項目を制御する」を実行する | Execute "control display items"
//				Optional<WorkFixedDto> workFixedOp = repo.findWorkFixed(x.getClosureId(), x.getClosureMonth());
//				if (workFixedOp.isPresent()) {
//					employeeAndDateRange.put(mergeString(x.getSid(), "|", x.getClosureId().toString(),
//							"|" + workFixedOp.get().getWkpId(), "|", LOCK_EDIT_CELL_WORK), datePeriod);
//				}
//			});
//		}
//		return employeeAndDateRange;
//	}
//
//	private String mergeString(String... x) {
//		return StringUtils.join(x);
//	}
//
//	private Optional<DailyRecOpeFuncDto> findDailyRecOpeFun(DailyPerformanceCorrectionDto screenDto, String companyId) {
//		Optional<DailyRecOpeFuncDto> findDailyRecOpeFun = repo.findDailyRecOpeFun(companyId);
//		if (findDailyRecOpeFun.isPresent()) {
//			screenDto.setShowPrincipal(
//					findDailyRecOpeFun.get().getUseConfirmByYourself() == 0 ? false : true);
//		} else {
//			screenDto.setShowPrincipal(false);
//		}
//		return findDailyRecOpeFun;
//	}
//
//	private Map<String, String> processErrorList(DailyPerformanceCorrectionDto screenDto, List<String> listEmployeeId) {
//		List<DPErrorDto> lstError = new ArrayList<>();
//		if (screenDto.getLstEmployee().size() > 0) {
//			/// ドメインモデル「社員の日別実績エラー一覧」をすべて取得する +
//			/// 対応するドメインモデル「勤務実績のエラーアラーム」をすべて取得する
//			/// Acquire all domain model "employee's daily performance error
//			/// list" + "work error error alarm" | lay loi thanh tich trong
//			/// khoang thoi gian
//			lstError = listEmployeeId.isEmpty() ? new ArrayList<>()
//					: repo.getListDPError(screenDto.getDateRange(), listEmployeeId);
//			if (lstError.size() > 0) {
//				// Get list error setting
//				List<DPErrorSettingDto> lstErrorSetting = this.repo
//						.getErrorSetting(lstError.stream().map(e -> e.getErrorCode()).collect(Collectors.toList()));
//				// Seperate Error and Alarm
//				//screenDto.addErrorToResponseData(lstError, lstErrorSetting);
//			}
//		}
//		return lstError.stream().collect(Collectors.toMap(e -> e.getEmployeeId(), e -> ""));
//	}
//
//	private List<DailyPerformanceEmployeeDto> extractEmployeeList(List<String> sId, DateRange range) {
//		return getListEmployee(sId, range);
//	}
//	/**
//	 * アルゴリズム「表示項目を制御する」を実行する | Execute the algorithm "control display items"
//	 */
//	private DisplayItem getItemIds(List<String> lstEmployeeId, DateRange dateRange,
//			CorrectionOfDailyPerformance correct, List<String> formatCodeSelects,
//			OperationOfDailyPerformanceDto dailyPerformanceDto, String companyId) {
//		DisplayItem result = new DisplayItem();
//		if (lstEmployeeId.size() > 0) {
//			// 取得したドメインモデル「日別実績の運用」をチェックする | Check the acquired domain model
//			// "Operation of daily performance"
//			List<FormatDPCorrectionDto> lstFormat = new ArrayList<FormatDPCorrectionDto>();
//			List<DPSheetDto> lstSheet = new ArrayList<DPSheetDto>();
//			List<Integer> lstAtdItem = new ArrayList<>();
//			List<Integer> lstAtdItemUnique = new ArrayList<>();
//			if (dailyPerformanceDto != null && dailyPerformanceDto.getSettingUnit() == SettingUnit.AUTHORITY) {
//				// setting button A2_4
//				result.setSettingUnit(true);
//				List<AuthorityFomatDailyDto> authorityFomatDailys = new ArrayList<>();
//				List<AuthorityFormatSheetDto> authorityFormatSheets = new ArrayList<>();
//				// アルゴリズム「社員の権限に対応する表示項目を取得する」を実行する
//				// kiem tra thong tin rieng biet user
//				if (correct == null) {
//					if (formatCodeSelects.isEmpty()) {
//						List<AuthorityFormatInitialDisplayDto> initialDisplayDtos = repo
//								.findAuthorityFormatInitialDisplay(companyId);
//						if (!initialDisplayDtos.isEmpty()) {
//							List<String> formatCodes = initialDisplayDtos.stream()
//									.map(x -> x.getDailyPerformanceFormatCode()).collect(Collectors.toList());
//							result.setFormatCode(formatCodes.stream().collect(Collectors.toSet()));
//							// Lấy về domain model "会社の日別実績の修正のフォーマット" tương ứng
//							authorityFomatDailys = repo.findAuthorityFomatDaily(companyId, formatCodes);
//							List<BigDecimal> sheetNos = authorityFomatDailys.stream().map(x -> x.getSheetNo())
//									.collect(Collectors.toList());
//							authorityFormatSheets = sheetNos.isEmpty() ? Collections.emptyList()
//									: repo.findAuthorityFormatSheet(companyId, formatCodes, sheetNos);
//						} else {
//							// アルゴリズム「表示項目の選択を起動する」を実行する
//							/// 画面「表示フォーマットの選択」をモーダルで起動する(Chạy màn hình "Select
//							// display format" theo cách thức) -- chay man hinh
//							// C
//							throw new BusinessException("");
//						}
//					} else {
//						result.setFormatCode(formatCodeSelects.stream().collect(Collectors.toSet()));
//						authorityFomatDailys = repo.findAuthorityFomatDaily(companyId, formatCodeSelects);
//						List<BigDecimal> sheetNos = authorityFomatDailys.stream().map(x -> x.getSheetNo())
//								.collect(Collectors.toList());
//						authorityFormatSheets = repo.findAuthorityFormatSheet(companyId, formatCodeSelects, sheetNos);
//					}
//				} else {
//					// Lấy về domain model "会社の日別実績の修正のフォーマット" tương ứng
//					List<String> formatCodes = Arrays.asList(correct.getPreviousDisplayItem());
//					result.setFormatCode(formatCodes.stream().collect(Collectors.toSet()));
//					authorityFomatDailys = repo.findAuthorityFomatDaily(companyId, formatCodes);
//					List<BigDecimal> sheetNos = authorityFomatDailys.stream().map(x -> x.getSheetNo())
//							.collect(Collectors.toList());
//					authorityFormatSheets = sheetNos.isEmpty() ? Collections.emptyList()
//							: repo.findAuthorityFormatSheet(companyId, formatCodes, sheetNos);
//				}
//				if (!authorityFomatDailys.isEmpty()) {
//					// set FormatCode for button A2_4
//					lstSheet = authorityFormatSheets.stream()
//							.map(x -> new DPSheetDto(x.getSheetNo().toString(), x.getSheetName().toString()))
//							.collect(Collectors.toList());
//					Set<String> lstSheetNo = lstSheet.stream().map(DPSheetDto::getName).collect(Collectors.toSet());
//					if (lstSheetNo.size() != lstSheet.size()) {
//						lstSheet = lstSheet.stream().map(x -> new DPSheetDto(x.getName(), x.getName()))
//								.collect(Collectors.toList());
//					}
//					lstFormat = authorityFomatDailys.stream()
//							.map(x -> new FormatDPCorrectionDto(companyId, x.getDailyPerformanceFormatCode(),
//									x.getAttendanceItemId(), x.getSheetNo().toString(), x.getDisplayOrder(),
//									x.getColumnWidth().intValue()))
//							.collect(Collectors.toList());
//					lstAtdItem = lstFormat.stream().map(f -> f.getAttendanceItemId()).collect(Collectors.toList());
//					lstAtdItemUnique = new HashSet<Integer>(lstAtdItem).stream().collect(Collectors.toList());
//
//				}
//			} else {
//				// setting button A2_4
//				result.setSettingUnit(false);
//				// アルゴリズム「社員の勤務種別に対応する表示項目を取得する」を実行する
//				/// アルゴリズム「社員の勤務種別をすべて取得する」を実行する
//				List<String> lstBusinessTypeCode = this.repo.getListBusinessType(lstEmployeeId, dateRange);
//				List<DPBusinessTypeControl> lstDPBusinessTypeControl = new ArrayList<>();
//				if (lstFormat.size() > 0) {
//					lstDPBusinessTypeControl = this.repo.getListBusinessTypeControl(lstBusinessTypeCode,
//							lstAtdItemUnique);
//				}
//				// Create header & sheet
//				if (lstBusinessTypeCode.size() > 0) {
//
//					lstSheet = this.repo.getFormatSheets(lstBusinessTypeCode);
//					Set<String> lstSheetNo = lstSheet.stream().map(DPSheetDto::getName).collect(Collectors.toSet());
//					if (lstSheetNo.size() != lstSheet.size()) {
//						lstSheet = lstSheet.stream().map(x -> new DPSheetDto(x.getName(), x.getName()))
//								.collect(Collectors.toList());
//					}
//					/// 対応するドメインモデル「勤務種別日別実績の修正のフォーマット」を取得する
//					lstFormat = this.repo.getListFormatDPCorrection(lstBusinessTypeCode); // 10s
//					lstAtdItem = lstFormat.stream().map(f -> f.getAttendanceItemId()).collect(Collectors.toList());
//					lstAtdItemUnique = new HashSet<Integer>(lstAtdItem).stream().collect(Collectors.toList());
//				}
//				result.setLstBusinessTypeCode(lstDPBusinessTypeControl);
//			}
//			result.setLstFormat(lstFormat);
//			result.setLstSheet(lstSheet);
//			result.setLstAtdItemUnique(lstAtdItemUnique);
//			result.setBussiness(dailyPerformanceDto.getSettingUnit().value);
//		}
//		return result;
//	}
//
//	/**
//	 * アルゴリズム「表示項目を制御する」を実行する | Execute the algorithm "control display items"
//	 */
//	private DPControlDisplayItem getItemIdNames(DisplayItem disItem) {
//		DPControlDisplayItem result = new DPControlDisplayItem();
//		result.setFormatCode(disItem.getFormatCode());
//		result.setSettingUnit(disItem.isSettingUnit());
//		List<DPAttendanceItem> lstAttendanceItem = new ArrayList<>();
//		Map<Integer, DPAttendanceItem> mapDP = new HashMap<>();
//		List<Integer> lstAtdItemUnique = disItem.getLstAtdItemUnique();
//		List<FormatDPCorrectionDto> lstFormat = disItem.getLstFormat();
//		if (!lstAtdItemUnique.isEmpty()) {
//			Map<Integer, String> itemName = dailyAttendanceItemNameAdapter
//					.getDailyAttendanceItemName(lstAtdItemUnique).stream()
//					.collect(Collectors.toMap(DailyAttendanceItemNameAdapterDto::getAttendanceItemId, x -> x.getAttendanceItemName())); // 9s
//			lstAttendanceItem = lstAtdItemUnique.isEmpty() ? Collections.emptyList()
//					: this.repo.getListAttendanceItem(lstAtdItemUnique).stream()
//							.map(x -> {
//								x.setName(itemName.get(x.getId()));
//								return x;
//							}).collect(Collectors.toList());
//		}
//		result.createSheets(disItem.getLstSheet());
//		mapDP = lstAttendanceItem.stream().collect(Collectors.toMap(DPAttendanceItem::getId, x -> x));
//		result.addColumnsToSheet(lstFormat, mapDP, true);
//		List<DPHeaderDto> lstHeader = new ArrayList<>();
//		for (FormatDPCorrectionDto dto : lstFormat) {
//			lstHeader.add(DPHeaderDto.createSimpleHeader(mergeString(ADD_CHARACTER, String.valueOf(dto.getAttendanceItemId())),
//					String.valueOf(dto.getColumnWidth()) + PX, mapDP));
//		}
//		result.setLstHeader(lstHeader);
//		if (!disItem.isSettingUnit()) {
//			if (disItem.getLstBusinessTypeCode().size() > 0) {
//				// set header access modifier
//				// only user are login can edit or others can edit
//				result.setColumnsAccessModifier(disItem.getLstBusinessTypeCode());
//			}
//		}
//		for (DPHeaderDto key : result.getLstHeader()) {
//			ColumnSetting columnSetting = new ColumnSetting(key.getKey(), false);
//			if (!key.getGroup().isEmpty()) {
//				result.getColumnSettings().add(new ColumnSetting(key.getGroup().get(0).getKey(), false));
//				result.getColumnSettings().add(new ColumnSetting(key.getGroup().get(1).getKey(), false));
//			} else {
//				/*
//				 * 時間 - thoi gian hh:mm 5, 回数: so lan 2, 金額 : so tien 3, 日数: so
//				 * ngay -
//				 */
//				DPAttendanceItem dPItem = mapDP
//						.get(Integer.parseInt(key.getKey().substring(1, key.getKey().length()).trim()));
//				columnSetting.setTypeFormat(dPItem.getAttendanceAtr());
//			}
//			result.getColumnSettings().add(columnSetting);
//
//		}
//		if (!lstAttendanceItem.isEmpty()) {
//			// set text to header
//			result.setHeaderText(lstAttendanceItem);
//			// set color to header
//			List<DPAttendanceItemControl> lstAttendanceItemControl = this.repo
//					.getListAttendanceItemControl(lstAtdItemUnique);
//			result.setLstAttendanceItem(lstAttendanceItem);
//			result.getLstAttendanceItem().stream().forEach(c -> c.setName(""));
//			result.setHeaderColor(lstAttendanceItemControl);
//		} else {
//			result.setLstAttendanceItem(lstAttendanceItem);
//		}
//		// set combo box
//		result.setComboItemCalc(EnumCodeName.getCalcHours());
//		result.setComboItemDoWork(EnumCodeName.getDowork());
//		result.setComboItemReason(EnumCodeName.getReasonGoOut());
//		result.setItemIds(lstAtdItemUnique);
//		return result;
//	}
//
//	/**
//	 * Get List Data include:<br/>
//	 * Employee and Date
//	 **/
//	private List<DPDataDto> getListData(List<DailyPerformanceEmployeeDto> listEmployee, DateRange dateRange) {
//		List<DPDataDto> result = new ArrayList<>();
//		if (listEmployee.size() > 0) {
//			List<GeneralDate> lstDate = dateRange.toListDate();
//			int dataId = 0;
//			for (int j = 0; j < listEmployee.size(); j++) {
//				DailyPerformanceEmployeeDto employee = listEmployee.get(j);
//				for (int i = 0; i < lstDate.size(); i++) {
//					GeneralDate filterDate = lstDate.get(i);
//					result.add(new DPDataDto("1"+"_"+employee.getId()+"_"+converDateToString(filterDate)+"_"+converDateToString(dateRange.getEndDate()), "", "", filterDate, false, employee.getId(), employee.getCode(),
//							employee.getBusinessName(),  employee.getWorkplaceId(), "", ""));
//					dataId++;
//				}
//			}
//		}
//		return result;
//	}
//}
