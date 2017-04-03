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
public class UpdateItemDeductBDCommandHandler extends CommandHandler<UpdateItemDeductBDCommand> {

	@Inject
	private ItemDeductBDRepository itemDeductBDRepo;

	@Override
	protected void handle(CommandHandlerContext<UpdateItemDeductBDCommand> context) {

		val itemCode = context.getCommand().getItemCode();
		val itemBreakdownCd = context.getCommand().getItemBreakdownCd();
		if (!this.itemDeductBDRepo.find(itemCode, itemBreakdownCd).isPresent())
			throw new BusinessException(new RawErrorMessage(" 明細書名が入力されていません。"));
		this.itemDeductBDRepo.update(context.getCommand().toDomain());
	}
}
