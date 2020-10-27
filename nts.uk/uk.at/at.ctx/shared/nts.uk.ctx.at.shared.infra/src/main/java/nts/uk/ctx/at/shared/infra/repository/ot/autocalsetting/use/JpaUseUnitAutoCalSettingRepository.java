/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.ot.autocalsetting.use;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.use.UseUnitAutoCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.use.UseUnitAutoCalSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.use.KrcmtCalcSetUnitSet;

/**
 * The Class JpaUseUnitAutoCalSettingRepository.
 */
@Stateless
public class JpaUseUnitAutoCalSettingRepository  extends JpaRepository implements UseUnitAutoCalSettingRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.UseUnitAutoCalSettingRepository#update(nts.uk.ctx.at.schedule.dom.shift.autocalsetting.UseUnitAutoCalSetting)
	 */
	@Override
	public void update(UseUnitAutoCalSetting useUnitAutoCalSetting) {
		Optional<KrcmtCalcSetUnitSet> optional = this.queryProxy().find(useUnitAutoCalSetting.getCompanyId().v(), KrcmtCalcSetUnitSet.class);

		if (!optional.isPresent()) {
			throw new RuntimeException("Unit Auto not existed.");
		}

		KrcmtCalcSetUnitSet entity = optional.get();
		useUnitAutoCalSetting.saveToMemento(new JpaUseUnitAutoCalSettingSetMemento(entity));
		this.commandProxy().update(entity);		
		
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.UseUnitAutoCalSettingRepository#getAllUseUnitAutoCalSetting(java.lang.String)
	 */
	@Override
	public Optional<UseUnitAutoCalSetting> getAllUseUnitAutoCalSetting(String companyId) {

		Optional<KrcmtCalcSetUnitSet> optKrcmtCalcSetUnitSet = this.queryProxy().find(companyId, KrcmtCalcSetUnitSet.class);

		if (!optKrcmtCalcSetUnitSet.isPresent()) {
			return Optional.empty();
		}
		return Optional.of(new UseUnitAutoCalSetting(new JpaUseUnitAutoCalSettingGetMemento(optKrcmtCalcSetUnitSet.get())));
	}
	
}
