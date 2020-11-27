/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.vacation.setting.nursingleave.dto;

//import java.util.List;
import java.util.Optional;

//import lombok.Setter;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.MaxPersonSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSettingSetMemento;
import nts.uk.shr.com.time.calendar.MonthDay;

public class NursingLeaveSettingDto implements NursingLeaveSettingSetMemento {

    /** The manage type. */
    public Integer manageType;

    /** The nursing category. */
    public Integer nursingCategory;

    /** The start month day. */
    public MonthDay startMonthDay;

    /** The nursing number leave day. */
    public Integer nursingNumberLeaveDay;

    /** The nursing number person. */
    public Integer nursingNumberPerson;

    public Integer specialHolidayFrame;

    public Integer absenceWorkDay;

    /*
     * (non-Javadoc)
     *
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingSetMemento#setCompanyId(java.lang.String)
     */
    @Override
    public void setCompanyId(String companyId) {
    }

    /*
     * (non-Javadoc)
     *
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingSetMemento#setManageType(nts.uk.ctx.at.shared.dom.
     * vacation.setting.ManageDistinct)
     */
    @Override
    public void setManageType(ManageDistinct manageType) {
        this.manageType = manageType.value;
    }

    /*
     * (non-Javadoc)
     *
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingSetMemento#setNursingCategory(nts.uk.ctx.at.shared.
     * dom.vacation.setting.nursingleave.NursingCategory)
     */
    @Override
    public void setNursingCategory(NursingCategory nursingCategory) {
        this.nursingCategory = nursingCategory.value;
    }

    /*
     * (non-Javadoc)
     *
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingSetMemento#setStartMonthDay(java.lang.Integer)
     */
    @Override
    public void setStartMonthDay(MonthDay startMonthDay) {
        this.startMonthDay = startMonthDay;
    }

    /*
     * (non-Javadoc)
     *
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingSetMemento#setMaxPersonSetting(nts.uk.ctx.at.shared
     * .dom.vacation.setting.nursingleave.MaxPersonSetting)
     */
    @Override
    public void setMaxPersonSetting(MaxPersonSetting maxPersonSetting) {
        if (maxPersonSetting.getNursingNumberLeaveDay() != null) {
            this.nursingNumberLeaveDay = maxPersonSetting.getNursingNumberLeaveDay().v();
        }
        if (maxPersonSetting.getNursingNumberPerson() != null) {
            this.nursingNumberPerson = maxPersonSetting.getNursingNumberPerson().v();
        }
    }

	@Override
	public void setHdspFrameNo(Optional<Integer> specialHolidayFrame) {
		if (specialHolidayFrame.isPresent())
			this.specialHolidayFrame = specialHolidayFrame.get();
		else
			this.specialHolidayFrame = 0;
	}

	@Override
	public void setAbsenceFrameNo(Optional<Integer> workAbsence) {
		if (workAbsence.isPresent())
			this.absenceWorkDay = workAbsence.get();
		else
			this.absenceWorkDay = 0;
	}
}
