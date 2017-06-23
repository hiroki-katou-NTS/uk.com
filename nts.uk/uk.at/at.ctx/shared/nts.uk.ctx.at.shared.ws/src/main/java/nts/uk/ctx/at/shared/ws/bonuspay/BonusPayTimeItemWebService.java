package nts.uk.ctx.at.shared.ws.bonuspay;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.BPTimeItemAddCommand;
import nts.uk.ctx.at.shared.app.command.BPTimeItemAddCommandHandler;
import nts.uk.ctx.at.shared.app.command.BPTimeItemUpdateCommand;
import nts.uk.ctx.at.shared.app.command.BPTimeItemUpdateCommandhandler;
import nts.uk.ctx.at.shared.app.find.bonuspay.BPTimeItemDto;
import nts.uk.ctx.at.shared.app.find.bonuspay.BPTimeItemFinder;

@Path("at/share/bonusPayTimeItem")
@Produces("application/json")
public class BonusPayTimeItemWebService extends WebService {
	@Inject
	private BPTimeItemAddCommandHandler bpTimeItemAddCommandHandler;
	@Inject
	private BPTimeItemUpdateCommandhandler bpTimeItemUpdateCommandhandler;
	@Inject
	private BPTimeItemFinder bpTimeItemFinder;

	@POST
	@Path("getListBonusPayTimeItem")
	List<BPTimeItemDto> getListBonusPayTimeItem() {
		return bpTimeItemFinder.getListBonusPayTimeItem();
	}

	@POST
	@Path("getListSpecialBonusPayTimeItem")
	List<BPTimeItemDto> getListSpecialBonusPayTimeItem() {
		return bpTimeItemFinder.getListSpecialBonusPayTimeItem();
	}

	@POST
	@Path("addListBonusPayTimeItem")
	void addListBonusPayTimeItem(List<BPTimeItemAddCommand> lstTimeItem) {
		bpTimeItemAddCommandHandler.handle(lstTimeItem);
	}

	@POST
	@Path("updateListBonusPayTimeItem")
	void updateListBonusPayTimeItem(List<BPTimeItemUpdateCommand> lstTimeItem) {
		bpTimeItemUpdateCommandhandler.handle(lstTimeItem);
	}
}
