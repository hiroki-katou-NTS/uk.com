/**
 * 1:57:38 PM Aug 21, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.task.AsyncTask;
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

	public DailyPerformanceCorrectionDto startScreen(DateRange dateRange, GeneralDate baseDate)
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
		// Get closure of login user
		// ClosureDto clo = this.repo.getClosure(sId, baseDate);
		ClosureDto clo = new ClosureDto("", 1, true, null);
		// システム日付を基準に1ヵ月前の期間を設定する
		screenDto.setDateRange(dateRange);
		// アルゴリズム「対象者を抽出する」を実行する
		// get list employee by closure
		if (clo != null) {
			screenDto.setLstEmployee(this.getListEmployee(sId, screenDto.getDateRange(), clo.getClosureId()));
		} else {
			screenDto.setLstEmployee(new ArrayList<>());
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
									screenDto.getLstEmployee().get(j).getCode(), "日通太郎"));
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
				lstError.forEach(error -> {
					screenDto.getLstData().forEach(data -> {
						if (data.getEmployeeId().equals(error.getEmployeeId())
								&& data.getDate().equals(error.getProcessingDate())) {
							String errorType = "";
							for (int i = 0; i < lstErrorSetting.size(); i++) {
								if (lstErrorSetting.get(i).getErrorAlarmCode().equals(error.getErrorCode())) {
									errorType = lstErrorSetting.get(i).getTypeAtr() == 0 ? "ER" : "AL";
								}
							}
							// add error alarm to response data
							if (data.getError().equals("ER")) {
								if (errorType.equals("AL")) {
									data.setError(data.getError() + "/" + errorType);
								}
							} else if (data.getError().equals("AL")) {
								if (errorType.equals("ER")) {
									data.setError("ER/AL");
								}
							} else if (data.getError().equals("")) {
								data.setError(errorType);
							}
							// add error alarm cell state
							DPCellStateDto existedCellState = findExistCellState(screenDto.getLstCellState(),
									data.getId(), error.getAttendanceItemId());
							if (existedCellState != null) {
								List<String> lstState = existedCellState.getState();
								lstState.add(errorType.equals("ER") ? "ntsgrid-error" : "ntsgrid-alarm");
								existedCellState.setState(lstState);
							} else {
								List<String> state = new ArrayList<>();
								state.add(errorType.equals("ER") ? "ntsgrid-error" : "ntsgrid-alarm");
								List<DPCellStateDto> lstCellState = screenDto.getLstCellState();
								lstCellState.add(new DPCellStateDto(data.getId(),
										String.valueOf(error.getAttendanceItemId()), state));
								screenDto.setLstCellState(lstCellState);
							}
						}
					});
				});
			}
		}
		createAccessModifierCellState(screenDto);
		return screenDto;
	}

	private List<DailyPerformanceEmployeeDto> getListEmployee(String sId, DateRange dateRange, Integer closureId) {
		List<String> lstJobTitle = this.repo.getListJobTitle(dateRange);
		List<String> lstEmployment = this.repo.getListEmployment(closureId);
		Map<String, String> lstWorkplace = this.repo.getListWorkplace(sId, dateRange);
		List<String> lstClassification = this.repo.getListClassification();
		return this.repo.getListEmployee(lstJobTitle, lstEmployment, lstWorkplace, lstClassification);
	}

	private DPControlDisplayItem getControlDisplayItems(List<String> lstEmployee, DateRange dateRange) {
		DPControlDisplayItem result = new DPControlDisplayItem();
		List<String> lstBusinessTypeCode = this.repo.getListBusinessType(lstEmployee, dateRange);
		List<FormatDPCorrectionDto> lstFormat = new ArrayList<FormatDPCorrectionDto>();
		List<DPSheetDto> lstSheet = new ArrayList<DPSheetDto>();
		// create header
		if (lstBusinessTypeCode.size() > 0) {
			lstSheet = this.repo.getFormatSheets(lstBusinessTypeCode);
			lstFormat = this.repo.getListFormatDPCorrection(lstBusinessTypeCode);
			result.setLstSheet(lstSheet);
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
			lstAttendanceItem.stream().forEach(i -> {
				Optional<DPHeaderDto> header = result.getLstHeader().stream()
						.filter(h -> h.getKey().equals(String.valueOf(i.getId()))).findFirst();
				if (header.isPresent()) {
					header.get().setHeaderText(i);
				}
			});
			// set color to header
			List<DPAttendanceItemControl> lstAttendanceItemControl = this.repo.getListAttendanceItemControl(
					lstFormat.stream().map(f -> f.getAttendanceItemId()).collect(Collectors.toList()));
			lstAttendanceItemControl.stream().forEach(i -> {
				Optional<DPHeaderDto> header = result.getLstHeader().stream()
						.filter(h -> h.getKey().equals(String.valueOf(i.getAttendanceItemId()))).findFirst();
				if (header.isPresent()) {
					header.get().setHeaderColor(i);
				}
			});
			// set header access modifier
			// only user are login can edit or others can edit
			lstDPBusinessTypeControl.stream().forEach(i -> {
				Optional<DPHeaderDto> header = result.getLstHeader().stream()
						.filter(h -> h.getKey().equals(String.valueOf(i.getAttendanceItemId()))).findFirst();
				if (header.isPresent()) {
					header.get().setChangedByOther(i.isChangedByOther());
					header.get().setChangedByYou(i.isChangedByYou());
				}
			});
		}
		return result;
	}

	private List<DPErrorDto> getDPErrorList(List<String> lstEmployee, DateRange dateRange) {
		return this.repo.getListDPError(dateRange, lstEmployee);
	}

	private DPCellStateDto findExistCellState(List<DPCellStateDto> lstCellState, int id, int attendanceId) {
		for (int i = 0; i < lstCellState.size(); i++) {
			if (lstCellState.get(i).getRowId() == id
					&& lstCellState.get(i).getColumnKey().equals(String.valueOf(attendanceId))) {
				return lstCellState.get(i);
			}
		}
		return null;
	}

	private boolean isLoginUser(List<DailyPerformanceEmployeeDto> lstEmployee, String employeeId) {
		for (int i = 0; i < lstEmployee.size(); i++) {
			if (lstEmployee.get(i).getId().equals(employeeId) && lstEmployee.get(i).isLoginUser()) {
				return true;
			}
		}
		return false;
	}
	
	private void setDisableCell(DailyPerformanceCorrectionDto screenDto, DPHeaderDto header, DPDataDto data){
		List<DPCellStateDto> lstCellState = screenDto.getLstCellState();
		DPCellStateDto existedCellState = findExistCellState(lstCellState, data.getId(),
				Integer.valueOf(header.getKey()));
		if (existedCellState != null) {
			List<String> lstState = existedCellState.getState();
			lstState.add("ntsgrid-disable");
			existedCellState.setState(lstState);
		} else {
			List<String> state = new ArrayList<>();
			state.add("ntsgrid-disable");
			lstCellState.add(new DPCellStateDto(data.getId(), header.getKey(), state));
			screenDto.setLstCellState(lstCellState);
		}
	}

	private void createAccessModifierCellState(DailyPerformanceCorrectionDto screenDto) {
		screenDto.getLstData().forEach(data -> {
			boolean isLoginUser = isLoginUser(screenDto.getLstEmployee(), data.getEmployeeId());
			screenDto.getLstControlDisplayItem().getLstHeader().forEach(header -> {
				if (header.isChangedByOther() && !header.isChangedByYou()) {
					boolean isDisableCell = isLoginUser;
					if (isDisableCell) {
						setDisableCell(screenDto, header, data);
					}
				} else if (!header.isChangedByOther() && header.isChangedByYou()) {
					boolean isDisableCell = !isLoginUser;
					if (isDisableCell) {
						setDisableCell(screenDto, header, data);
					}
				} else if (!header.isChangedByOther() && !header.isChangedByYou()) {
					setDisableCell(screenDto, header, data);
				}
			});
		});
	}

}
