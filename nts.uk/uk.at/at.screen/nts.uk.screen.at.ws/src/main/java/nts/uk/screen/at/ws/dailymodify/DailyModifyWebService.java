package nts.uk.screen.at.ws.dailymodify;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ItemValue;
import nts.uk.ctx.at.shared.app.util.attendanceitem.type.ValueType;
import nts.uk.screen.at.app.dailymodify.command.DailyModifyCommandFacade;


@Path("dailymodify")
@Produces("application/json") 
public class DailyModifyWebService {

	@Inject
	private DailyModifyCommandFacade facade;
	
	@POST
	@Path("register")
	public void register() {
		List<ItemValue> items = new ArrayList<>();
		items.add(new ItemValue("12", ValueType.INTEGER, "B_A", 2));
		Map<String, List<ItemValue>> data = new HashMap<>();
		data.put("AttendanceTimeOfDailyPerformance", items);
		facade.handle(data);
	}
}
