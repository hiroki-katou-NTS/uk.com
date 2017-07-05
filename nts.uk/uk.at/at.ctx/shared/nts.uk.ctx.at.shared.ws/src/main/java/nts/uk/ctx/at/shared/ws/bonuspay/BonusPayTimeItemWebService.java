package nts.uk.ctx.at.shared.ws.bonuspay;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
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
	public List<BPTimeItemDto> getListBonusPayTimeItem() {
		return this.bpTimeItemFinder.getListBonusPayTimeItem();
	}

	@POST
	@Path("getListSpecialBonusPayTimeItem")
	public List<BPTimeItemDto> getListSpecialBonusPayTimeItem() {
		return this.bpTimeItemFinder.getListSpecialBonusPayTimeItem();
	}
	@POST
	@Path("getBonusPayTimeItem/{timeItemId}")
	public BPTimeItemDto getBonusPayTimeItem(@PathParam("timeItemId") String timeItemId){
		return this.bpTimeItemFinder.getBonusPayTimeItem(timeItemId);
	}
	@POST
	@Path("getSpecialBonusPayTimeItem/{timeItemNo}")
	public BPTimeItemDto getSpecialBonusPayTimeItem(@PathParam("timeItemId") String timeItemId){
		return this.bpTimeItemFinder.getSpecialBonusPayTimeItem(timeItemId);
	}
	
	@POST
	@Path("addListBonusPayTimeItem")
	public void addListBonusPayTimeItem(List<BPTimeItemAddCommand> lstTimeItem) {
		this.bpTimeItemAddCommandHandler.handle(lstTimeItem);
	}

	@POST
	@Path("updateListBonusPayTimeItem")
	public void updateListBonusPayTimeItem(List<BPTimeItemUpdateCommand> lstTimeItem) {
		this.bpTimeItemUpdateCommandhandler.handle(lstTimeItem);
	}
}
