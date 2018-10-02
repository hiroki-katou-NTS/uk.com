package wageprovision.companyuniformamount;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import nts.arc.layer.ws.WebService;


@Path("core/monsalabonus/laborinsur")
@Produces("application/json")
public class PayrollUnitPriceHisService extends WebService {



    @POST
    @Path("getListOccAccIsHis")
    public  void getListOccAccIsHis() {

    }
//
//    @POST
//    @Path("getOccAccIsPrRate/{hisId}")
//    public List<OccAccIsPrRateDto> getOccAccIsPrRate(@PathParam("hisId") String hisId) {
//        return occAccIsPrRateFinder.getAllOccAccIsPrRate(hisId);
//    }
//
//    @POST
//    @Path("getOccAccInsurBus")
//    public  List<OccAccInsurBusDto> getOccAccInsurBus() {
//        return occAccInsurBusFinder.getOccAccInsurBus();
//    }
//
//    @POST
//    @Path("updateOccAccInsurBus")
//    public void updateOccAccInsurBus(UpdateNameOfEachBusinessCommand command){
//        updateNameOfEachBusinessCommandHandler.handle(command);
//    }
//
//    @POST
//    @Path("getAccInsurPreRate/{hisId}")
//    public  List<AccInsurPreRateDto> getAccInsurPreRate(@PathParam("hisId") String hisId) {
//        return accInsurPreRateFinder.getAccInsurPreRate(hisId);
//    }
//
//    @POST
//    @Path("registerOccAccInsur")
//    public void registerOccAccInsurPreRate(AddOccAccIsPrRateCommand command){
//        addOccAccIsPrRateCommandHandler.handle(command);
//    }
//
//    @POST
//    @Path("addOccAccIsHis")
//    public void addOccAccIsHis(AccInsurHisCommand accInsurHisCommand){
//        addOccAccIsHisCommandHandler.handle(accInsurHisCommand);
//    }


}
