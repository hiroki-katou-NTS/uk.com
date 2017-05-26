package nts.uk.ctx.at.shared.infra.repository.vacation.setting.retentionyearly;

import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.MaxDaysRetention;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearsAmount;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSettingGetMemento;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KmfmtRetentionEmpCtr;

public class JpaUpperLimitEmpGetMemento implements UpperLimitSettingGetMemento {

	private KmfmtRetentionEmpCtr typeValue;
	
	public JpaUpperLimitEmpGetMemento(KmfmtRetentionEmpCtr typeValue) {
		this.typeValue = typeValue;
	}
	
	@Override
	public RetentionYearsAmount getRetentionYearsAmount() {
		return new RetentionYearsAmount((int) this.typeValue.getYearAmount());
	}

	@Override
	public MaxDaysRetention getMaxDaysCumulation() {
		return new MaxDaysRetention((int) this.typeValue.getMaxDaysRetention());
	}

}
