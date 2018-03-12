package nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.withdrawalrequestset;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.withdrawalrequestset.WithDrawalReqSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.withdrawalrequestset.WithDrawalReqSetRepository;

@Stateless
public class WithDrawalReqSetFinder {
	@Inject
	private WithDrawalReqSetRepository repository;
	
	/**
	 * TanLV
	 * @return
	 */
	public WithDrawalReqSetDto findByCompanyID() {
		Optional<WithDrawalReqSet> data = repository.getWithDrawalReqSet();
		if(data.isPresent()){
			return WithDrawalReqSetDto.fromDomain(data.get());
		}
		return null;
	}
}
