package nts.uk.ctx.sys.env.infra.entity.mailnoticeset.company;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;

/**
 * Entity ユーザー情報の使用方法
 */
@Data
@Entity
@Table(name = "SEVMT_USER_INFO_USE")
@EqualsAndHashCode(callSuper = true)
public class SevmtUserInfoUse extends UkJpaEntity implements Serializable {

    /**
     *
     */
    private static final long serialVersionUID = 1L;

    // column 排他バージョン
    @Version
    @Column(name = "EXCLUS_VER")
    private long version;

    @Id
    @Column(name = "CID")
    public String cId;

    /**
     * プロフィールの利用
     */
    @Column(name = "PROFILE_USE")
    private Integer useOfProfile;

    /**
     * パスワードの利用
     */
    @Column(name = "PASSWORD_USE")
    private Integer useOfPassword;

    /**
     * 通知の利用
     */
    @Column(name = "NOTIFICATION_USE")
    private Integer useOfNotice;

    /**
     * 言語の利用
     */
    @Column(name = "LANGUAGE_USE")
    private Integer useOfLanguage;

    /**
     * 連絡先利用設定
     */
    @Column(name = "PHONE_NUMBER_COM_USE")
    private Integer phoneNumberComUse;

    /**
     * 更新可能：（するしない区分）
     */
    @Column(name = "PHONE_NUMBER_COM_UP_ABLE")
    private Integer phoneNumberComUpdatable;

    /**
     * 連絡先利用設定
     */
    @Column(name = "PHONE_NUMBER_PS_USE")
    private Integer phoneNumberPsUse;

    /**
     * 更新可能：（するしない区分）
     */
    @Column(name = "PHONE_NUMBER_PS_UP_ABLE")
    private Integer phoneNumberPsUpdatable;

    /**
     *連絡先利用設定
     */
    @Column(name = "URGENT_PHONE_NUMBER1_USE")
    private Integer urgentPhoneNumber1Use;

    /**
     *更新可能：（するしない区分）
     */
    @Column(name = "URGENT_PHONE_NUMBER1_UP_ABLE")
    private Integer urgentPhoneNumber1Updatable;

    /**
     *連絡先利用設定
     */
    @Column(name = "URGENT_PHONE_NUMBER2_USE")
    private Integer urgentPhoneNumber2Use;

    /**
     *更新可能：（するしない区分）
     */
    @Column(name = "URGENT_PHONE_NUMBER2_UP_ABLE")
    private Integer urgentPhoneNumber2Updatable;

    /**
     *連絡先利用設定
     */
    @Column(name = "DIAL_IN_NUMBER_USE")
    private Integer dialInNumberUse;

    /**
     *更新可能：（するしない区分）
     */
    @Column(name = "DIAL_IN_NUMBER_UP_ABLE")
    private Integer dialInNumberUpdatable;

    /**
     *連絡先利用設定
     */
    @Column(name = "EXTENSION_NUMBER_USE")
    private Integer extensionNumberUse;

    /**
     *更新可能：（するしない区分）
     */
    @Column(name = "EXTENSION_NUMBER_UP_ABLE")
    private Integer extensionNumberUpdatable;

    /**
     *会社メールアドレス
     */
    @Column(name = "MAIL_COM_USE")
    private Integer mailComUse;

    /**
     *更新可能：（するしない区分）
     */
    @Column(name = "MAIL_COM_UP_ABLE")
    private Integer mailComUpdatable;

    /**
     *連絡先利用設定
     */
    @Column(name = "MAIL_PS_USE")
    private Integer mailPsUse;

    /**
     *更新可能：（するしない区分）
     */
    @Column(name = "MAIL_PS_UP_ABLE")
    private Integer mailPsUpdatable;

    /**
     *連絡先利用設定
     */
    @Column(name = "PHONE_MAIL_COM_USE")
    private Integer phoneMailComUse;

    /**
     *更新可能：（するしない区分）
     */
    @Column(name = "PHONE_MAIL_COM_UP_ABLE")
    private Integer phoneMailComUpdatable;

    /**
     *連絡先利用設定
     */
    @Column(name = "PHONE_MAIL_PS_USE")
    private Integer phoneMailPsUse;

    /**
     *更新可能：（するしない区分）
     */
    @Column(name = "PHONE_MAIL_PS_UP_ABLE")
    private Integer phoneMailPsUpdatable;

    /**
     *他の連絡先名1
     */
    @Column(name = "OTHER＿CONTACT1_NAME")
    private String otherContact1Name;

    /**
     *他の連絡先1利用設定
     */
    @Column(name = "OTHER＿CONTACT1_USE")
    private Integer otherContact1Use;

    /**
     *他の連絡先名2
     */
    @Column(name = "OTHER＿CONTACT2_NAME")
    private String otherContact2Name;

    /**
     *他の連絡先2利用設定
     */
    @Column(name = "OTHER＿CONTACT2_USE")
    private Integer otherContact2Use;

    /**
     *他の連絡先名3
     */
    @Column(name = "OTHER＿CONTACT3_NAME")
    private String otherContact3Name;

    /**
     *他の連絡先3利用設定
     */
    @Column(name = "OTHER＿CONTACT3_USE")
    private Integer otherContact3Use;

    /**
     *他の連絡先名4
     */
    @Column(name = "OTHER＿CONTACT4_NAME")
    private String otherContact4Name;

    /**
     *他の連絡先4利用設定
     */
    @Column(name = "OTHER＿CONTACT4_USE")
    private Integer otherContact4Use;

    /**
     *他の連絡先名5
     */
    @Column(name = "OTHER＿CONTACT5_NAME")
    private String otherContact5Name;

    /**
     *他の連絡先5利用設定
     */
    @Column(name = "OTHER＿CONTACT5_USE")
    private Integer otherContact5Use;

    /**
     *記念日の表示
     */
    @Column(name = "ANNIVERSARY_USE")
    private Integer anniversaryUse;

    /**
     *カレンダーの予約の表示
     */
    @Column(name = "CALENDAR_RESERVATION_USE")
    private Integer calendarReservationUse;

    @Override
    protected Object getKey() {
        return this.cId;
    }
}