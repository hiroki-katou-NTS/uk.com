package nts.uk.ctx.core.infra.data.repository.layout;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetail;
import nts.uk.ctx.pr.proto.dom.layout.detail.LayoutMasterDetailRepository;
import nts.uk.ctx.pr.proto.infra.entity.layout.QstmtStmtLayoutDetail;
import nts.uk.ctx.pr.proto.infra.entity.layout.QstmtStmtLayoutDetailPK;

@RequestScoped
public class JpaLayoutMasterDetailRepository extends JpaRepository implements LayoutMasterDetailRepository {

	private final String SELECT_NO_WHERE = "SELECT c FROM QstmtStmtLayoutDetail c";
	private final String SELECT_ALL_DETAILS = SELECT_NO_WHERE
			+ " WHERE c.qstmtStmtLayoutDetailPk.companyCd := companyCd"
			+ " AND c.qstmtStmtLayoutDetailPk.stmtCd := stmtCd"
			+ " AND c.qstmtStmtLayoutDetailPk.strYm := strYm";
	private final String SELECT_DETAIL = SELECT_ALL_DETAILS 
			+ " AND c.qstmtStmtLayoutDetailPk.itemCd := itemCd";
	private final String SELECT_ALL_DETAILS_BY_CATEGORY = SELECT_NO_WHERE
			+ " WHERE c.qstmtStmtLayoutDetailPk.companyCd := companyCd"
			+ " AND c.qstmtStmtLayoutDetailPk.stmtCd := stmtCd"
			+ " AND c.qstmtStmtLayoutDetailPk.strYm := strYm"
			+ " AND c.qstmtStmtLayoutDetailPk.ctgAtr := ctgAtr";
	private final String SELECT_ALL_DETAILS_BY_LINE = SELECT_NO_WHERE
			+ " WHERE c.autoLineId := autoLineId";
	private final String SELECT_DETAILS_WITH_SUMSCOPEATR = SELECT_ALL_DETAILS
			   + " AND c.qstmtStmtLayoutDetailPk.ctgAtr = :ctgAtr" + " AND c.sumScopeAtr = :sumScopeAtr";
	
	@Override
	public void add(LayoutMasterDetail domain) {
		this.commandProxy().insert(toEntity(domain));
	}
	
	private QstmtStmtLayoutDetail toEntity(LayoutMasterDetail domain){
		val entity = new QstmtStmtLayoutDetail();
		entity.fromDomain(domain);
		entity.qstmtStmtLayoutDetailPk.companyCd = domain.getCompanyCode().v();
		entity.qstmtStmtLayoutDetailPk.stmtCd = domain.getLayoutCode().v();
		entity.qstmtStmtLayoutDetailPk.strYm = domain.getStartYm().v();
		entity.qstmtStmtLayoutDetailPk.ctgAtr = domain.getCategoryAtr().value;
		entity.qstmtStmtLayoutDetailPk.itemCd = domain.getItemCode().v();
		entity.endYm = domain.getEndYm().v();
		entity.autoLineId = domain.getAutoLineId().v();
		//xem lai voi qa 86
		//entity.itemPosColumn = domain.geti
		entity.dispAtr = domain.getDisplayAtr().value;
		entity.sumScopeAtr = domain.getSumScopeAtr().value;
		entity.calcMethod = domain.getCalculationMethod().value;
		entity.pWageCd = domain.getPersonalWageCode().v();
		//今回、対応対象外	↓
		entity.formulaCd = "000";
		entity.wageTableCd = "000";
		entity.commonMny = 0;
		//今回、対応対象外　↑
		entity.setoffItemCd = domain.getSetOffItemCode().v();
		entity.commuteAtr = domain.getCommuteAtr().value;
		entity.distributeSet = domain.getDistribute().getSetting().value;
		entity.distributeWay = domain.getDistribute().getMethod().value;
		entity.errRangeLowAtr = domain.getError().getIsUseLow().value;
		entity.errRangeLow = domain.getError().getRange().min();
		entity.errRangeHighAtr = domain.getError().getIsUseHigh().value;
		entity.errRangeHigh = domain.getError().getRange().max();
		entity.alRangeLowAtr = domain.getAlarm().getIsUseLow().value;
		entity.alRangeLow = domain.getAlarm().getRange().min();
		entity.alRangeHighAtr = domain.getAlarm().getIsUseHigh().value;
		entity.alRangeHigh = domain.getAlarm().getRange().max();		
		return entity;
	}

