package nts.uk.ctx.at.request.app.find.application.optitem.optitemdto;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.at.shared.app.find.scherec.dailyattendanceitem.ControlOfAttendanceItemsDto;

import java.util.List;

@Setter
@Getter
public class OptionalItemApplicationDetail {

    private OptionalItemApplicationDto application;

    private List<OptionalItemDto> optionalItems;

    private List<ControlOfAttendanceItemsDto> controlOfAttendanceItems;
}
