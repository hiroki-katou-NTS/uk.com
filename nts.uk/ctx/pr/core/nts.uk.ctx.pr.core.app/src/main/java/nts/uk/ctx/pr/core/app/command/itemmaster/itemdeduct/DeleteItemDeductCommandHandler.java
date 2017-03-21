package nts.uk.ctx.pr.core.app.command.itemmaster.itemdeduct;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeduct.ItemDeductRespository;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
@Transactional
public class DeleteItemDeductCommandHandler extends CommandHandler<DeleteItemDeductCommand> {

	@Inject
	ItemDeductRespository itemDeductRespository;

	@Override
	protected void handle(CommandHandlerContext<DeleteItemDeductCommand> context) {
		val companyCode = AppContexts.user().companyCode();
		this.itemDeductRespository.delete(companyCode, context.getCommand().getItemCd());

	}

}
