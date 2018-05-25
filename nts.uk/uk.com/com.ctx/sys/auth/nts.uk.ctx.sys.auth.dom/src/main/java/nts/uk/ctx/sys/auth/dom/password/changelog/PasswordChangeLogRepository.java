/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.dom.password.changelog;

public interface PasswordChangeLogRepository {
  
  /**
   * Register.
   *
   * @param passwordChangeLog the password change log
   */
  void register(PasswordChangeLog passwordChangeLog);
}
