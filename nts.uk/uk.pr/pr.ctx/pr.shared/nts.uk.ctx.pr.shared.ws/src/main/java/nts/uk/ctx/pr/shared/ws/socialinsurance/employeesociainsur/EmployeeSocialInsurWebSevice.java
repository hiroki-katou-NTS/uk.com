package nts.uk.ctx.pr.shared.ws.socialinsurance.employeesociainsur;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.shared.app.find.payrollgeneralpurposeparameters.SalGenParaIdentificationDto;
import nts.uk.ctx.pr.shared.app.find.payrollgeneralpurposeparameters.SalGenParaIdentificationFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;


@Path("shared/employeesociainsur")
@Produces("application/json")
public class EmployeeSocialInsurWebSevice extends WebService {

    @Inject
    private SalGenParaIdentificationFinder mSalGenParaIdentificationFinder;



    @POST
    @Path("getAllSalGenParaIdentification")
    public List<SalGenParaIdentificationDto> getAllSalGenParaIdentification() {
        return mSalGenParaIdentificationFinder.getAllSalGenParaIdentification();
    }

}