package nts.uk.ctx.at.shared.app.command.bonuspay;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.repository.BPTimeItemRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.bonuspay.timeitem.BonusPayTimeItem;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class BPTimeItemAddCommandHandler extends CommandHandler<List<BPTimeItemAddCommand>> {
	@Inject
	private BPTimeItemRepository bpTimeItemRepository;

	@Override
	protected void handle(CommandHandlerContext<List<BPTimeItemAddCommand>> context) {
		List<BPTimeItemAddCommand> lstBPTimeItemAddCommand = context.getCommand();
		String companyId = AppContexts.user().companyId();
		bpTimeItemRepository.addListBonusPayTimeItem(
				lstBPTimeItemAddCommand.stream().map(c -> toBonusPayTimeItemDomain(c,companyId)).collect(Collectors.toList()));
	}

	private BonusPayTimeItem toBonusPayTimeItemDomain(BPTimeItemAddCommand bpTimeItemAddCommand, String companyId) {
		return BonusPayTimeItem.createFromJavaType(companyId, bpTimeItemAddCommand.getUseAtr(),
				bpTimeItemAddCommand.getTimeItemName(), bpTimeItemAddCommand.getTimeItemNo(),
				bpTimeItemAddCommand.getTimeItemTypeAtr());

	}

}
