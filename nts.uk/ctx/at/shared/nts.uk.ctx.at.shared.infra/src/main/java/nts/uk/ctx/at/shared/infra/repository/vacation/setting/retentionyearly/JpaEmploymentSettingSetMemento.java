package nts.uk.ctx.at.shared.infra.repository.vacation.setting.retentionyearly;

import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingSetMemento;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.ManagementCategory;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.UpperLimitSetting;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KmfmtRetentionEmpCtr;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KmfmtRetentionEmpCtrPK;

public class JpaEmploymentSettingSetMemento implements EmploymentSettingSetMemento{

	private KmfmtRetentionEmpCtr typeValue;

	public JpaEmploymentSettingSetMemento(KmfmtRetentionEmpCtr typeValue) {
		this.typeValue = typeValue;
	}
	
	@Override
	public void setCompanyId(String companyId) {
		KmfmtRetentionEmpCtrPK retentionEmpPK = new KmfmtRetentionEmpCtrPK();
		retentionEmpPK.setCid(companyId);
		this.typeValue.setKmfmtRetentionEmpCtrPK(retentionEmpPK);
	}

	@Override
	public void setEmploymentCode(String employmentCode) {
		KmfmtRetentionEmpCtrPK retentionEmpPK = new KmfmtRetentionEmpCtrPK();
		retentionEmpPK.setEmpCtrCd(employmentCode);
		this.typeValue.setKmfmtRetentionEmpCtrPK(retentionEmpPK);
	}

	@Override
	public void setUpperLimitSetting(UpperLimitSetting upperLimitSetting) {
		this.typeValue.setMaxDaysRetention(upperLimitSetting.getMaxDaysCumulation().v().shortValue());
		this.typeValue.setYearAmount(upperLimitSetting.getRetentionYearsAmount().v().shortValue());
	}

	@Override
	public void setManagementCategory(ManagementCategory managementCategory) {
		this.typeValue.setManagementCtrAtr((short) managementCategory.value);
	}

}
