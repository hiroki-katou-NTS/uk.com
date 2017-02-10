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
import nts.uk.ctx.pr.core.app.insurance.social.office.command.DeleteSocialOfficeCommand;
import nts.uk.ctx.pr.core.app.insurance.social.office.command.DeleteSocialOfficeCommandHandler;
import nts.uk.ctx.pr.core.app.insurance.social.office.command.RegisterSocialOfficeCommand;
import nts.uk.ctx.pr.core.app.insurance.social.office.command.RegisterSocialOfficeCommandHandler;
import nts.uk.ctx.pr.core.app.insurance.social.office.command.UpdateSocialOfficeCommand;
import nts.uk.ctx.pr.core.app.insurance.social.office.command.UpdateSocialOfficeCommandHandler;
import nts.uk.ctx.pr.core.app.insurance.social.office.find.HistoryDto;
import nts.uk.ctx.pr.core.app.insurance.social.office.find.SocialInsuranceOfficeDto;
import nts.uk.ctx.pr.core.app.insurance.social.office.find.SocialInsuranceOfficeFinder;
import nts.uk.ctx.pr.core.app.insurance.social.office.find.SocialInsuranceOfficeItemDto;
import nts.uk.ctx.pr.core.app.insurance.social.pensionrate.command.RegisterPensionCommandHandler;

/**
 * The Class SocialInsuranceOfficeService.
 */
@Path("pr/insurance/social")
@Produces("application/json")
@Stateless
public class SocialInsuranceOfficeWs extends WebService {

	@Inject
	private RegisterSocialOfficeCommandHandler registerSocialOfficeCommandHandler;
	@Inject
	private UpdateSocialOfficeCommandHandler updateSocialOfficeCommandHandler;
	@Inject
	private DeleteSocialOfficeCommandHandler deleteSocialOfficeCommandHandler;
	@Inject
	private RegisterPensionCommandHandler registerPensionCommandHandler;
	
	@Inject
	private SocialInsuranceOfficeFinder socialInsuranceOfficeFinder;
	
	@POST
	@Path("findall")
	public List<SocialInsuranceOfficeItemDto> findAll(String companyCode) {
		return socialInsuranceOfficeFinder.findAll(companyCode);
	}

	@POST
	@Path("find/{officeCode}")
	public SocialInsuranceOfficeDto findOffice(@PathParam("officeCode") String officeCode) {
		return socialInsuranceOfficeFinder.find(officeCode).get();
	}
	
	@POST
	@Path("history/{officeCode}")
	public HistoryDto findHistory(@PathParam("officeCode") String officeCode) {
		
		List<HistoryDto> lstHistory = new ArrayList<HistoryDto>();
		HistoryDto returnHistory = new HistoryDto();
		
		HistoryDto history1 = new HistoryDto();
		history1.setCode("00000");
		history1.setName("2015");
		lstHistory.add(history1);
		HistoryDto history2 = new HistoryDto();
		history2.setCode("00002");
		history2.setName("2016");
		lstHistory.add(history2);
		HistoryDto history3 = new HistoryDto();
		history3.setCode("00003");
		history3.setName("2017");
		lstHistory.add(history3);
		
		for (HistoryDto HistoryDto : lstHistory) {
 			if (HistoryDto.getCode().toString().equals(officeCode)) {
 				returnHistory = HistoryDto;
 			}
		}
		return returnHistory;
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
}
