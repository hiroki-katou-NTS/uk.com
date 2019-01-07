package nts.uk.ctx.pr.core.ws.wageprovision.statebindingsetting;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.UsageMaster;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.service.ConfirmPersonSetStatusDto;
import nts.uk.ctx.pr.core.dom.wageprovision.statebindingset.service.ConfirmPersonSetStatusService;
import nts.uk.shr.com.context.AppContexts;

import javax.inject.Inject;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("core/wageprovision/statementbindingsetting")
@Produces("application/json")
public class ConOfIndiviSttWebService extends WebService {
    @Inject
    private ConfirmPersonSetStatusService confirmPersonSetStatusService;

    @Path("getStatementLinkPerson")
    public List<ConfirmPersonSetStatusDto> getStatementLinkPerson(ConOfIndiviSttWebParam params) {
        String cid = AppContexts.user().companyId();
        return confirmPersonSetStatusService.getStatementLinkPerson(params.getEmpIds(), params.getBaseDate(),
                EnumAdaptor.valueOf(params.getType(), UsageMaster.class));
    }
}
