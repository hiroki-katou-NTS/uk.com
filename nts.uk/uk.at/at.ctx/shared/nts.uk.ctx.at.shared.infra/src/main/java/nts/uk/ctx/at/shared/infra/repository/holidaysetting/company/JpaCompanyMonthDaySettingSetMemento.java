package nts.uk.ctx.at.shared.infra.repository.holidaysetting.company;

import java.util.List;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.PublicHolidayMonthSetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.company.CompanyMonthDaySettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.holidaysetting.company.KshmtHdpubDPerMCom;
import nts.uk.ctx.at.shared.infra.entity.holidaysetting.company.KshmtHdpubDPerMComPK;


/**
 * The Class JpaCompanyMonthDaySettingSetMemento.
 */
public class JpaCompanyMonthDaySettingSetMemento implements CompanyMonthDaySettingSetMemento{
	
	/** The list kshmt com month day set. */
	private List<KshmtHdpubDPerMCom> listKshmtHdpubDPerMCom;
	
	/** The company id. */
	private String companyId;

	/** The year. */
	private int year;

	/**
	 * Instantiates a new jpa company month day setting set memento.
	 *
	 * @param entities the entities
	 */
	public JpaCompanyMonthDaySettingSetMemento(List<KshmtHdpubDPerMCom> entities){
		entities.stream().forEach(item -> {
			if (item.getKshmtHdpubDPerMComPK() == null) {
				item.setKshmtHdpubDPerMComPK(new KshmtHdpubDPerMComPK());
			}
		});
		this.listKshmtHdpubDPerMCom = entities;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.company.CompanyMonthDaySettingSetMemento#setCompanyId(nts.uk.ctx.bs.employee.dom.common.CompanyId)
	 */
	@Override
	public void setCompanyId(CompanyId companyId) {
		this.companyId = companyId.v();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.company.CompanyMonthDaySettingSetMemento#setManagementYear(nts.uk.ctx.bs.employee.dom.holidaysetting.common.Year)
	 */
	@Override
	public void setManagementYear(Year managementYear) {
		this.year = managementYear.v();
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.company.CompanyMonthDaySettingSetMemento#setPublicHolidayMonthSettings(java.util.List)
	 */
	@Override
	public void setPublicHolidayMonthSettings(List<PublicHolidayMonthSetting> publicHolidayMonthSettings) {
		if(this.listKshmtHdpubDPerMCom.isEmpty()){
			publicHolidayMonthSettings.stream().forEach(item -> {
				KshmtHdpubDPerMCom entity = new KshmtHdpubDPerMCom();
				entity.setKshmtHdpubDPerMComPK(new KshmtHdpubDPerMComPK());
				entity.getKshmtHdpubDPerMComPK().setCid(this.companyId);
				entity.getKshmtHdpubDPerMComPK().setManageYear(this.year);
				entity.getKshmtHdpubDPerMComPK().setMonth(item.getMonth());
				entity.setInLegalHd(item.getInLegalHoliday().v());
				
				this.listKshmtHdpubDPerMCom.add(entity);
			});
		} else {
			this.listKshmtHdpubDPerMCom.stream().forEach(e -> {
				e.getKshmtHdpubDPerMComPK().setCid(this.companyId);
				e.getKshmtHdpubDPerMComPK().setManageYear(this.year);
				e.getKshmtHdpubDPerMComPK().setMonth(publicHolidayMonthSettings.stream()
														.filter(item -> e.getKshmtHdpubDPerMComPK().getMonth() == item.getMonth())
																	.findFirst().get().getMonth());
				e.setInLegalHd(publicHolidayMonthSettings.stream()
						.filter(item -> e.getKshmtHdpubDPerMComPK().getMonth() == item.getMonth())
									.findAny().get().getInLegalHoliday().v());
			});
		}
	}
	
}
