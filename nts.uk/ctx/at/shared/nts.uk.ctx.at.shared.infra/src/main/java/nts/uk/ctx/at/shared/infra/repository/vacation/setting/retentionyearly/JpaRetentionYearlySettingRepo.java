package nts.uk.ctx.at.shared.infra.repository.vacation.setting.retentionyearly;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySetting;
import nts.uk.ctx.at.shared.dom.vacation.setting.retentionyearly.RetentionYearlySettingRepository;
import nts.uk.ctx.at.shared.infra.entity.vacation.setting.KmfmtRetentionYearly;

@Stateless
public class JpaRetentionYearlySettingRepo extends JpaRepository implements RetentionYearlySettingRepository{

	@Override
	public void insert(RetentionYearlySetting setting) {
		this.commandProxy().insert(this.toEntity(setting));
	}

	@Override
	public void update(RetentionYearlySetting setting) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<RetentionYearlySetting> findByCompanyId(String companyId) {
		// TODO Auto-generated method stub
		return null;
	}
	
	private KmfmtRetentionYearly toEntity(RetentionYearlySetting retentionYearlySetting) {
		KmfmtRetentionYearly entity = new KmfmtRetentionYearly();
		retentionYearlySetting.saveToMemento(new JpaRetentionYearlySetMemento(entity));
		return entity;
	}
	
}
