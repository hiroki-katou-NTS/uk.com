package nts.uk.ctx.pr.shared.ws.socialinsurance.employeesociainsur;


import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurbeneinfo.AddEmpBasicPenNumInforCommandHandler;
import nts.uk.ctx.pr.shared.app.command.socialinsurance.employeesociainsur.emphealinsurbeneinfo.CredentialAcquisitionInfoCommand;
import nts.uk.ctx.pr.shared.dom.adapter.query.person.PersonInfoAdapter;
import nts.uk.ctx.pr.shared.dom.adapter.query.person.PersonInfoExportAdapter;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;

@Path("shared/employeesociainsur")
@Produces("application/json")
public class InforOnWelfPenInsurAccWebService extends WebService{

    @Inject
    private PersonInfoAdapter adapter;

    @Inject
    private AddEmpBasicPenNumInforCommandHandler commandHandler;


    @POST
    @Path("getPersonInfo/{empID}")
    public PersonInfoExportAdapter getPersonInfo(@PathParam("empID")String empID){
        return adapter.getPersonInfo(empID);
    }

    @POST
    @Path("add")
    public void add(CredentialAcquisitionInfoCommand command){
        commandHandler.handle(command);
    }


}
