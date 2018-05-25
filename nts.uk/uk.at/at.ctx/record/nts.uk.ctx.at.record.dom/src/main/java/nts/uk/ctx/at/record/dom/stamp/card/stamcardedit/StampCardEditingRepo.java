package nts.uk.ctx.at.record.dom.stamp.card.stamcardedit;

import java.util.Optional;

public interface StampCardEditingRepo {

	
	Optional<StampCardEditing> get(String companyId);

}
