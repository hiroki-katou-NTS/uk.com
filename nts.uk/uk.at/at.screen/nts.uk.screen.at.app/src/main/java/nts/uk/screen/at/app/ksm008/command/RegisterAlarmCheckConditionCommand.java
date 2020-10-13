package nts.uk.screen.at.app.ksm008.command;

import lombok.Data;

import java.util.List;

/** 勤務予定のアラームチェック条件を登録する */
@Data
public class RegisterAlarmCheckConditionCommand {
    List<AlarmCheckCondition> alarmCheckCondition;
}
