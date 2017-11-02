package nts.uk.ctx.bs.employee.app.find.person.item;

import lombok.Getter;
import nts.uk.ctx.bs.employee.app.find.person.category.CtgItemFixDto;

@Getter
public class ItemEmergencyContact extends CtgItemFixDto{
	private String emgencyContactId;
	private String pid;
	private String personName;
	private String personMailAddress;
	private String streetAddressPerson;
	private String phone;
	private int priorityEmegencyContact;
	private String relationShip;
	
	private ItemEmergencyContact(String emgencyContactId, String pid, String personName,
			String personMailAddress, String streetAddressPerson, String phone, 
			int priorityEmegencyContact, String relationShip){
		super();
		this.ctgItemType = CtgItemType.EMERGENCY_CONTACT;
		this.emgencyContactId = emgencyContactId;
		this.pid = pid;
		this.personName = personName;
		this.personMailAddress = personMailAddress;
		this.streetAddressPerson = streetAddressPerson;
		this.phone = phone;
		this.priorityEmegencyContact = priorityEmegencyContact;
		this.relationShip = relationShip;
	}
	
	public static ItemEmergencyContact createFromJavaType(String emgencyContactId, String pid, String personName,
			String personMailAddress, String streetAddressPerson, String phone, 
			int priorityEmegencyContact, String relationShip){
		return new ItemEmergencyContact(emgencyContactId, pid, personName, personMailAddress, 
				streetAddressPerson, phone, priorityEmegencyContact, relationShip);
	}
}
