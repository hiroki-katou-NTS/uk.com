package nts.uk.screen.at.ws.kwr004.b;

import nts.uk.screen.at.app.kwr003.AttendanceItemInfoDto;
import nts.uk.screen.at.app.kwr004.AnnualWorkLedgerSettingDto;
import nts.uk.screen.at.app.kwr004.GetAnnualWorkLedgerSettingRequestParams;
import nts.uk.screen.at.app.kwr004.GetAnnualWorkLedgerSettingScreenQuery;
import nts.uk.screen.at.app.kwr004.b.*;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("at/screen/kwr004/b")
@Produces("application/json")
public class Kwr004BWebService {

    @Inject
    private AnnualOutputSettingProcessor outputSettingProcessor;

    @Inject
    private AttendenceItemInfoProcessor attendenceItemInfoProcessor;

    @Inject
    private GetAnnualWorkLedgerSettingScreenQuery query;

    @POST
    @Path("getInitDayMonth")
    public AttendanceItemInfoDto getInitDisplay(RequestParams requestParams) {
        return attendenceItemInfoProcessor.getInfo(requestParams);
    }

    @POST
    @Path("getDetail")
    public AnnualOutputSettingDto get(AnnualOutputSettingRequestParams requestParams) {
        return outputSettingProcessor.getDetailOutputSetting(requestParams);
    }

    @POST
    @Path("getSetting")
    public List<AnnualWorkLedgerSettingDto> get(GetAnnualWorkLedgerSettingRequestParams requestPrams) {
        return query.getSettings(requestPrams);
    }
}
