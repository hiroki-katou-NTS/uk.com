/**
 * 
 */
package nts.uk.ctx.at.record.ws.workrecord.operationsetting;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.find.workrecord.operationsetting.DaiPerformanceFunDto;
import nts.uk.ctx.at.record.app.find.workrecord.operationsetting.DaiPerformanceFunFinder;
import nts.uk.ctx.at.record.app.find.workrecord.operationsetting.FormatPerformanceDto;
import nts.uk.ctx.at.record.app.find.workrecord.operationsetting.FormatPerformanceFinder;
import nts.uk.ctx.at.record.app.find.workrecord.operationsetting.MonPerformanceFunDto;
import nts.uk.ctx.at.record.app.find.workrecord.operationsetting.MonPerformanceFunFinder;
import nts.uk.shr.com.context.AppContexts;

/**
 * @author danpv
 *
 */
@Path("at/record/workrecord/operationsetting/")
@Produces("application/json")
public class OperationSettingWebservice extends WebService {
	@Inject
	private FormatPerformanceFinder  formatPerformanceFinder;
	
	@Inject
	private MonPerformanceFunFinder  monPerformanceFunFinder;
	
	@Inject
	private DaiPerformanceFunFinder  daiPerformanceFunFinder;
	
	@POST
	@Path("getFormat")
	public FormatPerformanceDto getAllFormatPerformanceById() {
		String companyId = AppContexts.user().companyId();
		FormatPerformanceDto dto =  formatPerformanceFinder.getAllFormatPerformanceById(companyId);
		
		return dto;
	}
	
	@POST
	@Path("getdaily")
	public DaiPerformanceFunDto getDaiPerformanceFunById() {
		String companyId = AppContexts.user().companyId();
		DaiPerformanceFunDto dto =  daiPerformanceFunFinder.getDaiPerformanceFunById(companyId);
		
		return dto;
	}
	
	@POST
	@Path("getMonthy")
	public MonPerformanceFunDto getAllMonPerformanceFunById() {
		String companyId = AppContexts.user().companyId();
		MonPerformanceFunDto dto =  monPerformanceFunFinder.getAllMonPerformanceFunById(companyId);
		
		return dto;
	}
	
}
