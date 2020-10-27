package nts.uk.ctx.at.shared.infra.repository.holidaysetting.workplace;

import java.util.List;
import java.util.stream.Collectors;

import nts.uk.ctx.at.shared.dom.common.CompanyId;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.MonthlyNumberOfDays;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.PublicHolidayMonthSetting;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.common.Year;
import nts.uk.ctx.at.shared.dom.holidaymanagement.publicholiday.workplace.WorkplaceMonthDaySettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.holidaysetting.workplace.KshmtHdpubDPerMWkp;

/**
 * The Class JpaWorkplaceMonthDaySettingGetMemento.
 */
public class JpaWorkplaceMonthDaySettingGetMemento implements WorkplaceMonthDaySettingGetMemento {
	
	/** The list kshmt wkp month day set. */
	private List<KshmtHdpubDPerMWkp> listKshmtHdpubDPerMWkp;
	
	/**
	 * Instantiates a new jpa workplace month day setting get memento.
	 *
	 * @param entities the entities
	 */
	public JpaWorkplaceMonthDaySettingGetMemento(List<KshmtHdpubDPerMWkp> entities){
		this.listKshmtHdpubDPerMWkp = entities;
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.workplace.WorkplaceMonthDaySettingGetMemento#getCompanyId()
	 */
	@Override
	public CompanyId getCompanyId() {
		return new CompanyId(this.listKshmtHdpubDPerMWkp.get(0).getKshmtHdpubDPerMWkpPK().getCid());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.workplace.WorkplaceMonthDaySettingGetMemento#getWorkplaceID()
	 */
	@Override
	public String getWorkplaceID() {
		return this.listKshmtHdpubDPerMWkp.get(0).getKshmtHdpubDPerMWkpPK().getWkpId();
	}
	
	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.workplace.WorkplaceMonthDaySettingGetMemento#getManagementYear()
	 */
	@Override
	public Year getManagementYear() {
		return new Year(this.listKshmtHdpubDPerMWkp.get(0).getKshmtHdpubDPerMWkpPK().getManageYear());
	}

	/* (non-Javadoc)
	 * @see nts.uk.ctx.bs.employee.dom.holidaysetting.workplace.WorkplaceMonthDaySettingGetMemento#getPublicHolidayMonthSettings()
	 */
	@Override
	public List<PublicHolidayMonthSetting> getPublicHolidayMonthSettings() {
		return this.listKshmtHdpubDPerMWkp.stream().map(e -> {
			PublicHolidayMonthSetting domain = new PublicHolidayMonthSetting(new Year(e.getKshmtHdpubDPerMWkpPK().getManageYear()),
					new Integer(e.getKshmtHdpubDPerMWkpPK().getMonth()),
					new MonthlyNumberOfDays(e.getInLegalHd()));
			return domain;
		}).collect(Collectors.toList());
	}
}
