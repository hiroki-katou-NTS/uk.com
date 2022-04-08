package nts.uk.screen.at.ws.kdl.kdl016;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.command.kdl.kdl016.*;
import nts.uk.screen.at.app.query.kdl.kdl016.SupportInformationFinder;
import nts.uk.screen.at.app.query.kdl.kdl016.dto.*;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("screen/at/kdl016")
@Produces("application/json")
public class Kdl016WebService extends WebService {
    @Inject
    private SupportInformationFinder supportInfoFinder;

    @Inject
    private DeleteSupportInfoCommandHandler deleteSupportScheduleHandler;

    @Inject
    private RegisterSupportInforCommandHandler registerSupportInforCommand;

    @Inject
    private UpdateSupportInforCommandHandler updateSupportInforCommand;

    @POST
    @Path("a/init")
    public InitialDataScreenA initialScreenAInfo(InitialScreenAParam param) {
        return this.supportInfoFinder.getDataInitScreenA(param.getEmployeeIds(), param.getPeriod());
    }

    @POST
    @Path("a/get")
    public List<SupportInfoDto> getDataByMode(SupportInfoInput input) {
        return this.supportInfoFinder.getInfoByDisplayMode(input);
    }

    @POST
    @Path("a/delete")
    public DeleteSupportInfoResult delete(DeleteSupportInfoCommand command) {
        return this.deleteSupportScheduleHandler.handle(command);
    }

    @POST
    @Path("b/init")
    public Kdl016ScreenBOutput initialScreenB(Kdl016ScreenBParam param) {
        return this.supportInfoFinder.getDataInitScreenB(param.getEmployeeIds(), param.getOrgId(), param.getOrgUnit());
    }

    @POST
    @Path("register")
    public RegisterSupportInforResult register(RegisterSupportInforCommand command) {
        return this.registerSupportInforCommand.handle(command);
    }

    @POST
    @Path("c/init")
    public Kdl016ScreenBOutput initialScreenC(Kd016ScreenCParam param) {
        return this.supportInfoFinder.getDataInitScreenC(param.getOrgId(), param.getOrgUnit(), param.getPeriod());
    }

    // test return map for client
    @POST
    @Path("c/init/v2")
    public Kdl016ScreenBOutput.Kdl016ScreenBOutputV2 initialScreenCV2(Kd016ScreenCParam param) {
        return this.supportInfoFinder.getDataInitScreenCV2(param.getOrgId(), param.getOrgUnit(), param.getPeriod());
    }

    @POST
    @Path("c/load-employee")
    public List<EmployeeInformationDto> getEmployeeByWkp(Kd016ScreenCParam param) {
        return this.supportInfoFinder.getEmployeeInfo(param.getOrgId(), param.getOrgUnit(), param.getPeriod());
    }

    @POST
    @Path("update")
    public UpdateSupportInforResult update(UpdateSupportInforCommand command) {
        return this.updateSupportInforCommand.handle(command);
    }

}
