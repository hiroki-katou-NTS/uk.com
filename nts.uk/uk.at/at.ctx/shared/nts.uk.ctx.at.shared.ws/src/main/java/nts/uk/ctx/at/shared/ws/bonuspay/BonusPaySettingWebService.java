package nts.uk.ctx.at.shared.ws.bonuspay;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.BPSettingAddCommand;
import nts.uk.ctx.at.shared.app.command.BPSettingAddCommandHandler;
import nts.uk.ctx.at.shared.app.command.BPSettingDeleteCommand;
import nts.uk.ctx.at.shared.app.command.BPSettingDeleteCommandHandler;
import nts.uk.ctx.at.shared.app.command.BPSettingUpdateCommand;
import nts.uk.ctx.at.shared.app.command.BPSettingUpdateCommandHandler;
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
	@Path("getAllBonusPaySetting/{companyId}")
	public List<BPSettingDto> getAllBonusPaySetting(@PathParam("companyId") String companyId) {
		return this.bpSettingFinder.getAllBonusPaySetting(companyId);
	}

	@POST
	@Path("addBonusPaySetting")
	void addBonusPaySetting(BPSettingAddCommand bpSettingAddCommand) {
		this.bpSettingAddCommandHandler.handle(bpSettingAddCommand);
	}

	@POST
	@Path("updateBonusPaySetting")
	void updateBonusPaySetting(BPSettingUpdateCommand bpSettingUpdateCommand) {
		this.bpSettingUpdateCommandHandler.handle(bpSettingUpdateCommand);
	}
	@POST
	@Path("removeBonusPaySetting")
	void removeBonusPaySetting(BPSettingDeleteCommand bpSettingDeleteCommand) {
		this.bpSettingDeleteCommandHandler.handle(bpSettingDeleteCommand);
	}
}
