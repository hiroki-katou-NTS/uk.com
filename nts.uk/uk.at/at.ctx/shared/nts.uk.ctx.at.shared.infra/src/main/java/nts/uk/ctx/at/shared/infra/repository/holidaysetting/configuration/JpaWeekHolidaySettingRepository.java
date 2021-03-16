package nts.uk.ctx.at.shared.infra.repository.holidaysetting.configuration;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.WeekHolidaySetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.configuration.WeekHolidaySettingRepository;
import nts.uk.ctx.at.shared.infra.entity.holidaysetting.configuration.KshmtHdpubPerWeek;

/**
 * The Class JpaWeekHolidaySettingRepository.
 */
@Stateless
public class JpaWeekHolidaySettingRepository extends JpaRepository implements WeekHolidaySettingRepository {

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.WeekHolidaySettingRepository#findByCID(java.lang.String)
	 */
	@Override
	public Optional<WeekHolidaySetting> findByCID(String companyId) {
		return this.queryProxy().find(companyId, KshmtHdpubPerWeek.class).map(e -> this.toDomain(e));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.WeekHolidaySettingRepository#update(nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.WeekHolidaySetting)
	 */
	@Override
	public void update(WeekHolidaySetting domain) {
		this.commandProxy().update(this.toEntity(domain));
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.WeekHolidaySettingRepository#add(nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.WeekHolidaySetting)
	 */
	@Override
	public void add(WeekHolidaySetting domain) {
		this.commandProxy().insert(this.toEntity(domain));
	}
	
	/**
	 * To entity.
	 *
	 * @param domain the domain
	 * @return the kshmt week hd set
	 */
	private KshmtHdpubPerWeek toEntity(WeekHolidaySetting domain){
		KshmtHdpubPerWeek entity = new KshmtHdpubPerWeek();
		domain.saveToMemento(new JpaWeekHolidaySettingSetMemento(entity));
		return entity;
	}
	
	/**
	 * To domain.
	 *
	 * @param entity the entity
	 * @return the week holiday setting
	 */
	private WeekHolidaySetting toDomain(KshmtHdpubPerWeek entity){
		WeekHolidaySetting domain = new WeekHolidaySetting(new JpaWeekHolidaySettingGetMemento(entity));
		return domain;
	}

}
