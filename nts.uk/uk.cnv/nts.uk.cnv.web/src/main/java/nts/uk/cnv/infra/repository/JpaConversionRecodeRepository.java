package nts.uk.cnv.infra.repository;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.core.dom.conversiontable.ConversionRecord;
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
				domain.getExplanation(),
				domain.isRemoveDuplicate()
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
				domain.getExplanation(),
				domain.isRemoveDuplicate()
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

	@Override
	public void swap(String category, String table, int recordNo1, int recordNo2) {
		updateRecordNo(category, table, recordNo1, -1);
		updateRecordNo(category, table, recordNo2, recordNo1);
		updateRecordNo(category, table, -1, recordNo2);
	}

	private void updateRecordNo(String category, String table, int recordNo, int newRecordNo) {

		String query =
				 " SET RECORD_NO = @newRecordNo"
				+ " WHERE CATEGORY_NAME = @category"
				+ " AND TARGET_TBL_NAME = @table"
				+ " AND RECORD_NO = @recordNo";

		this.jdbcProxy().query("UPDATE SCVMT_CONVERSION_RECORD" + query)
			.paramString("category", category)
			.paramString("table", table)
			.paramInt("recordNo", recordNo)
			.paramInt("newRecordNo", newRecordNo)
			.execute();

		this.jdbcProxy().query("UPDATE SCVMT_CONVERSION_TABLE" + query)
			.paramString("category", category)
			.paramString("table", table)
			.paramInt("recordNo", recordNo)
			.paramInt("newRecordNo", newRecordNo)
			.execute();
	}

}
