package nts.uk.ctx.pr.core.ws.wageprovision.individualwagecontract;

import nts.arc.layer.ws.WebService;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.pr.core.app.find.wageprovision.individualwagecontract.EmployeeInformationAcquisitionProcessingFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.individualwagecontract.PerProcesClsSetDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.individualwagecontract.PerProcesClsSetFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls.CurrProcessDateDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls.CurrProcessDateFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls.SetDaySupportDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.processdatecls.SetDaySupportFinder;
import org.junit.jupiter.api.Test;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("ctx.pr.core.ws.wageprovision.individualwagecontract")
@Produces("application/json")
public class EmployeeInformationAcquisitionProcessingWebService extends WebService {


    @Inject
    EmployeeInformationAcquisitionProcessingFinder employeeInformationAcquisitionProcessingFinder;



    @POST
    @Path("employeeReferenceDate")
    public SetDaySupportDto getEmpExtRefDate(){
        return employeeInformationAcquisitionProcessingFinder.getEmpExtRefDate();
    }



}
