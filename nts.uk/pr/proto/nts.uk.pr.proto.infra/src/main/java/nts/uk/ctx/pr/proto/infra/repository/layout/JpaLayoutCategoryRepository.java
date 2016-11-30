package nts.uk.ctx.pr.proto.infra.repository.layout;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.enterprise.context.RequestScoped;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.ctx.pr.proto.dom.layout.category.LayoutMasterCategory;
import nts.uk.ctx.pr.proto.dom.layout.category.LayoutMasterCategoryRepository;
import nts.uk.ctx.pr.proto.infra.entity.layout.QstmtStmtLayoutCtg;
import nts.uk.ctx.pr.proto.infra.entity.layout.QstmtStmtLayoutCtgPK;

@RequestScoped

public class JpaLayoutCategoryRepository extends JpaRepository implements LayoutMasterCategoryRepository {

	private final String SELECT_NO_WHERE = "SELECT c FROM QstmtStmtLayoutCtg c";
	private final String SELECT_ALL_DETAILS = SELECT_NO_WHERE 
			+ " WHERE c.qstmtStmtLayoutCtgPk.companyCd = :companyCd"
			+ " AND c.qstmtStmtLayoutCtgPk.stmtCd = :stmtCd" 
			+ " AND c.qstmtStmtLayoutCtgPk.strYm = :startYm";
	private final String SELECT_ALL_DETAILS_BEFORE = SELECT_NO_WHERE 
			+ " WHERE c.qstmtStmtLayoutCtgPk.companyCd = :companyCd"
			+ " AND c.qstmtStmtLayoutCtgPk.stmtCd = :stmtCd" 
			+ " AND c.endYm = :endYm";

	private static LayoutMasterCategory toDomain(QstmtStmtLayoutCtg entity) {
		LayoutMasterCategory domain = LayoutMasterCategory.createFromJavaType(entity.qstmtStmtLayoutCtgPk.companyCd,
				entity.qstmtStmtLayoutCtgPk.strYm, entity.qstmtStmtLayoutCtgPk.stmtCd,
				entity.qstmtStmtLayoutCtgPk.ctgAtr, entity.endYm, entity.ctgPos);

		return domain;
	}

	private QstmtStmtLayoutCtg toEntity(LayoutMasterCategory domain) {
		val entity = new QstmtStmtLayoutCtg();

		entity.qstmtStmtLayoutCtgPk = new QstmtStmtLayoutCtgPK();
		entity.qstmtStmtLayoutCtgPk.companyCd = domain.getCompanyCode().v();
		entity.qstmtStmtLayoutCtgPk.stmtCd = domain.getStmtCode().v();
		entity.qstmtStmtLayoutCtgPk.strYm = domain.getStartYM().v();
		entity.qstmtStmtLayoutCtgPk.ctgAtr = domain.getCtAtr().value;
		entity.endYm = domain.getEndYm().v();
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
		try {
			return this.queryProxy().query(SELECT_ALL_DETAILS, QstmtStmtLayoutCtg.class)
					.setParameter("companyCd", companyCd)
					.setParameter("stmtCd", stmtCd)
					.setParameter("startYm", startYm)
					.getList(c -> toDomain(c));
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
		
	}

	@Override
	public void add(List<LayoutMasterCategory> categories) {
		for (LayoutMasterCategory category : categories) {
			this.commandProxy().insert(toEntity(category));		   
		}		
	}

	@Override
	public void update(List<LayoutMasterCategory> categories) {
		for (LayoutMasterCategory category : categories) {
			this.commandProxy().update(toEntity(category));
		}
		
	}

	@Override
	public List<LayoutMasterCategory> getCategoriesBefore(String companyCode, String stmtCode, int endYm) {
		try {
			List<LayoutMasterCategory> lstCategory = this.queryProxy().query(SELECT_ALL_DETAILS_BEFORE, QstmtStmtLayoutCtg.class)
				.setParameter("companyCd", companyCode)
				.setParameter("stmtCd", stmtCode)
				.setParameter("endYm", endYm)
				.getList(c -> toDomain(c));
		return lstCategory;
		} catch (Exception e) {
			// TODO: handle exception
			throw e;
		}
	}

	@Override
	public void removeAllCategory(List<LayoutMasterCategory> categories) {
		List<QstmtStmtLayoutCtg> categoryEntity = categories.stream().map(
				category -> {
					return this.toEntity(category);
				}).collect(Collectors.toList());
		this.commandProxy().removeAll(categoryEntity);
	}

	@Override
	public Optional<LayoutMasterCategory> find(String companyCode, String stmtCode, int startYearMonth, int categoryAtr) {
		QstmtStmtLayoutCtgPK primaryKey = new QstmtStmtLayoutCtgPK(companyCode, stmtCode, startYearMonth, categoryAtr);
		
		return this.queryProxy().find(primaryKey, QstmtStmtLayoutCtg.class).map(x -> toDomain(x));
	}
}