package nts.uk.ctx.at.shared.infra.repository.dailypattern;

import java.util.ArrayList;
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
import nts.uk.ctx.at.shared.dom.dailypattern.DailyPattern;
import nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternRepository;
import nts.uk.ctx.at.shared.infra.entity.dailypattern.KdpstDailyPatternSet;
import nts.uk.ctx.at.shared.infra.entity.dailypattern.KdpstDailyPatternSetPK;
import nts.uk.ctx.at.shared.infra.entity.dailypattern.KdpstDailyPatternSetPK_;
import nts.uk.ctx.at.shared.infra.entity.dailypattern.KdpstDailyPatternSet_;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KmfmtRetentionEmpCtr;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KmfmtRetentionEmpCtrPK;

// TODO: Auto-generated Javadoc
/**
 * The Class JpaDailyPatternRepository.
 */
@Stateless
public class JpaDailyPatternRepository extends JpaRepository implements DailyPatternRepository {

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
			return null;
		}

		return result.stream().map(entity -> {
			return new DailyPattern(new JpaDailyPatternGetMemento(entity));
		}).collect(Collectors.toList());
		/*
		 * }
		 * 
		 * ) List<PatternCalendar> listSetting = new ArrayList<>();
		 * 
		 * PatternCalendar patternCalendar = new PatternCalendar();
		 * 
		 * KcsmtContCalendarSet calenderSetting =
		 * this.findCalendarSetByContCalendarVal(result,"1");
		 * 
		 * listSetting.add(new PatternCalendar(new
		 * JpaPatternCalendarGetMemento(calenderSetting)) );
		 * 
		 * return listSetting;
		 */
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.patterncalendar.PatternCalendarRepository#
	 * findByCompanyId(java.lang.String)
	 */
	@Override
	public List<DailyPattern> findByCompanyId(String companyId, String patternCd) {
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
			return null;
		}
		List<DailyPattern> listSetting = new ArrayList<>();

		// PATTERN
		KdpstDailyPatternSet nursingSetting = this.findCalendarSetByContCalendarVal(result,
				companyId);
		listSetting.add(new DailyPattern(new JpaDailyPatternGetMemento(nursingSetting)));

		return listSetting;
	}

	/**
	 * Find calendar set by cont calendar val.
	 *
	 * @param listSetting
	 *            the list setting
	 * @param companyId
	 *            the company id
	 * @return the kdpst daily pattern set
	 */
	private KdpstDailyPatternSet findCalendarSetByContCalendarVal(
			List<KdpstDailyPatternSet> listSetting, String companyId) {
		KdpstDailyPatternSet kcsmtContCalendarSet = new KdpstDailyPatternSet();
		kcsmtContCalendarSet = listSetting.stream()
				.filter(entity -> entity.getKdpstDailyPatternSetPK().getCid().equals(companyId))
				.findFirst().get();
		return kcsmtContCalendarSet;
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
	public void deleted(String cid, String patternCd) {
		this.commandProxy().remove(KdpstDailyPatternSet.class,
				new KdpstDailyPatternSetPK(cid, patternCd));

	}

}
