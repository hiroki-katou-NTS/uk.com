package nts.uk.ctx.core.infra.data.repository.layout;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetail;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetailRepository;
import nts.uk.ctx.pr.proto.infra.entity.layout.QstmtStmtLayoutDetail;

@RequestScoped
public class JpaLayoutMasterDetailRepository extends JpaRepository implements LayoutMasterDetailRepository {

	private final String SELECT_NO_WHERE = "SELECT c FROM QstmtStmtLayoutDetail c";
	private final String SELECT_ALL_DETAILS = SELECT_NO_WHERE
			+ " WHERE c.qstmtStmtLayoutDetailPk.companyCd := companyCd"
			+ " AND c.qstmtStmtLayoutDetailPk.stmtCd := stmtCd"
			+ " AND c.qstmtStmtLayoutDetailPk.strYm := strYm";
	private final String SELECT_DETAIL = SELECT_ALL_DETAILS 
			+ " AND c.qstmtStmtLayoutDetailPk.itemCd := itemCd";
	@Override
	public void add(LayoutMasterDetail layoutMasterDetail) {
		//this.commandProxy().insert();
	}

	@Override
	public void update(String companyCode, int startYm, String stmtCode, int categoryAtr, String autoLineID,
			String itemCode) {
		// TODO Auto-generated method stub
		
	}

	@Override
	public void remove(String companyCode, int startYm, String stmtCode, int categoryAtr, String itemCode) {
		// TODO Auto-generated method stub
		
	}
	/**
	 * find all layout master details
	 */
	@Override
	public List<LayoutMasterDetail> getDetails(String companyCd, 
			String stmtCd, 
			int startYm) {
		return this.queryProxy().query(SELECT_ALL_DETAILS, QstmtStmtLayoutDetail.class)
				.setParameter("companyCd", companyCd)
				.setParameter("stmtCd", stmtCd)
				.setParameter("startYM", startYm)
				.getList(c -> toDomain(c));
	}

	
	
	private static LayoutMasterDetail toDomain(QstmtStmtLayoutDetail entity) {
		val domain = LayoutMasterDetail.createSimpleFromJavaType(
				entity.qstmtStmtLayoutDetailPk.itemCd,
				entity.sumScopeAtr, 
				entity.calcMethod,
				entity.distributeWay,
				entity.distributeSet,
				entity.pWageCd,
				entity.errRangeHighAtr,
				entity.errRangeHigh,
				entity.errRangeLowAtr,
				entity.errRangeLow, 
				entity.alRangeHighAtr,
				entity.alRangeHigh,
				entity.alRangeLowAtr,
				entity.alRangeLow);
		entity.toDomain(domain);
		return domain;
	}

	@Override
	public Optional<LayoutMasterDetail> getDetail(String companyCd,
			String stmtCd, 
			int startYm,
			int categoryAtr, 
			String itemCd) {		
		return this.queryProxy().query(SELECT_DETAIL, QstmtStmtLayoutDetail.class)
				.setParameter("companyCd", companyCd)
				.setParameter("stmtCd", stmtCd)
				.setParameter("startYM", startYm)
				.setParameter("itemCd", itemCd)
				.getSingle(c -> toDomain(c));
				
	}

}
