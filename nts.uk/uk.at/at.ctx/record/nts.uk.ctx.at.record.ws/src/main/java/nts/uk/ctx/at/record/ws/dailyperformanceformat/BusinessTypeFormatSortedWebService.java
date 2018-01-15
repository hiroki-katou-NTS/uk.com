package nts.uk.ctx.at.record.ws.dailyperformanceformat;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.dailyperformanceformat.UpdateBusinessTypeSortedCommand;
import nts.uk.ctx.at.record.app.command.dailyperformanceformat.UpdateBusinessTypeSortedCommandHandler;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.BusinessTypeSortedFinder;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.BusinessTypeSortedDto;

@Path("at/record/businesstype")
@Produces("application/json")
public class BusinessTypeFormatSortedWebService extends WebService {

	@Inject
	private BusinessTypeSortedFinder businessTypeSortedFinder;

	@Inject
	private UpdateBusinessTypeSortedCommandHandler updateBusinessTypeSortedCommandHandler;
	
	@POST
	@Path("findBusinessTypeSorted")
	public List<BusinessTypeSortedDto> getAll() {
		return this.businessTypeSortedFinder.findAll();
	}

	@POST
	@Path("updateBusinessTypeSorted")
	public void UpdateBusinessTypeDailyDetail(UpdateBusinessTypeSortedCommand command) {
		this.updateBusinessTypeSortedCommandHandler.handle(command);
	}

}