	@Override
	public void update(LayoutMasterDetail domain) {
		this.commandProxy().update(toEntity(domain));
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
		val domain = LayoutMasterDetail.createFromJavaType(
				entity.qstmtStmtLayoutDetailPk.companyCd,
				entity.qstmtStmtLayoutDetailPk.stmtCd,
				entity.qstmtStmtLayoutDetailPk.strYm,
				entity.endYm,
				entity.qstmtStmtLayoutDetailPk.ctgAtr,
				entity.qstmtStmtLayoutDetailPk.itemCd,
				entity.autoLineId,
				entity.dispAtr,
				entity.sumScopeAtr, 
				entity.calcMethod,
				entity.distributeWay,
				entity.distributeSet,
				entity.pWageCd,
				entity.setoffItemCd,
				entity.commuteAtr,
				entity.errRangeHighAtr,
				entity.errRangeHigh,
				entity.errRangeLowAtr,
				entity.errRangeLow, 
				entity.alRangeHighAtr,
				entity.alRangeHigh,
				entity.alRangeLowAtr,
				entity.alRangeLow,
				entity.itemPosColumn);
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

	@Override
	public void remove(String companyCode
			, String layoutCode
			, int startYm
			, int categoryAtr
			, String itemCode) {
		val objectKey = new QstmtStmtLayoutDetailPK();
		objectKey.companyCd = companyCode;
		objectKey.stmtCd = layoutCode;
		objectKey.strYm = startYm;
		objectKey.ctgAtr = categoryAtr;
		objectKey.itemCd = itemCode;		
		this.commandProxy().remove(QstmtStmtLayoutDetail.class, objectKey);
		
	}

	@Override
	public void add(List<LayoutMasterDetail> details) {
		this.commandProxy().insertAll(details);
	}

	@Override
	public void update(List<LayoutMasterDetail> details) {
		this.commandProxy().updateAll(details);
	}

	@Override
	public List<LayoutMasterDetail> getDetailsByCategory(String companyCd, String stmtCd, int startYm,
			int categoryAtr) {
		
		return this.queryProxy().query(SELECT_ALL_DETAILS_BY_CATEGORY, QstmtStmtLayoutDetail.class)
				.setParameter("companyCd", companyCd)
				.setParameter("stmtCd", stmtCd)
				.setParameter("startYM", startYm)
				.setParameter("ctgAtr", categoryAtr)
				.getList(c -> toDomain(c));
	}


	@Override
	public void remove(List<LayoutMasterDetail> details) {
		List<QstmtStmtLayoutDetail> detailEntities = details.stream().map(
					detail -> {return this.toEntity(detail);}
				).collect(Collectors.toList());
		this.commandProxy().removeAll(QstmtStmtLayoutDetail.class, detailEntities);
	}

	@Override
	public List<LayoutMasterDetail> getDetailsByLine(String autoLineId) {

		return this.queryProxy().query(SELECT_ALL_DETAILS_BY_LINE, QstmtStmtLayoutDetail.class)
				.setParameter("autoLineId", autoLineId)
				.getList(c -> toDomain(c));
	}
	
	 @Override
	 public List<LayoutMasterDetail> getDetailsWithSumScopeAtr(String companyCode, String stmtCode, int startYearMonth,
	   int categoryAttribute, int sumScopeAtr) {
	  return this.queryProxy().query(SELECT_DETAILS_WITH_SUMSCOPEATR, QstmtStmtLayoutDetail.class)
	    .getList(c -> toDomain(c));
	 }
}
