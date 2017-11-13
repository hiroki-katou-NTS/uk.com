/**
 * 1:59:07 PM Aug 21, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction.dto;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.Getter;
import lombok.Setter;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author hungnm
 *
 */
@Getter
@Setter
public class DailyPerformanceCorrectionDto {

	private YearHolidaySettingDto yearHolidaySettingDto;

	private SubstVacationDto substVacationDto;

	private CompensLeaveComDto compensLeaveComDto;

	private Com60HVacationDto com60HVacationDto;

	private DateRange dateRange;

	private List<DailyPerformanceEmployeeDto> lstEmployee;

	private List<DPHeaderDto> lstFixedHeader;

	private DPControlDisplayItem lstControlDisplayItem;

	private List<DPCellStateDto> lstCellState;

	private List<DPDataDto> lstData;

	public DailyPerformanceCorrectionDto() {
		super();
		this.lstFixedHeader = DPHeaderDto.GenerateFixedHeader();
		this.lstCellState = new ArrayList<>();
		this.lstControlDisplayItem = new DPControlDisplayItem();
	}

	/** Check if employeeId is login user */
	private boolean isLoginUser(String employeeId) {
		for (DailyPerformanceEmployeeDto employee : this.lstEmployee) {
			if (employee.getId().equals(employeeId) && employee.isLoginUser()) {
				return true;
			}
		}
		return false;
	}

	/** Find cell by dataID and columnKey */
	private Optional<DPCellStateDto> findExistCellState(int dataId, String columnKey) {
		for (int i = 0; i < this.lstCellState.size(); i++) {
			if (this.lstCellState.get(i).getRowId() == dataId
					&& this.lstCellState.get(i).getColumnKey().equals(String.valueOf(columnKey))) {
				return Optional.of(this.lstCellState.get(i));
			}
		}
		return Optional.empty();
	}

	/** Set disable cell & Create not existed cell */
	private void setDisableCell(DPHeaderDto header, DPDataDto data) {
		Optional<DPCellStateDto> existedCellState = findExistCellState(data.getId(), header.getKey());

		if (existedCellState.isPresent()) {
			existedCellState.get().addState("ntsgrid-disable");
		} else {
			List<String> state = new ArrayList<>();
			state.add("ntsgrid-disable");
			this.lstCellState.add(new DPCellStateDto(data.getId(), header.getKey(), state));
		}
	}

	/** Mark current login employee */
	public void markLoginUser() {
		for (DailyPerformanceEmployeeDto employee : lstEmployee) {
			if (employee.getId().equals(AppContexts.user().employeeId())) {
				employee.setLoginUser(true);
			}
		}
	}

	/** Create Access Modifier Cellstate */
	public void createAccessModifierCellState() {
		this.getLstData().forEach(data -> {
			boolean isLoginUser = isLoginUser(data.getEmployeeId());
			this.getLstControlDisplayItem().getLstHeader().forEach(header -> {
				if (header.getChangedByOther() && !header.getChangedByYou()) {
					boolean isDisableCell = isLoginUser;
					if (isDisableCell) {
						setDisableCell(header, data);
					}
				} else if (!header.getChangedByOther() && header.getChangedByYou()) {
					boolean isDisableCell = !isLoginUser;
					if (isDisableCell) {
						setDisableCell(header, data);
					}
				} else if (!header.getChangedByOther() && !header.getChangedByYou()) {
					setDisableCell(header, data);
				}
			});
		});
	}

	/** Set Error/Alarm text and state for cell */
	public void addErrorToResponseData(List<DPErrorDto> lstError, List<DPErrorSettingDto> lstErrorSetting) {
		lstError.forEach(error -> {
			this.lstData.forEach(data -> {
				if (data.getEmployeeId().equals(error.getEmployeeId())
						&& data.getDate().equals(error.getProcessingDate())) {
					String errorType = "";
					for (int i = 0; i < lstErrorSetting.size(); i++) {
						if (lstErrorSetting.get(i).getErrorAlarmCode().equals(error.getErrorCode())) {
							errorType = lstErrorSetting.get(i).getTypeAtr() == 0 ? "ER" : "AL";
						}
					}
					// add error alarm to response data
					if (!data.getError().equals("")) {
						if (!errorType.equals(data.getError())) {
							data.setError("ER/AL");
						}
					} else {
						data.setError(errorType);
					}
					// add error alarm cell state
					Optional<DPCellStateDto> existedCellState = findExistCellState(data.getId(),
							error.getAttendanceItemId().toString());
					if (existedCellState.isPresent()) {
						List<String> lstState = existedCellState.get().getState();
						lstState.add(errorType.equals("ER") ? "ntsgrid-error" : "ntsgrid-alarm");
						existedCellState.get().setState(lstState);
					} else {
						List<String> state = new ArrayList<>();
						state.add(errorType.equals("ER") ? "ntsgrid-error" : "ntsgrid-alarm");
						List<DPCellStateDto> lstCellState = this.getLstCellState();
						lstCellState.add(
								new DPCellStateDto(data.getId(), String.valueOf(error.getAttendanceItemId()), state));
						this.setLstCellState(lstCellState);
					}
				}
			});
		});
	}

	/** Set AlarmCell state for Fixed cell */
	public void setAlarmCellForFixedColumn(int dataId) {
		String[] listHighlight = { "date", "employeeCode", "employeeName" };
		for (String columnKey : listHighlight) {
			Optional<DPCellStateDto> existedCellState = findExistCellState(dataId, columnKey);
			if (existedCellState.isPresent()) {
				existedCellState.get().addState("ntsgrid-alarm");
			}
		}

	}

}
