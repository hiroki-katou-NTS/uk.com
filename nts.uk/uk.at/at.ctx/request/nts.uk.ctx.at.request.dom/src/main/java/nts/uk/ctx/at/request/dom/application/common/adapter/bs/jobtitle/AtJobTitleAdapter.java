package nts.uk.ctx.at.request.dom.application.common.adapter.bs.jobtitle;

import java.util.Optional;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.jobtitle.dto.AffJobTitleHistoryImport;

public interface AtJobTitleAdapter {
	Optional<AffJobTitleHistoryImport> getJobTitlebBySIDAndDate(String sID, GeneralDate baseDate);
}
