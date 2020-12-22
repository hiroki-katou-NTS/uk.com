package nts.uk.ctx.at.record.ws.monthly.agreement.monthlyresult.specialprovision;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.monthly.agreement.monthlyresult.specialprovision.*;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import java.util.List;

/**
 * KAF021 Web service
 *
 * @author Le Huu Dat
 */
@Path("at/record/monthly/agreement/monthly-result/special-provision")
@Produces("application/json")
public class AppSpecialProvisionMonthWebService extends WebService {
    @Inject
    private RegisterAppSpecialProvisionMonthCommandHandler registerAppSpecialProvisionMonthCommandHandler;
    @Inject
    private RegisterAppSpecialProvisionYearCommandHandler registerAppSpecialProvisionYearCommandHandler;
    @Inject
    private ApplyAppSpecialProvisionCommandHandler aplyAppSpecialProvisionCommandHandler;
    @Inject
    private DeleteAppSpecialProvisionCommandHandler deleteAppSpecialProvisionCommandHandler;
    @Inject
    private ApproveDenialAppSpecialProvisionCommandHandler approveDenialAppSpecialProvisionCommandHandler;
    @Inject
    private BulkApproveAppSpecialProvisionCommandHandler bulkApproveAppSpecialProvisionCommandHandler;

    @Path("register-month")
    @POST
    public List<ErrorResultDto> registerMonth(List<RegisterAppSpecialProvisionMonthCommand> commands) {
        return this.registerAppSpecialProvisionMonthCommandHandler.handle(commands);
    }

    @Path("register-year")
    @POST
    public List<ErrorResultDto> registerYear(List<RegisterAppSpecialProvisionYearCommand> commands) {
        return this.registerAppSpecialProvisionYearCommandHandler.handle(commands);
    }

    @Path("apply")
    @POST
    public List<ErrorResultDto> applyMonth(List<ApplyAppSpecialProvisionCommand> commands) {
        return this.aplyAppSpecialProvisionCommandHandler.handle(commands);
    }

    @Path("delete")
    @POST
    public void delete(List<DeleteAppSpecialProvisionCommand> commands) {
        this.deleteAppSpecialProvisionCommandHandler.handle(commands);
    }

    @Path("approve-denial")
    @POST
    public void approveDenialApprover(ApproveDenialAppSpecialProvisionCommand command) {
        this.approveDenialAppSpecialProvisionCommandHandler.handle(command);
    }

    @Path("bulk-approve")
    @POST
    public void bulkApproveApprover(BulkApproveAppSpecialProvisionCommand command) {
        this.bulkApproveAppSpecialProvisionCommandHandler.handle(command);
    }
}
