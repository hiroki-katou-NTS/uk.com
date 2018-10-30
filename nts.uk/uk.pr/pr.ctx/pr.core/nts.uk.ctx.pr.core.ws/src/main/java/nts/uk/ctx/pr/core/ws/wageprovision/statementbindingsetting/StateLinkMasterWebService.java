package nts.uk.ctx.pr.core.ws.wageprovision.statementbindingsetting;


import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateLinkSettingMasterDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateLinkSettingMasterFinder;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("core/wageprovision/statementbindingsetting")
@Produces("application/json")
public class StateLinkMasterWebService extends WebService {

    @Inject
    private StateLinkSettingMasterFinder stateLinkSettingMasterFinder;

    @POST
    @Path("getStateLinkMaster")
    public List<StateLinkSettingMasterDto> getStateLinkMaster(@PathParam("hisId") String hisId) {
        return stateLinkSettingMasterFinder.getStateLinkSettingMasterByHisId(hisId);
    }

    @POST
    @Path("getStatementName")
    public List<StateLinkSettingMasterDto> getSpecName(@PathParam("hisId") String hisId) {
        //return stateLinkSettingMasterFinder.getStateLinkSettingMasterByHisId(hisId);
        return null;
    }

}
