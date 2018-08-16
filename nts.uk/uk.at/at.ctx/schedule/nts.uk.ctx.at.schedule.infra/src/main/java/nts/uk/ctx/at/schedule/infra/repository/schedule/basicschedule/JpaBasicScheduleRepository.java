/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.schedule.basicschedule;

import java.sql.Connection;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaDelete;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.time.GeneralDate;
import nts.gul.collection.CollectionUtil;
import nts.gul.text.StringUtil;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.ConfirmedAtr;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.childcareschedule.ChildCareSchedule;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.personalfee.WorkSchedulePersonFee;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workschedulebreak.WorkScheduleBreak;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletime.PersonFeeTime;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletime.WorkScheduleTime;
import nts.uk.ctx.at.schedule.dom.schedule.basicschedule.workscheduletimezone.WorkScheduleTimeZone;
import nts.uk.ctx.at.schedule.dom.schedule.schedulemaster.ScheMasterInfo;
import nts.uk.ctx.at.schedule.dom.schedule.workschedulestate.WorkScheduleState;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.KscdtBasicSchedule;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.KscdtBasicSchedulePK;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.childcareschedule.KscdtScheChildCare;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.childcareschedule.KscdtScheChildCarePK_;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.childcareschedule.KscdtScheChildCare_;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.personalfee.KscdtScheFee;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.personalfee.KscdtScheFeePK_;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.personalfee.KscdtScheFeeTime;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.personalfee.KscdtScheFee_;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.workschedulebreak.KscdtWorkScheduleBreak;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.workschedulebreak.KscdtWorkScheduleBreakPK;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.workschedulebreak.KscdtWorkScheduleBreakPK_;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.workschedulebreak.KscdtWorkScheduleBreak_;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.workscheduletime.KscdtScheTime;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.workscheduletime.KscdtScheTimePK;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.workscheduletimezone.KscdtWorkScheduleTimeZone;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.workscheduletimezone.KscdtWorkScheduleTimeZonePK_;
import nts.uk.ctx.at.schedule.infra.entity.schedule.basicschedule.workscheduletimezone.KscdtWorkScheduleTimeZone_;
import nts.uk.ctx.at.schedule.infra.entity.schedule.schedulemaster.KscdtScheMasterInfo;
import nts.uk.ctx.at.schedule.infra.entity.schedule.schedulemaster.KscdtScheMasterInfoPK;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedulestate.KscdtScheState;
import nts.uk.ctx.at.schedule.infra.entity.schedule.workschedulestate.KscdtScheStatePK;
import nts.uk.ctx.at.schedule.infra.repository.schedule.basicschedule.childcareschedule.JpaChildCareScheduleGetMemento;
import nts.uk.ctx.at.schedule.infra.repository.schedule.basicschedule.childcareschedule.JpaChildCareScheduleSetMememto;
import nts.uk.ctx.at.schedule.infra.repository.schedule.basicschedule.personalfee.JpaWorkSchedulePersonFeeGetMemento;
import nts.uk.ctx.at.schedule.infra.repository.schedule.basicschedule.workscheduletimezone.JpaWorkScheduleTimeZoneSetMemento;
import nts.uk.shr.com.time.calendar.period.DatePeriod;

/**
 * The Class JpaBasicScheduleRepository.
 */
@Stateless
public class JpaBasicScheduleRepository extends JpaRepository implements BasicScheduleRepository {

	public static final String GET_LIST_DATE_BY_LIST_SID = "SELECT a.kscdpBSchedulePK.date "
			+ "FROM KscdtBasicSchedule a " + "WHERE a.kscdpBSchedulePK.sId IN :sIds "
			+ "ORDER BY a.kscdpBSchedulePK.date DESC";

	private static final String QUERY_BY_SID_PERIOD = "SELECT c FROM KscdtBasicSchedule c"
			+ " WHERE c.kscdpBSchedulePK.sId = :employeeId" + " AND c.kscdpBSchedulePK.date >= :startDate"
			+ " AND c.kscdpBSchedulePK.date <= :endDate";

	public static final String GET_LIST_BY_LIST_SID_DATE = "SELECT a " + "FROM KscdtBasicSchedule a "
			+ "WHERE a.kscdpBSchedulePK.sId IN :sIds "
			+ "AND (a.kscdpBSchedulePK.date BETWEEN :startDate AND :endDate)";

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository
	 * #insert(nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicSchedule)
	 */
//	@Override
//	public void insert(BasicSchedule bSchedule) {
//		String employeeId = bSchedule.getEmployeeId();
//		GeneralDate date = bSchedule.getDate();
//		KscdtBasicSchedule x = toEntity(bSchedule);
//		this.removeAllChildCare(employeeId, date);
//		this.commandProxy().insert(x);
//		this.insertAllChildCare(employeeId, date, bSchedule.getChildCareSchedules());
//		List<WorkScheduleTimeZone> list = new ArrayList<>();
//		bSchedule.getWorkScheduleTimeZones().stream()
//				.filter(map -> (map.getScheduleStartClock() != null && map.getScheduleEndClock() != null))
//				.map(map -> list.add(map)).collect(Collectors.toList());
//		if (list.size() > 0) {
//			this.insertAllWorkScheduleTimeZone(employeeId, bSchedule.getDate(), list);
//		}
//		this.insertScheduleMaster(bSchedule.getWorkScheduleMaster());
//		this.insertScheduleBreakTime(employeeId, date, bSchedule.getWorkScheduleBreaks());
//		this.insertScheduleTime(employeeId, date, bSchedule.getWorkScheduleTime());
//	}
	
