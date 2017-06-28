package nts.uk.ctx.sys.portal.infra.repository.toppagesetting;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageSetting;
import nts.uk.ctx.sys.portal.dom.toppagesetting.TopPageSettingRepository;
import nts.uk.ctx.sys.portal.infra.entity.toppagesetting.CcgptTopPageSetting;
import nts.uk.ctx.sys.portal.infra.entity.toppagesetting.CcgptTopPageSettingPK;

@Stateless
public class JpaTopPageSettingRepository extends JpaRepository implements TopPageSettingRepository {

	public static TopPageSetting toDomain(CcgptTopPageSetting entity) {
		TopPageSetting domain = TopPageSetting.createFromJavaType(entity.ccgptTopPageSettingPK.companyId,
				entity.ctgSet);
		return domain;
	}

	private CcgptTopPageSetting toEntity(TopPageSetting domain) {
		val entity = new CcgptTopPageSetting();

		entity.ccgptTopPageSettingPK = new CcgptTopPageSettingPK();
		entity.ccgptTopPageSettingPK.companyId = domain.getCompanyId();
		entity.ctgSet = domain.getCtgSet().value;
		return entity;
	}

	@Override
	public Optional<TopPageSetting> findByCId(String companyId) {
		CcgptTopPageSettingPK ccgptTopPageSettingPK = new CcgptTopPageSettingPK(companyId);
		return this.queryProxy().find(ccgptTopPageSettingPK, CcgptTopPageSetting.class).map(x -> toDomain(x));
	}

	@Override
	public void insert(TopPageSetting topPageSetting) {
		this.commandProxy().insert(toEntity(topPageSetting));
	}

	@Override
	public void update(TopPageSetting topPageSetting) {
		this.commandProxy().update(toEntity(topPageSetting));
	}
}
