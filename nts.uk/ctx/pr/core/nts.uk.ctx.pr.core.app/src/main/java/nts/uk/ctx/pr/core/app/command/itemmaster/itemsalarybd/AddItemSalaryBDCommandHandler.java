package nts.uk.ctx.pr.core.app.command.itemmaster.itemsalarybd;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalarybd.ItemSalaryBDRepository;
import nts.uk.shr.com.context.AppContexts;

@Stateless
@Transactional
public class AddItemSalaryBDCommandHandler extends CommandHandler<AddItemSalaryBDCommand> {

	@Inject
	private ItemSalaryBDRepository itemSalaryBDRepository;

	@Override
	protected void handle(CommandHandlerContext<AddItemSalaryBDCommand> context) {
		val itemCode = context.getCommand().getItemCode();
		val itemBreakdownCode = context.getCommand().getItemBreakdownCode();
		String companyCode = AppContexts.user().companyCode();
		if (this.itemSalaryBDRepository.find(companyCode, itemCode, itemBreakdownCode).isPresent())
			throw new BusinessException(" 明細書名が入力されていません。");
		this.itemSalaryBDRepository.add(companyCode, context.getCommand().toDomain());
	}
}
