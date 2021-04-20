package nts.uk.cnv.dom.td.schema.tabledesign;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashSet;
import java.util.List;
import java.util.stream.Collectors;

import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.ToString;
import lombok.val;
import nts.arc.error.BusinessException;
import nts.arc.error.RawErrorMessage;
import nts.uk.cnv.dom.constants.Constants;
import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.TableConstraints;
import nts.uk.cnv.dom.td.tabledefinetype.TableDefineType;

@Getter
@EqualsAndHashCode
@ToString
public class TableDesign implements Cloneable {
	private String id;
	private TableName name;
	private String jpName;

	private List<ColumnDesign> columns;
	private TableConstraints constraints;

	public TableDesign(String id, TableName name, String jpName,
			List<ColumnDesign> columns, TableConstraints constraints) {
		this.validate(columns);
		this.id = id;
		this.name = name;
		this.jpName = jpName;
		this.columns = columns;
		this.constraints = constraints;
	}

	public TableDesign(TableDesign source) {
		this.validate(source.columns);
		this.id = source.id;
		this.name = source.name;
		this.jpName = source.jpName;
		this.columns = source.columns;
		this.constraints = source.constraints;
	}

	public static TableDesign empty() {
		return new TableDesign(
				null,
				null,
				null,
				Collections.emptyList(),
				TableConstraints.empty());
	}

	private void validate(List<ColumnDesign> columns) {
		val columnNames = columns.stream()
				.map(cd -> cd.getName())
				.collect(Collectors.toList());
		if(columnNames.size() != new HashSet<>(columnNames).size()){
			throw new BusinessException(new RawErrorMessage("列名が重複しています"));
		}
	}

	/**
	 * 自身がもっている列定義の内、指定された列Idのリストに一致する列の列名をリストで返す（ソート順なし）
	 * @param 列IDのリスト
	 * @return 列名のリスト
	 */
	public List<String> getColumnNames(List<String> columnIds) {
		return columnIds.stream()
				.map(columnId ->
					this.columns.stream()
						.filter(cd -> cd.getId().equals(columnId))
						.findFirst()
						.map(cd->cd.getName())
						.get()
				)
				.collect(Collectors.toList());
	}

	/**
	 * 自身がもっている列定義の内、指定された列Idに一致する列の列名を返す
	 * @param 列ID
	 * @return 列名
	 */
	public String getColumnName(String columnId) {
		return this.columns.stream()
					.filter(cd -> cd.getId().equals(columnId))
					.findFirst()
					.map(cd->cd.getName())
					.get();
	}

	public String createSimpleTableSql(TableDefineType defineType) {
		return createTableSql(defineType, false, false);
	}

	public String createFullTableSql(TableDefineType defineType) {
		return createTableSql(defineType, true, true);
	}

	private String createTableSql(TableDefineType define, boolean withComment, boolean withRLS) {

		String tableContaint = ",\r\n" + this.constraints.tableContaint(name, this.columns);

		String indexContaint = "";
		if(constraints.getIndexes().size() > 0) {
			indexContaint = String.join(
					";\r\n",
					constraints.getIndexes().stream()
					.map(idx -> idx.getCreateDdl(name, this.columns))
					.collect(Collectors.toList()));
			indexContaint = indexContaint + ";\r\n";
		}

		String comments = "";
		if(withComment) {
			List<String> commentList = new ArrayList<>();

			commentList.add(define.tableCommentDdl(name.v(), jpName));

			this.columns.stream()
				.forEach(col -> commentList.add(define.columnCommentDdl(name.v(), col.getName(), col.getJpName())));

			comments = String.join("\r\n", commentList);
		}

		String rls = "";
		// カラムにCONTRACT_CDがない場合RLSに関する記述はスキップする
		if(withRLS && containContractCd()) {
			rls = define.rlsDdl(name.v());
		}

		return "CREATE TABLE " + this.name.v() + "(\r\n" +
						columnContaint(define) +
						tableContaint +
					");\r\n" +
					indexContaint +
					comments +
					rls;
	}

	private String columnContaint(TableDefineType datatypedefine) {
		List<ColumnDesign> newList = new ArrayList<>();
		newList.addAll(Constants.FixColumns);
		newList.addAll(this.columns);

		return String.join(
						",\r\n",
						newList.stream()
							.map(col -> col.getColumnContaintDdl(datatypedefine))
							.collect(Collectors.toList())
					);
	}

	private boolean containContractCd() {
		return this.columns.stream()
				.map(col -> col.isContractCd())
				.anyMatch(con -> con == true);
	}

}
