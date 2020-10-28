package nts.uk.ctx.sys.env.dom.mailnoticeset.company;

import lombok.Getter;
import lombok.Setter;

import java.util.List;

/**
 * UKDesign.ドメインモデル.NittsuSystem.UniversalK.システム.環境.メール通知設定.会社.会社.連絡先情報の設定
 */
@Getter
@Setter
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

	public static SettingContactInformation createFromMemento(MementoGetter memento) {
		SettingContactInformation domain = new SettingContactInformation();
		domain.getMemento(memento);
		return domain;
	}

	public void getMemento(MementoGetter memento) {
		this.dialInNumber = memento.getDialInNumber();
		this.companyEmailAddress = memento.getCompanyEmailAddress();
		this.companyMobileEmailAddress = memento.getCompanyMobileEmailAddress();
		this.personalEmailAddress = memento.getPersonalEmailAddress();
		this.personalMobileEmailAddress = memento.getPersonalMobileEmailAddress();
		this.extensionNumber = memento.getExtensionNumber();
		this.companyMobilePhone = memento.getCompanyMobilePhone();
		this.personalMobilePhone = memento.getPersonalMobilePhone();
		this.emergencyNumber1 = memento.getEmergencyNumber1();
		this.emergencyNumber2 = memento.getEmergencyNumber2();
		this.otherContacts = memento.getOtherContacts();

	}

	public void setMemento(MementoSetter memento) {
		memento.setDialInNumber(this.dialInNumber);
		memento.setCompanyEmailAddress(this.companyEmailAddress);
		memento.setCompanyMobileEmailAddress(this.companyMobileEmailAddress);
		memento.setPersonalEmailAddress(this.personalEmailAddress);
		memento.setPersonalMobileEmailAddress(this.personalMobileEmailAddress);
		memento.setExtensionNumber(this.extensionNumber);
		memento.setCompanyMobilePhone(this.companyMobilePhone);
		memento.setPersonalMobilePhone(this.personalMobilePhone);
		memento.setEmergencyNumber1(this.emergencyNumber1);
		memento.setEmergencyNumber2(this.emergencyNumber2);
		memento.setOtherContacts(this.otherContacts);
	}

	public interface MementoSetter {

		void setDialInNumber(ContactSetting dialInNumber);

		void setCompanyEmailAddress(ContactSetting companyEmailAddress);

		void setCompanyMobileEmailAddress(ContactSetting companyMobileEmailAddress);

		void setPersonalEmailAddress(ContactSetting personalEmailAddress);

		void setPersonalMobileEmailAddress(ContactSetting personalMobileEmailAddress);

		void setExtensionNumber(ContactSetting extensionNumber);

		void setCompanyMobilePhone(ContactSetting companyMobilePhone);

		void setPersonalMobilePhone(ContactSetting personalMobilePhone);

		void setEmergencyNumber1(ContactSetting emergencyNumber1);

		void setEmergencyNumber2(ContactSetting emergencyNumber2);

		void setOtherContacts(List<OtherContact> otherContacts);
	}

	public interface MementoGetter {
		ContactSetting getDialInNumber();

		ContactSetting getCompanyEmailAddress();

		ContactSetting getCompanyMobileEmailAddress();

		ContactSetting getPersonalEmailAddress();

		ContactSetting getPersonalMobileEmailAddress();

		ContactSetting getExtensionNumber();

		ContactSetting getCompanyMobilePhone();

		ContactSetting getPersonalMobilePhone();

		ContactSetting getEmergencyNumber1();

		ContactSetting getEmergencyNumber2();

		List<OtherContact> getOtherContacts();
	}
}
