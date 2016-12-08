package nts.uk.ctx.pr.proto.infra.repository.layout;

import java.util.ArrayList;
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
	private final String SELECT_ALL = SELECT_NO_WHERE + " WHERE c.qstmtStmtLayoutHeadPK.companyCd = :companyCd"
			+ " ORDER BY c.strYm DESC";
	private final String SELECT_LAYOUT_BEFORE = "SELECT c FROM QstmtStmtLayoutHead c"
			+ " WHERE c.qstmtStmtLayoutHeadPK.companyCd = :companyCd" + " AND c.qstmtStmtLayoutHeadPK.stmtCd = :stmtCd"
			+ " AND c.strYm < :strYm" + " ORDER BY c.strYm DESC";
	private final String SELECT_LAYOUT_MAX_START = SELECT_NO_WHERE
			+ " WHERE c.qstmtStmtLayoutHeadPK.companyCd = :companyCode"
			+ " AND c.endYm = 999912";
	// private final String SELECT_PREVIOUS_TARGET = "SELECT e FROM
	// QstmtStmtLayoutHead e "
	// + "WHERE e.qstmtStmtLayoutHeadPK.companyCd = :companyCd AND
	// c.qstmtStmtLayoutHeadPK.stmtCd = :stmtCd Order by e.endYm DESC";
	private final String SEL_5 = SELECT_NO_WHERE 
			+ " WHERE c.qstmtStmtLayoutHeadPK.companyCd = :companyCode "
			+ " AND c.qstmtStmtLayoutHeadPK.stmtCd = :stmtCode"
			+ " AND c.strYm <= :baseYearMonth "
			+ " AND c.endYm >= :baseYearMonth "; 

	private final LayoutMaster toDomain(QstmtStmtLayoutHead entity) {
		val domain = LayoutMaster.createFromJavaType(entity.qstmtStmtLayoutHeadPK.companyCd,
				entity.strYm, entity.qstmtStmtLayoutHeadPK.stmtCd, entity.endYm, entity.layoutAtr,
				entity.stmtName, entity.qstmtStmtLayoutHeadPK.historyId);

		return domain;
	}

	private static QstmtStmtLayoutHead toEntity(LayoutMaster domain) {
		QstmtStmtLayoutHead entity = new QstmtStmtLayoutHead();
		entity.qstmtStmtLayoutHeadPK = new QstmtStmtLayoutHeadPK(domain.getCompanyCode().v(),
				domain.getStmtCode().v(),
				domain.getHistoryId());
		entity.strYm = domain.getStartYM().v();
		entity.endYm = domain.getEndYm().v();
		entity.layoutAtr = domain.getLayoutAtr().value;
		entity.stmtName = domain.getStmtName().v();

		return entity;
	}

	@Override
	public Optional<LayoutMaster> getLayout(String companyCode, String historyId, String stmtCode) {
		try {
			return this.queryProxy().find(new QstmtStmtLayoutHeadPK(companyCode, stmtCode, historyId), 
					QstmtStmtLayoutHead.class)
					.map(c -> toDomain(c));	
		} catch (Exception e) {
			throw e;
		}
		
	}

	@Override
	public Optional<LayoutMaster> getHistoryBefore(String companyCode, String stmtCode, int strYm) {
		try {
			List<LayoutMaster> lstHistoryBefore = this.queryProxy().query(SELECT_LAYOUT_BEFORE, QstmtStmtLayoutHead.class)
					.setParameter("companyCd", companyCode)
					.setParameter("stmtCd", stmtCode)
					.setParameter("strYm", strYm)
					.getList(c -> toDomain(c));
			LayoutMaster layoutMaster;
			if(!lstHistoryBefore.isEmpty()){
				layoutMaster = lstHistoryBefore.get(0);
				return Optional.of(layoutMaster);
			}
			return null;
		} catch (Exception e) {
			throw e;
		}
	}

	@Override
	public List<LayoutMaster> getLayouts(String companyCode) {

		return this.queryProxy().query(SELECT_ALL, QstmtStmtLayoutHead.class).setParameter("companyCd", companyCode)

				.getList(c -> toDomain(c));

	}

	@Override
	public void add(LayoutMaster layoutMaster) {
		this.commandProxy().insert(toEntity(layoutMaster));
	}

	@Override
	public void update(LayoutMaster layoutMaster) {
		try{
			this.commandProxy().update(toEntity(layoutMaster));
		}
		catch(Exception ex){
			throw ex;
		}
	}

	@Override
	public void remove(String companyCode, String layoutCode, String historyId) {
		val objectKey = new QstmtStmtLayoutHeadPK();
		objectKey.companyCd = companyCode;
		objectKey.stmtCd = layoutCode;
		objectKey.historyId = historyId;
		this.commandProxy().remove(QstmtStmtLayoutHead.class, objectKey);
	}

	@Override
	public boolean isExist(String companyCode, String stmtCode) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public List<LayoutMaster> getLayoutsWithMaxStartYm(String companyCode) {
		try {
			return this.queryProxy().query(SELECT_LAYOUT_MAX_START, QstmtStmtLayoutHead.class)
			.setParameter("companyCode", companyCode)
			.getList(c -> toDomain(c));
			
		} catch (Exception e) {
			throw e;
		}
	}

	// @Override
	// public Optional<LayoutMaster> getPreviosTarget(String companyCode, String
	// stmtCode) {
	// List<QstmtStmtLayoutHead> resultList =
	// this.queryProxy().query(SELECT_PREVIOUS_TARGET,
	// QstmtStmtLayoutHead.class)
	// .setParameter("companyCd", companyCode)
	// .setParameter("stmtCode", stmtCode)
	// .getList();
	// if(resultList.size() > 0){
	// QstmtStmtLayoutHead qstmtStmtLayoutHead = resultList.get(1);
	// LayoutMaster layoutMaster = toDomain(qstmtStmtLayoutHead);
	// return Optional.ofNullable(layoutMaster);
	// }
	// return Optional.empty();
	// }

	@Override
	public List<LayoutMaster> findAll(String companyCode, String stmtCode, int baseYearMonth) {
		return this.queryProxy().query(SEL_5, QstmtStmtLayoutHead.class)
				.setParameter("companyCode", companyCode)
				.setParameter("stmtCode", stmtCode)
				.setParameter("baseYearMonth", baseYearMonth)
				.getList(x -> toDomain(x));
	}
}
