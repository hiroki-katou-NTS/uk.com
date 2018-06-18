package nts.uk.ctx.at.request.infra.repository.setting.company.applicationsetting;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ProxyAppSet;
import nts.uk.ctx.at.request.dom.setting.company.applicationapprovalsetting.applicationsetting.ProxyAppSetRepository;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationsetting.KrqstProxyAppSet;
import nts.uk.ctx.at.request.infra.entity.setting.company.applicationsetting.KrqstProxyAppSetPK;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaProxyAppSetRepository extends JpaRepository implements ProxyAppSetRepository{
	private static final String SELECT_NO_WHERE = "SELECT c FROM KrqstProxyAppSet c ";
	private static final String SELECT_BY_COMID = SELECT_NO_WHERE + "WHERE c.krqstProxyAppSetPK.companyId = :companyId";
	private static final String DELETE_BY_COMID = "DELETE FROM KrqstProxyAppSet c WHERE c.krqstProxyAppSetPK.companyId = :companyId";
	
	/**
	 * convert from entity to domain
	 * @param entity
	 * @return
	 * @author yennth
	 */
	private ProxyAppSet toDomain(KrqstProxyAppSet entity){
		ProxyAppSet proxy = ProxyAppSet.createFromJavaType(entity.krqstProxyAppSetPK.companyId, 
															entity.krqstProxyAppSetPK.appType);
		return proxy;
	}
	/**
	 * convert from domain to entity
	 * @param domain
	 * @return
	 * @author yennth
	 */
	private static KrqstProxyAppSet toEntity(ProxyAppSet domain){
		val entity = new KrqstProxyAppSet();
		entity.krqstProxyAppSetPK = new KrqstProxyAppSetPK(domain.getCompanyId(), domain.getAppType().value);
		return entity;
	}
	/**
	 * get all proxy app set
	 * @author yennth
	 */
	@Override
	public List<ProxyAppSet> getAllProxy() {
		String companyId = AppContexts.user().companyId();
		return this.queryProxy().query(SELECT_BY_COMID, KrqstProxyAppSet.class)
				.setParameter("companyId", companyId)
				.getList(c -> toDomain(c));
	}
	/**
	 * get proxy app set by key
	 * @author yennth
	 */
	@Override
	public Optional<ProxyAppSet> getProxy(int appType) {
		String companyId = AppContexts.user().companyId();
		val pk = new KrqstProxyAppSetPK(companyId, appType);
		return this.queryProxy().find(pk, KrqstProxyAppSet.class).map(c -> toDomain(c));
	}
	/**
	 * insert proxy app set
	 * @author yennth
	 */
	@Override
	public void insert(ProxyAppSet proxy) {
		KrqstProxyAppSet entity = toEntity(proxy);
		this.commandProxy().insert(entity);
	}
	/**
	 * delete proxy app set by company Id
	 * @author yennth
	 */
	@Override
	public void delete(String companyId) {
		this.getEntityManager().createQuery(DELETE_BY_COMID)
			.setParameter("companyId", companyId)
			.executeUpdate();
	}
}
