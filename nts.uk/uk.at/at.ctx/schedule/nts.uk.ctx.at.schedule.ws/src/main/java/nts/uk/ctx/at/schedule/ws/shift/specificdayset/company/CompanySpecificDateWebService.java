package nts.uk.ctx.at.schedule.ws.shift.specificdayset.company;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.schedule.app.find.shift.specificdayset.company.CompanySpecificDateDto;
import nts.uk.ctx.at.schedule.app.find.shift.specificdayset.company.CompanySpecificDateFinder;

@Path("at/schedule/shift/specificdayset/company")
@Produces("application/json")
public class CompanySpecificDateWebService extends WebService {

	@Inject
	private CompanySpecificDateFinder find;
	
	@POST
	@Path("getcompanyspecificdaysetbydate/{processDate}")
	public List<CompanySpecificDateDto> getCompanySpecificDateByCompany(@PathParam("processDate") int processDate) {
		return this.find.getComSpecByDate(processDate);
	}
}