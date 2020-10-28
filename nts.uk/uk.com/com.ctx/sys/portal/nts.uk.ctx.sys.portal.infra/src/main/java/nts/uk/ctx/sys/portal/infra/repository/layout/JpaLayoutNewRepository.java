package nts.uk.ctx.sys.portal.infra.repository.layout;

import java.math.BigDecimal;
import java.util.List;
import java.util.Optional;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.sys.portal.dom.layout.LayoutNew;
import nts.uk.ctx.sys.portal.dom.layout.LayoutNewRepository;
import nts.uk.ctx.sys.portal.infra.entity.layout.SptmtLayout;
import nts.uk.ctx.sys.portal.infra.entity.layout.SptmtLayoutPk;

/**
 * 
 * @author NWS-Hieutt
 *
 */
@Stateless
public class JpaLayoutNewRepository extends JpaRepository implements LayoutNewRepository {
	
	private static final String SELECT_BY_CID = "SELECT a FROM SptmtLayout a WHERE a.id.cid =: cid ";
	
	private static final String SELECT_BY_CID_AND_CODE = "SELECT a FROM SptmtLayout a WHERE a.id.cid =: cid AND a.id.topPageCode =: topPageCode "
			+ "AND a.id.layoutNo =: layoutNo";
	
	@Override
	public void insert(LayoutNew domain) {
		SptmtLayout entity = JpaLayoutNewRepository.toEntity(domain);
		// insert
		this.commandProxy().insert(entity);
	}
	
	@Override
	public void update(LayoutNew domain) {
		SptmtLayout entity = JpaLayoutNewRepository.toEntity(domain);
		SptmtLayoutPk pk = new SptmtLayoutPk(entity.getCid(), entity.getTopPageCode(),entity.getLayoutNo());
		SptmtLayout oldEntity = this.queryProxy().find(pk, SptmtLayout.class).get();
		oldEntity.setExclusVer(entity.getExclusVer());
		oldEntity.setContractCd(entity.getContractCd());
		oldEntity.setLayoutType(entity.getLayoutType());
		oldEntity.setFlowMenuCd(entity.getFlowMenuCd());
		oldEntity.setUrl(entity.getUrl());
		oldEntity.setFlowMenuUpCd(entity.getFlowMenuUpCd());
		oldEntity.setWidgetSettings(entity.getWidgetSettings());
		
		// update
		this.commandProxy().update(oldEntity);
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
		return this.queryProxy()
				.query(SELECT_BY_CID_AND_CODE, SptmtLayout.class)
				.setParameter("cid", companyId)
				.setParameter("topPageCode", topPageCode)
				.setParameter("layoutNo", layoutNo)
				.getSingle(LayoutNew::createFromMemento);
	}
	
	private static SptmtLayout toEntity(LayoutNew domain) {
		SptmtLayout entity = new SptmtLayout();
		domain.setMemento(entity);
		return entity;
	}
}
