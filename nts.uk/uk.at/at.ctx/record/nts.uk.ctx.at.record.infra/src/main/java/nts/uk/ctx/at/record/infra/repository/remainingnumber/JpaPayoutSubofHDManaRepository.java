package nts.uk.ctx.at.record.infra.repository.remainingnumber;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.PayoutSubofHDManaRepository;
import nts.uk.ctx.at.record.dom.remainingnumber.paymana.PayoutSubofHDManagement;
import nts.uk.ctx.at.record.infra.entity.remainingnumber.paymana.KrcmtPayoutSubOfHDMana;
import nts.uk.ctx.at.record.infra.entity.remainingnumber.paymana.KrcmtPayoutSubOfHDManaPK;

@Stateless
public class JpaPayoutSubofHDManaRepository extends JpaRepository implements PayoutSubofHDManaRepository {

	@Override
	public void add(PayoutSubofHDManagement domain) {
		this.commandProxy().insert(toEntity(domain));
	}

	@Override
	public void update(PayoutSubofHDManagement domain) {
		KrcmtPayoutSubOfHDManaPK key = new KrcmtPayoutSubOfHDManaPK(domain.getPayoutId(), domain.getSubOfHDID());
		Optional<KrcmtPayoutSubOfHDMana> existed = this.queryProxy().find(key, KrcmtPayoutSubOfHDMana.class);
		if (existed.isPresent()){
			this.commandProxy().update(toEntity(domain));	
		}
	}

	@Override
	public void delete(String payoutId, String subOfHDID) {
		KrcmtPayoutSubOfHDManaPK key = new KrcmtPayoutSubOfHDManaPK(payoutId, subOfHDID);
		Optional<KrcmtPayoutSubOfHDMana> existed = this.queryProxy().find(key, KrcmtPayoutSubOfHDMana.class);
		if (existed.isPresent()){
			this.commandProxy().remove(KrcmtPayoutSubOfHDMana.class, key);	
		}
		
	}
	
	/** 
	 * Convert from enity to domain
	 * @param entity
	 * @return
	 */
	private PayoutSubofHDManagement toDomain(KrcmtPayoutSubOfHDMana entity){
		return new PayoutSubofHDManagement(entity.krcmtPayoutSubOfHDManaPK.payoutId, entity.krcmtPayoutSubOfHDManaPK.subOfHDID, entity.usedDays, entity.targetSelectionAtr);
	}
	
	/**
	 * Convert from domain to entity
	 * @param domain
	 * @return
	 */
	private KrcmtPayoutSubOfHDMana toEntity(PayoutSubofHDManagement domain){
		KrcmtPayoutSubOfHDManaPK key = new KrcmtPayoutSubOfHDManaPK(domain.getPayoutId(), domain.getSubOfHDID());
		return new KrcmtPayoutSubOfHDMana(key, domain.getUsedDays().v(), domain.getTargetSelectionAtr().value);
	}

}
