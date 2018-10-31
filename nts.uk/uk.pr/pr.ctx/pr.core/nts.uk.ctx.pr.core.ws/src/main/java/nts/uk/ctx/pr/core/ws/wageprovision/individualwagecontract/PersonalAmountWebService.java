package nts.uk.ctx.pr.core.ws.wageprovision.individualwagecontract;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.individualwagecontract.SalIndAmountByPerValCodeCommand;
import nts.uk.ctx.pr.core.app.find.wageprovision.individualwagecontract.EmployeeInfoImportDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.individualwagecontract.SalIndAmountHissAndSalIndAmountFinder;
import nts.uk.ctx.pr.core.dom.wageprovision.individualwagecontract.PersonalAmount;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

@Path("ctx.pr.core.ws.wageprovision.individualwagecontract")
@Produces("application/json")
public class PersonalAmountWebService  extends WebService {

    @Inject
    SalIndAmountHissAndSalIndAmountFinder finder;

    @POST
    @Path("salIndAmountHisByPeValCode")
    public EmployeeInfoImportDto salIndAmountHisByPeValCode(SalIndAmountByPerValCodeCommand command){
        return this.finder.getPersonalAmounts(command);
    }
}
