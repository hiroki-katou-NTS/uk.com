package nts.uk.screen.at.ws.kaf.kaf020.b;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.app.find.optitem.OptionalItemDto;
import nts.uk.ctx.at.record.app.find.optitem.OptionalItemFinder;
import nts.uk.ctx.at.shared.app.find.scherec.dailyattendanceitem.ControlOfAttendanceItemsDto;
import nts.uk.ctx.at.shared.app.find.scherec.dailyattendanceitem.ControlOfAttendanceItemsFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("screen/at/kaf020/b")
@Produces("application/json")
public class Kaf020BWebService {

    @Inject
    private OptionalItemFinder optionalItemFinder;

    @Inject
    private ControlOfAttendanceItemsFinder controlOfAttendanceItemsFinder;


    @POST
    @Path("get/{no}")
    public InnerDto get(@PathParam("no") Integer optionalItemNo) {
        OptionalItemDto optionalItemDto = optionalItemFinder.find(optionalItemNo);
        ControlOfAttendanceItemsDto controlOfAttendanceItem = controlOfAttendanceItemsFinder.getControlOfAttendanceItem(optionalItemNo.intValue());
        return new InnerDto(optionalItemDto, controlOfAttendanceItem);
    }



}

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
class InnerDto {
    private OptionalItemDto optionalItemDto;
    private ControlOfAttendanceItemsDto controlOfAttendanceItemsDto;
}