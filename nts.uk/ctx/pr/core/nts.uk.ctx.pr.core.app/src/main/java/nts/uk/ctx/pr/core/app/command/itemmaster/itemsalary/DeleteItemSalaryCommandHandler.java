package nts.uk.ctx.pr.core.app.command.itemmaster.itemsalary;

import javax.enterprise.context.RequestScoped;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ItemSalaryRespository;
import nts.uk.shr.com.context.AppContexts;

@RequestScoped
@Transactional
public class DeleteItemSalaryCommandHandler extends CommandHandler<DeleteItemSalaryCommand> {

	@Inject
	ItemSalaryRespository itemSalaryRespository;

	@Override
	protected void handle(CommandHandlerContext<DeleteItemSalaryCommand> context) {
		// TODO Auto-generated method stub
		val companyCode = AppContexts.user().companyCode();
		this.itemSalaryRespository.delete(companyCode, context.getCommand().getItemCd());

	}

}
