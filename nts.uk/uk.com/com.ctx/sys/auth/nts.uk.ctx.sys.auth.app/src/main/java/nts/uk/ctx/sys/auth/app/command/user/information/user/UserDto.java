package nts.uk.ctx.sys.auth.app.command.user.information.user;

import lombok.Data;

/**
 * Command dto ユーザ
 */
@Data
public class UserDto {

    /**
     * 現行のパスワード
     */
    private String currentPassword;

    /**
     * 新しいパスワード
     */
    private String newPassword;

    /**
     * 新しいパスワード（確認）
     */
    private String confirmPassword;

    /**
     * 言語
     */
    private int language;



}
