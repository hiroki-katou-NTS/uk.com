package nts.uk.cnv.dom.tabledesign;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDateTime;
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
		List<Indexes> indexList = indexes.stream().filter(idx -> idx.getConstraintType().equals("INDEX")).collect(Collectors.toList());
		if(!indexList.isEmpty())
		{
			index = String.join(
				";\r\n",
				indexList.stream()
				.map(idx -> "CREATE INDEX " + idx.getName() + " ON " + name + " (" + String.join(",", idx.getColmns()) + ")")
				.collect(Collectors.toList()));
		}
		
		return "CREATE TABLE " + this.name + "(\r\n" +
						columnContaint(datatypedefine) +
						",\r\n"+
						tableContaint() +
					");\r\n\r\n" +
					index + ";";
	}

	private String tableContaint() {
		return String.join(
					",\r\n",
					indexes.stream()
						.filter(idx -> !idx.getConstraintType().equals("INDEX"))
						.map(idx -> "\tCONSTRAINT " + idx.getName() + " " +
								  idx.getConstraintType() + " (" + String.join(",", idx.getColmns()) + ")")
						.collect(Collectors.toList())
				) + "\r\n";
	}

	private String columnContaint(DataTypeDefine datatypedefine) {
		return String.join(
						",\r\n",
						columns.stream()
							.map(col -> "\t" + col.getName() + " " +
									datatypedefine.dataType(col.getType(), col.getMaxLength(), col.getScale()) +
								(col.isNullable() ? " NULL " : " NOT NULL") +
								(col.getDefaultValue() != null && !col.getDefaultValue().isEmpty() ? " DEFAULT " + col.getDefaultValue() : "") )
							.collect(Collectors.toList())
					);
	}
}
