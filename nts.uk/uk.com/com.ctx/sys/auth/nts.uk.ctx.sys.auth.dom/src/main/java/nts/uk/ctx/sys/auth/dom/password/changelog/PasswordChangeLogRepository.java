/******************************************************************
 * Copyright (c) 2018 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.sys.auth.dom.password.changelog;

public interface PasswordChangeLogRepository {
  
  /**
   * Adds the.
   *
   * @param passwordChangeLog the password change log
   */
  void add(PasswordChangeLog passwordChangeLog);
}
