package nts.uk.ctx.pr.core.app.find.wageprovision.wagetable;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.time.YearMonth;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTable;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableRepository;
import nts.uk.ctx.pr.core.dom.wageprovision.wagetable.WageTableService;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author HungTT
 *
 */

@Stateless
public class WageTableFinder {

	@Inject
	private WageTableService wageTableService;

	@Inject
	private WageTableRepository wageTableRepo;

	public List<WageTableDto> getAll() {
		return wageTableRepo.getAllWageTable(AppContexts.user().companyId()).stream()
				.map(i -> WageTableDto.fromDomainToDto(i)).collect(Collectors.toList());
	}

	public List<WageTableDto> getWageTableByYearMonth(int yearMonth) {
		return wageTableService.getFormulaByYearMonth(new YearMonth(yearMonth)).stream()
				.map(WageTableDto::fromDomainToDto).collect(Collectors.toList());
	}

	public WageTableDto getWageTableById(String wageTableCode) {
		String cid = AppContexts.user().companyId();
		Optional<WageTable> domainOtp = wageTableRepo.getWageTableById(cid, wageTableCode);
		return domainOtp.map(WageTableDto::fromDomainToDto).orElse(null);
	}

}
