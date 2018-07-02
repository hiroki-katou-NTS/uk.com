package nts.uk.shr.com.security.audittrail.correction;

import java.io.Serializable;

import javax.enterprise.inject.spi.CDI;

import lombok.val;
import nts.gul.text.IdentifierUtil;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionProcessorId;

/**
 * データ修正記録のコンテキスト情報
 */
public class DataCorrectionContext {

	private static ThreadLocal<DataCorrectionContext> contextThreadLocal = new ThreadLocal<>();
	
	/** Processor ID */
	private final CorrectionProcessorId processorId;

	/** 操作ID */
	private final String operationId;
	
	/** パラメータ */
	private Serializable parameter;
	
	private DataCorrectionContext(CorrectionProcessorId processorId, String operationId) {
		this.processorId = processorId;
		this.operationId = operationId;
		this.parameter = null;
	}
	
	/**
	 * This method must be called when transaction begin.
	 * @param processorId Processor ID
	 */
	public static void transactionBegun(CorrectionProcessorId processorId) {
		contextThreadLocal.set(new DataCorrectionContext(
				processorId,
				IdentifierUtil.randomUniqueId()));
	}
	
	/**
	 * Set a parameter to be passed to Correction Log Processor.
	 * @param parameter
	 */
	public static void setParameter(Serializable parameter) {
		val context = getCurrentContext();
		context.parameter = parameter;
	}
	
	/**
	 * This method must be called when transaction finish.
	 */
	public static void transactionFinishing() {
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
