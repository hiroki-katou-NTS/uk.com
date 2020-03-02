package nts.uk.ctx.pr.core.ws.wageprovision.statementitem.taxamount;

import java.util.List;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.pr.core.app.command.wageprovision.statementitem.*;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementitem.TaxExemptLimitFinder;
import nts.uk.ctx.pr.core.app.find.wageprovision.statementitem.TaxExemptionLimitDto;

/**
 * @author thanh.tq
 */
@Path("ctx/pr/core/taxamount")
@Produces("application/json")
public class TaxAmountWebService extends WebService {

    @Inject
    private TaxExemptLimitFinder taxExemptLimitFinder;

    @Inject
    private AddTaxExemptionLimitCommandHandler addTaxExemptionLimitCommandHandler;

    @Inject
    private UpdateTaxExemptionLimitCommandHandler updateTaxExemptionLimitCommandHandler;

    @Inject
    private RemoveTaxExemptionLimitCommandHandler removeTaxExemptionLimitCommandHandler;

    @POST
    @Path("getAllTaxAmountByCompanyId")
    public List<TaxExemptionLimitDto> getCodeConvertByCompanyId() {
        return this.taxExemptLimitFinder.getTaxExemptLimitByCompanyId();
    }

    @POST
    @Path("addTaxExemptLimit")
    public void addTaxExemptLimit(TaxExemptionLimitCommand command) {
        this.addTaxExemptionLimitCommandHandler.handle(command);
    }

    @POST
    @Path("updateTaxExemptLimit")
    public void updateTaxExemptLimit(TaxExemptionLimitCommand command) {
        this.updateTaxExemptionLimitCommandHandler.handle(command);
    }

    @POST
    @Path("removeTaxExemptLimit")
    public void removeTaxExemptLimit(TaxExemptionLimitCommand command) {
        this.removeTaxExemptionLimitCommandHandler.handle(command);
    }
}
