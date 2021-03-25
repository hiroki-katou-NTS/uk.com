package nts.uk.cnv.infra.td.repository.alteration;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.layer.infra.data.JpaRepository;
import nts.arc.layer.infra.data.entity.JpaEntity;
import nts.arc.layer.infra.data.jdbc.NtsResultSet.NtsResultRecord;
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.alteration.AlterationMetaData;
import nts.uk.cnv.dom.td.alteration.AlterationRepository;
import nts.uk.cnv.dom.td.alteration.AlterationType;
import nts.uk.cnv.dom.td.alteration.content.AlterationContent;
import nts.uk.cnv.dom.td.devstatus.DevelopmentProgress;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAltContentBase;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAlteration;
import nts.uk.cnv.infra.td.entity.alteration.NemTdAlterationView;
import nts.uk.cnv.infra.td.entity.alteration.column.NemTdAltAddColumn;
import nts.uk.cnv.infra.td.entity.alteration.column.NemTdAltChangeColumnComment;
import nts.uk.cnv.infra.td.entity.alteration.column.NemTdAltChangeColumnJpName;
import nts.uk.cnv.infra.td.entity.alteration.column.NemTdAltChangeColumnName;
import nts.uk.cnv.infra.td.entity.alteration.column.NemTdAltChangeColumnType;
import nts.uk.cnv.infra.td.entity.alteration.column.NemTdAltDeleteColumn;
import nts.uk.cnv.infra.td.entity.alteration.index.NemTdAltChangeIndex;
import nts.uk.cnv.infra.td.entity.alteration.index.NemTdAltChangeIndexColumn;
import nts.uk.cnv.infra.td.entity.alteration.index.NemTdAltChangePrimaryKey;
import nts.uk.cnv.infra.td.entity.alteration.index.NemTdAltChangePrimaryKeyColumn;
import nts.uk.cnv.infra.td.entity.alteration.index.NemTdAltChangeUniqueKey;
import nts.uk.cnv.infra.td.entity.alteration.index.NemTdAltChangeUniqueKeyColumn;
import nts.uk.cnv.infra.td.entity.alteration.table.NemTdAltAddTable;
import nts.uk.cnv.infra.td.entity.alteration.table.NemTdAltAddTableColumn;
import nts.uk.cnv.infra.td.entity.alteration.table.NemTdAltAddTableIndex;
import nts.uk.cnv.infra.td.entity.alteration.table.NemTdAltAddTableIndexColumns;
import nts.uk.cnv.infra.td.entity.alteration.table.NemTdAltChangeTableJpName;
import nts.uk.cnv.infra.td.entity.alteration.table.NemTdAltChangeTableName;
import nts.uk.cnv.infra.td.entity.alteration.table.NemTdAltDeleteTable;

public class JpaAlterationRepository extends JpaRepository implements AlterationRepository {
	private final String BASE_SQL = ""
			+ " SELECT alt"
			+ " FROM %s alt"
			+ " WHERE alt.pk.alterationId in :alterationId";

	@Override
	public Alteration get(String alterationId) {
		Map<String, List<AlterationContent>> contents = getContents(Arrays.asList(alterationId));

		return this.queryProxy().find(alterationId, NemTdAlteration.class)
				.map(entity -> entity.toDomain(contents.get(alterationId)))
				.get();
	}

	@Override
	public List<Alteration> gets(List<String> alterIds) {

		Map<String, List<AlterationContent>> contents = getContents(alterIds);

		final String sql = ""
				+ " SELECT *"
				+ " FROM NEM_TD_ALTERATION alt"
				+ " WHERE alt.ALTERATION_ID IN @alterations";
		return this.jdbcProxy().query(sql)
			.paramString("alterations", alterIds)
			.getList(rs -> toDomain(rs, contents.get(rs.getString("ALTERATION_ID"))));
	}
	private Alteration toDomain(NtsResultRecord nrr, List<AlterationContent> contents) {
		return new Alteration(
				nrr.getString("ALTERATION_ID"),
				nrr.getString("FEATURE_ID"),
				nrr.getGeneralDateTime("DATETIME"),
				nrr.getString("TABLE_ID"),
				new AlterationMetaData(
					nrr.getString("USER_NAME"),
					nrr.getString("COMMENT")),
				contents
			);
	}

