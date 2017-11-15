package nts.uk.ctx.sys.auth.dom.grant;

/**
 * 
 * @author HungTT
 *
 */

public interface RoleSetGrantedPersonRepository {

	public boolean checkRoleSetCdExist(String roleSetCd, String companyId);
	
}
