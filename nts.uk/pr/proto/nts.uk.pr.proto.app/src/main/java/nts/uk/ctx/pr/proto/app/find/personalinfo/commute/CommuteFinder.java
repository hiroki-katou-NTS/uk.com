package nts.uk.ctx.pr.proto.app.find.personalinfo.commute;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import nts.uk.ctx.pr.proto.dom.personalinfo.commute.PersonalCommuteFeeRepository;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
public class CommuteFinder {
	
	@Inject
	private PersonalCommuteFeeRepository repository;
	
	public Optional<CommuteDto> find(String personId, int startYearMonth){
		String companyCode = AppContexts.user().companyCode(); 
		return this.repository.find(companyCode, personId, startYearMonth).map(c -> CommuteDto.fromDomain(c));
	}
}
