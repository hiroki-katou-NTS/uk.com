package nts.uk.ctx.at.record.app.find.worklocation;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.record.dom.worklocation.WorkLocationRepository;
import nts.uk.shr.com.context.AppContexts;
@Stateless
/**
 * 
 * @author hieult
 *
 */
public class WorkLocationFinder {

	@Inject
	private WorkLocationRepository workPlaceRepository;
	
	/**
	 * Find All WorkLocation
	 * @param companyID
	 * @return List
	 */
	public List<WorkLocationDto> getAllWorkPlace(){
		String companyID = AppContexts.user().companyId();
		return this.workPlaceRepository.findAll(companyID).stream()
				.map(workPlace -> WorkLocationDto.fromDomain(workPlace))
				.collect(Collectors.toList());
	}
	
	public Optional<WorkLocationDto> getWorkPlace(String workLocationCD){
		String companyID = AppContexts.user().companyId();
		return this.workPlaceRepository.findByCode(companyID, workLocationCD)
				.map(workPlace -> WorkLocationDto.fromDomain(workPlace));
	}
	
}
