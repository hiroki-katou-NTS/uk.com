package nts.uk.ctx.at.record.dom.workrecord.erroralarm;

import java.util.List;
import java.util.Optional;

public interface FixedConditionDataRepository {
	List<FixedConditionData> getAllFixedConditionData();
	
	Optional<FixedConditionData> getFixedByNO(int number);
}
