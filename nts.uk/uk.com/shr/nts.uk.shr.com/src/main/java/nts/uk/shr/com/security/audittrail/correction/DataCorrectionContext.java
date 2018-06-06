package nts.uk.shr.com.security.audittrail.correction;

import java.io.Serializable;

import javax.enterprise.inject.spi.CDI;

import lombok.val;
import nts.gul.text.IdentifierUtil;

public class DataCorrectionContext {

	private static ThreadLocal<DataCorrectionContext> contextThreadLocal = new ThreadLocal<>();
	
	private final CorrectionProcessorId processorId;

	private final String operationId;
	
	private Serializable parameter;
	
	private DataCorrectionContext(CorrectionProcessorId processorId, String operationId) {
		this.processorId = processorId;
		this.operationId = operationId;
		this.parameter = null;
	}
	
	public static void transactinBeginning(CorrectionProcessorId processorId) {
		contextThreadLocal.set(new DataCorrectionContext(
				processorId,
				IdentifierUtil.randomUniqueId()));
	}
	
	public static void setParameter(Serializable parameter) {
		val context = getCurrentContext();
		context.parameter = parameter;
	}
	
	public static void transactionCommited() {
		val context = getCurrentContext();
		val agent = CDI.current().select(CorrectionLoggingAgent.class).get();
		agent.requestProcess(context.operationId, context.processorId, context.parameter);
		
		contextThreadLocal.set(null);
	}
	
	private static DataCorrectionContext getCurrentContext() {
		val context = contextThreadLocal.get();
		
		if (context == null) {
			throw new RuntimeException("the current context has not been started.");
		}
		
		return context;
	}
	
}
