/**
 * 1:59:07 PM Aug 21, 2017
 */
package nts.uk.screen.at.app.dailyperformance.correction.dto;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

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

	// A13_1 コメント
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
		DailyPerformanceEmployeeDto emp = this.lstEmployee.stream().filter(e -> e.getId().equals(employeeId))
				.findFirst().orElse(null);
		return emp == null ? false : emp.isLoginUser();
	}

	/** Find cell by dataID and columnKey */
	private boolean findAndUpdateCellState(String dataId, String columnKey, String state) {
		for (DPCellStateDto cs : this.lstCellState){
			if(cs.getRowId().equals(dataId) && cs.getColumnKey().equals(columnKey)){
				cs.addState(state);
				return true;
			}
		}
		return false;
	}

	/** Set disable cell & Create not existed cell */
	private void setDisableCell(DPHeaderDto header, DPDataDto data, Map<Integer, DPAttendanceItem> mapDP) {
		String dataId = toId(data.getId());
		if (!findAndUpdateCellState(dataId, header.getKey(), "ntsgrid-disable")) {
			String id = getID(header.getKey());
			int attendanceAtr = mapDP.get(Integer.parseInt(id)).getAttendanceAtr();
			if (attendanceAtr == DailyAttendanceAtr.Code.value
					|| attendanceAtr == DailyAttendanceAtr.Classification.value) {
				if (attendanceAtr == DailyAttendanceAtr.Classification.value) {
					this.lstCellState.add(new DPCellStateDto(dataId, "NO" + id, toList("ntsgrid-disable")));
				} else {
					this.lstCellState.add(new DPCellStateDto(dataId, "Code" + id, toList("ntsgrid-disable")));
				}
				this.lstCellState.add(new DPCellStateDto(dataId, "Name" + id, toList("ntsgrid-disable")));
			} else {
				this.lstCellState.add(new DPCellStateDto(dataId, header.getKey(), toList("ntsgrid-disable")));
			}
		}
	}

	private String getID(String key) {
		key = key.trim();
		return key.substring(1, key.length());
	}

	/** Mark current login employee */
	public void markLoginUser() {
		String loginUser = AppContexts.user().employeeId();
		markLoginUser(loginUser);
	}

	/** Mark login employee */
	public void markLoginUser(String sId) {
		lstEmployee.stream().filter(e -> e.getId().equals(sId)).findFirst().ifPresent(e -> {
			e.markAsLoggedIn();
		});
	}

	/** Create Access Modifier Cellstate */
	public void createAccessModifierCellState(Map<Integer, DPAttendanceItem> mapDP) {
		String loginUser = AppContexts.user().employeeId();
		this.getLstData().forEach(data -> {
			boolean isLoginUser = loginUser.equals(data.getEmployeeId());
			this.getLstControlDisplayItem().getLstHeader().forEach(header -> {
				if (header.getChangedByOther() && !header.getChangedByYou()) {
					if (isLoginUser) {
						setDisableCell(header, data, mapDP);
					}
				} else if (!header.getChangedByOther() && header.getChangedByYou()) {
					if (!isLoginUser) {
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
					String errorType = getErrorType(lstErrorSetting, error);
					// add error alarm to response data
					if (!data.getError().isEmpty()) {
						if (!errorType.equals(data.getError())) {
							data.setError("ER/AL");
						}
					} else {
						data.setError(errorType);
					}
					// add error alarm cell state
					setCellState(toId(data.getId()), error.getAttendanceItemId().toString(),
							errorType.equals("ER") ? "ntsgrid-error" : "ntsgrid-alarm");
				}
			});
		});
	}

	private String toId(int dataId) {
		return "_" + dataId;
	}

	private String getErrorType(List<DPErrorSettingDto> lstErrorSetting, DPErrorDto error) {
		DPErrorSettingDto setting = lstErrorSetting.stream()
				.filter(c -> c.getErrorAlarmCode().equals(error.getErrorCode())).findFirst().orElse(null);
		if (setting == null) {
			return "";
		}
		return setting.getTypeAtr() == 0 ? "ER" : "AL";
	}

	/** Set AlarmCell state for Fixed cell */
	public void setAlarmCellForFixedColumn(int dataId) {
		String id = toId(dataId);
		Stream.of("date", "employeeCode", "employeeName").forEach(columnKey -> {
			setCellState(id, columnKey, "ntsgrid-alarm");
		});
	}

	private void setCellState(String dataId, String columnKey, String state) {
		if (!findAndUpdateCellState(dataId, columnKey, state)) {
			this.lstCellState.add(new DPCellStateDto(dataId, columnKey, toList(state)));
		}
	}

	private List<String> toList(String... item) {
		return new ArrayList<>(Arrays.asList(item));
	}

	/** Set AlarmCell state for Fixed cell */
	public void setLock(int rowId, String columnKey) {
		String id = toId(rowId);
		setCellState(id, columnKey, "ntsgrid-disable");
	}

}
