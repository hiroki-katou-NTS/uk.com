package nts.uk.ctx.at.request.infra.repository.setting.company.applicationcommonsetting;

import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationcommonsetting.AppCommonSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationcommonsetting.AppCommonSetRepository;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationcommonsetting.KrqstAppCommonSet;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaAppCommonSetRepository extends JpaRepository implements AppCommonSetRepository{
	/**
	 * convert from entity to domain
	 * @param entity
	 * @return
	 */
	private AppCommonSet toDomain(KrqstAppCommonSet entity){
		AppCommonSet domain = AppCommonSet.createFromJavaType(entity.companyId, entity.showWkpNameBelong);
		return domain;
	}
	/**
	 * convert from domain to entity
	 * @param domain
	 * @return
	 * @author yennth
	 */
	private static KrqstAppCommonSet toEntity(AppCommonSet domain){
		val entity = new KrqstAppCommonSet();
		entity.companyId = domain.getCompanyId();
		entity.showWkpNameBelong = domain.getShowWkpNameBelong().value;
		return entity;
	}
	/**
	 * find app common set by companyId
	 * @author yennth
	 */
	@Override
	public Optional<AppCommonSet> find() {
		String companyId = AppContexts.user().companyId();
		return this.queryProxy().find(companyId, KrqstAppCommonSet.class).map(x -> toDomain(x));
	}
	/**
	 * update app common set
	 * @author yennth
	 */
	@Override
	public void update(AppCommonSet appCommon) {
		KrqstAppCommonSet entity = toEntity(appCommon);
		KrqstAppCommonSet oldEntity = this.queryProxy().find(entity.companyId, KrqstAppCommonSet.class).get();
		oldEntity.showWkpNameBelong = entity.showWkpNameBelong;
		this.commandProxy().update(oldEntity);
	}
	/**
	 * insert app common set
	 * @author yennth
	 */
	@Override
	public void insert(AppCommonSet appCommon) {
		KrqstAppCommonSet entity = toEntity(appCommon);
		this.commandProxy().insert(entity);
	}

}
