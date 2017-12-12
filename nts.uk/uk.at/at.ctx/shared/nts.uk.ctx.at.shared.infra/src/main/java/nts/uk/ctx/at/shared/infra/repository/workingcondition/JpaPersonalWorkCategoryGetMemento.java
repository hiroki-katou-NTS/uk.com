package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import java.util.Optional;

import nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategoryGetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.SingleDaySchedule;

public class JpaPersonalWorkCategoryGetMemento implements PersonalWorkCategoryGetMemento {

	@Override
	public SingleDaySchedule getHolidayWork() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleDaySchedule getHolidayTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public SingleDaySchedule getWeekdayTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<SingleDaySchedule> getPublicHolidayWork() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<SingleDaySchedule> getInLawBreakTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<SingleDaySchedule> getOutsideLawBreakTime() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<SingleDaySchedule> getHolidayAttendanceTime() {
		// TODO Auto-generated method stub
		return null;
	}

}
