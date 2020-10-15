package nts.uk.ctx.at.record.app.command.monthly.standardtime.monthSetting;

import lombok.Getter;

/**
 * 特別条項設定を新規登録する（年度）
 */
@Getter
public class RegisterAgrMonthSettingCommand {

    private String employeeId;

    private Integer yearMonth;

    private Integer errorTime;

    private Integer alarmTime;

}
