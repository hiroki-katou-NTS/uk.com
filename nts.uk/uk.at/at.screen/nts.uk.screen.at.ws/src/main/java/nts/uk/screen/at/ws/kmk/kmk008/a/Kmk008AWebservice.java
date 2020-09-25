package nts.uk.screen.at.ws.kmk.kmk008.a;

import nts.uk.screen.at.app.kmk.kmk008.operationsetting.AgreeOpeSetScreenProcessor;
import nts.uk.screen.at.app.kmk.kmk008.operationsetting.AgreementOperationSettingDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * 画面表示を行う
 */
@Path("screen/at/kmk008/a")
@Produces("application/json")
public class Kmk008AWebservice {

    @Inject
    private AgreeOpeSetScreenProcessor agreeOpeSetScreenProcessor;

    @POST
    @Path("get")
    public AgreementOperationSettingDto getAgreeOpeSetting() {
        return this.agreeOpeSetScreenProcessor.findDataAgreeOpeSet();
    }

}
