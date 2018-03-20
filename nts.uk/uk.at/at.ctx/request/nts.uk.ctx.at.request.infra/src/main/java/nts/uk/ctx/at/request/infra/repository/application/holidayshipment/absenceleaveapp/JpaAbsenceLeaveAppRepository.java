package nts.uk.ctx.at.request.infra.repository.application.holidayshipment.absenceleaveapp;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveAppRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveWorkingHour;
import nts.uk.ctx.at.request.infra.entity.application.holidayshipment.absenceleaveapp.KrqdtAbsenceLeaveApp;

@Stateless
public class JpaAbsenceLeaveAppRepository extends JpaRepository implements AbsenceLeaveAppRepository {

	@Override
	public void insert(AbsenceLeaveApp absApp) {
		this.commandProxy().insert(toEntity(absApp));

	}

	private KrqdtAbsenceLeaveApp toEntity(AbsenceLeaveApp absApp) {
		KrqdtAbsenceLeaveApp entity = new KrqdtAbsenceLeaveApp();
		entity.setAppID(absApp.getAppID());
		entity.setWorkTypeCD(absApp.getWorkTypeCD());
		entity.setChangeWorkHoursAtr(absApp.getChangeWorkHoursType().value);
		entity.setWorkLocationCD(absApp.getWorkLocationCD().v());
		entity.setWorkTimeCD(absApp.getWorkTimeCD().v());
		AbsenceLeaveWorkingHour workTime1 = absApp.getWorkTime1();
		if (workTime1 != null) {
			entity.setStartWorkTime1(workTime1.getStartTime().v());
			entity.setEndWorkTime1(workTime1.getEndTime().v());
		}
		AbsenceLeaveWorkingHour workTime2 = absApp.getWorkTime2();
		if (absApp.getWorkTime2() != null) {
			entity.setStartWorkTime2(workTime2.getStartTime().v());
			entity.setEndWorkTime2(workTime2.getEndTime().v());
		}

		return entity;

	}

}
