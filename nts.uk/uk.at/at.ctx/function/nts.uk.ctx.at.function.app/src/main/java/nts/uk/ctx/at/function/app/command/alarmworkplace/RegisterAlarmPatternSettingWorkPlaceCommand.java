package nts.uk.ctx.at.function.app.command.alarmworkplace;

import lombok.Getter;
import nts.uk.ctx.at.function.app.command.alarmworkplace.checkcondition.AlarmPermissionSettingCommand;
import nts.uk.ctx.at.function.app.command.alarmworkplace.checkcondition.CheckConditionCommand;

import java.util.List;

@Getter
public class RegisterAlarmPatternSettingWorkPlaceCommand {

    /**
     * アラームリストパターンコード
     */
    private String patternCode;

    /**
     * 名称
     */
    private String patternName;

    /**
     * 実行権限
     */
    private AlarmPermissionSettingCommand alarmPerSet;

    /**
     * カテゴリ別チェック条件
     */
    private List<CheckConditionCommand> checkConditonList;

}
