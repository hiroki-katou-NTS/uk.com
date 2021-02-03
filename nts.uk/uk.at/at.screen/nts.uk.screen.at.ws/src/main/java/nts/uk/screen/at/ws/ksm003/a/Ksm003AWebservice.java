package nts.uk.screen.at.ws.ksm003.a;

import nts.uk.screen.at.app.ksm003.find.GetWorkCycle;
import nts.uk.screen.at.app.ksm003.find.WorkCycleDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("screen/at/ksm003/a")
@Produces("application/json")
public class Ksm003AWebservice {

    @Inject
    GetWorkCycle finder;

    @POST
    @Path("get")
    public List<WorkCycleDto> get() { return finder.getDataStartScreen(); }

    @POST
    @Path("getByCode/{patternCode}")
    public WorkCycleDto getByCode(@PathParam("patternCode") String patternCode) { return finder.getWorkCycleInfo(patternCode); }

}
