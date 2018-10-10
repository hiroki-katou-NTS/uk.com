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
    private SalaryPerUnitPriceNameFinder unitPriceNameFinder;

    @Inject
    private SalaryPerUnitPriceSettingFinder unitPriceSettingFinder;

    @Inject
    private AddSalaryPerUnitPriceNameCommandHandler addUnitPriceNameCommandHandler;

    @Inject
    private AddSalaryPerUnitPriceSettingCommandHandler addUnitPriceSettingCommandHandler;

    @Inject
    private UpdateSalaryPerUnitPriceNameCommandHandler updateUnitPriceNameCommandHandler;

    @Inject
    private UpdateSalaryPerUnitPriceSettingCommandHandler updateUnitPriceSettingCommandHandler;

    @Inject
    private RemoveSalaryPerUnitPriceNameCommandHandler removeUnitPriceNameCommandHandler;

    @Inject
    private RemoveSalaryPerUnitPriceSettingCommandHandler removeUnitPriceSettingCommandHandler;

    @POST
    @Path("getUnitPriceData/{cid}/{code}")
    public SalaryPerUnitPriceDataDto getStatementItemData(@PathParam("cid") String cid, @PathParam("code") String code) {
        SalaryPerUnitPriceNameDto salaryPerUnitPriceName = this.unitPriceNameFinder.getSalaryPerUnitPriceNameById(cid, code).orElse(null);
        SalaryPerUnitPriceSettingDto salaryPerUnitPriceSetting = this.unitPriceSettingFinder.getSalaryPerUnitPriceSettingById(cid, code).orElse(null);
        return new SalaryPerUnitPriceDataDto(salaryPerUnitPriceName, salaryPerUnitPriceSetting);
    }

    @POST
    @Path("getAllUnitPriceName/{isdisplayAbolition}")
    public List<SalaryPerUnitPriceNameDto> getAllUnitPriceName(@PathParam("isdisplayAbolition") boolean isdisplayAbolition) {
        List<SalaryPerUnitPriceNameDto> unitPriceNameList = this.unitPriceNameFinder.getAllSalaryPerUnitPriceName();
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
            this.addUnitPriceNameCommandHandler.handle(command.getSalaryPerUnitPriceName());
            this.addUnitPriceSettingCommandHandler.handle(command.getSalaryPerUnitPriceSetting());
        } else {
            this.updateUnitPriceNameCommandHandler.handle(command.getSalaryPerUnitPriceName());
            this.updateUnitPriceSettingCommandHandler.handle(command.getSalaryPerUnitPriceSetting());
        }
    }

    @POST
    @Path("removeUnitPriceData")
    public void removeStatementItemData(SalaryPerUnitPriceDataCommand command) {
        this.removeUnitPriceNameCommandHandler.handle(command.getSalaryPerUnitPriceName());
        this.removeUnitPriceSettingCommandHandler.handle(command.getSalaryPerUnitPriceSetting());
    }
}
