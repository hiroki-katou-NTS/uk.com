package nts.uk.screen.at.app.stamp.personalengraving.query;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.app.find.stamp.management.DisplayScreenStampingResultDto;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.StampRecordDto;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.ConfirmStatusActualResult;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.dailyattendance.converter.util.item.ItemValue;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;
import nts.uk.ctx.at.shared.dom.worktime.worktimeset.WorkTimeSetting;
import nts.uk.ctx.at.shared.dom.worktype.WorkType;

@Data
public class StampResultConfirmDto {

	private List<StampRecordDto> stampRecords = new ArrayList<>();
	private List<AttItemName> dailyItems;
	private List<ItemValueDto> itemValues = new ArrayList<>();
	private List<WorkDto> workTypes = new ArrayList<>();
	private List<WorkTimeDto> workTimeTypes = new ArrayList<>();
	private ConfirmResultDto confirmResult;
	private String workPlaceName;
	private String attendance;
	private String leave;

	public StampResultConfirmDto(List<DisplayScreenStampingResultDto> screenDisplays, List<AttItemName> dailyItems,
			List<ItemValue> itemValues, List<WorkType> workTypes, List<WorkTimeSetting> workTimes,
			ConfirmStatusActualResult cfsr, Optional<ItemValue> attendance, Optional<ItemValue> leave) {

		for (DisplayScreenStampingResultDto display : screenDisplays) {
			this.workPlaceName = display.getWorkPlaceName();
			this.stampRecords.addAll(display.getStampDataOfEmployeesDto().getStampRecords());
		}

		for (AttItemName item : dailyItems) {
			Optional<ItemValue> oValue = itemValues.stream().filter(a -> a.getItemId() == item.getAttendanceItemId()).findFirst(); 
			this.itemValues.add(new ItemValueDto(oValue, item));
		}

		for (WorkType item : workTypes) {
			this.workTypes.add(new WorkDto(item.getWorkTypeCode().v(), item.getName().v()));
		}

		for (WorkTimeSetting item : workTimes) {
			this.workTimeTypes
					.add(new WorkTimeDto(item.getWorktimeCode().v(), item.getWorkTimeDisplayName().getWorkTimeName().v()));
		}
		
		this.attendance = attendance.isPresent() ? attendance.get().getValue() : null;
		this.leave = leave.isPresent() ? leave.get().getValue() : null;
		if(cfsr != null) {
			if (cfsr.getEmployeeId() != null && cfsr.getDate() != null) {
				this.confirmResult = new ConfirmResultDto(cfsr.getEmployeeId(), 
						cfsr.getDate().toString(), 
						cfsr.isStatus(),
						cfsr.getPermissionCheck() != null ? cfsr.getPermissionCheck().value : null, 
								cfsr.getPermissionRelease() != null ? cfsr.getPermissionRelease().value : null);							
			}
		}

	}

	@Data
	@AllArgsConstructor
	class ItemValueDto {
		private String value;
		private String valueType;
		private int itemId;
		private String name;
		public ItemValueDto(Optional<ItemValue> oValue, AttItemName item) {
			if(oValue.isPresent()) {
				ItemValue value = oValue.get();
				this.value = value.getValue();
				this.valueType = value.getValueType().name;
			}
			this.itemId = item.getAttendanceItemId();
			this.name = item.getAttendanceItemName();
		}
	}

	@Data
	@AllArgsConstructor
	class WorkDto {
		private String code;
		private String name;
	}
	
	@Data
	@AllArgsConstructor
	class WorkTimeDto {
		private String code;
		private String name;
	}

	@Data
	@AllArgsConstructor
	class ConfirmResultDto {
		private String employeeId;
		private String date;
		private boolean status;
		protected Integer permissionCheck;
		protected Integer permissionRelease;

	}

}
