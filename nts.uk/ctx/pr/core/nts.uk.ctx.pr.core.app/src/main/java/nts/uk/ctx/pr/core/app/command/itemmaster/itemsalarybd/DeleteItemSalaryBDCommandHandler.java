package nts.uk.ctx.pr.core.app.command.itemmaster.itemsalarybd;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.itemmaster.itemsalarybd.ItemSalaryBDRepository;
@Stateless
@Transactional
public class DeleteItemSalaryBDCommandHandler extends CommandHandler<DeleteItemSalaryBDCommand> {

	@Inject
	private ItemSalaryBDRepository itemSalaryBDRepository;

	@Override
	protected void handle(CommandHandlerContext<DeleteItemSalaryBDCommand> context) {
		
		val itemCd = context.getCommand().getItemCd();
		val itemBreakdownCd = context.getCommand().getItemBreakdownCd();
		if(!this.itemSalaryBDRepository.find(itemCd,itemBreakdownCd).isPresent())
			throw new BusinessException(new RawErrorMessage(" 明細書名が入力されていません。"));
		this.itemSalaryBDRepository.delete(itemCd,itemBreakdownCd);
	}

}
