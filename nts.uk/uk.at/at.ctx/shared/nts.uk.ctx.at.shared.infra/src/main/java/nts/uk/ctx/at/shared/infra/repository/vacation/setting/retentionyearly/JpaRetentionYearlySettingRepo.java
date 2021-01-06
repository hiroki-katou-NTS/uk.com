/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.retentionyearly;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySettingRepository;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KmfmtRetentionYearly;

/**
 * The Class JpaRetentionYearlySettingRepo.
 */
@Stateless
public class JpaRetentionYearlySettingRepo extends JpaRepository implements RetentionYearlySettingRepository {

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
	 * RetentionYearlySettingRepository#insert(nts.uk.ctx.at.shared.dom.vacation
	 * .setting.retentionyearly.RetentionYearlySetting)
	 */
	@Override
	public void insert(RetentionYearlySetting setting) {
		KmfmtRetentionYearly entity = new KmfmtRetentionYearly();
		setting.saveToMemento(new JpaRetentionYearlySetMemento(entity));
		this.commandProxy().insert(entity);
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
	 * RetentionYearlySettingRepository#update(nts.uk.ctx.at.shared.dom.vacation
	 * .setting.retentionyearly.RetentionYearlySetting)
	 */
	@Override
	public void update(RetentionYearlySetting setting) {
		Optional<KmfmtRetentionYearly> optional = this.queryProxy().find(setting.getCompanyId(), KmfmtRetentionYearly.class);
		KmfmtRetentionYearly entity = null;
		if(optional.isPresent()) {
			entity = optional.get();
		}
		else{
			entity = new KmfmtRetentionYearly();
		}
		setting.saveToMemento(new JpaRetentionYearlySetMemento(entity));
		this.commandProxy().update(entity);
	}

	/*
	 * (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.
	 * RetentionYearlySettingRepository#findByCompanyId(java.lang.String)
	 */
	@Override
	public Optional<RetentionYearlySetting> findByCompanyId(String companyId) {
		return this.queryProxy()
				.find(companyId , KmfmtRetentionYearly.class)
				.map(c -> this.toDomain(c));
	}
	
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the retention yearly setting
	 */
	private RetentionYearlySetting toDomain(KmfmtRetentionYearly entity) {
		return new RetentionYearlySetting(new JpaRetentionYearlyGetMemento(entity));
		
	}

	@Override
	public void copyMasterData(String sourceCid, String targetCid, boolean isReplace) {
		// TODO Auto-generated method stub
		
	}
}
