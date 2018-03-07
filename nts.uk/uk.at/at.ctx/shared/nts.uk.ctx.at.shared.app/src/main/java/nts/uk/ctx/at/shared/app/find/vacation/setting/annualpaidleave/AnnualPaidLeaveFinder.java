/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.vacation.setting.annualpaidleave;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.find.vacation.setting.annualpaidleave.dto.AnnualPaidLeaveSettingFindDto;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class AnnualPaidLeaveFinder.
 */
@Stateless
public class AnnualPaidLeaveFinder {
    
    /** The paid leave repo. */
    @Inject
    private AnnualPaidLeaveSettingRepository paidLeaveRepo;
    
    /**
     * Find by company id.
     *
     * @return the annual paid leave setting
     */
    public AnnualPaidLeaveSettingFindDto findByCompanyId() {
        String companyId = AppContexts.user().companyId();
        AnnualPaidLeaveSetting paidLeaveSetting = this.paidLeaveRepo.findByCompanyId(companyId);
        if (paidLeaveSetting == null) {
            return null;
        }
        return this.converetToDto(paidLeaveSetting);
    }
    
    /**
     * Converet to dto.
     *
     * @param setting the setting
     * @return the annual paid leave setting find dto
     */
    private AnnualPaidLeaveSettingFindDto converetToDto(AnnualPaidLeaveSetting setting) {
        AnnualPaidLeaveSettingFindDto dto = new AnnualPaidLeaveSettingFindDto();
        
        dto.setAnnualManage(setting.getYearManageType().value);
        
        // AcquisitionSetting
        dto.setAnnualPriority(setting.getAcquisitionSetting().annualPriority.value);
        
        // Manage Annual
        dto.setAddAttendanceDay(setting.getManageAnnualSetting().isWorkDayCalculate() == true ? 1 : 0);
        dto.setMaxManageSemiVacation(setting.getManageAnnualSetting().getHalfDayManage().manageType.value);
        dto.setMaxNumberSemiVacation(setting.getManageAnnualSetting().getHalfDayManage().reference.value);
        dto.setMaxNumberCompany(setting.getManageAnnualSetting().getHalfDayManage().maxNumberUniformCompany.v());
        dto.setMaxGrantDay(setting.getManageAnnualSetting().getMaxGrantDay().v());
        dto.setMaxRemainingDay(setting.getManageAnnualSetting().getRemainingNumberSetting().remainingDayMaxNumber.v());
        dto.setNumberYearRetain(setting.getManageAnnualSetting().getRemainingNumberSetting().retentionYear.v());
        dto.setRemainingNumberDisplay(setting.getManageAnnualSetting().getDisplaySetting().remainingNumberDisplay.value);
        dto.setNextGrantDayDisplay(setting.getManageAnnualSetting().getDisplaySetting().nextGrantDayDisplay.value);
        dto.setYearlyOfDays(setting.getManageAnnualSetting().getYearlyOfNumberDays().v());        
        dto.setRoundProcessCla(setting.getManageAnnualSetting().getHalfDayManage().roundProcesCla.value);
        
        // Time Manage
        dto.setTimeManageType(setting.getTimeSetting().getTimeManageType().value);
        dto.setTimeUnit(setting.getTimeSetting().getTimeUnit().value);
        dto.setManageMaxDayVacation(setting.getTimeSetting().getMaxYearDayLeave().manageType.value);
        dto.setReference(setting.getTimeSetting().getMaxYearDayLeave().reference.value);
        dto.setMaxTimeDay(setting.getTimeSetting().getMaxYearDayLeave().maxNumberUniformCompany.v());
        dto.setRoundProcessClassific(setting.getTimeSetting().getRoundProcessClassific().value);
        
        return dto;
    }
}
