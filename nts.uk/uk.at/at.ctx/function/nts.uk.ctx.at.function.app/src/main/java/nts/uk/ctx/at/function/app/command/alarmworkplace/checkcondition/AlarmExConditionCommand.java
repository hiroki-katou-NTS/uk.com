package nts.uk.ctx.at.function.app.command.alarmworkplace.checkcondition;

import lombok.Data;

@Data
public class AlarmExConditionCommand {

    private int no;

    private boolean check;

    private String message;

}
