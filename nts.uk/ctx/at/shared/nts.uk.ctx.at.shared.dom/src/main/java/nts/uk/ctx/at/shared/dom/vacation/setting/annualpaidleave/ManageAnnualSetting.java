/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.vacation.setting.annualpaidleave;

import nts.arc.layer.dom.DomainObject;

/**
 * The Class YearVacationManageSetting.
 */
public class ManageAnnualSetting extends DomainObject {
    
    /** The company id. */
    private String companyId;
    
    /** The remaining number setting. */
    private RemainingNumberSetting remainingNumberSetting;

    /** The acquisition setting. */
    private AcquisitionVacationSetting acquisitionSetting;
    
    /** The display setting. */
    private DisplaySetting displaySetting;
    
    /** The time setting. */
    private TimeVacationSetting timeSetting;
    
    /**
     * Instantiates a new manage annual setting.
     *
     * @param memento the memento
     */
    public ManageAnnualSetting(ManageAnnualSettingGetMemento memento) {
        super();
        this.companyId = memento.getCompanyId();
        this.remainingNumberSetting = memento.getRemainingNumberSetting();
        this.acquisitionSetting = memento.getAcquisitionSetting();
        this.displaySetting = memento.getDisplaySetting();
        this.timeSetting = memento.getTimeSetting();
    }
    
    /**
     * Save to memento.
     *
     * @param memento the memento
     */
    public void saveToMemento(ManageAnnualSettingSetMemento memento) {
        memento.setCompanyId(this.companyId);
        memento.setRemainingNumberSetting(this.remainingNumberSetting);
        memento.setAcquisitionSetting(this.acquisitionSetting);
        memento.setDisplaySetting(this.displaySetting);
        memento.setTimeSetting(this.timeSetting);
    }
}
