package nts.uk.ctx.pr.core.ws.wageprovision.statementbindingsetting;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.ConfirmOfIndividualSetSttDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.ConfirmOfIndividualSetSttFinder;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.ConfirmOfIndividualSetSttParams;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("core/wageprovision/statementbindingsetting")
@Produces("application/json")
public class ConfirmOfIndividualSttWebService extends WebService {

    @Inject
    private ConfirmOfIndividualSetSttFinder mConfirmOfIndividualSetSttFinder;
    @POST
    @Path("indiTiedStatAcquiProcess")
    public List<ConfirmOfIndividualSetSttDto> indiTiedStatAcquiProcess(ConfirmOfIndividualSetSttParams params){
        String cid = AppContexts.user().companyId();
        return mConfirmOfIndividualSetSttFinder.indiTiedStatAcquiProcess(params.getType(),params.getEmployeeIds(),params.getHisId(),params.getBaseDate());

    }
}
