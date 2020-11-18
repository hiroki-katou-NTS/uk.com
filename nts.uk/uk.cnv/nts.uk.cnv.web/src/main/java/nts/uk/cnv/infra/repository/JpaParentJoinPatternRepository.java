package nts.uk.cnv.infra.repository;

import java.util.List;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.dom.conversiontable.pattern.ParentJoinPattern;
import nts.uk.cnv.dom.conversiontable.pattern.manager.ParentJoinPatternRepository;
import nts.uk.cnv.dom.service.ConversionInfo;
import nts.uk.cnv.infra.entity.conversiontable.ScvmtConversionTable;

@Stateless
public class JpaParentJoinPatternRepository extends JpaRepository implements ParentJoinPatternRepository {

	@Override
	public List<ParentJoinPattern> get(ConversionInfo info, String category, String name) {

		String query =
				  "SELECT c FROM ScvmtConversionTable c"
				+ " WHERE c.pk.categoryName = :category"
				+ " AND c.conversionType = :type"
				+ " AND c.typeParent.parentName = :table";
		List<ScvmtConversionTable> entities = this.queryProxy().query(query, ScvmtConversionTable.class)
			.setParameter("category", category)
			.setParameter("type", "PARENT")
			.setParameter("table", name)
			.getList();

		return entities.stream()
				.map(entity ->
						entity.typeParent.toDomain(info, null)
					)
				.collect(Collectors.toList());

	}

}