	@Override
	public void insert(BasicSchedule bSchedule) {
		KscdtBasicSchedule x = toEntity(bSchedule);
		this.commandProxy().insert(x);
		
		this.insertRelateToWorkTimeCd(bSchedule);
		this.insertScheduleMaster(bSchedule.getWorkScheduleMaster());
//		this.insertAllScheduleState(bSchedule.getWorkScheduleStates());
	}
	
//	@Override
//	public void insertKSU001(BasicSchedule bSchedule) {
//		String employeeId = bSchedule.getEmployeeId();
//		GeneralDate date = bSchedule.getDate();
//		KscdtBasicSchedule x = toEntity(bSchedule);
//		this.commandProxy().insert(x);
//		
//		List<WorkScheduleTimeZone> list = new ArrayList<>();
//		bSchedule.getWorkScheduleTimeZones().stream()
//				.filter(map -> (map.getScheduleStartClock() != null && map.getScheduleEndClock() != null))
//				.map(map -> list.add(map)).collect(Collectors.toList());
//		if (list.size() > 0) {
//			this.insertAllWorkScheduleTimeZone(employeeId, bSchedule.getDate(), list);
//		}
//		this.insertScheduleBreakTime(employeeId, date, bSchedule.getWorkScheduleBreaks());
//		// this.removeAllChildCare(employeeId, date);
//		// this.insertAllChildCare(employeeId, date, bSchedule.getChildCareSchedules());
//		this.insertScheduleTime(employeeId, date, bSchedule.getWorkScheduleTime());
//		this.insertScheduleMaster(bSchedule.getWorkScheduleMaster());
//		this.insertAllScheduleState(bSchedule.getWorkScheduleStates());
//	}
	
	@Override
	public void insertRelateToWorkTimeCd(BasicSchedule bSchedule) {
		String employeeId = bSchedule.getEmployeeId();
		GeneralDate date = bSchedule.getDate();
		List<WorkScheduleTimeZone> list = new ArrayList<>();
		bSchedule.getWorkScheduleTimeZones().stream()
				.filter(map -> (map.getScheduleStartClock() != null && map.getScheduleEndClock() != null))
				.map(map -> list.add(map)).collect(Collectors.toList());
		if (list.size() > 0) {
			this.insertAllWorkScheduleTimeZone(employeeId, bSchedule.getDate(), list);
		}
		this.insertScheduleBreakTime(employeeId, date, bSchedule.getWorkScheduleBreaks());
		this.insertScheduleTime(employeeId, date, bSchedule.getWorkScheduleTime());
		// this.removeAllChildCare(employeeId, date);
		// this.insertAllChildCare(employeeId, date, bSchedule.getChildCareSchedules());
	}
	
	public void insertScheTimeZone(BasicSchedule bSchedule) {
		List<WorkScheduleTimeZone> list = new ArrayList<>();
		bSchedule.getWorkScheduleTimeZones().stream()
				.filter(map -> (map.getScheduleStartClock() != null && map.getScheduleEndClock() != null))
				.map(map -> list.add(map)).collect(Collectors.toList());
		if (list.size() > 0) {
			this.insertAllWorkScheduleTimeZone(bSchedule.getEmployeeId(), bSchedule.getDate(), list);
		}
	}
	
	public void insertScheTime(BasicSchedule bSchedule) {
		this.insertScheduleTime(bSchedule.getEmployeeId(), bSchedule.getDate(), bSchedule.getWorkScheduleTime());
	}
	
	public void insertScheBreak(BasicSchedule bSchedule) {
		this.insertScheduleBreakTime(bSchedule.getEmployeeId(), bSchedule.getDate(), bSchedule.getWorkScheduleBreaks());
	}

	@Override
	public void insertAll(List<BasicSchedule> listBSchedule) {
		listBSchedule.forEach(x -> insert(x));
	}

	/**
	 * follow document phrase 3 KSU001, kscdtScheChildCare and
	 * kscdtScheMasterInfo no need update
	 */
	@Override
	public void update(BasicSchedule bSchedule) {
		String employeeId = bSchedule.getEmployeeId();
		GeneralDate date = bSchedule.getDate();
		this.commandProxy().update(this.toEntityUpdate(bSchedule));
		// this.removeAllChildCare(bSchedule.getEmployeeId(), bSchedule.getDate());
		// this.insertAllChildCare(bSchedule.getEmployeeId(), bSchedule.getDate(), bSchedule.getChildCareSchedules());
		this.removeAllTimeZone(employeeId, date);
		this.insertAllWorkScheduleTimeZone(employeeId, date, bSchedule.getWorkScheduleTimeZones());
		this.updateScheduleMaster(bSchedule.getWorkScheduleMaster());
		this.updateScheduleBreakTime(employeeId, date, bSchedule.getWorkScheduleBreaks());
		this.updateScheduleTime(employeeId, date, bSchedule.getWorkScheduleTime());
	}
	
	public void updateScheBasic(BasicSchedule bSchedule) {
		this.commandProxy().update(this.toEntityUpdate(bSchedule));
	}
	
	public void updateScheTime(BasicSchedule bSchedule) {
		this.updateScheduleTime(bSchedule.getEmployeeId(), bSchedule.getDate(), bSchedule.getWorkScheduleTime());
	}
	
