package nts.uk.screen.at.ws.kmk.kmk008.b;

import nts.uk.screen.at.app.ksm003.find.AgreeTimeOfCompanyScreenProcessor;
import nts.uk.screen.at.app.ksm003.find.AgreementTimeOfCompanyDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

/**
 * 画面表示を行う
 */
@Path("screen/at/kmk008/b")
@Produces("application/json")
public class Kmk008BWebservice {

    @Inject
    private AgreeTimeOfCompanyScreenProcessor findDataAgreeOpeSet;

    @POST
    @Path("get/{type}")
    public AgreementTimeOfCompanyDto getAgreeOpeSetting(@PathParam("type") int laborSystemAtr) {
        return this.findDataAgreeOpeSet.findAgreeTimeOfCompany(laborSystemAtr);
    }

}
