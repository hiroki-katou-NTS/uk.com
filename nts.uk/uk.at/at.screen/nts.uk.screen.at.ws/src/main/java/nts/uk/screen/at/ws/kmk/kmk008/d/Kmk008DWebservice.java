package nts.uk.screen.at.ws.kmk.kmk008.d;

import nts.uk.screen.at.app.kmk.kmk008.workplace.AgreeTimeOfWorkPlaceScreenProcessor;
import nts.uk.screen.at.app.kmk.kmk008.workplace.AgreementTimeOfWorkPlaceDto;
import nts.uk.screen.at.app.kmk.kmk008.workplace.RequestWorkPlace;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * 画面表示を行う
 */
@Path("screen/at/kmk008/d")
@Produces("application/json")
public class Kmk008DWebservice {

    @Inject
    private AgreeTimeOfWorkPlaceScreenProcessor agreeTimeOfWorkPlaceScreenProcessor;

    @POST
    @Path("get")
    public AgreementTimeOfWorkPlaceDto getAgreeOpeSetting(RequestWorkPlace request) {
        return this.agreeTimeOfWorkPlaceScreenProcessor.findAgreeTimeOfWorkPlace(request);
    }

}
