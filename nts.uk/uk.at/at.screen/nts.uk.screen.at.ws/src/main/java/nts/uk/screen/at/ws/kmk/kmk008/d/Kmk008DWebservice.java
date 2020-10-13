package nts.uk.screen.at.ws.kmk.kmk008.d;

import nts.uk.screen.at.app.kmk.kmk008.workplace.AgreeTimeOfWorkPlaceScreenProcessor;
import nts.uk.screen.at.app.kmk.kmk008.workplace.AgreementTimeOfWorkPlaceDto;
import nts.uk.screen.at.app.kmk.kmk008.workplace.RequestWorkPlace;
import nts.uk.screen.at.app.kmk.kmk008.workplace.WorkPlaceListCodesDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("screen/at/kmk008/d")
@Produces("application/json")
public class Kmk008DWebservice {

    @Inject
    private AgreeTimeOfWorkPlaceScreenProcessor agreeTimeOfWorkPlaceScreenProcessor;

    @POST
    @Path("getWorkPlaceCodes/{laborSystemAtr}")
    public WorkPlaceListCodesDto getEmploymentCodes(@PathParam("laborSystemAtr") int laborSystemAtr) {
        return this.agreeTimeOfWorkPlaceScreenProcessor.findAll(laborSystemAtr);
    }

    @POST
    @Path("get")
    public AgreementTimeOfWorkPlaceDto getAgrWorkPlace(RequestWorkPlace request) {
        return this.agreeTimeOfWorkPlaceScreenProcessor.findAgreeTimeOfWorkPlace(request);
    }

}
