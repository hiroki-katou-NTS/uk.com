package nts.uk.ctx.at.shared.ws.monthly;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.shared.app.query.MonthlyPattern.MonthlyPatternDto;
import nts.uk.ctx.at.shared.app.query.MonthlyPattern.MonthlyPatternQuery;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("monthly")
@Produces("application/json")
public class MonthlyPatternWebService extends WebService {
    @Inject
    private MonthlyPatternQuery query;

    @POST
    @Path("get/all")
    public MonthlyPatternDto getListMonthlyPattern() {
        return query.getListMonthlyPattern();
    }
}
