/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.ot.autocalsetting.wkp;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkp.WkpAutoCalSetting;
import nts.uk.ctx.at.shared.dom.ot.autocalsetting.wkp.WkpAutoCalSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.wkp.KshmtAutoWkpCalSet;
import nts.uk.ctx.at.shared.infra.entity.ot.autocalsetting.wkp.KshmtAutoWkpCalSetPK;

/**
 * The Class JpaWkpAutoCalSettingRepository.
 */
@Stateless
public class JpaWkpAutoCalSettingRepository extends JpaRepository implements WkpAutoCalSettingRepository {

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
	 * WkpAutoCalSettingRepository#update(nts.uk.ctx.at.schedule.dom.shift.
	 * autocalsetting.WkpAutoCalSetting)
	 */
	@Override
	public void update(WkpAutoCalSetting wkpAutoCalSetting) {
		this.commandProxy().update(this.toEntity(wkpAutoCalSetting));
		this.getEntityManager().flush();

	}

	@Override
	public void add(WkpAutoCalSetting wkpAutoCalSetting) {
		this.commandProxy().insert(this.toEntity(wkpAutoCalSetting));
		this.getEntityManager().flush();

	}

	/**
	 * To entity.
	 *
	 * @param jobAutoCalSetting
	 *            the job auto cal setting
	 * @return the kshmt auto job cal set
	 */
	private KshmtAutoWkpCalSet toEntity(WkpAutoCalSetting wkpAutoCalSetting) {
		Optional<KshmtAutoWkpCalSet> optinal = this.queryProxy().find(
				new KshmtAutoWkpCalSetPK(wkpAutoCalSetting.getCompanyId().v(), wkpAutoCalSetting.getWkpId().v()),
				KshmtAutoWkpCalSet.class);
		KshmtAutoWkpCalSet entity = null;
		if (optinal.isPresent()) {
			entity = optinal.get();
		} else {
			entity = new KshmtAutoWkpCalSet();
		}
		JpaWkpAutoCalSettingSetMemento memento = new JpaWkpAutoCalSettingSetMemento(entity);
		wkpAutoCalSetting.saveToMemento(memento);
		return entity;
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
	 * WkpAutoCalSettingRepository#getAllWkpAutoCalSetting(java.lang.String,
	 * java.lang.String)
	 */
	@Override
	public Optional<WkpAutoCalSetting> getAllWkpAutoCalSetting(String companyId, String wkpId) {
		KshmtAutoWkpCalSetPK kshmtAutoWkpCalSetPK = new KshmtAutoWkpCalSetPK(companyId, wkpId);

		Optional<KshmtAutoWkpCalSet> optKshmtAutoWkpCalSet = this.queryProxy().find(kshmtAutoWkpCalSetPK,
				KshmtAutoWkpCalSet.class);

		if (!optKshmtAutoWkpCalSet.isPresent()) {
			return Optional.empty();
		}

		return Optional.of(new WkpAutoCalSetting(new JpaWkpAutoCalSettingGetMemento(optKshmtAutoWkpCalSet.get())));
	}

	/*
	 * (non-Javadoc)
	 * 
	 * @see nts.uk.ctx.at.schedule.dom.shift.autocalsetting.
	 * WkpAutoCalSettingRepository#delete(java.lang.String, java.lang.String)
	 */
	@Override
	public void delete(String cid, String wkpId) {
		this.commandProxy().remove(KshmtAutoWkpCalSet.class, new KshmtAutoWkpCalSetPK(cid, wkpId));

	}

}
