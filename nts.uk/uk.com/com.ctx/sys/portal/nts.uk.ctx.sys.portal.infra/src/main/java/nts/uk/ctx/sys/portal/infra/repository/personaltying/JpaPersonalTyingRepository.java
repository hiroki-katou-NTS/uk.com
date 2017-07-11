package nts.uk.ctx.sys.portal.infra.repository.personaltying;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.webmenu.personaltying.PersonalTying;
import nts.uk.ctx.sys.portal.dom.webmenu.personaltying.PersonalTyingRepository;
import nts.uk.ctx.sys.portal.infra.entity.webmenu.CcgstPersonTying;
import nts.uk.ctx.sys.portal.infra.entity.webmenu.CcgstPersonTyingPK;


@Stateless
public class JpaPersonalTyingRepository extends JpaRepository implements PersonalTyingRepository {
	
	@Override
	public void add(PersonalTying personalTying){
		this.commandProxy().insert(convertToDbType(personalTying));
	}
	@Override
	public void delete(String companyId) {
		CcgstPersonTyingPK key = new CcgstPersonTyingPK(companyId, companyId, companyId);
		this.commandProxy().remove(CcgstPersonTying.class, key);
	}
	private CcgstPersonTying convertToDbType(PersonalTying personalTying) { 
		CcgstPersonTying ccgstPersonTying = new CcgstPersonTying();
		CcgstPersonTyingPK cPersonTyingPK = new CcgstPersonTyingPK(
				personalTying.getCompanyId(),
				personalTying.getWebMenuCode(),
				personalTying.getEmployeeId());
		ccgstPersonTying.ccgstPersonTyingPK = cPersonTyingPK;
		return ccgstPersonTying;
	}

}
