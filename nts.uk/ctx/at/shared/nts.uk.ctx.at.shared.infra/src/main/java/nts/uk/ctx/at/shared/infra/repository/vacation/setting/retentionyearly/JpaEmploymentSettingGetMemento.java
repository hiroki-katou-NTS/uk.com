package nts.uk.ctx.at.shared.infra.repository.vacation.setting.retentionyearly;

import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingGetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.ManagementCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSetting;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KmfmtRetentionEmpCtr;

public class JpaEmploymentSettingGetMemento implements EmploymentSettingGetMemento{
	
	private KmfmtRetentionEmpCtr typeValue;
	
	public JpaEmploymentSettingGetMemento(KmfmtRetentionEmpCtr typeValue) {
		this.typeValue = typeValue;
	}

	@Override
	public String getCompanyId() {
		return this.typeValue.getKmfmtRetentionEmpCtrPK().getCid();
	}

	@Override
	public String getEmploymentCode() {
		return this.typeValue.getKmfmtRetentionEmpCtrPK().getEmpCtrCd();
	}

	@Override
	public UpperLimitSetting getUpperLimitSetting() {
		return new UpperLimitSetting(new JpaUpperLimitEmpGetMemento(this.typeValue));
	}

	@Override
	public ManagementCategory getManagementCategory() {
		return ManagementCategory.valueOf((int)this.typeValue.getManagementCtrAtr());
	}

}
