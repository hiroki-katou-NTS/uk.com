package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategorySetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.SingleDaySchedule;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtPerWorkCat;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtPerWorkCatPK;

public class JpaPersonalWorkCategorySetMemento implements PersonalWorkCategorySetMemento {

	private KshmtPerWorkCat kshmtPerWorkCat;

	public JpaPersonalWorkCategorySetMemento(KshmtPerWorkCat entity) {
		if (entity.getKshmtPerWorkCatPK() == null) {
			entity.setKshmtPerWorkCatPK(new KshmtPerWorkCatPK());
		}
		this.kshmtPerWorkCat = entity;
	}

	@Override
	public void setHolidayWork(SingleDaySchedule holidayWork) {
		// TODO Auto-generated method stub
	}

	@Override
	public void setHolidayTime(SingleDaySchedule holidayTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setWeekdayTime(SingleDaySchedule weekdayTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setPublicHolidayWork(Optional<SingleDaySchedule> publicHolidayWork) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setInLawBreakTime(Optional<SingleDaySchedule> inLawBreakTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setOutsideLawBreakTime(Optional<SingleDaySchedule> outsideLawBreakTime) {
		// TODO Auto-generated method stub

	}

	@Override
	public void setHolidayAttendanceTime(Optional<SingleDaySchedule> holidayAttendanceTime) {
		// TODO Auto-generated method stub

	}

	private void checkPersWorkCategory() {

	}

}
