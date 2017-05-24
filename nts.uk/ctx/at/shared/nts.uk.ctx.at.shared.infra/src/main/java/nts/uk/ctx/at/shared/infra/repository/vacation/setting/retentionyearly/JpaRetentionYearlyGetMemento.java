package nts.uk.ctx.at.shared.infra.repository.vacation.setting.retentionyearly;

import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySettingGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSetting;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KmfmtRetentionYearly;

public class JpaRetentionYearlyGetMemento implements RetentionYearlySettingGetMemento {

	private KmfmtRetentionYearly typeValue;
	
	public JpaRetentionYearlyGetMemento(KmfmtRetentionYearly typeValue) {
		this.typeValue = typeValue;
	}
	
	@Override
	public String getCompanyId() {
		return this.typeValue.getCid();
	}

	@Override
	public UpperLimitSetting getUpperLimitSetting() {
		return new UpperLimitSetting(new JpaUpperLimitSettingGetMemento(this.typeValue));
	}

	@Override
	public Boolean getCanAddToCumulationYearlyAsNormalWorkDay() {
		// TODO Auto-generated method stub
		return null;
	}

}
