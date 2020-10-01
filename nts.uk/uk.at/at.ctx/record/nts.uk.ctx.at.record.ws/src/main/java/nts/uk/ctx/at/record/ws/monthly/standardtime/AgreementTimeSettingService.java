package nts.uk.ctx.at.record.ws.monthly.standardtime;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.monthly.standardtime.classification.*;
import nts.uk.ctx.at.record.app.command.monthly.standardtime.company.RegisterTimeCompanyCommand;
import nts.uk.ctx.at.record.app.command.monthly.standardtime.company.RegisterTimeCompanyCommandHandler;
import nts.uk.ctx.at.record.app.command.monthly.standardtime.employment.*;
import nts.uk.ctx.at.record.app.command.monthly.standardtime.operationsetting.UpdateAgreeOperationSetCommand;
import nts.uk.ctx.at.record.app.command.monthly.standardtime.operationsetting.UpdateAgreeOperationSetCommandHandler;
import nts.uk.ctx.at.record.app.command.monthly.standardtime.unitofapprove.UpdateUnitSetOfApproveCommand;
import nts.uk.ctx.at.record.app.command.monthly.standardtime.unitofapprove.UpdateUnitSetOfApproveCommandHandler;
import nts.uk.ctx.at.record.app.command.monthly.standardtime.unitsetting.UpdateAgreeUnitSetCommand;
import nts.uk.ctx.at.record.app.command.monthly.standardtime.unitsetting.UpdateAgreeUnitSetCommandHandler;
import nts.uk.ctx.at.record.app.command.monthly.standardtime.workplace.*;

import javax.inject.Inject;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;

@Path("monthly/estimatedtime")
@Produces("application/json")
public class AgreementTimeSettingService extends WebService {

    @Inject
    private RegisterTimeCompanyCommandHandler addCompany;

    @Inject
    private RegisterTimeEmploymentCommandHandler addEmployment;

    @Inject
    private CopyTimeEmploymentCommandHandler copyEmployment;

    @Inject
    private DeleteTimeEmploymentCommandHandler deleteEloyment;

    @Inject
    private RegisterTimeWorkPlaceCommandHandler addWorkPlace;

    @Inject
    private CopyTimeWorkplaceCommandHandler copyWorkPlace;

    @Inject
    private DeleteTimeWorkplaceCommandHandler deleteWorkPlace;

    @Inject
    private RegisterTimeClassificationCommandHandler addClassification;

    @Inject
    private CopyTimeClassificationCommandHandler copyClassification;

    @Inject
    private DeleteTimeClassificationCommandHandler deleteClassification;

    @Inject
    private UpdateAgreeOperationSetCommandHandler updateOperationSet;

    @Inject
    private UpdateAgreeUnitSetCommandHandler updateUnit;

    @Inject
    private UpdateUnitSetOfApproveCommandHandler unitSetOfApproveCommandHandler;

    @POST
    @Path("company/add")
    public void addCompany(RegisterTimeCompanyCommand command) {
        this.addCompany.handle(command);
    }

    @POST
    @Path("employment/add")
    public void addEmployment(RegisterTimeEmploymentCommand command) {
        this.addEmployment.handle(command);
    }

    @POST
    @Path("employment/copy")
    public void copyEmployment(CopyTimeEmploymentCommand command) {
        this.copyEmployment.handle(command);
    }

    @POST
    @Path("employment/delete")
    public void deleteEmployment(DeleteTimeEmploymentCommand command) {
        this.deleteEloyment.handle(command);
    }

    @POST
    @Path("workplace/add")
    public void addWorkplace(RegisterTimeWorkPlaceCommand command) {
        this.addWorkPlace.handle(command);
    }

    @POST
    @Path("workplace/copy")
    public void copyWorkplace(CopyTimeWorkplaceCommand command) {
        this.copyWorkPlace.handle(command);
    }

    @POST
    @Path("workplace/delete")
    public void deleteWorkplace(DeleteTimeWorkplaceCommand command) {
        this.deleteWorkPlace.handle(command);
    }

    @POST
    @Path("classification/add")
    public void addClassification(RegisterTimeClassificationCommand command) {
        this.addClassification.handle(command);
    }

    @POST
    @Path("classification/copy")
    public void copyClassification(CopyTimeClassificationCommand command) {
        this.copyClassification.handle(command);
    }

    @POST
    @Path("classification/delete")
    public void deleteClassification(DeleteTimeClassificationCommand command) {
        this.deleteClassification.handle(command);
    }

    @POST
    @Path("operationSet/update")
    public void updateOperationSet(UpdateAgreeOperationSetCommand command) {
        this.updateOperationSet.handle(command);
    }

    @POST
    @Path("unit/update")
    public void updateUnit(UpdateAgreeUnitSetCommand command) {
        this.updateUnit.handle(command);
    }

    @POST
    @Path("unitOfApprove/update")
    public void updateUnit(UpdateUnitSetOfApproveCommand command) {
        this.unitSetOfApproveCommandHandler.handle(command);
    }

}
