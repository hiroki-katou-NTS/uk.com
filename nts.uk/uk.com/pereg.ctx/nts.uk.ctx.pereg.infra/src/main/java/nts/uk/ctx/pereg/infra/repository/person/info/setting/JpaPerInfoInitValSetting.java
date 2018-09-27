package nts.uk.ctx.pereg.infra.repository.person.info.setting;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.person.setting.init.PerInfoInitValueSetting;
import nts.uk.ctx.pereg.dom.person.setting.init.PerInfoInitValueSettingRepository;
import nts.uk.ctx.pereg.infra.entity.person.info.setting.initvalue.PpemtPersonInitValueSetting;
import nts.uk.ctx.pereg.infra.entity.person.info.setting.initvalue.PpemtPersonInitValueSettingPk;

@Stateless
public class JpaPerInfoInitValSetting extends JpaRepository implements PerInfoInitValueSettingRepository {

	private static final String SEL_ALL = " SELECT c FROM PpemtPersonInitValueSetting c" + " WHERE c.companyId = :companyId"
			+ " ORDER BY c.settingCode";

	private static final String SEL_ALL_HAS_CHILD = "SELECT DISTINCT iv"
			+ " FROM PpemtPersonInitValueSetting iv"
			+ " INNER JOIN PpemtPersonInitValueSettingCtg ic" 
			+ " ON iv.initValueSettingPk.settingId  = ic.settingCtgPk.settingId"
			+ " INNER JOIN PpemtPerInfoCtg pc" 
			+ " ON ic.settingCtgPk.perInfoCtgId = pc.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " AND pc.abolitionAtr=0" 
			+ " INNER JOIN PpemtPerInfoItem pi"
			+ " ON ic.settingCtgPk.perInfoCtgId= pi.perInfoCtgId " 
			+ " AND pi.abolitionAtr = 0"
			+ " WHERE iv.companyId = :companyId" 
			+ " AND pi.ppemtPerInfoItemPK.perInfoItemDefId IS NOT NULL ORDER BY iv.settingCode ASC";

	private static final String SEL_BY_SET_ID = " SELECT c FROM PpemtPersonInitValueSetting c"
			+ " WHERE c.initValueSettingPk.settingId = :settingId";

	private static final String SEL_A_INIT_VAL_SET = " SELECT c FROM PpemtPersonInitValueSetting c"
			+ " WHERE c.companyId = :companyId" + " AND c.settingCode = :setCode";

	private static final String SEL_A_INIT_VAL_SET_1 = " SELECT c FROM PpemtPersonInitValueSetting c"
			+ " WHERE c.companyId = :companyId" + " AND c.settingCode = :settingCode"
			+ " AND c.initValueSettingPk.settingId = :settingId ";

	private static final String DELETE_BY_SETTINGCD_SETTINGID = " DELETE FROM PpemtPersonInitValueSetting c"
			+ " WHERE c.initValueSettingPk.settingId =:settingId AND c.settingCode =:settingCode"
			+ " AND c.companyId =:companyId";
	
	private static final String UPDATE_NAME_BY_SETTING_ID = " UPDATE PpemtPersonInitValueSetting c SET c.settingName =:settingName"
			+ " WHERE c.initValueSettingPk.settingId =:settingId";

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
		
		Optional<PerInfoInitValueSetting> x = this.queryProxy().query(SEL_BY_SET_ID, PpemtPersonInitValueSetting.class)
				.setParameter("settingId", settingId).getSingle(c -> toDomain(c));
		return x;
	}

	@Override
	public Optional<PerInfoInitValueSetting> getDetailInitValSetting(String companyId, String setCode) {
		return this.queryProxy().query(SEL_A_INIT_VAL_SET, PpemtPersonInitValueSetting.class)
				.setParameter("setCode", setCode).setParameter("companyId", companyId).getSingle(c -> toDomain(c));

	}

	@Override
	public void update(PerInfoInitValueSetting domain) {
		PpemtPersonInitValueSettingPk key =new PpemtPersonInitValueSettingPk(domain.getInitValueSettingId());
		Optional<PpemtPersonInitValueSetting> entity = this.queryProxy().find(key,
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
	public void delete(String companyId, String settingId, String settingCode) {
		this.getEntityManager().createQuery(DELETE_BY_SETTINGCD_SETTINGID)
							   .setParameter("companyId", companyId)
							   .setParameter("settingId", settingId)
							   .setParameter("settingCode", settingCode)
							   .executeUpdate();
		this.getEntityManager().flush();
	}

	@Override
	public Optional<PerInfoInitValueSetting> getDetailInitValSetting(String companyId, String settingCode,
			String settingId) {
		return this.queryProxy().query(SEL_A_INIT_VAL_SET_1, PpemtPersonInitValueSetting.class)
				.setParameter("companyId", companyId).setParameter("settingCode", settingCode)
				.setParameter("settingId", settingId).getSingle(c -> toDomain(c));
	}

	@Override
	public void updateName(String settingId, String settingName) {
		this.getEntityManager().createQuery(UPDATE_NAME_BY_SETTING_ID)
		.setParameter("settingId", settingId)
		.setParameter("settingName", settingName)
		.executeUpdate();
		
	}

}
