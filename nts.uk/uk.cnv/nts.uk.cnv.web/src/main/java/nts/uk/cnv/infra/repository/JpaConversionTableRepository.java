package nts.uk.cnv.infra.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.dom.conversiontable.ConversionSource;
import nts.uk.cnv.dom.conversiontable.ConversionTable;
import nts.uk.cnv.dom.conversiontable.ConversionTableRepository;
import nts.uk.cnv.dom.conversiontable.OneColumnConversion;
import nts.uk.cnv.dom.service.ConversionInfo;
import nts.uk.cnv.infra.entity.conversiontable.ScvmtConversionTable;

@Stateless
public class JpaConversionTableRepository extends JpaRepository implements ConversionTableRepository {

	@Override
	public Optional<ConversionTable> get(ConversionInfo info, String category, String tableName, int recordNo, ConversionSource source) {
		String query =
				  "SELECT c FROM ScvmtConversionTable c"
				+ " WHERE c.pk.categoryName = :category"
				+ " AND c.pk.targetTableName = :table"
				+ " AND c.pk.recordNo = :recordNo";
		List<ScvmtConversionTable> entities = this.queryProxy().query(query, ScvmtConversionTable.class)
			.setParameter("category", category)
			.setParameter("table", tableName)
			.setParameter("recordNo", recordNo)
			.getList();

		List<OneColumnConversion> columns = entities.stream()
				.map(entity -> entity.toDomain(info, source.getJoin(info)))
				.collect(Collectors.toList());

		return entities.stream()
			.map(entity -> entity.toDomain(info, columns, source.getCondition()))
			.findFirst();
	}

}
