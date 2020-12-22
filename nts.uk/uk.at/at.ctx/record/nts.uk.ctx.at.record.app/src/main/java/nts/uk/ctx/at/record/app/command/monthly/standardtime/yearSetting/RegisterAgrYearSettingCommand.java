package nts.uk.ctx.at.record.app.command.monthly.standardtime.yearSetting;

import lombok.Getter;

/**
 * 特別条項設定を新規登録する（年度）
 */
@Getter
public class RegisterAgrYearSettingCommand {

    private String employeeId;

    private int year;

    private int errorTime;

    private int alarmTime;

}
