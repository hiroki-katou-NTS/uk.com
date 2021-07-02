package nts.uk.ctx.at.function.dom.adapter.workplace;

import java.util.List;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.AffWorkplaceHistoryImport;

public interface SyWorkplaceAdapter {
	List<WkpConfigAtTimeAdapterDto> findByWkpIdsAtTime(String companyId, GeneralDate baseDate, List<String> wkpIds);
	
	List<AffWorkplaceHistoryImport> getWorkplaceBySidsAndBaseDate(List<String> sids, GeneralDate baseDate);
}
