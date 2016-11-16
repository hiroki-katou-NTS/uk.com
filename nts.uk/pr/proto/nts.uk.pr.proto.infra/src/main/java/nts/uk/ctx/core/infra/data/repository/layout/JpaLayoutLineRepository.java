package nts.uk.ctx.core.infra.data.repository.layout;

import java.util.List;
import java.util.Optional;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMaster;
import nts.uk.ctx.pr.proto.dom.layout.LayoutMasterRepository;
import nts.uk.ctx.pr.proto.dom.layout.line.AutoLineId;
import nts.uk.ctx.pr.proto.dom.layout.line.LayoutMasterLine;
import nts.uk.ctx.pr.proto.dom.layout.line.LayoutMasterLineRepository;
import nts.uk.ctx.pr.proto.infra.entity.layout.QstmtStmtLayoutHead;
import nts.uk.ctx.pr.proto.infra.entity.layout.QstmtStmtLayoutLines;

public class JpaLayoutLineRepository extends JpaRepository implements LayoutMasterLineRepository {

	private static String SELECT_NO_WHERE = "SELECT c FROM QstmtStmtLayoutLines c";
	private static String SELECT_ALL_DETAILS = SELECT_NO_WHERE
			+ " WHERE c.qstmtStmtLayoutLinesPk.companyCd = :companyCd"
			+ " AND c.qstmtStmtLayoutLinesPk.stmtCd = :stmtCd" + " AND c.qstmtStmtLayoutLinesPk.strYm = :strYm"
			+ " AND c.qstmtStmtLayoutLinesPk.linePos = :ctgAtr";
	private static String SELECT_DETAIL = SELECT_ALL_DETAILS + " AND c.qstmtStmtLayoutLinesPk.autoLineId = :autoLineId";

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

	@Override
	public Optional<LayoutMasterLine> getLine(String companyCd, String stmtCd, int strYm, String autoLineId) {

		return this.queryProxy().query(SELECT_DETAIL, QstmtStmtLayoutLines.class).setParameter("companyCd", companyCd)
				.setParameter("stmtCd", stmtCd).setParameter("strYm", strYm).setParameter("autoLineId", autoLineId)
				.getSingle(c -> toDomain(c));
	}

	@Override
	public void add(String companyCd, int strYm, String stmtCd) {
		// TODO Auto-generated method stub

	}

	@Override
	public void update(String companyCd, int strYm, String autoLineID, String categoryAtr, String stmtCd) {
		// TODO Auto-generated method stub

	}

	@Override
	public void remove(String companyCd, int strYm, String autoLineID, String categoryAtr, String stmtCd) {
		// TODO Auto-generated method stub

	}

}
