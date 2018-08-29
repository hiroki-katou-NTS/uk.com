package nts.uk.screen.at.app.dailyperformance.correction.selectitem;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.TreeSet;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.record.dom.optitem.OptionalItem;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemAtr;
import nts.uk.ctx.at.record.dom.optitem.OptionalItemRepository;
import nts.uk.ctx.at.record.dom.workinformation.enums.CalculationState;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.SettingUnitType;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemIdContainer;
import nts.uk.ctx.at.shared.dom.attendance.util.AttendanceItemUtil.AttendanceItemType;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapterDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.DailyAttendanceAtr;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.screen.at.app.dailymodify.query.DailyModifyQuery;
import nts.uk.screen.at.app.dailymodify.query.DailyModifyQueryProcessor;
import nts.uk.screen.at.app.dailymodify.query.DailyModifyResult;
import nts.uk.screen.at.app.dailyperformance.correction.DailyPerformanceScreenRepo;
import nts.uk.screen.at.app.dailyperformance.correction.DisplayRemainingHolidayNumber;
import nts.uk.screen.at.app.dailyperformance.correction.datadialog.CodeName;
import nts.uk.screen.at.app.dailyperformance.correction.datadialog.DataDialogWithTypeProcessor;
import nts.uk.screen.at.app.dailyperformance.correction.datadialog.ParamDialog;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ActualLockDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.AffEmploymentHistoryDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.AuthorityFomatDailyDto;
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
import nts.uk.screen.at.app.dailyperformance.correction.dto.DateRange;
import nts.uk.screen.at.app.dailyperformance.correction.dto.FormatDPCorrectionDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.OperationOfDailyPerformanceDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.WorkFixedDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.WorkInfoOfDailyPerformanceDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.checkshowbutton.DailyPerformanceAuthorityDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.type.TypeLink;
import nts.uk.shr.com.context.AppContexts;
import nts.uk.shr.com.i18n.TextResource;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

@Stateless
public class DailyPerformanceSelectItemProcessor {

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
	private OptionalItemRepository optionalItemRepository;
	
	@Inject
	private DisplayRemainingHolidayNumber remainHolidayService;
	
	private static final String CODE = "Code";
	private static final String NAME = "Name";
	private static final String NO = "NO";
    private static final String LOCK_DATE = "date";
	private static final String LOCK_EMP_CODE = "employeeCode";
	private static final String LOCK_EMP_NAME = "employeeName";
	private static final String LOCK_ERROR = "error";
	private static final String LOCK_SIGN = "sign";
	private static final String LOCK_PIC = "picture-person";
	private static final String ADD_CHARACTER = "A";
	private static final String PX = "px";
	private static final String TYPE_LABEL = "label";
	private static final String FORMAT_HH_MM = "%d:%02d";
	private static final String TYPE_LINK = "Link2";
	private static final String LOCK_EDIT_CELL_DAY = "D";
	private static final String LOCK_EDIT_CELL_MONTH = "M";
	private static final String LOCK_EDIT_CELL_WORK = "C";
	private static final String STATE_DISABLE = "mgrid-disable";

