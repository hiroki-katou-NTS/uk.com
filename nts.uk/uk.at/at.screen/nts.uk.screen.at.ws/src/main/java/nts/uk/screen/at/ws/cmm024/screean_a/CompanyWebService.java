package nts.uk.screen.at.ws.cmm024.screean_a;


import nts.arc.layer.ws.WebService;
import nts.uk.screen.at.app.query.cmm024.approver36agrbycompany.PerformInitialDisplaysByCompanyScreenDto;
import nts.uk.screen.at.app.query.cmm024.approver36agrbycompany.PerformInitialDisplaysByCompanyScreenQuery;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("approve/company")
@Produces("application/json")
public class CompanyWebService extends WebService {


    @Inject
    private PerformInitialDisplaysByCompanyScreenQuery screenQuery;

    @Path("initial")
    @POST
    public PerformInitialDisplaysByCompanyScreenDto getDataInitials(){
        return screenQuery.getApprove36AerByCompany();
    }



}