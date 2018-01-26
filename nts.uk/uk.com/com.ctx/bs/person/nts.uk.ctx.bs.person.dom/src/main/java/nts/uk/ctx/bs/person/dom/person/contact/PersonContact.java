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

}
