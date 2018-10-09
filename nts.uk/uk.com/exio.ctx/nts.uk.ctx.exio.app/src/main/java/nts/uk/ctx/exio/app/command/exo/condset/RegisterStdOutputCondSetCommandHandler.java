package nts.uk.ctx.exio.app.command.exo.condset;


import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.commonalgorithm.RegisterMode;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSet;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetService;
import nts.uk.ctx.exio.dom.exo.outputitemorder.StandardOutputItemOrder;
import nts.uk.shr.com.context.AppContexts;

@Transactional
@Stateless
public class RegisterStdOutputCondSetCommandHandler extends CommandHandler<StdOutputCondSetCommand>{
	
	@Inject
	private StdOutputCondSetService stdOutputCondSetService;
	
	@Override
	protected void handle(CommandHandlerContext<StdOutputCondSetCommand> context) {
		
		StdOutputCondSetCommand command = context.getCommand();
		RegisterMode mode = RegisterMode.NEW;
		
		if(!command.isNewMode()) {
			mode = RegisterMode.UPDATE;
		}
		
		int standType = command.getStandType();
		String cId = AppContexts.user().companyId();
		StdOutputCondSet stdOutputCondSet = new StdOutputCondSet(cId, command.getConditionSetCd(),
				command.getCategoryId(), command.getDelimiter(), command.getItemOutputName(),
				command.getAutoExecution(), command.getConditionSetName(), command.getConditionOutputName(),
				command.getStringFormat());
		List<StandardOutputItemOrder> listStandardOutputItemOrder = command.getListStandardOutputItem().stream().map(item -> {
            return new StandardOutputItemOrder(cId ,item.getOutItemCd(), item.getCondSetCd(), item.getOrder());
        }).collect(Collectors.toList());
		stdOutputCondSetService.registerOutputSet(mode, standType, stdOutputCondSet, listStandardOutputItemOrder);
	}
	
}
