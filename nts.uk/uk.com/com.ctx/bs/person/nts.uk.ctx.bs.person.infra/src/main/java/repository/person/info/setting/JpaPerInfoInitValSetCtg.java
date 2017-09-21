package repository.person.info.setting;

import java.util.List;
import java.util.Optional;

import entity.person.info.setting.BpsstPersonInitValueSettingCtg;
import entity.person.info.setting.BpsstPersonInitValueSettingCtgPk;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.setting.init.category.PerInfoInitValSetCtg;
import nts.uk.ctx.bs.person.dom.person.setting.init.category.PerInfoInitValSetCtgRepository;

public class JpaPerInfoInitValSetCtg extends JpaRepository implements PerInfoInitValSetCtgRepository {

	private static PerInfoInitValSetCtg toDomain(BpsstPersonInitValueSettingCtg entity) {
		PerInfoInitValSetCtg domain = PerInfoInitValSetCtg.createFromJavaType(entity.settingId,
				entity.settingCtgPk.settingCtgId);
		return domain;
	}

	private static BpsstPersonInitValueSettingCtg toEntity(PerInfoInitValSetCtg domain) {
		BpsstPersonInitValueSettingCtg entity = new BpsstPersonInitValueSettingCtg();
		entity.settingCtgPk = new BpsstPersonInitValueSettingCtgPk(domain.getInitValueSettingCtgId());
		entity.settingId = domain.getInitValueSettingId();
		return entity;

	}

	@Override
	public List<PerInfoInitValSetCtg> getAllInitValSetCtg(String initValueSettingId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public PerInfoInitValSetCtg getDetailInitValSetCtg(String initValueSettingId, String initValueSettingCtgId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(PerInfoInitValSetCtg domain) {
		this.commandProxy().insert(toEntity(domain));

	}

	@Override
	public void update(PerInfoInitValSetCtg domain) {
		Optional<BpsstPersonInitValueSettingCtg> entity = this.queryProxy().find(domain.getInitValueSettingCtgId(),
				BpsstPersonInitValueSettingCtg.class);
		try {
			if (entity.isPresent()) {
				this.commandProxy().update(toEntity(domain));
			}
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public void delete(String initValueSettingCtgId) {
		this.commandProxy().remove(BpsstPersonInitValueSettingCtg.class,
				new BpsstPersonInitValueSettingCtgPk(initValueSettingCtgId));

	}

}
