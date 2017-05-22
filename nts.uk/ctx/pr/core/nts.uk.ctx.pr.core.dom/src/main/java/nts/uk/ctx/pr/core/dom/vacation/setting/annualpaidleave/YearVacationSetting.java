/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave;

import lombok.Getter;
import nts.arc.layer.dom.DomainObject;
import nts.uk.ctx.pr.core.dom.vacation.setting.ManageDistinct;

/**
 * The Class YearVacationSetting.
 */
@Getter
public class YearVacationSetting extends DomainObject {

    /** The company id. */
    private String companyId;

    /** The year manage type. */
    private ManageDistinct yearManageType;

    /** The manage setting. */
    private YearVacationManageSetting yearManageSetting;
    
    /** The time manage type. */
    private ManageDistinct timeManageType;
    
    /** The time manage setting. */
    private YearVacationTimeManageSetting timeManageSetting;
}
