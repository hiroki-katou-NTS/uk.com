package nts.uk.ctx.at.shared.ws.workrecord.monthlyresults.roleopenperiod;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.command.workrecord.monthlyresults.roleopenperiod.RoleOfOpenPeriodSaveCommand;
import nts.uk.ctx.at.shared.app.command.workrecord.monthlyresults.roleopenperiod.RoleOfOpenPeriodSaveCommandHandler;
import nts.uk.ctx.at.shared.app.find.workrecord.monthlyresults.roleopenperiod.RoleOfOpenPeriodDto;
import nts.uk.ctx.at.shared.app.find.workrecord.monthlyresults.roleopenperiod.RoleOfOpenPeriodFinder;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.aggr.roleofovertimework.roleopenperiod.RoleOfOpenPeriodEnum;

/**
 * The Class RoleOfOpenPeriodWS.
 */
@Path("at/shared/workrecord/monthlyresults/roleopenperiod")
@Produces(MediaType.APPLICATION_JSON)
public class RoleOfOpenPeriodWS extends WebService{

	/** The finder. */
	@Inject
	private RoleOfOpenPeriodFinder finder;
	
	/** The save handler. */
	@Inject
	private RoleOfOpenPeriodSaveCommandHandler saveHandler;
	
	
	/**
	 * Find data.
	 *
	 * @return the list
	 */
	@Path("find")
	@POST
	public List<RoleOfOpenPeriodDto> findData() {
		return this.finder.findData();
	}
	
	/**
	 * Save data.
	 *
	 * @param lstCommand the lst command
	 */
	@Path("save")
	@POST
	public void saveData(List<RoleOfOpenPeriodSaveCommand> lstCommand) {
		this.saveHandler.handle(lstCommand);
	}
	
	/**
	 * Gets the role of open period enum.
	 *
	 * @return the role of open period enum
	 */
	@Path("enum/roleofopenperiod")
	@POST
	public List<EnumConstant> getRoleOfOpenPeriodEnum(){
		return EnumAdaptor.convertToValueNameList(RoleOfOpenPeriodEnum.class);
	}
}
