package nts.uk.ctx.at.function.ws.alarmworkplace.alarmlist;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.find.alarmworkplace.alarmlist.CheckConditionDto;
import nts.uk.ctx.at.function.app.find.alarmworkplace.alarmlist.ExtractAlarmListWorkPlaceFinder;
import nts.uk.ctx.at.function.app.find.alarmworkplace.alarmlist.InitActiveAlarmListDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * KAL011-アラームリスト(職場別)
 */
@Path("at/function/alarm/alarm-list")
@Produces("application/json")
public class ExtractAlarmListWorkPlaceWebService extends WebService {
    @Inject
    private ExtractAlarmListWorkPlaceFinder extractAlarmListWorkPlaceFinder;

    @POST
    @Path("init")
    public InitActiveAlarmListDto initActiveAlarmList() {
        return extractAlarmListWorkPlaceFinder.initActiveAlarmList();
    }

    @POST
    @Path("get-check-conditions")
    public List<CheckConditionDto> getCheckConditions() {
        return extractAlarmListWorkPlaceFinder.getCheckConditions();
    }
}
