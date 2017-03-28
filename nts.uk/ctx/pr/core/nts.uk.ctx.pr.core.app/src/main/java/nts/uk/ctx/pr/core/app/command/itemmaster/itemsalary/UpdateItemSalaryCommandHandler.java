package nts.uk.ctx.pr.core.app.command.itemmaster.itemsalary;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalary.ItemSalaryRespository;

@Stateless
@Transactional
public class UpdateItemSalaryCommandHandler extends CommandHandler<UpdateItemSalaryCommand> {

	@Inject
	private ItemSalaryRespository itemSalaryRespository;

	@Override
	protected void handle(CommandHandlerContext<UpdateItemSalaryCommand> context) {
		if (!this.itemSalaryRespository.find(context.getCommand().getCcd(), context.getCommand().getItemCd()).isPresent())
			throw new BusinessException(new RawErrorMessage(" 明細書名が入力されていません。"));
		this.itemSalaryRespository.update(context.getCommand().toDomain());

	}

}
