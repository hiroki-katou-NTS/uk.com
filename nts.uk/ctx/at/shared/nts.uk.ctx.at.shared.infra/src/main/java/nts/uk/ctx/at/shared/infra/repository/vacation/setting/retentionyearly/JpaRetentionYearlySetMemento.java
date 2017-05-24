package nts.uk.ctx.at.shared.infra.repository.vacation.setting.retentionyearly;

import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySettingSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSetting;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KmfmtRetentionYearly;

public class JpaRetentionYearlySetMemento implements RetentionYearlySettingSetMemento{

	private KmfmtRetentionYearly typeValue;
	
	public JpaRetentionYearlySetMemento(KmfmtRetentionYearly typeValue) {
		this.typeValue = typeValue;
	}
	
	@Override
	public void setCompanyId(String companyId) {
		this.typeValue.setCid(companyId);
	}

	@Override
	public void setUpperLimitSetting(UpperLimitSetting upperLimitSetting) {
		this.typeValue.setMaxDaysRetention(upperLimitSetting.getMaxDaysCumulation().v().shortValue());
		this.typeValue.setYearAmount(upperLimitSetting.getRetentionYearsAmount().v().shortValue());
	}

	@Override
	public void setcanAddToCumulationYearlyAsNormalWorkDay(Boolean canAddToCumulationYearlyAsNormalWorkDay) {
		// TODO Auto-generated method stub
		
	}

}
