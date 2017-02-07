/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.ws.insurance.social;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.insurance.social.DeleteSocialOfficeCommand;
import nts.uk.ctx.pr.core.app.command.insurance.social.DeleteSocialOfficeCommandHandler;
import nts.uk.ctx.pr.core.app.command.insurance.social.RegisterSocialOfficeCommand;
import nts.uk.ctx.pr.core.app.command.insurance.social.RegisterSocialOfficeCommandHandler;
import nts.uk.ctx.pr.core.app.command.insurance.social.UpdateSocialOfficeCommand;
import nts.uk.ctx.pr.core.app.command.insurance.social.UpdateSocialOfficeCommandHandler;
import nts.uk.ctx.pr.core.app.command.insurance.social.pension.RegisterPensionCommandHandler;
import nts.uk.ctx.pr.core.app.find.insurance.social.SocialInsuranceOfficeDto;

/**
 * The Class SocialInsuranceOfficeService.
 */
@Path("pr/insurance/social")
@Produces("application/json")
@Stateless
public class SocialInsuranceOfficeService extends WebService {

	@Inject
	private RegisterSocialOfficeCommandHandler registerSocialOfficeCommandHandler;
	@Inject
	private UpdateSocialOfficeCommandHandler updateSocialOfficeCommandHandler;
	@Inject
	private DeleteSocialOfficeCommandHandler deleteSocialOfficeCommandHandler;
	@Inject
	private RegisterPensionCommandHandler registerPensionCommandHandler;

	// Find all SocialInsuranceOffice conection data
	@POST
	@Path("findall")
	public List<SocialInsuranceOfficeDto> findAll() {
		List<SocialInsuranceOfficeDto> lstSocialInsuranceOfficeIn = new ArrayList<SocialInsuranceOfficeDto>();
		SocialInsuranceOfficeDto socialInsuranceOffice001 = new SocialInsuranceOfficeDto();
		socialInsuranceOffice001.setCode("000000000001");
		socialInsuranceOffice001.setName("A 事業所");
		lstSocialInsuranceOfficeIn.add(socialInsuranceOffice001);
		SocialInsuranceOfficeDto socialInsuranceOffice002 = new SocialInsuranceOfficeDto();
		socialInsuranceOffice002.setCode("000000000002");
		socialInsuranceOffice002.setName("BA 事業所");
		lstSocialInsuranceOfficeIn.add(socialInsuranceOffice002);
		SocialInsuranceOfficeDto socialInsuranceOffice003 = new SocialInsuranceOfficeDto();
		socialInsuranceOffice003.setCode("000000000003");
		socialInsuranceOffice003.setName("CA 事業所");
		lstSocialInsuranceOfficeIn.add(socialInsuranceOffice003);
		return lstSocialInsuranceOfficeIn;
	}

	@POST
	@Path("find/{officeName}")
	public SocialInsuranceOfficeDto findHistory(@PathParam("officeName") String officeName) {
		SocialInsuranceOfficeDto socialInsuranceOfficeDtoResult = new SocialInsuranceOfficeDto();
		List<SocialInsuranceOfficeDto> listOffice = this.findAll();
		for (SocialInsuranceOfficeDto SocialInsuranceOfficeDto : listOffice) {
			if (SocialInsuranceOfficeDto.getName().equals(officeName)) {
				socialInsuranceOfficeDtoResult = SocialInsuranceOfficeDto;
			}
		}
		return socialInsuranceOfficeDtoResult;
	}

	@POST
	@Path("create")
	public void createOffice(RegisterSocialOfficeCommand command) {
		this.registerSocialOfficeCommandHandler.handle(command);
		return;
	}

	@POST
	@Path("update")
	public void updateOffice(UpdateSocialOfficeCommand command) {
		this.updateSocialOfficeCommandHandler.handle(command);
		return;
	}

	@POST
	@Path("remove")
	public void removeOffice(DeleteSocialOfficeCommand command) {
		this.deleteSocialOfficeCommandHandler.handle(command);
		return;
	}
	@POST
	@Path("list/office")
	public void listOffice()
	{
		return;
	}

}
