package nts.uk.screen.at.ws.kml.kml002.g;

import nts.uk.screen.at.app.kml002.screenG.CountInfoDto;
import nts.uk.screen.at.app.kml002.screenG.CountInfoProcessor;
import nts.uk.screen.at.app.kml002.screenG.RequestPrams;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;

@Path("screen/at/kml002/g")
@Produces(MediaType.APPLICATION_JSON)
public class CountInfoWebService {

    @Inject
    CountInfoProcessor countInfoProcessor;

    @POST
    @Path("getInfo")
    public CountInfoDto get(RequestPrams requestPrams) {
        return countInfoProcessor.getInfo(requestPrams);
    }

}
