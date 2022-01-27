package nts.uk.screen.com.ws.smm001;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.com.app.smm.smm001.screencommand.RegisterSmileCooperationAcceptanceSettingScreenCommand;
import nts.uk.screen.com.app.smm.smm001.screencommand.RegisterSmileCooperationAcceptanceSettingScreenCommandHandler;
import nts.uk.screen.com.app.smm.smm001.screencommand.RegisterSmileLinkageExternalIOutputScreenCommand;
import nts.uk.screen.com.app.smm.smm001.screencommand.RegisterSmileLinkageExternalIOutputScreenCommandHandler;
import nts.uk.screen.com.app.smm.smm001.screenquery.EmploymentChoiceDto;
import nts.uk.screen.com.app.smm.smm001.screenquery.GetInformationOnExternalStartupScreenQuery;
import nts.uk.screen.com.app.smm.smm001.screenquery.GetInitialStartupInformationScreenQuery;
import nts.uk.screen.com.app.smm.smm001.screenquery.InitialStartupOutputDto;
import nts.uk.screen.com.app.smm.smm001.screenquery.OutputOfStartupDto;
import nts.uk.screen.com.app.smm.smm001.screenquery.SelectAPaymentDateScreenQuery;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("com/screen/smm001")
@Produces("application/json")
public class Smm001WebService extends WebService{
	@Inject
	private RegisterSmileCooperationAcceptanceSettingScreenCommandHandler registerSmileCooperationAcceptanceSettingScreenCommandHandle;

	@Inject
	private RegisterSmileLinkageExternalIOutputScreenCommandHandler registerSmileLinkageExternalIOutputScreenCommandHandle;

	@Inject
	private GetInformationOnExternalStartupScreenQuery getInformationOnExternalStartupScreenQuery;

	@Inject
	private GetInitialStartupInformationScreenQuery getInitialStartupInformationScreenQuery;

	@Inject
	private SelectAPaymentDateScreenQuery selectAPaymentDateScreenQuery;

	@POST
	@Path("register-smile-cooperation-acceptance-setting")
	public void registerSmileCooperationAcceptanceSetting(
			RegisterSmileCooperationAcceptanceSettingScreenCommand command) {
		registerSmileCooperationAcceptanceSettingScreenCommandHandle.handle(command);
	}

	@POST
	@Path("register-smile-linkage-external-output")
	public void registerRegisterSmileLinkageExternalIOutput(RegisterSmileLinkageExternalIOutputScreenCommand command) {
		registerSmileLinkageExternalIOutputScreenCommandHandle.handle(command);
	}

	@POST
	@Path("get-information-on-external")
	public InitialStartupOutputDto getInformationOnExternal(Integer paymentCode) {
		return getInformationOnExternalStartupScreenQuery.get(paymentCode);
	}

	@POST
	@Path("get-initial-startup-information")
	public OutputOfStartupDto getInitialStartupInformation() {
		return getInitialStartupInformationScreenQuery.get();
	}

	@POST
	@Path("select-a-payment-date")
	public EmploymentChoiceDto selectAPaymentDateScreenQuery(Integer paymentCode) {
		return selectAPaymentDateScreenQuery.get(paymentCode);
	}
}
