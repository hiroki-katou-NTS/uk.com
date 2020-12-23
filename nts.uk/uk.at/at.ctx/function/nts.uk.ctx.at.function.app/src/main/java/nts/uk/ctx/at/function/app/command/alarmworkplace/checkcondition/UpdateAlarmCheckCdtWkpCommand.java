package nts.uk.ctx.at.function.app.command.alarmworkplace.checkcondition;

import lombok.Data;

import java.util.List;

@Data
public class UpdateAlarmCheckCdtWkpCommand {

    private AlarmCheckWkpCommand alarmCheck;

    private List<AlarmExConditionCommand> alarmCheckCon;

}
