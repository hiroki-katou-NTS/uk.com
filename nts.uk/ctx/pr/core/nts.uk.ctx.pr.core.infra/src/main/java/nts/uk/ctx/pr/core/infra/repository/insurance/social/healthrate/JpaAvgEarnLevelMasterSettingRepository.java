package nts.uk.ctx.pr.core.infra.repository.insurance.social.healthrate;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.Dependent;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.AvgEarnLevelMasterSetting;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.AvgEarnLevelMasterSettingRepository;

@Dependent
public class JpaAvgEarnLevelMasterSettingRepository extends JpaRepository
		implements AvgEarnLevelMasterSettingRepository {

	@Override
	public void add(AvgEarnLevelMasterSetting level) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(AvgEarnLevelMasterSetting level) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String id, Long version) {
		// TODO Auto-generated method stub

	}

	@Override
	public List<AvgEarnLevelMasterSetting> findAll(String companyCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public Optional<AvgEarnLevelMasterSetting> findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
