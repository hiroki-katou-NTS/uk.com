package nts.uk.ctx.at.record.ws.dailyperformanceformat;

import java.math.BigDecimal;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.dailyperformanceformat.AddBusTypeCommand;
import nts.uk.ctx.at.record.app.command.dailyperformanceformat.AddBusTypeCommandHandler;
import nts.uk.ctx.at.record.app.command.dailyperformanceformat.UpdateBusTypeCommand;
import nts.uk.ctx.at.record.app.command.dailyperformanceformat.UpdateBusTypeCommandHandler;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.DailyPerformanceFinder;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.BusinessTypeDetailDto;

@Path("at/record/businesstype")
@Produces("application/json")
public class BusTypeFormatWebService extends WebService {
	
	@Inject
	private AddBusTypeCommandHandler addBusTypeCommandHandler;
	
	@Inject
	private UpdateBusTypeCommandHandler updateBusTypeCommandHandler;
	
	@Inject
	private DailyPerformanceFinder dailyPerformanceFinder;
	
	@POST
	@Path("addBusTypeFormat")
	public void addBusTypeFormat(AddBusTypeCommand command) {
		this.addBusTypeCommandHandler.handle(command);
	}
	
	@POST
	@Path("updateBusTypeFormat")
	public void updateBusTypeFormat(UpdateBusTypeCommand command) {
		this.updateBusTypeCommandHandler.handle(command);
	}
	
	@POST
	@Path("find/businessTypeDetail/{businessTypeCode}/{sheetNo}")
	public BusinessTypeDetailDto getBusinessTypeDetail(@PathParam("businessTypeCode") String businessTypeCode,
			@PathParam("sheetNo") BigDecimal sheetNo) {
		return this.dailyPerformanceFinder.findAll(businessTypeCode, sheetNo);
	}

}
