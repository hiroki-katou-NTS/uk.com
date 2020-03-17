package nts.uk.ctx.at.record.dom.stamp.application;

import java.util.Optional;

public interface StamPromptAppRepository {

	void insert(StamPromptApplication application);
	
	void update(StamPromptApplication application);
	
	Optional<StamPromptApplication> getStampSet (String companyId);
}
