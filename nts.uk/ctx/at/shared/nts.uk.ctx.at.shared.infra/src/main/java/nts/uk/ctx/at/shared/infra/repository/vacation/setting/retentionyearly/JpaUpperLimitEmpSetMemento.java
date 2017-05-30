package nts.uk.ctx.at.shared.infra.repository.vacation.setting.retentionyearly;

import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.MaxDaysRetention;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearsAmount;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSettingSetMemento;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KmfmtRetentionEmpCtr;

public class JpaUpperLimitEmpSetMemento implements UpperLimitSettingSetMemento{
	
private KmfmtRetentionEmpCtr typeValue;
	
	public JpaUpperLimitEmpSetMemento(KmfmtRetentionEmpCtr typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public void setRetentionYearsAmount(RetentionYearsAmount retentionYearsAmount) {
		this.typeValue.setYearAmount(retentionYearsAmount.v().shortValue());
	}

	@Override
	public void setMaxDaysRetention(MaxDaysRetention maxDaysCumulation) {
		this.typeValue.setMaxDaysRetention(maxDaysCumulation.v().shortValue());
	}

}
