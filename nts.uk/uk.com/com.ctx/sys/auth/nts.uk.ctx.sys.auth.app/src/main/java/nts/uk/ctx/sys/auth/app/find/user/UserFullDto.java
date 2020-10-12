package nts.uk.ctx.sys.auth.app.find.user;

import lombok.AllArgsConstructor;
import lombok.Data;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.sys.auth.dom.user.User;

/**
 * Dto ユーザ
 */
@Data
@AllArgsConstructor
public class UserFullDto {

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

    public UserFullDto toDto(User domain) {
        return new UserFullDto(
                domain.getUserID(),
                domain.isDefaultUser(),
                domain.getPassword().v(),
                domain.getLoginID().v(),
                domain.getContractCode().v(),
                domain.getExpirationDate(),
                domain.getSpecialUser().value,
                domain.getMultiCompanyConcurrent().value,
                domain.getMailAddress().isPresent() ? domain.getMailAddress().get().v() : null,
                domain.getUserName().isPresent() ? domain.getUserName().get().v() : null,
                domain.getAssociatedPersonID().isPresent() ? domain.getAssociatedPersonID().get() : null,
                domain.getPassStatus().value,
                domain.getLanguage().value
        );
    }
}
