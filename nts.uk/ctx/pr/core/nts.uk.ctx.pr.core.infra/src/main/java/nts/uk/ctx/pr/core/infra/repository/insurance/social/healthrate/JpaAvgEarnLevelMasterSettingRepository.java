package nts.uk.ctx.pr.core.infra.repository.insurance.social.healthrate;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.AvgEarnLevelMasterSetting;
import nts.uk.ctx.pr.core.dom.insurance.social.healthrate.AvgEarnLevelMasterSettingRepository;

@Stateless
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
		List<AvgEarnLevelMasterSetting> listAvgEarnLevelMasterSetting = new ArrayList<AvgEarnLevelMasterSetting>();
		listAvgEarnLevelMasterSetting.add(new AvgEarnLevelMasterSetting(1, 1, 1, 58000L, 63000L));
		listAvgEarnLevelMasterSetting.add(new AvgEarnLevelMasterSetting(2, 2, 1, 68000L, 73000L));
		listAvgEarnLevelMasterSetting.add(new AvgEarnLevelMasterSetting(3, 3, 1, 78000L, 83000L));
		listAvgEarnLevelMasterSetting.add(new AvgEarnLevelMasterSetting(4, 4, 1, 88000L, 93000L));
		listAvgEarnLevelMasterSetting.add(new AvgEarnLevelMasterSetting(5, 5, 1, 98000L, 101000L));
		listAvgEarnLevelMasterSetting.add(new AvgEarnLevelMasterSetting(6, 6, 2, 104000L, 121000L));
		return listAvgEarnLevelMasterSetting;
	}

	@Override
	public Optional<AvgEarnLevelMasterSetting> findById(String id) {
		// TODO Auto-generated method stub
		return null;
	}

}
