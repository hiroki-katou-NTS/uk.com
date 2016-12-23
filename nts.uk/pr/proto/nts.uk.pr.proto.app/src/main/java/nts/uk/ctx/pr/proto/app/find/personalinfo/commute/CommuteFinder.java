package nts.uk.ctx.pr.proto.app.find.personalinfo.commute;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;

import nts.uk.ctx.pr.proto.app.find.paymentdata.CommuteNotaxLimitDto;
import nts.uk.ctx.pr.proto.app.find.paymentdata.CommuteNotaxLimitFinder;
import nts.uk.ctx.pr.proto.dom.personalinfo.commute.PersonalCommuteFee;
import nts.uk.ctx.pr.proto.dom.personalinfo.commute.PersonalCommuteFeeRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class CommuteFinder {
	
	@Inject
	private PersonalCommuteFeeRepository repository;
	@Inject
	private CommuteNotaxLimitFinder commuteNotaxLimitFinder;
	
	public CommuteNotaxLimitDto find(String personId, int startYearMonth){
		String companyCode = AppContexts.user().companyCode(); 
		
		List<PersonalCommuteFee> list = this.repository.findAll(companyCode, personId, startYearMonth);
		
		if (list.isEmpty()) {
			return null;
		}
		
		PersonalCommuteFee personalCommute = list.get(0);
		
		Optional<CommuteNotaxLimitDto> comuteOpt = commuteNotaxLimitFinder.find(personalCommute.getCommuteNoTaxLimitPublishNo());
		
		return comuteOpt.get();
	}
}