	private Map<String, List<AlterationContent>> getContents(List<String> alterationIds) {

		String sql = "";
		Map<String, List<AlterationContent>> result = new HashMap<>();
		alterationIds.stream().forEach(alterationId -> {
			result.put(alterationId, new ArrayList<>());
		});

		for(AlterationType type : AlterationType.values()) {

			switch(type) {
			case TABLE_CREATE:
				Map<String, List<NemTdAltAddTableColumn>> columns = getAddTableColumns(alterationIds);
				Map<String, List<NemTdAltAddTableIndex>> indexes = getIndexes(alterationIds);
				Map<String, List<NemTdAltAddTableIndexColumns>> indexColumns = getIndexColumns(alterationIds);

				sql = String.format(BASE_SQL,"NemTdAltAddTable");
				this.queryProxy().query(sql, NemTdAltAddTable.class)
					.setParameter("alterationId", alterationIds)
					.getList()
					.forEach(e -> {
						result.get(e.pk.alterationId).add(
								e.toDomain(
										columns.get(e.pk.alterationId),
										indexes.get(e.pk.alterationId),
										indexColumns.get(e.pk.alterationId)));
					});
				break;
			case TABLE_NAME_CHANGE:
				sql = String.format(BASE_SQL,"NemTdAltChangeTableName");
				this.queryProxy().query(sql, NemTdAltChangeTableName.class)
					.setParameter("alterationId", alterationIds)
					.getList()
					.forEach(e -> {
						result.get(e.pk.alterationId).add(e.toDomain());
					});
				break;
			case TABLE_JPNAME_CHANGE:
				sql = String.format(BASE_SQL,"NemTdAltChangeTableJpName");
				this.queryProxy().query(sql, NemTdAltChangeTableJpName.class)
					.setParameter("alterationId", alterationIds)
					.getList()
					.forEach(e -> {
						result.get(e.pk.alterationId).add(e.toDomain());
					});
				break;
			case COLUMN_ADD:
				sql = String.format(BASE_SQL,"NemTdAltAddColumn");
				this.queryProxy().query(sql, NemTdAltAddColumn.class)
					.setParameter("alterationId", alterationIds)
					.getList()
					.forEach(e -> {
						result.get(e.pk.alterationId).add(e.toDomain());
					});
				break;
			case COLUMN_NAME_CHANGE:
				sql = String.format(BASE_SQL,"NemTdAltChangeColumnName");
				this.queryProxy().query(sql, NemTdAltChangeColumnName.class)
					.setParameter("alterationId", alterationIds)
					.getList()
					.forEach(e -> {
						result.get(e.pk.alterationId).add(e.toDomain());
					});
				break;
			case COLUMN_TYPE_CHANGE:
				sql = String.format(BASE_SQL,"NemTdAltChangeColumnType");
				this.queryProxy().query(sql, NemTdAltChangeColumnType.class)
					.setParameter("alterationId", alterationIds)
					.getList()
					.forEach(e -> {
						result.get(e.pk.alterationId).add(e.toDomain());
					});
				break;
			case COLUMN_JPNAME_CHANGE:
				sql = String.format(BASE_SQL,"NemTdAltChangeColumnJpName");
				this.queryProxy().query(sql, NemTdAltChangeColumnJpName.class)
					.setParameter("alterationId", alterationIds)
					.getList()
					.forEach(e -> {
						result.get(e.pk.alterationId).add(e.toDomain());
					});
				break;
			case COLUMN_COMMENT_CHANGE:
				sql = String.format(BASE_SQL,"NemTdAltChangeColumnComment");
				this.queryProxy().query(sql, NemTdAltChangeColumnComment.class)
					.setParameter("alterationId", alterationIds)
					.getList()
					.forEach(e -> {
						result.get(e.pk.alterationId).add(e.toDomain());
					});
				break;
			case PRIMARY_KEY_CHANGE:
				sql = String.format(BASE_SQL,"NemTdAltChangePrimaryKey");
				this.queryProxy().query(sql, NemTdAltChangePrimaryKey.class)
					.setParameter("alterationId", alterationIds)
					.getList()
					.forEach(e -> {
						val indexColumnIds = getIndexColumnsPK(e.pk.alterationId, e.pk.seqNo);
						result.get(e.pk.alterationId).add(e.toDomain(indexColumnIds));
					});
				break;
			case UNIQUE_KEY_CHANGE:
				sql = String.format(BASE_SQL,"NemTdAltChangeUniqueKey");
				this.queryProxy().query(sql, NemTdAltChangeUniqueKey.class)
					.setParameter("alterationId", alterationIds)
					.getList()
					.forEach(e -> {
						val indexColumnIds = getIndexColumnsUK(e.pk.alterationId, e.pk.seqNo, e.pk.suffix);
						result.get(e.pk.alterationId).add(e.toDomain(indexColumnIds));
					});
				break;
			case INDEX_CHANGE:
				sql = String.format(BASE_SQL,"NemTdAltChangeIndex");
				this.queryProxy().query(sql, NemTdAltChangeIndex.class)
					.setParameter("alterationId", alterationIds)
					.getList()
					.forEach(e -> {
						val indexColumnIds = getIndexColumnsIndex(e.pk.alterationId, e.pk.seqNo, e.pk.suffix);
						result.get(e.pk.alterationId).add(e.toDomain(indexColumnIds));
					});
				break;
			case COLUMN_DELETE:
				sql = String.format(BASE_SQL,"NemTdAltDeleteColumn");
				this.queryProxy().query(sql, NemTdAltDeleteColumn.class)
					.setParameter("alterationId", alterationIds)
					.getList()
					.forEach(e -> {
						result.get(e.pk.alterationId).add(e.toDomain());
					});
				break;
			case TABLE_DROP:
				sql = String.format(BASE_SQL,"NemTdAltDeleteTable");
				this.queryProxy().query(sql, NemTdAltDeleteTable.class)
					.setParameter("alterationId", alterationIds)
					.getList()
					.forEach(e -> {
						result.get(e.pk.alterationId).add(e.toDomain());
					});
				break;
			default:
				throw new IllegalArgumentException("想定されていない変更種類です");
			}
		}

		return result;
	}

