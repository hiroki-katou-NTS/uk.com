package nts.uk.ctx.at.shared.app.find.workrecord.monthlyresults.roleopenperiod;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleopenperiod.RoleOfOpenPeriod;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleopenperiod.RoleOfOpenPeriodRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class RoleOfOpenPeriodFinder.
 */
@Stateless
public class RoleOfOpenPeriodFinder {
	
	/** The repository. */
	@Inject 
	private RoleOfOpenPeriodRepository repository;
	
	/**
	 * Find data.
	 *
	 * @return the list
	 */
	public List<RoleOfOpenPeriodDto> findData() {
		String companyId = AppContexts.user().companyId();
		List<RoleOfOpenPeriod> lstDomain = repository.findByCID(companyId);
		
		List<RoleOfOpenPeriodDto> lstDto = lstDomain.stream()
												.map(e -> {
													
													RoleOfOpenPeriodDto dto = new RoleOfOpenPeriodDto();
													dto.setBreakoutFrNo(e.getBreakoutFrNo().v());
													dto.setRoleOfOpenPeriod(e.getRoleOfOpenPeriodEnum().value);
													return dto;
												}).collect(Collectors.toList());
		return lstDto;
	}
}
