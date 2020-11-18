package nts.uk.ctx.sys.auth.app.command.user.information;

import lombok.Data;
import nts.uk.ctx.sys.auth.app.command.user.information.user.UserDto;

/**
 * Command アカウント情報を登録する
 */
@Data
public class UserChangeCommand {

    /**
     * ユーザを変更する
     */
    private UserDto userChange;
}
