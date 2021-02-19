package nts.uk.ctx.at.schedulealarm.ws.alarmcheck;

import nts.uk.ctx.at.schedulealarm.app.alarmcheck.command.RegisterAlarmCheckConditionCommand;
import nts.uk.ctx.at.schedulealarm.app.alarmcheck.command.RegisterAlarmCheckConditionCommandHandler;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("screen/at/ksm008/alarm_contidion")
@Produces(MediaType.APPLICATION_JSON)
public class AlarmCheckConditionScheduleWs {

    @Inject
    RegisterAlarmCheckConditionCommandHandler commandHandler;

    /**
     * コードとサブコードを指定してメッセージ内容を取得する
     */
    @POST
    @Path("register")
    public void register(RegisterAlarmCheckConditionCommand command) {
        commandHandler.handle(command);
    }

}
