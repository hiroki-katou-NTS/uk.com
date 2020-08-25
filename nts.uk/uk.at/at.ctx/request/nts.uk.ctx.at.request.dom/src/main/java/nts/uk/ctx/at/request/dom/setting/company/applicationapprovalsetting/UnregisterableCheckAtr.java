package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting;

import lombok.AllArgsConstructor;

/**
 * 登録不可チェック区分
 */
@AllArgsConstructor
public enum UnregisterableCheckAtr {

    /**
     * チェックしない
     */
    NOT_CHECKED(0, "チェックしない"),

    /**
     * チェックする（登録不可）
     */
    CHECKED(1, "チェックする（登録不可）");

    public final int value;

    public final String name;
}