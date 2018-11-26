package nts.uk.ctx.pr.core.ws.wageprovision.statebindingsetting;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.ConOfIndiviSetSttDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementbindingsetting.ConOfIndiviSetSttFinder;
import nts.uk.ctx.pr.core.dom.wageprovision.statementbindingsetting.ConfirmOfIndividualSetSttParams;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("core/wageprovision/statementbindingsetting")
@Produces("application/json")
public class ConOfIndiviSttWebService extends WebService {

    @Inject
    private ConOfIndiviSetSttFinder mConOfIndiviSetSttFinder;
    @POST
    @Path("indiTiedStatAcquiProcess")
    public List<ConOfIndiviSetSttDto> indiTiedStatAcquiProcess(ConfirmOfIndividualSetSttParams params){
        String cid = AppContexts.user().companyId();
        return mConOfIndiviSetSttFinder.indiTiedStatAcquiProcess(params.getType(),params.getEmployeeIds(),params.getHisId(),params.getBaseDate());

    }
}
