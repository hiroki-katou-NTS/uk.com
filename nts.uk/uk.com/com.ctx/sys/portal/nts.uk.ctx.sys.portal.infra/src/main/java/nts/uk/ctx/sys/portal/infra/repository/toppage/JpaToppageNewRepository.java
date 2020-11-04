package nts.uk.ctx.sys.portal.infra.repository.toppage;

import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.toppage.ToppageNew;
import nts.uk.ctx.sys.portal.dom.toppage.ToppageNewRepository;
import nts.uk.ctx.sys.portal.infra.entity.toppage.SptmtToppage;
import nts.uk.ctx.sys.portal.infra.entity.toppage.SptmtToppagePk;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author NWS-Hieutt
 *
 */
@Stateless
public class JpaToppageNewRepository extends JpaRepository implements ToppageNewRepository {
	
	private static final String SELECT_BY_CID = "SELECT a FROM SptmtToppage a WHERE a.id.cid =:cid ";
	
	private static final String SELECT_BY_CID_AND_CODE = "SELECT a FROM SptmtToppage a WHERE a.id.cid =:cid AND a.id.topPageCode =:topPageCode";
	
	@Override
	public void insert(ToppageNew domain) {
		SptmtToppage entity = JpaToppageNewRepository.toEntity(domain);
		entity.setContractCd(AppContexts.user().contractCode());
		// insert
		this.commandProxy().insert(entity);
	}
	
	@Override
	public void update(ToppageNew domain) {
		SptmtToppage entity = JpaToppageNewRepository.toEntity(domain);
		SptmtToppagePk pk = new SptmtToppagePk(entity.getCid(), entity.getTopPageCode());
		SptmtToppage oldEntity = this.queryProxy().find(pk, SptmtToppage.class).get();
		oldEntity.setLayoutDisp(entity.getLayoutDisp());
		oldEntity.setTopPageName(entity.getTopPageName());
		// update
		this.commandProxy().update(oldEntity);
	}
	
	@Override
	public void delete(String CompanyId, String topPageCode) {
		SptmtToppagePk pk = new SptmtToppagePk(CompanyId, topPageCode);
		// delete
		this.commandProxy().remove(SptmtToppage.class, pk);
		
	}
	
	@Override
	public List<ToppageNew> getByCid(String companyId) {
		return this.queryProxy()
				.query(SELECT_BY_CID, SptmtToppage.class)
				.setParameter("cid", companyId)
				.getList(ToppageNew::createFromMemento);
	}
	
	@Override
	public Optional<ToppageNew> getByCidAndCode(String companyId, String topPageCode) {
		return this.queryProxy()
				.query(SELECT_BY_CID_AND_CODE, SptmtToppage.class)
				.setParameter("cid", companyId)
				.setParameter("topPageCode", topPageCode)
				.getSingle(ToppageNew::createFromMemento);
	}
	
	private static SptmtToppage toEntity(ToppageNew domain) {
		SptmtToppage entity = new SptmtToppage();
		domain.setMemento(entity);
		return entity;
	}
}
