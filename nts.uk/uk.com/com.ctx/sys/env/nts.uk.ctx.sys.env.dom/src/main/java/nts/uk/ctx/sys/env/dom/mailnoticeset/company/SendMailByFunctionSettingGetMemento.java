/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.env.dom.mailnoticeset.company;

import nts.uk.ctx.sys.env.dom.mailnoticeset.FunctionId;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * The Interface SendMailByFunctionSettingGetMemento.
 */
public interface SendMailByFunctionSettingGetMemento {
	
	/**
	 * Gets the function id.
	 *
	 * @return the function id
	 */
	public FunctionId getFunctionId();

    /**
     * Gets the send setting.
     *
     * @return the send setting
     */
    public NotUseAtr getSendSetting();
}
