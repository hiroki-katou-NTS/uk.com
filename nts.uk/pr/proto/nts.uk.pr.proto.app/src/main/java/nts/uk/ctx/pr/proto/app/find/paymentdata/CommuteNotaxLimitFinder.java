package nts.uk.ctx.pr.proto.app.find.paymentdata;

import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import nts.uk.ctx.pr.proto.dom.paymentdata.repository.CommuNotaxLimitRepository;
import nts.uk.shr.com.context.AppContexts;


@Stateless
public class CommuteNotaxLimitFinder {

	@Inject
	private CommuNotaxLimitRepository repository;
	
	public Optional<CommuteNotaxLimitDto> find( String commuNotaxLimitCode){
		String companyCode = AppContexts.user().companyCode(); 
		return this.repository.find(companyCode, commuNotaxLimitCode).map(c -> CommuteNotaxLimitDto.fromDomain(c));
	}
}
