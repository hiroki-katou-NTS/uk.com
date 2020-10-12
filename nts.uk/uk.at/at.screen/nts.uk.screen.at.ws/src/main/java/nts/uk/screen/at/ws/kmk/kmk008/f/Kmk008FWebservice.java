package nts.uk.screen.at.ws.kmk.kmk008.f;

import nts.uk.screen.at.app.kmk.kmk008.employee.*;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("screen/at/kmk008/f")
@Produces("application/json")
public class Kmk008FWebservice {

    @Inject
    private AgreeMonthSetScreenProcessor agreeMonthSetScreenProcessor;

    @Inject
    private AgreeYearSetScreenProcessor agreeYearSetScreenProcessor;

    @POST
    @Path("getMonthSetting")
    public List<AgreementMonthSettingDto> getAgreeMonthSetting(Request request) {
        return this.agreeMonthSetScreenProcessor.find(request);
    }

    @POST
    @Path("getYearSetting")
    public List<AgreementYearSettingDto> getAgreeYearSetting(Request request) {
        return this.agreeYearSetScreenProcessor.find(request);
    }

}
