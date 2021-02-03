package nts.uk.screen.at.ws.kmk.kmk008.k;

import nts.uk.screen.at.app.kmk.kmk008.basicAgrSetting.BasicAgrYearMonthSettingDto;
import nts.uk.screen.at.app.kmk.kmk008.basicAgrSetting.BasicAgrYearSettingDto;
import nts.uk.screen.at.app.kmk.kmk008.basicAgrSetting.InitialDisplayYearScreenProcessor;
import nts.uk.screen.at.app.kmk.kmk008.basicAgrSetting.RequestParam;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("screen/at/kmk008/k")
@Produces("application/json")
public class Kmk008KWebservice {

    @Inject
    private InitialDisplayYearScreenProcessor yearScreenProcessor;

    @POST
    @Path("getYear")
    public BasicAgrYearSettingDto getInitDisplayYear(RequestParam request) {
        return this.yearScreenProcessor.findYear(request);
    }

    @POST
    @Path("getYearMonth")
    public BasicAgrYearMonthSettingDto getInitDisplayYearMonth(RequestParam request) {
        return this.yearScreenProcessor.findYearMonth(request);
    }

}
