package nts.uk.ctx.at.request.ws.application.optionalitem;

import nts.uk.ctx.at.request.app.command.application.optionalitem.*;
import nts.uk.ctx.at.request.app.find.application.gobackdirectly.ParamUpdate;
import nts.uk.ctx.at.request.app.find.application.optitem.OptionalItemApplicationQuery;
import nts.uk.ctx.at.request.app.find.application.optitem.optitemdto.OptionalItemApplicationDetail;
import nts.uk.ctx.at.request.app.find.application.optitem.optitemdto.OptionalItemApplicationDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.optionalitemappsetting.OptionalItemAppSetDto;
import nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.optionalitemappsetting.OptionalItemAppSetFinder;
import nts.uk.ctx.at.request.dom.application.common.service.other.output.ProcessResult;
import nts.uk.ctx.at.shared.app.find.scherec.dailyattendanceitem.ControlOfAttendanceItemsDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.stream.Collectors;

@Path("ctx/at/request/application/optionalitem")
@Produces("application/json")
public class OptionalItemApplicationWebService {

    @Inject
    private OptionalItemApplicationQuery optionalItemApplicationQuery;

    @Inject
    private RegisterOptionalItemApplicationCommandHandler addOptionalItemCommandHandler;

    @Inject
    private UpdateOptionalItemApplicationCommandHandler updateOptionalItemCommandHandler;

    @Inject
    private OptionalItemAppSetFinder optionalItemAppSetFinder;

    @POST
    @Path("optionalItemAppSetting")
    public List<OptionalItemAppSetDto> get() {
        int UsageClassification = 1;
        return this.optionalItemAppSetFinder.findAllByCompany().stream().filter(optionalItemAppSetDto -> optionalItemAppSetDto.getUseAtr() == UsageClassification).collect(Collectors.toList());
    }

    @POST
    @Path("getControlAttendance")
    public List<ControlOfAttendanceItemsDto> getControlAttendance(List<Integer> optionalItemNos) {
        return this.optionalItemApplicationQuery.findControlOfAttendance(optionalItemNos);
    }

    @POST
    @Path("register")
    public ProcessResult register(RegisterOptionalItemApplicationCommand command) {
        return this.addOptionalItemCommandHandler.handle(command);
    }

    @POST
    @Path("update")
    public ProcessResult update(UpdateOptionalItemApplicationCommand command) {
        return this.updateOptionalItemCommandHandler.handle(command);
    }

    @POST
    @Path("getDetail")
    public OptionalItemApplicationDetail getDetail(ParamUpdate param) {
        return this.optionalItemApplicationQuery.getDetail(param.getApplicationId());
    }
}