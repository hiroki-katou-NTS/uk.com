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
public class BPTimeItemUpdateCommandhandler extends CommandHandler<List<BPTimeItemUpdateCommand>> {
	@Inject
	private BPTimeItemRepository bpTimeItemRepository;
	@Override
	protected void handle(CommandHandlerContext<List<BPTimeItemUpdateCommand>> context) {
		List<BPTimeItemUpdateCommand> lstBpTimeItemUpdateCommand = context.getCommand();
		String companyId = AppContexts.user().companyId();
		bpTimeItemRepository.updateListBonusPayTimeItem(lstBpTimeItemUpdateCommand.stream().map(c -> toBonusPayTimeItemDomain(c,companyId)).collect(Collectors.toList()));
	}
	private BonusPayTimeItem toBonusPayTimeItemDomain(BPTimeItemUpdateCommand bpTimeItemUpdateCommand,String companyId ) {
		return BonusPayTimeItem.createFromJavaType(companyId,
				bpTimeItemUpdateCommand.getUseAtr(),
				bpTimeItemUpdateCommand.getTimeItemName(), bpTimeItemUpdateCommand.getTimeItemNo(),
				bpTimeItemUpdateCommand.getTimeItemTypeAtr());

	}

}
