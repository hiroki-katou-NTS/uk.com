package nts.uk.ctx.at.record.dom.stamp.application;

import java.util.List;
import java.util.Optional;

public interface StampResultDisplayRepository{
	void insert(StampResultDisplay application);
	
	void update(StampResultDisplay application);
	
	Optional<StampResultDisplay> getStampSet (String companyId);

	List<StampAttenDisplay> getStampSets(String companyId);

	void delete();
}
