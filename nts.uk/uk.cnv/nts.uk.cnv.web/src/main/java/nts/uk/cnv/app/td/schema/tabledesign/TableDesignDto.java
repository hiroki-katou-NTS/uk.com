package nts.uk.cnv.app.td.schema.tabledesign;

import static java.util.stream.Collectors.*;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.cnv.app.td.schema.tabledesign.column.ColumnDesignDto;
import nts.uk.cnv.app.td.schema.tabledesign.constraint.TableConstraintsDto;
import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;
import nts.uk.cnv.dom.td.schema.tabledesign.TableName;

@Data
@NoArgsConstructor
public class TableDesignDto {

	String id;
	String name;
	String jpName;
	List<ColumnDesignDto> columns;
	TableConstraintsDto constraints;
	
	public TableDesignDto(TableDesign d) {
		id = d.getId();
		name = d.getName().v();
		jpName = d.getJpName();
		columns = d.getColumns().stream()
				.map(e -> new ColumnDesignDto(e))
				.collect(toList());
		constraints = new TableConstraintsDto(d.getConstraints());
	}
	
	public TableDesign toDomain() {
		return new TableDesign(
				id,
				new TableName(name),
				jpName,
				columns.stream().map(e -> e.toDomain()).collect(toList()),
				constraints.toDomain());
	}
}
