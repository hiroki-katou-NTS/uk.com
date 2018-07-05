package nts.uk.shr.com.security.audittrail.correction;

import java.io.Serializable;

import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionProcessorId;

public interface CorrectionLoggingAgent {

	void requestProcess(String operationId, CorrectionProcessorId processorId, Serializable parameter);
}
