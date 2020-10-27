package nts.uk.ctx.at.shared.infra.repository.holidaysetting.employment;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.MonthlyNumberOfDays;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.PublicHolidayMonthSetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employment.EmploymentMonthDaySettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.holidaysetting.employment.KshmtHdpubMonthdaysEmp;

/**
 * The Class JpaEmploymentMonthDaySettingGetMemento.
 */
public class JpaEmploymentMonthDaySettingGetMemento implements EmploymentMonthDaySettingGetMemento {
	
	/** The list kshmt emp month day set. */
	private List<KshmtHdpubMonthdaysEmp> listKshmtHdpubMonthdaysEmp;
	
	/**
	 * Instantiates a new jpa employment month day setting get memento.
	 *
	 * @param entities the entities
	 */
	public JpaEmploymentMonthDaySettingGetMemento(List<KshmtHdpubMonthdaysEmp> entities){
		this.listKshmtHdpubMonthdaysEmp = entities;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.employment.EmploymentMonthDaySettingGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.listKshmtHdpubMonthdaysEmp.get(0).getKshmtHdpubMonthdaysEmpPK().getCid());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.employment.EmploymentMonthDaySettingGetMemento#getEmploymentCode()
	 */
	@Override
	public String getEmploymentCode() {
		return this.listKshmtHdpubMonthdaysEmp.get(0).getKshmtHdpubMonthdaysEmpPK().getEmpCd();
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.employment.EmploymentMonthDaySettingGetMemento#getManagementYear()
	 */
	@Override
	public Year getManagementYear() {
		return new Year(this.listKshmtHdpubMonthdaysEmp.get(0).getKshmtHdpubMonthdaysEmpPK().getManageYear());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.employment.EmploymentMonthDaySettingGetMemento#getPublicHolidayMonthSettings()
	 */
	@Override
	public List<PublicHolidayMonthSetting> getPublicHolidayMonthSettings() {
		return this.listKshmtHdpubMonthdaysEmp.stream().map(e -> {
			PublicHolidayMonthSetting domain = new PublicHolidayMonthSetting(new Year(e.getKshmtHdpubMonthdaysEmpPK().getManageYear()),
					new Integer(e.getKshmtHdpubMonthdaysEmpPK().getMonth()),
					new MonthlyNumberOfDays(e.getInLegalHd()));
			return domain;
		}).collect(Collectors.toList());
	}
}
