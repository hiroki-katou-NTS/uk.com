package nts.uk.ctx.pr.core.app.command.wageprovision.statementitem;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.breakdownitemset.BreakdownItemCode;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.breakdownitemset.BreakdownItemSet;
import nts.uk.ctx.pr.core.dom.wageprovision.statementitem.breakdownitemset.BreakdownItemSetRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class AddBreakdownItemSetCommandHandler extends CommandHandler<BreakdownItemSetCommand> {

	@Inject
	private BreakdownItemSetRepository breakdownItemSetRepository;

	@Override
	protected void handle(CommandHandlerContext<BreakdownItemSetCommand> context) {
		String cid = AppContexts.user().companyId();
		BreakdownItemSetCommand command = context.getCommand();
		BreakdownItemCode code = new BreakdownItemCode(command.getBreakdownItemCode());

		BreakdownItemSet breakdownItemSet = new BreakdownItemSet(cid, command.getCategoryAtr(), command.getItemNameCd(),
				command.getBreakdownItemCode(), command.getBreakdownItemName());
        if(breakdownItemSetRepository.getBreakdownItemStById(
        		cid, command.getCategoryAtr(), command.getItemNameCd(), code.v()).isPresent()){
        	throw new BusinessException("Msg_3");
        }
		breakdownItemSetRepository.add(breakdownItemSet);
	}

}
