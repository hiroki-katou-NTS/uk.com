package nts.uk.ctx.sys.assist.ac.mastercopy;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.sys.assist.dom.mastercopy.InitValueAuthManagementAdapter;
import nts.uk.ctx.sys.auth.pub.event.InitValueAuthGlobalEventPublisher;

/**
 * The Class InitValueAuthManagementAdapterImpl.
 */
@Stateless
public class InitValueAuthManagementAdapterImpl implements InitValueAuthManagementAdapter {
	
	@Inject
	private InitValueAuthGlobalEventPublisher initValueAuthPub;

	/* (non-Javadoc)
	 * @see nts.uk.ctx.sys.assist.dom.mastercopy.InitValueAuthManagementAdapter#initValueAuth(java.lang.String)
	 */
	@Override
	public void initValueAuth(String companyIDCopy) {
		initValueAuthPub.initRole(companyIDCopy);
	}

}
