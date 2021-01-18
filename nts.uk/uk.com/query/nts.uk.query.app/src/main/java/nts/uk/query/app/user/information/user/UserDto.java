package nts.uk.query.app.user.information.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.user.User;

/**
 * Dto ユーザ
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class UserDto {

    /**
     * ユーザID
     */
    private String userId;

    /**
     * デフォルトユーザ
     */
    private boolean defaultUser;

    /**
     * パスワード
     */
    private String password;

    /**
     * ログインID
     */
    private String loginID;

    /**
     * 契約コード
     */
    private String contractCode;

    /**
     * 有効期限
     */
    private GeneralDate expirationDate;

    /**
     * 特別利用者
     */
    private int specialUser;

    /**
     * 複数会社を兼務する
     */
    private int multiCompanyConcurrent;

    /**
     * メールアドレス
     */
    private String mailAddress;

    /**
     * ユーザ名
     */
    private String userName;

    /**
     * 紐付け先個人ID
     */
    private String associatedPersonID;

    /**
     * パスワード状態
     */
    private int passStatus;

    /**
     * 言語
     */
    private int language;

    public static UserDto toDto(User domain) {
        return new UserDto(
                domain.getUserID(),
                domain.isDefaultUser(),
                domain.getPassword() == null ? null : domain.getPassword().v(),
                domain.getLoginID() == null ? null : domain.getLoginID().v(),
                domain.getContractCode() == null ? null : domain.getContractCode().v(),
                domain.getExpirationDate(),
                domain.getSpecialUser() == null ? null : domain.getSpecialUser().value,
                domain.getMultiCompanyConcurrent() == null ? null : domain.getMultiCompanyConcurrent().value,
                domain.getMailAddress().isPresent() ? domain.getMailAddress().get().v() : null,
                domain.getUserName().isPresent() ? domain.getUserName().get().v() : null,
                domain.getAssociatedPersonID().isPresent() ? domain.getAssociatedPersonID().get() : null,
                domain.getPassStatus() == null ? null : domain.getPassStatus().value,
                domain.getLanguage() == null ? null : domain.getLanguage().value
        );
    }

    public User toDomain() {
        return User.createFromJavatype(
                userId,
                defaultUser,
                password,
                loginID,
                contractCode,
                expirationDate,
                specialUser,
                multiCompanyConcurrent,
                mailAddress,
                userName,
                associatedPersonID,
                passStatus,
                language
        );
    }
}
