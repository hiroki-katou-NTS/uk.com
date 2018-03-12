package nts.uk.ctx.bs.person.dom.person.contact;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
/**
 * 緊急連絡先
 * The class EmergencyContact 
 * @author lanlt
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmergencyContact extends AggregateRoot{
	
	private Memo memo;
	
	private ContactName contactName;
	
	private PhoneNumber phoneNumber;
	
	public static EmergencyContact createFromJavaType(String memo, String contactName, String phoneNumber) {
		return new EmergencyContact(new Memo(memo), new ContactName(contactName), new PhoneNumber(phoneNumber));
	}
}
