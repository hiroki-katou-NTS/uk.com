package nts.uk.ctx.pereg.app.command.log;

import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionProcessorId;
import nts.uk.shr.com.security.audittrail.correction.processor.pereg.PeregCorrectionLogProcessor;
import nts.uk.shr.com.security.audittrail.correction.processor.pereg.PeregCorrectionLogProcessorContext;

public class PeregCorrectionLogProcessorExt extends PeregCorrectionLogProcessor {

	@Override
	public CorrectionProcessorId getId() {
		return CorrectionProcessorId.PEREG_REGISTER;
	}

	@Override
	protected void buildLogContents(PeregCorrectionLogProcessorContext context) {

	}
}
