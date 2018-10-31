package nts.uk.ctx.pr.core.ws.wageprovision.unitpricename;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.unitpricename.*;
import nts.uk.ctx.pr.core.app.find.wageprovision.unitpricename.*;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import java.util.List;
import java.util.stream.Collectors;

@Path("ctx/pr/core/wageprovision/unitpricename")
@Produces("application/json")
public class UnitPriceNameWebService extends WebService {
    @Inject
    private SalaryPerUnitPriceFinder unitPriceFinder;

    @Inject
    private AddSalaryPerUnitPriceCommandHandler addUnitPriceCommandHandler;

    @Inject
    private UpdateSalaryPerUnitPriceCommandHandler updateUnitPriceCommandHandler;

    @Inject
    private RemoveSalaryPerUnitPriceCommandHandler removeUnitPriceCommandHandler;

    @POST
    @Path("getUnitPriceData/{cid}/{code}")
    public SalaryPerUnitPriceDataDto getStatementItemData(@PathParam("cid") String cid, @PathParam("code") String code) {
        return this.unitPriceFinder.getSalaryPerUnitPriceById(cid, code).orElse(null);
    }

    @POST
    @Path("getAllUnitPriceName/{isdisplayAbolition}")
    public List<SalaryPerUnitPriceNameDto> getAllUnitPriceName(@PathParam("isdisplayAbolition") boolean isdisplayAbolition) {
        List<SalaryPerUnitPriceNameDto> unitPriceNameList = this.unitPriceFinder.getAllSalaryPerUnitPriceName();
        if(isdisplayAbolition) {
            return unitPriceNameList;
        } else {
            return unitPriceNameList.stream().filter(item -> item.getAbolition() == 0).collect(Collectors.toList());
        }
    }

    @POST
    @Path("registerUnitPriceData")
    public void registerStatementItemData(SalaryPerUnitPriceDataCommand command) {
        if (command.isCheckCreate()) {
            this.addUnitPriceCommandHandler.handle(command);
        } else {
            this.updateUnitPriceCommandHandler.handle(command);
        }
    }

    @POST
    @Path("removeUnitPriceData")
    public void removeStatementItemData(SalaryPerUnitPriceDataCommand command) {
        this.removeUnitPriceCommandHandler.handle(command.getSalaryPerUnitPriceName());
    }
}
