/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.vacation.setting.retentionyearly;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KmfmtRetentionEmpCtr;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KmfmtRetentionEmpCtrPK;

/**
 * The Class JpaEmploymentSettingRepository.
 */
@Stateless
public class JpaEmploymentSettingRepository extends JpaRepository implements EmploymentSettingRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingRepository#insert(nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSetting)
	 */
	@Override
	public void insert(EmploymentSetting employmentSetting) {
		KmfmtRetentionEmpCtr entity = new KmfmtRetentionEmpCtr();
		employmentSetting.saveToMemento(new JpaEmploymentSettingSetMemento(entity));
		this.commandProxy().insert(entity);
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingRepository#update(nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSetting)
	 */
	@Override
	public void update(EmploymentSetting employmentSetting) {
		KmfmtRetentionEmpCtr entity = new KmfmtRetentionEmpCtr();
		employmentSetting.saveToMemento(new JpaEmploymentSettingSetMemento(entity));
		this.commandProxy().update(entity);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingRepository#remove(java.lang.String, java.lang.String)
	 */
	@Override
	public void remove(String companyId, String employmentCode) {
		this.commandProxy()
		.remove(KmfmtRetentionEmpCtr.class, new KmfmtRetentionEmpCtrPK(companyId, employmentCode));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingRepository#find(java.lang.String, java.lang.String)
	 */
	@Override
	public Optional<EmploymentSetting> find(String companyId, String employmentCode) {
		return this.queryProxy()
				.find(new KmfmtRetentionEmpCtrPK(companyId, employmentCode), KmfmtRetentionEmpCtr.class)
				.map(c -> this.toDomain(c));
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the employment setting
	 */
	private EmploymentSetting toDomain(KmfmtRetentionEmpCtr entity) {
		return new EmploymentSetting(new JpaEmploymentSettingGetMemento(entity));
		
	}
}
