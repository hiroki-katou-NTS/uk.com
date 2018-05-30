/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.dom.mailnoticeset.company;

import nts.uk.ctx.sys.env.dom.mailnoticeset.FunctionId;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The Interface SendMailByFunctionSettingSetMemento.
 */
public interface SendMailByFunctionSettingSetMemento {

    /**
     * Sets the function id.
     *
     * @param functionId the new function id
     */
    public void setFunctionId(FunctionId functionId);

    /**
     * Sets the send setting.
     *
     * @param sendSetting the new send setting
     */
    public void setSendSetting(NotUseAtr sendSetting);
}
