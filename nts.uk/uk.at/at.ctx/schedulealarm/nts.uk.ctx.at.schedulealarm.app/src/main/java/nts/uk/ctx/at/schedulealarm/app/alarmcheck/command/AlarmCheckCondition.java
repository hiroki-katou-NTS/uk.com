package nts.uk.ctx.at.schedulealarm.app.alarmcheck.command;

import lombok.Data;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.AlarmCheckConditionScheduleCode;
import nts.uk.ctx.at.schedulealarm.dom.alarmcheck.MessageInfo;

import java.util.List;

@Data
public class AlarmCheckCondition {
    /**
     * コード
     */
    private AlarmCheckConditionScheduleCode code;



    private List<MessageInfo> msgLst;
}
