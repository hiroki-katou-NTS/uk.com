package nts.uk.cnv.dom.tabledesign;

import java.util.List;
import java.util.stream.Collectors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.databasetype.DataBaseSpec;
import nts.uk.cnv.dom.databasetype.DatabaseType;

@AllArgsConstructor
@Getter
public class TableDesign {
	private String name;
	private String id;
	private GeneralDateTime createDate;
	private GeneralDateTime updateDate;
	
	private List<ColumnDesign> columns;
	private List<Indexes> indexes;
	
	public String createDdl(DatabaseType dbtype) {
		return "CREATE TABLE " + this.name + "(\r\n" +
						columnContaint(dbtype.spec()) +
						tableContaint(dbtype.spec()) +
					")";
				
	}

	private String tableContaint(DataBaseSpec spec) {
		return String.join(
					",\r\n",
					indexes.stream()
						.map(idx -> idx.getConstraintType() + " " +
								  idx.getName() + " (" + String.join(",", idx.getColmns()) + ")")
						.collect(Collectors.toList())
				) + "\r\n";
	}

	private String columnContaint(DataBaseSpec spec) {
		return String.join(
						",\r\n",
						columns.stream()
							.map(col -> "\t" + col.getName() + " " +
								spec.dataType(col.getType(), col.getMaxLength(), col.getScale()) +
								(col.isNullable() ? " NULL " : " NOT NULL "))
							.collect(Collectors.toList())
					) + "\r\n";
	}
}
