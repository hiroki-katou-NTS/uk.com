package nts.uk.ctx.pereg.infra.repository.person.info.setting;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pereg.dom.person.setting.init.category.InitValSettingCtg;
import nts.uk.ctx.pereg.dom.person.setting.init.category.PerInfoInitValSetCtg;
import nts.uk.ctx.pereg.dom.person.setting.init.category.PerInfoInitValSetCtgRepository;
import nts.uk.ctx.pereg.dom.person.setting.init.category.PerInfoInitValueSettingCtg;
import nts.uk.ctx.pereg.infra.entity.person.info.setting.initvalue.PpemtPersonInitValueSettingCtg;
import nts.uk.ctx.pereg.infra.entity.person.info.setting.initvalue.PpemtPersonInitValueSettingCtgPk;

@Stateless
public class JpaPerInfoInitValSetCtg extends JpaRepository implements PerInfoInitValSetCtgRepository {

	private static final String SEL_ALL_CTG = "SELECT  b.ppemtPerInfoCtgPK.perInfoCtgId, b.categoryName, cm.categoryType, "
			+ " CASE WHEN (c.settingCtgPk.perInfoCtgId) IS NOT NULL  THEN 'True' ELSE 'False' END AS isSetting "
			+ " FROM PpemtPerInfoCtg b " + " INNER JOIN PpemtPerInfoCtgCm cm "
			+ " ON b.categoryCd = cm.ppemtPerInfoCtgCmPK.categoryCd " + " INNER JOIN PpemtPerInfoCtgOrder e "
			+ " ON  b.ppemtPerInfoCtgPK.perInfoCtgId = e.ppemtPerInfoCtgPK.perInfoCtgId " + " AND b.cid = e.cid "
			+ " LEFT JOIN PpemtPersonInitValueSettingCtg c "
			+ " ON  b.ppemtPerInfoCtgPK.perInfoCtgId  = c.settingCtgPk.perInfoCtgId "
			+ " AND c.settingCtgPk.settingId = :settingId " + " WHERE ( b.abolitionAtr = 0 "
			+ " AND cm.categoryParentCd IS NULL" + " AND b.cid =:companyId "
			+ " AND cm.personEmployeeType = 2 " + " AND  cm.categoryType <> 2 " + " AND cm.categoryType <> 5 "
			+ " AND cm.initValMasterObjCls = 1"
			+ " AND ((cm.salaryUseAtr = 1 AND :salaryUseAtr = 1) OR  (cm.personnelUseAtr = 1 AND :personnelUseAtr = 1) OR  (cm.employmentUseAtr = 1 AND :employmentUseAtr = 1))"
			+ " OR (:salaryUseAtr =  0 AND  :personnelUseAtr = 0 AND :employmentUseAtr = 0 ))"
			 + " ORDER BY e.disporder ";
	
	// sonnlb
	private final static String SEL_CTG_BY_SET_ID = "SELECT b.categoryCd, b.categoryName "
			+ " FROM PpemtPersonInitValueSettingCtg c " + " LEFT JOIN PpemtPerInfoCtg b"
			+ " ON c.settingCtgPk.perInfoCtgId = b.ppemtPerInfoCtgPK.perInfoCtgId" + " LEFT JOIN PpemtPerInfoCtgCm cm "
			+ " ON b.categoryCd = cm.ppemtPerInfoCtgCmPK.categoryCd" + " LEFT JOIN PpemtPerInfoCtgOrder e"
			+ " ON c.settingCtgPk.perInfoCtgId = e.ppemtPerInfoCtgPK.perInfoCtgId" + " AND b.cid = e.cid "
			+ " WHERE c.settingCtgPk.settingId = :settingId" + " ORDER BY e.disporder ";

	private final static String SEL_ALL_CTG_BY_SET_ID_1 = " SELECT c FROM PpemtPersonInitValueSettingCtg c"
			+ " WHERE c.settingCtgPk.settingId =:settingId";

	private final static String SEL_CTG_BY_SETID_CTGID = " SELECT c FROM PpemtPersonInitValueSettingCtg c"
			+ " WHERE c.settingCtgPk.settingId =:settingId AND c.settingCtgPk.perInfoCtgId =:perInfoCtgId";

