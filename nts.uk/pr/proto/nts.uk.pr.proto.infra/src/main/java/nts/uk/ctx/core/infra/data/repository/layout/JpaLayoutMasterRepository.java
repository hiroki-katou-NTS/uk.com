package nts.uk.ctx.core.infra.data.repository.layout;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMaster;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMasterRepository;
import nts.uk.ctx.pr.proto.infra.entity.layout.QstmtStmtLayoutHead;
import nts.uk.ctx.pr.proto.infra.entity.layout.QstmtStmtLayoutHeadPK;

@RequestScoped
public class JpaLayoutMasterRepository extends JpaRepository implements LayoutMasterRepository {

	private final String SELECT_NO_WHERE = "SELECT c FROM QstmtStmtLayoutHead c";
	private final String SELECT_ALL_DETAILS = SELECT_NO_WHERE + " WHERE c.qstmtStmtLayoutHeadPK.companyCd = :companyCd"
			+ " AND c.qstmtStmtLayoutHeadPK.stmtCd = :stmtCd";
	private final String SELECT_DETAIL = SELECT_ALL_DETAILS + " AND c.qstmtStmtLayoutHeadPK.strYm = :strYm";
	
//	private final String SELECT_PREVIOUS_TARGET = "SELECT e FROM QstmtStmtLayoutHead e "
//			+ "WHERE e.qstmtStmtLayoutHeadPK.companyCd = :companyCd AND c.qstmtStmtLayoutHeadPK.stmtCd = :stmtCd Order by e.endYm DESC";

	private final LayoutMaster toDomain(QstmtStmtLayoutHead entity) {
		val domain = LayoutMaster.createFromJavaType(entity.qstmtStmtLayoutHeadPK.companyCd,
				entity.qstmtStmtLayoutHeadPK.strYm, entity.qstmtStmtLayoutHeadPK.stmtCd, entity.endYm, entity.layoutAtr,
				entity.stmtName);

		entity.toDomain(domain);

		return domain;
	}

	private static QstmtStmtLayoutHead toEntity(LayoutMaster domain) {
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
	public Optional<LayoutMaster> getLayout(String companyCode, int strYm, String stmtCode) {
		return this.queryProxy().query(SELECT_DETAIL, QstmtStmtLayoutHead.class)
				.setParameter("companyCd", companyCode)
				.setParameter("stmtCode", stmtCode)
				.setParameter("strYm", strYm)
				.getSingle(c -> toDomain(c));
		
	}

	@Override
	public Optional<LayoutMaster> getHistoryBefore(String companyCode, String stmtCode, int strYm) {
		return this.queryProxy().query(SELECT_DETAIL, QstmtStmtLayoutHead.class)
				.setParameter("companyCd", companyCode)
				.setParameter("stmtCode", stmtCode)
				.setParameter("strYm", strYm)
				.getSingle(c -> toDomain(c));
		
	}
	@Override
	public List<LayoutMaster> getLayouts(String companyCode) {
		
		return this.queryProxy().query(SELECT_ALL_DETAILS, QstmtStmtLayoutHead.class)
				.setParameter("companyCd", companyCode)
				
				.getList(c -> toDomain(c));
		
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
		val objectKey = new QstmtStmtLayoutHeadPK();
		objectKey.companyCd = companyCode;
		objectKey.stmtCd = layoutCode;
		objectKey.strYm = startYm;
		this.commandProxy().remove(QstmtStmtLayoutHead.class, objectKey);
	}

	@Override
	public boolean isExist(String companyCode, String stmtCode) {
		// TODO Auto-generated method stub
		return false;
	}

//	@Override
//	public Optional<LayoutMaster> getPreviosTarget(String companyCode, String stmtCode) {
//		List<QstmtStmtLayoutHead> resultList =  this.queryProxy().query(SELECT_PREVIOUS_TARGET, QstmtStmtLayoutHead.class)
//		.setParameter("companyCd", companyCode)
//		.setParameter("stmtCode", stmtCode)
//		.getList();
//		if(resultList.size() > 0){
//			QstmtStmtLayoutHead qstmtStmtLayoutHead = resultList.get(1);
//			LayoutMaster layoutMaster = toDomain(qstmtStmtLayoutHead);
//			return Optional.ofNullable(layoutMaster);
//		}
//		return Optional.empty();
//	}

}
