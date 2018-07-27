package nts.uk.shr.com.security.audittrail;

import java.io.Serializable;

import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionProcessorId;

public interface AuditTrailTransaction {

	void begin(String operationId, CorrectionProcessorId processorId, Serializable parameter);
}
