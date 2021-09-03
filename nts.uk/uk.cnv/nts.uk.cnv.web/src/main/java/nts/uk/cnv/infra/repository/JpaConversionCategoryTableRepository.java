package nts.uk.cnv.infra.repository;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.core.dom.conversiontable.ConversionCategoryTable;
import nts.uk.cnv.dom.conversiontable.ConversionCategoryTableRepository;
import nts.uk.cnv.infra.entity.conversiontable.ScvmtConversionCategoryTables;

@Stateless
public class JpaConversionCategoryTableRepository extends JpaRepository implements ConversionCategoryTableRepository {

	@Override
	public List<ConversionCategoryTable> get(String category) {
		String query = "SELECT c"
				+ " FROM ScvmtConversionCategoryTables c"
				+ " WHERE c.pk.categoryName = :category"
				+ " ORDER BY c.sequenceNo ASC";
		return this.queryProxy().query(query, ScvmtConversionCategoryTables.class)
			.setParameter("category", category)
			.getList().stream()
				.map(cp -> cp.toDomain())
				.collect(Collectors.toList());
	}

	@Override
	public void regist(ConversionCategoryTable conversionCategoryTable) {
		ScvmtConversionCategoryTables entity = ScvmtConversionCategoryTables.fromDomain(conversionCategoryTable);
		this.commandProxy().insert(entity);
	}

	@Override
	public void delete(String category) {
		String sql = "DELETE FROM SCVMT_CONVERSION_CATEGORY_TABLES WHERE CATEGORY_NAME = @category";
		this.jdbcProxy().query(sql)
			.paramString("category", category)
			.execute();
		this.getEntityManager().flush();
	}


}
