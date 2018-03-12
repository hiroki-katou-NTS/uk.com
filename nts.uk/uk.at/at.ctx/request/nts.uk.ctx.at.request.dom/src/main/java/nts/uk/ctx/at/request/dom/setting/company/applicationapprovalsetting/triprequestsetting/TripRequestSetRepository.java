package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.triprequestsetting;

import java.util.Optional;

public interface TripRequestSetRepository {
	/**
	 * find trip request set by companyId
	 * @return
	 * @author yennth
	 */
	Optional<TripRequestSet> findByCid();
	/**
	 * update trip request set 
	 * @param tripRequest
	 * @author yennth
	 */
	void update(TripRequestSet tripRequest);
	/**
	 * insert trip request set
	 * @param tripRequest
	 * @author yennth
	 */
	void insert(TripRequestSet tripRequest);
}
