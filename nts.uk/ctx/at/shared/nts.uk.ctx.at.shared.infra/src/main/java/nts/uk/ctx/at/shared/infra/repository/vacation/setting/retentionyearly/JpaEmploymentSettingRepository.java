package nts.uk.ctx.at.shared.infra.repository.vacation.setting.retentionyearly;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.EmploymentSettingRepository;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KmfmtRetentionEmpCtr;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KmfmtRetentionEmpCtrPK;

@Stateless
public class JpaEmploymentSettingRepository extends JpaRepository implements EmploymentSettingRepository {

	@Override
	public void insert(EmploymentSetting employmentSetting) {
		KmfmtRetentionEmpCtr entity = new KmfmtRetentionEmpCtr();
		employmentSetting.saveToMemento(new JpaEmploymentSettingSetMemento(entity));
		this.commandProxy().insert(entity);
		
	}

	@Override
	public void update(EmploymentSetting employmentSetting) {
		KmfmtRetentionEmpCtr entity = new KmfmtRetentionEmpCtr();
		employmentSetting.saveToMemento(new JpaEmploymentSettingSetMemento(entity));
		this.commandProxy().update(entity);
	}

	@Override
	public void remove(String companyId, String employmentCode) {
		this.commandProxy()
		.remove(KmfmtRetentionEmpCtr.class, new KmfmtRetentionEmpCtrPK(companyId, employmentCode));
	}

	@Override
	public Optional<EmploymentSetting> find(String companyId, String employmentCode) {
		return this.queryProxy()
				.find(new KmfmtRetentionEmpCtrPK(companyId, employmentCode), KmfmtRetentionEmpCtr.class)
				.map(c -> this.toDomain(c));
	}
	
	private EmploymentSetting toDomain(KmfmtRetentionEmpCtr entity) {
		return new EmploymentSetting(new JpaEmploymentSettingGetMemento(entity));
		
	}
}
