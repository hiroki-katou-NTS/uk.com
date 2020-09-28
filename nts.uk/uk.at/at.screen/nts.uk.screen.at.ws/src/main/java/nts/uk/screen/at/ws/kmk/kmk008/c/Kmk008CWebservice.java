package nts.uk.screen.at.ws.kmk.kmk008.c;

import nts.uk.screen.at.app.kmk.kmk008.employment.AgreeTimeOfEmploymentScreenProcessor;
import nts.uk.screen.at.app.kmk.kmk008.employment.AgreementTimeOfEmploymentDto;
import nts.uk.screen.at.app.kmk.kmk008.employment.RequestEmployment;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("screen/at/kmk008/c")
@Produces("application/json")
public class Kmk008CWebservice {

    @Inject
    private AgreeTimeOfEmploymentScreenProcessor employmentScreenProcessor;

    @POST
    @Path("get")
    public AgreementTimeOfEmploymentDto getAgreeOpeSetting(RequestEmployment request) {
        return this.employmentScreenProcessor.findAgreeTimeOfEmployment(request);
    }

}
