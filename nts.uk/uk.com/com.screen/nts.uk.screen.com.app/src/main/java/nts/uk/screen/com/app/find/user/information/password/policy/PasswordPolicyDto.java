package nts.uk.screen.com.app.find.user.information.password.policy;


import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.ctx.sys.gateway.dom.securitypolicy.password.PasswordPolicy;

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
                domain.getContractCode() == null ? null : domain.getContractCode().v(),
                domain.getNotificationPasswordChange() == null ? null : domain.getNotificationPasswordChange().v(),
                domain.isLoginCheck(),
                domain.isInitialPasswordChange(),
                domain.isUse(),
                domain.getHistoryCount() == null ? null : domain.getHistoryCount().v(),
                domain.getComplexityRequirement().getMinimumLength() == null ? null : new BigDecimal(domain.getComplexityRequirement().getMinimumLength().v()),
                domain.getValidityPeriod() == null ? null : domain.getValidityPeriod().v(),
                domain.getComplexityRequirement().getNumeralDigits() == null ? null : new BigDecimal(domain.getComplexityRequirement().getNumeralDigits().v()),
                domain.getComplexityRequirement().getSymbolDigits() == null ? null : new BigDecimal(domain.getComplexityRequirement().getSymbolDigits().v()),
                domain.getComplexityRequirement().getAlphabetDigits() == null ? null : new BigDecimal(domain.getComplexityRequirement().getAlphabetDigits().v())
        );
    }
}
