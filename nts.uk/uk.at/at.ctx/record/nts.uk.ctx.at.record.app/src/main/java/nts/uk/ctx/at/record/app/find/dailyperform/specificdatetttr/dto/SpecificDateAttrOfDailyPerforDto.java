package nts.uk.ctx.at.record.app.find.dailyperform.specificdatetttr.dto;

import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.raisesalarytime.SpecificDateAttrOfDailyPerfor;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.annotation.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.annotation.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.item.ConvertibleAttendanceItem;

@Data
@AttendanceItemRoot(rootName = "日別実績の特定日区分")
public class SpecificDateAttrOfDailyPerforDto implements ConvertibleAttendanceItem {

	private String employeeId;

	private GeneralDate ymd;
	
	@AttendanceItemLayout(layout = "A", jpPropertyName = "特定日区分", listMaxLength = 10, indexField = "itemNo")
	private List<SpecificDateAttrDto> sepecificDateAttrs;
	
	public static SpecificDateAttrOfDailyPerforDto getDto(SpecificDateAttrOfDailyPerfor domain){
		SpecificDateAttrOfDailyPerforDto dto = new SpecificDateAttrOfDailyPerforDto();
		if (domain != null) {
			dto.setSepecificDateAttrs(ConvertHelper.mapTo(domain.getSpecificDateAttrSheets(), (c) -> {
				return new SpecificDateAttrDto(c.getSpecificDateAttr().value,
						c.getSpecificDateItemNo().v().intValue());
			}));
		}
		return dto;
	}
}
