package nts.uk.ctx.at.request.infra.repository.application.holidayshipment.absenceleaveapp;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.gobackdirectly.primitive.WorkLocationCD;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveAppRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveWorkingHour;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.ManagementDataAtr;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.ManagementDataDaysAtr;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.SubDigestion;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.SubTargetDigestion;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.WorkTime;
import nts.uk.ctx.at.request.infra.entity.application.holidayshipment.absenceleaveapp.KrqdtAbsenceLeaveApp;
import nts.uk.ctx.at.request.infra.entity.application.holidayshipment.subdigestion.KrqdtSubDigestion;
import nts.uk.ctx.at.request.infra.entity.application.holidayshipment.subtargetdigestion.KrqdtSubTargetDigestion;
import nts.uk.ctx.at.request.infra.entity.application.holidayshipment.subtargetdigestion.KrqdtSubTargetDigestionPK;
import nts.uk.ctx.at.shared.dom.worktime.common.WorkTimeCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 * 
 * @author sonnlb
 */
@Stateless
public class JpaAbsenceLeaveAppRepository extends JpaRepository implements AbsenceLeaveAppRepository {

	String FIND_SUB_DIG = "SELECT d FROM KrqdtSubDigestion d WHERE d.absenceLeaveAppID=:absenceLeaveAppID";
	String FIND_SUB_TAG_DIG_BY_ABS_ID = "SELECT d FROM KrqdtSubTargetDigestion d WHERE d.pk.absenceLeaveAppID=:absenceLeaveAppID";

	@Override
	public void insert(AbsenceLeaveApp absApp) {
		this.commandProxy().insert(toEntity(absApp));
		absApp.getSubDigestions().forEach(x -> {
			this.commandProxy().insert(toSubDigestionEntity(x));
		});

		absApp.getSubTargetDigestions().forEach(x -> {
			this.commandProxy().insert(toSubTagDigestionEntity(x));
		});
	}

	@Override
	public Optional<AbsenceLeaveApp> findByID(String applicationID) {
		return this.queryProxy().find(applicationID, KrqdtAbsenceLeaveApp.class).map(x -> toDomain(x));
	}

	private AbsenceLeaveApp toDomain(KrqdtAbsenceLeaveApp entity) {
		AbsenceLeaveWorkingHour WorkTime1 = new AbsenceLeaveWorkingHour(new WorkTime(entity.getStartWorkTime1()),
				new WorkTime(entity.getEndWorkTime1()));
		AbsenceLeaveWorkingHour WorkTime2 = new AbsenceLeaveWorkingHour(new WorkTime(entity.getStartWorkTime2()),
				new WorkTime(entity.getEndWorkTime2()));
		List<SubDigestion> subDigestions = this.queryProxy().query(FIND_SUB_DIG, KrqdtSubDigestion.class)
				.setParameter("absenceLeaveAppID", entity.getAppID()).getList(x -> toSubDigestion(x));
		List<SubTargetDigestion> subTargetDigestions = this.queryProxy()
				.query(FIND_SUB_TAG_DIG_BY_ABS_ID, KrqdtSubTargetDigestion.class)
				.setParameter("absenceLeaveAppID", entity.getAppID()).getList(x -> toSubTagDigestion(x));
		return new AbsenceLeaveApp(entity.getAppID(), entity.getWorkTimeCD(),
				EnumAdaptor.valueOf(entity.getChangeWorkHoursAtr(), NotUseAtr.class),
				new WorkLocationCD(entity.getWorkLocationCD()), new WorkTimeCode(entity.getWorkTimeCD()), WorkTime1,
				WorkTime2, subTargetDigestions, subDigestions);
	}

	private SubTargetDigestion toSubTagDigestion(KrqdtSubTargetDigestion entity) {
		return new SubTargetDigestion(entity.getPk().getRecAppID(), entity.getPk().getAbsenceLeaveAppID(),
				entity.getHoursUsed(), entity.getLeaveMngDataID(), entity.getBreakOutDate(),
				EnumAdaptor.valueOf(entity.getRestState(), ManagementDataAtr.class));
	}

	private SubDigestion toSubDigestion(KrqdtSubDigestion entity) {

		return new SubDigestion(entity.getAbsenceLeaveAppID(),
				EnumAdaptor.valueOf(entity.getDaysUsedNo(), ManagementDataDaysAtr.class), entity.getPayoutMngDataID(),
				EnumAdaptor.valueOf(entity.getPickUpState(), ManagementDataAtr.class), entity.getOccurrenceDate());
	}

	private KrqdtSubTargetDigestion toSubTagDigestionEntity(SubTargetDigestion domain) {
		KrqdtSubTargetDigestionPK pk = new KrqdtSubTargetDigestionPK(domain.getRecAppID(),
				domain.getAbsenceLeaveAppID());
		return new KrqdtSubTargetDigestion(pk, domain.getHoursUsed(), domain.getLeaveMngDataID(),
				domain.getBreakOutDate(), domain.getRestState().value);
	}

	private KrqdtSubDigestion toSubDigestionEntity(SubDigestion domain) {

		return new KrqdtSubDigestion(domain.getAbsenceLeaveAppID(), domain.getDaysUsedNo().value,
				domain.getPayoutMngDataID(), domain.getPickUpState().value, domain.getOccurrenceDate());
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
