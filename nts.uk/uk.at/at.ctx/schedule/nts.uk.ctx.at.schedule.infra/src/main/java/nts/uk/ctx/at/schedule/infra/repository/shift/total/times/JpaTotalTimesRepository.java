package nts.uk.ctx.at.schedule.infra.repository.shift.total.times;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.shift.pattern.daily.DailyPattern;
import nts.uk.ctx.at.schedule.dom.shift.total.times.TotalTimes;
import nts.uk.ctx.at.schedule.dom.shift.total.times.TotalTimesRepository;
import nts.uk.ctx.at.schedule.infra.entity.dailypattern.KdpstDailyPatternSet;
import nts.uk.ctx.at.schedule.infra.entity.dailypattern.KdpstDailyPatternSetPK_;
import nts.uk.ctx.at.schedule.infra.entity.dailypattern.KdpstDailyPatternSet_;
import nts.uk.ctx.at.schedule.infra.entity.shift.total.times.KshstTotalTimes;
import nts.uk.ctx.at.schedule.infra.entity.shift.total.times.KshstTotalTimesPK_;
import nts.uk.ctx.at.schedule.infra.entity.shift.total.times.KshstTotalTimes_;
import nts.uk.ctx.at.schedule.infra.repository.dailypattern.JpaDailyPatternGetMemento;

@Stateless
public class JpaTotalTimesRepository extends JpaRepository implements TotalTimesRepository {

	@Override
	public List<TotalTimes> getAllTotalTimes(String companyId) {
		return null;
//		EntityManager em = this.getEntityManager();
//
//		CriteriaBuilder builder = em.getCriteriaBuilder();
//
//		CriteriaQuery<KshstTotalTimes> query = builder.createQuery(KshstTotalTimes.class);
//		Root<KshstTotalTimes> root = query.from(KshstTotalTimes.class);
//
//		List<Predicate> predicateList = new ArrayList<>();
//
//		predicateList.add(builder.equal(root.get(KshstTotalTimes_.kshstTotalTimesPK)
//				.get(KshstTotalTimesPK_.cid), companyId));
//
//		query.where(predicateList.toArray(new Predicate[] {}));
//
//		// order by closure id asc
//		query.orderBy(builder.asc(root.get(KshstTotalTimes_.kshstTotalTimesPK)
//				.get(KshstTotalTimesPK_.totalTimesNo)));
//
//		List<KshstTotalTimes> result = em.createQuery(query).getResultList();
//
//		if (result.isEmpty()) {
//			return Collections.emptyList();
//		}

//		return result.stream().map(entity -> {
//			return new TotalTimes(new JpaTotalTimesGetMemento(entity));
//		}).collect(Collectors.toList());
//		
//		return result.stream().map(entity -> {
//			return new DailyPattern(new JpaDailyPatternGetMemento(entity));
//		}).collect(Collectors.toList());
	}

	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
	
}
