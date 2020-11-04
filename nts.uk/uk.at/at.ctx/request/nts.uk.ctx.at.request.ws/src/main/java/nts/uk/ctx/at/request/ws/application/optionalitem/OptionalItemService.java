package nts.uk.ctx.at.request.ws.application.optionalitem;

import nts.uk.ctx.at.request.app.command.application.optionalitem.RegisterOptionalItemApplicationCommand;
import nts.uk.ctx.at.request.app.command.application.optionalitem.RegisterOptionalItemApplicationCommandHandler;
import nts.uk.ctx.at.request.app.find.application.optionalitem.OptionalItemApplicationQuery;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.optionalitemappsetting.OptionalItemAppSetDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.optionalitemappsetting.OptionalItemAppSetFinder;
import nts.uk.ctx.at.shared.app.find.scherec.dailyattendanceitem.ControlOfAttendanceItemsDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.stream.Collectors;

@Path("ctx/at/request/application/optionalitem")
@Produces("application/json")
public class OptionalItemService {

    @Inject
    private OptionalItemApplicationQuery optionalItemApplicationQuery;

    @Inject
    RegisterOptionalItemApplicationCommandHandler addOptionalItemCommandHandler;

    @Inject
    private OptionalItemAppSetFinder finder;

    @POST
    @Path("optional_item_application_setting")
    public List<OptionalItemAppSetDto> get() {
        int UsageClassification = 1;
        return finder.findAllByCompany().stream().filter(optionalItemAppSetDto -> optionalItemAppSetDto.getUseAtr() == UsageClassification).collect(Collectors.toList());
    }

    @POST
    @Path("getControlAttendance")
    public List<ControlOfAttendanceItemsDto> getControlAttendance(List<Integer> optionalItemNos) {
        return optionalItemApplicationQuery.findControlOfAttendance(optionalItemNos);
    }

    @POST
    @Path("register")
    public void register(RegisterOptionalItemApplicationCommand command) {
        addOptionalItemCommandHandler.handle(command);
    }
}