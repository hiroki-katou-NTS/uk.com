package nts.uk.ctx.at.shared.infra.repository.vacation.setting.retentionyearly;

import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.MaxDaysRetention;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearsAmount;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KmfmtRetentionYearly;

public class JpaUpperLimitSettingSetMemento implements UpperLimitSettingSetMemento{

	private KmfmtRetentionYearly typeValue;
	
	public JpaUpperLimitSettingSetMemento(KmfmtRetentionYearly typeValue) {
		this.typeValue = typeValue;
	}
	
	@Override
	public void setRetentionYearsAmount(RetentionYearsAmount retentionYearsAmount) {
		this.typeValue.setMaxDaysRetention(retentionYearsAmount.v().shortValue());		
	}

	@Override
	public void setMaxDaysRetention(MaxDaysRetention maxDaysCumulation) {
		this.typeValue.setMaxDaysRetention(maxDaysCumulation.v().shortValue());
	}

}
