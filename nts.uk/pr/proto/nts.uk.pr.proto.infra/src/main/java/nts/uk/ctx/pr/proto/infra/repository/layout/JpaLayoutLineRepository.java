package nts.uk.ctx.pr.proto.infra.repository.layout;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

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
	private final String SELECT_ALL_DETAILS = SELECT_NO_WHERE 
			+ " WHERE c.qstmtStmtLayoutLinesPk.companyCd = :companyCd"
			+ " AND c.qstmtStmtLayoutLinesPk.stmtCd = :stmtCd" 
			+ " AND c.qstmtStmtLayoutLinesPk.strYm = :strYm";

	private final String SELECT_ALL_LINE_BY_CATEGORY = SELECT_NO_WHERE 
			+ " WHERE c.qstmtStmtLayoutLinesPk.companyCd = :companyCd"
			+ " AND c.qstmtStmtLayoutLinesPk.stmtCd = :stmtCd" 
			+ " AND c.qstmtStmtLayoutLinesPk.strYm = :strYm"
			+ " AND c.qstmtStmtLayoutLinesPk.ctgAtr = :ctgAtr";
	
	private final String SELECT_ALL_DETAILS_BEFORE = SELECT_NO_WHERE 
			+ " WHERE c.qstmtStmtLayoutLinesPk.companyCd = :companyCd"
			+ " AND c.qstmtStmtLayoutLinesPk.stmtCd = :stmtCd" 
			+ " AND c.endYm = :endYm";
	
	@Override
	public List<LayoutMasterLine> getLines(String companyCd, String stmtCd, int strYm) {
			return this.queryProxy().query(SELECT_ALL_DETAILS, QstmtStmtLayoutLines.class)
					.setParameter("companyCd", companyCd)
					.setParameter("stmtCd", stmtCd)
					.setParameter("strYm", strYm)
					.getList(c -> toDomain(c));
	
	}

	private static LayoutMasterLine toDomain(QstmtStmtLayoutLines entity) {
		val domain = LayoutMasterLine.createFromJavaType(entity.qstmtStmtLayoutLinesPk.companyCd,
				entity.qstmtStmtLayoutLinesPk.strYm, entity.qstmtStmtLayoutLinesPk.stmtCd,
				entity.endYm,
				entity.qstmtStmtLayoutLinesPk.autoLineId,
				entity.lineDispAtr,
				entity.linePos,
				entity.qstmtStmtLayoutLinesPk.ctgAtr 
				);

		return domain;
	}

	private QstmtStmtLayoutLines toEntity(LayoutMasterLine domain) {
		val entity = new QstmtStmtLayoutLines();

		entity.qstmtStmtLayoutLinesPk = new QstmtStmtLayoutLinesPK();
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
		this.commandProxy().remove(QstmtStmtLayoutLines.class, objectKey);
	}

	@Override
	public Optional<LayoutMasterLine> getLine(String companyCd, String stmtCd, int strYm, String autoLineId) {
		// TODO Auto-generated method stub
		return null;
	}

	@Override
	public void add(List<LayoutMasterLine> lines) {
		this.commandProxy().insertAll(lines);
	}

	@Override
	public void update(List<LayoutMasterLine> lines) {
		this.commandProxy().updateAll(lines);
	}

	@Override
	public void remove(List<LayoutMasterLine> lines) {
		List<QstmtStmtLayoutLines> linesEntity = lines.stream().map(
				line -> {
							return this.toEntity(line); 
						}).collect(Collectors.toList());
		this.commandProxy().removeAll(linesEntity);
	}

	@Override
	public List<LayoutMasterLine> getLines(String companyCd, String stmtCd, int strYm, int categoryAtr) {
		return this.queryProxy().query(SELECT_ALL_LINE_BY_CATEGORY, QstmtStmtLayoutLines.class)
				.setParameter("companyCd", companyCd)
				.setParameter("stmtCd", stmtCd)
				.setParameter("strYm", strYm)
				.setParameter("ctgAtr", categoryAtr)
				.getList(c -> toDomain(c));
	}

	@Override
	public List<LayoutMasterLine> getLinesBefore(String companyCd, String stmtCd, int endYm) {
		return this.queryProxy().query(SELECT_ALL_DETAILS_BEFORE, QstmtStmtLayoutLines.class)
				.setParameter("companyCd", companyCd)
				.setParameter("stmtCd", stmtCd)
				.setParameter("endYm", endYm)
				.getList(c -> toDomain(c));
	}

	@Override
	public Optional<LayoutMasterLine> find(String companyCode, String stmtCode, int startYearMonth, int categoryAtr,
			String autoLineId) {
		QstmtStmtLayoutLinesPK primaryKey = new QstmtStmtLayoutLinesPK(companyCode, stmtCode, startYearMonth, categoryAtr, autoLineId);
		return this.queryProxy().find(primaryKey, QstmtStmtLayoutLines.class)
				.map(x -> toDomain(x));
	}
}
