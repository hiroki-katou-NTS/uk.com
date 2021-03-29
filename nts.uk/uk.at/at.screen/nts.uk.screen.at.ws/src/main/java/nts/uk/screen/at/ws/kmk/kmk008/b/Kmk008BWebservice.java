package nts.uk.screen.at.ws.kmk.kmk008.b;

import nts.uk.screen.at.app.kmk.kmk008.company.AgreeTimeOfCompanyScreenProcessor;
import nts.uk.screen.at.app.kmk.kmk008.company.AgreementTimeOfCompanyDto;
import nts.uk.screen.at.app.kmk.kmk008.company.RequestCompany;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("screen/at/kmk008/b")
@Produces("application/json")
public class Kmk008BWebservice {

    @Inject
    private AgreeTimeOfCompanyScreenProcessor findDataAgreeOpeSet;

    @POST
    @Path("get")
    public AgreementTimeOfCompanyDto getAgreeOpeSetting(RequestCompany requestCompany) {
        return this.findDataAgreeOpeSet.findAgreeTimeOfCompany(requestCompany);
    }

}
