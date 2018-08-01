package nts.uk.shr.sample.audittrail.correction;

import java.util.ArrayList;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.shr.com.security.audittrail.correction.DataCorrectionContext;
import nts.uk.shr.com.security.audittrail.correction.processor.CorrectionProcessorId;

@Stateless
public class SampleCorrectionLogCommandHandler extends CommandHandler<SampleCorrectionLogCommand> {
	
	@Override
	protected void handle(CommandHandlerContext<SampleCorrectionLogCommand> context) {
		
		DataCorrectionContext.transactionBegun(CorrectionProcessorId.SAMPLE);
		
		val correctionLogParameter = new SampleCorrectionLogParameter(new ArrayList<>());
		DataCorrectionContext.setParameter(correctionLogParameter);
		
		DataCorrectionContext.transactionFinishing();
		
	}

	// transactionFinishingはhandleの中に実装しても良いが、
	// handleの途中でreturnする場合でも実行する必要があるので、postHandleに記述するとミスが少ないはず
	@Override
	protected void postHandle(CommandHandlerContext<SampleCorrectionLogCommand> context) {
		super.postHandle(context);
		DataCorrectionContext.transactionFinishing();
	}

}
