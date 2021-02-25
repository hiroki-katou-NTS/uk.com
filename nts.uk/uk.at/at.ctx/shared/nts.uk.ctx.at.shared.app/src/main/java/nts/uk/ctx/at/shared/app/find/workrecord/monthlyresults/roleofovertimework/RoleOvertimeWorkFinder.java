package nts.uk.ctx.at.shared.app.find.workrecord.monthlyresults.roleofovertimework;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleofovertimework.RoleOvertimeWork;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleofovertimework.RoleOvertimeWorkRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class RoleOvertimeWorkFinder.
 */
@Stateless
public class RoleOvertimeWorkFinder {
	
	/** The repository. */
	@Inject 
	private RoleOvertimeWorkRepository repository;
	
	/**
	 * Find data.
	 *
	 * @return the list
	 */
	public List<RoleOvertimeWorkDto> findData() {
		String companyId = AppContexts.user().companyId();
		List<RoleOvertimeWork> lstDomain = repository.findByCID(companyId);
		
		List<RoleOvertimeWorkDto> lstDto = lstDomain.stream()
												.map(e -> {
													RoleOvertimeWorkDto dto = new RoleOvertimeWorkDto();
													dto.setOvertimeFrNo(e.getOvertimeFrNo().v());
													dto.setRoleOTWork(e.getRoleOTWorkEnum().value);
													return dto;
												}).collect(Collectors.toList());
		return lstDto;
	}
}
