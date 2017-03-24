package nts.uk.ctx.pr.core.app.command.itemmaster.itemdeductbd;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.transaction.Transactional;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.pr.core.dom.itemmaster.itemdeductbd.ItemDeductBDRepository;

@Stateless
@Transactional
public class DeleteItemDeductBDCommandHandler extends CommandHandler<DeleteItemDeductBDCommand> {
	@Inject
	ItemDeductBDRepository itemDeductBDRepository;

	@Override
	protected void handle(CommandHandlerContext<DeleteItemDeductBDCommand> context) {
		// TODO Auto-generated method stub
		val itemCd = context.getCommand().getItemCd();
		val itemBreakdownCd = context.getCommand().getItemBreakdownCd();
		if (!this.itemDeductBDRepository.find(itemCd, itemBreakdownCd).isPresent())
			throw new BusinessException(new RawErrorMessage(" 明細書名が入力されていません。"));
		this.itemDeductBDRepository.delete(itemCd, itemBreakdownCd);
	}
}
