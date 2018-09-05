package nts.uk.ctx.exio.ws.monsalabonus.laborinsur;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.exio.app.find.monsalabonus.laborinsur.EmpInsurHisDto;


import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;


@Path("exio/monsalabonus/laborinsur")
@Produces("application/json")
public class WorkersCompenInsurWebService extends WebService {
    @Inject

    @POST
    @Path("getOccAccInsurBus")
    public List<EmpInsurHisDto> getOccAccInsurBus() {
        return empInsurHisFinder.getListEmplInsurHis();
    }


}
