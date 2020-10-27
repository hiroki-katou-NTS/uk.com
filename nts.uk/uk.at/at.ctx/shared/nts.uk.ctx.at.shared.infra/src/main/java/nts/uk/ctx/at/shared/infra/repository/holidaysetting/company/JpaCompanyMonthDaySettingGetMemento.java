package nts.uk.ctx.at.shared.infra.repository.holidaysetting.company;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.MonthlyNumberOfDays;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.PublicHolidayMonthSetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.company.CompanyMonthDaySettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.holidaysetting.company.KshmtHdpubDPerMCom;


/**
 * The Class JpaCompanyMonthDaySettingGetMemento.
 */
public class JpaCompanyMonthDaySettingGetMemento implements CompanyMonthDaySettingGetMemento {
	
	/** The list kshmt com month day set. */
	private List<KshmtHdpubDPerMCom> listKshmtHdpubDPerMCom;
	
	/**
	 * Instantiates a new jpa company month day setting get memento.
	 *
	 * @param entity the entity
	 */
	public JpaCompanyMonthDaySettingGetMemento(List<KshmtHdpubDPerMCom> entities){
		this.listKshmtHdpubDPerMCom = entities;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.company.CompanyMonthDaySettingGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.listKshmtHdpubDPerMCom.get(0).getKshmtHdpubDPerMComPK().getCid());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.company.CompanyMonthDaySettingGetMemento#getManagementYear()
	 */
	@Override
	public Year getManagementYear() {
		return new Year(this.listKshmtHdpubDPerMCom.get(0).getKshmtHdpubDPerMComPK().getManageYear());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.company.CompanyMonthDaySettingGetMemento#getPublicHolidayMonthSettings()
	 */
	@Override
	public List<PublicHolidayMonthSetting> getPublicHolidayMonthSettings() {
		return this.listKshmtHdpubDPerMCom.stream().map(e -> {
			PublicHolidayMonthSetting domain = new PublicHolidayMonthSetting(new Year(e.getKshmtHdpubDPerMComPK().getManageYear()),
					new Integer(e.getKshmtHdpubDPerMComPK().getMonth()),
					new MonthlyNumberOfDays(e.getInLegalHd()));
			return domain;
		}).collect(Collectors.toList());
	}

}
