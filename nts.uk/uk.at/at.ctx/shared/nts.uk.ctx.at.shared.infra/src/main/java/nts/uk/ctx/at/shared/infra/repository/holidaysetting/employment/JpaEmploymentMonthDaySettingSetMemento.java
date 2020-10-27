package nts.uk.ctx.at.shared.infra.repository.holidaysetting.employment;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.PublicHolidayMonthSetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employment.EmploymentMonthDaySettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.holidaysetting.employment.KshmtHdpubMonthdaysEmp;
import nts.uk.ctx.at.shared.infra.entity.holidaysetting.employment.KshmtHdpubMonthdaysEmpPK;

/**
 * The Class JpaEmploymentMonthDaySettingSetMemento.
 */
public class JpaEmploymentMonthDaySettingSetMemento implements EmploymentMonthDaySettingSetMemento{
	
	/** The list kshmt emp month day set. */
	private List<KshmtHdpubMonthdaysEmp> listKshmtHdpubMonthdaysEmp;
	
	/** The company id. */
	private String companyId;
	
	/** The emp cd. */
	private String empCd;
	
	/** The year. */
	private int year;

	/**
	 * Instantiates a new jpa employment month day setting set memento.
	 *
	 * @param entities the entities
	 */
	public JpaEmploymentMonthDaySettingSetMemento(List<KshmtHdpubMonthdaysEmp> entities){
		entities.stream().forEach(item -> {
			if (item.getKshmtHdpubMonthdaysEmpPK() == null) {
				item.setKshmtHdpubMonthdaysEmpPK(new KshmtHdpubMonthdaysEmpPK());
			}
		});
		this.listKshmtHdpubMonthdaysEmp = entities;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.employment.EmploymentMonthDaySettingSetMemento#setCompanyId(nts.uk.ctx.bs.employee.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.companyId = companyId.v();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.employment.EmploymentMonthDaySettingSetMemento#setEmploymentCode(java.lang.String)
	 */
	@Override
	public void setEmploymentCode(String employmentCode) {
		this.empCd = employmentCode;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.employment.EmploymentMonthDaySettingSetMemento#setManagementYear(nts.uk.ctx.bs.employee.dom.holidaysetting.common.Year)
	 */
	@Override
	public void setManagementYear(Year managementYear) {
		this.year = managementYear.v();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.employment.EmploymentMonthDaySettingSetMemento#setPublicHolidayMonthSettings(java.util.List)
	 */
	@Override
	public void setPublicHolidayMonthSettings(List<PublicHolidayMonthSetting> publicHolidayMonthSettings) {
		if(this.listKshmtHdpubMonthdaysEmp.isEmpty()){
			publicHolidayMonthSettings.stream().forEach(item -> {
				KshmtHdpubMonthdaysEmp entity = new KshmtHdpubMonthdaysEmp();
				entity.setKshmtHdpubMonthdaysEmpPK(new KshmtHdpubMonthdaysEmpPK());
				entity.getKshmtHdpubMonthdaysEmpPK().setCid(this.companyId);
				entity.getKshmtHdpubMonthdaysEmpPK().setEmpCd(this.empCd);
				entity.getKshmtHdpubMonthdaysEmpPK().setManageYear(this.year);
				entity.getKshmtHdpubMonthdaysEmpPK().setMonth(item.getMonth());
				entity.setInLegalHd(item.getInLegalHoliday().v());
				
				this.listKshmtHdpubMonthdaysEmp.add(entity);
			});
		} else {
			this.listKshmtHdpubMonthdaysEmp.stream().forEach(e -> {
				e.getKshmtHdpubMonthdaysEmpPK().setCid(this.companyId);
				e.getKshmtHdpubMonthdaysEmpPK().setManageYear(this.year);
				e.getKshmtHdpubMonthdaysEmpPK().setMonth(publicHolidayMonthSettings.stream()
														.filter(item -> e.getKshmtHdpubMonthdaysEmpPK().getMonth() == item.getMonth())
																	.findFirst().get().getMonth());
				e.setInLegalHd(publicHolidayMonthSettings.stream()
						.filter(item -> e.getKshmtHdpubMonthdaysEmpPK().getMonth() == item.getMonth())
									.findAny().get().getInLegalHoliday().v());
			});
		}
	}
}
