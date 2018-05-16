package nts.uk.shr.com.security.audittrail.correction;

import javax.inject.Inject;

import lombok.val;
import nts.uk.shr.com.security.audittrail.UserInfoAdaptorForLog;

/**
 * The base class to log audit trail of corrections.
 */
public abstract class CorrectionLogProcessor {

	/** userInfoAdaptorForLog */
	@Inject
	protected UserInfoAdaptorForLog userInfoAdaptor;

	/**
	 * Returns AuditTrailProcessorId.
	 * @return AuditTrailProcessorId
	 */
	public abstract CorrectionProcessorId getId();
	
	/**
	 * Process logging.
	 * @param parameter parameter object
	 */
	protected abstract void buildLogContents(DataCorrectionLoggingContext context);
	
	public void processLoggingForBus(String operationId, Object parameter) {
		val context = DataCorrectionLoggingContext.newContext(operationId, parameter);
		this.buildLogContents(context);
		
		context.getCorrections();
	}
}
