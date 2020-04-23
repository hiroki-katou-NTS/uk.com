package nts.uk.ctx.at.record.infra.repository.stamp.stampcardedit;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditing;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditingRepo;
import nts.uk.ctx.at.record.infra.entity.stamp.stampcardedit.KrcmtEditingCards;

@Stateless
public class StampCardEditingRepoImpl extends JpaRepository implements StampCardEditingRepo {

	@Override
	public Optional<StampCardEditing> get(String companyId) {
		Optional<KrcmtEditingCards> editCardEnt = this.queryProxy().find(companyId, KrcmtEditingCards.class);
		if (!editCardEnt.isPresent()) {
			return Optional.empty();
		}
		KrcmtEditingCards ent = editCardEnt.get();
		return Optional.of(StampCardEditing.createFromJavaType(companyId, ent.numberOfDigits, ent.editingMethod));
	}

	@Override
	public StampCardEditing getStampCardEditing(String companyId) {
		Optional<KrcmtEditingCards> editCardEnt = this.queryProxy().find(companyId, KrcmtEditingCards.class);
		if (!editCardEnt.isPresent()) {
			return null;
		}
		KrcmtEditingCards ent = editCardEnt.get();
		return StampCardEditing.createFromJavaType(companyId, ent.numberOfDigits, ent.editingMethod);
	}

	
	@Override
	public void update(StampCardEditing domain) {
		Optional<KrcmtEditingCards> editCardEnt = this.queryProxy().find(domain.getCompanyId(), KrcmtEditingCards.class);
		if (!editCardEnt.isPresent()) {
			return;
		}
		
		KrcmtEditingCards entity = editCardEnt.get();
		
		entity = toEntity(domain, entity);
		
		this.commandProxy().update(entity);
		
	}
	
	
	public KrcmtEditingCards toEntity(StampCardEditing domain, KrcmtEditingCards  entity) {
		entity.cid = domain.getCompanyId();
		entity.editingMethod = domain.getMethod() == null ? 0 : domain.getMethod().value;
		entity.numberOfDigits = domain.getDigitsNumber() == null ? 0 : domain.getDigitsNumber().v();
		return entity;
	}
	

}
