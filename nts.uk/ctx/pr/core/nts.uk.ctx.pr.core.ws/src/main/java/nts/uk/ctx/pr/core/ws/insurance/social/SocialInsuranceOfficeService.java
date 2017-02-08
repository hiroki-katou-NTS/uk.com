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
import nts.uk.ctx.core.app.insurance.social.command.DeleteSocialOfficeCommand;
import nts.uk.ctx.core.app.insurance.social.command.DeleteSocialOfficeCommandHandler;
import nts.uk.ctx.core.app.insurance.social.command.RegisterSocialOfficeCommand;
import nts.uk.ctx.core.app.insurance.social.command.RegisterSocialOfficeCommandHandler;
import nts.uk.ctx.core.app.insurance.social.command.UpdateSocialOfficeCommand;
import nts.uk.ctx.core.app.insurance.social.command.UpdateSocialOfficeCommandHandler;
import nts.uk.ctx.core.app.insurance.social.find.dto.SocialInsuranceOfficeDto;
import nts.uk.ctx.core.app.insurance.social.find.dto.SocialInsuranceOfficeItemDto;
import nts.uk.ctx.core.app.insurance.social.pension.command.RegisterPensionCommandHandler;
import nts.uk.ctx.pr.core.dom.insurance.OfficeCode;
import nts.uk.ctx.pr.core.dom.insurance.OfficeName;

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
	public List<SocialInsuranceOfficeItemDto> findAll() {
		List<SocialInsuranceOfficeItemDto> lstSocialInsuranceOfficeIn = new ArrayList<SocialInsuranceOfficeItemDto>();
		SocialInsuranceOfficeItemDto socialInsuranceOffice001 = new SocialInsuranceOfficeItemDto();
		socialInsuranceOffice001.setCode("000000000001");
		socialInsuranceOffice001.setName("A 事業所");
		lstSocialInsuranceOfficeIn.add(socialInsuranceOffice001);
		SocialInsuranceOfficeItemDto socialInsuranceOffice002 = new SocialInsuranceOfficeItemDto();
		socialInsuranceOffice002.setCode("000000000002");
		socialInsuranceOffice002.setName("B 事業所");
		lstSocialInsuranceOfficeIn.add(socialInsuranceOffice002);
		SocialInsuranceOfficeItemDto socialInsuranceOffice003 = new SocialInsuranceOfficeItemDto();
		socialInsuranceOffice003.setCode("000000000003");
		socialInsuranceOffice003.setName("C 事業所");
		lstSocialInsuranceOfficeIn.add(socialInsuranceOffice003);
		return lstSocialInsuranceOfficeIn;
	}

	@POST
	@Path("find/{officeCode}")
	public SocialInsuranceOfficeDto findHistory(@PathParam("officeCode") String officeCode) {
		
		List<SocialInsuranceOfficeDto> lstOffice = new ArrayList<SocialInsuranceOfficeDto>();
		SocialInsuranceOfficeDto officeDtoResult = new SocialInsuranceOfficeDto();
		SocialInsuranceOfficeDto office1 = new SocialInsuranceOfficeDto();
		office1.setCode("000000000001");
		office1.setName("A 事業所");
		lstOffice.add(office1);
		SocialInsuranceOfficeDto office2 = new SocialInsuranceOfficeDto();
		office2.setCode("000000000002");
		office2.setName("B 事業所");
		lstOffice.add(office2);
		SocialInsuranceOfficeDto office3 = new SocialInsuranceOfficeDto();
		office3.setCode("000000000003");
		office3.setName("C 事業所");
		lstOffice.add(office3);
		for (SocialInsuranceOfficeDto OfficeDto : lstOffice) {
 			if (OfficeDto.getCode().toString().equals(officeCode)) {
 				officeDtoResult = OfficeDto;
 			}
		}
		return officeDtoResult;
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
