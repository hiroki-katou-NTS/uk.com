package nts.uk.ctx.pr.yearend.ws.yearendadjustment.insurancecompany.lifeInsurance;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.yearend.app.command.yearendadjustment.insurancecompany.lifeinsurance.AddLifeInsuranceCommandHandler;
import nts.uk.ctx.pr.yearend.app.command.yearendadjustment.insurancecompany.lifeinsurance.LifeInsuranceCommand;
import nts.uk.ctx.pr.yearend.app.command.yearendadjustment.insurancecompany.lifeinsurance.RemoveLifeInsuranceCommandHandler;
import nts.uk.ctx.pr.yearend.app.command.yearendadjustment.insurancecompany.lifeinsurance.UpdateLifeInsuranceCommandHandler;
import nts.uk.ctx.pr.yearend.app.find.yearendadjustment.insurancecompany.lifeinsurance.LifeInsuranceDto;
import nts.uk.ctx.pr.yearend.app.find.yearendadjustment.insurancecompany.lifeinsurance.LifeInsuranceFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;



/**
 * @author thanh.tq
 */

@Path("ctx/pr/yearend/lifeInsurance")
@Produces("application/json")
public class LifeInsuranceWebService extends WebService {

    @Inject
    private LifeInsuranceFinder finder;

    @Inject
    private AddLifeInsuranceCommandHandler add;

    @Inject
    private UpdateLifeInsuranceCommandHandler update;

    @Inject
    private RemoveLifeInsuranceCommandHandler remove;

    @POST
    @Path("getLifeInsuranceData")
    public List<LifeInsuranceDto> getLifeInsuranceData() {
        return this.finder.getLifeInsuranceBycId();
    }

    @POST
    @Path("addLifeInsurance")
    public void addLifeInsurance(LifeInsuranceCommand command) {
        this.add.handle(command);
    }

    @POST
    @Path("updateLifeInsurance")
    public void updateLifeInsurance(LifeInsuranceCommand command) {
        this.update.handle(command);
    }

    @POST
    @Path("removeLifeInsurance")
    public void removeLifeInsurance(LifeInsuranceCommand command) {
        this.remove.handle(command);
    }

}
