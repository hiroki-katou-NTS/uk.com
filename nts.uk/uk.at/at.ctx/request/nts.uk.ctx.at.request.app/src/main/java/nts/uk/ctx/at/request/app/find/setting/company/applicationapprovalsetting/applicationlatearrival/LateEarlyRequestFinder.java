package nts.uk.ctx.at.request.app.find.setting.company.applicationapprovalsetting.applicationlatearrival;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyRequest_Old;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationlatearrival.LateEarlyRequestRepository;

/**
 * TanLV
 *
 */
@Stateless
public class LateEarlyRequestFinder {
	@Inject
	private LateEarlyRequestRepository repository;
	
	/**
	 * TanLV
	 * @return
	 */
	public LateEarlyRequestDto findByCompanyID() {
		Optional<LateEarlyRequest_Old> data = repository.getLateEarlyRequest();
		
		if(data.isPresent()){
			return LateEarlyRequestDto.fromDomain(data.get());
		}
		
		return null;
	}
}
