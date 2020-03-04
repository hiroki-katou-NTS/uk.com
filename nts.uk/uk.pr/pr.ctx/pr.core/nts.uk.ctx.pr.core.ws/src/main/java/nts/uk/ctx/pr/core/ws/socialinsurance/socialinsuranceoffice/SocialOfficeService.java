package nts.uk.ctx.pr.core.ws.socialinsurance.socialinsuranceoffice;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.pr.core.app.command.socialinsurance.socialinsuranceoffice.CreateSocialOfficeCommand;
import nts.uk.ctx.pr.core.app.command.socialinsurance.socialinsuranceoffice.CreateSocialOfficeCommandHandler;
import nts.uk.ctx.pr.core.app.command.socialinsurance.socialinsuranceoffice.DeleteSocialOfficeCommandHandler;
import nts.uk.ctx.pr.core.app.command.socialinsurance.socialinsuranceoffice.FindSocialOfficeCommand;
import nts.uk.ctx.pr.core.app.command.socialinsurance.socialinsuranceoffice.FindSocialOfficeFinder;
import nts.uk.ctx.pr.core.app.command.socialinsurance.socialinsuranceoffice.UpdateSocialOfficeCommand;
import nts.uk.ctx.pr.core.app.command.socialinsurance.socialinsuranceoffice.UpdateSocialOfficeCommandHandler;
import nts.uk.ctx.pr.core.app.command.socialinsurance.socialinsuranceoffice.dto.CreateOfficeDto;
import nts.uk.ctx.pr.core.app.command.socialinsurance.socialinsuranceoffice.dto.CusSociaInsuOfficeDto;
import nts.uk.ctx.pr.core.app.command.socialinsurance.socialinsuranceoffice.dto.DataResponseDto;
import nts.uk.ctx.pr.core.app.command.socialinsurance.socialinsuranceoffice.dto.SociaInsuOfficeDto;
import nts.uk.ctx.pr.core.app.command.socialinsurance.socialinsuranceoffice.dto.SociaInsuPreInfoDto;
import nts.uk.ctx.pr.core.app.find.system.socialinsuranceoffice.SocialSuranOfficeFinder;

@Path("ctx/pr/core/socialinsurance/socialinsuranceoffice")
@Produces("application/json")
public class SocialOfficeService {
	
	@Inject
	private SocialSuranOfficeFinder socialSuranOfficeFinder;
	
	@Inject
	private FindSocialOfficeFinder findSocialOfficeFinder;
	
	@Inject
	private CreateSocialOfficeCommandHandler createSocialOfficeCommandHandler;
	
	@Inject
	private UpdateSocialOfficeCommandHandler updateSocialOfficeCommandHandler;
	
	@Inject
	private DeleteSocialOfficeCommandHandler deleteSocialOfficeCommandHandler;
	
	@POST
	@Path("/start")
	public DataResponseDto startScreen() {
		SociaInsuOfficeDto sociaInsuOfficeDetail = new SociaInsuOfficeDto();
		
		// ドメインモデル「社会保険用都道府県履歴」を取得する
		List<SociaInsuPreInfoDto> data = socialSuranOfficeFinder.findAll();

		// ドメインモデル「社会保険事業所」をすべて取得する
		List<CusSociaInsuOfficeDto> dataCodeName = socialSuranOfficeFinder.findByCid();
		if(!dataCodeName.isEmpty()) {
			sociaInsuOfficeDetail = socialSuranOfficeFinder.findByKey(dataCodeName.get(0).getCode());
		}
		DataResponseDto response = new DataResponseDto();
		response.setListCodeName(dataCodeName);
		response.setSociaInsuOfficeDetail(sociaInsuOfficeDetail);
		response.setSociaInsuPreInfos(data);
		return response;
	}
	
	
	@POST
	@Path("/findByCode/{code}")
	public SociaInsuOfficeDto findByCode(@PathParam("code") String code) {
		return this.findSocialOfficeFinder.findByCode(code);
	}
	
	@POST
	@Path("/create")
	public CreateOfficeDto createSociaInsuOffice(CreateSocialOfficeCommand createSocialOfficeCommand) {
		return createSocialOfficeCommandHandler.handle(createSocialOfficeCommand);
	}
	
	@POST
	@Path("/update")
	public List<String> updateSociaInsuOffice(UpdateSocialOfficeCommand updateSocialOfficeCommand) {
		return updateSocialOfficeCommandHandler.handle(updateSocialOfficeCommand);
	}
	
	@POST
	@Path("/remove")
	public List<String> deleteSociaInsuOffice(FindSocialOfficeCommand findSocialOfficeCommand) {
		return deleteSocialOfficeCommandHandler.handle(findSocialOfficeCommand);
	}
	
	@POST
	@Path("/findAll")
	public List<SociaInsuOfficeDto> findAllIncludeInfo(){
		return socialSuranOfficeFinder.findAllIncludeInfo();
	}
}
