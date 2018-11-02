package nts.uk.ctx.pr.core.ws.wageprovision.statementbindingsetting;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateCorrelationHisDeparmentDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateLinkSettingDateDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateLinkSettingDateFinder;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.StateLinkSettingDate;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.Optional;

@Path("core/wageprovision/statementbindingsetting")
@Produces("application/json")
public class StateLinkSettingDateWebService extends WebService {

    @Inject
    private StateLinkSettingDateFinder stateLinkSettingDateFinder;

    @POST
    @Path("getStateLinkSettingDateById/{hisId}")
    public StateLinkSettingDateDto getStateLinkSettingDateById(@PathParam("hisId") String hisId){
        Optional<StateLinkSettingDateDto> stateLinkSettingDateDto = stateLinkSettingDateFinder.getStateLinkSettingDateById(hisId);
        if(stateLinkSettingDateDto.isPresent()){
            return stateLinkSettingDateDto.get();
        }
        return null;
    }
}
