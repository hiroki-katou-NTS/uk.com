package nts.uk.ctx.at.function.app.command.alarmworkplace.checkcondition;

import lombok.Data;
import nts.uk.ctx.at.function.dom.alarmworkplace.checkcondition.AlarmCheckCdtWorkplaceCategory;
import nts.uk.shr.com.context.AppContexts;

import java.util.List;

@Data
public class AddAlarmCheckCdtWkpCommand {

    private AlarmCheckWkpCommand alarmCheck;

    private List<AlarmExConditionCommand> alarmCheckCon;


}
