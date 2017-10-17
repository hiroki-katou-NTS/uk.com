package repository.person.info.setting;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import entity.person.info.setting.innitvalue.PpemtPersonInitValueSetting;
import entity.person.info.setting.innitvalue.PpemtPersonInitValueSettingPk;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.setting.init.PerInfoInitValueSetting;
import nts.uk.ctx.bs.person.dom.person.setting.init.PerInfoInitValueSettingRepository;

@Stateless
public class JpaPerInfoInitValSetting extends JpaRepository implements PerInfoInitValueSettingRepository {

	private final String SEL_ALL = " SELECT c FROM PpemtPersonInitValueSetting c" + " WHERE c.companyId = :companyId"
			+ " ORDER BY c.settingCode";

	private final String SEL_ALL_HAS_CHILD = " SELECT iv FROM PpemtPersonInitValueSetting iv"
			+ " LEFT JOIN PpemtPersonInitValueSettingCtg ic" + " ON ic.settingId = iv.initValueSettingPk.settingId"
			+ " LEFT JOIN PpemtPerInfoCtg pc" + " ON ic.settingCtgPk.perInfoCtgId = pc.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " AND pc.abolitionAtr=0"
			+ " LEFT JOIN PpemtPerInfoItem pi"
			+ " ON pi.perInfoCtgId= ic.settingCtgPk.perInfoCtgId"
			+ " AND pi.abolitionAtr = 0"
			+ " WHERE iv.companyId = :companyId"
			+ " AND pi.ppemtPerInfoItemPK.perInfoItemDefId != NULL";

	private final String SEL_BY_SET_ID = " SELECT c FROM PpemtPersonInitValueSetting c"
			+ " WHERE c.initValueSettingPk.settingId = :settingId";

	private final String SEL_A_INIT_VAL_SET = " SELECT c FROM FROM PpemtPersonInitValueSetting c"
			+ " WHERE c.companyId = :companyId" + " AND c.settingCode = :setCode";

	private static PerInfoInitValueSetting toDomain(PpemtPersonInitValueSetting entity) {
		PerInfoInitValueSetting domain = PerInfoInitValueSetting.createFromJavaType(entity.initValueSettingPk.settingId,
				entity.companyId, entity.settingCode, entity.settingName);
		return domain;
	}

	private static PpemtPersonInitValueSetting toEntity(PerInfoInitValueSetting domain) {
		PpemtPersonInitValueSetting entity = new PpemtPersonInitValueSetting();
		entity.initValueSettingPk = new PpemtPersonInitValueSettingPk(domain.getInitValueSettingId());
		entity.companyId = domain.getCompanyId();
		entity.settingCode = domain.getSettingCode().v();
		entity.settingName = domain.getSettingName().v();
		return entity;

	}

	@Override
	public List<PerInfoInitValueSetting> getAllInitValueSetting(String companyId) {

		return this.queryProxy().query(SEL_ALL, PpemtPersonInitValueSetting.class).setParameter("companyId", companyId)
				.getList(c -> toDomain(c));
	}

	// sonnlb

	@Override
	public List<PerInfoInitValueSetting> getAllInitValueSettingHasChild(String companyId) {

		return this.queryProxy().query(SEL_ALL_HAS_CHILD, PpemtPersonInitValueSetting.class)
				.setParameter("companyId", companyId).getList(c -> toDomain(c));
	}

	// sonnlb

	@Override
	public Optional<PerInfoInitValueSetting> getDetailInitValSetting(String settingId) {
		this.getEntityManager().flush();
		Optional<PerInfoInitValueSetting> x= this.queryProxy().query(SEL_BY_SET_ID, PpemtPersonInitValueSetting.class)
				.setParameter("settingId", settingId)
				.getSingle(c -> toDomain(c));
		return x;
	}

	@Override
	public Optional<PerInfoInitValueSetting> getDetailInitValSetting(String companyId, String setCode) {
		return this.queryProxy().query(SEL_A_INIT_VAL_SET, PpemtPersonInitValueSetting.class)
				.setParameter("setCode", setCode).setParameter("companyId", companyId).getSingle(c -> toDomain(c));

	}

	@Override
	public void update(PerInfoInitValueSetting domain) {
		Optional<PpemtPersonInitValueSetting> entity = this.queryProxy().find(domain.getInitValueSettingId(),
				PpemtPersonInitValueSetting.class);
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
		this.commandProxy().remove(PpemtPersonInitValueSetting.class,
				new PpemtPersonInitValueSettingPk(initValueSettingId));
	}

}
