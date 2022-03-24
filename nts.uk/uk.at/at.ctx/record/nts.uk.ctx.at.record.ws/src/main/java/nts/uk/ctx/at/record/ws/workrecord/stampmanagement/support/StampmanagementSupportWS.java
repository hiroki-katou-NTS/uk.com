package nts.uk.ctx.at.record.ws.workrecord.stampmanagement.support;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.workrecord.stampmanagement.support.DeleteSupportCardCommandHandler;
import nts.uk.ctx.at.record.app.command.workrecord.stampmanagement.support.RegisterNewSupportCardCommandHandler;
import nts.uk.ctx.at.record.app.command.workrecord.stampmanagement.support.RenewalSupportCardCommandHandler;
import nts.uk.ctx.at.record.app.command.workrecord.stampmanagement.support.SupportCardCommand;
import nts.uk.ctx.at.record.app.command.workrecord.stampmanagement.support.SupportCardSettingCommand;
import nts.uk.ctx.at.record.app.command.workrecord.stampmanagement.support.UpdateSupportCardSettingCommandHandler;
import nts.uk.ctx.at.record.app.query.workrecord.stampmanagement.support.InitialStartupDto;
import nts.uk.ctx.at.record.app.query.workrecord.stampmanagement.support.RegistrationInfoDto;
import nts.uk.ctx.at.record.app.query.workrecord.stampmanagement.support.RegistrationInfoParam;
import nts.uk.ctx.at.record.app.query.workrecord.stampmanagement.support.RegistrationInfoSupportCardQuery;
import nts.uk.ctx.at.record.app.query.workrecord.stampmanagement.support.SupportCardQuery;
import nts.uk.ctx.at.record.app.query.workrecord.stampmanagement.support.SupportCardSettingDto;
import nts.uk.ctx.at.record.app.query.workrecord.stampmanagement.support.SupportCardSettingQuery;

/**
 * 
 * @author NWS_namnv
 *
 */
@Path("at/record/workrecord/stampmanagement/support/")
@Produces("application/json")
public class StampmanagementSupportWS extends WebService {
	
	@Inject
	private SupportCardQuery supportCardQuery;
	
	@Inject
	private RegistrationInfoSupportCardQuery registrationInfoSupportCardQuery;
	
	@Inject
	private SupportCardSettingQuery supportCardSettingQuery;
	
	@Inject
	private RegisterNewSupportCardCommandHandler registerNewSupportCardCommandHandler;
	
	@Inject
	private RenewalSupportCardCommandHandler renewalSupportCardCommandHandler;
	
	@Inject
	private DeleteSupportCardCommandHandler deleteSupportCardCommandHandler;
	
	@Inject
	private UpdateSupportCardSettingCommandHandler cardSettingCommandHandler;
	
	/**
	 * Gets the initial startup.
	 *
	 * @return the initial startup
	 */
	@POST
	@Path("initialStartupSupportCard")
	public InitialStartupDto initialStartupSupportCard() {
		return this.supportCardQuery.initialStartupSupportCard();
	}
	
	/**
	 * Register support card.
	 *
	 * @param cardCommand the card command
	 */
	@POST
	@Path("registerSupportCard")
	public void registerSupportCard(SupportCardCommand cardCommand) {
		this.registerNewSupportCardCommandHandler.handle(cardCommand);
	}
	
	/**
	 * Renewal support card.
	 *
	 * @param cardCommand the card command
	 */
	@POST
	@Path("renewalSupportCard")
	public void renewalSupportCard(SupportCardCommand cardCommand) {
		this.renewalSupportCardCommandHandler.handle(cardCommand);
	}
	
	/**
	 * Delete support card.
	 *
	 * @param cardCommand the card command
	 */
	@POST
	@Path("deleteSupportCard")
	public void deleteSupportCard(SupportCardCommand cardCommand) {
		this.deleteSupportCardCommandHandler.handle(cardCommand);
	}
	
	/**
	 * Gets the registration info support card.
	 *
	 * @param param the param
	 * @return the registration info support card
	 */
	@POST
	@Path("getRegistrationInfoSupportCard")
	public RegistrationInfoDto getRegistrationInfoSupportCard(RegistrationInfoParam param) {
		return this.registrationInfoSupportCardQuery.getRegistrationInfoSupportCard(param);
	}
	
	/**
	 * Initial startup support card setting.
	 *
	 * @return the support card setting dto
	 */
	@POST
	@Path("initialStartupSupportCardSetting")
	public SupportCardSettingDto initialStartupSupportCardSetting() {
		return this.supportCardSettingQuery.getSupportCardSetting();
	}
	
	/**
	 * Update support card setting.
	 *
	 * @param cardSettingCommand the card setting command
	 */
	@POST
	@Path("updateSupportCardSetting")
	public void updateSupportCardSetting(SupportCardSettingCommand cardSettingCommand) {
		this.cardSettingCommandHandler.handle(cardSettingCommand);
	}

}
