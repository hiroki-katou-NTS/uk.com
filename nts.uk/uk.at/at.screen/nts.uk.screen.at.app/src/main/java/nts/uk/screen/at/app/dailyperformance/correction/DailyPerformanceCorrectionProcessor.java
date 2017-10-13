/**
 * 1:57:38 PM Aug 21, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author hungnm
 *
 */
@Stateless
public class DailyPerformanceCorrectionProcessor {

	@Inject
	private DailyPerformanceScreenRepo repo;

	public DailyPerformanceCorrectionDto generateData(DateRange dateRange, List<DailyPerformanceEmployeeDto> lstEmployee)
			throws InterruptedException {
		String sId = AppContexts.user().employeeId();
		DailyPerformanceCorrectionDto screenDto = new DailyPerformanceCorrectionDto();
		/*
		 * アルゴリズム「休暇の管理状況をチェックする」を実行する Get holiday setting data
		 */
		screenDto.setYearHolidaySettingDto(this.repo.getYearHolidaySetting());
		screenDto.setSubstVacationDto(this.repo.getSubstVacationDto());
		screenDto.setCompensLeaveComDto(this.repo.getCompensLeaveComDto());
		screenDto.setCom60HVacationDto(this.repo.getCom60HVacationDto());
		screenDto.setDateRange(dateRange);
		if (lstEmployee.size() > 0) {
			screenDto.setLstEmployee(lstEmployee);
		} else {
			screenDto.setLstEmployee(this.getListEmployee(sId, screenDto.getDateRange()));
		}
		// create lst data
		if (screenDto.getLstEmployee().size() > 0) {
			List<GeneralDate> lstDate = dateRange.toListDate();
			List<DPDataDto> lstData = new ArrayList<>();
			int id = 0;
			for (int j = 0; j < screenDto.getLstEmployee().size(); j++) {
				for (int i = 0; i < lstDate.size(); i++) {
					lstData.add(
							new DPDataDto(id, "", "", lstDate.get(i), false, screenDto.getLstEmployee().get(j).getId(),
									screenDto.getLstEmployee().get(j).getCode(), screenDto.getLstEmployee().get(j).getBusinessName()));
					id += 1;
				}
			}
			screenDto.setLstData(lstData);
		}
		// アルゴリズム「表示項目を制御する」を実行する
		// Get display item control
		if (screenDto.getLstEmployee().size() > 0) {
			screenDto.setLstControlDisplayItem(this.getControlDisplayItems(
					screenDto.getLstEmployee().stream().map(e -> e.getId()).collect(Collectors.toList()),
					screenDto.getDateRange()));
		}
		/*
		 * アルゴリズム「期間に対応する実績エラーを取得する」を実行する Get list daily performance error
		 */
		if (screenDto.getLstEmployee().size() > 0) {
			List<DPErrorDto> lstError = this.getDPErrorList(
					screenDto.getLstEmployee().stream().map(e -> e.getId()).collect(Collectors.toList()),
					screenDto.getDateRange());
			if (lstError.size() > 0) {
				// 対応するドメインモデル「勤務実績のエラーアラーム」をすべて取得する
				// Get list error setting
				List<DPErrorSettingDto> lstErrorSetting = this.repo
						.getErrorSetting(lstError.stream().map(e -> e.getErrorCode()).collect(Collectors.toList()));
				screenDto.addErrorToResponseData(lstError, lstErrorSetting);
			}
		}
		screenDto.markLoginUser();
		screenDto.createAccessModifierCellState();
		return screenDto;
	}

	private List<DailyPerformanceEmployeeDto> getListEmployee(String sId, DateRange dateRange) {
		// List<String> lstJobTitle = this.repo.getListJobTitle(dateRange);
		// List<String> lstEmployment = this.repo.getListEmployment();
		Map<String, String> lstWorkplace = this.repo.getListWorkplace(sId, dateRange);
		// List<String> lstClassification = this.repo.getListClassification();
		return this.repo.getListEmployee(null, null, lstWorkplace, null);
	}

	private DPControlDisplayItem getControlDisplayItems(List<String> lstEmployee, DateRange dateRange) {
		DPControlDisplayItem result = new DPControlDisplayItem();
		List<String> lstBusinessTypeCode = this.repo.getListBusinessType(lstEmployee, dateRange);
		List<FormatDPCorrectionDto> lstFormat = new ArrayList<FormatDPCorrectionDto>();
		List<DPSheetDto> lstSheet = new ArrayList<DPSheetDto>();
		// create header & sheet
		if (lstBusinessTypeCode.size() > 0) {
			lstSheet = this.repo.getFormatSheets(lstBusinessTypeCode);
			lstFormat = this.repo.getListFormatDPCorrection(lstBusinessTypeCode);
			result.createSheets(lstSheet);
			result.addColumnsToSheet(lstFormat);
			result.setLstHeader(lstFormat.stream().map(f -> {
				return new DPHeaderDto(String.valueOf(f.getAttendanceItemId()),
						String.valueOf(f.getColumnWidth()) + "px");
			}).collect(Collectors.toList()));
		}
		List<DPBusinessTypeControl> lstDPBusinessTypeControl = new ArrayList<>();
		if (lstFormat.size() > 0) {
			lstDPBusinessTypeControl = this.repo.getListBusinessTypeControl(lstBusinessTypeCode,
					lstFormat.stream().map(f -> f.getAttendanceItemId()).collect(Collectors.toList()));
		}
		if (lstDPBusinessTypeControl.size() > 0) {
			// set text to header
			List<DPAttendanceItem> lstAttendanceItem = this.repo.getListAttendanceItem(
					lstFormat.stream().map(f -> f.getAttendanceItemId()).collect(Collectors.toList()));
			result.setHeaderText(lstAttendanceItem);
			// set color to header
			List<DPAttendanceItemControl> lstAttendanceItemControl = this.repo.getListAttendanceItemControl(
					lstFormat.stream().map(f -> f.getAttendanceItemId()).collect(Collectors.toList()));
			result.setHeaderColor(lstAttendanceItemControl);
			// set header access modifier
			// only user are login can edit or others can edit
			result.setColumnsAccessModifier(lstDPBusinessTypeControl);
		}
		return result;
	}

	private List<DPErrorDto> getDPErrorList(List<String> lstEmployee, DateRange dateRange) {
		return this.repo.getListDPError(dateRange, lstEmployee);
	}
}
