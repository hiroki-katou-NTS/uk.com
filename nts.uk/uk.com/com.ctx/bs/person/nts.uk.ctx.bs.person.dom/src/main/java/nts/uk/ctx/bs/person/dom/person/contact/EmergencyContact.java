package nts.uk.ctx.bs.person.dom.person.contact;

import java.util.Optional;

import org.apache.commons.lang3.StringUtils;

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

	private Optional<Memo> memo;

	private Optional<ContactName> contactName;

	private Optional<PhoneNumber> phoneNumber;

	public static Optional<EmergencyContact> createFromJavaType(String memo, String contactName, String phoneNumber) {
		if (StringUtils.isEmpty(memo) && StringUtils.isEmpty(contactName) && StringUtils.isEmpty(phoneNumber)) {
			return Optional.empty();
		}

		Optional<Memo> memoValue = StringUtils.isNotEmpty(memo) ? Optional.of(new Memo(memo)) : Optional.empty();

		boolean check = StringUtils.isNotEmpty(contactName);
		Optional<ContactName> contactNameValue = check ? Optional.of(new ContactName(contactName)) : Optional.empty();

		check = StringUtils.isNotEmpty(phoneNumber);
		Optional<PhoneNumber> phoneNumberValue = check ? Optional.of(new PhoneNumber(phoneNumber)) : Optional.empty();

		return Optional.of(new EmergencyContact(memoValue, contactNameValue, phoneNumberValue));
	}
}
