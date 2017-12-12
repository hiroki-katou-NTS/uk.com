package nts.uk.ctx.at.shared.infra.repository.workingcondition;

import javax.ejb.Stateless;

import nts.uk.ctx.at.shared.dom.workingcondition.BreakdownTimeDay;
import nts.uk.ctx.at.shared.dom.workingcondition.LaborContractTime;
import nts.uk.ctx.at.shared.dom.workingcondition.NotUseAtr;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalDayOfWeek;
import nts.uk.ctx.at.shared.dom.workingcondition.PersonalWorkCategory;
import nts.uk.ctx.at.shared.dom.workingcondition.ScheduleMethod;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingConditionItemSetMemento;
import nts.uk.ctx.at.shared.dom.workingcondition.WorkingSystem;
import nts.uk.ctx.at.shared.infra.entity.workingcondition.KshmtWorkingCondItem;

@Stateless
public class JpaWorkingConditionItemSetMemento implements WorkingConditionItemSetMemento{
	
	private KshmtWorkingCondItem kshmtWorkingCondItem;
	
	public JpaWorkingConditionItemSetMemento(KshmtWorkingCondItem entity){
		this.kshmtWorkingCondItem = entity;
	}
	
	@Override
	public void setScheduleManagementAtr(NotUseAtr scheduleManagementAtr) {
		this.kshmtWorkingCondItem.setScheManagementAtr(scheduleManagementAtr.value);
	}

	@Override
	public void setVacationAddedTimeAtr(NotUseAtr vacationAddedTimeAtr) {
		this.kshmtWorkingCondItem.setVacationAddTimeAtr(vacationAddedTimeAtr.value);
	}

	@Override
	public void setLaborSystem(WorkingSystem laborSystem) {
		this.kshmtWorkingCondItem.setLaborSys(laborSystem.value);
	}

	@Override
	public void setWorkCategory(PersonalWorkCategory workCategory) {
		
	}

	@Override
	public void setContractTime(LaborContractTime contractTime) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAutoIntervalSetAtr(NotUseAtr autoIntervalSetAtr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHistoryId(String historyId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setWorkDayOfWeek(PersonalDayOfWeek workDayOfWeek) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setEmployeeId(String employeeId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setAutoStampSetAtr(NotUseAtr autoStampSetAtr) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setScheduleMethod(ScheduleMethod scheduleMethod) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setHolidayAddTimeSet(BreakdownTimeDay holidayAddTimeSet) {
		// TODO Auto-generated method stub
		
	}

}
