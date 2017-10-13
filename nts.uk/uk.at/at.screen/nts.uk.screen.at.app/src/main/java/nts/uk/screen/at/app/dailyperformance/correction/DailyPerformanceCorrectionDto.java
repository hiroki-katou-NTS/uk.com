/**
 * 1:59:07 PM Aug 21, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction;

import java.util.ArrayList;
import java.util.List;

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
	
	private DPControlDisplayItem lstControlDisplayItem;
	
	private List<DPCellStateDto> lstCellState;
	
	private List<DPDataDto> lstData;

	public DailyPerformanceCorrectionDto() {
		super();
		this.lstCellState = new ArrayList<>();
		this.lstControlDisplayItem = new DPControlDisplayItem();
	}
	
	private boolean isLoginUser(String employeeId) {
		for (int i = 0; i < this.getLstEmployee().size(); i++) {
			if (this.getLstEmployee().get(i).getId().equals(employeeId) && this.getLstEmployee().get(i).isLoginUser()) {
				return true;
			}
		}
		return false;
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
	
	private void setDisableCell(DPHeaderDto header, DPDataDto data){
		List<DPCellStateDto> lstCellState = this.getLstCellState();
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
			this.setLstCellState(lstCellState);
		}
	}

	public void markLoginUser(){
		for(DailyPerformanceEmployeeDto employee: lstEmployee){
			if(employee.getId().equals(AppContexts.user().employeeId())){
				employee.setLoginUser(true);
			}
		}
	}
	
	public void createAccessModifierCellState() {
		this.getLstData().forEach(data -> {
			boolean isLoginUser = isLoginUser(data.getEmployeeId());
			this.getLstControlDisplayItem().getLstHeader().forEach(header -> {
				if (header.isChangedByOther() && !header.isChangedByYou()) {
					boolean isDisableCell = isLoginUser;
					if (isDisableCell) {
						setDisableCell(header, data);
					}
				} else if (!header.isChangedByOther() && header.isChangedByYou()) {
					boolean isDisableCell = !isLoginUser;
					if (isDisableCell) {
						setDisableCell(header, data);
					}
				} else if (!header.isChangedByOther() && !header.isChangedByYou()) {
					setDisableCell(header, data);
				}
			});
		});
	}
	
	public void addErrorToResponseData(List<DPErrorDto> lstError, List<DPErrorSettingDto> lstErrorSetting){
		lstError.forEach(error -> {
			this.getLstData().forEach(data -> {
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
					DPCellStateDto existedCellState = findExistCellState(this.getLstCellState(),
							data.getId(), error.getAttendanceItemId());
					if (existedCellState != null) {
						List<String> lstState = existedCellState.getState();
						lstState.add(errorType.equals("ER") ? "ntsgrid-error" : "ntsgrid-alarm");
						existedCellState.setState(lstState);
					} else {
						List<String> state = new ArrayList<>();
						state.add(errorType.equals("ER") ? "ntsgrid-error" : "ntsgrid-alarm");
						List<DPCellStateDto> lstCellState = this.getLstCellState();
						lstCellState.add(new DPCellStateDto(data.getId(),
								String.valueOf(error.getAttendanceItemId()), state));
						this.setLstCellState(lstCellState);
					}
				}
			});
		});
	}
}
