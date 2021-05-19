package nts.uk.ctx.at.function.ac.worklocation;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.adapter.worklocation.WorkLocationAdapter;
import nts.uk.ctx.at.function.dom.adapter.worklocation.WorkLocationImport;
import nts.uk.ctx.at.record.pub.worklocation.WorkLocationPub;

@Stateless
public class WorkLocationRecAdapterImpl implements WorkLocationAdapter {

	@Inject
	private WorkLocationPub workLocationPub;
	
	@Override
	public List<WorkLocationImport> findAll(String companyId) {
		return workLocationPub.findAll(companyId).stream().map(w -> 
					new WorkLocationImport(w.getContractCode(), w.getWorkLocationCD(), w.getWorkLocationName(), 
						w.getRadius(), w.getLatitude(), w.getLongitude()))
				.collect(Collectors.toList());
	}

}
