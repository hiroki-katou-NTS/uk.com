/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.schedule.infra.repository.budget.external.actualresult.error;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.error.ExternalBudgetError;
import nts.uk.ctx.at.schedule.dom.budget.external.acceptance.error.ExternalBudgetErrorRepository;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.error.KscdtExtBudgetError;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.error.KscdtExtBudgetErrorPK_;
import nts.uk.ctx.at.schedule.infra.entity.budget.external.actualresult.error.KscdtExtBudgetError_;

/**
 * The Class JpaExternalBudgetErrorRepository.
 */
@Stateless
public class JpaExternalBudgetErrorRepository extends JpaRepository implements ExternalBudgetErrorRepository {

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExternalBudgetErrorRepository#add(nts.uk.ctx.at.schedule.dom.budget.
     * external.actualresult.ExternalBudgetError)
     */
    @Override
    public void add(ExternalBudgetError domain) {
        this.commandProxy().insert(this.toEntity(domain));
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.schedule.dom.budget.external.actualresult.
     * ExternalBudgetErrorRepository#findByExecutionId(java.lang.String)
     */
    @Override
    public List<ExternalBudgetError> findByExecutionId(String executionId) {
        EntityManager em = this.getEntityManager();

        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<KscdtExtBudgetError> query = builder.createQuery(KscdtExtBudgetError.class);
        Root<KscdtExtBudgetError> root = query.from(KscdtExtBudgetError.class);

        List<Predicate> predicateList = new ArrayList<>();

        predicateList.add(builder.equal(
                root.get(KscdtExtBudgetError_.kscdtExtBudgetErrorPK).get(KscdtExtBudgetErrorPK_.exeId), executionId));

        query.where(predicateList.toArray(new Predicate[] {}));
        
        query.orderBy(Arrays.asList(
            builder.asc(root.get(KscdtExtBudgetError_.kscdtExtBudgetErrorPK).get(KscdtExtBudgetErrorPK_.lineNo)),
            builder.asc(root.get(KscdtExtBudgetError_.kscdtExtBudgetErrorPK).get(KscdtExtBudgetErrorPK_.columnNo))
        ));

        return em.createQuery(query).getResultList().stream().map(
                entity -> new ExternalBudgetError(new JpaExternalBudgetErrorGetMemento((KscdtExtBudgetError) entity)))
                .collect(Collectors.toList());
    }

    /**
     * To entity.
     *
     * @param domain
     *            the domain
     * @return the kbedt ext budget error
     */
    private KscdtExtBudgetError toEntity(ExternalBudgetError domain) {
        KscdtExtBudgetError entity = new KscdtExtBudgetError();
        JpaExternalBudgetErrorSetMemento memento = new JpaExternalBudgetErrorSetMemento(entity);
        domain.saveToMemento(memento);
        return entity;
    }
}
