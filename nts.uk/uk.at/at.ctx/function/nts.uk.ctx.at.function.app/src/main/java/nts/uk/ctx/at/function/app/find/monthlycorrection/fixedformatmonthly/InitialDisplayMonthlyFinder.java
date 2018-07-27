package nts.uk.ctx.at.function.app.find.monthlycorrection.fixedformatmonthly;

import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.InitialDisplayMonthly;
import nts.uk.ctx.at.function.dom.monthlycorrection.fixedformatmonthly.InitialDisplayMonthlyRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class InitialDisplayMonthlyFinder {
	
	@Inject
	private InitialDisplayMonthlyRepository repo;
	
	public InitialDisplayMonthlyDto getByCode(String monthlyPfmFormatCode ) {
		String companyID = AppContexts.user().companyId();
		Optional<InitialDisplayMonthly> data = repo.getInitialDisplayMon(companyID, monthlyPfmFormatCode);
		if(data.isPresent())
			return InitialDisplayMonthlyDto.fromDomain(data.get());
		return null;
		
	}
	
	public List<InitialDisplayMonthlyDto> getAllDisplayMonthly() {
		String companyID = AppContexts.user().companyId();
		List<InitialDisplayMonthly> data = repo.getAllInitialDisMon(companyID);
		if(data.isEmpty())
			return Collections.emptyList();
					
		return data.stream().map(c->InitialDisplayMonthlyDto.fromDomain(c)).collect(Collectors.toList());
		
	}
	
	

}
