package nts.uk.ctx.sys.auth.dom.grant.rolesetperson;

import java.util.List;

/**
 * 
 * @author HungTT
 *
 */

public interface RoleSetGrantedPersonRepository {

	public boolean checkRoleSetCdExist(String roleSetCd, String companyId);
	
	public List<RoleSetGrantedPerson> getAll(String roleSetCd, String companyId);
	
	public void insert(RoleSetGrantedPerson domain);
	
	public void update(RoleSetGrantedPerson domain);
	
	public void delete(String employeeId);
	
}