	/**
	 * アルゴリズム「表示項目を制御する」を実行する | Execute the algorithm "control display items"
	 */
	private DPControlDisplayItem getControlDisplayItems(List<String> lstEmployeeId, DateRange dateRange,
			CorrectionOfDailyPerformance correct, List<String> formatCodes, OperationOfDailyPerformanceDto dailyPerformanceDto) {
		DPControlDisplayItem result = new DPControlDisplayItem();
		String companyId = AppContexts.user().companyId();
		if (lstEmployeeId.size() > 0) {
			// 対応するドメインモデル「日別実績の運用」を取得する | Acquire corresponding domain model
			// 取得したドメインモデル「日別実績の運用」をチェックする | Check the acquired domain model
			// "Operation of daily performance"
			List<FormatDPCorrectionDto> lstFormat = new ArrayList<FormatDPCorrectionDto>();
			List<DPSheetDto> lstSheet = new ArrayList<DPSheetDto>();
			List<Integer> lstAtdItem = new ArrayList<>();
			List<Integer> lstAtdItemUnique = new ArrayList<>();
			List<DPAttendanceItem> lstAttendanceItem = new ArrayList<>();
			List<DPBusinessTypeControl> lstDPBusinessTypeControl = new ArrayList<>();
			Map<Integer, DPAttendanceItem> mapDP = new HashMap<>();
			if (dailyPerformanceDto != null && dailyPerformanceDto.getSettingUnit() == SettingUnitType.AUTHORITY) {
				List<AuthorityFomatDailyDto> authorityFomatDailys = new ArrayList<>();
				List<AuthorityFormatSheetDto> authorityFormatSheets = new ArrayList<>();
				// アルゴリズム「社員の権限に対応する表示項目を取得する」を実行する
				// kiem tra thong tin rieng biet user
				if (correct == null) {
						// Lấy về domain model "会社の日別実績の修正のフォーマット" tương ứng
						authorityFomatDailys = repo.findAuthorityFomatDaily(companyId, formatCodes);
						// set FormatCode for button A2_4
						result.setFormatCode(formatCodes.stream().collect(Collectors.toSet()));
						List<BigDecimal> sheetNos = authorityFomatDailys.stream().map(x -> x.getSheetNo())
								.collect(Collectors.toList());
						if(!formatCodes.isEmpty() && !sheetNos.isEmpty())
						authorityFormatSheets = repo.findAuthorityFormatSheet(companyId, formatCodes, sheetNos);
				} else {
					// Lấy về domain model "会社の日別実績の修正のフォーマット" tương ứng
					// set FormatCode for button A2_4
					result.setFormatCode(formatCodes.stream().collect(Collectors.toSet()));
					authorityFomatDailys = repo.findAuthorityFomatDaily(companyId, formatCodes);
					List<BigDecimal> sheetNos = authorityFomatDailys.stream().map(x -> x.getSheetNo())
							.collect(Collectors.toList());
					authorityFormatSheets = repo.findAuthorityFormatSheet(companyId, formatCodes, sheetNos);
				}
				if (!authorityFomatDailys.isEmpty()) {
					lstFormat = new ArrayList<FormatDPCorrectionDto>();
					lstSheet = new ArrayList<DPSheetDto>();
					lstSheet = authorityFormatSheets.stream().map(x -> new DPSheetDto(x.getSheetNo().toString(), x.getSheetName().toString()))
							.collect(Collectors.toList());
					Set<String> lstSheetNo = lstSheet.stream().map(DPSheetDto :: getName).collect(Collectors.toSet());
					if(lstSheetNo.size() != lstSheet.size()){
						lstSheet = lstSheet.stream().map(x -> new DPSheetDto(x.getName(), x.getName())).collect(Collectors.toList());
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
												x.getTypeGroup(), x.getPrimitive()))
										.collect(Collectors.toList());
						Map<Integer, Integer> optionalItemOpt = AttendanceItemIdContainer.optionalItemIdsToNos(lstAtdItemUnique, AttendanceItemType.DAILY_ITEM);
						Map<Integer, OptionalItemAtr> optionalItemAtrOpt= optionalItemOpt.isEmpty() ? Collections.emptyMap()
								: optionalItemRepository.findByListNos(companyId, new ArrayList<>(optionalItemOpt.values())).stream()
										.filter(x -> x.getOptionalItemNo() != null && x.getOptionalItemAtr() != null)
										.collect(Collectors.toMap(x -> x.getOptionalItemNo().v(), OptionalItem::getOptionalItemAtr));
						mapDP = lstAttendanceItem.stream().collect(Collectors.toMap(DPAttendanceItem::getId, x -> x));
					}
					List<DPHeaderDto> lstHeader = new ArrayList<>();
					for (FormatDPCorrectionDto dto : lstFormat) {
						// chia cot con code name cua AttendanceItemId chinh va
						// set
						lstHeader.add(DPHeaderDto.createSimpleHeader(companyId, ADD_CHARACTER+String.valueOf(dto.getAttendanceItemId()),
								String.valueOf(dto.getColumnWidth()) + PX, mapDP));
					}
					result.setLstHeader(lstHeader);
					// result.setLstSheet(lstSheet);
					result.createSheets(lstSheet);
					result.addColumnsToSheet(lstFormat, mapDP, true);
				}
			} else {
				// アルゴリズム「社員の勤務種別に対応する表示項目を取得する」を実行する
				/// アルゴリズム「社員の勤務種別をすべて取得する」を実行する
				List<String> lstBusinessTypeCode = this.repo.getListBusinessType(lstEmployeeId, dateRange);

				// Create header & sheet
				if (lstBusinessTypeCode.size() > 0) {

					lstSheet = this.repo.getFormatSheets(lstBusinessTypeCode);
					Set<String> lstSheetNo = lstSheet.stream().map(DPSheetDto :: getName).collect(Collectors.toSet());
					if(lstSheetNo.size() != lstSheet.size()){
						lstSheet = lstSheet.stream().map(x -> new DPSheetDto(x.getName(), x.getName())).collect(Collectors.toList());
					}
					/// 対応するドメインモデル「勤務種別日別実績の修正のフォーマット」を取得する
					lstFormat = this.repo.getListFormatDPCorrection(lstBusinessTypeCode);
					lstAtdItem = lstFormat.stream().map(f -> f.getAttendanceItemId()).collect(Collectors.toList());
					lstAtdItemUnique = new HashSet<Integer>(lstAtdItem).stream().collect(Collectors.toList());
					result.createSheets(lstSheet);
					if (!lstAtdItemUnique.isEmpty()) {
						Map<Integer, DailyAttendanceItemNameAdapterDto> itemName = dailyAttendanceItemNameAdapter
								.getDailyAttendanceItemName(lstAtdItemUnique).stream().collect(Collectors
										.toMap(DailyAttendanceItemNameAdapterDto::getAttendanceItemId, x -> x));
						lstAttendanceItem = lstAtdItemUnique.isEmpty() ? Collections.emptyList()
								: this.repo.getListAttendanceItem(lstAtdItemUnique).stream()
										.map(x -> new DPAttendanceItem(x.getId(),
												itemName.get(x.getId()).getAttendanceItemName(), x.getDisplayNumber(),
												x.isUserCanSet(), x.getLineBreakPosition(), x.getAttendanceAtr(),
												x.getTypeGroup(), x.getPrimitive()))
										.collect(Collectors.toList());
						Map<Integer, Integer> optionalItemOpt = AttendanceItemIdContainer.optionalItemIdsToNos(lstAtdItemUnique, AttendanceItemType.DAILY_ITEM);
						Map<Integer, OptionalItemAtr> optionalItemAtrOpt= optionalItemOpt.isEmpty() ? Collections.emptyMap()
								: optionalItemRepository.findByListNos(companyId, new ArrayList<>(optionalItemOpt.values())).stream()
										.filter(x -> x.getOptionalItemNo() != null && x.getOptionalItemAtr() != null)
										.collect(Collectors.toMap(x -> x.getOptionalItemNo().v(), OptionalItem::getOptionalItemAtr));
						
						mapDP = lstAttendanceItem.stream().collect(Collectors.toMap(DPAttendanceItem::getId, x -> x));
					}
					result.addColumnsToSheet(lstFormat, mapDP, true);
					List<DPHeaderDto> lstHeader = new ArrayList<>();
					for (FormatDPCorrectionDto dto : lstFormat) {
						lstHeader.add(DPHeaderDto.createSimpleHeader(companyId, ADD_CHARACTER+String.valueOf(dto.getAttendanceItemId()),
								String.valueOf(dto.getColumnWidth()) + PX, mapDP));
					}
					result.setLstHeader(lstHeader);
				}

			}
			// set text to header
			for (DPHeaderDto key : result.getLstHeader()) {
				ColumnSetting columnSetting = new ColumnSetting(key.getKey(), false);
				if(!key.getGroup().isEmpty()){
					result.getColumnSettings().add(new ColumnSetting(key.getGroup().get(0).getKey(), false));
					result.getColumnSettings().add(new ColumnSetting(key.getGroup().get(1).getKey(), false));
				}else{
					DPAttendanceItem dPItem  = mapDP.get(Integer.parseInt(key.getKey().substring(1, key.getKey().length()).trim()));
					columnSetting.setTypeFormat(dPItem.getAttendanceAtr());
				}
				result.getColumnSettings().add(columnSetting);

			};
			String authorityDailyID =  AppContexts.user().roles().forAttendance(); 
			if (lstFormat.size() > 0) {
				lstDPBusinessTypeControl = this.repo.getListBusinessTypeControl(companyId, authorityDailyID,
						lstAtdItemUnique, true);
			}
			if (lstDPBusinessTypeControl.size() > 0) {
				// set header access modifier
				// only user are login can edit or others can edit
				result.setColumnsAccessModifier(lstDPBusinessTypeControl);
			}
			if (!lstAttendanceItem.isEmpty()) {
				result.setHeaderText(lstAttendanceItem);
				// set color to header
				List<DPAttendanceItemControl> lstAttendanceItemControl = this.repo
						.getListAttendanceItemControl(lstAtdItemUnique);
				result.setLstAttendanceItem(lstAttendanceItem);
				result.setHeaderColor(lstAttendanceItemControl);
			}
		}
		return result;
	}

	/** アルゴリズム「休暇の管理状況をチェックする」を実行する */
	private void getHolidaySettingData(DailyPerformanceCorrectionDto dailyPerformanceCorrectionDto) {
//		String companyId = AppContexts.user().companyId();
//		String employeeId = "";
//		GeneralDate baseDate = GeneralDate.today();
//		// アルゴリズム「年休設定を取得する」を実行する
//		dailyPerformanceCorrectionDto.setYearHolidaySettingDto(remainHolidayService.getAnnualLeaveSetting(companyId, employeeId, baseDate));
//		// アルゴリズム「振休管理設定を取得する」を実行する
//		dailyPerformanceCorrectionDto.setSubstVacationDto(remainHolidayService.getSubsitutionVacationSetting(companyId, employeeId, baseDate));
//		// アルゴリズム「代休管理設定を取得する」を実行する
//		dailyPerformanceCorrectionDto.setCompensLeaveComDto(remainHolidayService.getCompensatoryLeaveSetting(companyId, employeeId, baseDate));
//		// アルゴリズム「60H超休管理設定を取得する」を実行する
////		dailyPerformanceCorrectionDto.setCom60HVacationDto(this.repo.getCom60HVacationDto());
	}

	public DailyPerformanceCorrectionDto generateData(DateRange dateRange,
			List<DailyPerformanceEmployeeDto> lstEmployee, Integer initScreen, Integer displayFormat, CorrectionOfDailyPerformance correct,
			List<String> formatCodes) {
		String sId = AppContexts.user().employeeId();
		DailyPerformanceCorrectionDto screenDto = new DailyPerformanceCorrectionDto();
        String companyId = AppContexts.user().companyId();
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
		AffEmploymentHistoryDto employment = repo.getAffEmploymentHistory(companyId, sId, dateRange);
		screenDto.setEmploymentCode(employment == null ? "" : employment.getEmploymentCode());
		// アルゴリズム「休暇の管理状況をチェックする」を実行する | Get holiday setting data
		getHolidaySettingData(screenDto);

		/**
		 * アルゴリズム「表示形式に従って情報を取得する」を実行する | Execute "Get information according to
		 * display format"
		 */
		// アルゴリズム「対象者を抽出する」を実行する | Execute "Extract subject"
		if (lstEmployee.size() > 0) {
			screenDto.setLstEmployee(lstEmployee);
		} else {
			screenDto.setLstEmployee(getListEmployee(sId, screenDto.getDateRange()));
		}
		List<DailyPerformanceEmployeeDto> lstEmployeeData = new ArrayList<>();
		if(initScreen != null && initScreen == 0){
			lstEmployeeData = screenDto.getLstEmployee().stream().filter(x-> x.getId().equals(sId)).collect(Collectors.toList());
		}else{
			lstEmployeeData = screenDto.getLstEmployee();
		}
		// 表示形式をチェックする | Check display format => UI
		// Create lstData: Get by listEmployee & listDate
		// 日付別の情報を取得する + 個人別の情報を取得する + エラーアラームの情報を取得する | Acquire information by
		// date + Acquire personalized information + Acquire error alarm
		// information
		
		screenDto.setLstData(getListData(lstEmployeeData, dateRange));
		/// 対応する「日別実績」をすべて取得する | Acquire all corresponding "daily performance"
		List<String> listEmployeeId = lstEmployeeData.stream().map(e -> e.getId())
				.collect(Collectors.toList());
		
		/// アルゴリズム「対象日に対応する社員の実績の編集状態を取得する」を実行する | Execute "Acquire edit status
		/// of employee's record corresponding to target date"| lay ve trang
		/// thai sua cua thanh tich nhan vien tuong ung
		// --List<DailyRecEditSetDto> dailyRecEditSets =
		/// repo.getDailyRecEditSet(listEmployeeId, dateRange);
		/// アルゴリズム「実績エラーをすべて取得する」を実行する | Execute "Acquire all actual errors"
		List<DPErrorDto> lstError = new ArrayList<>();
		if (screenDto.getLstEmployee().size() > 0) {
			/// ドメインモデル「社員の日別実績エラー一覧」をすべて取得する +
			/// 対応するドメインモデル「勤務実績のエラーアラーム」をすべて取得する
			/// Acquire all domain model "employee's daily performance error
			/// list" + "work error error alarm" | lay loi thanh tich trong
			/// khoang thoi gian
			lstError = this.repo.getListDPError(screenDto.getDateRange(), listEmployeeId);
			if (lstError.size() > 0) {
				// Get list error setting
				List<DPErrorSettingDto> lstErrorSetting = this.repo
						.getErrorSetting(companyId, lstError.stream().map(e -> e.getErrorCode()).collect(Collectors.toList()));
				// Seperate Error and Alarm
				//screenDto.addErrorToResponseData(lstError, lstErrorSetting);
			}
		}

		// アルゴリズム「社員に対応する処理締めを取得する」を実行する | Execute "Acquire Process Tightening
		// Corresponding to Employees"--
		Map<String, String> employmentWithSidMap = repo.getAllEmployment(companyId, listEmployeeId,  new DateRange(GeneralDate.today(), GeneralDate.today()));
		List<ClosureDto> closureDtos = repo.getClosureId(employmentWithSidMap, dateRange.getEndDate());
		/// TODO : アルゴリズム「対象日に対応する承認者確認情報を取得する」を実行する | Execute "Acquire Approver
		/// Confirmation Information Corresponding to Target Date"
		// アルゴリズム「就業確定情報を取得する」を実行する
		/// アルゴリズム「日別実績のロックを取得する」を実行する (Tiến hành xử lý "Lấy về lock của thành tích theo ngày")
		Map<String, DatePeriod> employeeAndDateRange = new HashMap<>();
		if(!closureDtos.isEmpty()){
			closureDtos.forEach(x ->{
				DatePeriod datePeriod = closureService.getClosurePeriod(x.getClosureId(), new YearMonth(x.getClosureMonth()));
				Optional<ActualLockDto> actualLockDto = repo.findAutualLockById(AppContexts.user().companyId(), x.getClosureId());
				if(actualLockDto.isPresent()){
					if(actualLockDto.get().getDailyLockState()==1){
						employeeAndDateRange.put(x.getSid()+"|"+x.getClosureId()+"|"+LOCK_EDIT_CELL_DAY, datePeriod);
					};
					if(actualLockDto.get().getMonthlyLockState()==1){
						employeeAndDateRange.put(x.getSid()+"|"+x.getClosureId()+"|"+LOCK_EDIT_CELL_MONTH, datePeriod);
					}
				}
				//アルゴリズム「表示項目を制御する」を実行する | Execute "control display items"
				List<WorkFixedDto> workFixeds = repo.findWorkFixed(x.getClosureId(), x.getClosureMonth());
				for (WorkFixedDto workFixedOp : workFixeds) {
					employeeAndDateRange.put(x.getSid()+"|"+x.getClosureId().toString()+
							"|" + workFixedOp.getWkpId()+ "|"+LOCK_EDIT_CELL_WORK, datePeriod);
				}
			});
		}

		OperationOfDailyPerformanceDto dailyPerformanceDto = repo.findOperationOfDailyPerformance();
		//dailyPerformanceDto.setSettingUnit(SettingUnit.BUSINESS_TYPE);
		//dailyPerformanceDto.setSettingUnit(SettingUnit.BUSINESS_TYPE);
		screenDto.setComment(dailyPerformanceDto != null && dailyPerformanceDto.getComment() != null
				? dailyPerformanceDto.getComment() : null);
		DPControlDisplayItem dPControlDisplayItem = getControlDisplayItems(listEmployeeId, screenDto.getDateRange(),
				correct, formatCodes, dailyPerformanceDto);
		screenDto.setLstControlDisplayItem(dPControlDisplayItem);
		screenDto.getLstFixedHeader().forEach(column ->{
			screenDto.getLstControlDisplayItem().getColumnSettings().add(new ColumnSetting(column.getKey(), false));
		});
		if (displayFormat == 2) {
			// only filter data error
			Map<String, String> listEmployeeError = new HashMap<>();
			for(DPErrorDto dto: lstError){
				listEmployeeError.put(dto.getEmployeeId(), "");
			}
			listEmployeeId = listEmployeeId.stream().filter(x -> listEmployeeError.containsKey(x))
					.collect(Collectors.toList());
			screenDto.setLstData(screenDto.getLstData().stream().filter(x -> listEmployeeError.containsKey(x.getEmployeeId())).collect(Collectors.toList()));
		}
		/// 対応する「日別実績」をすべて取得する-- lay tat ca thanh tich theo ngay tuong ung
		//// 日別実績の勤務情報
		List<DailyModifyResult> results = new ArrayList<>();
		if(!dPControlDisplayItem.getItemIds().isEmpty()){
			for (int i = 0; i < listEmployeeId.size(); i++) {
				for (int j = 0; j < dateRange.toListDate().size(); j++) {
					DailyModifyResult result = dailyModifyQueryProcessor.initScreen(
							new DailyModifyQuery(listEmployeeId.get(i), dateRange.toListDate().get(j), null),
							dPControlDisplayItem.getItemIds());
					if (result != null)
						results.add(result);
				}
			}
		}
		Map<String, DailyModifyResult> resultDailyMap = results.stream()
				.collect(Collectors.toMap((x) -> x.getEmployeeId() + "|" + x.getDate(), Function.identity()));
		//// 11. Excel: 未計算のアラームがある場合は日付又は名前に表示する
		// Map<Integer, Integer> typeControl =
		//// lstAttendanceItem.stream().collect(Collectors.toMap(DPAttendanceItem::
		//// getId, DPAttendanceItem::getAttendanceAtr));
		List<WorkInfoOfDailyPerformanceDto> workInfoOfDaily = repo.getListWorkInfoOfDailyPerformance(listEmployeeId,
				dateRange);
		List<DPDataDto> lstData = new ArrayList<DPDataDto>();
		Map<Integer, DPAttendanceItem> mapDP = dPControlDisplayItem.getLstAttendanceItem() != null
				? dPControlDisplayItem.getLstAttendanceItem().stream()
						.collect(Collectors.toMap(DPAttendanceItem::getId, x -> x))
				: new HashMap<>();
	    Map<String, ItemValue> itemValueMap = new HashMap<>();
		for (DPDataDto data : screenDto.getLstData()) {
			boolean lock = false;
			if(!employeeAndDateRange.isEmpty()){
				for(int i = 1; i<= 5 ; i++){
					DatePeriod dateD = employeeAndDateRange.get(data.getEmployeeId()+"|"+i+"|"+LOCK_EDIT_CELL_DAY);
					DatePeriod dateM = employeeAndDateRange.get(data.getEmployeeId()+"|"+i+"|"+LOCK_EDIT_CELL_MONTH);
					DatePeriod dateC = employeeAndDateRange.get(data.getEmployeeId()+"|"+i+"|"+data.getWorkplaceId()+"|"+LOCK_EDIT_CELL_WORK);
					String lockD="";
					String lockM="";
					String lockC="";
					if((dateD != null && (data.getDate().afterOrEquals(dateD.start()) && data.getDate().beforeOrEquals(dateD.end())))){
						lockD ="|"+LOCK_EDIT_CELL_DAY;
					}
					if((dateM != null && (data.getDate().afterOrEquals(dateM.start()) && data.getDate().beforeOrEquals(dateM.end())))){
						lockM ="|"+LOCK_EDIT_CELL_MONTH;
					}
					if((dateC != null && (data.getDate().afterOrEquals(dateC.start()) && data.getDate().beforeOrEquals(dateC.end())))){
						lockC ="|"+LOCK_EDIT_CELL_WORK;
					}
					if(!lockD.equals("")|| !lockM.equals("")|| !lockC.equals("")){
						data.setState("lock"+lockD+lockM+lockC);
					    lock = true;
					}
				}
			}
			if(lock){
				screenDto.setCellSate(data.getId(), LOCK_DATE, STATE_DISABLE);
			    screenDto.setCellSate(data.getId(), LOCK_EMP_CODE, STATE_DISABLE);
			    screenDto.setCellSate(data.getId(), LOCK_EMP_NAME, STATE_DISABLE);
			    screenDto.setCellSate(data.getId(), LOCK_ERROR, STATE_DISABLE);
			    screenDto.setCellSate(data.getId(), LOCK_SIGN, STATE_DISABLE);
			    screenDto.setCellSate(data.getId(), LOCK_PIC, STATE_DISABLE);
			}
			DailyModifyResult resultOfOneRow = resultDailyMap.isEmpty() ? null : resultDailyMap.get(data.getEmployeeId()+"|"+data.getDate());
			if(resultOfOneRow != null){
				//List<ItemValue> attendanceTimes = resultOfOneRow.getItems().get("AttendanceTimeOfDailyPerformance");
				List<ItemValue> attendanceTimes = new ArrayList<>();
				resultOfOneRow.getItems().forEach(x ->{
					attendanceTimes.add(x);
				});
				screenDto.getItemValues().addAll(attendanceTimes);
				itemValueMap = attendanceTimes.isEmpty()? Collections.emptyMap(): attendanceTimes.stream().collect(Collectors.toMap(x -> x.itemId()+"|"+data.getEmployeeId()+"|"+data.getDate(), x -> x));
			}
			Set<DPCellDataDto> cellDatas = new HashSet<>();
			if (dPControlDisplayItem.getLstAttendanceItem() != null) {
				for (DPAttendanceItem item : dPControlDisplayItem.getLstAttendanceItem()){
					//int a = 1;
					int attendanceAtr = mapDP.get(item.getId()).getAttendanceAtr();
					String key = item.getId()+"|"+data.getEmployeeId()+"|"+data.getDate();
					String value = (itemValueMap.containsKey(key) && itemValueMap.get(key).value() != null) ? itemValueMap.get(key).value().toString() : "";
					if (attendanceAtr == DailyAttendanceAtr.Code.value
							|| attendanceAtr == DailyAttendanceAtr.Classification.value) {
						if(attendanceAtr == DailyAttendanceAtr.Code.value){
							if(lock){
								screenDto.setCellSate(data.getId(), CODE + String.valueOf(item.getId()), STATE_DISABLE);
								screenDto.setCellSate(data.getId(), NAME + String.valueOf(item.getId()), STATE_DISABLE);
							}
							cellDatas.add(new DPCellDataDto(CODE + String.valueOf(item.getId()), value ,
									String.valueOf(item.getAttendanceAtr()), TYPE_LABEL));
							if(value.equals("")){
								value = TextResource.localize("KDW003_82");
							}else{
								CodeName codeName = dataDialogWithTypeProcessor.getTypeDialog(TypeLink.valueOf(item.getTypeGroup()).value, new ParamDialog("", screenDto.getEmploymentCode(), data.getWorkplaceId(), data.getDate(), value, "", null, null));
								//CodeName codeName = null;
								value = (codeName == null) ? TextResource.localize("KDW003_81") : codeName.getName();
							}
							cellDatas.add(new DPCellDataDto(NAME + String.valueOf(item.getId()),
									value , String.valueOf(item.getAttendanceAtr()), TYPE_LINK));
							
						}else{
							if(lock){
								screenDto.setCellSate(data.getId(), NO + String.valueOf(item.getId()), STATE_DISABLE);
								screenDto.setCellSate(data.getId(), NAME + String.valueOf(item.getId()), STATE_DISABLE);
							}
							cellDatas.add(new DPCellDataDto(NO + String.valueOf(item.getId()), value ,
									String.valueOf(item.getAttendanceAtr()), TYPE_LABEL));
							cellDatas.add(new DPCellDataDto(NAME + String.valueOf(item.getId()),
									value , String.valueOf(item.getAttendanceAtr()), TYPE_LINK));
						}
						
					} else {
						if (lock) {
							screenDto.setCellSate(data.getId(), ADD_CHARACTER + String.valueOf(item.getId()), STATE_DISABLE);
						}
						if (attendanceAtr == DailyAttendanceAtr.Time.value
								|| attendanceAtr == DailyAttendanceAtr.TimeOfDay.value) {
							if (!value.equals("")) {
								// convert HH:mm
								int minute = Integer.parseInt(value);
								int hours = Math.abs(minute / 60);
								int minutes = Math.abs(minute) % 60;
								value = String.format(FORMAT_HH_MM, minute > 0 ? hours : 0 - hours, minutes);
								cellDatas.add(new DPCellDataDto(ADD_CHARACTER + String.valueOf(item.getId()), value,
										String.valueOf(item.getAttendanceAtr()), TYPE_LABEL));
							} else {
								cellDatas.add(new DPCellDataDto(ADD_CHARACTER + String.valueOf(item.getId()), value,
										String.valueOf(item.getAttendanceAtr()), TYPE_LABEL));
							}
						} else {
							cellDatas.add(new DPCellDataDto(ADD_CHARACTER + String.valueOf(item.getId()), value,
									String.valueOf(item.getAttendanceAtr()), TYPE_LABEL));
						}
					}
				};
			}
			data.setCellDatas(cellDatas);
			lstData.add(data);
			// DPCellDataDto bPCellDataDto = new DPCellDataDto(columnKey, value,
			// dataType, type);
			Optional<WorkInfoOfDailyPerformanceDto> optWorkInfoOfDailyPerformanceDto = workInfoOfDaily.stream()
					.filter(w -> w.getEmployeeId().equals(data.getEmployeeId()) && w.getYmd().equals(data.getDate()))
					.findFirst();
			if (optWorkInfoOfDailyPerformanceDto.isPresent()
					&& optWorkInfoOfDailyPerformanceDto.get().getState() == CalculationState.No_Calculated)
				screenDto.setAlarmCellForFixedColumn(data.getId(), 0);
		}
		Set<ItemValue> set = screenDto.getItemValues().stream()
	            .collect(Collectors.toCollection(() -> 
	                 new TreeSet<>(Comparator.comparing(ItemValue::getItemId))));
		screenDto.getItemValues().clear();
		screenDto.getItemValues().addAll(set);
		// screenDto.setLstData(lstData);
		screenDto.markLoginUser(sId);
		screenDto.createAccessModifierCellState(mapDP);
		return screenDto;
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
		return this.repo.getListEmployee(null, null, lstWorkplace, null);
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
					result.add(new DPDataDto(employee.getId()+"_"+dataId, "", "", filterDate, false, false,  employee.getId(), employee.getCode(),
							employee.getBusinessName(), employee.getWorkplaceId(), "", ""));
					dataId++;
				}
			}
		}
		return result;
	}
}
