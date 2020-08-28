package nts.uk.screen.at.app.ksm005.find;

import lombok.Getter;
import lombok.Setter;
import nts.arc.time.GeneralDate;
import nts.uk.ctx.at.schedule.dom.shift.pattern.monthly.MonthlyPatternCode;
import nts.uk.ctx.at.schedule.dom.shift.pattern.work.WorkMonthlySettingSetMemento;
import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;

@Getter
@Setter
public class MonthlyPatternDto implements WorkMonthlySettingSetMemento {


    /** The work monthly setting code. */
    private String workTypeCode;

    /** The working code. */
    private String workingCode;

    /** The work type name. */
    private String workTypeName;

    /** The type color. */
    // ATTENDANCE = 1 , HOLIDAY = 0
    private Integer typeColor;

    /** The working name. */
    private String workingName;

    /** The date. */
    private GeneralDate ymdk;

    /** The monthly pattern code. */
    private String monthlyPatternCode;

    /*
     * (non-Javadoc)
     *
     * @see
     * nts.uk.ctx.at.schedule.dom.shift.pattern.WorkMonthlySettingSetMemento#
     * setCompanyId(nts.uk.ctx.at.shared.dom.common.CompanyId)
     */
    @Override
    public void setCompanyId(CompanyId companyId) {
        // No thing code
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * nts.uk.ctx.at.schedule.dom.shift.pattern.WorkMonthlySettingSetMemento#
     * setWorkMonthlySettingCode(nts.uk.ctx.at.schedule.dom.shift.pattern.
     * WorkMonthlySettingCode)
     */
    @Override
    public void setWorkTypeCode(WorkTypeCode workTypeCode) {
        this.workTypeCode = workTypeCode.v();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * nts.uk.ctx.at.schedule.dom.shift.pattern.WorkMonthlySettingSetMemento#
     * setSiftCode(nts.uk.ctx.at.shared.dom.worktime.SiftCode)
     */
    @Override
    public void setWorkingCode(WorkTimeCode workingCode) {
        this.workingCode = workingCode == null ? "" : workingCode.v();
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * nts.uk.ctx.at.schedule.dom.shift.pattern.WorkMonthlySettingSetMemento#
     * setDate(nts.arc.time.GeneralDate)
     */
    @Override
    public void setYmdK(GeneralDate ymdk) {
        this.ymdk = ymdk;
    }

    /*
     * (non-Javadoc)
     *
     * @see
     * nts.uk.ctx.at.schedule.dom.shift.pattern.WorkMonthlySettingSetMemento#
     * setMonthlyPatternCode(nts.uk.ctx.at.schedule.dom.shift.pattern.
     * MonthlyPatternCode)
     */
    @Override
    public void setMonthlyPatternCode(MonthlyPatternCode monthlyPatternCode) {
        this.monthlyPatternCode = monthlyPatternCode.v();
    }

}
