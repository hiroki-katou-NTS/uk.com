package nts.uk.screen.at.ws.kha.kha001.a;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.kha.InitKha001DisplayDto;
import nts.uk.screen.at.app.kha.InitialDisplayKhaScreenQuery;
import nts.uk.shr.com.context.AppContexts;

@Path("at/screen/kha001/query")
@Produces("application/json")
public class SupportOperationSettingGetWebService extends WebService {
    @Inject
    private InitialDisplayKhaScreenQuery displayKhaScreenQuery;

    @POST
    @Path("get-data")
    public InitKha001DisplayDto getData(){
        String cid = AppContexts.user().companyId();
        return displayKhaScreenQuery.get(cid);
    }
}
