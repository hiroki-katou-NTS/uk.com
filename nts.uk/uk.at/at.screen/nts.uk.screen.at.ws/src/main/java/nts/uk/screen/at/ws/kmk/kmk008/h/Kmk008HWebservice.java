package nts.uk.screen.at.ws.kmk.kmk008.h;

import nts.uk.screen.at.app.kmk.kmk008.unitsetting.AgreementUnitSettingDto;
import nts.uk.screen.at.app.kmk.kmk008.unitsetting.PerformInitDisplayProcessor;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("screen/at/kmk008/h")
@Produces("application/json")
public class Kmk008HWebservice {

    @Inject
    private PerformInitDisplayProcessor performInitDisplayProcessor;

    @POST
    @Path("getInitDisplay")
    public AgreementUnitSettingDto getInitDisplay() {
        return this.performInitDisplayProcessor.find();
    }

}
