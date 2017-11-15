package nts.uk.ctx.sys.auth.infra.repository.grant;

import javax.ejb.Stateless;
import javax.transaction.Transactional;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.auth.dom.grant.RoleSetGrantedPersonRepository;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
@Transactional
public class JpaRoleSetGrantedPersonRepository extends JpaRepository implements RoleSetGrantedPersonRepository {

	@Override
	public boolean checkRoleSetCdExist(String roleSetCd, String companyId) {
		// TODO Auto-generated method stub
		return false;
	}

}
