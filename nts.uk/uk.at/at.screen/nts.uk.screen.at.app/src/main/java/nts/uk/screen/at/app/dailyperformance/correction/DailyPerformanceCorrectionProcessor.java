/**
 * 1:57:38 PM Aug 21, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.workinformation.enums.CalculationState;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPAttendanceItem;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPAttendanceItemControl;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPBusinessTypeControl;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPControlDisplayItem;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPDataDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPErrorDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPErrorSettingDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPHeaderDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DPSheetDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DailyPerformanceCorrectionDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DailyPerformanceEmployeeDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.DateRange;
import nts.uk.screen.at.app.dailyperformance.correction.dto.ErrorReferenceDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.FormatDPCorrectionDto;
import nts.uk.screen.at.app.dailyperformance.correction.dto.WorkInfoOfDailyPerformanceDto;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author hungnm
 *
 */
@Stateless
public class DailyPerformanceCorrectionProcessor {

	@Inject
	private DailyPerformanceScreenRepo repo;

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
					result.add(new DPDataDto(dataId, "", "", filterDate, false, employee.getId(), employee.getCode(), employee.getBusinessName()));
					dataId++;
				}
			}
		}
		return result;
	}

	/** アルゴリズム「表示項目を制御する」を実行する | Execute the algorithm "control display items" */
	private DPControlDisplayItem getControlDisplayItems(List<String> lstEmployeeId, DateRange dateRange) {
		DPControlDisplayItem result = new DPControlDisplayItem();
		if (lstEmployeeId.size() > 0) {
			// TODO : 対応するドメインモデル「日別実績の運用」を取得する | Acquire corresponding domain model "Operation of daily performance"
			
			// TODO : 取得したドメインモデル「日別実績の運用」をチェックする | Check the acquired domain model "Operation of daily performance"

			// アルゴリズム「社員の勤務種別に対応する表示項目を取得する」を実行する
			/// アルゴリズム「社員の勤務種別をすべて取得する」を実行する
			List<String> lstBusinessTypeCode = this.repo.getListBusinessType(lstEmployeeId, dateRange);
			
			List<FormatDPCorrectionDto> lstFormat = new ArrayList<FormatDPCorrectionDto>();
			List<DPSheetDto> lstSheet = new ArrayList<DPSheetDto>();
			// Create header & sheet
			if (lstBusinessTypeCode.size() > 0) {
				
				lstSheet = this.repo.getFormatSheets(lstBusinessTypeCode);
				/// 対応するドメインモデル「勤務種別日別実績の修正のフォーマット」を取得する
				lstFormat = this.repo.getListFormatDPCorrection(lstBusinessTypeCode);
				result.createSheets(lstSheet);
				result.addColumnsToSheet(lstFormat);
				result.setLstHeader(lstFormat.stream().map(f -> {
					return DPHeaderDto.createSimpleHeader(String.valueOf(f.getAttendanceItemId()),
							String.valueOf(f.getColumnWidth()) + "px");
				}).collect(Collectors.toList()));
			}
			
			List<DPBusinessTypeControl> lstDPBusinessTypeControl = new ArrayList<>();
			if (lstFormat.size() > 0) {
				List<Integer> lstAtdItem = lstFormat.stream().map(f -> f.getAttendanceItemId())
						.collect(Collectors.toList());
				List<Integer> lstAtdItemUnique = new HashSet<Integer>(lstAtdItem).stream().collect(Collectors.toList());
				lstDPBusinessTypeControl = this.repo.getListBusinessTypeControl(lstBusinessTypeCode, lstAtdItemUnique);
			}
			if (lstDPBusinessTypeControl.size() > 0) {
				List<Integer> lstAtdItem = lstFormat.stream().map(f -> f.getAttendanceItemId())
						.collect(Collectors.toList());
				List<Integer> lstAtdItemUnique = new HashSet<Integer>(lstAtdItem).stream().collect(Collectors.toList());
				// set text to header
				List<DPAttendanceItem> lstAttendanceItem = this.repo.getListAttendanceItem(lstAtdItemUnique);
				result.setHeaderText(lstAttendanceItem);
				// set color to header
				List<DPAttendanceItemControl> lstAttendanceItemControl = this.repo
						.getListAttendanceItemControl(lstAtdItemUnique);
				result.setHeaderColor(lstAttendanceItemControl);
				// set header access modifier
				// only user are login can edit or others can edit
				result.setColumnsAccessModifier(lstDPBusinessTypeControl);
			}
		}
		return result;
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

	public DailyPerformanceCorrectionDto generateData(DateRange dateRange, List<DailyPerformanceEmployeeDto> lstEmployee) {
		String sId = AppContexts.user().employeeId();
		DailyPerformanceCorrectionDto screenDto = new DailyPerformanceCorrectionDto();
		
		/** システム日付を基準に1ヵ月前の期間を設定する | Set date range one month before system date */
		screenDto.setDateRange(dateRange);
		
		/** 画面制御に関する情報を取得する | Acquire information on screen control */
		// TODO: アルゴリズム「社員の日別実績の権限をすべて取得する」を実行する | Execute "Acquire all permissions of employee's daily performance"
		
		
		// TODO: アルゴリズム「社員に対応する処理締めを取得する」を実行する | Execute "Acquire Process Tightening Corresponding to Employees"
		/// 対応するドメインモデル「所属雇用履歴」を取得する
		
		
		// アルゴリズム「休暇の管理状況をチェックする」を実行する | Get holiday setting data
		getHolidaySettingData(screenDto);
		
		
		/** アルゴリズム「表示形式に従って情報を取得する」を実行する | Execute "Get information according to display format" */
		// アルゴリズム「対象者を抽出する」を実行する  | Execute "Extract subject"
		if (lstEmployee.size() > 0) {
			screenDto.setLstEmployee(lstEmployee);
		} else {
			screenDto.setLstEmployee(getListEmployee(sId, screenDto.getDateRange()));
		}
		
		// 表示形式をチェックする | Check display format => UI
		// Create lstData: Get by listEmployee & listDate
		// 日付別の情報を取得する + 個人別の情報を取得する + エラーアラームの情報を取得する | Acquire information by date + Acquire personalized information + Acquire error alarm information
		screenDto.setLstData(getListData(screenDto.getLstEmployee(), dateRange));
		/// TODO : 対応する「日別実績」をすべて取得する | Acquire all corresponding "daily performance"
		//// 11. Excel: 未計算のアラームがある場合は日付又は名前に表示する
		List<String> listEmployeeId = screenDto.getLstEmployee().stream().map(e -> e.getId()).collect(Collectors.toList());
		List<WorkInfoOfDailyPerformanceDto> workInfoOfDaily = repo.getListWorkInfoOfDailyPerformance(listEmployeeId, dateRange);
		for (DPDataDto data : screenDto.getLstData()) {
			Optional<WorkInfoOfDailyPerformanceDto> optWorkInfoOfDailyPerformanceDto = workInfoOfDaily.stream()
					.filter(w -> w.getEmployeeId().equals(data.getEmployeeId()) && w.getYmd().equals(data.getDate())).findFirst();
			if (optWorkInfoOfDailyPerformanceDto.isPresent() && optWorkInfoOfDailyPerformanceDto.get().getState() == CalculationState.No_Calculated)
				screenDto.setAlarmCellForFixedColumn(data.getId());
		}
		
		/// TODO : アルゴリズム「対象日に対応する社員の実績の編集状態を取得する」を実行する | Execute "Acquire edit status of employee's record corresponding to target date"
		
		/// アルゴリズム「実績エラーをすべて取得する」を実行する | Execute "Acquire all actual errors"
		if (screenDto.getLstEmployee().size() > 0) {
			/// ドメインモデル「社員の日別実績エラー一覧」をすべて取得する  + 対応するドメインモデル「勤務実績のエラーアラーム」をすべて取得する
			/// Acquire all domain model "employee's daily performance error list" + "work error error alarm"
			List<DPErrorDto> lstError = this.repo.getListDPError(screenDto.getDateRange(), listEmployeeId);
			if (lstError.size() > 0) {
				// Get list error setting
				List<DPErrorSettingDto> lstErrorSetting = this.repo.getErrorSetting(lstError.stream().map(e -> e.getErrorCode()).collect(Collectors.toList()));
				// Seperate Error and Alarm
				screenDto.addErrorToResponseData(lstError, lstErrorSetting);
			}
		}
		
		/// TODO : アルゴリズム「対象日に対応する承認者確認情報を取得する」を実行する | Execute "Acquire Approver Confirmation Information Corresponding to Target Date"
		
		// アルゴリズム「表示項目を制御する」を実行する | Execute "control display items"
		screenDto.setLstControlDisplayItem(getControlDisplayItems(listEmployeeId, screenDto.getDateRange()));
		
		screenDto.markLoginUser();
		screenDto.createAccessModifierCellState();
		return screenDto;
	}

	public List<ErrorReferenceDto> getListErrorRefer(DateRange dateRange, List<DailyPerformanceEmployeeDto> lstEmployee) {
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
}
