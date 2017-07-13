package nts.uk.ctx.at.shared.ws.bonuspay;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.bonuspay.BPSettingAddCommand;
import nts.uk.ctx.at.shared.app.command.bonuspay.BPSettingAddCommandHandler;
import nts.uk.ctx.at.shared.app.command.bonuspay.BPSettingDeleteCommand;
import nts.uk.ctx.at.shared.app.command.bonuspay.BPSettingDeleteCommandHandler;
import nts.uk.ctx.at.shared.app.command.bonuspay.BPSettingUpdateCommand;
import nts.uk.ctx.at.shared.app.command.bonuspay.BPSettingUpdateCommandHandler;
import nts.uk.ctx.at.shared.app.command.bonuspay.RegisterErrorList;
import nts.uk.ctx.at.shared.app.find.bonuspay.BPSettingDto;
import nts.uk.ctx.at.shared.app.find.bonuspay.BPSettingFinder;

@Path("at/share/bonusPaySetting")
@Produces("application/json")
public class BonusPaySettingWebService extends WebService {
	@Inject
	private BPSettingFinder bpSettingFinder;

	@Inject
	private BPSettingAddCommandHandler bpSettingAddCommandHandler;

	@Inject
	private BPSettingDeleteCommandHandler bpSettingDeleteCommandHandler;

	@Inject
	private BPSettingUpdateCommandHandler bpSettingUpdateCommandHandler;

	@POST
	@Path("getAllBonusPaySetting")
	public List<BPSettingDto> getAllBonusPaySetting() {
		return this.bpSettingFinder.getAllBonusPaySetting();
	}

	@POST
	@Path("getBonusPaySetting/{bonusPaySettingCode}")
	public BPSettingDto getBonusPaySetting(@PathParam("bonusPaySettingCode") String bonusPaySettingCode) {
		return this.bpSettingFinder.getBonusPaySetting(bonusPaySettingCode);
	}

	@POST
	@Path("addBonusPaySetting")
	public List<RegisterErrorList> addBonusPaySetting(BPSettingAddCommand bpSettingAddCommand) {
		return this.bpSettingAddCommandHandler.handle(bpSettingAddCommand);
	}

	@POST
	@Path("updateBonusPaySetting")
	public List<RegisterErrorList> updateBonusPaySetting(BPSettingUpdateCommand bpSettingUpdateCommand) {
		return this.bpSettingUpdateCommandHandler.handle(bpSettingUpdateCommand);
	}

	@POST
	@Path("removeBonusPaySetting")
	public void removeBonusPaySetting(BPSettingDeleteCommand bpSettingDeleteCommand) {
		this.bpSettingDeleteCommandHandler.handle(bpSettingDeleteCommand);
	}
}
