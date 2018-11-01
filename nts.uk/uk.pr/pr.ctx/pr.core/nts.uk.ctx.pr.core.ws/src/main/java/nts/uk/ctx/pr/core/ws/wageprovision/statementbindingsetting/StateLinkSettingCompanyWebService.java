package nts.uk.ctx.pr.core.ws.wageprovision.statementbindingsetting;


import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementbindingsetting.*;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateLinkSettingCompanyDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.StateLinkSettingCompanyFinder;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.RegisterMode;

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
    public StateLinkSettingCompanyDto getStateLinkSettingCompanyById(@PathParam("hisId") String hisId){
        Optional<StateLinkSettingCompanyDto> stateLinkSettingCompanyDto = stateLinkSettingCompanyFinder.getStateLinkSettingCompanyById(hisId);
        if(stateLinkSettingCompanyDto.isPresent()){
            return stateLinkSettingCompanyDto.get();
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
