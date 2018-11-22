package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class WageTableFinder {

	@Inject
	private WageTableRepository repo;

	public List<WageTableDto> getAll() {
		return repo.getAllWageTable(AppContexts.user().companyId()).stream().map(i -> WageTableDto.fromDomainToDto(i))
				.collect(Collectors.toList());
	}
	
}
