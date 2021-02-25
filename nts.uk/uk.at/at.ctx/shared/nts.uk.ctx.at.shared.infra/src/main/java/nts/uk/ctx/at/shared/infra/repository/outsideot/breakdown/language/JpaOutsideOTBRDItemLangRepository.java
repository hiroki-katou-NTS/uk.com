/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.outsideot.breakdown.language;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.BreakdownItemNo;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.language.OutsideOTBRDItemLang;
import nts.uk.ctx.at.shared.dom.scherec.monthlyattdcal.outsideot.breakdown.language.OutsideOTBRDItemLangRepository;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.language.KshstOutsideOtBrdLang;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.language.KshstOutsideOtBrdLangPK_;
import nts.uk.ctx.at.shared.infra.entity.outsideot.breakdown.language.KshstOutsideOtBrdLang_;

/**
 * The Class JpaOutsideOTBRDItemLangRepository.
 */
@Stateless
public class JpaOutsideOTBRDItemLangRepository extends JpaRepository
		implements OutsideOTBRDItemLangRepository {

	
	/** The Constant INDEX_FIRST. */
	public static final int INDEX_FIRST = 0;
	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.breakdown.language.
	 * OvertimeLangBRDItemRepository#findAll(java.lang.String, java.lang.String)
	 */
	@Override
	public List<OutsideOTBRDItemLang> findAll(String companyId, String languageId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSHST_OVER_TIME_LANG_BRD (KshstOverTimeLangBrd SQL)
		CriteriaQuery<KshstOutsideOtBrdLang> cq = criteriaBuilder
				.createQuery(KshstOutsideOtBrdLang.class);

		// root data
		Root<KshstOutsideOtBrdLang> root = cq.from(KshstOutsideOtBrdLang.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KshstOutsideOtBrdLang_.kshstOutsideOtBrdLangPK)
						.get(KshstOutsideOtBrdLangPK_.cid), companyId));
		// equal language id
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KshstOutsideOtBrdLang_.kshstOutsideOtBrdLangPK)
						.get(KshstOutsideOtBrdLangPK_.languageId), languageId));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// order by over time breakdown item no asc
		cq.orderBy(criteriaBuilder.asc(root.get(KshstOutsideOtBrdLang_.kshstOutsideOtBrdLangPK)
				.get(KshstOutsideOtBrdLangPK_.brdItemNo)));

		// create query
		TypedQuery<KshstOutsideOtBrdLang> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(entity -> this.toDomain(entity))
				.collect(Collectors.toList());
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.overtime.breakdown.language.
	 * OvertimeLangBRDItemRepository#saveAll(java.util.List)
	 */
	@Override
	public void saveAll(List<OutsideOTBRDItemLang> domains) {
		// check exist data
		if (CollectionUtil.isEmpty(domains)) {
			return;
		}
		// get company id do main
		String companyId = domains.get(INDEX_FIRST).getCompanyId().v();

		// get language id domain
		String languageId = domains.get(INDEX_FIRST).getLanguageId().v();

		// to map over time
		Map<BreakdownItemNo, OutsideOTBRDItemLang> mapOvertimeLangBRD = this
				.findAll(companyId, languageId).stream()
				.collect(Collectors.toMap((overtimeLangBRDItem) -> {
					return overtimeLangBRDItem.getBreakdownItemNo();
				}, Function.identity()));

		// entity add all
		List<KshstOutsideOtBrdLang> entityAddAll = new ArrayList<>();

		// entity update all
		List<KshstOutsideOtBrdLang> entityUpdateAll = new ArrayList<>();

		// for each data overtime language breakdown item
		domains.forEach(overtimeLang -> {
			if (mapOvertimeLangBRD.containsKey(overtimeLang.getBreakdownItemNo())) {
				entityUpdateAll.add(this.toEntity(overtimeLang));
			} else {
				entityAddAll.add(this.toEntity(overtimeLang));
			}
		});

		// insert all
		this.commandProxy().insertAll(entityAddAll);

		// update all
		this.commandProxy().updateAll(entityUpdateAll);
		
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the outside OTBRD item lang
	 */
	private OutsideOTBRDItemLang toDomain(KshstOutsideOtBrdLang entity) {
		return new OutsideOTBRDItemLang(new JpaOutsideOTBRDItemLangGetMemento(entity));
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kshst over time lang brd
	 */
	private KshstOutsideOtBrdLang toEntity(OutsideOTBRDItemLang domain){
		KshstOutsideOtBrdLang entity = new KshstOutsideOtBrdLang();
		domain.saveToMemento(new JpaOutsideOTBRDItemLangSetMemento(entity));
		return entity;
	}

}
