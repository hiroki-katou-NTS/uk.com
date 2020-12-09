package nts.uk.ctx.at.record.infra.repository.stamp.stampcardedit;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardDigitNumber;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditMethod;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditing;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditingRepo;
import nts.uk.ctx.at.record.infra.entity.stamp.stampcardedit.KrcmtCardsEditing;

@Stateless
public class StampCardEditingRepoImpl extends JpaRepository implements StampCardEditingRepo {

	@Override
	public StampCardEditing get(String companyId) {
		
		Optional<StampCardEditing> StampCardEditingOpt= this.queryProxy().find(companyId, KrcmtCardsEditing.class).map(x -> toDomain(x));
		if(!StampCardEditingOpt.isPresent()){
			
			return null;
		}
		 
		 return StampCardEditingOpt.get();
	}

	
	@Override
	public void update(StampCardEditing domain) {
		Optional<KrcmtCardsEditing> editCardEnt = this.queryProxy().find(domain.getCompanyId(), KrcmtCardsEditing.class);
		if (!editCardEnt.isPresent()) {
			return;
		}
		
		KrcmtCardsEditing entity = editCardEnt.get();
		
		entity = toEntity(domain, entity);
		
		this.commandProxy().update(entity);
		
	}
	
	private StampCardEditing toDomain(KrcmtCardsEditing ent) {

		return new StampCardEditing(ent.cid, new StampCardDigitNumber(ent.numberOfDigits),
				EnumAdaptor.valueOf(ent.editingMethod, StampCardEditMethod.class));
	}
	
	
	
	public KrcmtCardsEditing toEntity(StampCardEditing domain, KrcmtCardsEditing  entity) {
		entity.editingMethod = domain.getStampMethod() == null ? 0 : domain.getStampMethod().value;
		entity.numberOfDigits = domain.getDigitsNumber() == null ? 0 : domain.getDigitsNumber().v();
		return entity;
	}
	

}
