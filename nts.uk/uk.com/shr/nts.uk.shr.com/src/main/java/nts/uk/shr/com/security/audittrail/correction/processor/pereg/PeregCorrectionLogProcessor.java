package nts.uk.shr.com.security.audittrail.correction.processor.pereg;

import java.io.Serializable;
import java.util.HashMap;

import javax.inject.Inject;

import lombok.val;
import nts.uk.shr.com.security.audittrail.basic.LogBasicInformation;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionLogProcessor;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionProcessorId;
import nts.uk.shr.com.security.audittrail.correction.processor.LogBasicInformationWriter;

public class PeregCorrectionLogProcessor extends CorrectionLogProcessor<PeregCorrectionLogProcessorContext> {

	@Inject
	protected LogBasicInformationWriter basicInfoRepository;
	
	@Inject
	protected PeregCorrectionLogWriter correctionLogRepository;
	
	
	@Override
	public CorrectionProcessorId getId() {
		return CorrectionProcessorId.PEREG_REGISTER;
	}

	@Override
	protected void buildLogContents(PeregCorrectionLogProcessorContext context) {
		// xử lý PeregCorrectionLogParameter để chuyển thành domain
		// PersonInfoCorrectionLog ở đây
	}

	@Override
	public void processLoggingForBus(LogBasicInformation basicInfo, Object parameter) {
		
		@SuppressWarnings("unchecked")
		HashMap<String, Serializable> parameters = (HashMap<String, Serializable>) parameter;
		
		val context = PeregCorrectionLogProcessorContext.newContext(basicInfo.getOperationId(), parameters);
		this.buildLogContents(context);

		this.basicInfoRepository.save(basicInfo);
		this.correctionLogRepository.save(context.getCorrections());
	}
}
