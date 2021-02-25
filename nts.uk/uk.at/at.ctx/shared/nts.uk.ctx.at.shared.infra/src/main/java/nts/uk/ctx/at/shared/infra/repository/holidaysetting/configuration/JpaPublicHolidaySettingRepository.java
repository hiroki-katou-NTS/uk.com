/******************************************************************
 * Copyright (c) 2017 Nittsu System to present.                   *
 * All right reserved.                                            *
 *****************************************************************/
package nts.uk.ctx.at.shared.infra.repository.holidaysetting.configuration;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidayCarryOverDeadline;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidayPeriod;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.PublicHolidaySettingRepository;
import nts.uk.ctx.at.shared.infra.entity.holidaysetting.configuration.KshmtHdPublicMgt;

/**
 * The Class JpaPublicHolidaySettingRepository.
 */
@Stateless
public class JpaPublicHolidaySettingRepository extends JpaRepository implements PublicHolidaySettingRepository{

	@Override
	public Optional<PublicHolidaySetting> get(String companyId) {
		return this.queryProxy().find(companyId, KshmtHdPublicMgt.class).map(e -> this.toDomain(e));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySettingRepository#findByCIDToList(java.lang.String)
	 */
	@Override
	public List<PublicHolidaySetting> findByCIDToList(String companyId) {
//		Optional<KshmtHdPublicMgt> optKshmtPublicHdSet = this.queryProxy().find(companyId, KshmtHdPublicMgt.class);
//		if (optKshmtPublicHdSet.isPresent()) {
//			return this.queryProxy().find(companyId, KshmtPublicHdSet.class).map(e -> this.toListDomain(e)).get();
//		}
		return new ArrayList<>();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySettingRepository#update(nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySetting)
	 */
	@Override
	public void update(PublicHolidaySetting domain) {
		this.commandProxy().update(this.toEntity(domain, true));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySettingRepository#add(nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySetting)
	 */
	@Override
	public void insert(PublicHolidaySetting domain) {
		this.commandProxy().insert(this.toEntity(domain, false));
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kshmt public hd set
	 */
	private KshmtHdPublicMgt toEntity(PublicHolidaySetting domain, boolean isUpdate){
		KshmtHdPublicMgt entity;
		if (isUpdate) {
			entity = this.queryProxy().find(domain.getCompanyID(), KshmtHdPublicMgt.class).get();
		} else {
			entity = new KshmtHdPublicMgt();
		}
		entity.setCid(domain.getCompanyID());
		entity.setMgtAtr(domain.getIsManagePublicHoliday());
		entity.setMgtPeriodAtr(domain.getPublicHolidayPeriod().value);
		entity.setCarryOverDeadline(domain.getPublicHolidayCarryOverDeadline().value);
		entity.setCarryFwdMinusArt(domain.getCarryOverNumberOfPublicHolidayIsNegative());
//		domain.saveToMemento(new JpaPublicHolidaySettingSetMemento(entity));
		return entity;
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the public holiday setting
	 */
	private PublicHolidaySetting toDomain(KshmtHdPublicMgt entity){
		PublicHolidaySetting domain = new PublicHolidaySetting(
				entity.getCid(), 
				entity.getMgtAtr(), 
				PublicHolidayPeriod.valueOf(entity.getMgtPeriodAtr()),
				PublicHolidayCarryOverDeadline.valueOf(entity.getCarryOverDeadline()),
				entity.getCarryFwdMinusArt());
		return domain;
	}

	
//	private List<PublicHolidaySetting> toListDomain(KshmtPublicHdSet entity){
//		List<PublicHolidaySetting> lstDomain = new ArrayList<>();
//		PublicHolidaySetting domain = new PublicHolidaySetting(new JpaPublicHolidaySettingGetMemento(entity), 0);
//		PublicHolidaySetting domainGrantDate = new PublicHolidaySetting(new JpaPublicHolidaySettingGetMemento(entity), 1);
//		lstDomain.add(domain);
//		lstDomain.add(domainGrantDate);
//		return lstDomain;
//	}
}
