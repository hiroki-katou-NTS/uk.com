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

}
