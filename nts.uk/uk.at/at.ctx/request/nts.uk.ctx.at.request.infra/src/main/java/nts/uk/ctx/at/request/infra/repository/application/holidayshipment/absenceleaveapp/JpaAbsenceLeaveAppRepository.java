package nts.uk.ctx.at.request.infra.repository.application.holidayshipment.absenceleaveapp;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.enums.EnumAdaptor;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveApp;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveAppRepository;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.AbsenceLeaveWorkingHour;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.ManagementDataAtr;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.ManagementDataDaysAtr;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.SubDigestion;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.SubTargetDigestion;
import nts.uk.ctx.at.request.dom.application.holidayshipment.absenceleaveapp.WorkTime;
import nts.uk.ctx.at.request.infra.entity.application.holidayshipment.absenceleaveapp.KrqdtAppHdSub;
import nts.uk.ctx.at.request.infra.entity.application.holidayshipment.subdigestion.KrqdtSubDigestion;
import nts.uk.ctx.at.request.infra.entity.application.holidayshipment.subtargetdigestion.KrqdtSubTargetDigestion;
import nts.uk.ctx.at.shared.dom.worktype.WorkTypeCode;
import nts.uk.shr.com.enumcommon.NotUseAtr;

/**
 *
 * @author sonnlb
 */
@Stateless
public class JpaAbsenceLeaveAppRepository extends JpaRepository implements AbsenceLeaveAppRepository {

	private static final String FIND_SUB_DIG = "SELECT d FROM KrqdtSubDigestion d WHERE d.absenceLeaveAppID=:absenceLeaveAppID";
	private static final String FIND_SUB_TAG_DIG_BY_ABS_ID = "SELECT d FROM KrqdtSubTargetDigestion d WHERE d.appID=:appID";

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
		return this.queryProxy().find(applicationID, KrqdtAppHdSub.class).map(x -> toDomain(x));
	}

	@Override
	public void update(AbsenceLeaveApp absApp) {
		this.commandProxy().update(toEntity(absApp));

	}

	private AbsenceLeaveApp toDomain(KrqdtAppHdSub entity) {
		AbsenceLeaveWorkingHour WorkTime1 = new AbsenceLeaveWorkingHour(new WorkTime(entity.getStartWorkTime1()),
				new WorkTime(entity.getEndWorkTime1()));
		AbsenceLeaveWorkingHour WorkTime2 = new AbsenceLeaveWorkingHour(new WorkTime(entity.getStartWorkTime2()),
				new WorkTime(entity.getEndWorkTime2()));
		List<SubDigestion> subDigestions = this.queryProxy().query(FIND_SUB_DIG, KrqdtSubDigestion.class)
				.setParameter("absenceLeaveAppID", entity.getAppID()).getList(x -> toSubDigestion(x));
		List<SubTargetDigestion> subTargetDigestions = this.queryProxy()
				.query(FIND_SUB_TAG_DIG_BY_ABS_ID, KrqdtSubTargetDigestion.class)
				.setParameter("appID", entity.getAppID()).getList(x -> toSubTagDigestion(x));
		return new AbsenceLeaveApp(entity.getAppID(), new WorkTypeCode(entity.getWorkTypeCD()),
				EnumAdaptor.valueOf(entity.getChangeWorkHoursAtr(), NotUseAtr.class), entity.getWorkTimeCD(), WorkTime1,
				WorkTime2, subTargetDigestions, subDigestions);
	}

	private SubTargetDigestion toSubTagDigestion(KrqdtSubTargetDigestion entity) {
		return new SubTargetDigestion(entity.getAppID(), entity.getHoursUsed(), entity.getLeaveMngDataID(),
				entity.getBreakOutDate(), EnumAdaptor.valueOf(entity.getRestState(), ManagementDataAtr.class),
				entity.getUnknownDate());
	}

	private SubDigestion toSubDigestion(KrqdtSubDigestion entity) {

		return new SubDigestion(entity.getAbsenceLeaveAppID(),
				EnumAdaptor.valueOf(entity.getDaysUsedNo(), ManagementDataDaysAtr.class), entity.getPayoutMngDataID(),
				EnumAdaptor.valueOf(entity.getPickUpState(), ManagementDataAtr.class), entity.getOccurrenceDate(),
				entity.getUnknownDate());
	}

	private KrqdtSubTargetDigestion toSubTagDigestionEntity(SubTargetDigestion domain) {
		return new KrqdtSubTargetDigestion(domain.getAppID(), domain.getHoursUsed(), domain.getLeaveMngDataID(),
				domain.getBreakOutDate(), domain.getRestState().value, domain.getUnknownDate());
	}

	private KrqdtSubDigestion toSubDigestionEntity(SubDigestion domain) {

		return new KrqdtSubDigestion(domain.getAbsenceLeaveAppID(), domain.getDaysUsedNo().value,
				domain.getPayoutMngDataID(), domain.getPickUpState().value, domain.getOccurrenceDate(),
				domain.getUnknownDate());
	}

	private KrqdtAppHdSub toEntity(AbsenceLeaveApp absApp) {
		KrqdtAppHdSub entity = new KrqdtAppHdSub();
		entity.setAppID(absApp.getAppID());
		entity.setWorkTypeCD(absApp.getWorkTypeCD().v());
		entity.setChangeWorkHoursAtr(absApp.getChangeWorkHoursType().value);
		entity.setWorkTimeCD(absApp.getWorkTimeCD());
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

	@Override
	public void remove(String appID) {
		Optional<KrqdtAppHdSub> entityOpt = this.queryProxy().find(appID, KrqdtAppHdSub.class);
		if (entityOpt.isPresent()) {
			this.commandProxy().remove(KrqdtAppHdSub.class, appID);
		}

	}

	/**
	 * find AbsenceLeaveApp By AppId
	 *
	 * @author hoatt
	 * @param applicationID
	 * @return
	 */
	@Override
	public Optional<AbsenceLeaveApp> findByAppId(String applicationID) {
		return this.queryProxy().find(applicationID, KrqdtAppHdSub.class).map(x -> toDomainMain(x));
	}

	/**
	 * convert entity to domain
	 *
	 * @author hoatt
	 * @param entity
	 * @return
	 */
	private AbsenceLeaveApp toDomainMain(KrqdtAppHdSub entity) {
		AbsenceLeaveWorkingHour WorkTime1 = new AbsenceLeaveWorkingHour(new WorkTime(entity.getStartWorkTime1()),
				new WorkTime(entity.getEndWorkTime1()));
		AbsenceLeaveWorkingHour WorkTime2 = new AbsenceLeaveWorkingHour(new WorkTime(entity.getStartWorkTime2()),
				new WorkTime(entity.getEndWorkTime2()));
		return new AbsenceLeaveApp(entity.getAppID(), new WorkTypeCode(entity.getWorkTypeCD()),
				EnumAdaptor.valueOf(entity.getChangeWorkHoursAtr(), NotUseAtr.class), entity.getWorkTimeCD(), WorkTime1,
				WorkTime2, null, null);
	}
}