	public void updateScheBreak(BasicSchedule bSchedule) {
		this.updateScheduleBreakTime(bSchedule.getEmployeeId(), bSchedule.getDate(), bSchedule.getWorkScheduleBreaks());
	}
	
//	@Override
//	public void updateKSU001(BasicSchedule bSchedule) {
//		String employeeId = bSchedule.getEmployeeId();
//		GeneralDate date = bSchedule.getDate();
//		this.commandProxy().update(this.toEntityUpdate(bSchedule));
//		this.removeAllTimeZone(employeeId, date);
//		this.insertAllWorkScheduleTimeZone(employeeId, date, bSchedule.getWorkScheduleTimeZones());
////		this.removeAllChildCare(bSchedule.getEmployeeId(), bSchedule.getDate());
////		this.insertAllChildCare(bSchedule.getEmployeeId(), bSchedule.getDate(), bSchedule.getChildCareSchedules());
//		this.updateScheduleBreakTime(employeeId, date, bSchedule.getWorkScheduleBreaks());
//		this.updateScheduleTime(employeeId, date, bSchedule.getWorkScheduleTime());
//		this.updateAllScheState(employeeId, date, bSchedule.getWorkScheduleStates());
//	}
	

	@Override
	public void updateAll(List<BasicSchedule> listBSchedule) {
		listBSchedule.forEach(x -> update(x));
	}

	@Override
	public void changeWorkTypeTime(String sId, GeneralDate date, String workTypeCode, String workTimeCode) {
		Optional<KscdtBasicSchedule> optionalEntity = this.findById(sId, date);
		if (!optionalEntity.isPresent()) {
			return;
		}
		KscdtBasicSchedule schedule = optionalEntity.get();
		if (workTypeCode != null) {
			schedule.workTypeCode = workTypeCode;
		}
		if (workTimeCode != null) {
			schedule.workTimeCode = workTimeCode;
		}
		this.commandProxy().update(schedule);
		this.getEntityManager().flush();
	}

