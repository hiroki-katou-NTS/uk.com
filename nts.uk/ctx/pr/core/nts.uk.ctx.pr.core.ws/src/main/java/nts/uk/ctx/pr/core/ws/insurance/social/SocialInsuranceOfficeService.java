package nts.uk.ctx.pr.core.ws.insurance.social;

import java.util.ArrayList;
import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.core.app.insurance.social.SocialInsuranceOfficeInDto;
import nts.uk.ctx.pr.core.app.command.insurance.social.DeleteSocialOfficeCommand;
import nts.uk.ctx.pr.core.app.command.insurance.social.DeleteSocialOfficeCommandHandler;
import nts.uk.ctx.pr.core.app.command.insurance.social.RegisterSocialOfficeCommand;
import nts.uk.ctx.pr.core.app.command.insurance.social.RegisterSocialOfficeCommandHandler;
import nts.uk.ctx.pr.core.app.command.insurance.social.UpdateSocialOfficeCommand;
import nts.uk.ctx.pr.core.app.command.insurance.social.UpdateSocialOfficeCommandHandler;

@Path("pr/insurance/social")
@Produces("application/json")
public class SocialInsuranceOfficeService extends WebService {
	
	@Inject
	private RegisterSocialOfficeCommandHandler registerSocialOfficeCommandHandler;
	@Inject
	private UpdateSocialOfficeCommandHandler updateSocialOfficeCommandHandler;
	@Inject
	private DeleteSocialOfficeCommandHandler deleteSocialOfficeCommandHandler;
	// Find all SocialInsuranceOffice conection data
	@POST
	@Path("findall")
	public List<SocialInsuranceOfficeInDto> findAll() {
		List<SocialInsuranceOfficeInDto> lstSocialInsuranceOfficeIn = new ArrayList<SocialInsuranceOfficeInDto>();
		SocialInsuranceOfficeInDto socialInsuranceOffice001 = new SocialInsuranceOfficeInDto();
		socialInsuranceOffice001.setCode("000000000001");
		socialInsuranceOffice001.setName("A事業所");
		lstSocialInsuranceOfficeIn.add(socialInsuranceOffice001);
		SocialInsuranceOfficeInDto socialInsuranceOffice002 = new SocialInsuranceOfficeInDto();
		socialInsuranceOffice002.setCode("000000000002");
		socialInsuranceOffice002.setName("B事業所");
		lstSocialInsuranceOfficeIn.add(socialInsuranceOffice002);
		SocialInsuranceOfficeInDto socialInsuranceOffice003 = new SocialInsuranceOfficeInDto();
		socialInsuranceOffice003.setCode("000000000003");
		socialInsuranceOffice003.setName("C事業所");
		lstSocialInsuranceOfficeIn.add(socialInsuranceOffice003);
		return lstSocialInsuranceOfficeIn;
	}
	@POST
	@Path("create")
	public void createOffice(RegisterSocialOfficeCommand command)
	{
		this.registerSocialOfficeCommandHandler.handle(command);
		return;
	}
	@POST
	@Path("update")
	public void updateOffice(UpdateSocialOfficeCommand command)
	{
		this.updateSocialOfficeCommandHandler.handle(command);
		return;
	}
	@POST
	@Path("remove")
	public void removeOffice(DeleteSocialOfficeCommand command)
	{
		this.deleteSocialOfficeCommandHandler.handle(command);
		return;
	}
}
