package nts.uk.ctx.at.function.app.command.alarmworkplace.checkcondition;

import lombok.Data;

@Data
public class AlarmCheckWkpCommand {

    private int category;

    private String code;

    private String name;

}
