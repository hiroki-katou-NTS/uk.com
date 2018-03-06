/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.command.vacation.setting.annualpaidleave;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.arc.layer.app.command.CommandHandler;
import nts.arc.layer.app.command.CommandHandlerContext;
import nts.uk.ctx.at.shared.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.TimeDigestiveUnit;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPriority;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.DisplayDivision;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.MaxDayReference;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.RoundProcessingClassification;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class AnnualPaidLeaveUpateCommandHandler.
 */
@Stateless
public class AnnualPaidLeaveSaveCommandHandler extends CommandHandler<AnnualPaidLeaveSaveCommand> {

    /** The Constant TRUE. */
    private static final boolean TRUE = true;

    /** The bit true. */
    private static int BIT_TRUE = 1;

    /** The bit false. */
    private static int BIT_FALSE = 0;

    /** The annual repo. */
    @Inject
    private AnnualPaidLeaveSettingRepository annualRepo;

    /*
     * (non-Javadoc)
     * 
     * @see
     * nts.arc.layer.app.command.CommandHandler#handle(nts.arc.layer.app.command
     * .CommandHandlerContext)
     */
    @Override
    protected void handle(CommandHandlerContext<AnnualPaidLeaveSaveCommand> context) {
        AnnualPaidLeaveSaveCommand command = context.getCommand();

        String companyId = AppContexts.user().companyId();
        AnnualPaidLeaveSetting domain = this.annualRepo.findByCompanyId(companyId);
        
        // Check field enable/disable
        this.validateField(command, domain);

        AnnualPaidLeaveSetting setting = command.toDomain(companyId);
        if (domain != null) {
            this.annualRepo.update(setting);
        } else {
            this.annualRepo.add(setting);
        }
    }

    /**
     * Validate field.
     *
     * @param command the command
     * @param setttingDB the settting DB
     */
    private void validateField(AnnualPaidLeaveSaveCommand command, AnnualPaidLeaveSetting setttingDB) {
        boolean isAnnualManage = command.getAnnualManage() == ManageDistinct.YES.value;

        // ========= NOT MANAGE =========
        if (!isAnnualManage) {
            // Setting not existed
            if(setttingDB == null) {
                this.initValue(command);
                return;
            }
            // Manage Annual Setting
            command.setAddAttendanceDay(
                    setttingDB.getManageAnnualSetting().isWorkDayCalculate() == TRUE ? BIT_TRUE : BIT_FALSE);
            command.setMaxManageSemiVacation(setttingDB.getManageAnnualSetting().getHalfDayManage().manageType.value);
            command.setMaxNumberSemiVacation(setttingDB.getManageAnnualSetting().getHalfDayManage().reference.value);
            command.setMaxNumberCompany(setttingDB.getManageAnnualSetting().getHalfDayManage()
                    .maxNumberUniformCompany.v());
            command.setMaxGrantDay(setttingDB.getManageAnnualSetting().getMaxGrantDay().v());
            command.setMaxRemainingDay(setttingDB.getManageAnnualSetting().getRemainingNumberSetting()
                    .remainingDayMaxNumber.v());
            command.setNumberYearRetain(setttingDB.getManageAnnualSetting().getRemainingNumberSetting()
                    .retentionYear.v());
            command.setPermitType(setttingDB.getAcquisitionSetting().permitType.value);
            command.setAnnualPriority(setttingDB.getAcquisitionSetting().annualPriority.value);
            command.setRemainingNumberDisplay(setttingDB.getManageAnnualSetting().getDisplaySetting()
                    .remainingNumberDisplay.value);
            command.setNextGrantDayDisplay(setttingDB.getManageAnnualSetting().getDisplaySetting()
                    .nextGrantDayDisplay.value);            
            command.setYearlyOfDays(setttingDB.getManageAnnualSetting().getYearlyOfNumberDays().v());
            command.setRoundProcessCla(setttingDB.getManageAnnualSetting().getHalfDayManage().roundProcesCla.value);

            // Time Leave Setting
            command.setTimeManageType(setttingDB.getTimeSetting().getTimeManageType().value);
            command.setTimeUnit(setttingDB.getTimeSetting().getTimeUnit().value);
            command.setManageMaxDayVacation(setttingDB.getTimeSetting().getMaxYearDayLeave().manageType.value);
            command.setReference(setttingDB.getTimeSetting().getMaxYearDayLeave().reference.value);
            command.setMaxTimeDay(setttingDB.getTimeSetting().getMaxYearDayLeave().maxNumberUniformCompany.v());
            command.setIsEnoughTimeOneDay(setttingDB.getTimeSetting().isEnoughTimeOneDay());
            
            return;
        }
        
        // ========= MANAGE =========
        // Manage Annual Setting
        if (command.getMaxNumberSemiVacation() == MaxDayReference.ReferAnnualGrantTable.value) {
            command.setMaxNumberCompany(setttingDB.getManageAnnualSetting().getHalfDayManage()
                    .maxNumberUniformCompany.v());
        }
        // Time Leave Setting
        boolean isTimeManage = command.getTimeManageType() == ManageDistinct.YES.value;
        if (!isTimeManage) {
            command.setMaxTimeDay(setttingDB.getTimeSetting().getMaxYearDayLeave().maxNumberUniformCompany.v());
            command.setIsEnoughTimeOneDay(setttingDB.getTimeSetting().isEnoughTimeOneDay());
            return;
        }
        if (command.getReference() == MaxDayReference.ReferAnnualGrantTable.value) {
            command.setMaxTimeDay(setttingDB.getTimeSetting().getMaxYearDayLeave().maxNumberUniformCompany.v());
            command.setIsEnoughTimeOneDay(setttingDB.getTimeSetting().isEnoughTimeOneDay());
        }
    }
    
    /**
     * Inits the value.
     *
     * @param command the command
     */
    private void initValue(AnnualPaidLeaveSaveCommand command) {
        command.setAddAttendanceDay(ManageDistinct.YES.value);
        
        // Manage Annual Setting
        command.setMaxManageSemiVacation(ManageDistinct.YES.value);
        command.setMaxNumberSemiVacation(MaxDayReference.CompanyUniform.value);
        command.setMaxNumberCompany(null);
        // TODO: Check value default
        command.setMaxGrantDay(null);
        command.setMaxRemainingDay(null);
        command.setNumberYearRetain(null);
        command.setYearlyOfDays(null);
        command.setRoundProcessCla(RoundProcessingClassification.TruncateOnDay0.value);
        // =======
        command.setPermitType(ApplyPermission.ALLOW.value);
        command.setAnnualPriority(AnnualPriority.FIFO.value);
        command.setRemainingNumberDisplay(DisplayDivision.Indicate.value);
        command.setNextGrantDayDisplay(DisplayDivision.Indicate.value);

        // Time Leave Setting
        command.setTimeManageType(ManageDistinct.YES.value);
        command.setTimeUnit(TimeDigestiveUnit.OneMinute.value);
        command.setManageMaxDayVacation(ManageDistinct.YES.value);
        command.setReference(MaxDayReference.CompanyUniform.value);
        command.setMaxTimeDay(null);
        command.setIsEnoughTimeOneDay(false);
    }
}
