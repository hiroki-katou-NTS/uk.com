package nts.uk.ctx.at.shared.ws.scherec.monthlyattditem;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.find.scherec.monthlyattditem.MonthlyAttdItemSharedDto;
import nts.uk.ctx.at.shared.app.find.scherec.monthlyattditem.MonthlyAttdItemSharedFinder;

@Path("at/shared/scherec/monthlyattditem")
@Produces("application/json")
public class MonthlyAttdItemSharedWS extends WebService {
	
	@Inject
	private MonthlyAttdItemSharedFinder finder;
	
	@POST
	@Path("getallmonthlyattd")
	public List<MonthlyAttdItemSharedDto> getListDailyAttdItem(){
		List<MonthlyAttdItemSharedDto> data = finder.getAllMonthlyAttdItemShared();
		return data;
	}

}
