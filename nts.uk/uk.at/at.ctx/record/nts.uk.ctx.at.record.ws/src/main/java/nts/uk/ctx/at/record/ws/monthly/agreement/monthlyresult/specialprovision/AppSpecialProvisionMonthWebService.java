package nts.uk.ctx.at.record.ws.monthly.agreement.monthlyresult.specialprovision;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.monthly.agreement.monthlyresult.specialprovision.*;
import nts.uk.ctx.at.record.app.command.reservation.bento.*;

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
    private ApplyAppSpecialProvisionMonthCommandHandler aplyAppSpecialProvisionMonthCommandHandler;
    @Inject
    private ApplyAppSpecialProvisionYearCommandHandler applyAppSpecialProvisionYearCommandHandler;
    @Inject
    private DeleteAppSpecialProvisionCommandHandler deleteAppSpecialProvisionCommandHandler;
    @Inject
    private ApproveDenialAppSpecialProvisionApproverCommandHandler approveDenialAppSpecialProvisionApproverCommandHandler;
    @Inject
    private ApproveDenialAppSpecialProvisionConfirmerCommandHandler approveDenialAppSpecialProvisionConfirmerCommandHandler;
    @Inject
    private BulkApproveAppSpecialProvisionApproverCommandHandler bulkApproveAppSpecialProvisionApproverCommandHandler;
    @Inject
    private BulkApproveAppSpecialProvisionConfirmerCommandHandler bulkApproveAppSpecialProvisionConfirmerCommandHandler;

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

    @Path("apply-month")
    @POST
    public List<ErrorResultDto> applyMonth(List<ApplyAppSpecialProvisionMonthCommand> commands) {
        return this.aplyAppSpecialProvisionMonthCommandHandler.handle(commands);
    }

    @Path("apply-year")
    @POST
    public List<ErrorResultDto> applyYear(List<ApplyAppSpecialProvisionYearCommand> commands) {
        return this.applyAppSpecialProvisionYearCommandHandler.handle(commands);
    }

    @Path("delete")
    @POST
    public void delete(List<DeleteAppSpecialProvisionCommand> commands) {
        this.deleteAppSpecialProvisionCommandHandler.handle(commands);
    }

    @Path("approve-denial-approver")
    @POST
    public void approveDenialApprover(List<ApproveDenialAppSpecialProvisionApproverCommand> commands) {
        this.approveDenialAppSpecialProvisionApproverCommandHandler.handle(commands);
    }

    @Path("approve-denial-confirmer")
    @POST
    public void approveDenialConfirmer(List<ApproveDenialAppSpecialProvisionConfirmerCommand> commands) {
        this.approveDenialAppSpecialProvisionConfirmerCommandHandler.handle(commands);
    }

    @Path("bulk-approve-approver")
    @POST
    public void bulkApproveApprover(List<BulkApproveAppSpecialProvisionApproverCommand> commands) {
        this.bulkApproveAppSpecialProvisionApproverCommandHandler.handle(commands);
    }

    @Path("bulk-approve-confirmer")
    @POST
    public void bulkApproveConfirmer(List<BulkApproveAppSpecialProvisionConfirmerCommand> commands) {
        this.bulkApproveAppSpecialProvisionConfirmerCommandHandler.handle(commands);
    }
}
