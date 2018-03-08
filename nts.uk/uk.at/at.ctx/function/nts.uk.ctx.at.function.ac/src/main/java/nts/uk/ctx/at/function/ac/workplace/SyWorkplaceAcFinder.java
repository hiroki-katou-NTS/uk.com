package nts.uk.ctx.at.function.ac.workplace;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.function.dom.adapter.workplace.SyWorkplaceAdapter;
import nts.uk.ctx.at.function.dom.adapter.workplace.WkpConfigAtTimeAdapterDto;
import nts.uk.ctx.bs.employee.pub.workplace.SyWorkplacePub;
import nts.uk.ctx.bs.employee.pub.workplace.WkpConfigAtTimeExport;
@Stateless
public class SyWorkplaceAcFinder implements SyWorkplaceAdapter {

	@Inject
	private SyWorkplacePub syWorkplacePub;
	
	@Override
	public List<WkpConfigAtTimeAdapterDto> findByWkpIdsAtTime(String companyId, GeneralDate baseDate,
			List<String> wkpIds) {
		List<WkpConfigAtTimeAdapterDto> data  = syWorkplacePub.findByWkpIdsAtTime(companyId, baseDate, wkpIds)
				.stream().map(c->convertToExport(c)).collect(Collectors.toList());
		
		return data;
	}
	
	private WkpConfigAtTimeAdapterDto convertToExport(WkpConfigAtTimeExport export) {
		return new WkpConfigAtTimeAdapterDto(
				export.getWorkplaceId(),
				export.getHierarchyCd()
				);
	}

}
