package nts.uk.ctx.at.shared.ws.workrecord.monthlyresults.roleofovertimework;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.workrecord.monthlyresults.roleofovertimework.RoleOvertimeWorkSaveCommand;
import nts.uk.ctx.at.shared.app.command.workrecord.monthlyresults.roleofovertimework.RoleOvertimeWorkSaveCommandHandler;
import nts.uk.ctx.at.shared.app.find.workrecord.monthlyresults.roleofovertimework.RoleOvertimeWorkDto;
import nts.uk.ctx.at.shared.app.find.workrecord.monthlyresults.roleofovertimework.RoleOvertimeWorkFinder;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleofovertimework.RoleOvertimeWorkEnum;

/**
 * The Class RoleOvertimeWorkWS.
 */
@Path("at/shared/workrecord/monthlyresults/roleofovertimework")
@Produces(MediaType.APPLICATION_JSON)
public class RoleOvertimeWorkWS extends WebService{

	/** The finder. */
	@Inject
	private RoleOvertimeWorkFinder finder;
	
	/** The save handler. */
	@Inject
	private RoleOvertimeWorkSaveCommandHandler saveHandler;
	
	/**
	 * Find data.
	 *
	 * @return the list
	 */
	@Path("find")
	@POST
	public List<RoleOvertimeWorkDto> findData() {
		return this.finder.findData();
	}
	
	/**
	 * Save data.
	 *
	 * @param lstCommand the lst command
	 */
	@Path("save")
	@POST
	public void saveData(List<RoleOvertimeWorkSaveCommand> lstCommand) {
		this.saveHandler.handle(lstCommand);
	}
	
	@Path("enum/roleotwork")
	@POST
	public List<EnumConstant> getRoleOvertimeWorkEnum(){
		return EnumAdaptor.convertToValueNameList(RoleOvertimeWorkEnum.class);
	}
}
