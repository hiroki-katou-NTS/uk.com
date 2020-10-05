package nts.uk.ctx.at.record.ws.approver36agrbycompany;


import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.query.approver36agrbycompany.PerformInitialDisplaysScreenDto;
import nts.uk.ctx.at.record.app.query.approver36agrbycompany.PerformInitialDisplaysScreenQuery;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

/**
 * @author chinh.hm
 */
@Path("approve/bycompany")
@Produces("application/json")
public class PerformInitialDisplaysWebService extends WebService{

    @Inject
    private PerformInitialDisplaysScreenQuery screenQuery;

    @Path("initial")
    @POST
    public PerformInitialDisplaysScreenDto getDataInitials(){
        return screenQuery.getApprove36AerByCompany();
    }
}
