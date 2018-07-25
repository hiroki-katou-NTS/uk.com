package nts.uk.ctx.exio.app.command.exo.condset;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.exio.dom.exo.condset.StandardAtr;
import nts.uk.ctx.exio.dom.exo.condset.StdOutputCondSetService;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class OutSetContentCommandHandler extends CommandHandler<StdOutputCondSetCommand>{

	@Inject
	private StdOutputCondSetService stdOutputCondSetService;
	
	@Override
	protected void handle(CommandHandlerContext<StdOutputCondSetCommand> context) {
		String userId = AppContexts.user().userId();
		String condSetCd = context.getCommand().getConditionSetCd();
		String outItemCd = new String ();
		StandardAtr standType =  EnumAdaptor.valueOf(context.getCommand().getStandType(), StandardAtr.class);
		stdOutputCondSetService.outputAcquisitionItemList(condSetCd, userId, outItemCd, standType, false);
	}

}
