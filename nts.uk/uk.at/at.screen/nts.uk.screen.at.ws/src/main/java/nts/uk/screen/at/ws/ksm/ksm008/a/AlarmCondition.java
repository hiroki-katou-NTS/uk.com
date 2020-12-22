package nts.uk.screen.at.ws.ksm.ksm008.a;

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

    /**
     * 勤務予定のアラームチェック条件一覧を取得する
     */
    @POST
    @Path("list")
    public List<AlarmCheckDto> getAlarmCheckConditionScheduleItems() {
        return alarmCheckConditions.getAlarmCheckConditionScheduleItems();
    }

    /**
     * 勤務予定のアラームチェック条件を登録する
     */
    @POST
    @Path("getMsg/{code}")
    public AlarmCheckDto getMsg(@PathParam("code") String alarmCode) {
        return alarmCheckConditions.getMsg(alarmCode);
    }




}
