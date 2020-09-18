package nts.uk.cnv.infra.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.dom.conversiontable.ConversionRecord;
import nts.uk.cnv.dom.conversiontable.ConversionRecordRepository;
import nts.uk.cnv.infra.entity.conversiontable.ScvmtConversionRecord;
import nts.uk.cnv.infra.entity.conversiontable.ScvmtConversionRecordPk;

@Stateless
public class JpaConversionRecodeRepository extends JpaRepository implements ConversionRecordRepository {

	@Override
	public List<ConversionRecord> getRecords(String category, String tableName) {
		String query =
				  "SELECT c FROM ScvmtConversionRecord c"
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
		return this.get(category, tableName, recordNo)
			.get()
			.toDomain();
	}

	private Optional<ScvmtConversionRecord> get(String category, String tableName, int recordNo){
		String query =
				  "SELECT c FROM ScvmtConversionRecord c"
				+ " WHERE c.pk.categoryName = :category"
				+ " AND c.pk.targetTableName = :table"
				+ " AND c.pk.recordNo = :recordNo";
		return this.queryProxy().query(query, ScvmtConversionRecord.class)
			.setParameter("category", category)
			.setParameter("table", tableName)
			.setParameter("recordNo", recordNo)
			.getSingle();
	}

	@Override
	public boolean isExists(String category, String tableName, int recordNo) {
		return this.get(category, tableName, recordNo).isPresent();
	}

	@Override
	public void insert(ConversionRecord domain) {
		ScvmtConversionRecordPk pk = new ScvmtConversionRecordPk(
				domain.getCategoryName(),
				domain.getTableName(),
				domain.getRecordNo()
			);

		ScvmtConversionRecord entity = new ScvmtConversionRecord(
				pk,
				domain.getSourceId(),
				domain.getExplanation()
			);

		this.commandProxy().insert(entity);
	}

	@Override
	public void update(ConversionRecord domain) {
		ScvmtConversionRecordPk pk = new ScvmtConversionRecordPk(
				domain.getCategoryName(),
				domain.getTableName(),
				domain.getRecordNo()
			);

		ScvmtConversionRecord entity = new ScvmtConversionRecord(
				pk,
				domain.getSourceId(),
				domain.getExplanation()
			);

		this.commandProxy().update(entity);
	}

	@Override
	public void delete(String category, String tableName, int recordNo) {
		ScvmtConversionRecordPk pk = new ScvmtConversionRecordPk(
				category,
				tableName,
				recordNo
			);

		this.commandProxy().remove(ScvmtConversionRecord.class, pk);

	}

}
