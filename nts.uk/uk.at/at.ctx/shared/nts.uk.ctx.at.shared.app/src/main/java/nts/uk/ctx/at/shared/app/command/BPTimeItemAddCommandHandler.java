package nts.uk.ctx.at.shared.app.command;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.bonuspay.repository.BPTimeItemRepository;
import nts.uk.ctx.at.shared.dom.bonuspay.timeitem.BonusPayTimeItem;

@Stateless
public class BPTimeItemAddCommandHandler extends CommandHandler<List<BPTimeItemAddCommand>> {
	@Inject
	private BPTimeItemRepository bpTimeItemRepository;

	@Override
	protected void handle(CommandHandlerContext<List<BPTimeItemAddCommand>> context) {
		List<BPTimeItemAddCommand> lstBPTimeItemAddCommand = context.getCommand();
		bpTimeItemRepository.addListBonusPayTimeItem(
				lstBPTimeItemAddCommand.stream().map(c -> toBonusPayTimeItemDomain(c)).collect(Collectors.toList()));
	}

	private BonusPayTimeItem toBonusPayTimeItemDomain(BPTimeItemAddCommand bpTimeItemAddCommand) {
		return BonusPayTimeItem.createFromJavaType(bpTimeItemAddCommand.getCompanyId(),
				bpTimeItemAddCommand.getTimeItemId(), bpTimeItemAddCommand.getUseAtr(),
				bpTimeItemAddCommand.getTimeItemName(), bpTimeItemAddCommand.getId(),
				bpTimeItemAddCommand.getTimeItemTypeAtr());

	}

}
