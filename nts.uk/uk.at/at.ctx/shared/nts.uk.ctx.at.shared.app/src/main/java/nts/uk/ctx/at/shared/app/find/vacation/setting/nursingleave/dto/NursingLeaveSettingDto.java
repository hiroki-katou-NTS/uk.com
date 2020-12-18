/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.app.find.vacation.setting.nursingleave.dto;

import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.uk.ctx.at.shared.dom.vacation.setting.ManageDistinct;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.MaxPersonSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSettingSetMemento;
import nts.uk.shr.com.time.calendar.MonthDay;

public class NursingLeaveSettingDto implements NursingLeaveSettingSetMemento {

    /** The manage type. 管理区分 */
    public Integer manageType;

    /** The nursing category. 介護看護区分*/
    public Integer nursingCategory;

    /** The start month day. 起算日 */
    public Integer startMonthDay;

    /** The nursing number leave day. 看護休暇日数*/
    public Integer nursingNumberLeaveDay;

    /** The nursing number person. 看護休暇人数*/
    public Integer nursingNumberPerson;

    /** The nursing number leave day. 看護休暇日数2*/
    public Integer nursingNumberLeaveDay2;

    /** The nursing number person. 看護休暇人数2*/
    public Integer nursingNumberPerson2;

    /** 特別休暇枠NO */
    public Integer specialHolidayFrame;

    /** 欠勤枠NO */
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
    	int monthday = startMonthDay.getMonth() * 100 + startMonthDay.getDay();
    	this.startMonthDay = monthday;
    }

    /*
     * (non-Javadoc)
     *
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingSetMemento#setMaxPersonSetting(nts.uk.ctx.at.shared
     * .dom.vacation.setting.nursingleave.MaxPersonSetting)
     */
    @Override
    public void setMaxPersonSetting(List<MaxPersonSetting> maxPersonSetting) {
//        if (maxPersonSetting.getNursingNumberLeaveDay() != null) {
//            this.nursingNumberLeaveDay = maxPersonSetting.getNursingNumberLeaveDay().v();
//        }
//        if (maxPersonSetting.getNursingNumberPerson() != null) {
//            this.nursingNumberPerson = maxPersonSetting.getNursingNumberPerson().v();
//        }

    	val maxPerson1 = maxPersonSetting.stream().filter(c -> c.getNursingNumberPerson().v() == 1).findFirst().get();
    	val maxPerson2 = maxPersonSetting.stream().filter(c -> c.getNursingNumberPerson().v() >= 2).findFirst().get();
    	this.nursingNumberLeaveDay = maxPerson1.getNursingNumberLeaveDay().v();
    	this.nursingNumberLeaveDay2 = maxPerson2.getNursingNumberLeaveDay().v();
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
