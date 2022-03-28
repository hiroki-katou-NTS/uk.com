package nts.uk.ctx.at.record.ws.workrecord.stampmanagement.support;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.workrecord.stampmanagement.support.RegisterJudgmentCriteriaSettingScreenCommand;
import nts.uk.ctx.at.record.app.command.workrecord.stampmanagement.support.RegisterJudgmentCriteriaSettingScreenCommandHandler;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("at/ctx/record/stampmanagement")
@Produces("application/json")
public class RegisterJudgmentCriteriaSettingWebService extends WebService {

    @Inject
    private RegisterJudgmentCriteriaSettingScreenCommandHandler registerJudgmentCriteriaSettingScreenCommandHandler;

    @POST
    @Path("register-judgment-criteria")
    public void registerJudgmentCriteria(RegisterJudgmentCriteriaSettingScreenCommand command){
    	registerJudgmentCriteriaSettingScreenCommandHandler.handle(command);
    }
}
