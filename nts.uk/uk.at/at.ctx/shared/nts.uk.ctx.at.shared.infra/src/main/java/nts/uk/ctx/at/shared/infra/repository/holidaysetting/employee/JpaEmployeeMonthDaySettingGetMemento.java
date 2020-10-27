package nts.uk.ctx.at.shared.infra.repository.holidaysetting.employee;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.MonthlyNumberOfDays;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.PublicHolidayMonthSetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employee.EmployeeMonthDaySettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.holidaysetting.employee.KshmtHdpubMonthdaysSya;

/**
 * The Class JpaEmployeeMonthDaySettingGetMemento.
 */
public class JpaEmployeeMonthDaySettingGetMemento implements EmployeeMonthDaySettingGetMemento {
	

	/** The list kshmt employee month day set. */
	private List<KshmtHdpubMonthdaysSya> listKshmtHdpubMonthdaysSya;
	

	/**
	 * Instantiates a new jpa employee month day setting get memento.
	 *
	 * @param entities the entities
	 */
	public JpaEmployeeMonthDaySettingGetMemento(List<KshmtHdpubMonthdaysSya> entities){
		this.listKshmtHdpubMonthdaysSya = entities;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.employee.EmployeeMonthDaySettingGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.listKshmtHdpubMonthdaysSya.get(0).getKshmtHdpubMonthdaysSyaPK().getCid());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.employee.EmployeeMonthDaySettingGetMemento#getEmployeeId()
	 */
	@Override
	public String getEmployeeId() {
		return this.listKshmtHdpubMonthdaysSya.get(0).getKshmtHdpubMonthdaysSyaPK().getSid();
	}
	

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.employee.EmployeeMonthDaySettingGetMemento#getManagementYear()
	 */
	@Override
	public Year getManagementYear() {
		return new Year(this.listKshmtHdpubMonthdaysSya.get(0).getKshmtHdpubMonthdaysSyaPK().getManageYear());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.employee.EmployeeMonthDaySettingGetMemento#getPublicHolidayMonthSettings()
	 */
	@Override
	public List<PublicHolidayMonthSetting> getPublicHolidayMonthSettings() {
		return this.listKshmtHdpubMonthdaysSya.stream().map(e -> {
			PublicHolidayMonthSetting domain = new PublicHolidayMonthSetting(new Year(e.getKshmtHdpubMonthdaysSyaPK().getManageYear()),
					new Integer(e.getKshmtHdpubMonthdaysSyaPK().getMonth()),
					new MonthlyNumberOfDays(e.getInLegalHd()));
			return domain;
		}).collect(Collectors.toList());
	}

}
