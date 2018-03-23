package nts.uk.ctx.at.request.infra.repository.application.holidayshipment.compltleavesimmng;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.CompltLeaveSimMng;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.CompltLeaveSimMngRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.compltleavesimmng.SyncState;
import nts.uk.ctx.at.request.infra.entity.application.holidayshipment.compltleavesimmana.KrqdtCompltLeaveSimMana;
import nts.uk.ctx.at.request.infra.entity.application.holidayshipment.compltleavesimmana.KrqdtCompltLeaveSimManaPK;

/**
 * 
 * @author sonnlb
 */
@Stateless
public class JpaCompltLeaveSimMngRepository extends JpaRepository implements CompltLeaveSimMngRepository {

	private static final String FIND_ALL = "SELECT m FROM KrqdtCompltLeaveSimMana m";
	private static final String FIND_BY_APPID = FIND_ALL + " WHERE m.pk.recAppID=:recAppID ";

	@Override
	public void insert(CompltLeaveSimMng domain) {
		this.commandProxy().insert(toEntity(domain));

	}

	KrqdtCompltLeaveSimMana toEntity(CompltLeaveSimMng domain) {
		KrqdtCompltLeaveSimMana entity = new KrqdtCompltLeaveSimMana();
		entity.setSyncing(domain.getSyncing().value);
		KrqdtCompltLeaveSimManaPK pk = new KrqdtCompltLeaveSimManaPK(domain.getRecAppID(),
				domain.getAbsenceLeaveAppID());
		entity.setPk(pk);
		return entity;

	}

	@Override
	public Optional<CompltLeaveSimMng> findByRecID(String recAppID) {
		return this.queryProxy().query(FIND_BY_APPID, KrqdtCompltLeaveSimMana.class).setParameter("recAppID", recAppID)
				.getSingle().map(x -> toDomain(x));
	}

	private CompltLeaveSimMng toDomain(KrqdtCompltLeaveSimMana entity) {
		return new CompltLeaveSimMng(entity.getPk().getRecAppID(), entity.getPk().getAbsenceLeaveAppID(),
				EnumAdaptor.valueOf(entity.getSyncing(), SyncState.class));
	}

}
