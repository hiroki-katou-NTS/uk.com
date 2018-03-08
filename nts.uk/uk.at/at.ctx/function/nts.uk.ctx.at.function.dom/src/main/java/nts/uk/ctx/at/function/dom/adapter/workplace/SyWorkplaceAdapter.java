package nts.uk.ctx.at.function.dom.adapter.workplace;

import java.util.List;

import nts.arc.time.GeneralDate;

public interface SyWorkplaceAdapter {
	List<WkpConfigAtTimeAdapterDto> findByWkpIdsAtTime(String companyId, GeneralDate baseDate, List<String> wkpIds);
}
