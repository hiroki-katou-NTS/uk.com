package nts.uk.cnv.app.td.schema.tabledesign.constraint;

import static java.util.stream.Collectors.*;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.TableConstraints;

@Data
@NoArgsConstructor
public class TableConstraintsDto {

	PrimaryKeyDto primaryKey;
	List<IndexDto> uniqueConstraints;
	List<IndexDto> indexes;
	
	public TableConstraintsDto(TableConstraints d) {
		
		primaryKey = new PrimaryKeyDto(d.getPrimaryKey());
		
		uniqueConstraints = d.getUniqueConstraints().stream()
				.map(e -> new IndexDto(e))
				.collect(toList());
		
		indexes = d.getIndexes().stream()
				.map(e -> new IndexDto(e))
				.collect(toList());
	}
	
	public TableConstraints toDomain() {
		return new TableConstraints(
				primaryKey.toDomain(),
				uniqueConstraints.stream()
					.map(e -> e.toDomainUnique())
					.collect(toList()),
				indexes.stream()
					.map(e -> e.toDomainIndex())
					.collect(toList()));
	}
}
