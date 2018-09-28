package nts.uk.ctx.at.shared.ws.scherec.attitem;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.shared.app.find.scherec.attitem.AttItemFinder;
import nts.uk.ctx.at.shared.app.find.scherec.attitem.AttItemParam;
import nts.uk.ctx.at.shared.dom.scherec.dailyattendanceitem.adapter.attendanceitemname.AttItemName;

@Path("at/shared/scherec/attitem")
@Produces("application/json")
public class AttItemWS {

	@Inject
	private AttItemFinder finder;

	@POST
	@Path("getDailyAttItem")
	public List<AttItemName> getDailyAttItemByIdAndAtr(AttItemParam param) {
		return this.finder.getDailyAttItemByIdAndAtr(param);
	}

	@POST
	@Path("getdailyattitembyid/{authorityId}")
	public List<AttItemName> getDailyAttItemById(@PathParam(value = "authorityId") String authorityId) {
		return this.finder.getDailyAttItemById(authorityId);
	}
	
	@POST
	@Path("getMonthlyAttItem")
	public List<AttItemName> getMonthlyAttItemByIdAndAtr(AttItemParam param) {
		return this.finder.getMonthlyAttItemByIdAndAtr(param);
	}
	
	@POST
	@Path("getmonthlyattitembyid/{authorityId}")
	public List<AttItemName> getMonthlyAttItemById(@PathParam(value = "authorityId") String authorityId) {
		return this.finder.getMonthlyAttItemById(authorityId);
	}
}
