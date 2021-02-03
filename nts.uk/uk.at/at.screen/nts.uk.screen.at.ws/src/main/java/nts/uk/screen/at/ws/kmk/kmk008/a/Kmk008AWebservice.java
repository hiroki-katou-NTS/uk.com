package nts.uk.screen.at.ws.kmk.kmk008.a;

import nts.uk.screen.at.app.kmk.kmk008.operationsetting.AgreeOpeInitDisplayProcessor;
import nts.uk.screen.at.app.kmk.kmk008.operationsetting.AgreementOperationSettingDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("screen/at/kmk008/a")
@Produces("application/json")
public class Kmk008AWebservice {

    @Inject
    private AgreeOpeInitDisplayProcessor agreeOpeSetScreenProcessor;

    @POST
    @Path("get")
    public AgreementOperationSettingDto getAgreeOpeSetting() {
        return this.agreeOpeSetScreenProcessor.find();
    }

}
