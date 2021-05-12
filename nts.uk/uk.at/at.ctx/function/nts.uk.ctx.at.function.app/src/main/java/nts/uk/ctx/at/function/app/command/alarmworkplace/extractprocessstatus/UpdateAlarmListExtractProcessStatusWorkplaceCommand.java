package nts.uk.ctx.at.function.app.command.alarmworkplace.extractprocessstatus;

import lombok.Data;

@Data
public class UpdateAlarmListExtractProcessStatusWorkplaceCommand {
    private String processId;
    private int status;
}
