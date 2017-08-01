package nts.uk.ctx.at.schedule.infra.repository.dailypattern;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.DailyPattern;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.DailyPatternRepository;
import nts.uk.ctx.at.schedule.infra.entity.dailypattern.KdpstDailyPatternSet;
import nts.uk.ctx.at.schedule.infra.entity.dailypattern.KdpstDailyPatternSetPK;
import nts.uk.ctx.at.schedule.infra.entity.dailypattern.KdpstDailyPatternSetPK_;
import nts.uk.ctx.at.schedule.infra.entity.dailypattern.KdpstDailyPatternSet_;

/**
 * The Class JpaDailyPatternRepository.
 */
@Stateless
public class JpaDailyPatternRepository extends JpaRepository implements DailyPatternRepository {

	/** The Constant FIRST_DATA. */
	public static final int FIRST_DATA = 0;

	/** The Constant FIRST_LENGTH. */
	public static final int FIRST_LENGTH = 1;

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarRepository#
	 * getAllPattCalendar(java.lang.String)
	 */
	@Override
	public List<DailyPattern> getAllPattCalendar(String companyId) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();

		CriteriaQuery<KdpstDailyPatternSet> query = builder.createQuery(KdpstDailyPatternSet.class);
		Root<KdpstDailyPatternSet> root = query.from(KdpstDailyPatternSet.class);

		List<Predicate> predicateList = new ArrayList<>();

		predicateList.add(builder.equal(root.get(KdpstDailyPatternSet_.kdpstDailyPatternSetPK)
				.get(KdpstDailyPatternSetPK_.cid), companyId));

		query.where(predicateList.toArray(new Predicate[] {}));

		// order by closure id asc
		query.orderBy(builder.asc(root.get(KdpstDailyPatternSet_.kdpstDailyPatternSetPK)
				.get(KdpstDailyPatternSetPK_.patternCd)));

		List<KdpstDailyPatternSet> result = em.createQuery(query).getResultList();

		if (result.isEmpty()) {
			return Collections.emptyList();
		}

		return result.stream().map(entity -> {
			return new DailyPattern(new JpaDailyPatternGetMemento(entity));
		}).collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarRepository#
	 * findByCompanyId(java.lang.String)
	 */
	@Override
	public Optional<DailyPattern> findByCode(String companyId, String patternCd) {

		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<KdpstDailyPatternSet> query = builder.createQuery(KdpstDailyPatternSet.class);
		Root<KdpstDailyPatternSet> root = query.from(KdpstDailyPatternSet.class);

		List<Predicate> predicateList = new ArrayList<>();

		predicateList.add(builder.equal(root.get(KdpstDailyPatternSet_.kdpstDailyPatternSetPK)
				.get(KdpstDailyPatternSetPK_.cid), companyId));
		predicateList.add(builder.equal(root.get(KdpstDailyPatternSet_.kdpstDailyPatternSetPK)
				.get(KdpstDailyPatternSetPK_.patternCd), patternCd));

		query.where(predicateList.toArray(new Predicate[] {}));

		List<KdpstDailyPatternSet> result = em.createQuery(query).getResultList();
		if (result.isEmpty()) {
			return Optional.empty();
		}

		return Optional.of(new DailyPattern(new JpaDailyPatternGetMemento(result.get(FIRST_DATA))));
	}

	/**
	 * To entity.
	 *
	 * @param setting
	 *            the setting
	 * @return the kdpst daily pattern set
	 */
	private KdpstDailyPatternSet toEntity(DailyPattern setting) {
		Optional<KdpstDailyPatternSet> optinal = this.queryProxy()
				.find(new KdpstDailyPatternSetPK(setting.getCompanyId().v(),
						setting.getPatternCode().v()), KdpstDailyPatternSet.class);
		KdpstDailyPatternSet entity = null;
		if (optinal.isPresent()) {
			entity = optinal.get();
		} else {
			entity = new KdpstDailyPatternSet();
		}
		JpaDailyPatternSetMemento memento = new JpaDailyPatternSetMemento(entity);
		setting.saveToMemento(memento);
		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarRepository#add(
	 * nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendar)
	 */
	@Override
	public void add(DailyPattern patternCalendar) {
		this.commandProxy().insert(this.toEntity(patternCalendar));
		this.getEntityManager().flush();

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarRepository#update
	 * (nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendar)
	 */
	@Override
	public void update(DailyPattern patternCalendar) {
		this.commandProxy().update(this.toEntity(patternCalendar));
		this.getEntityManager().flush();

	}

	@Override
	public void delete(String cid, String patternCd) {
		this.commandProxy().remove(KdpstDailyPatternSet.class,
				new KdpstDailyPatternSetPK(cid, patternCd));

	}

}
