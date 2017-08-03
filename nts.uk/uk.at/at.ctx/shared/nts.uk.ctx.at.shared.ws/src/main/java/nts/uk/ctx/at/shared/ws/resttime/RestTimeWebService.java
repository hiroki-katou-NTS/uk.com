/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.ws.resttime;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

import nts.arc.enums.EnumAdaptor;
import nts.arc.enums.EnumConstant;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.find.resttime.RestTimeFinder;
import nts.uk.ctx.at.shared.app.find.resttime.dto.RestTimeDto;
import nts.uk.ctx.at.shared.dom.worktimeset.TimeDayAtr;

@Path("at/share/resttime")
@Produces(MediaType.APPLICATION_JSON)
public class RestTimeWebService extends WebService {

	@Inject
	private RestTimeFinder find;

	@POST
	@Path("getresttime/{selectedSiftCode}")
	public RestTimeDto getPossibleWorkType(@PathParam("selectedSiftCode") String selectedSiftCode) {
		return this.find.getAllRestTime(selectedSiftCode);
	}
	
	@POST
	@Path("enum/TimeDayAtr")
	public List<EnumConstant> getTimeDayAtrEnum() {
		return EnumAdaptor.convertToValueNameList(TimeDayAtr.class);
	}
	
}
