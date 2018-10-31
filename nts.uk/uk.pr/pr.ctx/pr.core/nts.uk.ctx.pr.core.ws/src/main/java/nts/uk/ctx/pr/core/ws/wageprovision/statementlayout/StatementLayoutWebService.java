package nts.uk.ctx.pr.core.ws.wageprovision.statementlayout;

import nts.uk.ctx.pr.core.app.find.wageprovision.salaryindividualamountname.SalIndAmountNameDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.salaryindividualamountname.SalIndAmountNameFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("core/wageprovision/statementlayout")
@Produces("application/json")
public class StatementLayoutWebService {
    @Inject
    SalIndAmountNameFinder salIndAmountNameFinder;

    @POST
    @Path("getSalIndAmountName/{cateIndicator}")
    public List<SalIndAmountNameDto> getSalIndAmountName(@PathParam("cateIndicator") int cateIndicator ){
        return salIndAmountNameFinder.getAllSalIndAmountNameByCateIndi(cateIndicator);
    }
}
