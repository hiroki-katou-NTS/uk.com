package nts.uk.ctx.at.shared.infra.repository.remainingnumber;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.remainingnumber.excessleave.ExcessLeaveInfo;
import nts.uk.ctx.at.shared.dom.remainingnumber.excessleave.ExcessLeaveInfoRepository;
import nts.uk.ctx.at.shared.infra.entity.remainingnumber.excessleave.KrcmtExcessLeaveInfo;

@Stateless
public class JpaExcessLeaveInfoRepo extends JpaRepository  implements ExcessLeaveInfoRepository{

	/**
	 * Convert to domain
	 * @param entity
	 * @return
	 */
	private ExcessLeaveInfo toDomain(KrcmtExcessLeaveInfo entity){
		return new ExcessLeaveInfo(entity.cID, entity.employeeId, entity.useAtr, entity.occurrenceUnit, entity.paymentMethod);
	}
	
	/**
	 * Convert to entity
	 * @param domain
	 * @return
	 */
	private KrcmtExcessLeaveInfo toEntity(ExcessLeaveInfo domain){
		KrcmtExcessLeaveInfo entity = new KrcmtExcessLeaveInfo();
		entity.cID = domain.getCid();
		entity.employeeId = domain.getSID();
		entity.useAtr = domain.getUseAtr().value;
		entity.occurrenceUnit = domain.getOccurrenceUnit().v();
		entity.paymentMethod = domain.getPaymentMethod().value;
		return entity;
	}
	@Override
	public Optional<ExcessLeaveInfo> get(String sid) {
		Optional<KrcmtExcessLeaveInfo> leaveInfo = this.queryProxy().find(sid, KrcmtExcessLeaveInfo.class);
		if (leaveInfo.isPresent()){
			return Optional.of(toDomain(leaveInfo.get()));
		}
		
		return Optional.empty();
	}

	@Override
	public void add(ExcessLeaveInfo domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(ExcessLeaveInfo domain) {
		this.commandProxy().update(toEntity(domain));
	}

	@Override
	public void delete(String sid) {
		this.commandProxy().remove(KrcmtExcessLeaveInfo.class, sid);
 	}

}
