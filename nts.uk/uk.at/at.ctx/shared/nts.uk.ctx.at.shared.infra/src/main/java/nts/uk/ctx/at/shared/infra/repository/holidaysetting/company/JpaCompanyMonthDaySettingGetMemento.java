package nts.uk.ctx.at.shared.infra.repository.holidaysetting.company;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.MonthlyNumberOfDays;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.PublicHolidayMonthSetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.company.CompanyMonthDaySettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.holidaysetting.company.KshmtComMonthDaySet;


/**
 * The Class JpaCompanyMonthDaySettingGetMemento.
 */
public class JpaCompanyMonthDaySettingGetMemento implements CompanyMonthDaySettingGetMemento {
	
	/** The list kshmt com month day set. */
	private List<KshmtComMonthDaySet> listKshmtComMonthDaySet;
	
	/**
	 * Instantiates a new jpa company month day setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaCompanyMonthDaySettingGetMemento(List<KshmtComMonthDaySet> entities){
		this.listKshmtComMonthDaySet = entities;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.company.CompanyMonthDaySettingGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.listKshmtComMonthDaySet.get(0).getKshmtComMonthDaySetPK().getCid());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.company.CompanyMonthDaySettingGetMemento#getManagementYear()
	 */
	@Override
	public Year getManagementYear() {
		return new Year(this.listKshmtComMonthDaySet.get(0).getKshmtComMonthDaySetPK().getManageYear());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.company.CompanyMonthDaySettingGetMemento#getPublicHolidayMonthSettings()
	 */
	@Override
	public List<PublicHolidayMonthSetting> getPublicHolidayMonthSettings() {
		return this.listKshmtComMonthDaySet.stream().map(e -> {
			PublicHolidayMonthSetting domain = new PublicHolidayMonthSetting(new Year(e.getKshmtComMonthDaySetPK().getManageYear()),
					new Integer(e.getKshmtComMonthDaySetPK().getMonth()),
					new MonthlyNumberOfDays(e.getInLegalHd()));
			return domain;
		}).collect(Collectors.toList());
	}

}
