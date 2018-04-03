package nts.uk.ctx.at.record.infra.repository.workrecord.closurestatus;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.ClosureStatusManagement;
import nts.uk.ctx.at.record.dom.workrecord.closurestatus.ClosureStatusManagementRepository;
import nts.uk.ctx.at.record.infra.entity.workrecord.closurestatus.KrcdtClosureSttMng;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class JpaClosureStatusManagementRepository extends JpaRepository implements ClosureStatusManagementRepository {

	@Override
	public void add(ClosureStatusManagement domain) {
		this.commandProxy().insert(KrcdtClosureSttMng.fromDomain(domain));
	}

}
