package nts.uk.ctx.at.record.app.find.workplace;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import nts.uk.shr.com.context.AppContexts;
import workplace.WorkPlaceRepository;
@Stateless
/**
 * 
 * @author hieult
 *
 */
public class WorkPlaceFinder {

	@Inject
	private WorkPlaceRepository workPlaceRepository;
	
	/**
	 * Find All WorkPlace
	 * @param companyID
	 * @return List
	 */
	public List<WorkPlaceDto> getAllWorkPlace(){
		String companyID = AppContexts.user().companyId();
		return this.workPlaceRepository.findAll(companyID).stream()
				.map(workPlace -> WorkPlaceDto.fromDomain(workPlace))
				.collect(Collectors.toList());
	}
	
	public Optional<WorkPlaceDto> getWorkPlace(String workLocationCD){
		String companyID = AppContexts.user().companyId();
		return this.workPlaceRepository.findByCode(companyID, workLocationCD).map(workPlace -> WorkPlaceDto.fromDomain(workPlace));
	}
	
}
