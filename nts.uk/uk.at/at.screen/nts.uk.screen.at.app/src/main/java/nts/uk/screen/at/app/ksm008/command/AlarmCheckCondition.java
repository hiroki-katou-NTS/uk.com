package nts.uk.screen.at.app.ksm008.command;

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
