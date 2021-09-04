package nts.uk.cnv.infra.repository;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import javax.ejb.Stateless;

import nts.arc.layer.infra.data.JpaRepository;
import nts.uk.cnv.core.dom.conversionsql.Join;
import nts.uk.cnv.core.dom.conversionsql.JoinAtr;
import nts.uk.cnv.core.dom.conversionsql.TableFullName;
import nts.uk.cnv.core.dom.conversiontable.ConversionInfo;
import nts.uk.cnv.core.dom.conversiontable.ConversionRecord;
import nts.uk.cnv.core.dom.conversiontable.ConversionSource;
import nts.uk.cnv.core.dom.conversiontable.ConversionTable;
import nts.uk.cnv.core.dom.conversiontable.OneColumnConversion;
import nts.uk.cnv.core.infra.entity.conversiontable.ScvmtConversionTable;
import nts.uk.cnv.core.infra.entity.conversiontable.ScvmtConversionTablePk;
import nts.uk.cnv.dom.conversiontable.ConversionTableRepository;

@Stateless
public class JpaConversionTableRepository extends JpaRepository implements ConversionTableRepository {

	@Override
	public Optional<ConversionTable> get(ConversionInfo info, String category, String tableName, ConversionSource source, ConversionRecord record) {
		String query =
				  "SELECT c FROM ScvmtConversionTable c"
				+ " WHERE c.pk.categoryName = :category"
				+ " AND c.pk.targetTableName = :table"
				+ " AND c.pk.recordNo = :recordNo";
		List<ScvmtConversionTable> entities = this.queryProxy().query(query, ScvmtConversionTable.class)
			.setParameter("category", category)
			.setParameter("table", tableName)
			.setParameter("recordNo", record.getRecordNo())
			.getList();

		List<OneColumnConversion> columns = entities.stream()
				.map(entity -> entity.toDomain(info, info.getJoin(source)))
				.collect(Collectors.toList());

		return Optional.of(
				ScvmtConversionTable.toDomain(
						tableName, info, columns, source, record
					));
	}

	@Override
	public Optional<OneColumnConversion> findColumnConversion(ConversionInfo info, String category, String table, int recordNo, String targetColumn, Join sourceJoin) {

		ScvmtConversionTablePk pk = new ScvmtConversionTablePk(category, table, recordNo, targetColumn);

		Optional<ScvmtConversionTable> entity = this.queryProxy().find(pk, ScvmtConversionTable.class);

		if (!entity.isPresent()) return Optional.empty();

		return Optional.of(entity.get().toDomain(info, sourceJoin));
	}

	@Override
	public boolean isExists(String category, String table, int recordNo, String targetColumn) {

		ScvmtConversionTablePk pk = new ScvmtConversionTablePk(category, table, recordNo, targetColumn);
		return this.queryProxy()
				.find(pk, ScvmtConversionTable.class)
				.isPresent();
	}

	@Override
	public void insert(String category, String table, int recordNo, OneColumnConversion domain) {
		ScvmtConversionTable entity = ScvmtConversionTable.toEntity(category, table, recordNo, domain);

		this.commandProxy().insert(entity);
		this.getEntityManager().flush();
	}

	@Override
	public void delete(String category, String table, int recordNo, String targetColumn) {
		ScvmtConversionTablePk pk = new ScvmtConversionTablePk(category, table, recordNo, targetColumn);

		this.commandProxy().remove(ScvmtConversionTable.class, pk);
		this.getEntityManager().flush();
	}

	/**
	 * 画面表示用のためConversionInfoとJoinがダミー。コンバートコードは構築不可
	 */
	@Override
	public List<OneColumnConversion> find(String category, String tableName, int recordNo) {
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

		return entities.stream()
				.map(entity -> entity.toDomain(
						ConversionInfo.createDummry(),
						new Join(new TableFullName("", "", "", ""), JoinAtr.Main, new ArrayList<>())))
				.collect(Collectors.toList());
	}

}
