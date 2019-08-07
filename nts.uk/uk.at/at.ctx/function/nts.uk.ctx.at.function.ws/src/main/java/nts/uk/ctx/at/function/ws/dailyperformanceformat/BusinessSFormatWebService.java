package nts.uk.ctx.at.function.ws.dailyperformanceformat;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.function.app.command.dailyperformanceformat.AddBusinessTypeMobileCommand;
import nts.uk.ctx.at.function.app.command.dailyperformanceformat.AddBusinessTypeSDailyCommandHandler;
import nts.uk.ctx.at.function.app.command.dailyperformanceformat.UpdateBusinessTypeSDailyCommand;
import nts.uk.ctx.at.function.app.command.dailyperformanceformat.UpdateBusinessTypeSDailyCommandHandler;
import nts.uk.ctx.at.function.app.find.dailyperformanceformat.BusinessTypeSDailyDetailFinder;
import nts.uk.ctx.at.function.app.find.dailyperformanceformat.BusinessTypeSMonthlyDetailFinder;
import nts.uk.ctx.at.function.app.find.dailyperformanceformat.dto.BusinessTypeSFormatDto;
import nts.uk.ctx.at.function.app.find.dailyperformanceformat.dto.BusinessTypeSMonthlyFormatDto;

@Path("at/function/businesstype/mobile")
@Produces("application/json")
public class BusinessSFormatWebService extends WebService {
	
	@Inject
	private BusinessTypeSMonthlyDetailFinder businessTypeMonthlyDetailFinder;

	@Inject
	private BusinessTypeSDailyDetailFinder businessTypeDailyDetailFinder;

	@Inject
	private AddBusinessTypeSDailyCommandHandler addBusinessTypeMDailyCommandHandler;

	@Inject
	private UpdateBusinessTypeSDailyCommandHandler updateBusinessTypeDailyCommandHandler;

	@POST
	@Path("findBusinessTypeDailyDetail/{businessTypeCode}")
	public BusinessTypeSFormatDto getAll(@PathParam("businessTypeCode") String businessTypeCode) {
		return this.businessTypeDailyDetailFinder.getDetail(businessTypeCode);
	}
	
	@POST
	@Path("findBusinessTypeMonthlyDetail/{businessTypeCode}")
	public BusinessTypeSMonthlyFormatDto getAllMonthly(@PathParam("businessTypeCode") String businessTypeCode){
		return this.businessTypeMonthlyDetailFinder.findDetail(businessTypeCode);
	}

	@POST
	@Path("addBusinessTypeDailyDetail")
	public void AddBusinessTypeDailyDetail(AddBusinessTypeMobileCommand command) {
		this.addBusinessTypeMDailyCommandHandler.handle(command);
	}

	@POST
	@Path("updateBusinessTypeDailyDetail")
	public void UpdateBusinessTypeDailyDetail(UpdateBusinessTypeSDailyCommand command) {
		this.updateBusinessTypeDailyCommandHandler.handle(command);
	}

}
