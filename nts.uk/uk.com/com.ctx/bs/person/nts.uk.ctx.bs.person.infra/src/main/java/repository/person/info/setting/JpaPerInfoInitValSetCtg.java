package repository.person.info.setting;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import entity.person.info.setting.innitvalue.BpsstPersonInitValueSettingCtg;
import entity.person.info.setting.innitvalue.BpsstPersonInitValueSettingCtgPk;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.bs.person.dom.person.setting.init.category.PerInfoInitValSetCtg;
import nts.uk.ctx.bs.person.dom.person.setting.init.category.PerInfoInitValSetCtgRepository;

@Stateless
public class JpaPerInfoInitValSetCtg extends JpaRepository implements PerInfoInitValSetCtgRepository {
	
	private final String SEL_CTG_BY_SET_ID = "SELECT c FROM BpsstPersonInitValueSettingCtg c "
			+ " INNER JOIN PpemtPerInfoCtg b"
			+ " ON c.settingCtgPk.perInfoCtgId = b.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " INNER JOIN PpemtPerInfoCtgCm cm "
			+ " ON b.categoryCd = cm.ppemtPerInfoCtgCmPK.categoryCd"
			+ " INNER JOIN PpemtPerInfoCtgOrder e"
			+ " ON c.settingCtgPk.perInfoCtgId = e.ppemtPerInfoCtgPK.perInfoCtgId"
			+ " AND b.cid = e.cid "
			+ " WHERE b.abolitionAtr = 0 "
			+ " AND cm.personEmployeeType = 2 "
			+ " AND cm.categoryType >< 2 "
			+ " AND cm.categoryType >< 5 "
			+ " AND cm.categoryParentCd IS NULL"
			+ " AND b.cid = : companyId"
			+ " ORDER BY e.disporder ";
	
	
	private final String SEL_ALL_CTG = "SELECT b  FROM PpemtPerInfoCtg b "
			+ " INNER JOIN PpemtPerInfoCtgCm cm "
			+ " ON b.categoryCd = cm.ppemtPerInfoCtgCmPK.categoryCd "
			+ " INNER JOIN PpemtPerInfoCtgOrder e "
			+ " ON b.ppemtPerInfoCtgPK.perInfoCtgId = e.ppemtPerInfoCtgPK.perInfoCtgId "
			+ " AND b.cid = e.cid "
			+ " LEFT JOIN BpsstPersonInitValueSettingCtg c "
			+ " ON c.settingCtgPk.perInfoCtgId = b.ppemtPerInfoCtgPK.perInfoCtgId "
			+ " WHERE b.abolitionAtr = 0 "
			+ " AND cm.personEmployeeType = 2 "
			+ " AND cm.categoryType >< 2 "
			+ " AND cm.categoryType >< 5 "
			+ " AND cm.categoryParentCd IS NULL"
			+ " AND b.cid = : companyId"
			+ " ORDER BY e.disporder ";
	

	private static PerInfoInitValSetCtg toDomain(BpsstPersonInitValueSettingCtg entity) {
		PerInfoInitValSetCtg domain = PerInfoInitValSetCtg.createFromJavaType(entity.settingId,
				entity.settingCtgPk.perInfoCtgId);
		return domain;
	}

	private static BpsstPersonInitValueSettingCtg toEntity(PerInfoInitValSetCtg domain) {
		BpsstPersonInitValueSettingCtg entity = new BpsstPersonInitValueSettingCtg();
		entity.settingCtgPk = new BpsstPersonInitValueSettingCtgPk(domain.getPerInfoCtgId());
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
		Optional<BpsstPersonInitValueSettingCtg> entity = this.queryProxy().find(domain.getPerInfoCtgId(),
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
