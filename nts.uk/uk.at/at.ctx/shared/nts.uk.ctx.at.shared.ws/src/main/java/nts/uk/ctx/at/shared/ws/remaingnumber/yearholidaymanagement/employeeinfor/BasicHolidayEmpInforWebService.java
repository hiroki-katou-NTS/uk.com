/**
 * 
 */
package nts.uk.ctx.at.shared.ws.remaingnumber.yearholidaymanagement.employeeinfor;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.find.remainingnumber.yearholidaymanagement.employeeinfor.basicinfor.export.query.BasicHolidayEmpInforFinder;
import nts.uk.ctx.at.shared.dom.remainingnumber.annualleave.empinfo.basicinfo.AnnualLeaveEmpBasicInfo;


/**
 * @author hieult
 *
 */
@Path("at/record/remainnumber/yearholidaymanagement")
@Produces("application/json")
public class BasicHolidayEmpInforWebService extends WebService {
	@Inject
	private BasicHolidayEmpInforFinder basicHolidayEmpInforFinder;

	
	@POST
	@Path("get-data")
	public List<AnnualLeaveEmpBasicInfo> getData(List<String> listEmpId) {
		return basicHolidayEmpInforFinder.getData(listEmpId);
	}
}
