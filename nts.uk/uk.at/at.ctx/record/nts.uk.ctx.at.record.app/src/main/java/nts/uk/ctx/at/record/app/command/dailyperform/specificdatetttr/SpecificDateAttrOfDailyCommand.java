package nts.uk.ctx.at.record.app.command.dailyperform.specificdatetttr;

import java.util.Optional;

import lombok.Getter;
import nts.uk.ctx.at.record.app.find.dailyperform.specificdatetttr.dto.SpecificDateAttrOfDailyPerforDto;
import nts.uk.ctx.at.record.dom.raisesalarytime.SpecificDateAttrOfDailyPerfor;
import nts.uk.ctx.at.record.dom.raisesalarytime.SpecificDateAttrSheet;
import nts.uk.ctx.at.record.dom.raisesalarytime.enums.SpecificDateAttr;
import nts.uk.ctx.at.record.dom.raisesalarytime.primitivevalue.SpecificDateItemNo;
import nts.uk.ctx.at.shared.app.util.attendanceitem.ConvertHelper;
import nts.uk.ctx.at.shared.app.util.attendanceitem.DailyWorkCommonCommand;
import nts.uk.ctx.at.shared.dom.attendanceitem.util.item.ConvertibleAttendanceItem;

public class SpecificDateAttrOfDailyCommand extends DailyWorkCommonCommand {

	@Getter
	private Optional<SpecificDateAttrOfDailyPerforDto> data;

	@Override
	public void setRecords(ConvertibleAttendanceItem item) {
		this.data = item == null ? Optional.empty() : Optional.of((SpecificDateAttrOfDailyPerforDto) item);
	}

	@Override
	public SpecificDateAttrOfDailyPerfor toDomain() {
		return !data.isPresent() ? null : new SpecificDateAttrOfDailyPerfor(getEmployeeId(),
						ConvertHelper.mapTo(data.get().getSepecificDateAttrs(),
								(c) -> new SpecificDateAttrSheet(new SpecificDateItemNo(c.getItemNo()),
										ConvertHelper.getEnum(c.getSpecificDate(), SpecificDateAttr.class))),
						getWorkDate());
	}
}
