package nts.uk.cnv.dom.td.tabledesign;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.uk.cnv.dom.constants.Constants;
import nts.uk.cnv.dom.td.alteration.Alteration;
import nts.uk.cnv.dom.td.tabledefinetype.TableDefineType;

@AllArgsConstructor
@Getter
public class TableDesign implements Cloneable {
	private String id;
	private String name;
	private String jpName;

	private List<ColumnDesign> columns;
	private List<Indexes> indexes;

	public String createSimpleTableSql(TableDefineType defineType) {
		return createTableSql(defineType, false, false);
	}

	public String createFullTableSql(TableDefineType defineType) {
		return createTableSql(defineType, true, true);
	}

	/**
	 * 変更を適用する
	 * @param altarations 適用する変更履歴のリスト
	 * @return 適用後のテーブル定義。テーブルが削除された場合はempty
	 */
	public Optional<TableDesign> applyAlteration(List<Alteration> altarations) {
		TableDesignBuilder builder = new TableDesignBuilder(this);

		altarations.stream().forEach(alt ->{
			alt.apply(builder);
		});

		return builder.build();
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
			index = index + ";\r\n";
		}

		String comments = "";
		if(withComment) {
			List<String> commentList = new ArrayList<>();

			commentList.add(define.tableCommentDdl(name, jpName));

			this.columns.stream()
				.forEach(col -> commentList.add(define.columnCommentDdl(name, col.getName(), col.getJpName())));

			comments = String.join("\r\n", commentList) + "\r\n";
		}

		String rls = "";
		// カラムにCONTRACT_CDがない場合RLSに関する記述はスキップする
		if(withRLS && containContractCd()) {
			rls = define.rlsDdl(name);
		}

		return "CREATE TABLE " + this.name + "(\r\n" +
						columnContaint(define) +
						tableContaint +
					");\r\n" +
					index +
					comments +
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

	private boolean containContractCd() {
		return this.columns.stream()
				.map(col -> col.isContractCd())
				.anyMatch(con -> con == true);
	}

//	@Override
//	public TableDesign clone() {
//		List<ColumnDesign> newColumns = new ArrayList<>();
//		columns.stream().forEach(col -> {
//			newColumns.add(col);
//		});
//
//		List<Indexes> newIndexes = new ArrayList<>();
//		indexes.stream().forEach(idx -> {
//			newIndexes.add(idx);
//		});
//
//		return new TableDesign(
//				this.ver.clone(),
//				name,
//				id,
//				comment,
//				GeneralDateTime.ymdhms(createDate.year(), createDate.month(), createDate.day(), createDate.hours(), createDate.minutes(), createDate.seconds()),
//				GeneralDateTime.ymdhms(updateDate.year(), updateDate.month(), updateDate.day(), updateDate.hours(), updateDate.minutes(), updateDate.seconds()),
//				newColumns,
//				newIndexes
//			);
//
//	}
}