	/**
	 * update work schedule time zone
	 * 
	 * @param bSchedule
	 * @return
	 */
	// private List<KscdtWorkScheduleTimeZone>
	// updateWorkScheduleTimeZone(BasicSchedule bSchedule) {
	// List<WorkScheduleTimeZone> scheduleTimeZones =
	// bSchedule.getWorkScheduleTimeZones();
	// List<KscdtWorkScheduleTimeZone> entities = new
	// ArrayList<KscdtWorkScheduleTimeZone>();
	// scheduleTimeZones.forEach(schedule -> {
	// KscdtWorkScheduleTimeZone entity = new KscdtWorkScheduleTimeZone();
	// String employeeId = bSchedule.getEmployeeId();
	// GeneralDate date = bSchedule.getDate();
	// Optional<KscdtWorkScheduleTimeZone> optionalEntity =
	// this.findWorkScheduleTimeZone(employeeId, date,
	// schedule.getScheduleCnt());
	// // check null of startTime-endTime
	// if (schedule.getScheduleStartClock() == null ||
	// schedule.getScheduleEndClock() == null) {
	// if (optionalEntity.isPresent()) {
	// entity = optionalEntity.get();
	// this.commandProxy().remove(KscdtWorkScheduleTimeZone.class,
	// entity.kscdtWorkScheduleTimeZonePk);
	// }
	// return;
	// }
	//
	// if (optionalEntity.isPresent()) {
	// entity = optionalEntity.get();
	// }
	// schedule.saveToMemento(new JpaWorkScheduleTimeZoneSetMemento(entity,
	// employeeId, date));
	// entities.add(entity);
	// });
	// return entities;
	// }

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository
	 * #delete(java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public void delete(String employeeId, GeneralDate baseDate) {
		this.commandProxy().remove(KscdtBasicSchedule.class, new KscdtBasicSchedulePK(employeeId, baseDate));
		this.removeAllChildCare(employeeId, baseDate);
		this.removeAllTimeZone(employeeId, baseDate);
		this.removeAllScheduleBreakTime(employeeId, baseDate);
		this.removeScheduleMaster(employeeId, baseDate);
		this.removeScheduleTime(employeeId, baseDate);
	}
	
	@Override
	public void deleteWithWorkTimeCodeNull(String employeeId, GeneralDate baseDate) {
		this.removeAllTimeZone(employeeId, baseDate);
		this.removeAllScheduleBreakTime(employeeId, baseDate);
		this.removeAllChildCare(employeeId, baseDate);
		this.removeScheduleTime(employeeId, baseDate);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository
	 * #find(java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public Optional<BasicSchedule> find(String sId, GeneralDate date) {
		Optional<KscdtBasicSchedule> optionalEntity = this.findById(sId, date);
		if (optionalEntity.isPresent()) {
			BasicSchedule basicSchedule = this.toDomain(optionalEntity.get(),
					this.findAllWorkScheduleTimeZone(sId, date));
			basicSchedule.setWorkScheduleMaster(toDomainScheMaster(optionalEntity.get().getKscdtScheMasterInfo()));
			return Optional.of(basicSchedule);
		}
		return Optional.empty();
	}
	
	@Override
	public Optional<BasicSchedule> findWithAllChild(String sId, GeneralDate date) {
		Optional<KscdtBasicSchedule> optionalEntity = this.findById(sId, date);
		if (optionalEntity.isPresent()) {
			BasicSchedule basicSchedule = this.toDomain(optionalEntity.get(),
					this.findAllWorkScheduleTimeZone(sId, date));
			basicSchedule.setWorkScheduleMaster(toDomainScheMaster(optionalEntity.get().getKscdtScheMasterInfo()));
			basicSchedule.setWorkScheduleBreaks(optionalEntity.get().getKscdtScheBreak().stream().map(x -> toDomainWorkScheduleBreak(x)).collect(Collectors.toList()));
			basicSchedule.setWorkScheduleTime(toDomainScheduleTime(optionalEntity.get().getKscdtScheTime()));
			return Optional.of(basicSchedule);
		}
		return Optional.empty();
	}

	/**
	 * 
	 * @param kscdtScheMasterEntity
	 * @return
	 */
	private ScheMasterInfo toDomainScheMaster(KscdtScheMasterInfo kscdtScheMasterEntity) {
		ScheMasterInfo scheMasterInfo = ScheMasterInfo.createFromJavaType(
				kscdtScheMasterEntity.kscdtScheMasterInfoPk.sId,
				kscdtScheMasterEntity.kscdtScheMasterInfoPk.generalDate, kscdtScheMasterEntity.employmentCd,
				kscdtScheMasterEntity.classificationCd, kscdtScheMasterEntity.businessTypeCd,
				kscdtScheMasterEntity.jobId, kscdtScheMasterEntity.workplaceId);
		return scheMasterInfo;
	}
	
	private WorkScheduleTime toDomainScheduleTime(KscdtScheTime entity) {
		if(entity == null){
			return null;
		}
		
		WorkScheduleTime workScheduleTime = WorkScheduleTime.createFromJavaType(entity.getKscdtScheFeeTime().stream().map(x -> toDomainPersonFeeTime(x)).collect(Collectors.toList()),
				entity.getBreakTime(), entity.getWorkingTime(), entity.getWeekdayTime(), entity.getPrescribedTime(),
				entity.getTotalLaborTime(), entity.getChildCareTime());
		return workScheduleTime;
	}
	
	private PersonFeeTime toDomainPersonFeeTime(KscdtScheFeeTime entityScheFeeTime) {
		PersonFeeTime personFeeTime = PersonFeeTime.createFromJavaType(entityScheFeeTime.getKscdtScheFeeTimePK().getNo(), entityScheFeeTime.getPersonFeeTime());
		return personFeeTime;
	}

	@Override
	public boolean isExists(String employeeId, GeneralDate date) {
		Optional<KscdtBasicSchedule> result = findById(employeeId, date);
		return result.isPresent();
	}

	/**
	 * Find by id.
	 *
	 * @param employeeId
	 *            the employee id
	 * @param date
	 *            the date
	 * @return the optional
	 */
	private Optional<KscdtBasicSchedule> findById(String employeeId, GeneralDate date) {
		return this.queryProxy().find(new KscdtBasicSchedulePK(employeeId, date), KscdtBasicSchedule.class);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository
	 * #findChildCareById(java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<ChildCareSchedule> findChildCareById(String employeeId, GeneralDate baseDate) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSCMT_CHILD_CARE_SCH (KscmtChildCareSch SQL)
		CriteriaQuery<KscdtScheChildCare> cq = criteriaBuilder.createQuery(KscdtScheChildCare.class);

		// root data
		Root<KscdtScheChildCare> root = cq.from(KscdtScheChildCare.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal employee id
		lstpredicateWhere.add(criteriaBuilder
				.equal(root.get(KscdtScheChildCare_.kscdtScheChildCarePK).get(KscdtScheChildCarePK_.sid), employeeId));

		// equal year month date base date
		lstpredicateWhere.add(criteriaBuilder
				.equal(root.get(KscdtScheChildCare_.kscdtScheChildCarePK).get(KscdtScheChildCarePK_.ymd), baseDate));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// order by child care number asc
		cq.orderBy(criteriaBuilder
				.asc(root.get(KscdtScheChildCare_.kscdtScheChildCarePK).get(KscdtScheChildCarePK_.childCareNumber)));

		// create query
		TypedQuery<KscdtScheChildCare> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(entity -> this.toDomainChildCare(entity))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.schedule.dom.schedule.basicschedule.BasicScheduleRepository
	 * #findPersonFeeById(java.lang.String, nts.arc.time.GeneralDate)
	 */
	@Override
	public List<WorkSchedulePersonFee> findPersonFeeById(String employeeId, GeneralDate baseDate) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSCMT_WS_PERSON_FEE (KscmtWsPersonFee SQL)
		CriteriaQuery<KscdtScheFee> cq = criteriaBuilder.createQuery(KscdtScheFee.class);

		// root data
		Root<KscdtScheFee> root = cq.from(KscdtScheFee.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal employee id
		lstpredicateWhere.add(
				criteriaBuilder.equal(root.get(KscdtScheFee_.kscdtScheFeePK).get(KscdtScheFeePK_.sid), employeeId));

		// equal year month date base date
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KscdtScheFee_.kscdtScheFeePK).get(KscdtScheFeePK_.ymd), baseDate));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// order by no id asc
		cq.orderBy(criteriaBuilder.asc(root.get(KscdtScheFee_.kscdtScheFeePK).get(KscdtScheFeePK_.no)));

		// create query
		TypedQuery<KscdtScheFee> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(entity -> this.toDomainPersonFee(entity))
				.collect(Collectors.toList());
	}

	@Override
	public List<WorkScheduleBreak> findWorkBreakTime(String employeeId, GeneralDate baseDate) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		CriteriaQuery<KscdtWorkScheduleBreak> cq = criteriaBuilder.createQuery(KscdtWorkScheduleBreak.class);

		// root data
		Root<KscdtWorkScheduleBreak> root = cq.from(KscdtWorkScheduleBreak.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal employee id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KscdtWorkScheduleBreak_.kscdtWorkScheduleBreakPk).get(KscdtWorkScheduleBreakPK_.sId),
				employeeId));

		// equal year month date base date
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KscdtWorkScheduleBreak_.kscdtWorkScheduleBreakPk).get(KscdtWorkScheduleBreakPK_.date),
				baseDate));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KscdtWorkScheduleBreak> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(entity -> this.toDomainWorkScheduleBreak(entity))
				.collect(Collectors.toList());
	}

	/**
	 * Convert entity KscdtWorkScheduleBreak to domain object WorkScheduleBreak
	 * 
	 * @param entity
	 * @return
	 */
	private WorkScheduleBreak toDomainWorkScheduleBreak(KscdtWorkScheduleBreak entity) {
		return WorkScheduleBreak.createFromJavaType(entity.kscdtWorkScheduleBreakPk.scheduleBreakCnt, entity.scheduleStartClock,
				entity.scheduleEndClock);
	}

	/**
	 * Insert all child care.
	 *
	 * @param employeeId
	 *            the employee id
	 * @param baseDate
	 *            the base date
	 * @param childCareSchedules
	 *            the child care schedules
	 */
	private void insertAllChildCare(String employeeId, GeneralDate baseDate,
			List<ChildCareSchedule> childCareSchedules) {
		if (CollectionUtil.isEmpty(childCareSchedules)) {
			return;
		}
		List<KscdtScheChildCare> entityChildCares = childCareSchedules.stream().map(domain -> {
			KscdtScheChildCare entity = new KscdtScheChildCare();
			domain.saveToMemento(new JpaChildCareScheduleSetMememto(entity, employeeId, baseDate));
			return entity;
		}).collect(Collectors.toList());
		this.commandProxy().insertAll(entityChildCares);
	}

	/**
	 * To domain child care.
	 *
	 * @param entity
	 *            the entity
	 * @return the child care schedule
	 */
	private ChildCareSchedule toDomainChildCare(KscdtScheChildCare entity) {
		return new ChildCareSchedule(new JpaChildCareScheduleGetMemento(entity));
	}

	/**
	 * To domain person fee.
	 *
	 * @param entity
	 *            the entity
	 * @return the work schedule person fee
	 */
	private WorkSchedulePersonFee toDomainPersonFee(KscdtScheFee entity) {
		return new WorkSchedulePersonFee(new JpaWorkSchedulePersonFeeGetMemento(entity));
	}

	/**
	 * To entity.
	 *
	 * @param domain
	 *            the domain
	 * @return the kscdt basic schedule
	 */
	private KscdtBasicSchedule toEntity(BasicSchedule domain) {
		KscdtBasicSchedule entity = new KscdtBasicSchedule();
		domain.saveToMemento(new JpaBasicScheduleSetMemento(entity));
		return entity;
	}

	/**
	 * To entity.
	 *
	 * @param domain
	 *            the domain
	 * @return the kscdt basic schedule
	 */
	private KscdtBasicSchedule toEntityUpdate(BasicSchedule domain) {
		KscdtBasicSchedule entity = new KscdtBasicSchedule();
		Optional<KscdtBasicSchedule> optionalEntity = this.findById(domain.getEmployeeId(), domain.getDate());
		entity = optionalEntity.get();
		domain.saveToMemento(new JpaBasicScheduleSetMemento(entity));
		entity.workTimeCode = StringUtil.isNullOrEmpty(domain.getWorkTimeCode(), true) ? null
				: domain.getWorkTimeCode();

		return entity;
	}

	/**
	 * To domain.
	 *
	 * @param entity
	 *            the entity
	 * @param entityTimeZones
	 *            the entity time zones
	 * @return the basic schedule
	 */
	private BasicSchedule toDomain(KscdtBasicSchedule entity, List<KscdtWorkScheduleTimeZone> entityTimeZones) {
		return new BasicSchedule(new JpaBasicScheduleGetMemento(entity, entityTimeZones));
	}

	private BasicSchedule toDomain(KscdtBasicSchedule entity) {
		return new BasicSchedule(entity.kscdpBSchedulePK.sId, entity.kscdpBSchedulePK.date, entity.workTypeCode,
				entity.workTypeCode, ConfirmedAtr.valueOf(entity.confirmedAtr));
	}

	/**
	 * Insert all work schedule time zone.
	 *
	 * @param employeeId
	 *            the employee id
	 * @param baseDate
	 *            the base date
	 * @param list
	 *            the list
	 */
	private void insertAllWorkScheduleTimeZone(String employeeId, GeneralDate baseDate,
			List<WorkScheduleTimeZone> list) {
		if (CollectionUtil.isEmpty(list)) {
			return;
		}
		List<KscdtWorkScheduleTimeZone> entityWorkTimeZone = list.stream().map(domain -> {
			KscdtWorkScheduleTimeZone entity = new KscdtWorkScheduleTimeZone();
			domain.saveToMemento(new JpaWorkScheduleTimeZoneSetMemento(entity, employeeId, baseDate));
			return entity;
		}).collect(Collectors.toList());
		this.commandProxy().insertAll(entityWorkTimeZone);
	}

	/**
	 * Removes the all child care.
	 *
	 * @param employeeId
	 *            the employee id
	 * @param baseDate
	 *            the base date
	 */
	private void removeAllChildCare(String employeeId, GeneralDate baseDate) {

		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSCMT_CHILD_CARE_SCH (KscdtScheChildCare SQL)
		CriteriaDelete<KscdtScheChildCare> cq = criteriaBuilder.createCriteriaDelete(KscdtScheChildCare.class);

		// root data
		Root<KscdtScheChildCare> root = cq.from(KscdtScheChildCare.class);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal employee id
		lstpredicateWhere.add(criteriaBuilder
				.equal(root.get(KscdtScheChildCare_.kscdtScheChildCarePK).get(KscdtScheChildCarePK_.sid), employeeId));

		// equal year month date base date
		lstpredicateWhere.add(criteriaBuilder
				.equal(root.get(KscdtScheChildCare_.kscdtScheChildCarePK).get(KscdtScheChildCarePK_.ymd), baseDate));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		em.createQuery(cq).executeUpdate();

	}

	/**
	 * Removes the all time zone.
	 *
	 * @param employeeId
	 *            the employee id
	 * @param baseDate
	 *            the base date
	 */
	private void removeAllTimeZone(String employeeId, GeneralDate baseDate) {

		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSCDT_WORK_SCH_TIMEZONE (KscdtWorkScheduleTimeZone SQL)
		CriteriaDelete<KscdtWorkScheduleTimeZone> cq = criteriaBuilder
				.createCriteriaDelete(KscdtWorkScheduleTimeZone.class);

		// root data
		Root<KscdtWorkScheduleTimeZone> root = cq.from(KscdtWorkScheduleTimeZone.class);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal employee id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KscdtWorkScheduleTimeZone_.kscdtWorkScheduleTimeZonePk).get(KscdtWorkScheduleTimeZonePK_.sId),
				employeeId));

		// equal year month date base date
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KscdtWorkScheduleTimeZone_.kscdtWorkScheduleTimeZonePk).get(KscdtWorkScheduleTimeZonePK_.date),
				baseDate));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		em.createQuery(cq).executeUpdate();

	}

	/**
	 * find employeeId
	 * 
	 * @param employeeId
	 * @param date
	 * @param scheduleCnt
	 * @return
	 */
	// private Optional<KscdtWorkScheduleTimeZone>
	// findWorkScheduleTimeZone(String employeeId, GeneralDate date,
	// int scheduleCnt) {
	// return this.queryProxy().find(new KscdtWorkScheduleTimeZonePK(employeeId,
	// date, scheduleCnt),
	// KscdtWorkScheduleTimeZone.class);
	// }

	/**
	 * Find all work schedule time zone.
	 *
	 * @param employeeId
	 *            the employee id
	 * @param baseDate
	 *            the base date
	 * @return the list
	 */
	private List<KscdtWorkScheduleTimeZone> findAllWorkScheduleTimeZone(String employeeId, GeneralDate baseDate) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSCDT_WORK_SCH_TIMEZONE (KscdtWorkScheduleTimeZone SQL)
		CriteriaQuery<KscdtWorkScheduleTimeZone> cq = criteriaBuilder.createQuery(KscdtWorkScheduleTimeZone.class);

		// root data
		Root<KscdtWorkScheduleTimeZone> root = cq.from(KscdtWorkScheduleTimeZone.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal employee id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KscdtWorkScheduleTimeZone_.kscdtWorkScheduleTimeZonePk).get(KscdtWorkScheduleTimeZonePK_.sId),
				employeeId));

		// equal year month date base date
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KscdtWorkScheduleTimeZone_.kscdtWorkScheduleTimeZonePk).get(KscdtWorkScheduleTimeZonePK_.date),
				baseDate));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KscdtWorkScheduleTimeZone> query = em.createQuery(cq);

		// exclude select
		return query.getResultList();
	}

	/**
	 * insert 勤務予定マスタ情報
	 * 
	 * @param workScheduleMaster
	 */
	private void insertScheduleMaster(ScheMasterInfo scheMasterInfo) {
		KscdtScheMasterInfoPK primaryKey = new KscdtScheMasterInfoPK(scheMasterInfo.getSId(),
				scheMasterInfo.getGeneralDate());

		KscdtScheMasterInfo sscdtScheMasterInfo = new KscdtScheMasterInfo(primaryKey, scheMasterInfo.getEmploymentCd(),
				scheMasterInfo.getClassificationCd(), scheMasterInfo.getBusinessTypeCd(), scheMasterInfo.getJobId(),
				scheMasterInfo.getWorkplaceId());
		this.commandProxy().insert(sscdtScheMasterInfo);
	}

	/**
	 * update 勤務予定マスタ情報
	 * 
	 * @param workScheduleMaster
	 */
	private void updateScheduleMaster(ScheMasterInfo scheMasterInfo) {
		if (scheMasterInfo == null) {
			return;
		}

		KscdtScheMasterInfoPK primaryKey = new KscdtScheMasterInfoPK(scheMasterInfo.getSId(),
				scheMasterInfo.getGeneralDate());
		KscdtScheMasterInfo kscdtScheMasterInfo = this.queryProxy().find(primaryKey, KscdtScheMasterInfo.class).get();

		kscdtScheMasterInfo.employmentCd = scheMasterInfo.getEmploymentCd();
		kscdtScheMasterInfo.classificationCd = scheMasterInfo.getClassificationCd();
		kscdtScheMasterInfo.businessTypeCd = scheMasterInfo.getBusinessTypeCd();
		kscdtScheMasterInfo.jobId = scheMasterInfo.getJobId();
		kscdtScheMasterInfo.workplaceId = scheMasterInfo.getWorkplaceId();

		this.commandProxy().update(kscdtScheMasterInfo);
	}

	/**
	 * remove 勤務予定マスタ情報
	 * 
	 * @param employeeId
	 * @param baseDate
	 */
	private void removeScheduleMaster(String employeeId, GeneralDate baseDate) {
		KscdtScheMasterInfoPK primaryKey = new KscdtScheMasterInfoPK(employeeId, baseDate);
		if (this.queryProxy().find(primaryKey, KscdtScheMasterInfo.class).isPresent()) {
			this.commandProxy().remove(KscdtScheMasterInfo.class, new KscdtScheMasterInfoPK(employeeId, baseDate));
		}
	}

	/**
	 * insert 勤務予定休憩
	 * 
	 * @param workScheduleBreaks
	 */
	private void insertScheduleBreakTime(String employeeId, GeneralDate baseDate,
			List<WorkScheduleBreak> workScheduleBreaks) {
		if (CollectionUtil.isEmpty(workScheduleBreaks)) {
			return;
		}

		List<KscdtWorkScheduleBreak> entityWorkBreakList = workScheduleBreaks.stream().map(domain -> {
			KscdtWorkScheduleBreakPK key = new KscdtWorkScheduleBreakPK(employeeId, baseDate,
					domain.getScheduleBreakCnt().v());
			KscdtWorkScheduleBreak entity = new KscdtWorkScheduleBreak(key, domain.getScheduledStartClock().v(),
					domain.getScheduledEndClock().v());
			return entity;
		}).collect(Collectors.toList());

		this.commandProxy().insertAll(entityWorkBreakList);
	}

	private void insertAllScheduleState(List<WorkScheduleState> listWorkScheduleState) {
		if (listWorkScheduleState == null || listWorkScheduleState.size() == 0) {
			return;
		}

		List<KscdtScheState> entities = listWorkScheduleState.stream().map(x -> {
			KscdtScheStatePK pk = new KscdtScheStatePK(x.getSId(), x.getScheduleItemId(), x.getYmd());
			KscdtScheState entity = new KscdtScheState(pk, x.getScheduleEditState().value);
			return entity;
		}).collect(Collectors.toList());
		this.commandProxy().insertAll(entities);
	}

	/**
	 * update 勤務予定休憩
	 * 
	 * @param workScheduleBreaks
	 */
	private void updateScheduleBreakTime(String employeeId, GeneralDate baseDate,
			List<WorkScheduleBreak> listWorkScheduleBreaks) {
		if (CollectionUtil.isEmpty(listWorkScheduleBreaks)) {
			return;
		}

		Connection con = this.getEntityManager().unwrap(Connection.class);
		String sqlQuery = null;
		for (WorkScheduleBreak workScheduleBreak : listWorkScheduleBreaks) {
			sqlQuery = "Update KSCDT_SCHE_BREAK Set START_CLOCK = "
					+ workScheduleBreak.getScheduledStartClock().valueAsMinutes() + ", END_CLOCK = "
					+ workScheduleBreak.getScheduledEndClock().valueAsMinutes() + " Where SID = " + "'"+ employeeId + "'"
					+ " and BREAK_CNT = " + workScheduleBreak.getScheduleBreakCnt() + " and YMD = "
					+ "'"+baseDate+ "'";
			try {
				con.createStatement().executeUpdate(sqlQuery);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	private void updateAllScheState(String employeeId, GeneralDate baseDate,
			List<WorkScheduleState> listWorkScheduleState) {
		if (listWorkScheduleState.size() == 0) {
			return;
		}
		
		Connection con = this.getEntityManager().unwrap(Connection.class);
		String sqlQuery = null;
		for (WorkScheduleState workScheduleState : listWorkScheduleState) {
			sqlQuery = "Update KSCDT_SCHE_STATE Set SCHE_EDIT_STATE = " + workScheduleState.getScheduleEditState().value
					+ " Where SID = " + "'" + workScheduleState.getSId() + "'" + " and SCHE_ITEM_ID = "
					+ "'" +workScheduleState.getScheduleItemId()+ "'" + " and YMD = "
					+ "'" +workScheduleState.getYmd()+ "'";
			try {
				con.createStatement().executeUpdate(sqlQuery);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	/**
	 * Removes the all time zone.
	 *
	 * @param employeeId
	 *            the employee id
	 * @param baseDate
	 *            the base date
	 */
	private void removeAllScheduleBreakTime(String employeeId, GeneralDate baseDate) {

		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSCDT_WORK_SCH_TIMEZONE (KscdtWorkScheduleBreak SQL)
		CriteriaDelete<KscdtWorkScheduleBreak> cq = criteriaBuilder.createCriteriaDelete(KscdtWorkScheduleBreak.class);

		// root data
		Root<KscdtWorkScheduleBreak> root = cq.from(KscdtWorkScheduleBreak.class);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal employee id
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KscdtWorkScheduleBreak_.kscdtWorkScheduleBreakPk).get(KscdtWorkScheduleBreakPK_.sId),
				employeeId));

		// equal year month date base date
		lstpredicateWhere.add(criteriaBuilder.equal(
				root.get(KscdtWorkScheduleBreak_.kscdtWorkScheduleBreakPk).get(KscdtWorkScheduleBreakPK_.date),
				baseDate));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		em.createQuery(cq).executeUpdate();

	}

	/**
	 * insert 勤務予定時間
	 * 
	 * @param workScheduleTime
	 */
	private void insertScheduleTime(String employeeId, GeneralDate baseDate,
			Optional<WorkScheduleTime> workScheduleTime) {
		if (workScheduleTime == null || !workScheduleTime.isPresent()) {
			return;
		}

		WorkScheduleTime scheduleTime = workScheduleTime.get();
		KscdtScheTimePK key = new KscdtScheTimePK(employeeId, baseDate);
		KscdtScheTime entity = new KscdtScheTime(key);
		entity.setBreakTime(scheduleTime.getBreakTime().valueAsMinutes());
		entity.setChildCareTime(scheduleTime.getChildCareTime().valueAsMinutes());
		entity.setPrescribedTime(scheduleTime.getPredetermineTime().valueAsMinutes());
		entity.setTotalLaborTime(scheduleTime.getTotalLaborTime().valueAsMinutes());
		entity.setWeekdayTime(scheduleTime.getWeekdayTime().valueAsMinutes());
		entity.setWorkingTime(scheduleTime.getWorkingTime().valueAsMinutes());
		this.commandProxy().insert(entity);
	}

	/**
	 * update 勤務予定時間
	 * 
	 * @param workScheduleTime
	 */
	private void updateScheduleTime(String employeeId, GeneralDate baseDate,
			Optional<WorkScheduleTime> workScheduleTime) {
		if (workScheduleTime == null || !workScheduleTime.isPresent()) {
			return;
		}

		WorkScheduleTime scheduleTime = workScheduleTime.get();
		KscdtScheTimePK key = new KscdtScheTimePK(employeeId, baseDate);
		Optional<KscdtScheTime> entityOpt = this.queryProxy().find(key, KscdtScheTime.class);
		if(entityOpt.isPresent()){
			KscdtScheTime entity = entityOpt.get();
			entity.setBreakTime(scheduleTime.getBreakTime().valueAsMinutes());
			entity.setChildCareTime(scheduleTime.getChildCareTime().valueAsMinutes());
			entity.setPrescribedTime(scheduleTime.getPredetermineTime().valueAsMinutes());
			entity.setTotalLaborTime(scheduleTime.getTotalLaborTime().valueAsMinutes());
			entity.setWeekdayTime(scheduleTime.getWeekdayTime().valueAsMinutes());
			entity.setWorkingTime(scheduleTime.getWorkingTime().valueAsMinutes());
			this.commandProxy().update(entity);
		}else{
			this.insertScheduleTime(employeeId, baseDate, workScheduleTime);
		}
		
	}

	/**
	 * remove 勤務予定時間
	 * 
	 * @param workScheduleTime
	 */
//	private void removeScheduleTime(String employeeId, GeneralDate baseDate) {
//		KscdtScheTimePK key = new KscdtScheTimePK(employeeId, baseDate);
//		if (this.queryProxy().find(key, KscdtScheTime.class).isPresent()) {
//			this.commandProxy().remove(KscdtScheTime.class, key);
//		}
//	}
	
	private void removeScheduleTime(String employeeId, GeneralDate baseDate) {
		Connection con = this.getEntityManager().unwrap(Connection.class);
		String sqlQuery = null;
		sqlQuery = "Delete From KSCDT_SCHE_TIME Where SID = " + "'" + employeeId + "'" + " and YMD = "
				+ "'" + baseDate + "'";
		try {
			con.createStatement().executeUpdate(sqlQuery);
		} catch (SQLException e) {
			e.printStackTrace();
		}
	}

	/**
	 * 
	 * @param sIds
	 * @return
	 */
	@Override
	public GeneralDate findMaxDateByListSid(List<String> sIds) {
		List<GeneralDate> listDate = this.queryProxy().query(GET_LIST_DATE_BY_LIST_SID, GeneralDate.class)
				.setParameter("sIds", sIds).getList();
		if (listDate == null || listDate.isEmpty())
			return null;

		return listDate.get(0);
	}

	@Override
	public List<BasicSchedule> getBasicScheduleBySidPeriodDate(String employeeId, DatePeriod dateData) {
		List<BasicSchedule> lstData = this.queryProxy().query(QUERY_BY_SID_PERIOD, KscdtBasicSchedule.class)
				.setParameter("employeeId", employeeId).setParameter("startDate", dateData.start())
				.setParameter("endDate", dateData.end())
				.getList(x -> toDomain(x, this.findAllWorkScheduleTimeZone(employeeId, x.kscdpBSchedulePK.date)));
		return lstData;
	}

	@Override
	public List<BasicSchedule> findAllBetweenDate(List<String> sId, GeneralDate startDate, GeneralDate endDate) {
		List<BasicSchedule> result = this.queryProxy().query(GET_LIST_BY_LIST_SID_DATE, KscdtBasicSchedule.class)
				.setParameter("sIds", sId).setParameter("startDate", startDate).setParameter("endDate", endDate)
				.getList(x -> toDomain(x));
		return result;
	}

	@Override
	public void updateConfirmAtr(List<BasicSchedule> listBasicSchedule) {
		Connection con = this.getEntityManager().unwrap(Connection.class);
		String sqlQuery = null;
		for (BasicSchedule basicSchedule : listBasicSchedule) {
			sqlQuery = "Update KSCDT_SCHE_BASIC Set CONFIRMED_ATR = " + basicSchedule.getConfirmedAtr().value
					+ " Where SID = " + "'"+ basicSchedule.getEmployeeId() + "'" + " and YMD = " + "'" +basicSchedule.getDate()+ "'";
			try {
				con.createStatement().executeUpdate(sqlQuery);
			} catch (SQLException e) {
				e.printStackTrace();
			}
		}
	}

	@Override
	public void updateStartEndTimeZone() {
		// TODO Auto-generated method stub
		
	}
}
