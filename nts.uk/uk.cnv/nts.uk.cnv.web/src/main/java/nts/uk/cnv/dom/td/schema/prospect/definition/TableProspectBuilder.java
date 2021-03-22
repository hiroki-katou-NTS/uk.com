package nts.uk.cnv.dom.td.schema.prospect.definition;

import static java.util.stream.Collectors.*;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.TableName;
import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.column.DataType;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.PrimaryKey;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.TableConstraints;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.TableIndex;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.UniqueConstraint;

public class TableProspectBuilder {
	private String id;
	private TableName name;
	private String jpName;

	public Map<String, ColumnDesignBuilder> columnBuilder;
	public Map<String, String> columnName;

	private PrimaryKey primaryKey;
	private List<UniqueConstraint> uniqueConstraints;
	private List<TableIndex> indexes;

	public boolean isEmpty;

	// 最後に適用されたalterationId
	public String alterationId = "";

	public TableProspectBuilder(TableDesign base) {
		transfer(base);
	}

	private void transfer(TableDesign base) {
		this.id = base.getId();
		this.name = base.getName();
		this.jpName = base.getJpName();
		this.columnBuilder = new HashMap<>();
		this.columnName = new HashMap<>();
		base.getColumns().stream().forEach(col -> {
			columnBuilder.put(col.getId(), new ColumnDesignBuilder(col));
			columnName.put(col.getId(), col.getName());
		});

		this.primaryKey = base.getConstraints().getPrimaryKey();
		this.uniqueConstraints = new ArrayList<>(base.getConstraints().getUniqueConstraints());
		this.indexes = new ArrayList<>(base.getConstraints().getIndexes());

		this.isEmpty = false;
	}

	public static TableProspectBuilder empty() {
		return new TableProspectBuilder(TableDesign.empty());
	}

	public Optional<TableProspect> build() {

		if (this.isEmpty) return Optional.empty();

		val columns = columnBuilder.values().stream()
				.map(c -> c.build())
				.sorted()
				.collect(toList());

		val constraints = new TableConstraints(primaryKey, uniqueConstraints, indexes);

		val table = new TableDesign(id, name, jpName, columns, constraints);

		return Optional.of(new TableProspect(alterationId, table));
	}


	public void add(String alterationId, TableDesign base) {
		this.alterationId = alterationId;
		transfer(base);
	}

	public void remove(String alterationId) {
		this.alterationId = alterationId;
		this.isEmpty = true;
	}

	public void tableName(String alterationId, String name) {
		checkBeforeChangeTable();
		this.alterationId = alterationId;

		this.name = new TableName(name);
	}

	public void jpName(String alterationId, String jpName) {
		checkBeforeChangeTable();
		this.alterationId = alterationId;

		this.jpName = jpName;
	}

	public void pk(String alterationId, List<String> columnIds, boolean clustred) {

		checkBeforeChangeTable();
		this.alterationId = alterationId;

		List<String> unMatchColumn = columnIds.stream()
			.filter(columnId -> !this.columnBuilder.keySet().contains(columnId))
			.collect(Collectors.toList());
		if (unMatchColumn.size() > 0) {
			throw new BusinessException(new RawErrorMessage(this.name + "テーブルに" + String.join(",", unMatchColumn) + "列が存在しないため主キーを変更できません。"));
		}

		this.primaryKey = new PrimaryKey(columnIds, clustred);
	}

	public void unique(String alterationId, String suffix, List<String> columnIds, boolean clustred) {

		checkBeforeChangeTable();
		this.alterationId = alterationId;

		List<String> unMatchColumn = columnIds.stream()
			.filter(colName -> !this.columnName.keySet().contains(colName))
			.collect(Collectors.toList());
		if(unMatchColumn.size() > 0) {
			throw new BusinessException(new RawErrorMessage(
					this.name + "テーブルに" + String.join(",", unMatchColumn) + "列が存在しないため一意キー" + suffix + "を変更できません。"));
		}

		this.uniqueConstraints.removeIf(u -> u.getSuffix().equals(suffix));
		this.uniqueConstraints.add(new UniqueConstraint(suffix, columnIds, clustred));
	}

	public void index(String alterationId, String suffix, List<String> columnIds, boolean clustred) {
		checkBeforeChangeTable();
		this.alterationId = alterationId;

		this.indexes.removeIf(u -> u.getSuffix().equals(suffix));
		this.indexes.add(new TableIndex(suffix, columnIds, clustred));
	}

	public void addColumn(String alterationId, String columnId, ColumnDesign column) {
		checkBeforeChangeTable();
		this.alterationId = alterationId;

		if(this.columnName.containsValue(column.getName())) {
			throw new BusinessException(new RawErrorMessage(this.name + "テーブルの" + column.getName() + "列はすでに存在しているため追加できません。"));
		}

		this.columnBuilder.put(columnId, new ColumnDesignBuilder(column));
		this.columnName.put(columnId, column.getName());
	}

	public void columnName(String alterationId, String columnId, String afterName) {
		checkBeforeChangeColumn(columnId);
		this.alterationId = alterationId;

		if(this.columnName.containsValue(afterName)) {
			throw new BusinessException(new RawErrorMessage(this.name + "テーブルの" + afterName + "列はすでに存在しているため変更できません。"));
		}

		this.columnBuilder.get(columnId).name(afterName);
		this.columnName.put(columnId, afterName);
	}

	public void columnJpName(String alterationId, String columnId, String jpName) {
		checkBeforeChangeColumn(columnId);
		this.alterationId = alterationId;

		this.columnBuilder.get(columnId).jpName(jpName);
	}

	public void columnType(String alterationId, String columnId, DataType type, int maxLength, int scale, boolean nullable) {
		checkBeforeChangeColumn(columnId);
		this.alterationId = alterationId;

		this.columnBuilder.get(columnId).type(type, maxLength, scale, nullable);
	}

	public void columnComment(String alterationId, String columnId, String comment) {
		checkBeforeChangeColumn(columnId);
		this.alterationId = alterationId;

		this.columnBuilder.get(columnId).comment(comment);
	}

	public void removeColumn(String alterationId, String columnId) {
		checkBeforeChangeTable();
		this.alterationId = alterationId;

		String colName = this.columnName.get(columnId);


		if(primaryKey.getColumnIds().contains(columnId)) {
			throw new BusinessException(new RawErrorMessage(colName + "は主キーに含まれるため削除できません。"));
		}

		if(uniqueConstraints.stream().anyMatch(u -> u.getColumnIds().contains(columnId))) {
			throw new BusinessException(new RawErrorMessage(colName + "は一意キーに含まれるため削除できません。"));
		}

		if(indexes.stream().anyMatch(i -> i.getColumnIds().contains(columnId))) {
			throw new BusinessException(new RawErrorMessage(colName + "はインデックスに含まれるため削除できません。"));
		}

		this.columnBuilder.remove(columnId);
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
