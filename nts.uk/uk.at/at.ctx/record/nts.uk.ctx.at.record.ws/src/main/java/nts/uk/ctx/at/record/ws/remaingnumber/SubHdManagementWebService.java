/**
 * 
 */
package nts.uk.ctx.at.record.ws.remaingnumber;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.remainingnumber.subhdmana.AddSubHdManagementCommand;
import nts.uk.ctx.at.record.app.command.remainingnumber.subhdmana.AddSubHdManagementCommandHandler;

/**
 * @author nam.lh
 *
 */

@Path("at/record/remaingnumber")
@Produces("application/json")
public class SubHdManagementWebService extends WebService{
	@Inject
	private AddSubHdManagementCommandHandler add;
	
	@POST
	@Path("add")
	public void addBusinessTypeName(AddSubHdManagementCommand command){
		this.add.handle(command);
	}
}
