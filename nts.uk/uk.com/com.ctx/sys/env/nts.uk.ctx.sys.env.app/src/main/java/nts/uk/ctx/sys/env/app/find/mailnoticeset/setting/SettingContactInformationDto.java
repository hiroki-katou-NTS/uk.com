package nts.uk.ctx.sys.env.app.find.mailnoticeset.setting;

import lombok.Builder;
import lombok.Data;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.ContactName;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.ContactSetting;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.ContactUsageSetting;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.OtherContact;
import nts.uk.ctx.sys.env.dom.mailnoticeset.company.SettingContactInformation;
import nts.uk.shr.com.enumcommon.NotUseAtr;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

/**
 * Dto 連絡先情報の設定
 */
@Data
@Builder
public class SettingContactInformationDto implements SettingContactInformation.MementoSetter, SettingContactInformation.MementoGetter {

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
		this.dialInNumber = ContactSettingDto.builder().contactUsageSetting(dialInNumber.getContactUsageSetting().value)
				.updatable(dialInNumber.getUpdatable().isPresent() ? dialInNumber.getUpdatable().get().value : null)
				.build();
	}

	@Override
	public void setCompanyEmailAddress(ContactSetting companyEmailAddress) {
		this.companyEmailAddress = ContactSettingDto.builder()
				.contactUsageSetting(companyEmailAddress.getContactUsageSetting().value)
				.updatable(
						companyEmailAddress.getUpdatable().isPresent() ? companyEmailAddress.getUpdatable().get().value
								: null)
				.build();
	}

	@Override
	public void setCompanyMobileEmailAddress(ContactSetting companyMobileEmailAddress) {
		this.companyMobileEmailAddress = ContactSettingDto.builder()
				.contactUsageSetting(companyMobileEmailAddress.getContactUsageSetting().value)
				.updatable(companyMobileEmailAddress.getUpdatable().isPresent()
						? companyMobileEmailAddress.getUpdatable().get().value
						: null)
				.build();
	}

	@Override
	public void setPersonalEmailAddress(ContactSetting personalEmailAddress) {
		this.personalEmailAddress = ContactSettingDto.builder()
				.contactUsageSetting(personalEmailAddress.getContactUsageSetting().value)
				.updatable(personalEmailAddress.getUpdatable().isPresent()
						? personalEmailAddress.getUpdatable().get().value
						: null)
				.build();
	}

	@Override
	public void setPersonalMobileEmailAddress(ContactSetting personalMobileEmailAddress) {
		this.personalMobileEmailAddress = ContactSettingDto.builder()
				.contactUsageSetting(personalMobileEmailAddress.getContactUsageSetting().value)
				.updatable(personalMobileEmailAddress.getUpdatable().isPresent()
						? personalMobileEmailAddress.getUpdatable().get().value
						: null)
				.build();
	}

	@Override
	public void setExtensionNumber(ContactSetting extensionNumber) {
		this.extensionNumber = ContactSettingDto.builder()
				.contactUsageSetting(extensionNumber.getContactUsageSetting().value)
				.updatable(
						extensionNumber.getUpdatable().isPresent() ? extensionNumber.getUpdatable().get().value : null)
				.build();
	}

	@Override
	public void setCompanyMobilePhone(ContactSetting companyMobilePhone) {
		this.companyMobilePhone = ContactSettingDto.builder()
				.contactUsageSetting(companyMobilePhone.getContactUsageSetting().value)
				.updatable(companyMobilePhone.getUpdatable().isPresent() ? companyMobilePhone.getUpdatable().get().value
						: null)
				.build();
	}

	@Override
	public void setPersonalMobilePhone(ContactSetting personalMobilePhone) {
		this.personalMobilePhone = ContactSettingDto.builder()
				.contactUsageSetting(personalMobilePhone.getContactUsageSetting().value)
				.updatable(
						personalMobilePhone.getUpdatable().isPresent() ? personalMobilePhone.getUpdatable().get().value
								: null)
				.build();
	}

	@Override
	public void setEmergencyNumber1(ContactSetting emergencyNumber1) {
		this.emergencyNumber1 = ContactSettingDto.builder()
				.contactUsageSetting(emergencyNumber1.getContactUsageSetting().value)
				.updatable(emergencyNumber1.getUpdatable().isPresent() ? emergencyNumber1.getUpdatable().get().value
						: null)
				.build();
	}

	@Override
	public void setEmergencyNumber2(ContactSetting emergencyNumber2) {
		this.emergencyNumber2 = ContactSettingDto.builder()
				.contactUsageSetting(emergencyNumber2.getContactUsageSetting().value)
				.updatable(emergencyNumber2.getUpdatable().isPresent() ? emergencyNumber2.getUpdatable().get().value
						: null)
				.build();
	}

	@Override
	public void setOtherContacts(List<OtherContact> otherContacts) {
		this.otherContacts = otherContacts.stream()
				.map(item -> OtherContactDto.builder().no(item.getNo())
						.contactUsageSetting(item.getContactUsageSetting().value).contactName(item.getContactName().v())
						.build())
				.collect(Collectors.toList());
	}

	@Override
	public ContactSetting getCompanyMobileEmailAddress() {
		return ContactSetting.builder()
				.contactUsageSetting(
						ContactUsageSetting.valueOf(this.companyMobileEmailAddress.getContactUsageSetting()))
				.updatable(Optional.of(NotUseAtr.valueOf(this.companyMobileEmailAddress.getUpdatable()))).build();
	}

	@Override
	public ContactSetting getPersonalMobilePhone() {
		return ContactSetting.builder()
				.contactUsageSetting(ContactUsageSetting.valueOf(this.personalMobilePhone.getContactUsageSetting()))
				.updatable(Optional.of(NotUseAtr.valueOf(this.personalMobilePhone.getUpdatable()))).build();
	}

	@Override
	public ContactSetting getDialInNumber() {
		return ContactSetting.builder()
				.contactUsageSetting(ContactUsageSetting.valueOf(this.dialInNumber.getContactUsageSetting()))
				.updatable(Optional.of(NotUseAtr.valueOf(this.dialInNumber.getUpdatable()))).build();
	}

	@Override
	public ContactSetting getExtensionNumber() {
		return ContactSetting.builder()
				.contactUsageSetting(ContactUsageSetting.valueOf(this.extensionNumber.getContactUsageSetting()))
				.updatable(Optional.of(NotUseAtr.valueOf(this.extensionNumber.getUpdatable()))).build();
	}

	@Override
	public ContactSetting getCompanyMobilePhone() {
		return ContactSetting.builder()
				.contactUsageSetting(ContactUsageSetting.valueOf(this.companyMobilePhone.getContactUsageSetting()))
				.updatable(Optional.of(NotUseAtr.valueOf(this.companyMobilePhone.getUpdatable()))).build();
	}

	@Override
	public ContactSetting getCompanyEmailAddress() {
		return ContactSetting.builder()
				.contactUsageSetting(ContactUsageSetting.valueOf(this.companyEmailAddress.getContactUsageSetting()))
				.updatable(Optional.of(NotUseAtr.valueOf(this.companyEmailAddress.getUpdatable()))).build();
	}

	@Override
	public ContactSetting getEmergencyNumber1() {
		return ContactSetting.builder()
				.contactUsageSetting(ContactUsageSetting.valueOf(this.emergencyNumber1.getContactUsageSetting()))
				.updatable(Optional.of(NotUseAtr.valueOf(this.emergencyNumber1.getUpdatable()))).build();
	}

	@Override
	public ContactSetting getEmergencyNumber2() {
		return ContactSetting.builder()
				.contactUsageSetting(ContactUsageSetting.valueOf(this.emergencyNumber2.getContactUsageSetting()))
				.updatable(Optional.of(NotUseAtr.valueOf(this.emergencyNumber2.getUpdatable()))).build();
	}

	@Override
	public List<OtherContact> getOtherContacts() {
		return this.otherContacts.stream()
				.map(item -> OtherContact.builder().no(item.getNo())
						.contactUsageSetting(ContactUsageSetting.valueOf(item.getContactUsageSetting()))
						.contactName(new ContactName(item.getContactName())).build())
				.collect(Collectors.toList());
	}

	@Override
	public ContactSetting getPersonalEmailAddress() {
		return ContactSetting.builder()
				.contactUsageSetting(ContactUsageSetting.valueOf(this.personalEmailAddress.getContactUsageSetting()))
				.updatable(Optional.of(NotUseAtr.valueOf(this.personalEmailAddress.getUpdatable()))).build();
	}

	@Override
	public ContactSetting getPersonalMobileEmailAddress() {
		return ContactSetting.builder()
				.contactUsageSetting(
						ContactUsageSetting.valueOf(this.personalMobileEmailAddress.getContactUsageSetting()))
				.updatable(Optional.of(NotUseAtr.valueOf(this.personalMobileEmailAddress.getUpdatable()))).build();
	}

}
