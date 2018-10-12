package nts.uk.ctx.pr.core.ws.wageprovision.individualwagecontract;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.find.wageprovision.salaryindividualamountname.SalIndAmountNameDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.salaryindividualamountname.SalIndAmountNameFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("ctx.pr.core.ws.wageprovision.individualwagecontract")
@Produces("application/json")
public class WageItemAcquisitionProcessWebService extends WebService {

    @Inject
    private SalIndAmountNameFinder salIndAmountNameFinder;

    @POST
    @Path("allSalIndAmountNameflowCateIndicator/{cateIndicator}")
    public List<SalIndAmountNameDto> getAllSalIndAmountNameflowCateIndicator(@PathParam("cateIndicator") int cateIndicator ){
        return salIndAmountNameFinder.getAllSalIndAmountName(cateIndicator);

    }
}
