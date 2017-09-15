/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.outsideot.overtime.language;

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
import nts.uk.ctx.at.shared.dom.outsideot.overtime.OvertimeNo;
import nts.uk.ctx.at.shared.dom.outsideot.overtime.language.OvertimeNameLang;
import nts.uk.ctx.at.shared.dom.outsideot.overtime.language.OvertimeNameLangRepository;
import nts.uk.ctx.at.shared.infra.entity.outside.overtime.language.KshstOverTimeLangName;
import nts.uk.ctx.at.shared.infra.entity.outside.overtime.language.KshstOverTimeLangNamePK_;
import nts.uk.ctx.at.shared.infra.entity.outside.overtime.language.KshstOverTimeLangName_;

/**
 * The Class JpaOvertimeLangNameRepository.
 */
@Stateless
public class JpaOvertimeLangNameRepository extends JpaRepository
		implements OvertimeNameLangRepository {
	
	/** The Constant INDEX_FIRST. */
	public static final int INDEX_FIRST = 0;

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.language.OvertimeLangNameRepository#
	 * findById(java.lang.String, int, java.lang.String)
	 */
	@Override
	public List<OvertimeNameLang> findAll(String companyId, String languageId) {
		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KSHST_OVER_TIME_LANG_NAME (KshstOverTimeLangName SQL)
		CriteriaQuery<KshstOverTimeLangName> cq = criteriaBuilder
				.createQuery(KshstOverTimeLangName.class);

		// root data
		Root<KshstOverTimeLangName> root = cq.from(KshstOverTimeLangName.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KshstOverTimeLangName_.kshstOverTimeLangNamePK)
						.get(KshstOverTimeLangNamePK_.cid), companyId));
		// equal language id
		lstpredicateWhere
				.add(criteriaBuilder.equal(root.get(KshstOverTimeLangName_.kshstOverTimeLangNamePK)
						.get(KshstOverTimeLangNamePK_.languageId), languageId));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// order by over time no asc
		cq.orderBy(criteriaBuilder.asc(root.get(KshstOverTimeLangName_.kshstOverTimeLangNamePK)
				.get(KshstOverTimeLangNamePK_.overTimeNo)));

		// create query
		TypedQuery<KshstOverTimeLangName> query = em.createQuery(cq);

		// exclude select
		return query.getResultList().stream().map(entity -> this.toDomain(entity))
				.collect(Collectors.toList());
	}
	

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.shared.dom.overtime.language.OvertimeLangNameRepository#
	 * saveAll(java.util.List)
	 */
	@Override
	public void saveAll(List<OvertimeNameLang> domains) {

		// check exist data
		if(CollectionUtil.isEmpty(domains)){
			return;
		}
		// get company id do main
		String companyId = domains.get(INDEX_FIRST).getCompanyId().v();
		
		// get language id domain
		String languageId = domains.get(INDEX_FIRST).getLanguageId().v();
		
		// to map over time
		Map<OvertimeNo, OvertimeNameLang> mapOvertimeLange = this.findAll(companyId, languageId)
				.stream().collect(Collectors.toMap((overtimeLang) -> {
					return overtimeLang.getOvertimeNo();
				}, Function.identity()));
		
		// entity add all
		List<KshstOverTimeLangName> entityAddAll = new ArrayList<>();
		
		// entity update all
		List<KshstOverTimeLangName> entityUpdateAll = new ArrayList<>();
		
		
		// for each data overtime language name
		domains.forEach(overtimeLang -> {
			if (mapOvertimeLange.containsKey(overtimeLang.getOvertimeNo())) {
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
	 * @return the overtime lang name
	 */
	private OvertimeNameLang toDomain(KshstOverTimeLangName entity) {
		return new OvertimeNameLang(new JpaOvertimeNameLangGetMemento(entity));
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kshst over time lang name
	 */
	private KshstOverTimeLangName toEntity(OvertimeNameLang domain){
		KshstOverTimeLangName entity = new KshstOverTimeLangName();
		domain.saveToMemento(new JpaOvertimeLangNameSetMemento(entity));
		return entity;
	}

}
