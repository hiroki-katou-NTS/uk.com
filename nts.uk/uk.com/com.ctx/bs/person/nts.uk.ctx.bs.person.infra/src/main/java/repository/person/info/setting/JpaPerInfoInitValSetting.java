package repository.person.info.setting;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import entity.person.info.setting.innitvalue.BpsstPersonInitValueSetting;
import entity.person.info.setting.innitvalue.BpsstPersonInitValueSettingPk;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.setting.init.PerInfoInitValueSetting;
import nts.uk.ctx.bs.person.dom.person.setting.init.PerInfoInitValueSettingRepository;

@Stateless
public class JpaPerInfoInitValSetting extends JpaRepository implements PerInfoInitValueSettingRepository {

	private final String SEL_ALL = " SELECT c FROM BpsstPersonInitValueSetting c" + " WHERE c.companyId = :companyId"
			+ " ORDER BY c.settingCode";

	private final String SEL_BY_SET_ID = " SELECT c FROM BpsstPersonInitValueSetting c"
			+ " WHERE c.bpsstPersonInitValueSettingPk.settingId = :settingId";

	private final String SEL_A_INIT_VAL_SET = " SELECT c FROM FROM BpsstPersonInitValueSetting c"
			+ " WHERE c.companyId = :companyId" + " AND c.settingCode = :setCode";

	private static PerInfoInitValueSetting toDomain(BpsstPersonInitValueSetting entity) {
		PerInfoInitValueSetting domain = PerInfoInitValueSetting.createFromJavaType(
				entity.bpsstPersonInitValueSettingPk.settingId, entity.companyId, entity.settingCode,
				entity.settingName);
		return domain;
	}

	private static BpsstPersonInitValueSetting toEntity(PerInfoInitValueSetting domain) {
		BpsstPersonInitValueSetting entity = new BpsstPersonInitValueSetting();
		entity.bpsstPersonInitValueSettingPk = new BpsstPersonInitValueSettingPk(domain.getInitValueSettingId());
		entity.companyId = domain.getCompanyId();
		entity.settingCode = domain.getSettingCode().v();
		entity.settingName = domain.getSettingName().v();
		return entity;

	}

	@Override
	public List<PerInfoInitValueSetting> getAllInitValueSetting(String companyId) {

		return this.queryProxy().query(SEL_ALL, BpsstPersonInitValueSetting.class).setParameter("companyId", companyId)
				.getList(c -> toDomain(c));
	}

	@Override
	public Optional<PerInfoInitValueSetting> getDetailInitValSetting(String initValueSettingId) {
		return this.queryProxy().query(SEL_BY_SET_ID, BpsstPersonInitValueSetting.class).getSingle(c -> toDomain(c));
	}

	@Override
	public Optional<PerInfoInitValueSetting> getDetailInitValSetting(String companyId, String setCode) {
		return this.queryProxy().query(SEL_A_INIT_VAL_SET, BpsstPersonInitValueSetting.class)
				.setParameter("setCode", setCode).setParameter("companyId", companyId).getSingle(c -> toDomain(c));

	}

	@Override
	public void update(PerInfoInitValueSetting domain) {
		Optional<BpsstPersonInitValueSetting> entity = this.queryProxy().find(domain.getInitValueSettingId(),
				BpsstPersonInitValueSetting.class);
		try {
			if (entity.isPresent()) {
				this.commandProxy().update(toEntity(domain));
			}
		} catch (Exception e) {
			throw e;
		}

	}

	@Override
	public void insert(PerInfoInitValueSetting domain) {
		this.commandProxy().insert(toEntity(domain));

	}

	@Override
	public void delete(String initValueSettingId) {
		this.commandProxy().remove(BpsstPersonInitValueSetting.class, new BpsstPersonInitValueSettingPk(initValueSettingId));
	}

}
