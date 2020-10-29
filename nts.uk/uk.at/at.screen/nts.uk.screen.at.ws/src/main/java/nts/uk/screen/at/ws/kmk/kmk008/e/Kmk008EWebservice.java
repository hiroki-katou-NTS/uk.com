package nts.uk.screen.at.ws.kmk.kmk008.e;

import nts.uk.screen.at.app.kmk.kmk008.classification.AgreeTimeOfClassidicationScreenProcessor;
import nts.uk.screen.at.app.kmk.kmk008.classification.AgreementTimeClassificationDto;
import nts.uk.screen.at.app.kmk.kmk008.classification.RequestClassification;
import nts.uk.screen.at.app.kmk.kmk008.workplace.AgreeTimeOfWorkPlaceScreenProcessor;
import nts.uk.screen.at.app.kmk.kmk008.workplace.AgreementTimeOfWorkPlaceDto;
import nts.uk.screen.at.app.kmk.kmk008.workplace.RequestWorkPlace;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("screen/at/kmk008/e")
@Produces("application/json")
public class Kmk008EWebservice {

    @Inject
    private AgreeTimeOfClassidicationScreenProcessor findAgreeTimeOfWorkPlace;

    @POST
    @Path("get")
    public AgreementTimeClassificationDto getAgrClassification(RequestClassification request) {
        return this.findAgreeTimeOfWorkPlace.findAgreeTimeOfClassidication(request);
    }

}
