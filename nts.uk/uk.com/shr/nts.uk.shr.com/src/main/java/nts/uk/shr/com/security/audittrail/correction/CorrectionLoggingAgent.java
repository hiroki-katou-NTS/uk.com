package nts.uk.shr.com.security.audittrail.correction;

import java.io.Serializable;

public interface CorrectionLoggingAgent {

	void requestProcess(String operationId, CorrectionProcessorId processorId, Serializable parameter);
}
