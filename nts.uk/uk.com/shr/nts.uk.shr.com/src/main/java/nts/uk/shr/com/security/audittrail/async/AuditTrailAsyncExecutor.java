package nts.uk.shr.com.security.audittrail.async;

import java.io.Serializable;
import java.util.HashMap;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.inject.spi.CDI;
import javax.inject.Inject;

import lombok.val;
import nts.arc.task.AsyncTask;
import nts.arc.task.AsyncTaskService;
import nts.uk.shr.com.security.audittrail.AuditTrailTransaction;
import nts.uk.shr.com.security.audittrail.correction.CorrectionLoggingAgent;
import nts.uk.shr.com.security.audittrail.correction.DataCorrectionContext;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionProcessorId;

@ApplicationScoped
public class AuditTrailAsyncExecutor implements CorrectionLoggingAgent {

	/**
	 * AsyncTaskService
	 */
	@Inject
	private AsyncTaskService asyncTaskService;
	
	@Override
	public void requestProcess(String operationId, CorrectionProcessorId processorId, HashMap<String, Serializable> parameters) {
		
		val task = AsyncTask.builder()
				.withContexts()
				.keepsTrack(false)
				.build(() -> {
					val auditTrailTransaction = CDI.current().select(AuditTrailTransaction.class).get();
					auditTrailTransaction.begin(operationId, processorId, parameters);
				});
		
		this.asyncTaskService.execute(task);
	}

}
