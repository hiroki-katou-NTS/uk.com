package nts.uk.cnv.dom.td.tabledesign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.uk.cnv.dom.td.tabledefinetype.DataType;

public class TableDesignBuilder {
	private String id;
	private String name;
	private String jpName;

	private List<Indexes> indexes;

	public Map<String, ColumnDesignBuilder> columnBuilder;
	public Map<String, String> columnName;

	public boolean isRemoved;

	public TableDesignBuilder(TableDesign base) {
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

		this.isRemoved = false;
	}

	public Optional<TableDesign> build() {

		if (this.isRemoved) return Optional.empty();

		List<ColumnDesign> newColumns = new ArrayList<>();
		this.columnBuilder.keySet().stream().forEach(col -> {
			newColumns.add(columnBuilder.get(col).build());
		});

		List<Indexes> newIndexes = new ArrayList<>();
		indexes.stream().forEach(idx -> {
			newIndexes.add(idx);
		});

		return Optional.of(new TableDesign(
				this.id,
				this.name,
				this.jpName,
				newColumns,
				newIndexes
			));
	}

	public TableDesignBuilder add(TableDesign base) {
		return new TableDesignBuilder(base);
	}

	public TableDesignBuilder remove(String tableName) {
		this.isRemoved = true;
		return this;
	}

	public TableDesignBuilder tableName(String name) {
		checkBeforeChangeTable();

		this.name = name;
		return this;
	}

	public TableDesignBuilder jpName(String jpName) {
		checkBeforeChangeTable();

		this.jpName = jpName;
		return this;
	}

	public TableDesignBuilder pk(List<String> columnNames, boolean clustred) {
		checkBeforeChangeTable();

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
		this.columnBuilder.keySet().forEach(columnId->{
			this.columnBuilder.get(columnId).pk(false, 0);
		});
		columnNames.stream().forEach(columnName -> {
			String columnId = this.columnName.entrySet().stream()
				.filter(e -> columnName.endsWith(e.getValue()))
				.map(e -> e.getKey())
				.findFirst()
				.get();
			this.columnBuilder.get(columnId).pk(true, columnNames.indexOf(columnName));
		});
		return this;
	}

	public TableDesignBuilder uk(String ukName, List<String> columnNames, boolean clustred) {
		checkBeforeChangeTable();

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
		this.columnBuilder.keySet().forEach(columnId->{
			this.columnBuilder.get(columnId).uk(false, 0);
		});
		columnNames.stream().forEach(columnName -> {
			String columnId = this.columnName.entrySet().stream()
					.filter(e -> columnName.endsWith(e.getValue()))
					.map(e -> e.getKey())
					.findFirst()
					.get();
			this.columnBuilder.get(columnId).uk(true, columnNames.indexOf(columnName));
		});
		return this;
	}

	public TableDesignBuilder index(String indexName, List<String> columnNames, boolean clustred) {
		checkBeforeChangeTable();

		this.indexes.removeIf(idx -> idx.isIndex() && idx.getName().equals(indexName));
		this.indexes.add(
			Indexes.createIndex(
				indexName,
				columnNames,
				clustred
			));
		return this;
	}

	public TableDesignBuilder addColumn(String columnId, ColumnDesign column) {
		checkBeforeChangeTable();
		if(this.columnName.containsValue(column.getName())) {
			throw new BusinessException(new RawErrorMessage(this.name + "テーブルの" + column.getName() + "列はすでに存在しているため追加できません。"));
		}

		this.columnBuilder.put(columnId, new ColumnDesignBuilder(column));
		this.columnName.put(columnId, column.getName());
		return this;
	}

	public TableDesignBuilder columnName(String columnId, String afterName) {
		checkBeforeChangeColumn(columnId);

		if(this.columnName.containsValue(afterName)) {
			throw new BusinessException(new RawErrorMessage(this.name + "テーブルの" + afterName + "列はすでに存在しているため変更できません。"));
		}

		this.columnBuilder.get(columnId).name(afterName);
		this.columnName.put(columnId, afterName);
		return this;
	}

	public TableDesignBuilder columnJpName(String columnId, String jpName) {
		checkBeforeChangeColumn(columnId);

		this.columnBuilder.get(columnId).jpName(jpName);
		return this;
	}

	public TableDesignBuilder columnType(String columnId, DataType type, int maxLength, int scale, boolean nullable) {
		checkBeforeChangeColumn(columnId);

		this.columnBuilder.get(columnId).type(type, maxLength, scale, nullable);
		return this;
	}

	public TableDesignBuilder columnComment(String columnId, String comment) {
		checkBeforeChangeColumn(columnId);

		this.columnBuilder.get(columnId).comment(comment);
		return this;
	}

	public TableDesignBuilder removeColumn(String columnId) {
		checkBeforeChangeTable();

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
		if (this.isRemoved) {
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
