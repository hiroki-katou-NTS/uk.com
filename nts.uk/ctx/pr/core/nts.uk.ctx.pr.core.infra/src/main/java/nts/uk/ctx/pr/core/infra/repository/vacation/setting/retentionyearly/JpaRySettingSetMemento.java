package nts.uk.ctx.pr.core.infra.repository.vacation.setting.retentionyearly;

import nts.uk.ctx.pr.core.dom.vacation.setting.retentionyearly.RetentionYearlySettingSetMemento;
import nts.uk.ctx.pr.core.dom.vacation.setting.retentionyearly.UpperLimitSetting;
import nts.uk.ctx.pr.core.infra.entity.vacation.setting.KmfmtRetentionYearly;

public class JpaRySettingSetMemento implements RetentionYearlySettingSetMemento{

	private KmfmtRetentionYearly typeValue;
	
	public JpaRySettingSetMemento(KmfmtRetentionYearly typeValue) {
		this.typeValue = typeValue;
	}
	
	@Override
	public void setCompanyId(String companyId) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setUpperLimitSetting(UpperLimitSetting upperLimitSetting) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void setcanAddToCumulationYearlyAsNormalWorkDay(Boolean canAddToCumulationYearlyAsNormalWorkDay) {
		// TODO Auto-generated method stub
		
	}

}
