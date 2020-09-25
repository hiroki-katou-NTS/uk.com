package nts.uk.screen.com.ws.cmm024;

import nts.arc.layer.ws.WebService;
import nts.uk.screen.com.app.query.PerformInitialDisplaysByCompanyScreenDto;
import nts.uk.screen.com.app.query.PerformInitialDisplaysByCompanyScreenQuery;

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