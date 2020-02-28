package nts.uk.ctx.bs.employee.ws.groupcommonmaster;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.bs.employee.app.command.groupcommonmaster.SaveGroupCommonMasterCommand;
import nts.uk.ctx.bs.employee.app.command.groupcommonmaster.SaveGroupCommonMasterCommandHandler;
import nts.uk.ctx.bs.employee.app.find.groupcommonmaster.GroupCommonItemDto;
import nts.uk.ctx.bs.employee.app.find.groupcommonmaster.GroupCommonMasterDto;
import nts.uk.ctx.bs.employee.app.find.groupcommonmaster.GroupCommonMasterFinder;

/**
 * 
 * @author sonnlb
 *
 */
@Path("bs/employee/group_common_master")
@Produces(MediaType.APPLICATION_JSON)
public class GroupCommonMasterWebServices extends WebService {

	@Inject
	private GroupCommonMasterFinder commonFinder;

	@Inject
	private SaveGroupCommonMasterCommandHandler saveHandler;

	@POST
	@Path("get_master")
	public List<GroupCommonMasterDto> getMaster() {
		return this.commonFinder.getMaster();
	}

	@POST
	@Path("get_items")
	public List<GroupCommonItemDto> getItems(GetItemParam param) {
		return this.commonFinder.getItems(param.getCommonMasterId());
	}

	@POST
	@Path("save_master")
	public void getCurrentHistoryItem(SaveGroupCommonMasterCommand command) {
		this.saveHandler.handle(command);
	}

}
