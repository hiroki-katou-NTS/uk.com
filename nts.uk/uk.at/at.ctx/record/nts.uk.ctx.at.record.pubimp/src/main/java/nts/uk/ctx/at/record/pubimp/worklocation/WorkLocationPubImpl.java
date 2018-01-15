package nts.uk.ctx.at.record.pubimp.worklocation;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.worklocation.WorkLocation;
import nts.uk.ctx.at.record.dom.worklocation.WorkLocationRepository;
import nts.uk.ctx.at.record.pub.worklocation.WorkLocationPub;
import nts.uk.ctx.at.record.pub.worklocation.WorkLocationPubExport;

@Stateless
public class WorkLocationPubImpl implements WorkLocationPub {
	@Inject
	private WorkLocationRepository workRepository;

	@Override
	public WorkLocationPubExport getLocationName(String companyID, String workLocationCd) {
		WorkLocationPubExport workLocationExport = convertToExport(
				workRepository.findByCode(companyID, workLocationCd).get());
		return workLocationExport;
	}

	/**
	 * 
	 * @param workLocation
	 * @return
	 */
	public WorkLocationPubExport convertToExport(WorkLocation workLocation) {
		return new WorkLocationPubExport(workLocation.getCompanyID(), workLocation.getWorkLocationCD().v(),
				workLocation.getWorkLocationName().v(), workLocation.getHoriDistance(), workLocation.getVertiDistance(),
				workLocation.getLatitude().v(), workLocation.getLongitude().v());
	}
}
