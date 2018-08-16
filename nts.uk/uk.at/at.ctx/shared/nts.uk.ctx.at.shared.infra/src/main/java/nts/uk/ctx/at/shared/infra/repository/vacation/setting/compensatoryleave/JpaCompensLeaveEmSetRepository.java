/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensatoryleave;

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
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveEmSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveEmSetting;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KclmtCompensLeaveEmp;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KclmtCompensLeaveEmpPK;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KclmtCompensLeaveEmpPK_;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KclmtCompensLeaveEmp_;

/**
 * The Class JpaCompensLeaveEmSetRepository.
 */
@Stateless
public class JpaCompensLeaveEmSetRepository extends JpaRepository implements CompensLeaveEmSetRepository {
    
    /** The element first. */
    private static final int ELEMENT_FIRST = 0;
    
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensLeaveEmSetRepository#insert(nts.uk.ctx.at.shared.dom.vacation.
     * setting.compensatoryleave.CompensatoryLeaveEmSetting)
     */
    @Override
    public void insert(CompensatoryLeaveEmSetting setting) {
        this.commandProxy().insert(this.toEntity(setting));
        this.getEntityManager().flush();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensLeaveEmSetRepository#update(nts.uk.ctx.at.shared.dom.vacation.
     * setting.compensatoryleave.CompensatoryLeaveEmSetting)
     */
    @Override
    public void update(CompensatoryLeaveEmSetting setting) {
        this.commandProxy().update(this.toEntity(setting));
        this.getEntityManager().flush();
    }
    
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensLeaveEmSetRepository#update(nts.uk.ctx.at.shared.dom.vacation.
     * setting.compensatoryleave.CompensatoryLeaveEmSetting)
     */
    @Override
	public void delete(String companyId, String employmentCode) {
    	KclmtCompensLeaveEmpPK key = new KclmtCompensLeaveEmpPK(companyId, employmentCode);
    	this.commandProxy().remove(KclmtCompensLeaveEmp.class, key);
	}

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensLeaveEmSetRepository#find(java.lang.String, java.lang.String)
     */
    @Override
    public CompensatoryLeaveEmSetting find(String companyId, String employmentCode) {
        EntityManager em = this.getEntityManager();
        
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<KclmtCompensLeaveEmp> query = builder.createQuery(KclmtCompensLeaveEmp.class);
        Root<KclmtCompensLeaveEmp> root = query.from(KclmtCompensLeaveEmp.class);
        
        List<Predicate> predicateList = new ArrayList<>();
        
        predicateList.add(builder.equal(root.get(KclmtCompensLeaveEmp_.kclmtCompensLeaveEmpPK).get(
                KclmtCompensLeaveEmpPK_.cid), companyId));
        predicateList.add(builder.equal(root.get(KclmtCompensLeaveEmp_.kclmtCompensLeaveEmpPK).get(
                KclmtCompensLeaveEmpPK_.empcd), employmentCode));
        
        query.where(predicateList.toArray(new Predicate[]{}));
        
        List<KclmtCompensLeaveEmp> result = em.createQuery(query).getResultList();
        if (result.isEmpty()) {
            return null;
        }
        KclmtCompensLeaveEmp entity = result.get(ELEMENT_FIRST);
        return new CompensatoryLeaveEmSetting(new JpaCompensLeaveEmSettingGetMemento(entity));
    }

	/*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensLeaveEmSetRepository#findAll(java.lang.String)
     */
    @Override
    public List<CompensatoryLeaveEmSetting> findAll(String companyId) {
        EntityManager em = this.getEntityManager();
        
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<KclmtCompensLeaveEmp> query = builder.createQuery(KclmtCompensLeaveEmp.class);
        Root<KclmtCompensLeaveEmp> root = query.from(KclmtCompensLeaveEmp.class);
        
        List<Predicate> predicateList = new ArrayList<>();
        
        predicateList.add(builder.equal(root.get(KclmtCompensLeaveEmp_.kclmtCompensLeaveEmpPK).get(
                KclmtCompensLeaveEmpPK_.cid), companyId));
        
        query.where(predicateList.toArray(new Predicate[]{}));
        
        List<KclmtCompensLeaveEmp> result = em.createQuery(query).getResultList();
        
        return em.createQuery(query).getResultList().stream()
                .map(entity -> new CompensatoryLeaveEmSetting(new JpaCompensLeaveEmSettingGetMemento(entity)))
                .collect(Collectors.toList());
    }
    
    /**
     * To entity.
     *
     * @param setting the setting
     * @return the kclmt compens leave emp
     */
    private KclmtCompensLeaveEmp toEntity(CompensatoryLeaveEmSetting setting) {
        Optional<KclmtCompensLeaveEmp> optinal = this.queryProxy().find(new KclmtCompensLeaveEmpPK(
                setting.getCompanyId(), setting.getEmploymentCode().v()), KclmtCompensLeaveEmp.class);
        KclmtCompensLeaveEmp entity = null;
        if (optinal.isPresent()) {
            entity = optinal.get();
        } else {
            entity = new KclmtCompensLeaveEmp();
        }
        JpaCompensLeaveEmSettingSetMemento memento = new JpaCompensLeaveEmSettingSetMemento(entity);
        setting.saveToMemento(memento);
        return entity;
    }

}
