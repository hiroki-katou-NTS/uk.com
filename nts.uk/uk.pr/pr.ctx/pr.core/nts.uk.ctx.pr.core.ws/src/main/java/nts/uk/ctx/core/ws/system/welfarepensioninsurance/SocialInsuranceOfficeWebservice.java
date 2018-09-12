package nts.uk.ctx.core.ws.system.welfarepensioninsurance;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.core.app.command.system.socialinsuranceoffice.dto.CreateSocialOfficeCommand;
import nts.uk.ctx.core.app.command.system.socialinsuranceoffice.dto.CreateSocialOfficeCommandHandler;
import nts.uk.ctx.core.app.command.system.socialinsuranceoffice.dto.DeleteSocialOfficeCommandHandler;
import nts.uk.ctx.core.app.command.system.socialinsuranceoffice.dto.FindSocialOfficeCommand;
import nts.uk.ctx.core.app.command.system.socialinsuranceoffice.dto.FindSocialOfficeCommandHandler;
import nts.uk.ctx.core.app.command.system.socialinsuranceoffice.dto.UpdateSocialOfficeCommand;
import nts.uk.ctx.core.app.command.system.socialinsuranceoffice.dto.UpdateSocialOfficeCommandHandler;
import nts.uk.ctx.core.app.find.system.socialinsuranceoffice.SocialSuranOfficeFinder;
import nts.uk.ctx.core.app.system.socialinsuranceoffice.dto.CusSociaInsuOfficeDto;
import nts.uk.ctx.core.app.system.socialinsuranceoffice.dto.DataResponseDto;
import nts.uk.ctx.core.app.system.socialinsuranceoffice.dto.SociaInsuOfficeDto;
import nts.uk.ctx.core.app.system.socialinsuranceoffice.dto.SociaInsuPreInfoDto;

@Path("basic/system/social")
@Produces("application/json")
public class SocialInsuranceOfficeWebservice {
	
	@Inject
	private SocialSuranOfficeFinder socialSuranOfficeFinder;
	
	@POST
	@Path("/start")
	public DataResponseDto startScreen() {
		SociaInsuOfficeDto sociaInsuOfficeDetail = new SociaInsuOfficeDto();
		List<SociaInsuPreInfoDto> data = socialSuranOfficeFinder.findAll();
		List<CusSociaInsuOfficeDto> dataCodeName = socialSuranOfficeFinder.findByCid();
		if(dataCodeName.isEmpty()) {
			sociaInsuOfficeDetail = socialSuranOfficeFinder.findByKey(dataCodeName.get(0).getCode());
		}
		DataResponseDto response = new DataResponseDto();
		response.setListCodeName(dataCodeName);
		response.setSociaInsuOfficeDetail(sociaInsuOfficeDetail);
		response.setSociaInsuPreInfos(data);
		return response;
	}
	
}
