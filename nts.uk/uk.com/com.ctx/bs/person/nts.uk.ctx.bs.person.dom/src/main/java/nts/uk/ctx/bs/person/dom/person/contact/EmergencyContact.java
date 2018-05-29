package nts.uk.ctx.bs.person.dom.person.contact;

import java.util.Optional;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;

/**
 * 緊急連絡先 The class EmergencyContact
 * 
 * @author lanlt
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class EmergencyContact extends AggregateRoot {

	private Memo memo;

	private ContactName contactName;

	private PhoneNumber phoneNumber;

	public static Optional<EmergencyContact> createFromJavaType(String memo, String contactName, String phoneNumber) {
		if ((memo == null || memo.isEmpty()) 
				&& (contactName == null || contactName.isEmpty()) 
				&& phoneNumber == null || phoneNumber.isEmpty()) {
			return Optional.empty();
		}
		return Optional
				.of(new EmergencyContact(new Memo(memo), new ContactName(contactName), new PhoneNumber(phoneNumber)));
	}
}
