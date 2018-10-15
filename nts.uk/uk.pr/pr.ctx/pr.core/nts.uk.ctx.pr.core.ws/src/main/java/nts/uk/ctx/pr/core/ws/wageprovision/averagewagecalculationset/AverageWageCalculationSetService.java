package nts.uk.ctx.pr.core.ws.wageprovision.averagewagecalculationset;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.averagewagecalculationset.DataDisplayAverageCommand;
import nts.uk.ctx.pr.core.app.command.wageprovision.averagewagecalculationset.UpdateAverageWageCalculationSetCommand;
import nts.uk.ctx.pr.core.app.find.wageprovision.averagewagecalculationset.AverageWageCalculationSetFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.averagewagecalculationset.DataDisplayAverageDto;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;


/**
 * @author thanh.tq
 */

@Path("ctx/pr/core/averagewagecalculationset")
@Produces("application/json")
public class AverageWageCalculationSetService extends WebService {

    @Inject
    private AverageWageCalculationSetFinder finder;

    @Inject
    private UpdateAverageWageCalculationSetCommand update;

    @POST
    @Path("getStatemetItemData")
    public DataDisplayAverageDto getStatemetItem() {
        return this.finder.getStatemetItem();
    }

    @POST
    @Path("registration")
    public void registration(DataDisplayAverageCommand command) {
        this.update.handle(command);
    }
}
