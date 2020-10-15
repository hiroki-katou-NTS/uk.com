package nts.uk.ctx.sys.env.infra.entity.mailnoticeset.company;

import lombok.Data;
import lombok.EqualsAndHashCode;
import nts.arc.enums.EnumAdaptor;
import nts.uk.ctx.sys.env.dom.mailnoticeset.FunctionId;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.*;
import nts.uk.shr.com.enumcommon.NotUseAtr;
import nts.uk.shr.infra.data.entity.UkJpaEntity;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Entity ユーザー情報の使用方法
 */
@Data
@Entity
@Table(name = "SEVMT_USER_INFO_USE")
@EqualsAndHashCode(callSuper = true)
public class SevmtUserInfoUse extends UkJpaEntity implements
        Serializable,
        UserInfoUseMethod_.MementoGetter,
        UserInfoUseMethod_.MementoSetter,
        SettingContactInformation.MementoSetter,
        SettingContactInformation.MementoGetter {

    /**
     *
     */
    private static final long serialVersionUID = 1L;
    @Id
    @Column(name = "CID")
    public String cId;

    @OneToMany(targetEntity = SevmtMailDestination.class, cascade = CascadeType.ALL, mappedBy = "sevmtUserInfoUse", orphanRemoval = true, fetch = FetchType.LAZY)
    @JoinTable(name = "SEVMT_MAIL_DESTINATION")
    public List<SevmtMailDestination> sevmtMailDestinations;

    // column 排他バージョン
    @Version
    @Column(name = "EXCLUS_VER")
    private long version;
    // column 契約コード
    @Basic(optional = false)
    @Column(name = "CONTRACT_CD")
    private String contractCd;
    /**
     * プロフィールの利用
     */
    @Basic(optional = false)
    @Column(name = "PROFILE_USE")
    private Integer useOfProfile;
    /**
     * パスワードの利用
     */
    @Basic(optional = false)
    @Column(name = "PASSWORD_USE")
    private Integer useOfPassword;
    /**
     * 通知の利用
     */
    @Basic(optional = false)
    @Column(name = "NOTIFICATION_USE")
    private Integer useOfNotice;
    /**
     * 言語の利用
     */
    @Basic(optional = false)
    @Column(name = "LANGUAGE_USE")
    private Integer useOfLanguage;
    /**
     * 連絡先利用設定
     */
    @Basic(optional = true)
    @Column(name = "PHONE_NUMBER_COM_USE")
    private Integer phoneNumberComUse;
    /**
     * 更新可能：（するしない区分）
     */
    @Basic(optional = true)
    @Column(name = "PHONE_NUMBER_COM_UP_ABLE")
    private Integer phoneNumberComUpdatable;
    /**
     * 連絡先利用設定
     */
    @Basic(optional = true)
    @Column(name = "PHONE_NUMBER_PS_USE")
    private Integer phoneNumberPsUse;
    /**
     * 更新可能：（するしない区分）
     */
    @Basic(optional = true)
    @Column(name = "PHONE_NUMBER_PS_UP_ABLE")
    private Integer phoneNumberPsUpdatable;
    /**
     * 連絡先利用設定
     */
    @Basic(optional = true)
    @Column(name = "URGENT_PHONE_NUMBER1_USE")
    private Integer urgentPhoneNumber1Use;
    /**
     * 更新可能：（するしない区分）
     */
    @Basic(optional = true)
    @Column(name = "URGENT_PHONE_NUMBER1_UP_ABLE")
    private Integer urgentPhoneNumber1Updatable;
    /**
     * 連絡先利用設定
     */
    @Basic(optional = true)
    @Column(name = "URGENT_PHONE_NUMBER2_USE")
    private Integer urgentPhoneNumber2Use;
    /**
     * 更新可能：（するしない区分）
     */
    @Basic(optional = true)
    @Column(name = "URGENT_PHONE_NUMBER2_UP_ABLE")
    private Integer urgentPhoneNumber2Updatable;
    /**
     * 連絡先利用設定
     */
    @Basic(optional = true)
    @Column(name = "DIAL_IN_NUMBER_USE")
    private Integer dialInNumberUse;
    /**
     * 更新可能：（するしない区分）
     */
    @Basic(optional = true)
    @Column(name = "DIAL_IN_NUMBER_UP_ABLE")
    private Integer dialInNumberUpdatable;
    /**
     * 連絡先利用設定
     */
    @Basic(optional = true)
    @Column(name = "EXTENSION_NUMBER_USE")
    private Integer extensionNumberUse;
    /**
     * 更新可能：（するしない区分）
     */
    @Basic(optional = true)
    @Column(name = "EXTENSION_NUMBER_UP_ABLE")
    private Integer extensionNumberUpdatable;
    /**
     * 会社メールアドレス
     */
    @Basic(optional = true)
    @Column(name = "MAIL_COM_USE")
    private Integer mailComUse;
    /**
     * 更新可能：（するしない区分）
     */
    @Basic(optional = true)
    @Column(name = "MAIL_COM_UP_ABLE")
    private Integer mailComUpdatable;
    /**
     * 連絡先利用設定
     */
    @Basic(optional = true)
    @Column(name = "MAIL_PS_USE")
    private Integer mailPsUse;
    /**
     * 更新可能：（するしない区分）
     */
    @Basic(optional = true)
    @Column(name = "MAIL_PS_UP_ABLE")
    private Integer mailPsUpdatable;
    /**
     * 連絡先利用設定
     */
    @Basic(optional = true)
    @Column(name = "PHONE_MAIL_COM_USE")
    private Integer phoneMailComUse;
    /**
     * 更新可能：（するしない区分）
     */
    @Basic(optional = true)
    @Column(name = "PHONE_MAIL_COM_UP_ABLE")
    private Integer phoneMailComUpdatable;
    /**
     * 連絡先利用設定
     */
    @Basic(optional = true)
    @Column(name = "PHONE_MAIL_PS_USE")
    private Integer phoneMailPsUse;
    /**
     * 更新可能：（するしない区分）
     */
    @Basic(optional = true)
    @Column(name = "PHONE_MAIL_PS_UP_ABLE")
    private Integer phoneMailPsUpdatable;
    /**
     * 他の連絡先名1
     */
    @Basic(optional = true)
    @Column(name = "OTHER＿CONTACT1_NAME")
    private String otherContact1Name;
    /**
     * 他の連絡先1利用設定
     */
    @Basic(optional = true)
    @Column(name = "OTHER＿CONTACT1_USE")
    private Integer otherContact1Use;
    /**
     * 他の連絡先名2
     */
    @Basic(optional = true)
    @Column(name = "OTHER＿CONTACT2_NAME")
    private String otherContact2Name;
    /**
     * 他の連絡先2利用設定
     */
    @Basic(optional = true)
    @Column(name = "OTHER＿CONTACT2_USE")
    private Integer otherContact2Use;
    /**
     * 他の連絡先名3
     */
    @Basic(optional = true)
    @Column(name = "OTHER＿CONTACT3_NAME")
    private String otherContact3Name;
    /**
     * 他の連絡先3利用設定
     */
    @Basic(optional = true)
    @Column(name = "OTHER＿CONTACT3_USE")
    private Integer otherContact3Use;
    /**
     * 他の連絡先名4
     */
    @Basic(optional = true)
    @Column(name = "OTHER＿CONTACT4_NAME")
    private String otherContact4Name;
    /**
     * 他の連絡先4利用設定
     */
    @Basic(optional = true)
    @Column(name = "OTHER＿CONTACT4_USE")
    private Integer otherContact4Use;
    /**
     * 他の連絡先名5
     */
    @Basic(optional = true)
    @Column(name = "OTHER＿CONTACT5_NAME")
    private String otherContact5Name;
    /**
     * 他の連絡先5利用設定
     */
    @Basic(optional = true)
    @Column(name = "OTHER＿CONTACT5_USE")
    private Integer otherContact5Use;
    /**
     * 記念日の表示
     */
    @Basic(optional = true)
    @Column(name = "ANNIVERSARY_USE")
    private Integer anniversaryUse;
    /**
     * カレンダーの予約の表示
     */
    @Basic(optional = true)
    @Column(name = "CALENDAR_RESERVATION_USE")
    private Integer calendarReservationUse;

    @Override
    protected Object getKey() {
        return this.cId;
    }

    @Override
    public String getCompanyId() {
        return this.cId;
    }

    @Override
    public void setCompanyId(String companyId) {
        this.setCId(companyId);
    }

    @Override
    public List<EmailDestinationFunction> getEmailDestinationFunctions() {
        List<EmailDestinationFunction> emailDestinationFunctions = new ArrayList<>();
        for (int i = 0; i < 4; i++) {
            emailDestinationFunctions.add(filterListMailDestination(i));
        }
        return emailDestinationFunctions;
    }

    @Override
    public void setEmailDestinationFunctions(List<EmailDestinationFunction> emailDestinationFunctions) {
        List<SevmtMailDestination> list = new ArrayList<>();
        emailDestinationFunctions.forEach(item -> list.addAll(SevmtMailDestination.toListEntity(item, this.cId)));
        this.sevmtMailDestinations = list;
    }

    private EmailDestinationFunction filterListMailDestination(int mailClassification) {
        List<FunctionId> list = this.sevmtMailDestinations.stream()
                .filter(item -> item.getPk().getMailClassification() == mailClassification)
                .map(item -> new FunctionId(item.getPk().getFuncId()))
                .collect(Collectors.toList());
        return EmailDestinationFunction.builder()
                .emailClassification(EnumAdaptor.valueOf(mailClassification, EmailClassification.class))
                .functionIds(list)
                .build();
    }

    @Override
    public SettingContactInformation getSettingContactInformation() {
        return SettingContactInformation.createFromMemento(this);
    }

    @Override
    public void setSettingContactInformation(SettingContactInformation settingContactInformation) {
        ContactSetting dialInNumber = settingContactInformation.getDialInNumber();
        ContactSetting companyEmailAddress = settingContactInformation.getCompanyEmailAddress();
        ContactSetting companyMobileEmailAddress = settingContactInformation.getCompanyMobileEmailAddress();
        ContactSetting personalEmailAddress = settingContactInformation.getPersonalEmailAddress();
        ContactSetting personalMobileEmailAddress = settingContactInformation.getPersonalMobileEmailAddress();
        ContactSetting extensionNumber = settingContactInformation.getExtensionNumber();
        ContactSetting companyMobilePhone = settingContactInformation.getCompanyMobilePhone();
        ContactSetting emergencyNumber1 = settingContactInformation.getEmergencyNumber1();
        ContactSetting emergencyNumber2 = settingContactInformation.getEmergencyNumber2();
        this.dialInNumberUse = dialInNumber.getContactUsageSetting().code;
        this.dialInNumberUpdatable = dialInNumber.getUpdatable().isPresent() ? dialInNumber.getUpdatable().get().value : null;
        this.mailComUse = companyEmailAddress.getContactUsageSetting().code;
        this.mailComUpdatable = companyEmailAddress.getUpdatable().isPresent() ? companyEmailAddress.getUpdatable().get().value : null;
        this.phoneMailComUse = companyMobileEmailAddress.getContactUsageSetting().code;
        this.phoneMailComUpdatable = companyMobileEmailAddress.getUpdatable().isPresent() ? companyMobileEmailAddress.getUpdatable().get().value : null;
        this.mailPsUse = personalEmailAddress.getContactUsageSetting().code;
        this.mailComUpdatable = personalEmailAddress.getUpdatable().isPresent() ? personalEmailAddress.getUpdatable().get().value : null;
        this.phoneMailPsUse = personalMobileEmailAddress.getContactUsageSetting().code;
        this.phoneMailPsUpdatable = personalMobileEmailAddress.getUpdatable().isPresent() ? personalMobileEmailAddress.getUpdatable().get().value : null;
        this.extensionNumberUse = extensionNumber.getContactUsageSetting().code;
        this.extensionNumberUpdatable = extensionNumber.getUpdatable().isPresent() ? extensionNumber.getUpdatable().get().value : null;
        this.phoneNumberComUse = companyMobilePhone.getContactUsageSetting().code;
        this.phoneNumberComUpdatable = companyMobilePhone.getUpdatable().isPresent() ? companyMobilePhone.getUpdatable().get().value : null;
        this.urgentPhoneNumber1Use = emergencyNumber1.getContactUsageSetting().code;
        this.urgentPhoneNumber1Updatable = emergencyNumber1.getUpdatable().isPresent() ? emergencyNumber1.getUpdatable().get().value : null;
        this.urgentPhoneNumber2Use = emergencyNumber2.getContactUsageSetting().code;
        this.urgentPhoneNumber1Updatable = emergencyNumber2.getUpdatable().isPresent() ? emergencyNumber2.getUpdatable().get().value : null;
    }

    @Override
    public ContactSetting getDialInNumber() {
        return ContactSetting.builder()
                .contactUsageSetting(EnumAdaptor.valueOf(this.dialInNumberUse, ContactUsageSetting.class))
                .updatable(Optional.of(EnumAdaptor.valueOf(this.dialInNumberUpdatable, NotUseAtr.class)))
                .build();
    }

    @Override
    public void setDialInNumber(ContactSetting dialInNumber) {
        this.dialInNumberUse = dialInNumber.getContactUsageSetting().code;
        this.dialInNumberUpdatable = dialInNumber.getUpdatable().isPresent() ? dialInNumber.getUpdatable().get().value : null;
    }

    @Override
    public ContactSetting getCompanyEmailAddress() {
        return ContactSetting.builder()
                .contactUsageSetting(EnumAdaptor.valueOf(this.mailComUse, ContactUsageSetting.class))
                .updatable(Optional.of(EnumAdaptor.valueOf(this.mailComUpdatable, NotUseAtr.class)))
                .build();
    }

    @Override
    public void setCompanyEmailAddress(ContactSetting companyEmailAddress) {
        this.mailComUse = companyEmailAddress.getContactUsageSetting().code;
        this.mailComUpdatable = companyEmailAddress.getUpdatable().isPresent() ? companyEmailAddress.getUpdatable().get().value : null;
    }

    @Override
    public ContactSetting getCompanyMobileEmailAddress() {
        return ContactSetting.builder()
                .contactUsageSetting(EnumAdaptor.valueOf(this.phoneMailComUse, ContactUsageSetting.class))
                .updatable(Optional.of(EnumAdaptor.valueOf(this.phoneMailComUpdatable, NotUseAtr.class)))
                .build();
    }

    @Override
    public void setCompanyMobileEmailAddress(ContactSetting companyMobileEmailAddress) {
        this.phoneMailComUse = companyMobileEmailAddress.getContactUsageSetting().code;
        this.phoneMailComUpdatable = companyMobileEmailAddress.getUpdatable().isPresent() ? companyMobileEmailAddress.getUpdatable().get().value : null;
    }

    @Override
    public ContactSetting getPersonalEmailAddress() {
        return ContactSetting.builder()
                .contactUsageSetting(EnumAdaptor.valueOf(this.mailPsUse, ContactUsageSetting.class))
                .updatable(Optional.of(EnumAdaptor.valueOf(this.mailComUpdatable, NotUseAtr.class)))
                .build();
    }

    @Override
    public void setPersonalEmailAddress(ContactSetting personalEmailAddress) {
        this.mailPsUse = personalEmailAddress.getContactUsageSetting().code;
        this.mailComUpdatable = personalEmailAddress.getUpdatable().isPresent() ? personalEmailAddress.getUpdatable().get().value : null;
    }

    @Override
    public ContactSetting getPersonalMobileEmailAddress() {
        return ContactSetting.builder()
                .contactUsageSetting(EnumAdaptor.valueOf(this.phoneMailPsUse, ContactUsageSetting.class))
                .updatable(Optional.of(EnumAdaptor.valueOf(this.phoneMailPsUpdatable, NotUseAtr.class)))
                .build();
    }

    @Override
    public void setPersonalMobileEmailAddress(ContactSetting personalMobileEmailAddress) {
        this.phoneMailPsUse = personalMobileEmailAddress.getContactUsageSetting().code;
        this.phoneMailPsUpdatable = personalMobileEmailAddress.getUpdatable().isPresent() ? personalMobileEmailAddress.getUpdatable().get().value : null;
    }

    @Override
    public ContactSetting getExtensionNumber() {
        return ContactSetting.builder()
                .contactUsageSetting(EnumAdaptor.valueOf(this.extensionNumberUse, ContactUsageSetting.class))
                .updatable(Optional.of(EnumAdaptor.valueOf(this.extensionNumberUpdatable, NotUseAtr.class)))
                .build();
    }

    @Override
    public void setExtensionNumber(ContactSetting extensionNumber) {
        this.extensionNumberUse = extensionNumber.getContactUsageSetting().code;
        this.extensionNumberUpdatable = extensionNumber.getUpdatable().isPresent() ? extensionNumber.getUpdatable().get().value : null;
    }

    @Override
    public ContactSetting getCompanyMobilePhone() {
        return ContactSetting.builder()
                .contactUsageSetting(EnumAdaptor.valueOf(this.phoneNumberComUse, ContactUsageSetting.class))
                .updatable(Optional.of(EnumAdaptor.valueOf(this.phoneNumberComUpdatable, NotUseAtr.class)))
                .build();
    }

    @Override
    public void setCompanyMobilePhone(ContactSetting companyMobilePhone) {
        this.phoneNumberComUse = companyMobilePhone.getContactUsageSetting().code;
        this.phoneNumberComUpdatable = companyMobilePhone.getUpdatable().isPresent() ? companyMobilePhone.getUpdatable().get().value : null;
    }

    @Override
    public ContactSetting getPersonalMobilePhone() {
        return ContactSetting.builder()
                .contactUsageSetting(EnumAdaptor.valueOf(this.phoneNumberPsUse, ContactUsageSetting.class))
                .updatable(Optional.of(EnumAdaptor.valueOf(this.phoneNumberComUpdatable, NotUseAtr.class)))
                .build();
    }

    @Override
    public void setPersonalMobilePhone(ContactSetting personalMobilePhone) {
        this.phoneNumberPsUse = personalMobilePhone.getContactUsageSetting().code;
        this.phoneNumberComUpdatable = personalMobilePhone.getUpdatable().isPresent() ? personalMobilePhone.getUpdatable().get().value : null;
    }

    @Override
    public ContactSetting getEmergencyNumber1() {
        return ContactSetting.builder()
                .contactUsageSetting(EnumAdaptor.valueOf(this.urgentPhoneNumber1Use, ContactUsageSetting.class))
                .updatable(Optional.of(EnumAdaptor.valueOf(this.urgentPhoneNumber1Updatable, NotUseAtr.class)))
                .build();
    }

    @Override
    public void setEmergencyNumber1(ContactSetting emergencyNumber1) {
        this.urgentPhoneNumber1Use = emergencyNumber1.getContactUsageSetting().code;
        this.urgentPhoneNumber1Updatable = emergencyNumber1.getUpdatable().isPresent() ? emergencyNumber1.getUpdatable().get().value : null;
    }

    @Override
    public ContactSetting getEmergencyNumber2() {
        return ContactSetting.builder()
                .contactUsageSetting(EnumAdaptor.valueOf(this.urgentPhoneNumber2Use, ContactUsageSetting.class))
                .updatable(Optional.of(EnumAdaptor.valueOf(this.urgentPhoneNumber2Updatable, NotUseAtr.class)))
                .build();
    }

    @Override
    public void setEmergencyNumber2(ContactSetting emergencyNumber2) {
        this.urgentPhoneNumber2Use = emergencyNumber2.getContactUsageSetting().code;
        this.urgentPhoneNumber1Updatable = emergencyNumber2.getUpdatable().isPresent() ? emergencyNumber2.getUpdatable().get().value : null;
    }

    @Override
    public List<OtherContact> getOtherContacts() {
        List<OtherContact> otherContacts = new ArrayList<>();
        otherContacts.add(
                OtherContact.builder()
                        .contactName(new ContactName(this.otherContact1Name))
                        .contactUsageSetting(EnumAdaptor.valueOf(this.otherContact1Use, ContactUsageSetting.class))
                        .build()
        );
        otherContacts.add(
                OtherContact.builder()
                        .contactName(new ContactName(this.otherContact2Name))
                        .contactUsageSetting(EnumAdaptor.valueOf(this.otherContact2Use, ContactUsageSetting.class))
                        .build()
        );
        otherContacts.add(
                OtherContact.builder()
                        .contactName(new ContactName(this.otherContact3Name))
                        .contactUsageSetting(EnumAdaptor.valueOf(this.otherContact3Use, ContactUsageSetting.class))
                        .build()
        );
        otherContacts.add(
                OtherContact.builder()
                        .contactName(new ContactName(this.otherContact4Name))
                        .contactUsageSetting(EnumAdaptor.valueOf(this.otherContact4Use, ContactUsageSetting.class))
                        .build()
        );
        otherContacts.add(
                OtherContact.builder()
                        .contactName(new ContactName(this.otherContact5Name))
                        .contactUsageSetting(EnumAdaptor.valueOf(this.otherContact5Use, ContactUsageSetting.class))
                        .build()
        );
        return otherContacts;
    }


    @Override
    public void setOtherContacts(List<OtherContact> otherContacts) {
        for (OtherContact otherContact : otherContacts) {
            setOtherContactByNo(otherContact);
        }
    }

    private boolean setOtherContactByNo(OtherContact otherContact) {
        switch (otherContact.getNo()) {
            case 1:
                this.otherContact1Name = otherContact.getContactName().v();
                this.otherContact1Use = otherContact.getContactUsageSetting().code;
                return true;
            case 2:
                this.otherContact2Name = otherContact.getContactName().v();
                this.otherContact2Use = otherContact.getContactUsageSetting().code;
                return true;
            case 3:
                this.otherContact3Name = otherContact.getContactName().v();
                this.otherContact3Use = otherContact.getContactUsageSetting().code;
                return true;
            case 4:
                this.otherContact4Name = otherContact.getContactName().v();
                this.otherContact4Use = otherContact.getContactUsageSetting().code;
                return true;
            case 5:
                this.otherContact5Name = otherContact.getContactName().v();
                this.otherContact5Use = otherContact.getContactUsageSetting().code;
                return true;
            default:
                return false;
        }
    }
}