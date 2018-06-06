package nts.uk.ctx.at.record.app.find.dailyperform.optionalitem.dto;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.daily.optionalitemtime.AnyItemValueOfDaily;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemCommon;

@Data
@AttendanceItemRoot(rootName = "日別実績の任意項目")
public class OptionalItemOfDailyPerformDto extends AttendanceItemCommon {

	private String employeeId;

	private GeneralDate date;

	@AttendanceItemLayout(layout = "A", jpPropertyName = "任意項目値", listMaxLength = 100, indexField = "itemNo")
	private List<OptionalItemValueDto> optionalItems;

	public static OptionalItemOfDailyPerformDto getDto(AnyItemValueOfDaily domain) {
		OptionalItemOfDailyPerformDto dto = new OptionalItemOfDailyPerformDto();
		if (domain != null) {
			dto.setDate(domain.getYmd());
			dto.setEmployeeId(domain.getEmployeeId());
			dto.setOptionalItems(
					ConvertHelper.mapTo(domain.getItems(), (c) -> OptionalItemValueDto.from(c)));
			dto.exsistData();
		}
		return dto;
	}

	public static OptionalItemOfDailyPerformDto getDto(AnyItemValueOfDaily domain, Map<Integer, OptionalItem> master) {
		OptionalItemOfDailyPerformDto dto = new OptionalItemOfDailyPerformDto();
		if (domain != null) {
			dto.setDate(domain.getYmd());
			dto.setEmployeeId(domain.getEmployeeId());
			dto.setOptionalItems(ConvertHelper.mapTo(domain.getItems(), (c) -> 
							OptionalItemValueDto.from(c, getAttrFromMaster(master, c))));
			dto.exsistData();
		}
		return dto;
	}
	
	public void correctItems(Map<Integer, OptionalItem> optionalMaster) {
		optionalItems.stream().forEach(item -> {
//			if(item.isNeedCorrect()) {
				item.correctItem(optionalMaster.get(item.getItemNo()).getOptionalItemAtr());
//			}
		});
		optionalItems.removeIf(item -> !item.isHaveData());
	}

	private static OptionalItemAtr getAttrFromMaster(Map<Integer, OptionalItem> master, AnyItemValue c) {
		return master.get(c.getItemNo().v()).getOptionalItemAtr();
	}

	@Override
	public String employeeId() {
		return this.employeeId;
	}

	@Override
	public GeneralDate workingDate() {
		return this.date;
	}
	
	@Override
	public AnyItemValueOfDaily toDomain(String employeeId, GeneralDate date) {
		if(!this.isHaveData()) {
			return null;
		}
		if (employeeId == null) {
			employeeId = this.employeeId();
		}
		if (date == null) {
			date = this.workingDate();
		}
		optionalItems.removeIf(item -> !item.isHaveData());
		return new AnyItemValueOfDaily(employeeId, date,
				ConvertHelper.mapTo(optionalItems, c -> c == null ? null : c.toDomain()));
	}
}
