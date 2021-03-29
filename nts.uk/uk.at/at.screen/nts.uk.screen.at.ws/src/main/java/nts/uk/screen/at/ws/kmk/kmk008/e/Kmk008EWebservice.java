package nts.uk.screen.at.ws.kmk.kmk008.e;

import nts.uk.screen.at.app.kmk.kmk008.classification.AgreeTimeOfClassificationScreenProcessor;
import nts.uk.screen.at.app.kmk.kmk008.classification.AgreementTimeClassificationDto;
import nts.uk.screen.at.app.kmk.kmk008.classification.ClassificationCodesDto;
import nts.uk.screen.at.app.kmk.kmk008.classification.RequestClassification;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("screen/at/kmk008/e")
@Produces("application/json")
public class Kmk008EWebservice {

    @Inject
    private AgreeTimeOfClassificationScreenProcessor findAgreeTimeOfWorkPlace;

    @POST
    @Path("getClassificationCodes/{laborSystemAtr}")
    public ClassificationCodesDto getEmploymentCodes(@PathParam("laborSystemAtr") int laborSystemAtr) {
        return this.findAgreeTimeOfWorkPlace.findAll(laborSystemAtr);
    }

    @POST
    @Path("get")
    public AgreementTimeClassificationDto getAgrClassification(RequestClassification request) {
        return this.findAgreeTimeOfWorkPlace.findAgreeTimeOfClassidication(request);
    }

}
