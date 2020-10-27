/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.ot.autocalsetting.com;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.com.ComAutoCalSetting;
import nts.uk.ctx.at.shared.dom.scherec.dailyattdcal.autocalsetting.com.ComAutoCalSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.com.KrcmtCalcSetCom;

/**
 * The Class JpaComAutoCalSettingRepository.
 */
@Stateless
public class JpaComAutoCalSettingRepository extends JpaRepository implements ComAutoCalSettingRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.ComAutoCalSettingRepository#update(nts.uk.ctx.at.schedule.dom.shift.autocalsetting.ComAutoCalSetting)
	 */
	@Override
	public void update(ComAutoCalSetting comAutoCalSetting) {
		Optional<KrcmtCalcSetCom> optional = this.queryProxy().find(comAutoCalSetting.getCompanyId().v(), KrcmtCalcSetCom.class);

		if (!optional.isPresent()) {
			throw new RuntimeException("Total times not existed.");
		}

		KrcmtCalcSetCom entity = optional.get();
		comAutoCalSetting.saveToMemento(new JpaComAutoCalSettingSetMemento(entity));
		this.commandProxy().update(entity);
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.ComAutoCalSettingRepository#getAllComAutoCalSetting(java.lang.String)
	 */
	@Override
	public Optional<ComAutoCalSetting> getAllComAutoCalSetting(String companyId) {

		Optional<KrcmtCalcSetCom> optKrcmtCalcSetCom = this.queryProxy().find(companyId, KrcmtCalcSetCom.class);

		if (!optKrcmtCalcSetCom.isPresent()) {
			return Optional.empty();
		}
		return Optional.of(new ComAutoCalSetting(new JpaComAutoCalSettingGetMemento(optKrcmtCalcSetCom.get())));
	}

}
