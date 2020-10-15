package nts.uk.query.app.user.information.password.changelog;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDateTime;
import nts.uk.ctx.sys.auth.dom.password.changelog.PasswordChangeLog;

/**
 * Dto パスワード変更ログ
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordChangeLogDto {

    /**
     * ログID
     */
    private String logId;

    /**
     * ユーザID
     */
    private String userId;

    /**
     * 変更日時
     */
    private GeneralDateTime modifiedDate;

    /**
     * パスワード
     */
    private String password;

    public static PasswordChangeLogDto toDto(PasswordChangeLog domain) {
        return new PasswordChangeLogDto(
                domain.getLogID(),
                domain.getUserID(),
                domain.getModifiedDate(),
                domain.getPassword().v()
        );
    }
}
