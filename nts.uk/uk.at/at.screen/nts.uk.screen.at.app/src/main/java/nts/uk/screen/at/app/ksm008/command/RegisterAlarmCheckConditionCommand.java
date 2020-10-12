package nts.uk.screen.at.app.ksm008.command;

import lombok.Data;

import java.util.List;

@Data
public class RegisterAlarmCheckConditionCommand {
    List<AlarmCheckCondition> alarmCheckCondition;
}
