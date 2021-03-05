package nts.uk.cnv.dom.td.tabledesign;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.td.tabledefinetype.DataType;

public class TableDesignBuilder {
	private String id;
	private String name;
	private String jpName;
	private GeneralDateTime createDate;
	private GeneralDateTime updateDate;

	//private List<ColumnDesign> columns;
	private List<Indexes> indexes;

	public Map<String, ColumnDesignBuilder> colmnBuilder;

	public boolean isRemoved;

//	private TableDesignBuilder() {
//		this.isRemoved = true;
//	}

	public TableDesignBuilder(TableDesign base) {
		this.id = base.getId();
		this.name = base.getName();
		this.jpName = base.getJpName();
		this.createDate = GeneralDateTime.localDateTime(base.getCreateDate().localDateTime());
		this.updateDate = GeneralDateTime.localDateTime(base.getUpdateDate().localDateTime());

		//this.columns = new ArrayList<>();
		this.colmnBuilder = new HashMap<>();
		base.getColumns().stream().forEach(col -> {
			//this.columns.add(col);
			colmnBuilder.put(col.getName(), new ColumnDesignBuilder(col));
		});

		this.indexes = new ArrayList<Indexes>();
		base.getIndexes().stream().forEach(idx -> this.indexes.add(idx));

		this.isRemoved = false;
	}

	public Optional<TableDesign> build() {

		if (this.isRemoved) return Optional.empty();

		List<ColumnDesign> newColumns = new ArrayList<>();
		this.colmnBuilder.keySet().stream().forEach(col -> {
			newColumns.add(colmnBuilder.get(col).build());
		});

		List<Indexes> newIndexes = new ArrayList<>();
		indexes.stream().forEach(idx -> {
			newIndexes.add(idx);
		});

		return Optional.of(new TableDesign(
				this.id,
				this.name,
				this.jpName,
				this.createDate,
				this.updateDate,
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

//	public TableDesignBuilder id(String id) {
//		this.id = id;
//		return this;
//	}

	public TableDesignBuilder createDate(GeneralDateTime createDate) {
		checkBeforeChangeTable();

		this.createDate = createDate;
		return this;
	}

	public TableDesignBuilder updateDate(GeneralDateTime updateDate) {
		checkBeforeChangeTable();
		this.updateDate = updateDate;
		return this;
	}

	public TableDesignBuilder pk(List<String> columnNames, boolean clustred) {
		checkBeforeChangeTable();

		this.indexes.removeIf(idx -> idx.isPK());
		this.indexes.add(
			Indexes.createPk(
				new TableName(this.name),
				columnNames,
				clustred
			));
		this.colmnBuilder.keySet().forEach(columnName->{
			this.colmnBuilder.get(columnName).pk(false, 0);
		});
		columnNames.stream().forEach(columnName -> {
			this.colmnBuilder.get(columnName).pk(true, columnNames.indexOf(columnName));
		});
		return this;
	}

	public TableDesignBuilder uk(String ukName, List<String> columnNames, boolean clustred) {
		checkBeforeChangeTable();

		this.indexes.removeIf(idx -> idx.isUK() && idx.getName().equals(ukName));
		this.indexes.add(
			Indexes.createUk(
				ukName,
				columnNames,
				clustred
			));
		this.colmnBuilder.keySet().forEach(columnName->{
			this.colmnBuilder.get(columnName).uk(false, 0);
		});
		columnNames.stream().forEach(columnName -> {
			this.colmnBuilder.get(columnName).uk(true, columnNames.indexOf(columnName));
		});
		return this;
	}

	public TableDesignBuilder index(String indexName, List<String> columnNames, boolean clustred, boolean unique) {
		checkBeforeChangeTable();

		this.indexes.removeIf(idx -> idx.isIndex() && idx.getName().equals(indexName));
		this.indexes.add(
			Indexes.createIndex(
				indexName,
				columnNames,
				clustred,
				unique
			));
		return this;
	}

	public TableDesignBuilder addColumn(String columnName, ColumnDesign column) {
		checkBeforeChangeTable();
		if(this.colmnBuilder.containsKey(columnName)) {
			throw new BusinessException(new RawErrorMessage(this.name + "テーブルの" + columnName + "列はすでに存在しているため追加できません。"));
		}

		this.colmnBuilder.put(columnName, new ColumnDesignBuilder(column));
		return this;
	}

	public TableDesignBuilder columnName(String name, String afterName) {
		checkBeforeChangeColumn(name);

		if(this.colmnBuilder.containsKey(afterName)) {
			throw new BusinessException(new RawErrorMessage(this.name + "テーブルの" + afterName + "列はすでに存在しているため変更できません。"));
		}

		val builder = this.colmnBuilder.get(name).name(afterName);
		this.colmnBuilder.remove(name);
		this.colmnBuilder.put(afterName, builder);
		return this;
	}

	public TableDesignBuilder columnJpName(String columnName, String jpName) {
		this.colmnBuilder.get(columnName).jpName(jpName);
		return this;
	}

	public TableDesignBuilder columnType(String columnName, DataType type, int maxLength, int scale, boolean nullable) {
		this.colmnBuilder.get(columnName).type(type, maxLength, scale, nullable);
		return this;
	}

//	public TableDesignBuilder columnPk(String columnName, boolean isPrimaryKey, int seq) {
//		this.colmnBuilder.get(columnName).pk(isPrimaryKey, seq);
//		return this;
//	}

//	public TableDesignBuilder columnUk(String columnName, boolean isUniqueKey, int seq) {
//		this.colmnBuilder.get(columnName).uk(isUniqueKey, seq);
//		return this;
//	}

	public TableDesignBuilder columnDefaultValue(String columnName, String defaultValue) {
		this.colmnBuilder.get(columnName).defaultValue(defaultValue);
		return this;
	}

	public TableDesignBuilder columnComment(String columnName, String comment) {
		this.colmnBuilder.get(columnName).comment(comment);
		return this;
	}

	public TableDesignBuilder check(String columnName, String check) {
		this.colmnBuilder.get(columnName).check(check);
		return this;
	}

	public TableDesignBuilder removeColumn(String columnName) {
		checkBeforeChangeTable();

		this.colmnBuilder.remove(columnName);
		return this;
	}

//	public TableDesignBuilder indexes(List<Indexes> indexes) {
//		this.indexes = new ArrayList<Indexes>();
//		indexes.stream().forEach(idx -> this.indexes.add(idx));
//		return this;
//	}


	private void checkBeforeChangeTable() {
		if (this.isRemoved) {
			throw new BusinessException(new RawErrorMessage(this.name + "は存在しないため操作できません。"));
		}
	}

	private void checkBeforeChangeColumn(String name) {
		checkBeforeChangeTable();

		if(colmnBuilder.containsKey(name)) {
			throw new BusinessException(new RawErrorMessage(this.name + "テーブルの" + name + "列は存在しないため操作できません。"));
		}
	}
}
