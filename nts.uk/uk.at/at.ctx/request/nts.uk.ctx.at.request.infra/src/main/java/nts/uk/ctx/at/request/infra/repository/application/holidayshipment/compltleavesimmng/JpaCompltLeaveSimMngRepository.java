package nts.uk.ctx.at.request.infra.repository.application.holidayshipment.compltleavesimmng;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.CompltLeaveSimMng;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.CompltLeaveSimMngRepository;
import nts.uk.ctx.at.request.infra.entity.application.holidayshipment.compltleavesimmana.KrqdtCompltLeaveSimMana;
import nts.uk.ctx.at.request.infra.entity.application.holidayshipment.compltleavesimmana.KrqdtCompltLeaveSimManaPK;

@Stateless
public class JpaCompltLeaveSimMngRepository extends JpaRepository implements CompltLeaveSimMngRepository {

	@Override
	public void insert(CompltLeaveSimMng domain) {
		this.commandProxy().insert(toEntity(domain));

	}

	KrqdtCompltLeaveSimMana toEntity(CompltLeaveSimMng domain) {
		KrqdtCompltLeaveSimMana entity = new KrqdtCompltLeaveSimMana();
		entity.setSyncing(domain.getSyncing());
		KrqdtCompltLeaveSimManaPK pk = new KrqdtCompltLeaveSimManaPK(domain.getRecAppID(),
				domain.getAbsenceLeaveAppID());
		entity.setPk(pk);
		return entity;

	}

}
