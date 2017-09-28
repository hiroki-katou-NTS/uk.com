package nts.uk.screen.at.app.worktype;

import java.util.List;


public interface WorkTypeQueryRepository {

	List<WorkTypeDto> findAllWorkType(String companyId);
}
