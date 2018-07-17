package nts.uk.shr.com.security.audittrail.correction.processor;

import lombok.val;

/**
 * The base class to log audit trail of corrections.
 */

public abstract class DataCorrectionLogProcessor extends CorrectionLogProcessor<CorrectionLogProcessorContext> {

	public void processLoggingForBus(String operationId, Object parameter) {
		val context = CorrectionLogProcessorContext.newContext(operationId, parameter);
		this.buildLogContents(context);
		
		// TODO: write corrections
		context.getCorrections();
	}
}
