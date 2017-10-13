package repository.person.info.setting;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import entity.person.info.setting.innitvalue.PpemtPersonInitValueSettingCtg;
import entity.person.info.setting.innitvalue.PpemtPersonInitValueSettingCtgPk;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.setting.init.category.PerInfoInitValSetCtg;
import nts.uk.ctx.bs.person.dom.person.setting.init.category.PerInfoInitValSetCtgRepository;
import nts.uk.ctx.bs.person.dom.person.setting.init.category.PerInfoInitValueSettingCtg;

@Stateless
public class JpaPerInfoInitValSetCtg extends JpaRepository implements PerInfoInitValSetCtgRepository {

	private final String SEL_CTG_BY_SET_ID = "SELECT c FROM PpemtPersonInitValueSettingCtg c "
			+ " INNER JOIN PpemtPerInfoCtg b" + " ON c.settingCtgPk.perInfoCtgId = b.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " INNER JOIN PpemtPerInfoCtgCm cm " + " ON b.categoryCd = cm.ppemtPerInfoCtgCmPK.categoryCd"
			+ " INNER JOIN PpemtPerInfoCtgOrder e"
			+ " ON c.settingCtgPk.perInfoCtgId = e.ppemtPerInfoCtgPK.perInfoCtgId" + " AND b.cid = e.cid "
			+ " WHERE b.abolitionAtr = 0 " + " AND cm.personEmployeeType = 2 " + " AND cm.categoryType >< 2 "
			+ " AND cm.categoryType >< 5 " + " AND cm.categoryParentCd IS NULL" + " AND b.settingId = : settingId"
			+ " ORDER BY e.disporder ";

	private final String SEL_ALL_CTG = "SELECT b.ppemtPerInfoCtgPK.perInfoCtgId, b.categoryName, "
			+ " CASE WHEN (c.settingCtgPk.perInfoCtgId) IS NOT NULL  THEN 'True' ELSE 'False' END AS isSetting "
			+ " FROM PpemtPerInfoCtg b " + " INNER JOIN PpemtPerInfoCtgCm cm "
			+ " ON b.categoryCd = cm.ppemtPerInfoCtgCmPK.categoryCd " + " INNER JOIN PpemtPerInfoCtgOrder e "
			+ " ON  b.ppemtPerInfoCtgPK.perInfoCtgId = e.ppemtPerInfoCtgPK.perInfoCtgId " + " AND b.cid = e.cid "
			+ " LEFT JOIN PpemtPersonInitValueSettingCtg c "
			+ " ON  b.ppemtPerInfoCtgPK.perInfoCtgId  = c.settingCtgPk.perInfoCtgId " + " WHERE ( b.abolitionAtr = 0 "
			+ " AND cm.personEmployeeType = 2 " + " AND ( cm.categoryType <> 2 " + " AND cm.categoryType <> 5 )"
			+ " AND cm.categoryParentCd IS NULL" + " AND b.cid =:companyId )" + " ORDER BY e.disporder ";

	private static PerInfoInitValSetCtg toDomain(PpemtPersonInitValueSettingCtg entity) {
		PerInfoInitValSetCtg domain = PerInfoInitValSetCtg.createFromJavaType(entity.settingId,
				entity.settingCtgPk.perInfoCtgId);
		return domain;
	}

	private static PerInfoInitValueSettingCtg toDomain(Object[] entity) {
		PerInfoInitValueSettingCtg domain = new PerInfoInitValueSettingCtg();
		domain.setPerInfoCtgId(String.valueOf(entity[0].toString()));
		domain.setCategoryName(String.valueOf(entity[1].toString()));
		domain.setSetting(Boolean.valueOf(entity[2].toString()));
		return domain;

	}

	private static PpemtPersonInitValueSettingCtg toEntity(PerInfoInitValSetCtg domain) {
		PpemtPersonInitValueSettingCtg entity = new PpemtPersonInitValueSettingCtg();
		entity.settingCtgPk = new PpemtPersonInitValueSettingCtgPk(domain.getPerInfoCtgId());
		entity.settingId = domain.getInitValueSettingId();
		return entity;

	}

	// sonnlb
	@Override
	public List<PerInfoInitValueSettingCtg> getAllCategoryBySetId(String settingId) {
		return this.queryProxy().query(SEL_CTG_BY_SET_ID, Object[].class).setParameter("settingId", settingId)
				.getList(c -> toDomain(c));
	}

	// sonnlb

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
		Optional<PpemtPersonInitValueSettingCtg> entity = this.queryProxy().find(domain.getPerInfoCtgId(),
				PpemtPersonInitValueSettingCtg.class);
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
		this.commandProxy().remove(PpemtPersonInitValueSettingCtg.class,
				new PpemtPersonInitValueSettingCtgPk(initValueSettingCtgId));

	}

	@Override
	public List<PerInfoInitValueSettingCtg> getAllCategory(String companyId) {
		return this.queryProxy().query(SEL_ALL_CTG, Object[].class).setParameter("companyId", companyId)
				.getList(c -> toDomain(c));
	}

}