	private List<String> getIndexColumnsPK(String alterationId, int seqNo) {
		 String sql = ""
					+ " SELECT col"
					+ " FROM NemTdAltChangePrimaryKeyColumn col"
					+ " WHERE col.pk.alterationId = :alterationId"
					+ "   AND col.pk.seqNo = :seqNo"
					+ "   AND col.pk.suffix = :suffix";
		 return	 this.queryProxy().query(sql, NemTdAltChangePrimaryKeyColumn.class)
					.setParameter("alterationId", alterationId)
					.setParameter("seqNo", seqNo)
					.setParameter("suffix", "PK")
					.getList(entity -> entity.pk.columnId);
	}

	private List<String> getIndexColumnsUK(String alterationId, int seqNo, String suffix) {
		 String sql = ""
					+ " SELECT col"
					+ " FROM NemTdAltChangeUniqueKeyColumn col"
					+ " WHERE col.pk.alterationId = :alterationId"
					+ "   AND col.pk.seqNo = :seqNo"
					+ "   AND col.pk.suffix = :suffix";
		 return	 this.queryProxy().query(sql, NemTdAltChangeUniqueKeyColumn.class)
					.setParameter("alterationId", alterationId)
					.setParameter("seqNo", seqNo)
					.setParameter("suffix", suffix)
					.getList(entity -> entity.pk.columnId);
	}

