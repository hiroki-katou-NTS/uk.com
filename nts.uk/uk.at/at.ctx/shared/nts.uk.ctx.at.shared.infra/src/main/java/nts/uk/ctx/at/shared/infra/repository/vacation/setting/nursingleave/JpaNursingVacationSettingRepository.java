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
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingVacationSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingVacationSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KmfmtNursingLeaveSet;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KmfmtNursingLeaveSetPK_;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KmfmtNursingLeaveSet_;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KmfmtNursingWorkType;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KmfmtNursingWorkTypePK_;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KmfmtNursingWorkType_;

/**
 * The Class JpaNursingVacationSettingRepository.
 */
@Stateless
public class JpaNursingVacationSettingRepository extends JpaRepository implements NursingVacationSettingRepository {

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
    public void add(NursingVacationSetting nursingSetting, NursingVacationSetting childNursingSetting) {
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
    public void update(NursingVacationSetting nursingSetting, NursingVacationSetting childNursingSetting) {

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
    public List<NursingVacationSetting> findByCompanyId(String companyId) {
        EntityManager em = this.getEntityManager();
        
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<KmfmtNursingLeaveSet> query = builder.createQuery(KmfmtNursingLeaveSet.class);
        Root<KmfmtNursingLeaveSet> root = query.from(KmfmtNursingLeaveSet.class);
        
        List<Predicate> predicateList = new ArrayList<>();
        
        predicateList.add(builder.equal(root.get(KmfmtNursingLeaveSet_.kmfmtNursingLeaveSetPK)
                .get(KmfmtNursingLeaveSetPK_.cid), companyId));
        
        query.where(predicateList.toArray(new Predicate[]{}));
        
        List<KmfmtNursingLeaveSet> result = em.createQuery(query).getResultList();
        if (result.isEmpty()) {
            return null;
        }
        List<NursingVacationSetting> listSetting = new ArrayList<>();
        
        // NURSING
        KmfmtNursingLeaveSet nursingSetting = this.findNursingLeaveByNursingCategory(result,
                NursingCategory.Nursing.value);
        List<KmfmtNursingWorkType> entityWorkTypeNursings = this.findWorkTypeByCompanyId(companyId,
                NursingCategory.Nursing.value);
        listSetting.add(new NursingVacationSetting(
                new JpaNursingVacationSettingGetMemento(nursingSetting, entityWorkTypeNursings)));
        
        // CHILD NURSING
        KmfmtNursingLeaveSet childNursingSetting = this.findNursingLeaveByNursingCategory(result,
                NursingCategory.ChildNursing.value);
        List<KmfmtNursingWorkType> entityWorkTypeChildNursings = this.findWorkTypeByCompanyId(companyId,
                NursingCategory.ChildNursing.value);
        listSetting.add(new NursingVacationSetting(
                new JpaNursingVacationSettingGetMemento(childNursingSetting, entityWorkTypeChildNursings)));
        
        return listSetting;
    }

    /**
     * To entity.
     *
     * @param setting the setting
     * @return the kmfmt nursing leave set
     */
    private KmfmtNursingLeaveSet toEntity(NursingVacationSetting setting) {
        KmfmtNursingLeaveSet entity = new KmfmtNursingLeaveSet();
        JpaNursingVacationSettingSetMemento memento = new JpaNursingVacationSettingSetMemento(entity);
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
    private List<KmfmtNursingWorkType> findWorkTypeByCompanyId(String companyId, Integer nursingCtr) {
        EntityManager em = this.getEntityManager();
        
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<KmfmtNursingWorkType> query = builder.createQuery(KmfmtNursingWorkType.class);
        Root<KmfmtNursingWorkType> root = query.from(KmfmtNursingWorkType.class);
        
        List<Predicate> predicateList = new ArrayList<>();
        
        predicateList.add(builder.equal(root.get(KmfmtNursingWorkType_.kmfmtWorkTypePK)
                .get(KmfmtNursingWorkTypePK_.cid), companyId));
        predicateList.add(builder.equal(root.get(KmfmtNursingWorkType_.kmfmtWorkTypePK)
                .get(KmfmtNursingWorkTypePK_.nursingCtr), nursingCtr));
        
        query.where(predicateList.toArray(new Predicate[]{}));
        
        query.orderBy(builder.asc(root.get(KmfmtNursingWorkType_.kmfmtWorkTypePK).get(KmfmtNursingWorkTypePK_.orderNumber)));
        
        return em.createQuery(query).getResultList();
    }
    
    /**
     * Find nursing leave by nursing category.
     *
     * @param listSetting the list setting
     * @param nursingCtr the nursing ctr
     * @return the kmfmt nursing leave set
     */
    private KmfmtNursingLeaveSet findNursingLeaveByNursingCategory(List<KmfmtNursingLeaveSet> listSetting, Integer nursingCtr) {
        return listSetting.stream()
                .filter(entity -> entity.getKmfmtNursingLeaveSetPK().getNursingCtr() == nursingCtr)
                .findFirst()
                .get();
    }
}
