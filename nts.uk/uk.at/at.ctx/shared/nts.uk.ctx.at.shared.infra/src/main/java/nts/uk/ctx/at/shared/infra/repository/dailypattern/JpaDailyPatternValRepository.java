/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.dailypattern;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Consumer;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternVal;
import nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternValRepository;
import nts.uk.ctx.at.shared.infra.entity.dailypattern.KdpstDailyPatternVal;
import nts.uk.ctx.at.shared.infra.entity.dailypattern.KdpstDailyPatternValPK_;
import nts.uk.ctx.at.shared.infra.entity.dailypattern.KdpstDailyPatternVal_;

/**
 * The Class JpaDailyPatternValRepository.
 */
@Stateless
public class JpaDailyPatternValRepository extends JpaRepository implements DailyPatternValRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.dailypattern.DailyPatternValRepository#
	 * findByPatternCd(java.lang.String)
	 */
	@Override
	public List<DailyPatternVal> findByPatternCd(String cid, String patternCd) {
		EntityManager em = this.getEntityManager();

		CriteriaBuilder builder = em.getCriteriaBuilder();
		CriteriaQuery<KdpstDailyPatternVal> query = builder.createQuery(KdpstDailyPatternVal.class);
		Root<KdpstDailyPatternVal> root = query.from(KdpstDailyPatternVal.class);

		List<Predicate> predicateList = new ArrayList<>();

		predicateList.add(builder
				.equal(root.get(KdpstDailyPatternVal_.kdpstDailyPatternValPK).get(KdpstDailyPatternValPK_.cid), cid));
		predicateList.add(builder.equal(
				root.get(KdpstDailyPatternVal_.kdpstDailyPatternValPK).get(KdpstDailyPatternValPK_.patternCd),
				patternCd));

		query.where(predicateList.toArray(new Predicate[] {}));

		// order by closure id asc
		query.orderBy(builder
				.asc(root.get(KdpstDailyPatternVal_.kdpstDailyPatternValPK).get(KdpstDailyPatternValPK_.dispOrder)));
		
		List<KdpstDailyPatternVal> result = em.createQuery(query).getResultList();
		if (result.isEmpty()) {
			return null;
		}
		List<DailyPatternVal> listSetting = new ArrayList<>();
		
		result.forEach(new Consumer<KdpstDailyPatternVal>() {
			@Override
			public void accept(KdpstDailyPatternVal t) {
				listSetting.add(new DailyPatternVal(new JpaDailyPatternValGetMemento(t)));
			}
		});

		// PATTERN VAL
//		KdpstDailyPatternVal kdpstDailyPatternVal = this.findPatternValByPatternVal(result, patternCd);
//		listSetting.add(new DailyPatternVal(new JpaDailyPatternValGetMemento(kdpstDailyPatternVal)));

		return listSetting;
	}

	/**
	 * Find pattern val by pattern val.
	 *
	 * @param listSetting
	 *            the list setting
	 * @param patternCd
	 *            the pattern cd
	 * @return the kdpst daily pattern val
	 */
	private KdpstDailyPatternVal findPatternValByPatternVal(List<KdpstDailyPatternVal> listSetting, String patternCd) {
		KdpstDailyPatternVal kdpstDailyPatternVal = new KdpstDailyPatternVal();
		kdpstDailyPatternVal = listSetting.stream()
				.filter(entity -> entity.getKdpstDailyPatternValPK().getPatternCd().equals(patternCd)).findFirst()
				.get();
		return kdpstDailyPatternVal;
	}

}
