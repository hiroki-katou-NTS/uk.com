package nts.uk.ctx.bs.employee.infra.repository.holidaysetting.configuration;

import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidayManagementClassification;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidayManagementUsageUnit;
import nts.uk.ctx.bs.employee.dom.holidaysetting.configuration.PublicHolidaySettingGetMemento;
import nts.uk.ctx.bs.employee.infra.entity.holidaysetting.configuration.KshmtPublicHdSet;
import nts.uk.shr.com.context.AppContexts;

public class JpaPublicHolidaySettingGetMemento implements PublicHolidaySettingGetMemento {
	
	private final static int TRUE_VALUE = 1;
	
	private KshmtPublicHdSet kshmtPublicHdSet;
	
	public JpaPublicHolidaySettingGetMemento(KshmtPublicHdSet entity) {
		this.kshmtPublicHdSet = entity;
	}
	
	@Override
	public String getCompanyID() {
		return AppContexts.user().companyId();
	}

	@Override
	public boolean getIsManageComPublicHd() {
		if(this.kshmtPublicHdSet.getIsManageComPublicHd() == TRUE_VALUE){
			return true;
		}
		return false;
	}

	@Override
	public PublicHolidayManagementClassification getPublicHdManagementClassification() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PublicHolidayManagementUsageUnit getPublicHdManagementUsageUnit() {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public boolean getIsWeeklyHdCheck() {
		// TODO Auto-generated method stub
		return false;
	}

}
