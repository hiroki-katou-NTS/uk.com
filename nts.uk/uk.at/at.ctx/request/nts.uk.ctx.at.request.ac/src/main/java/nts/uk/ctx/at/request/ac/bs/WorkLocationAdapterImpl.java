package nts.uk.ctx.at.request.ac.bs;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.pub.worklocation.WorkLocationPub;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.WorkLocationAdapter;
import nts.uk.ctx.at.request.dom.application.common.adapter.bs.dto.WorkLocationNameImported;

/**
 * WorkLocationAdapter
 *
 * @author LienPTK
 */
@Stateless
public class WorkLocationAdapterImpl implements WorkLocationAdapter {

	@Inject
	private WorkLocationPub workLocationPub;


	@Override
	public List<WorkLocationNameImported> findWorkLocationName(List<String> workLocationCDs) {
		return this.workLocationPub.getWorkLocationName(workLocationCDs).stream()
				.map(workLocation -> {
					return new WorkLocationNameImported(
						workLocation.getWorkLocationCD(),
						workLocation.getWorkLocationName()
					);
				}).collect(Collectors.toList());
	}

}
