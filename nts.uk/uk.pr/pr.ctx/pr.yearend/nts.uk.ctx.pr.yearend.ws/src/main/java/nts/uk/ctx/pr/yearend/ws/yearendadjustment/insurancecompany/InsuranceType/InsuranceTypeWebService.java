package nts.uk.ctx.pr.yearend.ws.yearendadjustment.insurancecompany.InsuranceType;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.yearend.app.command.yearendadjustment.insurancecompany.insurancetype.AddInsuranceTypeCommandHandler;
import nts.uk.ctx.pr.yearend.app.command.yearendadjustment.insurancecompany.insurancetype.InsuranceTypeCommand;
import nts.uk.ctx.pr.yearend.app.command.yearendadjustment.insurancecompany.insurancetype.RemoveInsuranceTypeCommandHandler;
import nts.uk.ctx.pr.yearend.app.command.yearendadjustment.insurancecompany.insurancetype.UpdateInsuranceTypeCommandHandler;
import nts.uk.ctx.pr.yearend.app.find.yearendadjustment.insurancecompany.insurancetype.InsuranceTypeDto;
import nts.uk.ctx.pr.yearend.app.find.yearendadjustment.insurancecompany.insurancetype.InsuranceTypeFinder;
import nts.uk.ctx.pr.yearend.app.find.yearendadjustment.insurancecompany.lifeinsurance.LifeInsuranceDto;
import nts.uk.ctx.pr.yearend.app.find.yearendadjustment.insurancecompany.lifeinsurance.LifeInsuranceFinder;
import nts.uk.ctx.pr.yearend.dom.yearendadjustment.insurancecompany.insuranceType.InsuranceType;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;


/**
 * @author thanh.tq
 */

@Path("ctx/pr/yearend/insuranceType")
@Produces("application/json")
public class InsuranceTypeWebService extends WebService {

    @Inject
    private InsuranceTypeFinder finder;

    @Inject
    private AddInsuranceTypeCommandHandler add;

    @Inject
    private UpdateInsuranceTypeCommandHandler update;

    @Inject
    private RemoveInsuranceTypeCommandHandler remove;

    @POST
    @Path("getInsuranceType/{lifeInsuranceCode}")
    public List<InsuranceTypeDto> getInsuranceType(@PathParam("lifeInsuranceCode") String lifeInsuranceCode) {
        return this.finder.getAllInsuranceType(lifeInsuranceCode);
    }

    @POST
    @Path("addInsuranceType")
    public void addInsuranceType(InsuranceTypeCommand command) {
        this.add.handle(command);
    }

    @POST
    @Path("updateInsuranceType")
    public void updateInsuranceType(InsuranceTypeCommand command) {
        this.update.handle(command);
    }

    @POST
    @Path("removeInsuranceType")
    public void removeInsuranceType(InsuranceTypeCommand command) {
        this.remove.handle(command);
    }
}
