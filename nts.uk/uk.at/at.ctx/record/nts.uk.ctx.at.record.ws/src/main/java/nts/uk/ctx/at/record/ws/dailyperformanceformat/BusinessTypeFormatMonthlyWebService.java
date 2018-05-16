package nts.uk.ctx.at.record.ws.dailyperformanceformat;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.dailyperformanceformat.AddBusinessTypeMonthlyCommand;
import nts.uk.ctx.at.record.app.command.dailyperformanceformat.AddBusinessTypeMonthlyCommandHandler;
import nts.uk.ctx.at.record.app.command.dailyperformanceformat.DeleteBusiFormatBySheetCmd;
import nts.uk.ctx.at.record.app.command.dailyperformanceformat.DeleteBusiFormatBySheetCmdHandler;
import nts.uk.ctx.at.record.app.command.dailyperformanceformat.UpdateBusinessTypeMonthlyCommand;
import nts.uk.ctx.at.record.app.command.dailyperformanceformat.UpdateBusinessTypeMonthlyCommandHandler;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.BusinessTypeMonthlyDetailFinder;
import nts.uk.ctx.at.record.app.find.dailyperformanceformat.dto.BusinessTypeMonthlyDetailDto;

@Path("at/record/businesstype")
@Produces("application/json")
public class BusinessTypeFormatMonthlyWebService extends WebService  {
	
	@Inject
	private BusinessTypeMonthlyDetailFinder businessTypeMonthlyDetailFinder;
	
	@Inject
	private AddBusinessTypeMonthlyCommandHandler addBusinessTypeMonthlyCommandHandler;
	
	@Inject
	private UpdateBusinessTypeMonthlyCommandHandler updateBusinessTypeMonthlyCommandHandler;
	
	@Inject 
	private DeleteBusiFormatBySheetCmdHandler deleteHandler ;
	
	@POST
	@Path("findBusinessTypeMonthlyDetail/{businessTypeCode}")
	public BusinessTypeMonthlyDetailDto getAll(@PathParam("businessTypeCode") String businessTypeCode){
		return this.businessTypeMonthlyDetailFinder.findDetail(businessTypeCode);
	}
	
	@POST
	@Path("addBusinessTypeMonthlyDetail")
	public void AddBusinessTypeMonthlyDetail(AddBusinessTypeMonthlyCommand command){
		this.addBusinessTypeMonthlyCommandHandler.handle(command);
	}
	
	@POST
	@Path("updateBusinessTypeMonthlyDetail")
	public void UpdateBusinessTypeMonthlyDetail(UpdateBusinessTypeMonthlyCommand command){
		this.updateBusinessTypeMonthlyCommandHandler.handle(command);
	}
	
	@POST
	@Path("deletebysheet")
	public void deleteBusiFormatBySheet(DeleteBusiFormatBySheetCmd command){
		this.deleteHandler.handle(command);
	}

}
