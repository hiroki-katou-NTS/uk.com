package nts.uk.ctx.hr.develop.ws.sysoperationset.businessrecognition;

import java.util.List;
import java.util.stream.Collectors;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.uk.ctx.hr.develop.app.sysoperationset.businessrecognition.command.MenuApprovalSettingsCommand;
import nts.uk.ctx.hr.develop.app.sysoperationset.businessrecognition.dto.MenuApprovalSettingsInforDto;
import nts.uk.ctx.hr.develop.app.sysoperationset.businessrecognition.find.MenuApprovalSettingsFinder;

@Path("menuApprovalSettings")
@Produces(MediaType.APPLICATION_JSON)
public class MenuApprovalSettingsWS {

	@Inject
	private MenuApprovalSettingsFinder finder;
	
	@Inject
	private MenuApprovalSettingsCommand command;
	
	@POST
	@Path("/get")
	public List<MenuApprovalSettingsInforDto> get(){
		return finder.get();
	}
	
	@POST
	@Path("/update")
	public void update(List<MenuApprovalSettingsInforDto> param){
		command.update(param.stream().map(c->c.getMenuApprovalSettings()).collect(Collectors.toList()));
	}
}
