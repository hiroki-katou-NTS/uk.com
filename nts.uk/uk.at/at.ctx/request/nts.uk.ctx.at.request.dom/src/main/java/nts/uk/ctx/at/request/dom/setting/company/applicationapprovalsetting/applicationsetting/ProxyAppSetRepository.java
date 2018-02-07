package nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting;

import java.util.List;
import java.util.Optional;
/**
 * the proxy app set interface
 * @author yennth
 *
 */
public interface ProxyAppSetRepository {
	/**
	 * get all proxy app set
	 * @return
	 * @author yennth
	 */
	List<ProxyAppSet> getAllProxy();
	/**
	 * get proxy app set by key
	 * @return
	 * @author yennth
	 */
	Optional<ProxyAppSet> getProxy(int appType);
	/**
	 * insert proxy app set
	 * @param proxy
	 * @author yennth
	 */
	void insert (ProxyAppSet proxy);
	void delete (String companyId);
}
