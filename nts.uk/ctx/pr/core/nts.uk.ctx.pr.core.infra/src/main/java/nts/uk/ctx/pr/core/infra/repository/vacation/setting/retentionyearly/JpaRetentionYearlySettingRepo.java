package nts.uk.ctx.pr.core.infra.repository.vacation.setting.retentionyearly;

import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.vacation.setting.retentionyearly.RetentionYearlySetting;
import nts.uk.ctx.pr.core.dom.vacation.setting.retentionyearly.RetentionYearlySettingRepository;

@Stateless
public class JpaRetentionYearlySettingRepo extends JpaRepository implements RetentionYearlySettingRepository{

	@Override
	public void insert(RetentionYearlySetting setting) {
		// TODO Auto-generated method stub
		
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
	
}
