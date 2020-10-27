/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.nursingleave;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;
import javax.ejb.TransactionAttribute;
import javax.ejb.TransactionAttributeType;
import javax.persistence.EntityManager;
import javax.persistence.criteria.CriteriaBuilder;
import javax.persistence.criteria.CriteriaQuery;
import javax.persistence.criteria.Predicate;
import javax.persistence.criteria.Root;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.nursingleave.NursingLeaveSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KshmtHdnursingLeave;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KshmtHdnursingLeavePK;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KshmtHdnursingLeavePK_;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.nursingleave.KshmtHdnursingLeave_;

/**
 * The Class JpaNursingVacationSettingRepository.
 */
@Stateless
public class JpaNursingLeaveSettingRepository extends JpaRepository implements NursingLeaveSettingRepository {

    /** The select worktype. */
    private static final String FIND_WORKTYPE = "SELECT c.kshmtWorkTypePK.workTypeCode FROM KshmtWorkType c "
            + "WHERE c.kshmtWorkTypePK.companyId = :companyId ";
    
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
	@TransactionAttribute(TransactionAttributeType.SUPPORTS)
    public List<NursingLeaveSetting> findByCompanyId(String companyId) {
        EntityManager em = this.getEntityManager();
        
        CriteriaBuilder builder = em.getCriteriaBuilder();
        CriteriaQuery<KshmtHdnursingLeave> query = builder.createQuery(KshmtHdnursingLeave.class);
        Root<KshmtHdnursingLeave> root = query.from(KshmtHdnursingLeave.class);
        
        List<Predicate> predicateList = new ArrayList<>();
        
        predicateList.add(builder.equal(root.get(KshmtHdnursingLeave_.kshmtHdnursingLeavePK)
                .get(KshmtHdnursingLeavePK_.cid), companyId));
        
        query.where(predicateList.toArray(new Predicate[]{}));
        
        List<KshmtHdnursingLeave> result = em.createQuery(query).getResultList();
        if (result.isEmpty()) {
            return new ArrayList<>();
        }
        List<NursingLeaveSetting> listSetting = new ArrayList<>();
        
        // NURSING
        KshmtHdnursingLeave nursingSetting = this.findNursingLeaveByNursingCategory(result,
                NursingCategory.Nursing.value);
        listSetting.add(new NursingLeaveSetting(
                new JpaNursingLeaveSettingGetMemento(nursingSetting)));
        
        // CHILD NURSING
        KshmtHdnursingLeave childNursingSetting = this.findNursingLeaveByNursingCategory(result,
                NursingCategory.ChildNursing.value);
        listSetting.add(new NursingLeaveSetting(
                new JpaNursingLeaveSettingGetMemento(childNursingSetting)));
        
        return listSetting;
    }
    
    @Override
    public List<String> findWorkTypeCodesByCompanyId(String companyId) {
        return this.queryProxy().query(FIND_WORKTYPE, String.class)
                .setParameter("companyId", companyId)
                .getList();
    }

    /**
     * To entity.
     *
     * @param setting the setting
     * @return the kmfmt nursing leave set
     */
    private KshmtHdnursingLeave toEntity(NursingLeaveSetting setting) {
        Optional<KshmtHdnursingLeave> optinal = this.queryProxy().find(new KshmtHdnursingLeavePK(
                setting.getCompanyId(), setting.getNursingCategory().value), KshmtHdnursingLeave.class);
        KshmtHdnursingLeave entity = null;
        if (optinal.isPresent()) {
            entity = optinal.get();
        } else {
            entity = new KshmtHdnursingLeave();
        }
        JpaNursingLeaveSettingSetMemento memento = new JpaNursingLeaveSettingSetMemento(entity);
        setting.saveToMemento(memento);
        return entity;
    }
    
    /**
     * Find nursing leave by nursing category.
     *
     * @param listSetting the list setting
     * @param nursingCtr the nursing ctr
     * @return the kmfmt nursing leave set
     */
    private KshmtHdnursingLeave findNursingLeaveByNursingCategory(List<KshmtHdnursingLeave> listSetting,
            Integer nursingCtr) {
        return listSetting.stream()
                .filter(entity -> entity.getKshmtHdnursingLeavePK().getNursingCtr() == nursingCtr)
                .findFirst()
                .get();
    }
}
