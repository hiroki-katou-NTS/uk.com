package nts.uk.ctx.core.infra.data.repository.layout;

import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.core.dom.company.Company;
import nts.uk.ctx.core.infra.data.entity.SmpmtCompany;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMaster;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMasterCategory;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMasterLine;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMasterRepository;
import nts.uk.ctx.pr.proto.infra.entity.layout.QstmtStmtLayoutHead;

public class JpaLayoutMasterRepository extends JpaRepository implements LayoutMasterRepository{

	private final String SELECT_NO_WHERE = "SELECT c FROM QstmtStmtLayoutHead c";
	private final String FIND_BY_CODE = SELECT_NO_WHERE 
			+ " WHERE c.qstmtStmtLayoutHeadPK.companyCd = :companyCd"
			+ " AND c.qstmtStmtLayoutHeadPK.stmtCd = :stmtCd"
			+ " AND c.qstmtStmtLayoutHeadPK.strYm = :strYm";

	private static LayoutMaster toDomain(QstmtStmtLayoutHead entity) {
		val domain = LayoutMaster.createSimpleFromJavaType(
				entity.qstmtStmtLayoutHeadPK.companyCd, 
				entity.qstmtStmtLayoutHeadPK.strYm, 
				entity.qstmtStmtLayoutHeadPK.stmtCd, 
				entity.endYm, 
				entity.layoutAtr, 
				entity.stmtName);
		
		entity.toDomain(domain);
		
		return domain;
	}
	
	@Override
	public Optional<LayoutMaster> find(String companyCode, String layoutMaster, int strYm) {
		
		return this.queryProxy().query(FIND_BY_CODE, QstmtStmtLayoutHead.class)
				.setParameter("companyCd", companyCode)
				.setParameter("stmtCd", layoutMaster)
				.setParameter("strYm", strYm)
				.getSingle(c -> toDomain(c));
	}

	@Override
	public List<LayoutMaster> findAll(String companyCode) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(LayoutMaster layoutMaster) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(LayoutMaster layoutMaster) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String companyCode, String layoutCode, int startYm) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<LayoutMasterCategory> findCategory(String companyCode, String layoutCode, int startDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addCategory(LayoutMasterCategory layoutMasterCategory) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void update(LayoutMasterCategory layoutMasterCategory) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void delete(String companyCode, String layoutCode, int startDate, String categoryCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public Optional<LayoutMasterLine> findLine(String companyCode, String layoutCode, int startDate) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void addLine(LayoutMasterLine layoutMasterLine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void updateLine(LayoutMasterLine layoutMasterLine) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void removeLine(String companyCode, String layoutCode, int startDate, String categoryCode,
			String autoLineId) {
		// TODO Auto-generated method stub
		
	}

	

}
