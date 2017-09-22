/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.record.infra.repository.optitem.calculation;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.TypedQuery;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.record.dom.optitem.calculation.CalculationAtr;
import nts.uk.ctx.at.record.dom.optitem.calculation.Formula;
import nts.uk.ctx.at.record.dom.optitem.calculation.FormulaRepository;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtCalcItemSelection;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtCalcItemSelectionPK_;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtCalcItemSelection_;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtFormulaSetting;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtFormulaSettingPK;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtOptItemFormula;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtOptItemFormulaPK_;
import nts.uk.ctx.at.record.infra.entity.optitem.calculation.KrcmtOptItemFormula_;

/**
 * The Class JpaFormulaRepository.
 */
@Stateless
public class JpaFormulaRepository extends JpaRepository implements FormulaRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.calculation.FormulaRepository#create(
	 * java.util.List)
	 */
	@Override
	public void create(List<Formula> listFormula) {
		System.out.println(listFormula);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see
	 * nts.uk.ctx.at.record.dom.optitem.calculation.FormulaRepository#remove(
	 * java.lang.String, java.lang.String)
	 */
	@Override
	public void remove(String comId, String optItemNo) {

	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.record.dom.optitem.calculation.FormulaRepository#
	 * findByOptItemNo(java.lang.String, java.lang.String)
	 */
	@Override
	public List<Formula> findByOptItemNo(String companyId, String optItemNo) {
		
		return this.findAllFormulaEntity(companyId, optItemNo).stream().map(entity->{
			Optional<KrcmtFormulaSetting> setting = this.findSetting(
					entity.getKrcmtOptItemFormulaPK().getCid(), 
					entity.getKrcmtOptItemFormulaPK().getOptionalItemNo(), 
					entity.getKrcmtOptItemFormulaPK().getFormulaId());
			KrcmtFormulaSetting entitySetting = new KrcmtFormulaSetting();
			
			if(setting.isPresent()){
				entitySetting = setting.get();
			}
			List<KrcmtCalcItemSelection> selections = new ArrayList<>();
			if(entity.getCalcAtr() == CalculationAtr.ITEM_SELECTION.value){
				selections = this.getCalcItemSelectionByOptItemNo(entity.getKrcmtOptItemFormulaPK().getCid(), 
						entity.getKrcmtOptItemFormulaPK().getOptionalItemNo(), 
						entity.getKrcmtOptItemFormulaPK().getFormulaId());
			}
			return this.toDomain(entity, entitySetting, selections);
		}).collect(Collectors.toList());
	}
	
	/**
	 * Find all formula entity.
	 *
	 * @param companyId the company id
	 * @param optItemNo the opt item no
	 * @return the list
	 */
	private List<KrcmtOptItemFormula> findAllFormulaEntity(String companyId, String optItemNo){

		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KRCMT_OPT_ITEM_FORMULA (KrcmtOptItemFormula SQL)
		CriteriaQuery<KrcmtOptItemFormula> cq = criteriaBuilder
				.createQuery(KrcmtOptItemFormula.class);

		// root data
		Root<KrcmtOptItemFormula> root = cq.from(KrcmtOptItemFormula.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(
				criteriaBuilder.equal(root.get(KrcmtOptItemFormula_.krcmtOptItemFormulaPK)
						.get(KrcmtOptItemFormulaPK_.cid), companyId));

		// equal optional item no
		lstpredicateWhere.add(
				criteriaBuilder.equal(root.get(KrcmtOptItemFormula_.krcmtOptItemFormulaPK)
						.get(KrcmtOptItemFormulaPK_.optionalItemNo), optItemNo));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KrcmtOptItemFormula> query = em.createQuery(cq);

		// exclude select
		return query.getResultList();
	}
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @param setting the setting
	 * @param selections the selections
	 * @return the formula
	 */
	private Formula toDomain(KrcmtOptItemFormula entity, KrcmtFormulaSetting setting,
			List<KrcmtCalcItemSelection> selections){
		return new Formula(new JpaFormulaGetMemento(entity,setting,selections));
	}
	
	/**
	 * Find setting.
	 *
	 * @param companyId the company id
	 * @param optItemNo the opt item no
	 * @param formulaId the formula id
	 * @return the optional
	 */
	private Optional<KrcmtFormulaSetting> findSetting(String companyId, String optItemNo,
			String formulaId) {
		return this.queryProxy().find(new KrcmtFormulaSettingPK(companyId, optItemNo, formulaId),
				KrcmtFormulaSetting.class);
	}
	
	/**
	 * Gets the calc item selection by opt item no.
	 *
	 * @param companyId the company id
	 * @param optItemNo the opt item no
	 * @param formulaId the formula id
	 * @return the calc item selection by opt item no
	 */
	private List<KrcmtCalcItemSelection> getCalcItemSelectionByOptItemNo(String companyId,
			String optItemNo, String formulaId) {

		// get entity manager
		EntityManager em = this.getEntityManager();
		CriteriaBuilder criteriaBuilder = em.getCriteriaBuilder();

		// call KRCMT_CALC_ITEM_SELECTION (KrcmtCalcItemSelection SQL)
		CriteriaQuery<KrcmtCalcItemSelection> cq = criteriaBuilder
				.createQuery(KrcmtCalcItemSelection.class);

		// root data
		Root<KrcmtCalcItemSelection> root = cq.from(KrcmtCalcItemSelection.class);

		// select root
		cq.select(root);

		// add where
		List<Predicate> lstpredicateWhere = new ArrayList<>();

		// equal company id
		lstpredicateWhere.add(
				criteriaBuilder.equal(root.get(KrcmtCalcItemSelection_.krcmtCalcItemSelectionPK)
						.get(KrcmtCalcItemSelectionPK_.cid), companyId));

		// equal optional item no
		lstpredicateWhere.add(
				criteriaBuilder.equal(root.get(KrcmtCalcItemSelection_.krcmtCalcItemSelectionPK)
						.get(KrcmtCalcItemSelectionPK_.optionalItemNo), optItemNo));
		
		// equal formula id
		lstpredicateWhere.add(
				criteriaBuilder.equal(root.get(KrcmtCalcItemSelection_.krcmtCalcItemSelectionPK)
						.get(KrcmtCalcItemSelectionPK_.formulaId), formulaId));

		// set where to SQL
		cq.where(lstpredicateWhere.toArray(new Predicate[] {}));

		// create query
		TypedQuery<KrcmtCalcItemSelection> query = em.createQuery(cq);

		// exclude select
		return query.getResultList();
	}

}
