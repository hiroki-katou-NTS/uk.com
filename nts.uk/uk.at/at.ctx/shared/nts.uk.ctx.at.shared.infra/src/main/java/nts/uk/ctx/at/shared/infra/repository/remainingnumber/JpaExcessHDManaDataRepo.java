package nts.uk.ctx.at.shared.infra.repository.remainingnumber;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.excessleave.ExcessHolidayManaDataRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.excessleave.ExcessHolidayManagementData;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.excessleave.KrcmtExcessHDManaData;

@Stateless
public class JpaExcessHDManaDataRepo extends JpaRepository implements ExcessHolidayManaDataRepository{

	private static final String QUERY_BYSID = "SELECT e FROM KrcmtExcessHDManaData e WHERE e.cID =:cid AND e.sID =:sid AND e.expiredState = :expiredState ";
	
	@Override
	public List<ExcessHolidayManagementData> getBySidWithExpCond(String cid, String sid, int state) {
		List<KrcmtExcessHDManaData> list = this.queryProxy().query(QUERY_BYSID,KrcmtExcessHDManaData.class)
				.setParameter("sid", sid)
				.setParameter("cid", cid)
				.setParameter("expiredState", state)
				.getList();
		return list.stream().map(i->toDomain(i)).collect(Collectors.toList());
	}
	
	private ExcessHolidayManagementData toDomain(KrcmtExcessHDManaData entity) {
		return new ExcessHolidayManagementData(entity.id, entity.cID, entity.sID, entity.grantDate, entity.expiredDate,
				entity.expiredState, entity.registrationType, entity.occurrencesNumber, entity.remainNumer,
				entity.remainNumer);
	}

}
