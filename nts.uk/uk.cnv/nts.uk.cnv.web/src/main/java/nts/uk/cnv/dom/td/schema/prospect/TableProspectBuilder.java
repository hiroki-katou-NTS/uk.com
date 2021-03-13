package nts.uk.cnv.dom.td.schema.prospect;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.uk.cnv.dom.td.schema.tabledesign.ColumnDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.Indexes;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.TableName;
import nts.uk.cnv.dom.td.tabledefinetype.DataType;

public class TableProspectBuilder {
	private String id;
	private String name;
	private String jpName;

	private List<Indexes> indexes;

	public Map<String, ColumnDesignBuilder> columnBuilder;
	public Map<String, String> columnName;

	public boolean isEmpty;

	// 最後に適用されたalterationId
	public String alterationId = "";

	public TableProspectBuilder() {
		this.indexes = new ArrayList<Indexes>();
		this.columnBuilder = new HashMap<>();
		this.columnName = new HashMap<>();
		this.isEmpty = true;
	}

	public TableProspectBuilder(TableDesign base) {
		this.id = base.getId();
		this.name = base.getName();
		this.jpName = base.getJpName();
		this.columnBuilder = new HashMap<>();
		this.columnName = new HashMap<>();
		base.getColumns().stream().forEach(col -> {
			columnBuilder.put(col.getId(), new ColumnDesignBuilder(col));
			columnName.put(col.getId(), col.getName());
		});

		this.indexes = new ArrayList<Indexes>();
		base.getIndexes().stream().forEach(idx -> this.indexes.add(idx));

		this.isEmpty = false;
	}

	public Optional<TableProspect> build() {

		if (this.isEmpty) return Optional.empty();

		List<ColumnDesign> newColumns = new ArrayList<>();
		this.columnBuilder.keySet().stream().forEach(col -> {
			newColumns.add(columnBuilder.get(col).build());
		});

		List<Indexes> newIndexes = new ArrayList<>();
		indexes.stream().forEach(idx -> {
			newIndexes.add(idx);
		});

		return Optional.of(
				new TableProspect(
					alterationId,
					new TableDesign(
						this.id,
						this.name,
						this.jpName,
						newColumns,
						newIndexes)
				)
			);
	}

	public TableProspectBuilder add(String alterationId, TableDesign base) {
		this.alterationId = alterationId;
		this.isEmpty = false;
		return new TableProspectBuilder(base);
	}

	public TableProspectBuilder remove(String alterationId) {
		this.alterationId = alterationId;
		this.isEmpty = true;
		return this;
	}

	public TableProspectBuilder tableName(String alterationId, String name) {
		checkBeforeChangeTable();
		this.alterationId = alterationId;

		this.name = name;
		return this;
	}

	public TableProspectBuilder jpName(String alterationId, String jpName) {
		checkBeforeChangeTable();
		this.alterationId = alterationId;

		this.jpName = jpName;
		return this;
	}

	public TableProspectBuilder pk(String alterationId, List<String> columnNames, boolean clustred) {
		checkBeforeChangeTable();
		this.alterationId = alterationId;

		List<String> unMatchColumn = columnNames.stream()
			.filter(colName -> !this.columnName.values().contains(colName))
			.collect(Collectors.toList());
		if(unMatchColumn.size() > 0) {
			throw new BusinessException(new RawErrorMessage(this.name + "テーブルに" + String.join(",", unMatchColumn) + "列が存在しないため主キーを変更できません。"));
		}

		this.indexes.removeIf(idx -> idx.isPK());
		this.indexes.add(
			Indexes.createPk(
				new TableName(this.name),
				columnNames,
				clustred
			));
		return this;
	}

	public TableProspectBuilder uk(String alterationId, String ukName, List<String> columnNames, boolean clustred) {
		checkBeforeChangeTable();
		this.alterationId = alterationId;

		List<String> unMatchColumn = columnNames.stream()
			.filter(colName -> !this.columnName.values().contains(colName))
			.collect(Collectors.toList());
		if(unMatchColumn.size() > 0) {
			throw new BusinessException(new RawErrorMessage(
					this.name + "テーブルに" + String.join(",", unMatchColumn) + "列が存在しないため一意キー" + ukName + "を変更できません。"));
		}

		this.indexes.removeIf(idx -> idx.isUK() && idx.getName().equals(ukName));
		this.indexes.add(
			Indexes.createUk(
				ukName,
				columnNames,
				clustred
			));
		return this;
	}

