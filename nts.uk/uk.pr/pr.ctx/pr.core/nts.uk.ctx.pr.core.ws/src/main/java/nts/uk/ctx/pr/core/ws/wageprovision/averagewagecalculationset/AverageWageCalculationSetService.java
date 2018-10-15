package nts.uk.ctx.pr.core.ws.wageprovision.averagewagecalculationset;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.find.wageprovision.averagewagecalculationset.AverageWageCalculationSetFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.averagewagecalculationset.DataDisplayAverageDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.averagewagecalculationset.StatementDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;


/**
 * @author thanh.tq
 */

@Path("ctx/pr/core/averagewagecalculationset")
@Produces("application/json")
public class AverageWageCalculationSetService extends WebService {

    @Inject
    private AverageWageCalculationSetFinder finder;

    @POST
    @Path("getStatemetItemData")
    public DataDisplayAverageDto getStatemetItem() {
        return this.finder.getStatemetItem();
    }

    @POST
    @Path("getStatementItemDataByCategory/{categoryAtr}")
    public List<StatementDto> getStatementItem(@PathParam("categoryAtr") Integer categoryAtr) {
        return this.finder.getStatemetItemByCategory(categoryAtr);
    }
}
