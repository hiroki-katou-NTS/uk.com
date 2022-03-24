package nts.uk.screen.com.ws.smm001;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.com.app.command.smm001.RegisterSmileCoopAcceptSettingCommand;
import nts.uk.screen.com.app.command.smm001.RegisterSmileCoopAcceptSettingCommandHandler;
import nts.uk.screen.com.app.command.smm001.RegisterSmileLinkageExterOutputCommand;
import nts.uk.screen.com.app.command.smm001.RegisterSmileLinkageExterOutputCommandHandler;
import nts.uk.screen.com.app.find.smm001.EmploymentChoiceDto;
import nts.uk.screen.com.app.find.smm001.GetExternalStartupInfoScreenQuery;
import nts.uk.screen.com.app.find.smm001.GetInitialStartupInfoScreenQuery;
import nts.uk.screen.com.app.find.smm001.InitialStartupOutputDto;
import nts.uk.screen.com.app.find.smm001.OutputOfStartupDto;
import nts.uk.screen.com.app.find.smm001.SelectAPaymentDateScreenQuery;

@Path("com/screen/smm001")
@Produces("application/json")
public class Smm001WebService extends WebService{
	@Inject
	private RegisterSmileCoopAcceptSettingCommandHandler registerSmileCoopAcceptSettingCommandHandler;

	@Inject
	private RegisterSmileLinkageExterOutputCommandHandler registerSmileLinkageExterOutputCommandHandler;

	@Inject
	private GetExternalStartupInfoScreenQuery getExternalStartupInfoScreenQuery;

	@Inject
	private GetInitialStartupInfoScreenQuery getInitialStartupInfoScreenQuery;

	@Inject
	private SelectAPaymentDateScreenQuery selectAPaymentDateScreenQuery;

	@POST
	@Path("register-smile-cooperation-acceptance-setting")
	public void registerSmileCooperationAcceptanceSetting(
			RegisterSmileCoopAcceptSettingCommand command) {
		registerSmileCoopAcceptSettingCommandHandler.handle(command);
	}

	@POST
	@Path("register-smile-linkage-external-output")
	public void registerRegisterSmileLinkageExternalIOutput(RegisterSmileLinkageExterOutputCommand command) {
		registerSmileLinkageExterOutputCommandHandler.handle(command);
	}

	@POST
	@Path("get-information-on-external")
	public InitialStartupOutputDto getInformationOnExternal(Integer paymentCode) {
		return getExternalStartupInfoScreenQuery.get(paymentCode);
	}

	@POST
	@Path("get-initial-startup-information")
	public OutputOfStartupDto getInitialStartupInformation() {
		return getInitialStartupInfoScreenQuery.get();
	}

	@POST
	@Path("select-a-payment-date/{paymentCode}")
	public EmploymentChoiceDto selectAPaymentDateScreenQuery(@PathParam("paymentCode") Integer paymentCode) {
		return selectAPaymentDateScreenQuery.get(paymentCode);
	}
	
	@POST
	@Path("select-list-payment-date")
	public List<EmploymentChoiceDto> selectListPaymentDateScreenQuery(List<Integer> paymentCode) {
		return paymentCode.stream().map(e -> {
			return selectAPaymentDateScreenQuery.get(e);
		}).collect(Collectors.toList());
	}
}
