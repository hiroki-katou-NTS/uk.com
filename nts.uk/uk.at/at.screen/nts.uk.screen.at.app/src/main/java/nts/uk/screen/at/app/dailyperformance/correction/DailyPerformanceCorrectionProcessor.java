/**
 * 1:57:38 PM Aug 21, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction;

import java.math.BigDecimal;
import java.text.Format;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.Date;
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

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.time.GeneralDate;
import nts.arc.time.YearMonth;
import nts.uk.ctx.at.auth.dom.employmentrole.EmployeeReferenceRange;
import nts.uk.ctx.at.function.dom.adapter.AffComHistItemImport;
import nts.uk.ctx.at.function.dom.adapter.AffCompanyHistImport;
import nts.uk.ctx.at.function.dom.adapter.EmployeeHistWorkRecordAdapter;
import nts.uk.ctx.at.function.dom.adapter.WorkPlaceHistImport;
import nts.uk.ctx.at.function.dom.adapter.WorkPlaceIdAndPeriodImport;
import nts.uk.ctx.at.function.dom.adapter.WorkplaceWorkRecordAdapter;
import nts.uk.ctx.at.function.dom.adapter.person.EmployeeInfoFunAdapter;
import nts.uk.ctx.at.function.dom.adapter.person.EmployeeInfoFunAdapterDto;
import nts.uk.ctx.at.record.dom.adapter.query.employee.RegulationInfoEmployeeQuery;
import nts.uk.ctx.at.record.dom.adapter.query.employee.RegulationInfoEmployeeQueryAdapter;
import nts.uk.ctx.at.record.dom.adapter.query.employee.RegulationInfoEmployeeQueryR;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.ApprovalStatusAdapter;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApprovalRootOfEmployeeImport;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.dtos.ApproveRootStatusForEmpImport;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.enums.ApprovalActionByEmpl;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.enums.ApprovalStatusForEmployee;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.enums.ApproverEmployeeState;
import nts.uk.ctx.at.record.dom.adapter.workflow.service.enums.ReleasedProprietyDivision;
import nts.uk.ctx.at.record.dom.divergence.time.DivergenceTimeUseSet;
import nts.uk.ctx.at.record.dom.workinformation.enums.CalculationState;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.ConfirmOfManagerOrYouself;
import nts.uk.ctx.at.record.dom.workrecord.operationsetting.SettingUnit;
import nts.uk.ctx.at.record.dom.worktime.TimeActualStamp;
import nts.uk.ctx.at.record.dom.worktime.TimeLeavingOfDailyPerformance;
import nts.uk.ctx.at.record.dom.worktime.WorkStamp;
import nts.uk.ctx.at.record.dom.worktime.enums.StampSourceInfo;
import nts.uk.ctx.at.record.dom.worktime.repository.TimeLeavingOfDailyPerformanceRepository;
import nts.uk.ctx.at.request.app.find.application.applicationlist.ApplicationExportDto;
import nts.uk.ctx.at.request.app.find.application.applicationlist.ApplicationListForScreen;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapter;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.DailyAttendanceItemNameAdapterDto;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.DailyAttendanceAtr;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmployment;
import nts.uk.ctx.at.shared.dom.workrule.closure.ClosureEmploymentRepository;
import nts.uk.ctx.at.shared.dom.workrule.closure.service.ClosureService;
import nts.uk.ctx.at.shared.pub.workrule.closure.PresentClosingPeriodExport;
import nts.uk.ctx.at.shared.pub.workrule.closure.ShClosurePub;
import nts.uk.screen.at.app.dailymodify.query.DailyModifyQueryProcessor;
import nts.uk.screen.at.app.dailymodify.query.DailyModifyResult;
import nts.uk.screen.at.app.dailyperformance.correction.datadialog.CodeName;
import nts.uk.screen.at.app.dailyperformance.correction.datadialog.CodeNameType;
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
import nts.uk.screen.at.app.dailyperformance.correction.dto.DivergenceTimeDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ErrorReferenceDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.FormatDPCorrectionDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.IdentityProcessUseSetDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ObjectShare;
import nts.uk.screen.at.app.dailyperformance.correction.dto.OperationOfDailyPerformanceDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ScreenMode;
import nts.uk.screen.at.app.dailyperformance.correction.dto.WorkFixedDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.WorkInfoOfDailyPerformanceDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.checkapproval.ApproveRootStatusForEmpDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.checkshowbutton.DailyPerformanceAuthorityDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.type.TypeLink;
import nts.uk.screen.at.app.dailyperformance.correction.flex.FlexInfoDisplay;
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

	@Inject
	private ClosureEmploymentRepository closureEmploymentRepository;

	@Inject
	private ShClosurePub shClosurePub;

	@Inject
	private ApplicationListForScreen applicationListFinder;
	
	@Inject
	private ApprovalStatusAdapter approvalStatusAdapter;

	@Inject
	private RegulationInfoEmployeeQueryAdapter regulationInfoEmployeePub;
	
	@Inject
	private EmployeeHistWorkRecordAdapter employeeHistWorkRecordAdapter;
	
	@Inject
	private EmployeeInfoFunAdapter employeeInfoFunAdapter;
	
	@Inject
	private WorkplaceWorkRecordAdapter workplaceWorkRecordAdapter;
	
	@Inject
	private FlexInfoDisplay flexInfoDisplay;
	
	@Inject
	private TimeLeavingOfDailyPerformanceRepository timeLeavingOfDailyPerformanceRepository;
	
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
	private static final String LOCK_EDIT_APPROVAL = "A";
	private static final String DATE_FORMAT = "yyyy-MM-dd";
	private static final String LOCK_APPLICATION = "Application";
	private static final String COLUMN_SUBMITTED = "Submitted";
	public static final int MINUTES_OF_DAY = 24 * 60;
	private static final String STATE_DISABLE = "ntsgrid-disable";
	private static final String HAND_CORRECTION_MYSELF = "ntsgrid-manual-edit-target";
	private static final String HAND_CORRECTION_OTHER = "ntsgrid-manual-edit-other";
	private static final String REFLECT_APPLICATION = "ntsgrid-reflect";
	private static final String STATE_ERROR ="ntsgrid-error";
	
    static final Integer[] DEVIATION_REASON  = {436, 438, 439, 441, 443, 444, 446, 448, 449, 451, 453, 454, 456, 458, 459, 799, 801, 802, 804, 806, 807, 809, 811, 812, 814, 816, 817, 819, 821, 822};
	public static final Map<Integer, Integer> DEVIATION_REASON_MAP = IntStream.range(0, DEVIATION_REASON.length-1).boxed().collect(Collectors.toMap(x -> DEVIATION_REASON[x], x -> x/3 +1));
	
	/**
	 * Get List Data include:<br/>
	 * Employee and Date
	 **/
	private List<DPDataDto> getListData(List<DailyPerformanceEmployeeDto> listEmployee, DateRange dateRange,
			Integer displayFormat) {
		List<DPDataDto> result = new ArrayList<>();
		if (listEmployee.size() > 0) {
			List<GeneralDate> lstDate = dateRange.toListDate();
			int dataId = 0;
			for (int j = 0; j < listEmployee.size(); j++) {
				DailyPerformanceEmployeeDto employee = listEmployee.get(j);
				for (int i = 0; i < lstDate.size(); i++) {
					result.add(new DPDataDto(
							displayFormat + "_" + convertFormatString(employee.getId()) + "_" + convertFormatString(converDateToString(lstDate.get(i))) + "_"
									+ convertFormatString(converDateToString(lstDate.get(lstDate.size() - 1))) + "_" + dataId,
							"", "", lstDate.get(i), false, false, employee.getId(), employee.getCode(),
							employee.getBusinessName(), employee.getWorkplaceId(), "", ""));
					dataId++;
				}
			}
		}
		return result;
	}
    
	private String convertFormatString(String data){
		return data.replace("-", "_");
	}
	private String converDateToString(GeneralDate genDate) {
		Format formatter = new SimpleDateFormat(DATE_FORMAT);
		return formatter.format(genDate.date());
	}

	public DailyPerformanceCorrectionDto generateData(DateRange dateRange,
			List<DailyPerformanceEmployeeDto> lstEmployee, Integer initScreen, Integer mode, Integer displayFormat,
			CorrectionOfDailyPerformance correct, List<String> formatCodes, ObjectShare objectShare) {
		long timeStart = System.currentTimeMillis();
		String sId = AppContexts.user().employeeId();
		String NAME_EMPTY = TextResource.localize("KDW003_82");
		String NAME_NOT_FOUND = TextResource.localize("KDW003_81");
		String companyId = AppContexts.user().companyId();
		DailyPerformanceCorrectionDto screenDto = new DailyPerformanceCorrectionDto();
		
		// identityProcessDto show button A2_6
		//アルゴリズム「本人確認処理の利用設定を取得する」を実行する
		Optional<IdentityProcessUseSetDto> identityProcessDtoOpt = repo.findIdentityProcessUseSet(companyId);
		screenDto.setIdentityProcessDto(identityProcessDtoOpt.isPresent() ? identityProcessDtoOpt.get()
				: new IdentityProcessUseSetDto(false, false, null));
		Optional<ApprovalUseSettingDto> approvalUseSettingDtoOpt = repo.findApprovalUseSettingDto(companyId);		
		
		// アルゴリズム「休暇の管理状況をチェックする」を実行する | Get holiday setting data --休暇の管理状況をチェックする
		getHolidaySettingData(screenDto);
		
		//<<Public>> パラメータに初期値を設定する
		///期間を変更する
		if (dateRange == null) {
			if (objectShare != null && objectShare.getStartDate() != null && objectShare.getEndDate() != null) {
				// get employmentCode
				dateRange = new DateRange(objectShare.getStartDate(), objectShare.getEndDate());
				screenDto.setEmploymentCode(getEmploymentCode(dateRange, sId));
			} else {
				Optional<ClosureEmployment> closureEmploymentOptional = this.closureEmploymentRepository
						.findByEmploymentCD(companyId,
								getEmploymentCode(new DateRange(null, GeneralDate.today()), sId));
				if (closureEmploymentOptional.isPresent()) {
					Optional<PresentClosingPeriodExport> closingPeriod = shClosurePub.find(companyId,
							closureEmploymentOptional.get().getClosureId());
					if (closingPeriod.isPresent()) {
						dateRange = new DateRange(closingPeriod.get().getClosureStartDate(),
								closingPeriod.get().getClosureEndDate());
					} else {
						dateRange = new DateRange(GeneralDate.legacyDate(new Date()).addMonths(-1).addDays(+1),
								GeneralDate.legacyDate(new Date()));
					}
				} else {
					dateRange = new DateRange(GeneralDate.legacyDate(new Date()).addMonths(-1).addDays(+1),
							GeneralDate.legacyDate(new Date()));
				}
			}
		}
		/**
		 * システム日付を基準に1ヵ月前の期間を設定する | Set date range one month before system date
		 */
		/** 画面制御に関する情報を取得する | Acquire information on screen control */
		// アルゴリズム「社員の日別実績の権限をすべて取得する」を実行する | Execute "Acquire all permissions of
		// employee's daily performance"--
		screenDto.setAuthorityDto(getAuthority(screenDto));
		///TODO 表示形式を変更する -- get from Characteristic 
		if(initScreen == 0 && objectShare != null && objectShare.getDisplayFormat() == 1){
			dateRange = new DateRange(objectShare.getDateTarget(), objectShare.getDateTarget());
		}
		screenDto.setDateRange(dateRange);
		///TODO 社員一覧を変更する -- Lấy nhân viên từ màn hinh khác hoặc lấy từ lần khởi động đầu tiên
		List<String> changeEmployeeIds = new ArrayList<>();
		if (lstEmployee.isEmpty()) {
			val employeeIds = objectShare == null
					? lstEmployee.stream().map(x -> x.getId()).collect(Collectors.toList())
					: objectShare.getLstEmployeeShare();
			changeEmployeeIds = changeListEmployeeId(employeeIds, screenDto.getDateRange(), mode);
		} else {
			changeEmployeeIds = lstEmployee.stream().map(x -> x.getId()).collect(Collectors.toList());
		}
		//TODO アルゴリズム「通常モードで起動する」を実行する
		/**
		 * アルゴリズム「表示形式に従って情報を取得する」を実行する | Execute "Get information according to
		 * display format"
		 */
		// アルゴリズム「対象者を抽出する」を実行する | Execute "Extract subject"
		//List<EmployeeInfoFunAdapterDto> employeeInfoAdapter = changeEmployeeIds.isEmpty() ? Collections.emptyList() :  employeeInfoFunAdapter.getListPersonInfor(changeEmployeeIds);
		//screenDto.setLstEmployee(converEmployeeList(employeeInfoAdapter));
		screenDto.setLstEmployee(repo.getListEmployee(changeEmployeeIds));
//		if(displayFormat == 0 && !changeEmployeeIds.isEmpty()){
//			changeEmployeeIds = changeEmployeeIds.stream().filter(x -> x.equals((objectShare== null && initScreen == 0)  ? sId : objectShare.getIndividualTarget())).collect(Collectors.toList());
//		}
		System.out.println("time get data employee" + (System.currentTimeMillis() - timeStart));
		List<WorkPlaceHistImport> wPH = changeEmployeeIds.isEmpty() ? Collections.emptyList() : workplaceWorkRecordAdapter.getWplByListSidAndPeriod(changeEmployeeIds, new DatePeriod(GeneralDate.min(), GeneralDate.max()));
		System.out.println("time get data wplhis" + (System.currentTimeMillis() - timeStart));//slow
		List<DailyPerformanceEmployeeDto> lstEmployeeData = extractEmployeeData(initScreen, sId,
				screenDto.getLstEmployee(), objectShare);
		
		// 表示形式をチェックする | Check display format => UI
		// Create lstData: Get by listEmployee & listDate
		// 日付別の情報を取得する + 個人別の情報を取得する + エラーアラームの情報を取得する | Acquire information by
		// date + Acquire personalized information + Acquire error alarm
		// information
		screenDto.setLstData(getListData(lstEmployeeData, dateRange, displayFormat));
		//get employee 
		System.out.println("time before lay con ty hist:" + (System.currentTimeMillis() - timeStart));
		List<AffCompanyHistImport> affCompany = changeEmployeeIds.isEmpty() ? Collections.emptyList() : employeeHistWorkRecordAdapter.getWplByListSidAndPeriod(changeEmployeeIds, new DatePeriod(GeneralDate.min(), GeneralDate.max()));
		System.out.println("time before map data wplhis, date:" + (System.currentTimeMillis() - timeStart)); //slow
		screenDto.setLstData(setWorkPlace(wPH, affCompany, screenDto.getLstData()));
		/// 対応する「日別実績」をすべて取得する | Acquire all corresponding "daily performance"
		List<String> listEmployeeId = screenDto.getLstData().stream().map(e -> e.getEmployeeId()).collect(Collectors.toSet()).stream().collect(Collectors.toList());
		System.out.println("time map data wplhis, date:" + (System.currentTimeMillis() - timeStart));
		//パラメータ「表示形式」をチェックする - Đã thiết lập truyền từ UI nên không cần check lấy theo định dạng nào , nhân viên đã được truyền
		
		// Lấy thành tích nhân viên theo ngày 
		/// アルゴリズム「対象日に対応する社員の実績の編集状態を取得する」を実行する | Execute "Acquire edit status
		/// of employee's record corresponding to target date"| lay ve trang
		/// thai sua cua thanh tich nhan vien tuong ung
		List<DailyRecEditSetDto> dailyRecEditSets = repo.getDailyRecEditSet(listEmployeeId, dateRange);
		Map<String, Integer> dailyRecEditSetsMap = dailyRecEditSets.stream()
				.collect(Collectors.toMap(x -> mergeString(String.valueOf(x.getAttendanceItemId()), "|", x.getEmployeeId(), "|",
						converDateToString(x.getProcessingYmd())),
						x -> x.getEditState()));
		/// アルゴリズム「実績エラーをすべて取得する」を実行する | Execute "Acquire all actual errors"
		List<DPErrorDto> lstError = getErrorList(screenDto, listEmployeeId);
		screenDto.setDPErrorDto(lstError);
		Map<String, String> listEmployeeError = lstError.stream()
				.collect(Collectors.toMap(e -> e.getEmployeeId(), e -> "", (x, y) -> x));
		if (displayFormat == 2) {
			// only filter data error
			listEmployeeId = listEmployeeId.stream().filter(x -> listEmployeeError.containsKey(x))
					.collect(Collectors.toList());
			screenDto.setLstData(screenDto.getLstData().stream()
					.filter(x -> listEmployeeError.containsKey(x.getEmployeeId())).collect(Collectors.toList()));
		}

		// アルゴリズム「社員に対応する処理締めを取得する」を実行する | Execute "Acquire Process Tightening
		// Corresponding to Employees"--
		/// TODO : アルゴリズム「対象日に対応する承認者確認情報を取得する」を実行する | Execute "Acquire Approver
		/// Confirmation Information Corresponding to Target Date"
		// アルゴリズム「就業確定情報を取得する」を実行する
		/// アルゴリズム「日別実績のロックを取得する」を実行する (Tiến hành xử lý "Lấy về lock của thành
		// tích theo ngày")

		// check show column 本人
		// check show column 承認
		DailyRecOpeFuncDto dailyRecOpeFun = findDailyRecOpeFun(screenDto, companyId, mode).orElse(null);

		Map<String, DatePeriod> employeeAndDateRange = extractEmpAndRange(dateRange, companyId, listEmployeeId);
		System.out.println("time before get item" + (System.currentTimeMillis() - timeStart));
		long start = System.currentTimeMillis();
		// No 19, 20 show/hide button
		boolean showButton = true;
		if (displayFormat == 0) {
			if (!listEmployeeId.isEmpty() && !sId.equals(listEmployeeId.get(0))) {
				showButton = false;
			}
			// フレックス情報を表示する
			if(!listEmployeeId.isEmpty())
			screenDto.setFlexShortage(flexInfoDisplay.flexInfo(listEmployeeId.get(0), dateRange.getEndDate(),  AppContexts.user().roles().forAttendance()));
		}
		DisplayItem disItem = getDisplayItems(correct, formatCodes, companyId, screenDto, listEmployeeId, showButton);

		List<DailyModifyResult> results = new ArrayList<>();
		results = new GetDataDaily(listEmployeeId, dateRange, disItem.getLstAtdItemUnique(), dailyModifyQueryProcessor).call();
		DPControlDisplayItem dPControlDisplayItem = this.getItemIdNames(disItem, showButton);
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
		Map<Integer, DPAttendanceItem> mapDP = dPControlDisplayItem.getLstAttendanceItem() != null
				? dPControlDisplayItem.getLstAttendanceItem().stream()
						.collect(Collectors.toMap(DPAttendanceItem::getId, x -> x))
				: new HashMap<>();

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
		CodeNameType codeNameReason = dataDialogWithTypeProcessor.getReason(companyId);
		Map<String, CodeName> codeNameReasonMap = codeNameReason != null
				? codeNameReason.getCodeNames().stream()
						.collect(Collectors.toMap(x -> mergeString(x.getCode(), "|", x.getId()), x -> x))
				: Collections.emptyMap();
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
		Map<String, ApproveRootStatusForEmpDto> approvalDayMap =  getCheckApproval(listEmployeeId, dateRange, sId, mode);
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
		long start2 = System.currentTimeMillis();
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
			//get status check box 
			ApproveRootStatusForEmpDto approveRootStatus =  approvalDayMap.get(data.getEmployeeId() + "|" + data.getDate());
			if(mode == ScreenMode.APPROVAL.value){
				data.setApproval(approveRootStatus == null ? false : approveRootStatus.isCheckApproval());
			}
			DailyModifyResult resultOfOneRow = getRow(resultDailyMap, data.getEmployeeId(), data.getDate());
			if (resultOfOneRow != null) {
				lockDataCheckbox(sId, screenDto, dailyRecOpeFun, data, identityProcessDtoOpt, approvalUseSettingDtoOpt, approveRootStatus);

				boolean lock = checkLockAndSetState(employeeAndDateRange, data);

				if (lock || data.isApproval()) {
					lockCell(screenDto, data);
					val typeLock = data.getState();
					if(typeLock.equals("")) data.setState("lock|"+ LOCK_EDIT_APPROVAL);
					else data.setState(typeLock+"|"+LOCK_EDIT_APPROVAL);
					lock = true;
				}
				if (resultOfOneRow != null) {
					// List<ItemValue> attendanceTimes =
					// resultOfOneRow.getItems().get("AttendanceTimeOfDailyPerformance");
					itemValueMap = resultOfOneRow.getItems().stream()
							.collect(Collectors.toMap(x -> mergeString(String.valueOf(x.getItemId()), "|",
									data.getEmployeeId(), "|", data.getDate().toString()), x -> x));
				}
				processCellData(NAME_EMPTY, NAME_NOT_FOUND, screenDto, dPControlDisplayItem, mapDP, mapGetName, codeNameReasonMap,
						itemValueMap,  data, lock, dailyRecEditSetsMap, objectShare);
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
		System.out.println("time get data into cell : " + (System.currentTimeMillis() - start2));
		screenDto.setLstData(lstData);
		System.out.println("time add  return : " + (System.currentTimeMillis() - start));
		System.out.println("All time :" + (System.currentTimeMillis() - timeStart));
		return screenDto;
	}

	private void processCellData(String NAME_EMPTY, String NAME_NOT_FOUND, DailyPerformanceCorrectionDto screenDto,
			DPControlDisplayItem dPControlDisplayItem, Map<Integer, DPAttendanceItem> mapDP,
			Map<Integer, Map<String, String>> mapGetName,  Map<String, CodeName> mapReasonName, Map<String, ItemValue> itemValueMap, DPDataDto data,
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
								}else if(groupType == TypeLink.REASON.value){
									int group = DEVIATION_REASON_MAP.get(item.getId());
									value = mapReasonName.containsKey(value+"|"+group) ? mapReasonName.get(value+"|"+"group").getName() : NAME_NOT_FOUND;
								}
								else {
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
							boolean att = false;
							boolean leav = false;
							if(item.getId() == 31 && data.getEmployeeId().equals(share.getInitClock().getEmployeeId()) && data.getDate().equals(share.getInitClock().getDateSpr())){
								value = share.getInitClock().getGoOut() != null ?  share.getInitClock().getGoOut() : "";
								att = true;
							}else if(item.getId() == 34 && data.getEmployeeId().equals(share.getInitClock().getEmployeeId()) && data.getDate().equals(share.getInitClock().getDateSpr())){
								value = share.getInitClock().getLiveTime() != null ?  share.getInitClock().getLiveTime() : "";
								leav = true;
							}
							insertStampSourceInfo(data.getEmployeeId(), data.getDate(), att, leav);
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

	private DailyModifyResult getRow(Map<String, DailyModifyResult> resultDailyMap, String sId, GeneralDate date) {
		return resultDailyMap.isEmpty() ? null : resultDailyMap.get(mergeString(sId, "|", date.toString()));
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

	private boolean inRange(DPDataDto data, DatePeriod dateM) {
		return data.getDate().afterOrEquals(dateM.start()) && data.getDate().beforeOrEquals(dateM.end());
	}

	private void lockDataCheckbox(String sId, DailyPerformanceCorrectionDto screenDto, DailyRecOpeFuncDto dailyRecOpeFun,
			DPDataDto data, Optional<IdentityProcessUseSetDto> identityProcessUseSetDto, Optional<ApprovalUseSettingDto> approvalUseSettingDto, ApproveRootStatusForEmpDto approveRootStatus) {
		// disable, enable check sign no 10
		if (dailyRecOpeFun != null) {
			if (!sId.equals(data.getEmployeeId())) {
				screenDto.setLock(data.getId(), LOCK_SIGN, STATE_DISABLE);
				//screenDto.setLock(data.getId(), LOCK_APPROVAL, STATE_DISABLE);
			} else {
				//if (identityProcessUseSetDto.isPresent()) {
				if (dailyRecOpeFun != null) {
					//int selfConfirmError = identityProcessUseSetDto.get().getYourSelfConfirmError();
					int selfConfirmError = dailyRecOpeFun.getYourselfConfirmError();
					// lock sign
					if (selfConfirmError == ConfirmOfManagerOrYouself.CANNOT_CHECKED_WHEN_ERROR.value) {
						if (data.getError().contains("ER") && data.isSign()) {
							screenDto.setLock(data.getId(), LOCK_SIGN, STATE_ERROR);
						} else if(data.getError().contains("ER") && !data.isSign()){
							screenDto.setLock(data.getId(), LOCK_SIGN, STATE_DISABLE);
						}
						// thieu check khi co data
					} else if (selfConfirmError == ConfirmOfManagerOrYouself.CANNOT_REGISTER_WHEN_ERROR.value) {
						// co the dang ky data nhưng ko đăng ký được check box
					}
				}

			}
			
			//if (approvalUseSettingDto.isPresent()) {
			if (dailyRecOpeFun != null) {
				// lock approval
				//int supervisorConfirmError = approvalUseSettingDto.get().getSupervisorConfirmErrorAtr();
				int supervisorConfirmError = dailyRecOpeFun.getSupervisorConfirmError();
				if (supervisorConfirmError == ConfirmOfManagerOrYouself.CANNOT_CHECKED_WHEN_ERROR.value) {
					if (data.getError().contains("ER")) {
						screenDto.setLock(data.getId(), LOCK_APPROVAL, STATE_ERROR);
					} else {
						screenDto.setLock(data.getId(), LOCK_APPROVAL, STATE_DISABLE);
					}
					// thieu check khi co data
				} else if (supervisorConfirmError == ConfirmOfManagerOrYouself.CANNOT_REGISTER_WHEN_ERROR.value) {
					// co the dang ky data nhưng ko đăng ký được check box
				}
				
				// disable, enable checkbox with approveRootStatus
				if (approveRootStatus == null)
					return;
				if (approveRootStatus.getApproverEmployeeState() != null
						&& approveRootStatus.getApproverEmployeeState() != ApproverEmployeeState.PHASE_DURING) {
					screenDto.setLock(data.getId(), LOCK_APPROVAL, STATE_DISABLE);
				}
			}
		}
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

	private String mergeString(String... x) {
		return StringUtils.join(x);
	}

	private Optional<DailyRecOpeFuncDto> findDailyRecOpeFun(DailyPerformanceCorrectionDto screenDto, String companyId, int mode) {
		Optional<DailyRecOpeFuncDto> findDailyRecOpeFun = repo.findDailyRecOpeFun(companyId);
		if (findDailyRecOpeFun.isPresent()) {
			screenDto.setShowPrincipal(findDailyRecOpeFun.get().getUseConfirmByYourself() == 0 ? false : true);
			screenDto.setShowSupervisor(findDailyRecOpeFun.get().getUseSupervisorConfirm() == 0 ? false : ScreenMode.APPROVAL.value == mode);
		} else {
			screenDto.setShowPrincipal(false);
			screenDto.setShowSupervisor(false);
		}
		
		return findDailyRecOpeFun;
	}

	private List<DPErrorDto> getErrorList(DailyPerformanceCorrectionDto screenDto, List<String> listEmployeeId) {
		List<DPErrorDto> lstError = new ArrayList<>();
		if (screenDto.getLstEmployee().size() > 0) {
			/// ドメインモデル「社員の日別実績エラー一覧」をすべて取得する +
			/// 対応するドメインモデル「勤務実績のエラーアラーム」をすべて取得する
			/// Acquire all domain model "employee's daily performance error
			/// list" + "work error error alarm" | lay loi thanh tich trong
			/// khoang thoi gian
			return listEmployeeId.isEmpty() ? new ArrayList<>()
					: repo.getListDPError(screenDto.getDateRange(), listEmployeeId);
		} else {
			return lstError;
		}
	}

	private List<DailyPerformanceEmployeeDto> extractEmployeeData(Integer initScreen, String sId,
			List<DailyPerformanceEmployeeDto> emps, ObjectShare share) {
		if (initScreen == 0 && share == null) {
			return emps.stream().filter(x -> x.getId().equals(sId)).collect(Collectors.toList());
		}else if (initScreen == 0 && share.getDisplayFormat() == 0){
			List<DailyPerformanceEmployeeDto> datas = new ArrayList<>();
			if(!emps.isEmpty()) return emps.stream().filter(x-> x.getId().equals(share.getIndividualTarget())).collect(Collectors.toList());
			return datas;
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
    
	private List<DailyPerformanceEmployeeDto> converEmployeeList(List<EmployeeInfoFunAdapterDto> employees) {
		if (employees.isEmpty()) {
			return Collections.emptyList();
		} else {
			return employees.stream().map(x -> new DailyPerformanceEmployeeDto(x.getEmployeeId(), x.getEmployeeCode(),
					x.getBusinessName(), "", "", "", false)).collect(Collectors.toList());
		}
	}
	private List<DPDataDto> setWorkPlace(List<WorkPlaceHistImport> wPH, List<AffCompanyHistImport> affCompany, List<DPDataDto> employees){
		Map<String, List<WorkPlaceIdAndPeriodImport>> wPHMap = wPH.stream().collect(Collectors.toMap(x -> x.getEmployeeId(), x -> x.getLstWkpIdAndPeriod(), (x, y) -> x));
		Map<String, List<AffComHistItemImport>> affCompanyMap = affCompany.stream().collect(Collectors.toMap(x -> x.getEmployeeId(), x -> x.getLstAffComHistItem(), (x, y) -> x));
		return employees.stream().map(x -> {
			x.setWorkplaceId(wPHMap.containsKey(x.getEmployeeId()) ?  getWorkPlaceIdDate(wPHMap.get(x.getEmployeeId()), x.getDate()): "");
			//x.setDatePriod(affCompanyMap.containsKey(x.getId()) ?  getDateRetire(affCompanyMap.get(x.getId()), x.getDate()): new DatePeriod(GeneralDate.today(), GeneralDate.today()));
			return x;
		}).filter(x -> affCompanyMap.containsKey(x.getEmployeeId()) &&  getDateRetire(affCompanyMap.get(x.getEmployeeId()), x.getDate())).collect(Collectors.toList());
		//.filter(x -> affCompanyMap.containsKey(x.getId()) &&  getDateRetire(affCompanyMap.get(x.getId()), x.getDate()));
	}
	
	private String getWorkPlaceIdDate(List<WorkPlaceIdAndPeriodImport> dateWorkPlace, GeneralDate date){
		List<WorkPlaceIdAndPeriodImport> data = dateWorkPlace.stream().filter(x -> x.getDatePeriod().end().afterOrEquals(date) && x.getDatePeriod().start().beforeOrEquals(date)).collect(Collectors.toList());
		if(data.isEmpty()) return "";
		else return data.get(0).getWorkplaceId();
	}
	 
	private boolean getDateRetire(List<AffComHistItemImport> dateHist, GeneralDate date){
		List<AffComHistItemImport> data = dateHist.stream().filter(x -> x.getDatePeriod().end().afterOrEquals(date) && x.getDatePeriod().start().beforeOrEquals(date)).collect(Collectors.toList());
		if(data.isEmpty()) return false;
		else return true;
	}
	private String getEmploymentCode(DateRange dateRange, String sId) {
		AffEmploymentHistoryDto employment = repo.getAffEmploymentHistory(sId, dateRange);
		String employmentCode = employment == null ? "" : employment.getEmploymentCode();
		return employmentCode;
	}

	private List<DailyPerformanceAuthorityDto> getAuthority(DailyPerformanceCorrectionDto screenDto) {
		String roleId = AppContexts.user().roles().forAttendance();
		if (roleId != null) {
			List<DailyPerformanceAuthorityDto> dailyPerformans = repo.findDailyAuthority(roleId);
			if (!dailyPerformans.isEmpty()) {
				return dailyPerformans;
			}
		}
		throw new BusinessException("Msg_671");
	}

	// アルゴリズム「乖離理由を取得する」
	private Map<Integer, Boolean> getReasonDiscrepancy(String companyId, List<Integer> lstAtdItemUnique) {
		List<Integer> possiton = Arrays.asList(DEVIATION_REASON);
		List<Integer> divergenceNo = lstAtdItemUnique.stream().filter(x -> DEVIATION_REASON_MAP.containsKey(x))
				.map(x -> DEVIATION_REASON_MAP.get(x)).collect(Collectors.toSet()).stream().collect(Collectors.toList());
		// 日次勤怠項目一覧の勤怠項目に対応する乖離理由を取得する
		Map<Integer, Boolean> shows = new HashMap<>();
		if(divergenceNo.isEmpty()) return Collections.emptyMap();; 
		List<DivergenceTimeDto> divergenceTimeDtos = repo.findDivergenceTime(companyId, divergenceNo);
		if (divergenceTimeDtos.isEmpty())
			return Collections.emptyMap();
		Map<Integer, List<Integer>> groupMap = new HashMap<>();
		for (int i = 0; i < DEVIATION_REASON.length; i++) {
			if (groupMap.containsKey(i/3 + 1)) {
				val group = groupMap.get(i/3 + 1);
				group.add(DEVIATION_REASON[i]);
				groupMap.put(i/3 + 1, group);
			} else {
				val group = new ArrayList<Integer>();
				group.add(DEVIATION_REASON[i]);
				groupMap.put(i/3 + 1, group);
			}
		}
		divergenceTimeDtos.forEach(x -> {
			List<Integer> groupItem = groupMap.get(x.getDivergenceTimeNo());
			if (x.getDivTimeUseSet() == DivergenceTimeUseSet.NOT_USE.value)
				groupItem.forEach(y ->{
					shows.put(y, false);
				});
			else {
				groupItem.forEach(y -> {
					shows.put(y, false);
					if (possiton.indexOf(y) % 3 == 0) {
						shows.put(y, true);
					}
					if (x.getDvgcReasonInputed() && possiton.indexOf(y) % 3 == 2) {
						shows.put(y, true);
					}
					if (x.getDvgcReasonSelected() && possiton.indexOf(y) % 3 == 1) {
						shows.put(y, true);
					}
				});
			}
		});
		return shows;
	}

	public List<ErrorReferenceDto> getListErrorRefer(DateRange dateRange,
			List<DailyPerformanceEmployeeDto> lstEmployee) {
		List<ErrorReferenceDto> lstErrorRefer = new ArrayList<>();
		List<DPErrorDto> lstError = this.repo.getListDPError(dateRange,
				lstEmployee.stream().map(e -> e.getId()).collect(Collectors.toList()));
		if (lstError.size() > 0) {
			// 対応するドメインモデル「勤務実績のエラーアラーム」をすべて取得する
			// Get list error setting
			List<DPErrorSettingDto> lstErrorSetting = this.repo
					.getErrorSetting(lstError.stream().map(e -> e.getErrorCode()).collect(Collectors.toList()));
			// convert to list error reference
			for (int id = 0; id < lstError.size(); id++) {
				for (DPErrorSettingDto errorSetting : lstErrorSetting) {
					if (lstError.get(id).getErrorCode().equals(errorSetting.getErrorAlarmCode())) {
						lstErrorRefer.add(new ErrorReferenceDto(String.valueOf(id), lstError.get(id).getEmployeeId(),
								"", "", lstError.get(id).getProcessingDate(), lstError.get(id).getErrorCode(),
								errorSetting.getMessageDisplay(), lstError.get(id).getAttendanceItemId(), "",
								errorSetting.isBoldAtr(), errorSetting.getMessageColor()));
					}
				}
			}
			// get list item to add item name
			List<DPAttendanceItem> lstAttendanceItem = this.repo.getListAttendanceItem(
					lstError.stream().map(f -> f.getAttendanceItemId()).collect(Collectors.toList()));
			for (ErrorReferenceDto errorRefer : lstErrorRefer) {
				for (DPAttendanceItem atdItem : lstAttendanceItem) {
					if (errorRefer.getItemId().equals(atdItem.getId())) {
						errorRefer.setItemName(atdItem.getName());
					}
				}
			}
			// add employee code & name
			for (ErrorReferenceDto errorRefer : lstErrorRefer) {
				for (DailyPerformanceEmployeeDto employee : lstEmployee) {
					if (errorRefer.getEmployeeId().equals(employee.getId())) {
						errorRefer.setEmployeeCode(employee.getCode());
						errorRefer.setEmployeeName(employee.getBusinessName());
					}
				}
			}
		}
		return lstErrorRefer;
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
				List<DPBusinessTypeControl> lstDPBusinessTypeControl = new ArrayList<>();
				// Create header & sheet
				if (lstBusinessTypeCode.size() > 0) {
                    // List item hide 
					lstFormat = this.repo.getListFormatDPCorrection(lstBusinessTypeCode).stream().collect(Collectors.toList()); // 10s
					lstAtdItem = lstFormat.stream().map(f -> f.getAttendanceItemId()).collect(Collectors.toList());
					lstAtdItemUnique = new HashSet<Integer>(lstAtdItem).stream().collect(Collectors.toList());//.filter(x -> !itemHide.containsKey(x))
					if (lstFormat.size() > 0) {
						lstDPBusinessTypeControl = this.repo.getListBusinessTypeControl(lstBusinessTypeCode,
								lstAtdItemUnique);
					}
					Map<Integer, String> itemHide = lstDPBusinessTypeControl.stream().filter(x -> x.isUseAtr()).collect(Collectors.toMap(DPBusinessTypeControl :: getAttendanceItemId, x -> "", (x, y) -> x));
					lstSheet = this.repo.getFormatSheets(lstBusinessTypeCode);
					Set<String> lstSheetNo = lstSheet.stream().map(DPSheetDto::getName).collect(Collectors.toSet());
					if (lstSheetNo.size() != lstSheet.size()) {
						lstSheet = lstSheet.stream().map(x -> new DPSheetDto(x.getName(), x.getName()))
								.collect(Collectors.toList());
					}
					/// 対応するドメインモデル「勤務種別日別実績の修正のフォーマット」を取得する
					Map<Integer, Boolean> itemHideReason = getReasonDiscrepancy(companyId, lstAtdItemUnique);
					lstFormat = lstFormat.stream().filter(x -> itemHide.containsKey(x.getAttendanceItemId()) 
							                              && ((itemHideReason.containsKey(x.getAttendanceItemId()) 
							                              && itemHideReason.get(x.getAttendanceItemId())) 
							                              || !itemHideReason.containsKey(x.getAttendanceItemId()))
							                              )
							                      .collect(Collectors.toList()); 
				}
				result.setLstBusinessTypeCode(lstDPBusinessTypeControl);
			}
			result.setLstFormat(lstFormat);
			result.setLstSheet(lstSheet);
			result.setLstAtdItemUnique(lstAtdItemUnique);
			result.setBussiness(dailyPerformanceDto.getSettingUnit().value);
		}
		return result;
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
	
	private List<String> changeListEmployeeId(List<String> employeeIds, DateRange range, int mode) {
		// 社員一覧を変更する
		String companyId = AppContexts.user().companyId();
		String employeeIdLogin = AppContexts.user().employeeId();
		if (mode == ScreenMode.NORMAL.value) {
			if(employeeIds.isEmpty()){
				 List<RegulationInfoEmployeeQueryR> regulationRs= regulationInfoEmployeePub.search(createQueryEmployee(new ArrayList<>(), range.getStartDate(), range.getEndDate()));
				 return regulationRs.stream().map(x -> x.getEmployeeId()).collect(Collectors.toList());
			}else{
				// No 338
				return employeeIds;
			}
		} else if (mode == ScreenMode.APPROVAL.value) {
			ApprovalRootOfEmployeeImport approvalRoot = approvalStatusAdapter.getApprovalRootOfEmloyee(range.getStartDate(), range.getEndDate(), employeeIdLogin, companyId, 1);
			List<String> emloyeeIdApp = approvalRoot == null ? Collections.emptyList() : approvalRoot.getApprovalRootSituations().stream().map(x -> x.getTargetID()).collect(Collectors.toSet()).stream().collect(Collectors.toList());
			if(employeeIds.isEmpty()){
			   return emloyeeIdApp;
			}else{
				Map<String, String> emloyeeIdAppMap = emloyeeIdApp.stream().collect(Collectors.toMap(x -> x, x -> ""));
				return employeeIds.stream().filter(x -> emloyeeIdAppMap.containsKey(x)).collect(Collectors.toList());
			}
		}
		return Collections.emptyList();
	}
	
	private RegulationInfoEmployeeQuery createQueryEmployee(List<String> employeeCodes, GeneralDate startDate,
			GeneralDate endDate) {
		RegulationInfoEmployeeQuery query = new RegulationInfoEmployeeQuery();
		query.setBaseDate(GeneralDate.today());
		query.setReferenceRange(EmployeeReferenceRange.DEPARTMENT_AND_CHILD.value);
		query.setFilterByEmployment(false);
		query.setEmploymentCodes(Collections.emptyList());
//		query.setFilterByDepartment(false);
//		query.setDepartmentCodes(Collections.emptyList());
		query.setFilterByWorkplace(false);
		query.setWorkplaceCodes(Collections.emptyList());
		query.setFilterByClassification(false);
		query.setClassificationCodes(Collections.emptyList());
		query.setFilterByJobTitle(false);
		query.setJobTitleCodes(Collections.emptyList());
		query.setFilterByWorktype(false);
		query.setWorktypeCodes(Collections.emptyList());
		query.setPeriodStart(startDate.toString());
		query.setPeriodEnd(endDate.toString());
		query.setIncludeIncumbents(true);
		query.setIncludeWorkersOnLeave(true);
		query.setIncludeOccupancy(true);
//		query.setIncludeAreOnLoan(true);
//		query.setIncludeGoingOnLoan(false);
		query.setIncludeRetirees(false);
		return query;
	}
	
	private void insertStampSourceInfo(String employeeId, GeneralDate date, Boolean stampSourceAt,
			Boolean stampSourceLeav) {
		Optional<TimeLeavingOfDailyPerformance> timeLeavingOpt = timeLeavingOfDailyPerformanceRepository
				.findByKey(employeeId, date);
		if (timeLeavingOpt.isPresent()) {
			TimeLeavingOfDailyPerformance timeLeaving = timeLeavingOpt.get();
			if (!timeLeaving.getTimeLeavingWorks().isEmpty()) {
				timeLeaving.getTimeLeavingWorks().stream().forEach(x -> {
					Optional<TimeActualStamp> attOpt = x.getAttendanceStamp();
					if (attOpt.isPresent()) {
						Optional<WorkStamp> workStampOpt = attOpt.get().getStamp();
						if (workStampOpt.isPresent() && stampSourceAt) {
							workStampOpt.get().setPropertyWorkStamp(workStampOpt.get().getAfterRoundingTime(),
									workStampOpt.get().getTimeWithDay(), workStampOpt.get().getLocationCode().isPresent() ?  workStampOpt.get().getLocationCode().get() : null,
									StampSourceInfo.SPR);
						}
					}

					Optional<TimeActualStamp> leavOpt = x.getLeaveStamp();
					if (leavOpt.isPresent() && stampSourceLeav) {
						Optional<WorkStamp> workStampOpt = leavOpt.get().getStamp();
						workStampOpt.get().setPropertyWorkStamp(workStampOpt.get().getAfterRoundingTime(),
								workStampOpt.get().getTimeWithDay(), workStampOpt.get().getLocationCode().isPresent() ?  workStampOpt.get().getLocationCode().get() : null,
								StampSourceInfo.SPR);
					}
					timeLeavingOfDailyPerformanceRepository.update(timeLeaving);
				});
			}
		}
	}
	
	private Map<String, ApproveRootStatusForEmpDto> getCheckApproval(List<String> employeeIds, DateRange dateRange, String employeeIdApproval, int mode){
		// get check
		if (employeeIds.isEmpty())
			return Collections.emptyMap();

		// get disable
		if (mode == ScreenMode.APPROVAL.value) {
			ApprovalRootOfEmployeeImport approvalRoot = approvalStatusAdapter.getApprovalRootOfEmloyee(
					dateRange.getStartDate(), dateRange.getEndDate(), employeeIdApproval,
					AppContexts.user().companyId(), 1);
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
								return dto;
							}));
			return approvalRootMap;
		} else {
			List<ApproveRootStatusForEmpImport> approvals = approvalStatusAdapter.getApprovalByListEmplAndListApprovalRecordDate(dateRange.toListDate(), employeeIds, 1);
			Map<String, ApproveRootStatusForEmpDto> approvalRootMap = approvals.stream().collect(Collectors.toMap(x -> mergeString(x.getEmployeeID(), "|", x.getAppDate().toString()), x -> {
				return new ApproveRootStatusForEmpDto(null, x.getApprovalStatus() == ApprovalStatusForEmployee.APPROVED);
			}));
			return approvalRootMap;
		}
	}
}
