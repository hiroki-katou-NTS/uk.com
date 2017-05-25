/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.Setter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;

/**
 * The Class AnnualPaidLeaveSetting.
 */
@Getter
@EqualsAndHashCode(callSuper = true, of = {"companyId"})
public class AnnualPaidLeaveSetting extends DomainObject {
    
    /** The company id. */
    private String companyId;
    
    /** The year manage type. */
    private ManageDistinct yearManageType;
    
    /** The year manage setting. */
    @Setter
    private ManageAnnualSetting yearManageSetting;
    
    /**
     * Instantiates a new annual paid leave setting.
     *
     * @param memento the memento
     */
    public AnnualPaidLeaveSetting(AnnualPaidLeaveSettingGetMemento memento) {
        this.companyId = memento.getCompanyId();
        this.yearManageType = memento.getYearManageType();
        this.yearManageSetting = memento.getYearManageSetting();
    }
    
    /**
     * Save to memento.
     *
     * @param memento the memento
     */
    public void saveToMemento(AnnualPaidLeaveSettingSetMemento memento) {
        memento.setCompanyId(this.companyId);
        memento.setYearManageType(this.yearManageType);
        memento.setYearManageSetting(this.yearManageSetting);
    }
}
