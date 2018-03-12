package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.hdapplicationsetting;

import java.util.Optional;
/**
 * time holiday app set interface
 * @author yennth
 *
 */
public interface TimeHdAppSetRepository {
	/**
	 * get time holiday app set by companyId
	 * @return
	 * @author yennth
	 */
	Optional<TimeHdAppSet> getByCid();
	/**
	 * update time holiday app set
	 * @param timeHd
	 * @author yennth
	 */
	void update(TimeHdAppSet timeHd);
	/**
	 * insert time holiday app set
	 * @param timeHd
	 * @author yennth
	 */
	void insert(TimeHdAppSet timeHd);
}
