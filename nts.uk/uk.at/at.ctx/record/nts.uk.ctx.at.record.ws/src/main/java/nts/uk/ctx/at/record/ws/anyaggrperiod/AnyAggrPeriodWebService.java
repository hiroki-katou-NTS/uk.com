package nts.uk.ctx.at.record.ws.anyaggrperiod;


import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.query.anyaggrperiod.AggrPeriodDto;
import nts.uk.ctx.at.record.app.query.anyaggrperiod.AnyAggrPeriodQuery;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;


@Path("at/record/kwr/007/a/arbitraryaggregatio")
@Produces("application/json")
public class AnyAggrPeriodWebService extends WebService {
    @Inject
    AnyAggrPeriodQuery anyAggrPeriodQuery;

    @POST
    @Path("getlist")
    public List<AggrPeriodDto> findAll() {
        return anyAggrPeriodQuery.findAll();
    }
}
