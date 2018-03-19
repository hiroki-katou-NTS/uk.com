package nts.uk.ctx.at.record.infra.repository.divergence.time.setting;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTime;
import nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeGetMemento;
import nts.uk.ctx.at.record.dom.divergence.time.setting.*;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDvgcAttendance;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDvgcAttendancePK;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDvgcAttendancePK_;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDvgcAttendance_;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDvgcTime;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDvgcTimePK_;
import nts.uk.ctx.at.record.infra.entity.divergence.time.KrcstDvgcTime_;

// TODO: Auto-generated Javadoc
/**
 * The Class JpaDivergenceTimeRepository.
 */
@Stateless
public class JpaDivergenceTimeRepository extends JpaRepository implements DivergenceTimeRepository {

	/**
	 * get all divergence time.
	 *
	 * @param companyId
	 *            the company id
	 * @return the all div time
	 */

	@Override
	public List<DivergenceTime> getAllDivTime(String companyId) {
		return this.findByCompanyId(companyId);
	}

	/**
	 * Gets the div time info.
	 *
	 * @param companyId
	 *            the company id
	 * @param divTimeNo
	 *            the div time no
	 * @return the div time info
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeRepository
	 * #getDivTimeInfo(java.lang.String, int)
	 */
	@Override
	public DivergenceTime getDivTimeInfo(String companyId, int divTimeNo) {

		return this.findByCode(companyId, divTimeNo);
	}

	/**
	 * To domain.
	 *
	 * @param entities
	 *            the entities
	 * @return the divergence time
	 */
	private DivergenceTime toDomain(KrcstDvgcTime entities) {
		DivergenceTimeGetMemento memento = new JpaDivergenceTimeRepositoryGetMemento(entities);
		return new DivergenceTime(memento);
	}

	/**
	 * To entity attendance.
	 *
	 * @param divTimeNo
	 *            the div time no
	 * @param companyId
	 *            the company id
	 * @param attendanceId
	 *            the attendance id
	 * @return the krcst dvgc attendance
	 */
	private KrcstDvgcAttendance toEntityAttendance(int divTimeNo, String companyId, Integer attendanceId) {

		KrcstDvgcAttendancePK PK = toEntityAttendancePK(divTimeNo, companyId, attendanceId);

		KrcstDvgcAttendance entity = this.queryProxy().find(PK, KrcstDvgcAttendance.class)
				.orElse(new KrcstDvgcAttendance());

		return entity;
	}

	/**
	 * Delete all attendance.
	 *
	 * @param divTimeNo
	 *            the div time no
	 * @param companyId
	 *            the company id
	 */
	private void deleteAllAttendance(int divTimeNo, String companyId) {

		// Get List entity
		List<KrcstDvgcAttendance> attendanceList = findAttendanceEntity(companyId, divTimeNo);

		// Remove List
		this.commandProxy().removeAll(KrcstDvgcAttendance.class, attendanceList);
		this.getEntityManager().flush();

	}

	/**
	 * Adds the attendance.
	 *
	 * @param attendanceList
	 *            the attendance list
	 */
	private void addAttendance(List<KrcstDvgcAttendance> attendanceList) {

		this.commandProxy().insertAll(attendanceList);

	}

	/**
	 * To entity attendance PK.
	 *
	 * @param divTimeNo
	 *            the div time no
	 * @param companyId
	 *            the company id
	 * @param attendanceId
	 *            the attendance id
	 * @return the krcst dvgc attendance PK
	 */
	private KrcstDvgcAttendancePK toEntityAttendancePK(int divTimeNo, String companyId, Integer attendanceId) {
		return new KrcstDvgcAttendancePK(divTimeNo, companyId, attendanceId);
	}

	/**
	 * Find by company id.
	 *
	 * @param companyId
	 *            the company id
	 * @return the list
	 */
	private List<DivergenceTime> findByCompanyId(String companyId) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KrcstDvgcTime> cq = criteriaBuilder.createQuery(KrcstDvgcTime.class);
		Root<KrcstDvgcTime> root = cq.from(KrcstDvgcTime.class);

		// Build query
		cq.select(root);

