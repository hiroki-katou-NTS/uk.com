/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.vacation.setting.annualpaidleave.find;

import javax.ejb.Stateless;
import javax.inject.Inject;

import nts.uk.ctx.at.shared.app.vacation.setting.annualpaidleave.find.dto.AnnualPaidLeaveSettingFindDto;
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
        AnnualPaidLeaveSettingFindDto output = new AnnualPaidLeaveSettingFindDto();
        paidLeaveSetting.saveToMemento(output);
        return output;
    }
}
