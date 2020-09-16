package nts.uk.cnv.infra.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.dom.conversiontable.ConversionRecord;
import nts.uk.cnv.dom.conversiontable.ConversionSource;
import nts.uk.cnv.dom.conversiontable.ConversionTable;
import nts.uk.cnv.dom.conversiontable.ConversionTableRepository;
import nts.uk.cnv.dom.conversiontable.OneColumnConversion;
import nts.uk.cnv.dom.service.ConversionInfo;
import nts.uk.cnv.infra.entity.conversiontable.ScvmtConversionRecord;
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
				.map(entity -> entity.toDomain(info, source.getJoin()))
				.collect(Collectors.toList());

		return entities.stream()
			.map(entity -> entity.toDomain(info, columns, source.getCondition()))
			.findFirst();
	}

//	@Override
//	public List<OneColumnConversion> findColumns(ConversionInfo info, String category, int recordNo, String tableName) {
//		String query = "SELECT c FROM ScvmtConversionTable c "
//				+ "WHERE c.pk.categoryName = :category"
//				+ " AND  c.pk.targetTableName = :tableName"
//				+ " AND  c.pk.recordNo = :recordNo";
//		return this.queryProxy().query(query, ScvmtConversionTable.class)
//			.setParameter("category", category)
//			.setParameter("tableName", tableName)
//			.setParameter("recordNo", recordNo)
//			.getList().stream()
//				.map(cp -> cp.toDomain(info))
//				.collect(Collectors.toList());
//	}

	@Override
	public List<ConversionRecord> getRecords(String category, String tableName) {
		String query =
				  "SELECT c FROM ScvmtConversionTable c"
				+ " WHERE c.pk.categoryName = :category"
				+ " AND c.pk.targetTableName = :table";
		List<ScvmtConversionRecord> entity = this.queryProxy().query(query, ScvmtConversionRecord.class)
			.setParameter("category", category)
			.setParameter("table", tableName)
			.getList();

		return entity.stream()
			.map(e -> e.toDomain())
			.collect(Collectors.toList());
	}


	@Override
	public ConversionRecord getRecord(String category, String tableName, int recordNo) {
		String query =
				  "SELECT c FROM ScvmtConversionTable c"
				+ " WHERE c.pk.categoryName = :category"
				+ " AND c.pk.targetTableName = :table"
				+ " AND c.pk.recordNo = :recordNo";
		return this.queryProxy().query(query, ScvmtConversionRecord.class)
			.setParameter("category", category)
			.setParameter("table", tableName)
			.setParameter("recordNo", recordNo)
			.getSingle()
			.get()
			.toDomain();
	}

}
