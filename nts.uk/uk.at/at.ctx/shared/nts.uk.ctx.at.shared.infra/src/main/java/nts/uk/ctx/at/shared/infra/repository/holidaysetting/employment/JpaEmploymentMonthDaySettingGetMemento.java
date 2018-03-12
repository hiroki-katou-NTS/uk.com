package nts.uk.ctx.at.shared.infra.repository.holidaysetting.employment;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.MonthlyNumberOfDays;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.PublicHolidayMonthSetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employment.EmploymentMonthDaySettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.holidaysetting.employment.KshmtEmpMonthDaySet;

/**
 * The Class JpaEmploymentMonthDaySettingGetMemento.
 */
public class JpaEmploymentMonthDaySettingGetMemento implements EmploymentMonthDaySettingGetMemento {
	
	/** The list kshmt emp month day set. */
	private List<KshmtEmpMonthDaySet> listKshmtEmpMonthDaySet;
	
	/**
	 * Instantiates a new jpa employment month day setting get memento.
	 *
	 * @param entities the entities
	 */
	public JpaEmploymentMonthDaySettingGetMemento(List<KshmtEmpMonthDaySet> entities){
		this.listKshmtEmpMonthDaySet = entities;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.employment.EmploymentMonthDaySettingGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.listKshmtEmpMonthDaySet.get(0).getKshmtEmpMonthDaySetPK().getCid());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.employment.EmploymentMonthDaySettingGetMemento#getEmploymentCode()
	 */
	@Override
	public String getEmploymentCode() {
		return this.listKshmtEmpMonthDaySet.get(0).getKshmtEmpMonthDaySetPK().getEmpCd();
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.employment.EmploymentMonthDaySettingGetMemento#getManagementYear()
	 */
	@Override
	public Year getManagementYear() {
		return new Year(this.listKshmtEmpMonthDaySet.get(0).getKshmtEmpMonthDaySetPK().getManageYear());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.employment.EmploymentMonthDaySettingGetMemento#getPublicHolidayMonthSettings()
	 */
	@Override
	public List<PublicHolidayMonthSetting> getPublicHolidayMonthSettings() {
		return this.listKshmtEmpMonthDaySet.stream().map(e -> {
			PublicHolidayMonthSetting domain = new PublicHolidayMonthSetting(new Year(e.getKshmtEmpMonthDaySetPK().getManageYear()),
					new Integer(e.getKshmtEmpMonthDaySetPK().getMonth()),
					new MonthlyNumberOfDays(e.getInLegalHd()));
			return domain;
		}).collect(Collectors.toList());
	}
}
