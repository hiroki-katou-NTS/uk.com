package nts.uk.shr.sample.audittrail.correction;

import java.util.ArrayList;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.shr.com.security.audittrail.correction.CorrectionProcessorId;
import nts.uk.shr.com.security.audittrail.correction.DataCorrectionContext;

@Stateless
public class SampleCorrectionLogCommandHandler extends CommandHandler<SampleCorrectionLogCommand> {
	
	@Override
	protected void preHandle(CommandHandlerContext<SampleCorrectionLogCommand> context) {
		super.preHandle(context);
		DataCorrectionContext.transactinBeginning(CorrectionProcessorId.SAMPLE);
	}
	
	@Override
	protected void handle(CommandHandlerContext<SampleCorrectionLogCommand> context) {
		
		val correctionLogParameter = new SampleCorrectionLogParameter(new ArrayList<>());
		DataCorrectionContext.setParameter(correctionLogParameter);
		
	}

	@Override
	protected void postHandle(CommandHandlerContext<SampleCorrectionLogCommand> context) {
		super.postHandle(context);
		DataCorrectionContext.transactionCommited();
	}

}
