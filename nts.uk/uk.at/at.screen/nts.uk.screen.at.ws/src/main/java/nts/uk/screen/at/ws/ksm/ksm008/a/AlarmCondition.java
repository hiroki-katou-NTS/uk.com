package nts.uk.screen.at.ws.ksm.ksm008.a;

import nts.uk.screen.at.app.ksm008.command.RegisterAlarmCheckConditionCommand;
import nts.uk.screen.at.app.ksm008.command.RegisterAlarmCheckConditionCommandHandler;
import nts.uk.screen.at.app.ksm008.find.AlarmCheckConditionsFinder;
import nts.uk.screen.at.app.ksm008.find.AlarmCheckDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("screen/at/ksm008/alarm_contidion")
@Produces(MediaType.APPLICATION_JSON)
public class AlarmCondition {

    @Inject
    AlarmCheckConditionsFinder alarmCheckConditions;

    @Inject
    RegisterAlarmCheckConditionCommandHandler commandHandler;

    @POST
    @Path("list")
    public List<AlarmCheckDto> getList() {
        return alarmCheckConditions.getItems();
    }


    @POST
    @Path("getMsg/{code}")
    public AlarmCheckDto getMsg(@PathParam("code") String alarmCode) {
        return alarmCheckConditions.getMsg(alarmCode);
    }


    @POST
    @Path("register")
    public void register(RegisterAlarmCheckConditionCommand command) {
        commandHandler.handle(command);
    }

}
