package nts.uk.ctx.at.shared.infra.repository.holidaysetting.employee;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.PublicHolidayMonthSetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.employee.EmployeeMonthDaySettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.holidaysetting.employee.KshmtHdpubMonthdaysSya;
import nts.uk.ctx.at.shared.infra.entity.holidaysetting.employee.KshmtHdpubMonthdaysSyaPK;

/**
 * The Class JpaEmployeeMonthDaySettingSetMemento.
 */
public class JpaEmployeeMonthDaySettingSetMemento implements EmployeeMonthDaySettingSetMemento{
	

	/** The list kshmt employee month day set. */
	private List<KshmtHdpubMonthdaysSya> listKshmtHdpubMonthdaysSya;
	
	/** The company id. */
	private String companyId;
	
	/** The s id. */
	private String sId;
	
	/** The year. */
	private int year;

	/**
	 * Instantiates a new jpa employee month day setting set memento.
	 *
	 * @param entities the entities
	 */
	public JpaEmployeeMonthDaySettingSetMemento(List<KshmtHdpubMonthdaysSya> entities){
		entities.stream().forEach(item -> {
			if (item.getKshmtHdpubMonthdaysSyaPK() == null) {
				item.setKshmtHdpubMonthdaysSyaPK(new KshmtHdpubMonthdaysSyaPK());
			}
		});
		this.listKshmtHdpubMonthdaysSya = entities;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.employee.EmployeeMonthDaySettingSetMemento#setCompanyId(nts.uk.ctx.bs.employee.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.companyId = companyId.v();
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.employee.EmployeeMonthDaySettingSetMemento#setEmployeeId(java.lang.String)
	 */
	@Override
	public void setEmployeeId(String employeeId) {
		this.sId = employeeId;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.employee.EmployeeMonthDaySettingSetMemento#setManagementYear(nts.uk.ctx.bs.employee.dom.holidaysetting.common.Year)
	 */
	@Override
	public void setManagementYear(Year managementYear) {
		this.year = managementYear.v();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.employee.EmployeeMonthDaySettingSetMemento#setPublicHolidayMonthSettings(java.util.List)
	 */
	@Override
	public void setPublicHolidayMonthSettings(List<PublicHolidayMonthSetting> publicHolidayMonthSettings) {
		if(this.listKshmtHdpubMonthdaysSya.isEmpty()){
			publicHolidayMonthSettings.stream().forEach(item -> {
				KshmtHdpubMonthdaysSya entity = new KshmtHdpubMonthdaysSya();
				entity.setKshmtHdpubMonthdaysSyaPK(new KshmtHdpubMonthdaysSyaPK());
				entity.getKshmtHdpubMonthdaysSyaPK().setCid(this.companyId);
				entity.getKshmtHdpubMonthdaysSyaPK().setSid(this.sId);
				entity.getKshmtHdpubMonthdaysSyaPK().setManageYear(this.year);
				entity.getKshmtHdpubMonthdaysSyaPK().setMonth(item.getMonth());
				entity.setInLegalHd(item.getInLegalHoliday().v());
				
				this.listKshmtHdpubMonthdaysSya.add(entity);
			});
		} else {
			this.listKshmtHdpubMonthdaysSya.stream().forEach(e -> {
				e.getKshmtHdpubMonthdaysSyaPK().setCid(this.companyId);
				e.getKshmtHdpubMonthdaysSyaPK().setManageYear(this.year);
				e.getKshmtHdpubMonthdaysSyaPK().setMonth(publicHolidayMonthSettings.stream()
														.filter(item -> e.getKshmtHdpubMonthdaysSyaPK().getMonth() == item.getMonth())
																	.findFirst().get().getMonth());
				e.setInLegalHd(publicHolidayMonthSettings.stream()
						.filter(item -> e.getKshmtHdpubMonthdaysSyaPK().getMonth() == item.getMonth())
									.findAny().get().getInLegalHoliday().v());
			});
		}
	}
}
