/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.dom.worktime.worktimeset;

import nts.uk.ctx.at.shared.dom.common.color.ColorCode;
import nts.uk.ctx.at.shared.dom.worktime.common.AbolishAtr;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.shr.com.primitive.Memo;

/**
 * The Interface WorkTimeSettingSetMemento.
 */
public interface WorkTimeSettingSetMemento {
    
    /**
     * Sets the company id.
     *
     * @param companyId the new company id
     */
    public void setCompanyId(String companyId);

    /**
     * Sets the worktime code.
     *
     * @param worktimeCode the new worktime code
     */
    public void setWorktimeCode(WorkTimeCode worktimeCode);

    /**
     * Sets the work time division.
     *
     * @param workTimeDivision the new work time division
     */
    public void setWorkTimeDivision(WorkTimeDivision workTimeDivision);

    /**
     * Sets the abolish atr.
     *
     * @param abolishAtr the new abolish atr
     */
    public void setAbolishAtr(AbolishAtr abolishAtr);

    /**
     * Sets the color code.
     *
     * @param colorCode the new color code
     */
//    public void setColorCode(ColorCode colorCode);

    /**
     * Sets the work time display name.
     *
     * @param workTimeDisplayName the new work time display name
     */
    public void setWorkTimeDisplayName(WorkTimeDisplayName workTimeDisplayName);

    /**
     * Sets the memo.
     *
     * @param memo the new memo
     */
    public void setMemo(Memo memo);

    /**
     * Sets the note.
     *
     * @param note the new note
     */
    public void setNote(WorkTimeNote note);
}
