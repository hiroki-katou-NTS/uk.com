package nts.uk.ctx.at.record.ws.worktypeselection;

import java.util.List;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import nts.uk.ctx.at.record.app.find.worktypeselection.WorkTypeSelectionDto;
import nts.uk.ctx.at.record.app.find.worktypeselection.WorkTypeSelectionFinder;

/**
 * @author anhvd
 *
 */
@Path("at/record/worktypeselection")
@Produces("application/json")
public class WorkTypeSelectionWebservice {
	@Inject
	private WorkTypeSelectionFinder finder;

	@POST
	@Path("getall")
	public List<WorkTypeSelectionDto> getAll() {
		return this.finder.getListWorkTypeSelection();
	}
	
	@POST
	@Path("getNamesByCodes")
	public List<String> getNamesByCodes(List<String> codes) {
		return this.finder.getWorkTypeNamesByCodes(codes);
	}
	
	@POST
	@Path("getNamesByCodesNew")
	public List<String> getNamesByCodesHasDefault(List<String> codes) {
		return this.finder.getWorkTypeNamesByCodesHasDefault(codes);
	}
}
