package nts.uk.ctx.pr.yearend.ws.yearendadjustment.insurancecompany.earthquakeinsurance;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.yearend.app.command.yearendadjustment.insurancecompany.earthquakeinsurance.AddEarthquakeInsuranceCommandHandler;
import nts.uk.ctx.pr.yearend.app.command.yearendadjustment.insurancecompany.earthquakeinsurance.EarthquakeInsuranceCommand;
import nts.uk.ctx.pr.yearend.app.command.yearendadjustment.insurancecompany.earthquakeinsurance.RemoveEarthquakeInsuranceCommandHandler;
import nts.uk.ctx.pr.yearend.app.command.yearendadjustment.insurancecompany.earthquakeinsurance.UpdateEarthquakeInsuranceCommandHandler;
import nts.uk.ctx.pr.yearend.app.find.yearendadjustment.insurancecompany.earthquakeinsurance.EarthquakeInsuranceDto;
import nts.uk.ctx.pr.yearend.app.find.yearendadjustment.insurancecompany.earthquakeinsurance.EarthquakeInsuranceFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;


/**
 * @author thanh.tq
 */

@Path("ctx/pr/yearend/earthquake")
@Produces("application/json")
public class EarthQuakeInsuranceWebService extends WebService {

    @Inject
    private EarthquakeInsuranceFinder finder;

    @Inject
    private AddEarthquakeInsuranceCommandHandler add;

    @Inject
    private UpdateEarthquakeInsuranceCommandHandler update;

    @Inject
    private RemoveEarthquakeInsuranceCommandHandler remove;

    @POST
    @Path("getEarthquake")
    public List<EarthquakeInsuranceDto> getEarthquake() {
        return this.finder.getEarthquakeInsuranceByCid();
    }

    @POST
    @Path("addEarthquake")
    public void addEarthquake(EarthquakeInsuranceCommand command) {
        this.add.handle(command);
    }

    @POST
    @Path("updateEarthquake")
    public void updateEarthquake(EarthquakeInsuranceCommand command) {
        this.update.handle(command);
    }

    @POST
    @Path("removeEarthquake")
    public void removeEarthquake(EarthquakeInsuranceCommand command) {
        this.remove.handle(command);
    }
}
