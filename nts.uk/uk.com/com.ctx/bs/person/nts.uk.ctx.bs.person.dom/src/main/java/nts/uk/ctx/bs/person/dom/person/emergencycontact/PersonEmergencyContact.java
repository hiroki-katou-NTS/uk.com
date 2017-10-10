package nts.uk.ctx.bs.person.dom.person.emergencycontact;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import nts.arc.layer.dom.AggregateRoot;
import nts.uk.ctx.bs.person.dom.person.deletemanagement.PersonDeleteManagement;
import nts.uk.ctx.bs.person.dom.person.info.PersonMailAddress;
import nts.uk.ctx.bs.person.dom.person.info.PersonMobile;
import nts.uk.ctx.bs.person.dom.person.info.personnamegroup.PersonName;

/**
 * The class Person Emergecy Contact - 緊急連絡先
 *
 */
@Getter
@AllArgsConstructor
@NoArgsConstructor
public class PersonEmergencyContact extends AggregateRoot {

	/** 緊急連絡先ID */
	private String emgencyContactId;

	/** 個人ID */
	private String pid;
	
	/** 個人名 */
	private PersonName personName;
	
	/** 個人メールアドレス */
	private PersonMailAddress personMailAddress;
	
	/** 住所  */
	private StreetAddressPerson streetAddressPerson;
	
	/** 個人携帯 */
	private PersonMobile phone;
	
	/** 優先度 */
	private PriorityEmegencyContact priorityEmegencyContact;
	
	/** 関係 */
	private RelationShip relationShip;
	
	
	public static PersonEmergencyContact createFromJavaType(
			String emgencyContactId,
			String pid,
			String personName,
			String personMailAddress,
			String streetAddressPerson,
			String phone,
			int priorityEmegencyContact,
			String relationShip) 
	{
		return new PersonEmergencyContact(
				emgencyContactId,
				pid,
				new PersonName(personName),
				new PersonMailAddress(personMailAddress),
				new StreetAddressPerson(streetAddressPerson),
				new PersonMobile(phone), 
				new PriorityEmegencyContact(priorityEmegencyContact), 
				new RelationShip(relationShip));
	}

}
