package nts.uk.ctx.sys.assist.dom.datarestoration;

import java.util.List;

import nts.arc.time.GeneralDateTime;

public interface DataRecoverySelectionRepository {
	
	List<DataRecoverySelection> getDataRecoverySelection(String companyId, List<Integer> systemType, GeneralDateTime startDate, GeneralDateTime endDate);

}
