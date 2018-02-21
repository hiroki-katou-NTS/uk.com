package nts.uk.ctx.at.shared.infra.repository.holidaysetting.employee;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.MonthlyNumberOfDays;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.PublicHolidayMonthSetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employee.EmployeeMonthDaySettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.holidaysetting.employee.KshmtEmployeeMonthDaySet;

/**
 * The Class JpaEmployeeMonthDaySettingGetMemento.
 */
public class JpaEmployeeMonthDaySettingGetMemento implements EmployeeMonthDaySettingGetMemento {
	

	/** The list kshmt employee month day set. */
	private List<KshmtEmployeeMonthDaySet> listKshmtEmployeeMonthDaySet;
	

	/**
	 * Instantiates a new jpa employee month day setting get memento.
	 *
	 * @param entities the entities
	 */
	public JpaEmployeeMonthDaySettingGetMemento(List<KshmtEmployeeMonthDaySet> entities){
		this.listKshmtEmployeeMonthDaySet = entities;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.employee.EmployeeMonthDaySettingGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.listKshmtEmployeeMonthDaySet.get(0).getKshmtEmployeeMonthDaySetPK().getCid());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.employee.EmployeeMonthDaySettingGetMemento#getEmployeeId()
	 */
	@Override
	public String getEmployeeId() {
		return this.listKshmtEmployeeMonthDaySet.get(0).getKshmtEmployeeMonthDaySetPK().getSid();
	}
	

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.employee.EmployeeMonthDaySettingGetMemento#getManagementYear()
	 */
	@Override
	public Year getManagementYear() {
		return new Year(this.listKshmtEmployeeMonthDaySet.get(0).getKshmtEmployeeMonthDaySetPK().getManageYear());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.employee.EmployeeMonthDaySettingGetMemento#getPublicHolidayMonthSettings()
	 */
	@Override
	public List<PublicHolidayMonthSetting> getPublicHolidayMonthSettings() {
		return this.listKshmtEmployeeMonthDaySet.stream().map(e -> {
			PublicHolidayMonthSetting domain = new PublicHolidayMonthSetting(new Year(e.getKshmtEmployeeMonthDaySetPK().getManageYear()),
					new Integer(e.getKshmtEmployeeMonthDaySetPK().getMonth()),
					new MonthlyNumberOfDays(e.getInLegalHd()));
			return domain;
		}).collect(Collectors.toList());
	}

}
