package nts.uk.ctx.exio.app.command.exo.condset;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class CopyOutputCondSetCommandHandler extends CommandHandler<CopyOutCondSet>{

	@Inject
	private StdOutputCondSetService stdOutputCondSetService;
	
	@Override
	protected void handle(CommandHandlerContext<CopyOutCondSet> context) {
		String cId = AppContexts.user().companyId();
		CopyOutCondSet copy = context.getCommand();
		int standType = copy.getStandType();
		String cndSetCode = copy.getConditionSetCode();
		StdOutputCondSet copyParams = new StdOutputCondSet(cId, copy.getDestinationCode(), copy.getCategoryId(), copy.getDelimiter(), copy.getItemOutputName(),
				copy.getAutoExecution(), copy.getDestinationName(), copy.getConditionOutputName(), copy.getStringFormat());
		stdOutputCondSetService.copy(standType, cndSetCode, copyParams);
		
	}

	

}
