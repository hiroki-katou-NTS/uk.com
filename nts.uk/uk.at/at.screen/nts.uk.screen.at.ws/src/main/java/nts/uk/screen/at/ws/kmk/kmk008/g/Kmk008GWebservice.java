package nts.uk.screen.at.ws.kmk.kmk008.g;

import nts.uk.screen.at.app.kmk.kmk008.operationsetting.AgreementOperationSettingDto;
import nts.uk.screen.at.app.kmk.kmk008.operationsetting.AgreeOpeInitDisplayProcessor;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("screen/at/kmk008/g")
@Produces("application/json")
public class Kmk008GWebservice {

    @Inject
    private AgreeOpeInitDisplayProcessor performInitDisplayProcessor;

    @POST
    @Path("getInitDisplay")
    public AgreementOperationSettingDto getInitDisplay() {
        return this.performInitDisplayProcessor.find();
    }

}
