package nts.uk.ctx.pr.core.ws.wageprovision.statementbindingsetting;


import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.*;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateLinkSettingCompanyDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateLinkSettingCompanyFinder;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.Optional;

@Path("core/wageprovision/statementbindingsetting")
@Produces("application/json")
public class StateLinkSettingCompanyWebService extends WebService {

    @Inject
    private StateLinkSettingCompanyFinder stateLinkSettingCompanyFinder;

    @Inject
    private StateCorrelationHisCompanySettingCommandHandler stateCorrelationHisCompanySettingCommandHandler;

    @POST
    @Path("getStateLinkSettingCompanyById/{hisId}")
    public StateLinkSettingCompanyDto getStateLinkSettingCompanyById(@PathParam("hisId") String hisId) {
        Optional<StateLinkSettingCompanyDto> stateLinkSettingCompanyDto = stateLinkSettingCompanyFinder.getStateLinkSettingCompanyById(hisId);
        if (stateLinkSettingCompanyDto.isPresent()) {
            return stateLinkSettingCompanyDto.get();
        }
        return null;
    }

    @POST
    @Path("indiTiedStatAcquiProcess")
    public StateLinkSettingCompanyDto indiTiedStatAcquiProcess() {

        return null;
    }

    @POST
    @Path("getStateCorrelationHisClassification")
    public StateLinkSettingCompanyDto getStateCorrelationHisClassification() {
        String cid = AppContexts.user().companyId();
        return null;
    }

    @POST
    @Path("register")
    public void register(StateCorrelationHisCompanySettingCommand command) {
        stateCorrelationHisCompanySettingCommandHandler.handle(command);
    }

}