		// create where conditions
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(KrcstDvgcTime_.id).get(KrcstDvgcTimePK_.cid), companyId));

		// add where to query
		cq.where(predicates.toArray(new Predicate[] {}));

		// query data
		List<KrcstDvgcTime> KrcstDvgcTime = em.createQuery(cq).getResultList();

		// return
		return KrcstDvgcTime.isEmpty() ? new ArrayList<DivergenceTime>()
				: KrcstDvgcTime.stream().map(item -> this.toDomain(item)).collect(Collectors.toList());
	}

	/**
	 * Find by code.
	 *
	 * @param companyId
	 *            the company id
	 * @param divTimeNo
	 *            the div time no
	 * @return the divergence time
	 */
	private DivergenceTime findByCode(String companyId, int divTimeNo) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KrcstDvgcTime> cq = criteriaBuilder.createQuery(KrcstDvgcTime.class);
		Root<KrcstDvgcTime> root = cq.from(KrcstDvgcTime.class);

		// Build query
		cq.select(root);

		// create where conditions
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(criteriaBuilder.equal(root.get(KrcstDvgcTime_.id).get(KrcstDvgcTimePK_.cid), companyId));
		predicates.add(criteriaBuilder.equal(root.get(KrcstDvgcTime_.id).get(KrcstDvgcTimePK_.no), divTimeNo));

		// add where to query
		cq.where(predicates.toArray(new Predicate[] {}));

		// query data
		KrcstDvgcTime KrcstDvgcTime = em.createQuery(cq).getResultList().get(0);

		// return
		if (KrcstDvgcTime != null)
			return this.toDomain(KrcstDvgcTime);
		return new DivergenceTime(null);

	}

	/**
	 * Find attendance id.
	 *
	 * @param companyId
	 *            the company id
	 * @param divTimeNo
	 *            the div time no
	 * @return the list
	 */
	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.divergence.time.setting.DivergenceTimeRepository
	 * #findAttendanceId(java.lang.String, int)
	 */
	@Override
	public List<Integer> findAttendanceId(String companyId, int divTimeNo) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KrcstDvgcAttendance> cq = criteriaBuilder.createQuery(KrcstDvgcAttendance.class);
		Root<KrcstDvgcAttendance> root = cq.from(KrcstDvgcAttendance.class);

		// Build query
		cq.select(root);

		// create where conditions
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(
				criteriaBuilder.equal(root.get(KrcstDvgcAttendance_.id).get(KrcstDvgcAttendancePK_.cid), companyId));
		predicates.add(
				criteriaBuilder.equal(root.get(KrcstDvgcAttendance_.id).get(KrcstDvgcAttendancePK_.no), divTimeNo));

		// add where to query
		cq.where(predicates.toArray(new Predicate[] {}));

		// query data
		List<KrcstDvgcAttendance> KrcstDvgcAttendance = em.createQuery(cq).getResultList();

		// return
		return KrcstDvgcAttendance.isEmpty() ? new ArrayList<Integer>()
				: KrcstDvgcAttendance.stream().map(item -> item.getId().getAttendanceId()).collect(Collectors.toList());

	}

	/**
	 * To entity.
	 *
	 * @param domain
	 *            the domain
	 * @return the krcst dvgc time
	 */
	private KrcstDvgcTime toEntity(DivergenceTime domain) {
		KrcstDvgcTime entity = this.queryProxy().find(domain.getCompanyId(), KrcstDvgcTime.class)
				.orElse(new KrcstDvgcTime());

		domain.saveToMemento(new JpaDivergenceTimeRepositorySetMemento(entity));

		return entity;

	}

	/**
	 * Update.
	 *
	 * @param divTimeDomain
	 *            the div time domain
	 */
	@Override
	public void update(DivergenceTime divTimeDomain) {

		// Update Divergence Time Information
		this.commandProxy().update(this.toEntity(divTimeDomain));

		// Delete Divergence Attendance List
		this.deleteAllAttendance(divTimeDomain.getDivergenceTimeNo(), divTimeDomain.getCompanyId());

		// Get Divergence Attendance Entity List
		List<KrcstDvgcAttendance> attendanceList = divTimeDomain.getTargetItems().stream()
				.map(e -> toEntityAttendance(divTimeDomain.getDivergenceTimeNo(), divTimeDomain.getCompanyId(),
						(int) e.doubleValue()))
				.collect(Collectors.toList());

		// Add new Divergence Attendance List
		this.addAttendance(attendanceList);
	}

	/**
	 * Find attendance entity.
	 *
	 * @param companyId
	 *            the company id
	 * @param divTimeNo
	 *            the div time no
	 * @return the list
	 */
	private List<KrcstDvgcAttendance> findAttendanceEntity(String companyId, int divTimeNo) {
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();
		CriteriaQuery<KrcstDvgcAttendance> cq = criteriaBuilder.createQuery(KrcstDvgcAttendance.class);
		Root<KrcstDvgcAttendance> root = cq.from(KrcstDvgcAttendance.class);

		// Build query
		cq.select(root);

		// create where conditions
		List<Predicate> predicates = new ArrayList<>();
		predicates.add(
				criteriaBuilder.equal(root.get(KrcstDvgcAttendance_.id).get(KrcstDvgcAttendancePK_.cid), companyId));
		predicates.add(
				criteriaBuilder.equal(root.get(KrcstDvgcAttendance_.id).get(KrcstDvgcAttendancePK_.no), divTimeNo));

		// add where to query
		cq.where(predicates.toArray(new Predicate[] {}));

		// query data
		List<KrcstDvgcAttendance> KrcstDvgcAttendance = em.createQuery(cq).getResultList();

		// return
		return KrcstDvgcAttendance.isEmpty() ? new ArrayList<KrcstDvgcAttendance>() : KrcstDvgcAttendance;

	}

}
