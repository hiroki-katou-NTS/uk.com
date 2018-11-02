package nts.uk.ctx.pr.core.ws.wageprovision.statementbindingsetting;


import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.AddStateCorrelationHisCompanySettingCommandHandler;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.StateCorrelationHisCompanySettingCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.UpdateStateLinkSettingCompanyCommandHandler;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateLinkSettingCompanyFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StatementLayoutDto;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.RegisterMode;
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
    private AddStateCorrelationHisCompanySettingCommandHandler addStateCorrelationHisCompanySettingCommandHandler;

    @Inject
    private UpdateStateLinkSettingCompanyCommandHandler updateStateLinkSettingCompanyCommandHandler;

    @POST
    @Path("getStateLinkSettingCompanyById/{hisId}")
    public StatementLayoutDto getStateLinkSettingCompanyById(@PathParam("hisId") String hisId){
        String cid = AppContexts.user().companyId();
        Optional<StatementLayoutDto> statementLayoutDto = stateLinkSettingCompanyFinder.getStateLinkSettingCompanyById(cid,hisId);
        if(statementLayoutDto.isPresent()){
            return statementLayoutDto.get();
        }
        return null;
    }

    @POST
    @Path("register")
    public void register(StateCorrelationHisCompanySettingCommand command){
        if(command.getMode() == RegisterMode.NEW.value){
            addStateCorrelationHisCompanySettingCommandHandler.handle(command);
        }else if(command.getMode() == RegisterMode.UPDATE.value){
            updateStateLinkSettingCompanyCommandHandler.handle(command);
        }

    }
}
