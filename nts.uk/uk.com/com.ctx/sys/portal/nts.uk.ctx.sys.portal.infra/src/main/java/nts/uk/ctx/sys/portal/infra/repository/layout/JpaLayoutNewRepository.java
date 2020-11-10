package nts.uk.ctx.sys.portal.infra.repository.layout;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.layout.LayoutNew;
import nts.uk.ctx.sys.portal.dom.layout.LayoutNewRepository;
import nts.uk.ctx.sys.portal.infra.entity.layout.SptmtLayout;
import nts.uk.ctx.sys.portal.infra.entity.layout.SptmtLayoutPk;
import nts.uk.shr.com.context.AppContexts;

/**
 * 
 * @author NWS-Hieutt
 *
 */
@Stateless
public class JpaLayoutNewRepository extends JpaRepository implements LayoutNewRepository {
	
	private static final String SELECT_BY_CID = "SELECT a FROM SptmtLayout a WHERE a.id.cid =:cid ";
	
	private static final String SELECT_BY_CID_AND_CODE = "SELECT a FROM SptmtLayout a WHERE a.id.cid  =:cid AND a.id.topPageCode =:topPageCode "
			+ "AND a.id.layoutNo =:layoutNo";
	
	private static final String SELECT_BY_CID_AND_TOPPAGECODE = "SELECT a FROM SptmtLayout a WHERE a.id.cid  =:cid AND a.id.topPageCode =:topPageCode ";
	
	private static final String SELECT_BY_CODE = "SELECT a FROM SptmtLayout a WHERE a.id.topPageCode =:topPageCode ";
		
	@Override
	public void insert(LayoutNew domain) {
		SptmtLayout entity = JpaLayoutNewRepository.toEntity(domain);
		entity.setCid(AppContexts.user().companyId());
		entity.setContractCd(AppContexts.user().contractCode());
		// insert
		this.commandProxy().insert(entity);
	}
	
	@Override
	public void update(LayoutNew domain) {
		Optional<SptmtLayout> entity = findByCidAndCode(domain.getCid(), domain.getTopPageCode().toString(), domain.getLayoutNo().v());
		
		if (entity.isPresent()) {
			domain.setMemento(entity.get());
			// Update 
			this.commandProxy().update(entity.get());
		}
	}
	
	@Override
	public void delete(String CompanyId, String topPageCd, BigDecimal layoutNo) {
		SptmtLayoutPk pk = new SptmtLayoutPk(CompanyId, topPageCd, layoutNo);
		// delete
		this.commandProxy().remove(SptmtLayout.class, pk);
		
	}
	
	@Override
	public List<LayoutNew> getByCid(String companyId) {
		return this.queryProxy()
				.query(SELECT_BY_CID, SptmtLayout.class)
				.setParameter("cid", companyId)
				.getList(LayoutNew::createFromMemento);
	}
	
	@Override
	public Optional<LayoutNew> getByCidAndCode(String companyId, String topPageCode, BigDecimal layoutNo) {
		return this.findByCidAndCode(companyId, topPageCode, layoutNo).map(LayoutNew::createFromMemento);
				
	}
	
	@Override
	public void delete(String companyId, String topPageCode, List<BigDecimal> lstLayoutNo) {
		List<SptmtLayoutPk> lstSptmtLayoutPk =  lstLayoutNo.stream().map(x -> new SptmtLayoutPk(companyId, topPageCode, x)).collect(Collectors.toList());
		this.commandProxy().removeAll(SptmtLayoutPk.class, lstSptmtLayoutPk);
		this.getEntityManager().flush();
	}

	@Override
	public List<BigDecimal> getLstLayoutNo(String topPageCd) {
		return this.queryProxy()
		.query(SELECT_BY_CODE, SptmtLayout.class)
		.setParameter("topPageCode", topPageCd)
		.getList(LayoutNew::createFromMemento)
		.stream()
		.map(x -> x.getLayoutNo().v())
		.collect(Collectors.toList());
	}
	
	private Optional<SptmtLayout> findByCidAndCode(String companyId, String topPageCode, BigDecimal layoutNo) {
		return this.queryProxy()
				.query(SELECT_BY_CID_AND_CODE, SptmtLayout.class)
				.setParameter("cid", companyId)
				.setParameter("topPageCode", topPageCode)
				.setParameter("layoutNo", layoutNo)
				.getSingle();
	}
	
	private static SptmtLayout toEntity(LayoutNew domain) {
		SptmtLayout entity = new SptmtLayout();
		domain.setMemento(entity);
		return entity;
	}

	@Override
	public Optional<LayoutNew> getByCidAndCode(String companyId, String topPageCd) {
		return this.queryProxy()
				.query(SELECT_BY_CID_AND_TOPPAGECODE, SptmtLayout.class)
				.setParameter("cid", companyId)
				.setParameter("topPageCode", topPageCd)  
				.getSingle(LayoutNew::createFromMemento);
	}

}
