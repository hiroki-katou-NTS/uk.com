package nts.uk.shr.com.security.audittrail.correction.processor;

import javax.inject.Inject;

import lombok.val;
import nts.uk.shr.com.security.audittrail.UserInfoAdaptorForLog;
import nts.uk.shr.com.security.audittrail.basic.LogBasicInformation;

/**
 * The base class to log audit trail of corrections.
 */
public abstract class CorrectionLogProcessor {

	/** userInfoAdaptorForLog */
	@Inject
	protected UserInfoAdaptorForLog userInfoAdaptor;
	
	@Inject
	private LogBasicInformationWriter basicInfoRepository;
	
	@Inject
	private DataCorrectionLogWriter correctionLogRepository;

	/**
	 * Returns AuditTrailProcessorId.
	 * @return AuditTrailProcessorId
	 */
	public abstract CorrectionProcessorId getId();
	
	/**
	 * Process logging.
	 * @param parameter parameter object
	 */
	protected abstract void buildLogContents(CorrectionLogProcessorContext context);
	
	public void processLoggingForBus(LogBasicInformation basicInfo, Object parameter) {
		val context = CorrectionLogProcessorContext.newContext(basicInfo, parameter);
		this.buildLogContents(context);

		this.basicInfoRepository.save(basicInfo);
		this.correctionLogRepository.save(context.getCorrections());
	}
}
