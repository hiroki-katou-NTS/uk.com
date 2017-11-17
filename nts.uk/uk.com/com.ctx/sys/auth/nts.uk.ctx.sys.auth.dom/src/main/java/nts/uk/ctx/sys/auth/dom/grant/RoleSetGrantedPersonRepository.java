package nts.uk.ctx.sys.auth.dom.grant;

import java.util.List;

/**
 * 
 * @author HungTT
 *
 */

public interface RoleSetGrantedPersonRepository {

	public boolean checkRoleSetCdExist(String roleSetCd, String companyId);
	
	public List<RoleSetGrantedPerson> getAll(String roleSetCd, String companyId);
	
}
