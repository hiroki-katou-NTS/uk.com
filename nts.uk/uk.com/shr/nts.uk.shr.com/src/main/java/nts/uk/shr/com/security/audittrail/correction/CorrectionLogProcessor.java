package nts.uk.shr.com.security.audittrail;

import javax.inject.Inject;

public abstract class AuditTrailLogger<P> {

	@Inject
	protected UserInfoAdaptorForLog userInfoAdaptor;
	
	public void startLogging(P parameter) {
		
	}
	
	protected abstract void processLogging(P parameter);
	
	
}
