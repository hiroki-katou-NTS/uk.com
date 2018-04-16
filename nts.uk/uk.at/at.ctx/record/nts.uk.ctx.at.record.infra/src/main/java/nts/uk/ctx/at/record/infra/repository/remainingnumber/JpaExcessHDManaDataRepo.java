package nts.uk.ctx.at.record.infra.repository.remainingnumber;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.excessleave.ExcessHolidayManaDataRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.excessleave.ExcessHolidayManagementData;
import nts.uk.ctx.at.record.infra.entity.remainingnumber.excessleave.KrcmtExcessHDManaData;

@Stateless
public class JpaExcessHDManaDataRepo extends JpaRepository implements ExcessHolidayManaDataRepository{

	private String QUERY_BYSID = "SELECT e FROM KrcmtExcessHDManaData e WHERE e.cID =:cid AND e.sID =:sid AND e.expiredState = 1 ";
	
	@Override
	public List<ExcessHolidayManagementData> getBySid(String cid, String sid) {
		List<KrcmtExcessHDManaData> list = this.queryProxy().query(QUERY_BYSID,KrcmtExcessHDManaData.class)
				.setParameter("sid", sid)
				.setParameter("cid", cid)
				.getList();
		return list.stream().map(i->toDomain(i)).collect(Collectors.toList());
	}
	
	private ExcessHolidayManagementData toDomain(KrcmtExcessHDManaData entity) {
		return new ExcessHolidayManagementData(entity.id, entity.cID, entity.sID, entity.grantDate, entity.expiredDate,
				entity.expiredState, entity.registrationType, entity.occurrencesNumber, entity.remainNumer,
				entity.remainNumer);
	}

}
