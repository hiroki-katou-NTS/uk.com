package nts.uk.ctx.pr.core.ws.wageprovision.individualwagecontract;

import nts.uk.ctx.pr.core.app.command.wageprovision.individualwagecontract.*;
import nts.uk.ctx.pr.core.app.find.wageprovision.individualwagecontract.SalIndAmountHisDisplayDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.individualwagecontract.SalIndAmountHisDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.individualwagecontract.SalIndAmountHisFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.individualwagecontract.SalIndAmountHisPackDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.salaryindividualamountname.SalIndAmountNameDto;
import nts.uk.ctx.pr.core.app.find.wageprovision.salaryindividualamountname.SalIndAmountNameFinder;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;

@Path("ctx/pr/core/ws/wageprovision/individualwagecontract")
@Produces("application/json")
public class IndividualWageContract {

    @Inject
    private SalIndAmountNameFinder salIndAmountNameFinder;

    @Inject
    private SalIndAmountHisFinder salIndAmountHisFinder;

    @Inject
    private RegisterIndividualWageContractCommandHandler commandHandler;

    @Inject
    private UpdateSalIndAmountCommandHandler updateSalIndAmountCommandHandler;

    @Inject
    private AddIndividualwagecontractCommandHandler addIndividualwagecontractCommandHandler;


    @POST
    @Path("getPersonalMoneyName/{cateIndicator}")
    public List<SalIndAmountNameDto> getPersonalMoneyName(@PathParam("cateIndicator") int cateIndicator) {
        return salIndAmountNameFinder.getAllSalIndAmountNameByCateIndi(cateIndicator);
    }

    @POST
    @Path("getSalIndAmountHis")
    public SalIndAmountHisPackDto getSalIndAmountHis(SalIndAmountHisDto dto) {
        return salIndAmountHisFinder.getSalIndAmountHis(dto);
    }

    @POST
    @Path("processYearFromEmp/{employmentCode}")
    public Integer processYearFromEmp(@PathParam("employmentCode") String employmentCode) {
        return salIndAmountHisFinder.processYearFromEmp(employmentCode);
    }

    @POST
    @Path("updateHistory")
    public void updateHistory(SalIndAmountCommand command) {
        updateSalIndAmountCommandHandler.handle(command);
    }


    //TODO ADD HISTORY
    @POST
    @Path("addHistory")
    public void addHistory(AddIndividualwagecontractCommand command) {
        addIndividualwagecontractCommandHandler.handle(command);
    }

    @POST
    @Path("salIndAmountHisDisplay")
    public List<SalIndAmountHisPackDto> salIndAmountHisDisplay(SalIndAmountHisDisplayDto dto) {
        return salIndAmountHisFinder.salIndAmountHisDisplay(dto);
    }
}
