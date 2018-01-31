/**
 * 1:59:07 PM Aug 21, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction.dto;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.enums.DailyAttendanceAtr;
import nts.uk.screen.at.app.dailyperformance.correction.dto.checkshowbutton.DailyPerformanceAuthorityDto;
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
	
	private List<DailyPerformanceAuthorityDto> authorityDto;
	
	private String employmentCode;
	
	//A13_1  コメント
	private String comment;
	
	private Set<ItemValue> itemValues;
	
	private Boolean showPrincipal;

	public DailyPerformanceCorrectionDto() {
		super();
		this.lstFixedHeader = DPHeaderDto.GenerateFixedHeader();
		this.lstCellState = new ArrayList<>();
		this.lstControlDisplayItem = new DPControlDisplayItem();
		this.itemValues = new HashSet<>();
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
			if (this.lstCellState.get(i).getRowId().equals("_"+String.valueOf(dataId))
					&& this.lstCellState.get(i).getColumnKey().equals(String.valueOf(columnKey))) {
				return Optional.of(this.lstCellState.get(i));
			}
		}
		return Optional.empty();
	}

	/** Set disable cell & Create not existed cell */
	private void setDisableCell(DPHeaderDto header, DPDataDto data, Map<Integer,DPAttendanceItem> mapDP ) {
		Optional<DPCellStateDto> existedCellState = findExistCellState(data.getId(), header.getKey());

		if (existedCellState.isPresent()) {
			existedCellState.get().addState("ntsgrid-disable");
		} else {
			List<String> state = new ArrayList<>();
			state.add("ntsgrid-disable");
				int attendanceAtr = mapDP.get(Integer.parseInt(header.getKey().trim().substring(1, header.getKey().trim().length()))).getAttendanceAtr() ;
				if(attendanceAtr == DailyAttendanceAtr.Code.value || attendanceAtr == DailyAttendanceAtr.Classification.value ){
					if(attendanceAtr == DailyAttendanceAtr.Classification.value){
						this.lstCellState.add(new DPCellStateDto("_"+data.getId(), "NO"+header.getKey().substring(1, header.getKey().length()), state));
					}else{
						this.lstCellState.add(new DPCellStateDto("_"+data.getId(), "Code"+header.getKey().substring(1, header.getKey().length()), state));
					}
				this.lstCellState.add(new DPCellStateDto("_"+data.getId(), "Name"+header.getKey().substring(1, header.getKey().length()), state));
			} else {
				this.lstCellState.add(new DPCellStateDto("_"+data.getId(), header.getKey(), state));
			}
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
	public void createAccessModifierCellState(Map<Integer,DPAttendanceItem> mapDP) {
		this.getLstData().forEach(data -> {
			boolean isLoginUser = isLoginUser(data.getEmployeeId());
			this.getLstControlDisplayItem().getLstHeader().forEach(header -> {
				if (header.getChangedByOther() && !header.getChangedByYou()) {
					boolean isDisableCell = isLoginUser;
					if (isDisableCell) {
						setDisableCell(header, data, mapDP);
					}
				} else if (!header.getChangedByOther() && header.getChangedByYou()) {
					boolean isDisableCell = !isLoginUser;
					if (isDisableCell) {
						setDisableCell(header, data, mapDP);
					}
				} else if (!header.getChangedByOther() && !header.getChangedByYou()) {
					setDisableCell(header, data, mapDP);
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
								new DPCellStateDto("_"+data.getId(), String.valueOf(error.getAttendanceItemId()), state));
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
			}else{
				List<String> state = new ArrayList<>();
				state.add("ntsgrid-alarm");
				DPCellStateDto dto = new DPCellStateDto("_"+dataId, columnKey, state);
				this.lstCellState.add(dto);
			}
		}

	}
	
	/** Set AlarmCell state for Fixed cell */
	public void setLock(int rowId, String columnKey) {
			Optional<DPCellStateDto> existedCellState = findExistCellState(rowId, columnKey);
			if (existedCellState.isPresent()) {
				existedCellState.get().addState("ntsgrid-disable");
			}else{
				List<String> state = new ArrayList<>();
				state.add("ntsgrid-disable");
				DPCellStateDto dto = new DPCellStateDto("_"+rowId, columnKey, state);
				this.lstCellState.add(dto);
		}

	}

}
