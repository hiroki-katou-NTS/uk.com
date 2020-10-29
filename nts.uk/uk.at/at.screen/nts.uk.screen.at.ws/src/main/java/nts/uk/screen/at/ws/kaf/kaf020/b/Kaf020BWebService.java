package nts.uk.screen.at.ws.kaf.kaf020.b;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import nts.uk.ctx.at.record.app.find.optitem.OptionalItemDto;
import nts.uk.ctx.at.record.app.find.optitem.OptionalItemFinder;
import nts.uk.ctx.at.request.app.command.application.optionalitem.RegisterOptionalItemApplicationCommand;
import nts.uk.ctx.at.request.app.command.application.optionalitem.RegisterOptionalItemApplicationCommandHandler;
import nts.uk.ctx.at.shared.app.find.scherec.dailyattendanceitem.ControlOfAttendanceItemsDto;
import nts.uk.ctx.at.shared.app.find.scherec.dailyattendanceitem.ControlOfAttendanceItemsFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.ArrayList;
import java.util.List;

@Path("screen/at/kaf020/b")
@Produces("application/json")
public class Kaf020BWebService {

    @Inject
    private OptionalItemFinder optionalItemFinder;

    @Inject
    private ControlOfAttendanceItemsFinder controlOfAttendanceItemsFinder;

    @Inject
    RegisterOptionalItemApplicationCommandHandler addOptionalItemCommandHandler;


    @POST
    @Path("get")
    public List<InnerDto> get(Params params) {
        List<InnerDto> result = new ArrayList<>();
        for (Integer item : params.getSettingItemNoList()) {
            OptionalItemDto optionalItemDto = optionalItemFinder.find(item);
            ControlOfAttendanceItemsDto controlOfAttendanceItem = controlOfAttendanceItemsFinder.getControlOfAttendanceItem(item.intValue() + 640);
            result.add(new InnerDto(optionalItemDto, controlOfAttendanceItem));
        }
        return result;
    }

    @POST
    @Path("register")
    public void register(RegisterOptionalItemApplicationCommand command) {

        addOptionalItemCommandHandler.handle(command);
    }

}

@Setter
@Getter
class Params {
    private List<Integer> settingItemNoList;
}

@NoArgsConstructor
@AllArgsConstructor
@Setter
@Getter
class InnerDto {
    private OptionalItemDto optionalItemDto;
    private ControlOfAttendanceItemsDto controlOfAttendanceItemsDto;
}