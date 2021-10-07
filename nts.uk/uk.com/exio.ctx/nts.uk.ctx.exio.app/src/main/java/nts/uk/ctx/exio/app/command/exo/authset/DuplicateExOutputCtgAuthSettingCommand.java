package nts.uk.ctx.exio.app.command.exo.authset;

import lombok.Value;

@Value
public class DuplicateExOutputCtgAuthSettingCommand {
    /** ロール種類 */
    int roleType;

    /** 選択ロールID */
    int selectedRoleId;

    /** 選択ロール名 */
    int selectedRoleName;
}
