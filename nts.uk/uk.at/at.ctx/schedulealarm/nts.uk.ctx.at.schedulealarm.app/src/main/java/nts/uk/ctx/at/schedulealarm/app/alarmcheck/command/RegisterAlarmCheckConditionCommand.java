package nts.uk.ctx.at.schedulealarm.app.alarmcheck.command;

import lombok.Data;

import java.util.List;

/** 勤務予定のアラームチェック条件を登録する */
@Data
public class RegisterAlarmCheckConditionCommand {
    List<AlarmCheckCondition> alarmCheckCondition;
}
