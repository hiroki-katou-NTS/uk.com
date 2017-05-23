/******************************************************************
 * Copyright (c) 2016 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package vacation.setting.annualpaidleave.command;

import lombok.Getter;
import lombok.Setter;
import nts.uk.ctx.pr.core.dom.vacation.setting.ApplyPermission;
import nts.uk.ctx.pr.core.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.AnnualNumberDay;
import nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.DisplayDivision;
import nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.MaxDayReference;
import nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.MaxTimeDay;
import nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.PreemptionPermit;
import nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.RetentionYear;
import nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.YearVacationAge;
import nts.uk.ctx.pr.core.dom.vacation.setting.annualpaidleave.YearVacationTimeUnit;

/**
 * The Class AnnualPaidLeaveUpateCommand.
 */
@Setter
@Getter
public class AnnualPaidLeaveUpateCommand {
    
    /** The annual manage. */
    private ManageDistinct annualManage;
    
    /** The add attendance day. */
    private ManageDistinct addAttendanceDay;
    
    /** The max manage semi vacation. */
    private ManageDistinct maxManageSemiVacation;
    
    /** The max number semi vacation. */
    private MaxDayReference maxNumberSemiVacation;
    
    /** The max number company. */
    private AnnualNumberDay maxNumberCompany;
    
    /** The max grant day. */
    private YearVacationAge maxGrantDay;
    
    /** The max remaining day. */
    private Integer maxRemainingDay;
    
    /** The number year retain. */
    private RetentionYear numberYearRetain;
    
    /** The preemption annual vacation. */
    private ApplyPermission preemptionAnnualVacation;
    
    /** The preemption year leave. */
    private PreemptionPermit preemptionYearLeave;
    
    /** The remaining number display. */
    private DisplayDivision remainingNumberDisplay;
    
    /** The next grant day display. */
    private DisplayDivision nextGrantDayDisplay;
    
    /** The time manage type. */
    private ManageDistinct timeManageType;
    
    /** The time unit. */
    private YearVacationTimeUnit timeUnit;
    
    /** The manage max day vacation. */
    private ManageDistinct manageMaxDayVacation;
    
    /** The reference. */
    private MaxDayReference reference;
    
    /** The max time day. */
    private MaxTimeDay maxTimeDay;
    
    /** The is enough time one day. */
    private Boolean isEnoughTimeOneDay;
}
