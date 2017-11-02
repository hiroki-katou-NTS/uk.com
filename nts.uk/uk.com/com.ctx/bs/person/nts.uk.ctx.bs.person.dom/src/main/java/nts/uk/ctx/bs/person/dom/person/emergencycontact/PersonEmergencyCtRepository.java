package nts.uk.ctx.bs.person.dom.person.emergencycontact;

import java.util.List;

public interface PersonEmergencyCtRepository {
	
	public PersonEmergencyContact getByid(String id);
	
	public List<PersonEmergencyContact> getListbyPid(String pid);

}
