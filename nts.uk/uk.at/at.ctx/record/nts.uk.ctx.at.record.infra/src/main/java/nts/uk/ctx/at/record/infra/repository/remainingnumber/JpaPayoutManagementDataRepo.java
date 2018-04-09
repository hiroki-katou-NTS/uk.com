package nts.uk.ctx.at.record.infra.repository.remainingnumber;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.PayoutManagementData;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.PayoutManagementDataRepository;
import nts.uk.ctx.at.record.infra.entity.remainingnumber.paymana.KrcmtPayoutManaData;

@Stateless
public class JpaPayoutManagementDataRepo extends JpaRepository implements PayoutManagementDataRepository{

	private String QUERY_BYSID = "SELECT p FROM KrcmtPayoutManaData p WHERE p.cID = :cid AND p.sID =:employeeId";
	
	private String QUERY_BYSID_WITH_COND = String.join(" ",QUERY_BYSID, "AND p.stateAtr = :state");
	
	
	@Override
	public List<PayoutManagementData> getSid(String cid, String sid) {
		List<KrcmtPayoutManaData> list = this.queryProxy().query(QUERY_BYSID,KrcmtPayoutManaData.class)
				.setParameter("cid", cid)
				.setParameter("employeeId", sid).getList();
		return list.stream().map(i->toDomain(i)).collect(Collectors.toList());
	}
	
	@Override
	public List<PayoutManagementData> getSidWithCod(String cid, String sid, int state) {
		List<KrcmtPayoutManaData> list = this.queryProxy().query(QUERY_BYSID_WITH_COND,KrcmtPayoutManaData.class)
				.setParameter("cid", cid)
				.setParameter("employeeId", sid)
				.setParameter("state", state)
				.getList();
		return list.stream().map(i->toDomain(i)).collect(Collectors.toList());
	}

	/**
	 * Convert to domain
	 * @param entity
	 * @return
	 */
	private PayoutManagementData toDomain(KrcmtPayoutManaData entity) {
		return new PayoutManagementData(entity.payoutId,entity.cID, entity.sID, entity.unknownDate, entity.dayOff,
				entity.expiredDate, entity.lawAtr, entity.occurredDays, entity.occurredDays, entity.stateAtr);
	}
	
	@Override
	public void create(PayoutManagementData domain) {
		this.commandProxy().insert(toEntity(domain));
	}
	
	/**
	 * Convert to entity
	 * @param domain
	 * @return
	 */
	private KrcmtPayoutManaData toEntity(PayoutManagementData domain){
		KrcmtPayoutManaData entity = new KrcmtPayoutManaData();
		entity.payoutId = domain.getPayoutId();
		entity.sID = domain.getSID();
		entity.cID = domain.getCID();
		entity.unknownDate = domain.getPayoutDate().isUnknownDate();
		if (domain.getPayoutDate().getDayoffDate().isPresent()){
			entity.dayOff = domain.getPayoutDate().getDayoffDate().get();
		}
		entity.expiredDate = domain.getExpiredDate();
		entity.lawAtr = domain.getLawAtr().value;
		entity.occurredDays = domain.getOccurredDays().v();
		entity.unUsedDays = domain.getUnUsedDays().v();
		entity.stateAtr = domain.getStateAtr().value;
		return entity;
	}

}
