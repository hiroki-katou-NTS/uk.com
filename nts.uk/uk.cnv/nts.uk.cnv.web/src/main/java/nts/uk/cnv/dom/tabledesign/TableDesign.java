package nts.uk.cnv.dom.tabledesign;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.constants.Constants;
import nts.uk.cnv.dom.databasetype.DataTypeDefine;
import nts.uk.cnv.dom.databasetype.UkDataType;

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

	public String createDdl() {
		return this.createDdl(new UkDataType());
	}

	public String createDdl(DataTypeDefine datatypedefine) {
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

		String tableContaint = indexes.stream().anyMatch(idx -> !idx.isIndex())
				? ",\r\n" + tableContaint()
				: "";
		return "CREATE TABLE " + this.name + "(\r\n" +
						columnContaint(datatypedefine) +
						tableContaint +
					");\r\n\r\n" +
					index;
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

	private String columnContaint(DataTypeDefine datatypedefine) {
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
