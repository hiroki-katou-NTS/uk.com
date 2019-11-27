package nts.uk.ctx.at.record.ws.dailyperformanceformat;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.dailyperformanceformat.UpdateBusTypeSortedMBCommand;
import nts.uk.ctx.at.record.app.command.dailyperformanceformat.UpdateBusTypeSortedMBCommandHandler;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.BusinessTypeSortedMBFinder;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.BusinessTypeSortedMBDto;
/**
 * 
 * @author anhdt
 *
 */
@Path("at/record/businesstype/mobile")
@Produces("application/json")
public class BusinessTypeSortedMobileWS extends WebService {

	@Inject
	private BusinessTypeSortedMBFinder businessTypeSortedMBFinder;

	@Inject
	private UpdateBusTypeSortedMBCommandHandler updateBusinessTypeSortedCommandHandler;
	
	@POST
	@Path("findBusinessTypeSorted")
	public List<BusinessTypeSortedMBDto> getAll() {
		return this.businessTypeSortedMBFinder.findAll();
	}

	@POST
	@Path("updateBusinessTypeSorted")
	public void UpdateBusinessTypeDailyDetail(UpdateBusTypeSortedMBCommand command) {
		this.updateBusinessTypeSortedCommandHandler.handle(command);
	}

}

