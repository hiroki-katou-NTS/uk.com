package nts.uk.ctx.at.record.pubimp.worklocation;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocation;
import nts.uk.ctx.at.record.dom.stampmanagement.workplace.WorkLocationRepository;
import nts.uk.ctx.at.record.pub.worklocation.WorkLocationExportNew;
import nts.uk.ctx.at.record.pub.worklocation.WorkLocationPub;
import nts.uk.ctx.at.record.pub.worklocation.WorkLocationPubExport;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class WorkLocationPubImpl implements WorkLocationPub {
	@Inject
	private WorkLocationRepository workRepository;

	@Override
	public WorkLocationPubExport getLocationName(String contractCode , String workLocationCd) {
		WorkLocationPubExport workLocationExport = convertToExport(
				workRepository.findByCode(contractCode, workLocationCd).get());
		return workLocationExport;
	}

	/**
	 * 
	 * @param workLocation
	 * @return
	 */
	public WorkLocationPubExport convertToExport(WorkLocation workLocation) {
		return new WorkLocationPubExport(
				workLocation.getContractCode().v(),
				workLocation.getWorkLocationCD().v(),
				workLocation.getWorkLocationName().v(),
				workLocation.getStampRange().map(s -> s.getRadius().value).orElse(null),
				workLocation.getStampRange().map(s -> s.getGeoCoordinate().getLatitude()).orElse(null),
				workLocation.getStampRange().map(s -> s.getGeoCoordinate().getLongitude()).orElse(null));
	}

	@Override
	public List<WorkLocationExportNew> getWorkLocationName(List<String> listWorkLocationCd) {
		String contractCode = AppContexts.user().contractCode();
		List<WorkLocation> data = workRepository.findByCodes(contractCode, listWorkLocationCd);
		return data.stream().map(c-> new WorkLocationExportNew(c.getWorkLocationCD().v(), c.getWorkLocationName().v())).collect(Collectors.toList());
	}

	@Override
	public List<WorkLocationPubExport> findAll(String companyId) {
		String contractCode = AppContexts.user().contractCode();
		return workRepository.findAll(contractCode).stream().map(w -> 
					WorkLocationPubExport.createSimpleFromJavaType(
							w.getContractCode().v(),
							w.getWorkLocationCD().v(),
							w.getWorkLocationName().v(),
							w.getStampRange().map(s -> s.getRadius().value).orElse(null),
							w.getStampRange().map(s -> s.getGeoCoordinate().getLatitude()).orElse(null),
							w.getStampRange().map(s -> s.getGeoCoordinate().getLongitude()).orElse(null)))
				.collect(Collectors.toList());
	}
}
