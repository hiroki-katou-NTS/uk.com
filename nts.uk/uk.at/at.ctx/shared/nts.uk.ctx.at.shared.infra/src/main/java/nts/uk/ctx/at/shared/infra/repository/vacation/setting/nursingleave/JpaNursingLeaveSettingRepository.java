/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.nursingleave;

import java.util.ArrayList;
import java.util.List;

import javax.ejb.Stateless;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KnlmtNursingLeaveSet;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KnlmtNursingLeaveSetPK_;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KnlmtNursingLeaveSet_;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KnlmtNursingWorkType;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KnlmtNursingWorkTypePK_;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KnlmtNursingWorkType_;

/**
 * The Class JpaNursingVacationSettingRepository.
 */
@Stateless
public class JpaNursingLeaveSettingRepository extends JpaRepository implements NursingLeaveSettingRepository {

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingRepository#add(nts.uk.ctx.at.shared.dom.vacation.
     * setting.nursingleave.NursingVacationSetting,
     * nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSetting)
     */
    @Override
    public void add(NursingLeaveSetting nursingSetting, NursingLeaveSetting childNursingSetting) {
        this.commandProxy().insert(this.toEntity(nursingSetting));
        this.getEntityManager().flush();

        this.commandProxy().insert(this.toEntity(childNursingSetting));
        this.getEntityManager().flush();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingRepository#update(nts.uk.ctx.at.shared.dom.vacation
     * .setting.nursingleave.NursingVacationSetting,
     * nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSetting)
     */
    @Override
    public void update(NursingLeaveSetting nursingSetting, NursingLeaveSetting childNursingSetting) {

        this.commandProxy().update(this.toEntity(nursingSetting));
        this.getEntityManager().flush();

        this.commandProxy().update(this.toEntity(childNursingSetting));
        this.getEntityManager().flush();
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.
     * NursingVacationSettingRepository#findByCompanyId(java.lang.String)
     */
    @Override
    public List<NursingLeaveSetting> findByCompanyId(String companyId) {
        EntityManager em = this.getEntityManager();
        
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<KnlmtNursingLeaveSet> query = builder.createQuery(KnlmtNursingLeaveSet.class);
        Root<KnlmtNursingLeaveSet> root = query.from(KnlmtNursingLeaveSet.class);
        
        List<Predicate> predicateList = new ArrayList<>();
        
        predicateList.add(builder.equal(root.get(KnlmtNursingLeaveSet_.knlmtNursingLeaveSetPK)
                .get(KnlmtNursingLeaveSetPK_.cid), companyId));
        
        query.where(predicateList.toArray(new Predicate[]{}));
        
        List<KnlmtNursingLeaveSet> result = em.createQuery(query).getResultList();
        if (result.isEmpty()) {
            return null;
        }
        List<NursingLeaveSetting> listSetting = new ArrayList<>();
        
        // NURSING
        KnlmtNursingLeaveSet nursingSetting = this.findNursingLeaveByNursingCategory(result,
                NursingCategory.Nursing.value);
        List<KnlmtNursingWorkType> entityWorkTypeNursings = this.findWorkTypeByCompanyId(companyId,
                NursingCategory.Nursing.value);
        listSetting.add(new NursingLeaveSetting(
                new JpaNursingLeaveSettingGetMemento(nursingSetting, entityWorkTypeNursings)));
        
        // CHILD NURSING
        KnlmtNursingLeaveSet childNursingSetting = this.findNursingLeaveByNursingCategory(result,
                NursingCategory.ChildNursing.value);
        List<KnlmtNursingWorkType> entityWorkTypeChildNursings = this.findWorkTypeByCompanyId(companyId,
                NursingCategory.ChildNursing.value);
        listSetting.add(new NursingLeaveSetting(
                new JpaNursingLeaveSettingGetMemento(childNursingSetting, entityWorkTypeChildNursings)));
        
        return listSetting;
    }

    /**
     * To entity.
     *
     * @param setting the setting
     * @return the kmfmt nursing leave set
     */
    private KnlmtNursingLeaveSet toEntity(NursingLeaveSetting setting) {
        KnlmtNursingLeaveSet entity = new KnlmtNursingLeaveSet();
        JpaNursingLeaveSettingSetMemento memento = new JpaNursingLeaveSettingSetMemento(entity);
        setting.saveToMemento(memento);
        return entity;
    }
    
    /**
     * Find work type by company id.
     *
     * @param companyId the company id
     * @param nursingCtr the nursing ctr
     * @return the list
     */
    private List<KnlmtNursingWorkType> findWorkTypeByCompanyId(String companyId, Integer nursingCtr) {
        EntityManager em = this.getEntityManager();
        
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<KnlmtNursingWorkType> query = builder.createQuery(KnlmtNursingWorkType.class);
        Root<KnlmtNursingWorkType> root = query.from(KnlmtNursingWorkType.class);
        
        List<Predicate> predicateList = new ArrayList<>();
        
        predicateList.add(builder.equal(root.get(KnlmtNursingWorkType_.knlmtNursingWorkTypePK)
                .get(KnlmtNursingWorkTypePK_.cid), companyId));
        predicateList.add(builder.equal(root.get(KnlmtNursingWorkType_.knlmtNursingWorkTypePK)
                .get(KnlmtNursingWorkTypePK_.nursingCtr), nursingCtr));
        
        query.where(predicateList.toArray(new Predicate[]{}));
        
        query.orderBy(builder.asc(root.get(KnlmtNursingWorkType_.knlmtNursingWorkTypePK)
                .get(KnlmtNursingWorkTypePK_.orderNumber)));
        
        return em.createQuery(query).getResultList();
    }
    
    /**
     * Find nursing leave by nursing category.
     *
     * @param listSetting the list setting
     * @param nursingCtr the nursing ctr
     * @return the kmfmt nursing leave set
     */
    private KnlmtNursingLeaveSet findNursingLeaveByNursingCategory(List<KnlmtNursingLeaveSet> listSetting,
            Integer nursingCtr) {
        return listSetting.stream()
                .filter(entity -> entity.getKnlmtNursingLeaveSetPK().getNursingCtr() == nursingCtr)
                .findFirst()
                .get();
    }
}
