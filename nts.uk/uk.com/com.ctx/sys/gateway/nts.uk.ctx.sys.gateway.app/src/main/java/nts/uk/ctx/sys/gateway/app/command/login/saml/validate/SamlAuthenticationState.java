package nts.uk.ctx.sys.gateway.app.command.login.saml.validate;

import lombok.AccessLevel;
import lombok.RequiredArgsConstructor;

/**
 * SAML認証の結果区分みたいな
 */
@RequiredArgsConstructor(access = AccessLevel.PRIVATE)
public enum SamlAuthenticationState {

    /** SAMLSettingが無い */
    NO_SAML_SETTING("Msg_1980"),

    /** SAMLレスポンスが不正 */
    INVALID_SAML_RESPONSE("Msg_1988"),

    /** IdPユーザとの紐付けが無い */
    NO_IDP_USER_ASSOCIATION("Msg_1989"), // メッセージID使ってない

    /** 社員が存在しない（社員のデータが物理削除されている、かなりのレアケース） */
    NO_EMPLOYEE("Msg_1990"), // メッセージID使ってない

    /** 成功 */
    SUCCESS(null),

    ;
    public final String errorMessageId;

    public static SamlAuthenticationState of(IdentifySamlUser.ErrorType errorType) {
        switch (errorType) {
            case NO_IDP_USER_ASSOCIATION: return NO_IDP_USER_ASSOCIATION;
            case NO_EMPLOYEE: return NO_EMPLOYEE;
            default: throw new RuntimeException("not supported: " + errorType);
        }
    }

    public boolean isValidated() {
        return this != NO_SAML_SETTING && this != INVALID_SAML_RESPONSE;
    }

    public boolean isAssociationNeeded() {
        // NO_EMPLOYEEの場合、すでにある「社員との紐付けが失われた紐付けデータ」を消す必要がある
        return this == NO_IDP_USER_ASSOCIATION || this == NO_EMPLOYEE;
    }
}
