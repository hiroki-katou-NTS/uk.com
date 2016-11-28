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
			+ " ORDER BY c.qstmtStmtLayoutHeadPK.strYm DESC";
	//private final String SELECT_DETAIL = SELECT_ALL + " AND c.qstmtStmtLayoutHeadPK.strYm = :strYm";
	private final String SELECT_LAYOUT_BEFORE = "SELECT c FROM QstmtStmtLayoutHead c"
			+ " WHERE c.qstmtStmtLayoutHeadPK.companyCd = :companyCd" + " AND c.qstmtStmtLayoutHeadPK.stmtCd = :stmtCd"
			+ " AND c.qstmtStmtLayoutHeadPK.strYm < :strYm" + " ORDER BY c.qstmtStmtLayoutHeadPK.strYm DESC";

	private final String SELECT_LAYOUT_MAX_START = SELECT_NO_WHERE
			+ " WHERE c.qstmtStmtLayoutHeadPK.companyCd = :companyCode"
			+ " AND c.endYm = 999912";
	// private final String SELECT_PREVIOUS_TARGET = "SELECT e FROM
	// QstmtStmtLayoutHead e "
	// + "WHERE e.qstmtStmtLayoutHeadPK.companyCd = :companyCd AND
	// c.qstmtStmtLayoutHeadPK.stmtCd = :stmtCd Order by e.endYm DESC";

	private final LayoutMaster toDomain(QstmtStmtLayoutHead entity) {
		val domain = LayoutMaster.createFromJavaType(entity.qstmtStmtLayoutHeadPK.companyCd,
				entity.qstmtStmtLayoutHeadPK.strYm, entity.qstmtStmtLayoutHeadPK.stmtCd, entity.endYm, entity.layoutAtr,
				entity.stmtName);

		return domain;
	}

	private static QstmtStmtLayoutHead toEntity(LayoutMaster domain) {
		QstmtStmtLayoutHead entity = new QstmtStmtLayoutHead();
		entity.qstmtStmtLayoutHeadPK = new QstmtStmtLayoutHeadPK(domain.getCompanyCode().v(),
				domain.getStmtCode().v(),
				domain.getStartYM().v());
		entity.endYm = domain.getEndYm().v();
		entity.layoutAtr = domain.getLayoutAtr().value;
		entity.stmtName = domain.getStmtName().v();

		return entity;
	}

	@Override
	public Optional<LayoutMaster> getLayout(String companyCode, int strYm, String stmtCode) {
		try {
			return this.queryProxy().find(new QstmtStmtLayoutHeadPK(companyCode, stmtCode, strYm), 
					QstmtStmtLayoutHead.class)
					.map(c -> toDomain(c));	
		} catch (Exception e) {
			throw e;
		}
		
	}

	@Override
	public Optional<LayoutMaster> getHistoryBefore(String companyCode, String stmtCode, int strYm) {
		try {
			LayoutMaster lstHistoryBefore = this.queryProxy().query(SELECT_LAYOUT_BEFORE, QstmtStmtLayoutHead.class)
					.setParameter("companyCd", companyCode)
					.setParameter("stmtCd", stmtCode)
					.setParameter("strYm", strYm)
					.getList(c -> toDomain(c)).get(0);
		return Optional.of(lstHistoryBefore);
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

}
