package nts.uk.cnv.dom.tabledesign;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.constants.Constants;
import nts.uk.cnv.dom.tabledefinetype.TableDefineType;
import nts.uk.cnv.dom.tabledefinetype.UkDataType;

@AllArgsConstructor
@Getter
public class TableDesign {
	private String name;
	private String id;
	private String comment;
	private GeneralDateTime createDate;
	private GeneralDateTime updateDate;

	private List<ColumnDesign> columns;
	private List<Indexes> indexes;

	public String createTableSql() {
		return this.createSimpleTableSql(new UkDataType());
	}

	public String createSimpleTableSql(TableDefineType defineType) {
		return createTableSql(defineType, false, false);
	}

	public String createFullTableSql(TableDefineType defineType) {
		return createTableSql(defineType, true, true);
	}

	private String createTableSql(TableDefineType define, boolean withComment, boolean withRLS) {

		String tableContaint = indexes.stream().anyMatch(idx -> !idx.isIndex())
				? ",\r\n" + tableContaint()
				: "";

		String index = "";
		List<Indexes> indexList = indexes.stream().filter(idx -> idx.isIndex()).collect(Collectors.toList());
		if(!indexList.isEmpty())
		{
			index = String.join(
				";\r\n",
				indexList.stream()
				.map(idx -> idx.getCreateDdl(name))
				.collect(Collectors.toList()));
			index = index + ";";
		}

		String comments = "";
		if(withComment) {
			List<String> commentList = new ArrayList<>();

			commentList.add(define.tableCommentDdl(name, comment));

			this.columns.stream()
				.forEach(col -> commentList.add(define.columnCommentDdl(name, col.getName(), col.getComment())));

			comments = String.join("\r\n", commentList);
		}

		String rls = "";
		if(withRLS) {
			rls = define.rlsDdl(name);
		}

		return "CREATE TABLE " + this.name + "(\r\n" +
						columnContaint(define) +
						tableContaint +
					");\r\n\r\n" +
					index + "\r\n" +
					comments + "\r\n" +
					rls;
	}

	private String tableContaint() {
		return String.join(
					",\r\n",
					indexes.stream()
						.filter(idx -> !idx.isIndex())
						.map(idx -> idx.getTableContaintDdl())
						.collect(Collectors.toList())
				) + "\r\n";
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
}
