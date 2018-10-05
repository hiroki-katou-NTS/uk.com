package nts.uk.ctx.pr.core.ws.laborinsurance;

import nts.arc.layer.ws.WebService;

import nts.uk.ctx.pr.core.app.command.laborinsurance.AddOccAccIsPrRateCommand;
import nts.uk.ctx.pr.core.app.command.laborinsurance.AddOccAccIsPrRateCommandHandler;
import nts.uk.ctx.pr.core.app.command.laborinsurance.accident.AccInsurHisCommand;
import nts.uk.ctx.pr.core.app.command.laborinsurance.accident.AddOccAccIsHisCommandHandler;
import nts.uk.ctx.pr.core.app.command.laborinsurance.UpdateNameOfEachBusinessCommand;
import nts.uk.ctx.pr.core.app.command.laborinsurance.UpdateNameOfEachBusinessCommandHandler;
import nts.uk.ctx.pr.core.app.find.laborinsurance.*;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("core/monsalabonus/laborinsur")
@Produces("application/json")
public class OccAccidentInsurWebService extends WebService {

    @Inject
    private OccAccIsHisFinder occAccIsHisFinder ;
    @Inject
    private OccAccIsPrRateFinder occAccIsPrRateFinder;

    @Inject
    private AccInsurPreRateFinder accInsurPreRateFinder;

    @Inject
    private OccAccInsurBusFinder occAccInsurBusFinder;

    @Inject
    private AddOccAccIsPrRateCommandHandler addOccAccIsPrRateCommandHandler;

    @Inject
    private AddOccAccIsHisCommandHandler addOccAccIsHisCommandHandler;

    @Inject
    private UpdateNameOfEachBusinessCommandHandler updateNameOfEachBusinessCommandHandler;

    @POST
    @Path("getListOccAccIsHis")
    public  List<OccAccIsHisDto> getListOccAccIsHis() {
       return occAccIsHisFinder.getListEmplInsurHis();
    }

    @POST
    @Path("getOccAccIsPrRate/{hisId}")
    public List<OccAccIsPrRateDto> getOccAccIsPrRate(@PathParam("hisId") String hisId) {
        return occAccIsPrRateFinder.getAllOccAccIsPrRate(hisId);
    }

    @POST
    @Path("getOccAccInsurBus")
    public  List<OccAccInsurBusDto> getOccAccInsurBus() {
        return occAccInsurBusFinder.getOccAccInsurBus();
    }

    @POST
    @Path("updateOccAccInsurBus")
    public void updateOccAccInsurBus(UpdateNameOfEachBusinessCommand command){
        updateNameOfEachBusinessCommandHandler.handle(command);
    }

    @POST
    @Path("getAccInsurPreRate/{hisId}")
    public  List<AccInsurPreRateDto> getAccInsurPreRate(@PathParam("hisId") String hisId) {
        return accInsurPreRateFinder.getAccInsurPreRate(hisId);
    }

    @POST
    @Path("registerOccAccInsur")
    public void registerOccAccInsurPreRate(AddOccAccIsPrRateCommand command){
        addOccAccIsPrRateCommandHandler.handle(command);
    }

    @POST
    @Path("addOccAccIsHis")
    public void addOccAccIsHis(AccInsurHisCommand accInsurHisCommand){
        addOccAccIsHisCommandHandler.handle(accInsurHisCommand);
    }


}
