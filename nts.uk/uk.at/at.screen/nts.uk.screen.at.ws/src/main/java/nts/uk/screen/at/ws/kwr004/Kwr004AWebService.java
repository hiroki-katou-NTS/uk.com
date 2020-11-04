package nts.uk.screen.at.ws.kwr004;

import nts.uk.screen.at.app.kwr004.AnnualWorkLedgerSettingDto;
import nts.uk.screen.at.app.kwr004.GetAnnualWorkLedgerSettingRequestParams;
import nts.uk.screen.at.app.kwr004.GetAnnualWorkLedgerSettingScreenQuery;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("screen/at/kwr004/a")
@Produces("application/json")
public class Kwr004AWebService {

    @Inject
    private GetAnnualWorkLedgerSettingScreenQuery query;

    @POST
    @Path("getSetting")
    public List<AnnualWorkLedgerSettingDto> get(GetAnnualWorkLedgerSettingRequestParams requestPrams) {
        return query.getSettings(requestPrams);
    }
}
