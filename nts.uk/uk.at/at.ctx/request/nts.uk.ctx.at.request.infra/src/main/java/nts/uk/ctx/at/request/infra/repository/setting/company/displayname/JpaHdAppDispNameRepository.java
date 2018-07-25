package nts.uk.ctx.at.request.infra.repository.setting.company.displayname;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.at.request.dom.setting.company.displayname.HdAppDispName;
import nts.uk.ctx.at.request.dom.setting.company.displayname.HdAppDispNameRepository;
import nts.uk.ctx.at.request.infra.entity.setting.company.displayname.KrqmtHdAppDispName;
import nts.uk.ctx.at.request.infra.entity.setting.company.displayname.KrqmtHdAppDispNamePK;
import nts.uk.shr.com.context.AppContexts;

@Stateless
public class JpaHdAppDispNameRepository extends JpaRepository implements HdAppDispNameRepository{
	private static final String SELECT_NO_WHERE = "SELECT c FROM KrqmtHdAppDispName c ";
	private static final String SELECT_BY_CID = SELECT_NO_WHERE + "WHERE c.krqmtHdAppDispNamePK.companyId = :companyId";
	/**
	 * convert from entity to domain
	 * @param entity
	 * @return
	 * @author yennth
	 */
	private HdAppDispName toDomain(KrqmtHdAppDispName entity){
		HdAppDispName domain = HdAppDispName.createFromJavaType(entity.krqmtHdAppDispNamePK.companyId, 
																entity.krqmtHdAppDispNamePK.hdAppType, 
																entity.dispName);
		return domain;
	}
	/**
	 * convert from domain to entity
	 * @param domain
	 * @return
	 */
	private KrqmtHdAppDispName toEntity(HdAppDispName domain){
		val entity = new KrqmtHdAppDispName();
		entity.krqmtHdAppDispNamePK.companyId = domain.getCompanyId();
		entity.krqmtHdAppDispNamePK.hdAppType = domain.getHdAppType().value;
		entity.dispName = domain.getDispName().v();
		return entity;
	}
	/**
	 * get all hd app disp name by company Id
	 * @author yennth
	 */
	@Override
	public List<HdAppDispName> getAllHdApp() {
		String companyId = AppContexts.user().companyId();
		return this.queryProxy().query(SELECT_BY_CID, KrqmtHdAppDispName.class)
				.setParameter("companyId", companyId)
				.getList(c -> toDomain(c));
	}
	/**
	 * get hd app disp name by company id and hd app type
	 * @author yennth
	 */
	@Override
	public Optional<HdAppDispName> getHdApp(int hdAppType) {
		String companyId = AppContexts.user().companyId();
		return this.queryProxy().find(new KrqmtHdAppDispNamePK(companyId, hdAppType), KrqmtHdAppDispName.class)
				.map(c -> toDomain(c));
	}
	/**
	 * update hd app disp name
	 * @author yennth
	 */
	@Override
	public void update(HdAppDispName appDisp) {
		KrqmtHdAppDispName entity = toEntity(appDisp);
		KrqmtHdAppDispName oldEntity = this.queryProxy().find(entity.krqmtHdAppDispNamePK, KrqmtHdAppDispName.class).get();
		oldEntity.dispName = entity.dispName;
		this.commandProxy().update(oldEntity);
	}
	/**
	 * insert hd app disp name
	 * @author yennth
	 */
	@Override
	public void insert(HdAppDispName appDisp) {
		KrqmtHdAppDispName entity = toEntity(appDisp);
		this.commandProxy().insert(entity);
	}
}
