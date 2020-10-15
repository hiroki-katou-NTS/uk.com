package nts.uk.query.app.user.information.setting;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.ContactSetting;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.OtherContact;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.SettingContactInformation;

import java.util.List;
import java.util.stream.Collectors;

/**
 * Dto 連絡先情報の設定
 */
@Data
@Builder
public class SettingContactInformationDto implements SettingContactInformation.MementoSetter {

    /**
     * ダイヤルイン番号
     */
    private ContactSettingDto dialInNumber;

    /**
     * 会社メールアドレス
     */
    private ContactSettingDto companyEmailAddress;

    /**
     * 会社携帯メールアドレス
     */
    private ContactSettingDto companyMobileEmailAddress;

    /**
     * 個人メールアドレス
     */
    private ContactSettingDto personalEmailAddress;

    /**
     * 個人携帯メールアドレス
     */
    private ContactSettingDto personalMobileEmailAddress;

    /**
     * 内線番号
     */
    private ContactSettingDto extensionNumber;

    /**
     * 携帯電話（会社用）
     */
    private ContactSettingDto companyMobilePhone;

    /**
     * 携帯電話（個人用）
     */
    private ContactSettingDto personalMobilePhone;

    /**
     * 緊急電話番号1
     */
    private ContactSettingDto emergencyNumber1;

    /**
     * 緊急電話番号2
     */
    private ContactSettingDto emergencyNumber2;

    /**
     * 他の連絡先
     */
    private List<OtherContactDto> otherContacts;

    @Override
    public void setDialInNumber(ContactSetting dialInNumber) {
        this.dialInNumber = ContactSettingDto.builder()
                .contactUsageSetting(dialInNumber.getContactUsageSetting().code)
                .updatable(dialInNumber.getUpdatable().isPresent() ? dialInNumber.getUpdatable().get().value : null)
                .build();
    }

    @Override
    public void setCompanyEmailAddress(ContactSetting companyEmailAddress) {
        this.companyEmailAddress = ContactSettingDto.builder()
                .contactUsageSetting(companyEmailAddress.getContactUsageSetting().code)
                .updatable(companyEmailAddress.getUpdatable().isPresent() ? companyEmailAddress.getUpdatable().get().value : null)
                .build();
    }

    @Override
    public void setCompanyMobileEmailAddress(ContactSetting companyMobileEmailAddress) {
        this.companyMobileEmailAddress = ContactSettingDto.builder()
                .contactUsageSetting(companyMobileEmailAddress.getContactUsageSetting().code)
                .updatable(companyMobileEmailAddress.getUpdatable().isPresent() ? companyMobileEmailAddress.getUpdatable().get().value : null)
                .build();
    }

    @Override
    public void setPersonalEmailAddress(ContactSetting personalEmailAddress) {
        this.personalEmailAddress = ContactSettingDto.builder()
                .contactUsageSetting(personalEmailAddress.getContactUsageSetting().code)
                .updatable(personalEmailAddress.getUpdatable().isPresent() ? personalEmailAddress.getUpdatable().get().value : null)
                .build();
    }

    @Override
    public void setPersonalMobileEmailAddress(ContactSetting personalMobileEmailAddress) {
        this.personalMobileEmailAddress = ContactSettingDto.builder()
                .contactUsageSetting(personalMobileEmailAddress.getContactUsageSetting().code)
                .updatable(personalMobileEmailAddress.getUpdatable().isPresent() ? personalMobileEmailAddress.getUpdatable().get().value : null)
                .build();
    }

    @Override
    public void setExtensionNumber(ContactSetting extensionNumber) {
        this.extensionNumber = ContactSettingDto.builder()
                .contactUsageSetting(extensionNumber.getContactUsageSetting().code)
                .updatable(extensionNumber.getUpdatable().isPresent() ? extensionNumber.getUpdatable().get().value : null)
                .build();
    }

    @Override
    public void setCompanyMobilePhone(ContactSetting companyMobilePhone) {
        this.companyMobilePhone = ContactSettingDto.builder()
                .contactUsageSetting(companyMobilePhone.getContactUsageSetting().code)
                .updatable(companyMobilePhone.getUpdatable().isPresent() ? companyMobilePhone.getUpdatable().get().value : null)
                .build();
    }

    @Override
    public void setPersonalMobilePhone(ContactSetting personalMobilePhone) {
        this.personalMobilePhone = ContactSettingDto.builder()
                .contactUsageSetting(personalMobilePhone.getContactUsageSetting().code)
                .updatable(personalMobilePhone.getUpdatable().isPresent() ? personalMobilePhone.getUpdatable().get().value : null)
                .build();
    }

    @Override
    public void setEmergencyNumber1(ContactSetting emergencyNumber1) {
        this.emergencyNumber1 = ContactSettingDto.builder()
                .contactUsageSetting(emergencyNumber1.getContactUsageSetting().code)
                .updatable(emergencyNumber1.getUpdatable().isPresent() ? emergencyNumber1.getUpdatable().get().value : null)
                .build();
    }

    @Override
    public void setEmergencyNumber2(ContactSetting emergencyNumber2) {
        this.emergencyNumber2 = ContactSettingDto.builder()
                .contactUsageSetting(emergencyNumber2.getContactUsageSetting().code)
                .updatable(emergencyNumber2.getUpdatable().isPresent() ? emergencyNumber2.getUpdatable().get().value : null)
                .build();
    }

    @Override
    public void setOtherContacts(List<OtherContact> otherContacts) {
        this.otherContacts = otherContacts.stream()
                .map(item -> OtherContactDto.builder()
                        .no(item.getNo())
                        .contactUsageSetting(item.getContactUsageSetting().code)
                        .contactName(item.getContactName().v())
                        .build())
                .collect(Collectors.toList());
    }
}
