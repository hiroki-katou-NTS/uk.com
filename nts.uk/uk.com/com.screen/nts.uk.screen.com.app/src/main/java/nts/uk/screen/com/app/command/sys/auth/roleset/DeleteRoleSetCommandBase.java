/******************************************************************
 * Copyright (c) 2015 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.screen.com.app.command.sys.auth.roleset;

import lombok.Value;

/**
* The Class DeleteRoleSetCommandBase.
* @author HieuNV
*/
@Value
public class DeleteRoleSetCommandBase {
    /** ロールセットコード. */
    private String roleSetCd;
}
