package nts.uk.ctx.pr.core.ws.wageprovision.statebindingsetting;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.service.ConfirmPersonSetStatus;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.service.ConfirmPersonSetStatusService;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("core/wageprovision/statementbindingsetting")
@Produces("application/json")
public class ConOfIndiviSttWebService extends WebService {
    @Inject
    private ConfirmPersonSetStatusService confirmPersonSetStatusService;

    @POST
    @Path("getStatementLinkPerson")
    public List<ConfirmPersonSetStatus> getStatementLinkPerson(ConOfIndiviSttWebParam params) {
        return confirmPersonSetStatusService.getStatementLinkPerson(params.getEmpIds(), params.getBaseDate());
    }
}
