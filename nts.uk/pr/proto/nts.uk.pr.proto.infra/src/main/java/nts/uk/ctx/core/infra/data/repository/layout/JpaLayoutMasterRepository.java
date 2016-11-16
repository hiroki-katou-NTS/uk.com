package nts.uk.ctx.core.infra.data.repository.layout;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMaster;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMasterRepository;
import nts.uk.ctx.pr.proto.infra.entity.layout.QstmtStmtLayoutHead;

@RequestScoped
public class JpaLayoutMasterRepository extends JpaRepository implements LayoutMasterRepository{

	private final String SELECT_NO_WHERE = "SELECT c FROM QstmtStmtLayoutHead c";
	private final String SELECT_ALL_DETAILS = SELECT_NO_WHERE 
			+ " WHERE c.qstmtStmtLayoutHeadPK.companyCd = :companyCd"
			+ " AND c.qstmtStmtLayoutHeadPK.stmtCd = :stmtCd"
			+ " AND c.qstmtStmtLayoutHeadPK.strYm = :strYm";

	private final LayoutMaster toDomain(QstmtStmtLayoutHead entity) {
		val domain = LayoutMaster.createFromJavaType(
				entity.qstmtStmtLayoutHeadPK.companyCd, 
				entity.qstmtStmtLayoutHeadPK.strYm, 
				entity.qstmtStmtLayoutHeadPK.stmtCd, 
				entity.endYm, 
				entity.layoutAtr, 
				entity.stmtName);
		
		entity.toDomain(domain);

		return domain;
	}
	
	private static QstmtStmtLayoutHead toEntity(LayoutMaster domain){
		val entity = new QstmtStmtLayoutHead();
		
		entity.fromDomain(domain);
		
		entity.qstmtStmtLayoutHeadPK.companyCd = domain.getCompanyCode().v();
		entity.qstmtStmtLayoutHeadPK.stmtCd = domain.getStmtCode().v();
		entity.qstmtStmtLayoutHeadPK.strYm = domain.getStartYM().v();
		entity.endYm = domain.getEndYM().v();
		entity.layoutAtr = domain.getLayoutAtr().value;
		entity.stmtName = domain.getStmtName().v();
		
		return entity;
	}
	
	@Override
	public Optional<LayoutMaster> getLayout(String companyCode, String layoutMaster, int strYm) {
		
		return this.queryProxy().query(SELECT_NO_WHERE, QstmtStmtLayoutHead.class)
				.setParameter("companyCd", companyCode)
				.setParameter("stmtCd", layoutMaster)
				.setParameter("strYm", strYm)
				.getSingle(c -> toDomain(c));
	}
	


	@Override
	public List<LayoutMaster> getLayouts(String companyCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(LayoutMaster layoutMaster) {
		this.commandProxy().insert(toEntity(layoutMaster));		
	}

	@Override
	public void update(LayoutMaster layoutMaster) {
		this.commandProxy().update(toEntity(layoutMaster));
		
	}

	@Override
	public void remove(String companyCode, String layoutCode, int startYm) {
		
		
	}

	@Override
	public boolean isExist(String companyCode, String stmtCode) {
		// TODO Auto-generated method stub
		return false;
	}

	

}