	private List<String> getIndexColumnsIndex(String alterationId, int seqNo, String suffix) {
		 String sql = ""
					+ " SELECT col"
					+ " FROM NemTdAltChangeIndexColumn col"
					+ " WHERE col.pk.alterationId = :alterationId"
					+ "   AND col.pk.seqNo = :seqNo"
					+ "   AND col.pk.suffix = :suffix";
		 return	 this.queryProxy().query(sql, NemTdAltChangeIndexColumn.class)
					.setParameter("alterationId", alterationId)
					.setParameter("seqNo", seqNo)
					.setParameter("suffix", suffix)
					.getList(entity -> entity.pk.columnId);
	}

	private Map<String, List<NemTdAltAddTableColumn>> getAddTableColumns(List<String> alterationIds) {
		String jpql = "SELECT c"
				+ " FROM NemTdAltAddTableColumn c"
				+ " WHERE c.pk.alterationId in :alterationId";

		return this.queryProxy().query(jpql, NemTdAltAddTableColumn.class)
				.setParameter("alterationId", alterationIds)
				.getList()
				.stream()
				.collect(Collectors.groupingBy(entity -> entity.pk.alterationId));
	}

	private Map<String, List<NemTdAltAddTableIndex>> getIndexes(List<String> alterationIds) {
		String jpql = "SELECT idx"
				+ " FROM NemTdAltAddTableIndex idx"
				+ " WHERE idx.pk.alterationId in :alterationId";

		return this.queryProxy().query(jpql, NemTdAltAddTableIndex.class)
				.setParameter("alterationId", alterationIds)
				.getList()
				.stream()
				.collect(Collectors.groupingBy(entity -> entity.pk.alterationId));
	}

	private Map<String, List<NemTdAltAddTableIndexColumns>> getIndexColumns(List<String> alterationIds) {
		String jpql = "SELECT ic"
				+ " FROM NemTdAltAddTableIndexColumns ic"
				+ " WHERE ic.pk.alterationId in :alterationId";

		return this.queryProxy().query(jpql, NemTdAltAddTableIndexColumns.class)
				.setParameter("alterationId", alterationIds)
				.getList()
				.stream()
				.collect(Collectors.groupingBy(entity -> entity.pk.alterationId));
	}

	@Override
	public List<Alteration> getTable(String tableId, DevelopmentProgress progress) {

		String jpql = "SELECT alt"
				+ " FROM NemTdAlteration alt"
				+ " INNER JOIN NemTdAlterationView view ON alt.alterationId = view.alterationId"
				+ " WHERE view.tableId = :tableId"
				+ " AND view." + NemTdAlterationView.jpqlWhere(progress);

		return this.queryProxy().query(jpql, NemTdAlteration.class)
				.setParameter("tableId", tableId)
				.getList(entity -> {
					val contents = getContents(Arrays.asList(entity.alterationId)).get(entity.alterationId);
					return entity.toDomain(contents);
				});
	}

	@Override
	public List<Alteration> getByEvent(String eventId) {
		String jpql = "SELECT alt"
				+ " FROM NemTdAlteration alt"
				+ " INNER JOIN NemTdAlterationView view ON alt.alterationId = view.alterationId"
				+ " WHERE view.deliveredEventId = :eventId";
		val parents = this.queryProxy().query(jpql, NemTdAlteration.class)
				.setParameter("eventId", eventId)
				.getList();
		List<String> alterationIds = parents.stream()
				.map(alt -> alt.alterationId)
				.collect(Collectors.toList());

		Map<String, List<AlterationContent>> contents = getContents(alterationIds);

		return parents.stream()
				.map(entity -> entity.toDomain(contents.get(entity.alterationId)))
				.collect(Collectors.toList());
	}

	@Override
	public void insert(Alteration alt) {
		this.commandProxy().insert(NemTdAlteration.toEntity(alt));

		alt.getContents().stream()
			.forEach(ac -> {
				insertAlterationContent(alt.getAlterId(), alt.getContents().indexOf(ac), ac);
			});
	}

	private void insertAlterationContent(String alterationId, int seq, AlterationContent ac) {
		List<JpaEntity> entities = NemTdAltContentBase.toEntity(alterationId, seq, ac);

		entities.stream().forEach(entity -> {
			this.commandProxy().insert(entity);
		});
	}
}
