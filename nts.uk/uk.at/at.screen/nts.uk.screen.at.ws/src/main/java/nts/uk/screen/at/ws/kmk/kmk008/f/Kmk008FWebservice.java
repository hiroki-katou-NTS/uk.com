package nts.uk.screen.at.ws.kmk.kmk008.f;

import nts.uk.screen.at.app.kmk.kmk008.employee.*;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("screen/at/kmk008/f")
@Produces("application/json")
public class Kmk008FWebservice {

    @Inject
    private AgreeMonthSetScreenProcessor agreeMonthSetScreenProcessor;

    @Inject
    private AgreeYearSetScreenProcessor agreeYearSetScreenProcessor;

    @POST
    @Path("getMonthSetting")
    public AgreementMonthSettingDto getAgreeMonthSetting(Request requestMonth) {
        return this.agreeMonthSetScreenProcessor.find(requestMonth);
    }

    @POST
    @Path("getYearSetting")
    public AgreementYearSettingDto getAgreeYearSetting(Request request) {
        return this.agreeYearSetScreenProcessor.find(request);
    }

}
