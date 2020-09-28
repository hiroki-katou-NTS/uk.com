package nts.uk.screen.at.ws.ksm.ksm008.a;

import nts.uk.screen.at.app.ksm008.query.AlarmCheckConditionsFinder;
import nts.uk.screen.at.app.ksm008.query.AlarmCheckDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.List;

@Path("screen/at/ksm008/alarm_contidion")
@Produces(MediaType.APPLICATION_JSON)
public class AlarmCondition {

    @Inject
    AlarmCheckConditionsFinder alarmCheckConditions;

    @POST
    @Path("list")
    public List<AlarmCheckDto> getList() {
        return alarmCheckConditions.getItems();
    }

}
