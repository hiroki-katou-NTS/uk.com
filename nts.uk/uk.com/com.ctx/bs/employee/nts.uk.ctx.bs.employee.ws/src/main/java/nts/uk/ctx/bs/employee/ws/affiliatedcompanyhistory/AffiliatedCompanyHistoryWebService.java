/**
 * 
 */
package nts.uk.ctx.bs.employee.ws.affiliatedcompanyhistory;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.bs.employee.app.find.affiliatedcompanyhistory.AffiliatedCompanyHistoryFinder;
import nts.uk.ctx.bs.employee.dom.employee.history.AffCompanyHistItem;

/**
 * @author hieult
 *
 */
@Path("bs/employee/affiliatedcompanyhistory")
@Produces(MediaType.APPLICATION_JSON)
public class AffiliatedCompanyHistoryWebService extends WebService {

	@Inject
	private AffiliatedCompanyHistoryFinder affiliatedCompanyHistoryFinder;
	
	@Path("getdata}")
	@POST
	public List<AffCompanyHistItem> getByIDAndBasedate(GeneralDate baseDate , List<String> listempID) {
		return this.affiliatedCompanyHistoryFinder.getByIDAndBasedate(baseDate, listempID);
	}
}
