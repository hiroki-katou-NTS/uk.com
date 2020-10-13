package nts.uk.ctx.sys.env.dom.mailnoticeset.company;

import java.util.List;

import lombok.Getter;

/**
 * 連絡先情報の設定
 * @author admin
 *
 */
@Getter
public class SettingContactInformation {

    /**
     * ダイヤルイン番号
     */
    private ContactSetting dialInNumber;

    /**
     * 会社メールアドレス
     */
    private ContactSetting companyEmailAddress;

    /**
     * 会社携帯メールアドレス
     */
    private ContactSetting companyMobileEmailAddress;

    /**
     * 個人メールアドレス
     */
    private ContactSetting personalEmailAddress;

    /**
     * 個人携帯メールアドレス
     */
    private ContactSetting personalMobileEmailAddress;

    /**
     * 内線番号
     */
    private ContactSetting extensionNumber;

    /**
     * 携帯電話（会社用）
     */
    private ContactSetting companyMobilePhone;

    /**
     * 携帯電話（個人用）
     */
    private ContactSetting personalMobilePhone;

    /**
     * 緊急電話番号1
     */
    private ContactSetting emergencyNumber1;

    /**
     * 緊急電話番号2
     */
    private ContactSetting emergencyNumber2;

    /**
     * 他の連絡先
     */
    private List<OtherContact> otherContacts;
}
