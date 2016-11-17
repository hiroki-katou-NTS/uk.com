package nts.uk.ctx.core.infra.data.repository.layout;

import java.util.List;
import java.util.Optional;

import javax.enterprise.context.RequestScoped;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.proto.dom.layout.line.LayoutMasterLine;
import nts.uk.ctx.pr.proto.dom.layout.line.LayoutMasterLineRepository;
import nts.uk.ctx.pr.proto.infra.entity.layout.QstmtStmtLayoutLines;
import nts.uk.ctx.pr.proto.infra.entity.layout.QstmtStmtLayoutLinesPK;

@RequestScoped

public class JpaLayoutLineRepository extends JpaRepository implements LayoutMasterLineRepository {

	private final String SELECT_NO_WHERE = "SELECT c FROM QstmtStmtLayoutLines c";
	private final String SELECT_ALL_DETAILS = SELECT_NO_WHERE + " WHERE c.qstmtStmtLayoutLinesPk.companyCd = :companyCd"
			+ " AND c.qstmtStmtLayoutLinesPk.stmtCd = :stmtCd" + " AND c.qstmtStmtLayoutLinesPk.strYm = :strYm"
			+ " AND c.qstmtStmtLayoutLinesPk.linePos = :ctgAtr"
			// private final String SELECT_DETAIL = SELECT_ALL_DETAILS
			+ " AND c.qstmtStmtLayoutLinesPk.autoLineId = :autoLineId";

	@Override
	public List<LayoutMasterLine> getLines(String companyCd, String stmtCd, int strYm) {
		return this.queryProxy().query(SELECT_ALL_DETAILS, QstmtStmtLayoutLines.class)
				.setParameter("companyCd", companyCd).setParameter("stmtCd", stmtCd).setParameter("strYm", strYm)
				.getList(c -> toDomain(c));
	}

	private static LayoutMasterLine toDomain(QstmtStmtLayoutLines entity) {
		val domain = LayoutMasterLine.createFromJavaType(entity.qstmtStmtLayoutLinesPk.companyCd,
				entity.qstmtStmtLayoutLinesPk.strYm, entity.qstmtStmtLayoutLinesPk.stmtCd,
				entity.qstmtStmtLayoutLinesPk.ctgAtr, entity.qstmtStmtLayoutLinesPk.autoLineId, entity.endYm,
				entity.linePos, entity.lineDispAtr);

		entity.toDomain(domain);
		return domain;
	}

	private static QstmtStmtLayoutLines toEntity(LayoutMasterLine domain) {
		val entity = new QstmtStmtLayoutLines();

		entity.fromDomain(domain);

		entity.qstmtStmtLayoutLinesPk.companyCd = domain.getCompanyCode().v();
		entity.qstmtStmtLayoutLinesPk.stmtCd = domain.getStmtCode().v();
		entity.qstmtStmtLayoutLinesPk.strYm = domain.getStartYM().v();
		entity.qstmtStmtLayoutLinesPk.ctgAtr = domain.getCategoryAtr().value;
		entity.qstmtStmtLayoutLinesPk.autoLineId = domain.getAutoLineId().v();
		entity.endYm = domain.getEndYM().v();
		entity.linePos = domain.getLinePosition().v();
		entity.lineDispAtr = domain.getLineDispayAttribute().value;

		return entity;
	}

	@Override
	public void add(LayoutMasterLine layoutMasterLine) {
		this.commandProxy().insert(toEntity(layoutMasterLine));
	}

	@Override
	public void update(LayoutMasterLine layoutMasterLine) {
		this.commandProxy().update(toEntity(layoutMasterLine));
	}

	@Override
	public void remove(String companyCode, int startYm, String autoLineID, int categoryAtr, String stmtCode) {
		val objectKey = new QstmtStmtLayoutLinesPK();
		objectKey.companyCd = companyCode;
		objectKey.strYm = startYm;
		objectKey.autoLineId = autoLineID;
		objectKey.ctgAtr = categoryAtr;
		objectKey.stmtCd = stmtCode;

	}

	@Override
	public Optional<LayoutMasterLine> getLine(String companyCd, String stmtCd, int strYm, String autoLineId) {
		// TODO Auto-generated method stub
		return null;
	}

}
