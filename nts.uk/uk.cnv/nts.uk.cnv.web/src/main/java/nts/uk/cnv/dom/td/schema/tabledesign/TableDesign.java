package nts.uk.cnv.dom.td.schema.tabledesign;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import nts.uk.cnv.dom.constants.Constants;
import nts.uk.cnv.dom.td.schema.tabledesign.column.ColumnDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.TableConstraints;
import nts.uk.cnv.dom.td.tabledefinetype.TableDefineType;

@AllArgsConstructor
@Getter
@EqualsAndHashCode
public class TableDesign implements Cloneable {
	private String id;
	private TableName name;
	private String jpName;

	private List<ColumnDesign> columns;
	private TableConstraints constraints;

	public TableDesign(TableDesign source) {
		id = source.id;
		name = source.name;
		jpName = source.jpName;
		columns = source.columns;
		constraints = source.constraints;
	}

	public static TableDesign empty() {
		return new TableDesign(
				null,
				null,
				null,
				Collections.emptyList(),
				TableConstraints.empty());
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
			String.join(
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
