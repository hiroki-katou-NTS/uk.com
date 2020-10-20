package nts.uk.query.app.user.information.password.policy;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.PasswordPolicy;

import java.math.BigDecimal;

/**
 * Dto パスワードポリシー
 */
@Data
@AllArgsConstructor
@NoArgsConstructor
public class PasswordPolicyDto {

    /**
     * 契約コード
     */
    private String contractCode;

    /**
     * パスワード変更通知
     */
    private BigDecimal notificationPasswordChange;

    /**
     * ログイン時にパスワードに従っていない場合変更させる
     */
    private Boolean loginCheck;

    /**
     * 最初のパスワード変更
     */
    private Boolean initialPasswordChange;

    /**
     * 利用する
     */
    private Boolean isUse;

    /**
     * 履歴回数
     */
    private BigDecimal historyCount;

    /**
     * 最低桁数
     */
    private BigDecimal lowestDigits;

    /**
     * 有効期間
     */
    private BigDecimal validityPeriod;

    /**
     * 複雑さ
     */
    private BigDecimal numberOfDigits;

    /**
     *
     */
    private BigDecimal symbolCharacters;

    /**
     *
     */
    private BigDecimal alphabetDigit;

    public static PasswordPolicyDto toDto(PasswordPolicy domain) {
        return new PasswordPolicyDto(
                domain.getContractCode().v(),
                domain.getNotificationPasswordChange().v(),
                domain.isLoginCheck(),
                domain.isInitialPasswordChange(),
                domain.isUse(),
                domain.getHistoryCount().v(),
                domain.getLowestDigits().v(),
                domain.getValidityPeriod().v(),
                domain.getNumberOfDigits().v(),
                domain.getSymbolCharacters().v(),
                domain.getAlphabetDigit().v()
        );
    }
}
