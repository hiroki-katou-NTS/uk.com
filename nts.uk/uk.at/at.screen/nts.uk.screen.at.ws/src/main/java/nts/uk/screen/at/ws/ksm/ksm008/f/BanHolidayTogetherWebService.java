package nts.uk.screen.at.ws.ksm.ksm008.f;

import nts.uk.screen.at.app.ksm008.BanHolidayTogether.StartupInfoBanHolidayDto;
import nts.uk.screen.at.app.ksm008.BanHolidayTogether.StartupInfoBanHolidayScreenQuery;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("screen/at/ksm008/f")
@Produces("application/json")
public class BanHolidayTogetherWebService {
    @Inject
    private StartupInfoBanHolidayScreenQuery startupInfoBanHolidayScreenQuery;

    @POST
    @Path("getStartupInfoBanHoliday")
    public StartupInfoBanHolidayDto getStartupInfoBanHoliday(getStartupInfoBanHolidayParam param) {
        return startupInfoBanHolidayScreenQuery.getStartupInfoBanHoliday(param.getCode());
    }
}
