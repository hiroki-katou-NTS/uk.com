package nts.uk.ctx.at.shared.dom.adapter.jobtitle;

import java.util.List;
import java.util.Optional;

import nts.arc.time.GeneralDate;

public interface SharedAffJobtitleHisAdapter {

	Optional<SharedAffJobTitleHisImport> findAffJobTitleHis(String employeeId, GeneralDate processingDate);
	
	List<SharedAffJobTitleHisImport> findAffJobTitleHisByListSid(List<String> employeeIds, GeneralDate processingDate);
}
