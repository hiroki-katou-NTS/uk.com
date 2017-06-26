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
public class BPTimeItemUpdateCommandhandler extends CommandHandler<List<BPTimeItemUpdateCommand>> {
	@Inject
	private BPTimeItemRepository bpTimeItemRepository;
	@Override
	protected void handle(CommandHandlerContext<List<BPTimeItemUpdateCommand>> context) {
		List<BPTimeItemUpdateCommand> lstBpTimeItemUpdateCommand = context.getCommand();
		bpTimeItemRepository.updateListBonusPayTimeItem(lstBpTimeItemUpdateCommand.stream().map(c -> toBonusPayTimeItemDomain(c)).collect(Collectors.toList()));
	}
	private BonusPayTimeItem toBonusPayTimeItemDomain(BPTimeItemUpdateCommand bpTimeItemUpdateCommand) {
		return BonusPayTimeItem.createFromJavaType(bpTimeItemUpdateCommand.getCompanyId(),
				bpTimeItemUpdateCommand.getTimeItemId(), bpTimeItemUpdateCommand.getUseAtr(),
				bpTimeItemUpdateCommand.getTimeItemName(), bpTimeItemUpdateCommand.getId(),
				bpTimeItemUpdateCommand.getTimeItemTypeAtr());

	}

}
