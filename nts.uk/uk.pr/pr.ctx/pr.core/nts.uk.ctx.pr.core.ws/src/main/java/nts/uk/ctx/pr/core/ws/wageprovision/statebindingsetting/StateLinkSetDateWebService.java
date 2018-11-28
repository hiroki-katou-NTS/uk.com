package nts.uk.ctx.pr.core.ws.wageprovision.statebindingsetting;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset.StateLinkSetDateDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statebindingset.StateLinkSetDateFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.Optional;

@Path("core/wageprovision/statementbindingsetting")
@Produces("application/json")
public class StateLinkSetDateWebService extends WebService {

    @Inject
    private StateLinkSetDateFinder stateLinkSetDateFinder;

    @POST
    @Path("getStateLinkSettingDateById/{hisId}")
    public StateLinkSetDateDto getStateLinkSettingDateById(@PathParam("hisId") String hisId){
        Optional<StateLinkSetDateDto> stateLinkSettingDateDto = stateLinkSetDateFinder.getStateLinkSettingDateById(hisId);
        if(!stateLinkSettingDateDto.isPresent()){
            return null;
        }
        return stateLinkSettingDateDto.get();
    }
}
