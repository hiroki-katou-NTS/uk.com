package nts.uk.ctx.pr.core.ws.wageprovision.statementlayout;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout.StatementLayoutHistDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementlayout.StatementLayoutHistFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("core/wageprovision/statementlayout")
@Produces("application/json")
public class StatementLayoutWebService extends WebService {

    @Inject
    private StatementLayoutHistFinder mStatementLayoutHistFinder;

    @POST
    @Path("getAllStatementLayoutHist/{startYearMonth}")
    public List<StatementLayoutHistDto> getAllStatementLayoutHist(@PathParam("startYearMonth") String startYearMonth ){
        List<StatementLayoutHistDto> stateUseUnitSettingDto = mStatementLayoutHistFinder.getAllStatementLayoutHist(Integer.valueOf(startYearMonth));
        return stateUseUnitSettingDto;
    }


}

