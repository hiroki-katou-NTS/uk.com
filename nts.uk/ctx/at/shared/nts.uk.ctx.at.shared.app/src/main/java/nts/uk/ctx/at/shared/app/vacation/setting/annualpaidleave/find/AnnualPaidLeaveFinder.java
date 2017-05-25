/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.annualpaidleave.find;

import java.util.Optional;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.AnnualPaidLeaveSettingRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.ManageAnnualSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave.ManageAnnualSettingRepository;
import nts.uk.shr.com.context.AppContexts;

/**
 * The Class AnnualPaidLeaveFinder.
 */
@Stateless
public class AnnualPaidLeaveFinder {
    
    /** The paid leave repo. */
    @Inject
    private AnnualPaidLeaveSettingRepository paidLeaveRepo;
    
    /** The manage repo. */
    @Inject
    private ManageAnnualSettingRepository manageRepo;
    
    /**
     * Find by company id.
     *
     * @return the annual paid leave setting
     */
    public AnnualPaidLeaveSetting findByCompanyId() {
        String companyId = AppContexts.user().companyId();
        Optional<AnnualPaidLeaveSetting> optional = this.paidLeaveRepo.findByCompanyId(companyId);
        if (!optional.isPresent()) {
            return null;
        }
        AnnualPaidLeaveSetting paidLeaveSetting = optional.get();
        ManageAnnualSetting manageSetting = this.manageRepo.findByCompanyId(companyId);
        paidLeaveSetting.setYearManageSetting(manageSetting);
        return paidLeaveSetting;
    }
}
