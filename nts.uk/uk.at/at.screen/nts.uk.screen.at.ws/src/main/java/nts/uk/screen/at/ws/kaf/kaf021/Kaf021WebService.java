package nts.uk.screen.at.ws.kaf.kaf021;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.kaf021.find.Kaf021Finder;
import nts.uk.screen.at.app.kaf021.find.StartupInfo;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("screen/at/kaf021")
@Produces("application/json")
public class Kaf021WebService extends WebService {

    @Inject
    private Kaf021Finder kaf021Finder;

    @POST
    @Path("init")
    public StartupInfo finder() {
        return this.kaf021Finder.initStarup();
    }
}
