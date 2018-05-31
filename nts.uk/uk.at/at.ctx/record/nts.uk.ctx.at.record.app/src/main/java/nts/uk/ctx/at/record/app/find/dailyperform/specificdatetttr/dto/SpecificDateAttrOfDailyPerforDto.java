package nts.uk.ctx.at.record.app.find.dailyperform.specificdatetttr.dto;

import java.util.ArrayList;
import java.util.List;

import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.record.dom.raisesalarytime.SpecificDateAttrOfDailyPerfor;
import nts.uk.ctx.at.record.dom.raisesalarytime.SpecificDateAttrSheet;
import nts.uk.ctx.at.record.dom.raisesalarytime.enums.SpecificDateAttr;
import nts.uk.ctx.at.record.dom.raisesalarytime.primitivevalue.SpecificDateItemNo;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemLayout;
import nts.uk.ctx.at.shared.dom.attendance.util.anno.AttendanceItemRoot;
import nts.uk.ctx.at.shared.dom.attendance.util.item.AttendanceItemCommon;

@Data
@AttendanceItemRoot(rootName = "日別実績の特定日区分")
public class SpecificDateAttrOfDailyPerforDto extends AttendanceItemCommon {

	private String employeeId;

	private GeneralDate ymd;

	@AttendanceItemLayout(layout = "A", jpPropertyName = "特定日区分", listMaxLength = 10, indexField = "itemNo")
	private List<SpecificDateAttrDto> sepecificDateAttrs;

	public static SpecificDateAttrOfDailyPerforDto getDto(SpecificDateAttrOfDailyPerfor domain) {
		SpecificDateAttrOfDailyPerforDto dto = new SpecificDateAttrOfDailyPerforDto();
		if (domain != null) {
			dto.setEmployeeId(domain.getEmployeeId());
			dto.setYmd(domain.getYmd());
			dto.setSepecificDateAttrs(ConvertHelper.mapTo(domain.getSpecificDateAttrSheets(), (c) -> {
				return new SpecificDateAttrDto(
						c.getSpecificDateAttr() == null ? 0 : c.getSpecificDateAttr().value, 
						c.getSpecificDateItemNo().v().intValue());
			}));
			dto.exsistData();
		}
		return dto;
	}

	@Override
	public String employeeId() {
		return this.employeeId;
	}

	@Override
	public GeneralDate workingDate() {
		return this.ymd;
	}

	@Override
	public SpecificDateAttrOfDailyPerfor toDomain(String emp, GeneralDate date) {
		if(!this.isHaveData()) {
			return null;
		}
		if (employeeId == null) {
			employeeId = this.employeeId();
		}
		if (date == null) {
			date = this.workingDate();
		}
		return new SpecificDateAttrOfDailyPerfor(emp,
				ConvertHelper.mapTo(sepecificDateAttrs,
						(c) -> new SpecificDateAttrSheet(new SpecificDateItemNo(c.getItemNo()),
								ConvertHelper.getEnum(c.getSpecificDate(), SpecificDateAttr.class))),
						date);
	}
}
