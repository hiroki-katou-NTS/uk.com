package nts.uk.ctx.bs.person.dom.person.family;

import java.util.List;

public interface FamilyRepository {
	
	public Family getFamilyById(String familyId);
	
	public List<Family> getListByPid(String pid);
}
