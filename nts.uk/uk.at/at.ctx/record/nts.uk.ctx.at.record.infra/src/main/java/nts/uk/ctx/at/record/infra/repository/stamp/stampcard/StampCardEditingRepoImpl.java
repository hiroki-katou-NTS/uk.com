package nts.uk.ctx.at.record.infra.repository.stamp.stampcard;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditing;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditMethod;
import nts.uk.ctx.at.record.dom.stamp.card.stamcardedit.StampCardEditingRepo;

@Stateless
public class StampCardEditingRepoImpl implements StampCardEditingRepo {


	@Override
	public Optional<StampCardEditing> get(String companyId) {
		return Optional.of(StampCardEditing.createFromJavaType(companyId, 16, StampCardEditMethod.PreviousZero.value));
	}

}
