package nts.uk.ctx.bs.employee.infra.repository.holidaysetting.configuration;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySetting;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySettingRepository;
import nts.uk.ctx.bs.employee.infra.entity.holidaysetting.configuration.KshmtPublicHdSet;

/**
 * The Class JpaPublicHolidaySettingRepository.
 */
@Stateless
public class JpaPublicHolidaySettingRepository extends JpaRepository implements PublicHolidaySettingRepository{

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySettingRepository#findByCID(java.lang.String)
	 */
	@Override
	public Optional<PublicHolidaySetting> findByCID(String companyId) {
		return this.queryProxy().find(companyId, KshmtPublicHdSet.class).map(e -> this.toDomain(e));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySettingRepository#update(nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySetting)
	 */
	@Override
	public void update(PublicHolidaySetting domain) {
		this.commandProxy().update(this.toEntity(domain));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySettingRepository#add(nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySetting)
	 */
	@Override
	public void add(PublicHolidaySetting domain) {
		this.commandProxy().insert(this.toEntity(domain));
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kshmt public hd set
	 */
	private KshmtPublicHdSet toEntity(PublicHolidaySetting domain){
		KshmtPublicHdSet entity = new KshmtPublicHdSet();
		domain.saveToMemento(new JpaPublicHolidaySettingSetMemento(entity));
		return entity;
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the public holiday setting
	 */
	private PublicHolidaySetting toDomain(KshmtPublicHdSet entity){
		PublicHolidaySetting domain = new PublicHolidaySetting(new JpaPublicHolidaySettingGetMemento(entity));
		return domain;
	}
}