	public TableProspectBuilder index(String alterationId, String indexName, List<String> columnNames, boolean clustred) {
		checkBeforeChangeTable();
		this.alterationId = alterationId;

		this.indexes.removeIf(idx -> idx.isIndex() && idx.getName().equals(indexName));
		this.indexes.add(
			Indexes.createIndex(
				indexName,
				columnNames,
				clustred
			));
		return this;
	}

	public TableProspectBuilder addColumn(String alterationId, String columnId, ColumnDesign column) {
		checkBeforeChangeTable();
		this.alterationId = alterationId;

		if(this.columnName.containsValue(column.getName())) {
			throw new BusinessException(new RawErrorMessage(this.name + "テーブルの" + column.getName() + "列はすでに存在しているため追加できません。"));
		}

		this.columnBuilder.put(columnId, new ColumnDesignBuilder(column));
		this.columnName.put(columnId, column.getName());
		return this;
	}

	public TableProspectBuilder columnName(String alterationId, String columnId, String afterName) {
		checkBeforeChangeColumn(columnId);
		this.alterationId = alterationId;

		if(this.columnName.containsValue(afterName)) {
			throw new BusinessException(new RawErrorMessage(this.name + "テーブルの" + afterName + "列はすでに存在しているため変更できません。"));
		}

		this.columnBuilder.get(columnId).name(afterName);
		this.columnName.put(columnId, afterName);
		return this;
	}

	public TableProspectBuilder columnJpName(String alterationId, String columnId, String jpName) {
		checkBeforeChangeColumn(columnId);
		this.alterationId = alterationId;

		this.columnBuilder.get(columnId).jpName(jpName);
		return this;
	}

	public TableProspectBuilder columnType(String alterationId, String columnId, DataType type, int maxLength, int scale, boolean nullable) {
		checkBeforeChangeColumn(columnId);
		this.alterationId = alterationId;

		this.columnBuilder.get(columnId).type(type, maxLength, scale, nullable);
		return this;
	}

	public TableProspectBuilder columnComment(String alterationId, String columnId, String comment) {
		checkBeforeChangeColumn(columnId);
		this.alterationId = alterationId;

		this.columnBuilder.get(columnId).comment(comment);
		return this;
	}

	public TableProspectBuilder removeColumn(String alterationId, String columnId) {
		checkBeforeChangeTable();
		this.alterationId = alterationId;

		String colName = this.columnName.get(columnId);
		Optional<Indexes> pk = this.indexes.stream()
				.filter(idx -> idx.isPK() && idx.getColumns().contains(colName))
				.findFirst();
		if(pk.isPresent()) {
			throw new BusinessException(new RawErrorMessage(colName + "は主キーため削除できません。"));
		}
		Optional<Indexes> uk = this.indexes.stream()
				.filter(idx -> idx.isPK() && idx.getColumns().contains(colName))
				.findFirst();
		if(uk.isPresent()) {
			throw new BusinessException(new RawErrorMessage(colName + "は一意キー" + uk.get().getName() +"に含まれるため削除できません。"));
		}
		Optional<Indexes> index = this.indexes.stream()
				.filter(idx -> idx.isIndex() && idx.getColumns().contains(colName))
				.findFirst();
		if(index.isPresent()) {
			throw new BusinessException(new RawErrorMessage(colName + "はインデックス" + index.get().getName() + "に含まれるため削除できません。"));
		}

		this.columnBuilder.remove(columnId);
		return this;
	}

	private void checkBeforeChangeTable() {
		if (this.isEmpty) {
			throw new BusinessException(new RawErrorMessage(this.name + "は存在しないため操作できません。"));
		}
	}

	private void checkBeforeChangeColumn(String columnId) {
		checkBeforeChangeTable();

		if(!columnBuilder.containsKey(columnId)) {
			throw new BusinessException(new RawErrorMessage("対象の列が見つからないため操作できません。テーブル：" + this.name + "　列：" + columnId));
		}
	}
}