	private final static String DELETE_BY_SETTING_ID = " DELETE FROM PpemtPersonInitValueSettingCtg c"
			+ " WHERE c.settingCtgPk.settingId =:settingId";

	private static PerInfoInitValSetCtg toDomain(PpemtPersonInitValueSettingCtg entity) {
		PerInfoInitValSetCtg domain = PerInfoInitValSetCtg.createFromJavaType(entity.settingCtgPk.settingId,
				entity.settingCtgPk.perInfoCtgId);
		return domain;
	}

	private static PerInfoInitValueSettingCtg toDomain(Object[] entity) {
		PerInfoInitValueSettingCtg domain = new PerInfoInitValueSettingCtg();
		domain.setPerInfoCtgId(String.valueOf(entity[0].toString()));
		domain.setCategoryName(String.valueOf(entity[1].toString()));
		domain.setCategoryType(Integer.valueOf(entity[2].toString()));
		// chua đc setting
		domain.setSetting(false);
		return domain;

	}

	private static PpemtPersonInitValueSettingCtg toEntity(PerInfoInitValSetCtg domain) {
		PpemtPersonInitValueSettingCtg entity = new PpemtPersonInitValueSettingCtg();
		entity.settingCtgPk = new PpemtPersonInitValueSettingCtgPk(domain.getPerInfoCtgId(),
				domain.getInitValueSettingId());
		return entity;

	}

	// sonnlb

	private static InitValSettingCtg toValSettingDomain(Object[] entity) {
		InitValSettingCtg domain = new InitValSettingCtg(String.valueOf(entity[0].toString()),
				String.valueOf(entity[1].toString()));
		return domain;

	}

	@Override
	public List<InitValSettingCtg> getAllCategoryBySetId(String settingId) {
		return this.queryProxy().query(SEL_CTG_BY_SET_ID, Object[].class).setParameter("settingId", settingId)
				.getList(c -> toValSettingDomain(c));
	}

	// sonnlb

	@Override
	public Optional<PerInfoInitValSetCtg> getDetailInitValSetCtg(String settingId, String perInfoCtgId) {
		return this.queryProxy().query(SEL_CTG_BY_SETID_CTGID, PpemtPersonInitValueSettingCtg.class)
				.setParameter("settingId", settingId).setParameter("perInfoCtgId", perInfoCtgId)
				.getSingle(c -> toDomain(c));
	}

	@Override
	public void add(PerInfoInitValSetCtg domain) {
		this.commandProxy().insert(toEntity(domain));
		this.getEntityManager().flush();
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
	public List<PerInfoInitValueSettingCtg> getAllCategory(String companyId, String settingId, int salaryUseAtr, int personnelUseAtr, int employmentUseAtr) {
		
		return this.queryProxy().query(SEL_ALL_CTG, Object[].class)
				.setParameter("companyId", companyId)
				.setParameter("settingId", settingId)
				.setParameter("salaryUseAtr", salaryUseAtr)
				.setParameter("personnelUseAtr", personnelUseAtr)
				.setParameter("employmentUseAtr", employmentUseAtr)
				.getList(c -> toDomain(c));
	}

	@Override
	public void delete(String perInfoCtgId, String settingId) {
		this.commandProxy().remove(PpemtPersonInitValueSettingCtg.class,
				new PpemtPersonInitValueSettingCtgPk(perInfoCtgId, settingId));

	}

	@Override
	public List<PerInfoInitValSetCtg> getAllInitValueCtg(String settingId) {
		return this.queryProxy().query(SEL_ALL_CTG_BY_SET_ID_1, PpemtPersonInitValueSettingCtg.class)
				.setParameter("settingId", settingId).getList(c -> toDomain(c));
	}

	@Override
	public void delete(String settingId) {
		this.getEntityManager().createQuery(DELETE_BY_SETTING_ID).setParameter("settingId", settingId).executeUpdate();
		this.getEntityManager().flush();

	}

	// hoatt
	@Override
	public void addAllCtg(List<PerInfoInitValSetCtg> lstCtg) {
		List<PpemtPersonInitValueSettingCtg> lstEntity = new ArrayList<>();
		for (PerInfoInitValSetCtg perSetCtg : lstCtg) {
			lstEntity.add(toEntity(perSetCtg));
		}
		this.commandProxy().insertAll(lstEntity);
	}

}
