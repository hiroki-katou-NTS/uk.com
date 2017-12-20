package nts.uk.ctx.at.schedule.app.find.shift.workpairpattern;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.schedule.dom.shift.workpairpattern.WorkPairPatternRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author sonnh1
 *
 */
@Stateless
public class WorkPairPatternFinder {

	@Inject
	private WorkPairPatternRepository repo;

	public List<ComPatternDto> getAllDataComPattern() {
		String companyId = AppContexts.user().companyId();
		return repo.getAllDataComPattern(companyId).stream().map(x -> ComPatternDto.fromDomain(x))
				.collect(Collectors.toList());
	}
	
	public List<WkpPatternDto> getAllDataWkpPattern(String workPlaceId) {
		return repo.getAllDataWkpPattern(workPlaceId).stream().map(x -> WkpPatternDto.fromDomain(x))
				.collect(Collectors.toList());
	}
}
