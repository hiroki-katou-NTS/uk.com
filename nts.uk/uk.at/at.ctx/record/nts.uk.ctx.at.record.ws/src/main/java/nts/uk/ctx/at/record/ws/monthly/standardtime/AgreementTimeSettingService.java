package nts.uk.ctx.at.record.ws.monthly.standardtime;

import nts.arc.layer.ws.WebService;
import nts.uk.ctx.at.record.app.command.monthly.standardtime.classification.*;
import nts.uk.ctx.at.record.app.command.monthly.standardtime.company.RegisterTimeCompanyCommand;
import nts.uk.ctx.at.record.app.command.monthly.standardtime.company.RegisterTimeCompanyCommandHandler;
import nts.uk.ctx.at.record.app.command.monthly.standardtime.employment.*;
import nts.uk.ctx.at.record.app.command.monthly.standardtime.monthSetting.*;
import nts.uk.ctx.at.record.app.command.monthly.standardtime.operationsetting.RegisterAgreeOperationSetCommand;
import nts.uk.ctx.at.record.app.command.monthly.standardtime.operationsetting.RegisterAgreeOperationSetCommandHandler;
import nts.uk.ctx.at.record.app.command.monthly.standardtime.unitofapprove.RegisterUnitSetOfApproveCommand;
import nts.uk.ctx.at.record.app.command.monthly.standardtime.unitofapprove.RegisterUnitSetOfApproveCommandHandler;
import nts.uk.ctx.at.record.app.command.monthly.standardtime.unitsetting.RegisterAgreeUnitSetCommand;
import nts.uk.ctx.at.record.app.command.monthly.standardtime.unitsetting.RegisterAgreeUnitSetCommandHandler;
import nts.uk.ctx.at.record.app.command.monthly.standardtime.workplace.*;
import nts.uk.ctx.at.record.app.command.monthly.standardtime.yearSetting.*;

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
    private RegisterAgreeOperationSetCommandHandler registerOperationSet;

    @Inject
    private RegisterAgreeUnitSetCommandHandler RegisterUnit;

    @Inject
    private RegisterUnitSetOfApproveCommandHandler unitSetOfApproveCommandHandler;

    @Inject
    private RegisterAgrYearSettingCommandHandler registerAgrYearSettingCommandHandler;

    @Inject
    private UpdateAgrYearSettingCommandHandler updateAgrYearSettingCommandHandler;

    @Inject
    private DeleteAgrYearSettingCommandHandler deleteAgrYearSettingCommandHandler;

    @Inject
    private RegisterAgrMonthSettingCommandHandler registerAgrMonthSettingCommandHandler;

    @Inject
    private UpdateAgrMonthSettingCommandHandler updateAgrMonthSettingCommandHandler;

    @Inject
    private DeleteAgrMonthSettingCommandHandler deleteAgrMonthSettingCommandHandler;

    /**
     * Screen B
     */
    @POST
    @Path("company/add")
    public void addCompany(RegisterTimeCompanyCommand command) {
        this.addCompany.handle(command);
    }

    /**
     * Screen C
     */
    @POST
    @Path("employment/add")
    public void addEmployment(RegisterTimeEmploymentCommand command) {
        this.addEmployment.handle(command);
    }

    /**
     * Screen C
     */
    @POST
    @Path("employment/copy")
    public void copyEmployment(CopyTimeEmploymentCommand command) {
        this.copyEmployment.handle(command);
    }

    /**
     * Screen C
     */
    @POST
    @Path("employment/delete")
    public void deleteEmployment(DeleteTimeEmploymentCommand command) {
        this.deleteEloyment.handle(command);
    }

    /**
     * Screen D
     */
    @POST
    @Path("workplace/add")
    public void addWorkplace(RegisterTimeWorkPlaceCommand command) {
        this.addWorkPlace.handle(command);
    }

    /**
     * Screen D
     */
    @POST
    @Path("workplace/copy")
    public void copyWorkplace(CopyTimeWorkplaceCommand command) {
        this.copyWorkPlace.handle(command);
    }

    /**
     * Screen D
     */
    @POST
    @Path("workplace/delete")
    public void deleteWorkplace(DeleteTimeWorkplaceCommand command) {
        this.deleteWorkPlace.handle(command);
    }

    /**
     * Screen E
     */
    @POST
    @Path("classification/add")
    public void addClassification(RegisterTimeClassificationCommand command) {
        this.addClassification.handle(command);
    }

    /**
     * Screen E
     */
    @POST
    @Path("classification/copy")
    public void copyClassification(CopyTimeClassificationCommand command) {
        this.copyClassification.handle(command);
    }

    /**
     * Screen E
     */
    @POST
    @Path("classification/delete")
    public void deleteClassification(DeleteTimeClassificationCommand command) {
        this.deleteClassification.handle(command);
    }

    /**
     * Screen G
     */
    @POST
    @Path("operationSet/register")
    public void updateOperationSet(RegisterAgreeOperationSetCommand command) {
        this.registerOperationSet.handle(command);
    }

    /**
     * Screen H
     */
    @POST
    @Path("unit/Register")
    public void updateUnit(RegisterAgreeUnitSetCommand command) {
        this.RegisterUnit.handle(command);
    }

    /**
     * Screen I
     */
    @POST
    @Path("unitOfApprove/register")
    public void updateUnitOfApprove(RegisterUnitSetOfApproveCommand command) {
        this.unitSetOfApproveCommandHandler.handle(command);
    }

    /**
     * Screen K
     */
    @POST
    @Path("yearSetting/register")
    public void registerAgrYearSet(RegisterAgrYearSettingCommand command) {
        this.registerAgrYearSettingCommandHandler.handle(command);
    }

    /**
     * Screen K
     */
    @POST
    @Path("yearSetting/update")
    public void updateAgrYearSet(RegisterAgrYearSettingCommand command) {
        this.updateAgrYearSettingCommandHandler.handle(command);
    }

    /**
     * Screen K
     */
    @POST
    @Path("yearSetting/delete")
    public void deleteAgrYearSet(DeleteAgrYearSettingCommand command) {
        this.deleteAgrYearSettingCommandHandler.handle(command);
    }

    /**
     * Screen K
     */
    @POST
    @Path("monthSetting/register")
    public void registerAgrMonthSet(RegisterAgrMonthSettingCommand command) {
        this.registerAgrMonthSettingCommandHandler.handle(command);
    }

    /**
     * Screen K
     */
    @POST
    @Path("monthSetting/update")
    public void updateAgrMonthSet(RegisterAgrMonthSettingCommand command) {
        this.updateAgrMonthSettingCommandHandler.handle(command);
    }

    /**
     * Screen K
     */
    @POST
    @Path("monthSetting/delete")
    public void deleteAgrMonthSet(DeleteAgrMonthSettingCommand command) {
        this.deleteAgrMonthSettingCommandHandler.handle(command);
    }

}
