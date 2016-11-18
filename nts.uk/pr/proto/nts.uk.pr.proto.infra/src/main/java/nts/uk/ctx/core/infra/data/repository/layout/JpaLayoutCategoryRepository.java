package nts.uk.ctx.core.infra.data.repository.layout;

import java.util.List;

import javax.enterprise.context.RequestScoped;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.proto.dom.layout.category.LayoutMasterCategory;
import nts.uk.ctx.pr.proto.dom.layout.category.LayoutMasterCategoryRepository;
import nts.uk.ctx.pr.proto.infra.entity.layout.QstmtStmtLayoutCtg;
import nts.uk.ctx.pr.proto.infra.entity.layout.QstmtStmtLayoutCtgPK;

@RequestScoped

public class JpaLayoutCategoryRepository extends JpaRepository implements LayoutMasterCategoryRepository {

	private final String SELECT_NO_WHERE = "SELECT c FROM QstmtStmtLayoutCtgPK c";
	private final String SELECT_ALL_DETAILS = SELECT_NO_WHERE 
			+ " WHERE c.qstmtStmtLayoutCtgPK.companyCd = :companyCd"
			+ " AND c.qstmtStmtLayoutCtgPK.stmtCd = :stmtCd" 
			+ " AND c.qstmtStmtLayoutCtgPK.strYm = :strYm"
			+ "AND c.qstmtStmtLayoutCtgPK.ctgAtr = :ctgAtr";

	private static LayoutMasterCategory toDomain(QstmtStmtLayoutCtg entity) {
		LayoutMasterCategory domain = LayoutMasterCategory.createFromJavaType(entity.qstmtStmtLayoutCtgPk.companyCd,
				entity.qstmtStmtLayoutCtgPk.strYm, entity.qstmtStmtLayoutCtgPk.stmtCd,
				entity.qstmtStmtLayoutCtgPk.ctgAtr, entity.endYm, entity.ctgPos);

		entity.toDomain(domain);
		return domain;
	}

	private static QstmtStmtLayoutCtg toEntity(LayoutMasterCategory domain) {
		val entity = new QstmtStmtLayoutCtg();

		entity.fromDomain(domain);

		entity.qstmtStmtLayoutCtgPk.companyCd = domain.getCompanyCode().v();
		entity.qstmtStmtLayoutCtgPk.stmtCd = domain.getStmtCode().v();
		entity.qstmtStmtLayoutCtgPk.strYm = domain.getStartYM().v();
		entity.qstmtStmtLayoutCtgPk.ctgAtr = domain.getCtAtr().value;
		entity.endYm = domain.getEndYM().v();
		entity.ctgPos = domain.getCtgPos().v();

		return entity;
	}

	@Override
	public void add(LayoutMasterCategory layoutMasterCategory) {
		this.commandProxy().insert(toEntity(layoutMasterCategory));

	}

	@Override
	public void update(LayoutMasterCategory layoutMasterCategory) {
		this.commandProxy().update(toEntity(layoutMasterCategory));

	}

	@Override
	public void remove(String companyCode, String stmtCode, int startYm, int categoryAtr) {
		val objectKey = new QstmtStmtLayoutCtgPK();
		objectKey.companyCd = companyCode;
		objectKey.stmtCd = stmtCode;
		objectKey.strYm = startYm;
		objectKey.ctgAtr = categoryAtr;
		this.commandProxy().remove(QstmtStmtLayoutCtg.class, objectKey);
	}

	@Override
	public List<LayoutMasterCategory> getCategories(String companyCd, String stmtCd, int startYm) {
		return this.queryProxy().query(SELECT_ALL_DETAILS, QstmtStmtLayoutCtg.class)
				.setParameter("companyCd", companyCd).setParameter("stmtCd", stmtCd).setParameter("startYM", startYm)
				.getList(c -> toDomain(c));
	}

	@Override
	public void add(List<LayoutMasterCategory> categories) {
		this.commandProxy().insertAll(categories);
	}

	@Override
	public void update(List<LayoutMasterCategory> categories) {
		this.commandProxy().updateAll(categories);
	}

	@Override
	public void removeAllCategory(String companyCode, String stmtCode, int startYm) {
		val objectKey = new QstmtStmtLayoutCtgPK();
		objectKey.companyCd = companyCode;
		objectKey.stmtCd = stmtCode;
		objectKey.strYm = startYm;
		this.commandProxy().remove(QstmtStmtLayoutCtg.class, objectKey);
	}

}