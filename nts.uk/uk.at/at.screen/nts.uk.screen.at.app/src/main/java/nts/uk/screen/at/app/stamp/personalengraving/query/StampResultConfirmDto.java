package nts.uk.screen.at.app.stamp.personalengraving.query;

import java.util.ArrayList;
import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.uk.ctx.at.record.app.find.stamp.management.DisplayScreenStampingResultDto;
import nts.uk.ctx.at.record.app.find.stamp.management.personalengraving.dto.StampRecordDto;
import nts.uk.ctx.at.record.dom.dailyperformanceprocessing.confirmationstatus.ConfirmStatusActualResult;
import nts.uk.ctx.at.request.app.find.application.common.dto.WorkTimeDto;
import nts.uk.ctx.at.shared.dom.attendance.util.item.ItemValue;
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

	public StampResultConfirmDto(List<DisplayScreenStampingResultDto> screenDisplays, List<AttItemName> dailyItems,
			List<ItemValue> itemValues, List<WorkType> workTypes, List<WorkTimeSetting> workTimes,
			ConfirmStatusActualResult cfsr) {

		for (DisplayScreenStampingResultDto display : screenDisplays) {
			this.stampRecords.addAll(display.getStampDataOfEmployeesDto().getStampRecords());
		}

		this.dailyItems = dailyItems;
		for (ItemValue item : itemValues) {
			this.itemValues.add(new ItemValueDto(item.getValue(), item.getValueType().name, item.getItemId()));
		}

		for (WorkType item : workTypes) {
			this.workTypes.add(new WorkDto(item.getWorkTypeCode().v(), item.getName().v()));
		}

		for (WorkTimeSetting item : workTimes) {
			this.workTimeTypes
					.add(new WorkTimeDto(item.getWorktimeCode().v(), item.getWorkTimeDisplayName().getWorkTimeName().v()));
		}

		this.confirmResult = new ConfirmResultDto(cfsr.getEmployeeId(), cfsr.getDate().toString(), cfsr.isStatus(),
				cfsr.getPermissionCheck().value, cfsr.getPermissionRelease().value);

	}

	@Data
	@AllArgsConstructor
	class ItemValueDto {
		private String value;
		private String valueType;
		private int itemId;
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
