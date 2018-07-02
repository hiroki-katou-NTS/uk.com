package nts.uk.shr.com.security.audittrail.correction;

import java.io.Serializable;

import javax.enterprise.inject.spi.CDI;

import lombok.val;
import nts.gul.text.IdentifierUtil;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionProcessorId;

public class DataCorrectionContext {

	private static ThreadLocal<DataCorrectionContext> contextThreadLocal = new ThreadLocal<>();
	
	private final CorrectionProcessorId processorId;

	private final String operationId;
	
	private Serializable parameter;
	
	private boolean isAborted;
	
	private DataCorrectionContext(CorrectionProcessorId processorId, String operationId) {
		this.processorId = processorId;
		this.operationId = operationId;
		this.parameter = null;
	}
	
	public static void transactionBegun(CorrectionProcessorId processorId) {
		contextThreadLocal.set(new DataCorrectionContext(
				processorId,
				IdentifierUtil.randomUniqueId()));
	}
	
	public static void setParameter(Serializable parameter) {
		val context = getCurrentContext();
		context.parameter = parameter;
	}
	
	/**
	 * Exceptionをthrowして止めるのではない場合、これを明示的に呼び、transactionFinishingの処理を抑制する必要がある。
	 */
	public static void transactionAborted() {
		getCurrentContext().isAborted = true;
	}
	
	public static void transactionFinishing() {
		val context = getCurrentContext();
		if (!context.isAborted) {
			val agent = CDI.current().select(CorrectionLoggingAgent.class).get();
			agent.requestProcess(context.operationId, context.processorId, context.parameter);
		}
		
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
