package nts.uk.shr.com.security.audittrail.correction.processor.pereg;

import java.io.Serializable;
import java.util.HashMap;

import lombok.val;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionLogProcessor;

/**
 * The base class to log audit trail of corrections.
 */
public abstract class PeregCorrectionLogProcessor extends CorrectionLogProcessor<PeregCorrectionLogProcessorContext> {

	@Override
	public void processLoggingForBus(String operationId, Object parameter) {
		
		@SuppressWarnings("unchecked")
		HashMap<String, Serializable> parameters = (HashMap<String, Serializable>) parameter;
		
		val context = PeregCorrectionLogProcessorContext.newContext(operationId, parameters);
		this.buildLogContents(context);
		
		// TODO: write corrections
		context.getCorrections();
	}
}
