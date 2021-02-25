/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.compensatoryleave;

import java.sql.PreparedStatement;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.SneakyThrows;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.jdbc.NtsResultSet;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensLeaveComSetRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.CompensatoryLeaveComSetting;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KclmtAcquisitionCom;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KclmtCompensLeaveCom;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KctmtDigestTimeCom;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KocmtOccurrenceSet;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.compensatoryleave.KocmtOccurrenceSetPK;

/**
 * The Class JpaCompensLeaveComSetRepository.
 */
@Stateless
public class JpaCompensLeaveComSetRepository extends JpaRepository implements CompensLeaveComSetRepository {
	private static final String GET_KSHMT_HDCOM_CMP = "SELECT c FROM KclmtCompensLeaveCom c  WHERE c.cid = :cid " ;
    
    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensLeaveComSetRepository#insert(nts.uk.ctx.at.shared.dom.vacation.
     * setting.compensatoryleave.CompensatoryLeaveComSetting)
     */
    @Override
    public void insert(CompensatoryLeaveComSetting setting) {
        this.commandProxy().insert(KclmtCompensLeaveCom.toEntity(setting));
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
    	Optional<KclmtCompensLeaveCom> oldEntity = this.queryProxy().find(setting.getCompanyId(),
                KclmtCompensLeaveCom.class);
    	if(oldEntity.isPresent()) {
    		KclmtCompensLeaveCom newEntity = KclmtCompensLeaveCom.toEntity(setting);
    		oldEntity.get().manageAtr = newEntity.manageAtr;
    		oldEntity.get().linkMngAtr = newEntity.linkMngAtr;
    		oldEntity.get().expirationUseSet = newEntity.expirationUseSet;
    		oldEntity.get().prepaidGetAllow = newEntity.prepaidGetAllow;
    		oldEntity.get().expDateMngMethod = newEntity.expDateMngMethod;
    		oldEntity.get().expCheckMonthNumber = newEntity.expCheckMonthNumber;
    		oldEntity.get().timeManageAtr = newEntity.timeManageAtr;
    		oldEntity.get().digestionUnit = newEntity.digestionUnit;
    		oldEntity.get().occurrOtUseAtr = newEntity.occurrOtUseAtr;
    		oldEntity.get().deadlCheckMonth = newEntity.deadlCheckMonth;
    		oldEntity.get().desOtOneDayTime = newEntity.desOtOneDayTime;
    		oldEntity.get().desOtHalfDayTime = newEntity.desOtHalfDayTime;
    		oldEntity.get().certainOtTime = newEntity.certainOtTime;
    		oldEntity.get().occurrHDWorkUseAtr = newEntity.occurrHDWorkUseAtr;
    		oldEntity.get().occurrHDWorkTimeAtr = newEntity.occurrHDWorkTimeAtr;
    		oldEntity.get().desHDWorkOneDayTime = newEntity.desHDWorkOneDayTime;
    		oldEntity.get().desHDWorkHalfDayTime = newEntity.desHDWorkHalfDayTime;
    		oldEntity.get().cerTainHDWorkTime = newEntity.cerTainHDWorkTime;
	        this.commandProxy().update(oldEntity.get());
	        this.getEntityManager().flush();
    	}
    }

    /*
     * (non-Javadoc)
     * 
     * @see nts.uk.ctx.at.shared.dom.vacation.setting.compensatoryleave.
     * CompensLeaveComSetRepository#find(java.lang.String)
     */
    @SneakyThrows
    @Override
	public CompensatoryLeaveComSetting find(String companyId) {
    	Optional<KclmtCompensLeaveCom> kclmtCompensLeaveCom =  this.queryProxy().find(companyId,
                KclmtCompensLeaveCom.class);
    	if(kclmtCompensLeaveCom.isPresent()) {
    		return kclmtCompensLeaveCom.get().toDomain();
    	}
    	return null;
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
