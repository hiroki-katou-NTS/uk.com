/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensatoryleave;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.gul.collection.CollectionUtil;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KclmtCompensLeaveCom;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KclmtCompensLeaveCom_;

/**
 * The Class JpaCompensLeaveComSetRepository.
 */
@Stateless
public class JpaCompensLeaveComSetRepository extends JpaRepository implements CompensLeaveComSetRepository {
    
    /** The element first. */
    private static final int ELEMENT_FIRST = 0;
    
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensLeaveComSetRepository#insert(nts.uk.ctx.at.shared.dom.vacation.
     * setting.compensatoryleave.CompensatoryLeaveComSetting)
     */
    @Override
    public void insert(CompensatoryLeaveComSetting setting) {
        this.commandProxy().insert(this.toEntity(setting));
        this.getEntityManager().flush();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensLeaveComSetRepository#update(nts.uk.ctx.at.shared.dom.vacation.
     * setting.compensatoryleave.CompensatoryLeaveComSetting)
     */
    @Override
    public void update(CompensatoryLeaveComSetting setting) {
        this.commandProxy().update(this.toEntity(setting));
        this.getEntityManager().flush();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensLeaveComSetRepository#find(java.lang.String)
     */
    @Override
    public CompensatoryLeaveComSetting find(String companyId) {
        EntityManager em = this.getEntityManager();
        
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<KclmtCompensLeaveCom> query = builder.createQuery(KclmtCompensLeaveCom.class);
        Root<KclmtCompensLeaveCom> root = query.from(KclmtCompensLeaveCom.class);
        
        List<Predicate> predicateList = new ArrayList<>();
        
        predicateList.add(builder.equal(root.get(KclmtCompensLeaveCom_.cid), companyId));
        
        query.where(predicateList.toArray(new Predicate[]{}));
        List<KclmtCompensLeaveCom> result = em.createQuery(query).getResultList();
        if (CollectionUtil.isEmpty(result)) {
            return null;
        }
        KclmtCompensLeaveCom entity = result.get(ELEMENT_FIRST);
        return new CompensatoryLeaveComSetting(new JpaCompensLeaveComGetMemento(entity));
    }

    /**
     * To entity.
     *
     * @param setting the setting
     * @return the kclmt compens leave com
     */
    private KclmtCompensLeaveCom toEntity(CompensatoryLeaveComSetting setting) {
        Optional<KclmtCompensLeaveCom> optinal = this.queryProxy().find(setting.getCompanyId(),
                KclmtCompensLeaveCom.class);
        KclmtCompensLeaveCom entity = null;
        if (optinal.isPresent()) {
            entity = optinal.get();
        } else {
            entity = new KclmtCompensLeaveCom();
        }
        JpaCompensLeaveComSetMemento memento = new JpaCompensLeaveComSetMemento(entity);
        setting.saveToMemento(memento);
        return entity;
    }

	@Override
	public void copyMasterData(String sourceCid, String targetCid, boolean isReplace) {
		// TODO Auto-generated method stub
		
	}
}
