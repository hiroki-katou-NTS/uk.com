package nts.uk.ctx.at.shared.ws.bonuspay;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.bonuspay.BPTimeItemAddCommand;
import nts.uk.ctx.at.shared.app.command.bonuspay.BPTimeItemAddCommandHandler;
import nts.uk.ctx.at.shared.app.command.bonuspay.BPTimeItemUpdateCommand;
import nts.uk.ctx.at.shared.app.command.bonuspay.BPTimeItemUpdateCommandhandler;
import nts.uk.ctx.at.shared.app.find.bonuspay.BPTimeItemDto;
import nts.uk.ctx.at.shared.app.find.bonuspay.BPTimeItemFinder;
import nts.uk.ctx.at.shared.app.find.bonuspay.item.BPItemArt;

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
	@Path("checkInit")
	public int checkInit() {
		return this.bpTimeItemFinder.checkInit();
	}

	@POST
	@Path("getListBonusPayTimeItem")
	public List<BPTimeItemDto> getListBonusPayTimeItem() {
		return this.bpTimeItemFinder.getListBonusPayTimeItem();
	}

	@POST
	@Path("getListBonusPayTimeItemInUse")
	public List<BPTimeItemDto> getListBonusPayTimeItemInUse() {
		return this.bpTimeItemFinder.getListBonusPayTimeItemInUse();
	}

	@POST
	@Path("checkUseArt")
	public void checkUseArt(BPItemArt arts){
		this.bpTimeItemFinder.checkUseArt(arts);
	}

	@POST
	@Path("getListSpecialBonusPayTimeItem")
	public List<BPTimeItemDto> getListSpecialBonusPayTimeItem() {
		return this.bpTimeItemFinder.getListSpecialBonusPayTimeItem();
	}

	@POST
	@Path("getListSpecialBonusPayTimeItemInUse")
	public List<BPTimeItemDto> getListSpecialBonusPayTimeItemInUse() {
		return this.bpTimeItemFinder.getListSpecialBonusPayTimeItemInUse();
	}

	// cann't find this Path in UI 
//	@POST
//	@Path("getBonusPayTimeItem/{timeItemId}")
//	public BPTimeItemDto getBonusPayTimeItem(@PathParam("timeItemId") String timeItemId){
//		return this.bpTimeItemFinder.getBonusPayTimeItem(timeItemId);
//	}
	
	// cann't find this Path in UI 
//	@POST
//	@Path("getSpecialBonusPayTimeItem/{timeItemNo}")
//	public BPTimeItemDto getSpecialBonusPayTimeItem(@PathParam("timeItemId") String timeItemId){
//		return this.bpTimeItemFinder.getSpecialBonusPayTimeItem(timeItemId);
//	}
	
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
