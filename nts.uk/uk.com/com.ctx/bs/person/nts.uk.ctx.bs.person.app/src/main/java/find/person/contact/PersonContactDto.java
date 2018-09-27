package find.person.contact;

import java.util.Optional;

import lombok.Setter;
import nts.uk.ctx.bs.person.dom.person.contact.EmergencyContact;
import nts.uk.ctx.bs.person.dom.person.contact.PersonContact;
import nts.uk.shr.pereg.app.PeregItem;
import nts.uk.shr.pereg.app.find.dto.PeregDomainDto;

/**
 * 個人連絡先
 * 
 * @author xuan vinh
 *
 */

@Setter
public class PersonContactDto extends PeregDomainDto {

	/**
	 * 個人携帯電話番号
	 */
	@PeregItem("IS00262")
	private String cellPhoneNumber;

	/**
	 * 個人メールアドレス
	 */
	@PeregItem("IS00263")
	private String mailAdress;

	/**
	 * 個人携帯メールアドレス
	 */
	@PeregItem("IS00264")
	private String mobileMailAdress;

	/**
	 * 緊急連絡先名1
	 */
	@PeregItem("IS00265")
	private String emerContactName1;

	/**
	 * 緊急連絡先電話番号1
	 */
	@PeregItem("IS00266")
	private String emerPhoneNumber1;

	/**
	 * 緊急連絡先メモ1
	 */
	@PeregItem("IS00267")
	private String emerMemo1;

	/**
	 * 緊急連絡先名2
	 */
	@PeregItem("IS00268")
	private String emerContactName2;

	/**
	 * 緊急連絡先電話番号2
	 */
	@PeregItem("IS00269")
	private String emerPhoneNumber2;

	/**
	 * 緊急連絡先メモ2
	 */
	@PeregItem("IS00270")
	private String emerMemo2;

	public static PersonContactDto createFromDomain(PersonContact domain) {
		PersonContactDto perContact = new PersonContactDto();
		perContact.setRecordId(domain.getPersonId());
		if (domain.getCellPhoneNumber().isPresent()) {
			perContact.setCellPhoneNumber(domain.getCellPhoneNumber().get().v());
		}
		if (domain.getMailAdress().isPresent()) {
			perContact.setMailAdress(domain.getMailAdress().get().v());
		}
		if (domain.getMobileMailAdress().isPresent()) {
			perContact.setMobileMailAdress(domain.getMobileMailAdress().get().v());
		}

		Optional<EmergencyContact> emerContact1Opt = domain.getEmergencyContact1();
		if (emerContact1Opt.isPresent()) {
			EmergencyContact emerContact1 = emerContact1Opt.get();
			if (emerContact1.getContactName() != null) {
				perContact.setEmerContactName1(emerContact1.getContactName().map(i->i.v()).orElse(null));
			}
			if (emerContact1.getPhoneNumber() != null) {
				perContact.setEmerPhoneNumber1(emerContact1.getPhoneNumber().map(i->i.v()).orElse(null));
			}
			if (emerContact1.getMemo() != null) {
				perContact.setEmerMemo1(emerContact1.getMemo().map(i->i.v()).orElse(null));
			}
		}
		Optional<EmergencyContact> emerContact2Opt = domain.getEmergencyContact2();
		if (emerContact2Opt.isPresent()) {
			EmergencyContact emerContact2 = emerContact2Opt.get();
			if (emerContact2.getContactName() != null) {
				perContact.setEmerContactName2(emerContact2.getContactName().map(i->i.v()).orElse(null));
			}
			if (emerContact2.getPhoneNumber() != null) {
				perContact.setEmerPhoneNumber2(emerContact2.getPhoneNumber().map(i->i.v()).orElse(null));
			}
			if (emerContact2.getMemo() != null) {
				perContact.setEmerMemo2(emerContact2.getMemo().map(i->i.v()).orElse(null));
			}
		}
		return perContact;
	}
}
