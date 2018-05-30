package nts.uk.ctx.bs.person.dom.person.contact;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.bs.person.dom.person.info.PersonMobile;

/**
 * The class PersonContact 個人連絡先
 * 
 * @author lanlt
 *
 */

@AllArgsConstructor
@Getter
public class PersonContact extends AggregateRoot {

	// 個人ID
	private String personId;

	// 携帯電話番号
	private Optional<PersonMobile> cellPhoneNumber;

	// メールアドレス
	private Optional<MailAddress> mailAdress;

	// 携帯メールアドレス
	private Optional<MailAddress> mobileMailAdress;

	// 緊急連絡先１
	private Optional<EmergencyContact> emergencyContact1;

	// 緊急連絡先2
	private Optional<EmergencyContact> emergencyContact2;

	public PersonContact(String personId, String cellPhoneNumber, String mailAddress, String mobileMailAdress,
			String memo1, String contactName1, String phoneNumber1, String memo2, String contactName2,
			String phoneNumber2) {
		this.personId = personId;

		if (cellPhoneNumber == null || cellPhoneNumber.isEmpty()) {
			this.cellPhoneNumber = Optional.empty();
		} else {
			this.cellPhoneNumber = Optional.of(new PersonMobile(cellPhoneNumber));
		}

		if (mailAddress == null || mailAddress.isEmpty()) {
			this.mailAdress = Optional.empty();
		} else {
			this.mailAdress = Optional.of(new MailAddress(mailAddress));
		}

		if (mobileMailAdress == null || mobileMailAdress.isEmpty()) {
			this.mobileMailAdress = Optional.empty();
		} else {
			this.mobileMailAdress = Optional.of(new MailAddress(mobileMailAdress));
		}

		this.emergencyContact1 = EmergencyContact.createFromJavaType(memo1, contactName1, phoneNumber1);
		this.emergencyContact2 = EmergencyContact.createFromJavaType(memo2, contactName2, phoneNumber2);

	}

	public static PersonContact createFromJavaType(String personId, String cellPhoneNumber, String mailAddress,
			String mobileMailAdress) {
		return new PersonContact(personId, cellPhoneNumber, mailAddress, mobileMailAdress, null, null, null, null, null,
				null);
	}

}
