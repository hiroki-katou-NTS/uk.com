package nts.uk.ctx.bs.person.dom.person.contact;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.bs.person.dom.person.info.PersonMobile;
/**
 * The class PersonContact 個人連絡先
 * @author lanlt
 *
 */

@AllArgsConstructor
@Getter
public class PersonContact extends AggregateRoot{
	
	//個人ID
	private String personId;
	
	//携帯電話番号
	private PersonMobile cellPhoneNumber;
	
	//メールアドレス
	private MailAddress mailAdress;
	
	//携帯メールアドレス
	private MailAddress mobileMailAdress;
	
	//緊急連絡先１
	private EmergencyContact emergencyContact1;
	
	//緊急連絡先2
	private EmergencyContact emergencyContact2;
	
	public PersonContact(String personId, String phoneNumber, String mailAddress, String mobileMailAdd,
			String memo1, String contactName1, String phoneNumber1, String memo2, String contactName2, String phoneNumber2){
		this.personId = personId;
		this.cellPhoneNumber = new PersonMobile(phoneNumber);
		this.mailAdress = new MailAddress(mailAddress);
		this.mobileMailAdress = new MailAddress(mobileMailAdd);
		this.emergencyContact1 = EmergencyContact.createFromJavaType(memo1, contactName1, phoneNumber1);
		this.emergencyContact2 = EmergencyContact.createFromJavaType(memo2, contactName2, phoneNumber2);
		
	}

}
