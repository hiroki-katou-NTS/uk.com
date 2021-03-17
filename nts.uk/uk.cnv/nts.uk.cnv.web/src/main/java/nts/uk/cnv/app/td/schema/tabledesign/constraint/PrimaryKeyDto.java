package nts.uk.cnv.app.td.schema.tabledesign.constraint;

import java.util.List;

import lombok.Data;
import lombok.NoArgsConstructor;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.PrimaryKey;

@Data
@NoArgsConstructor
public class PrimaryKeyDto {

	String indexId;
	List<String> columnIds;
	boolean isClustered;
	
	public PrimaryKeyDto(PrimaryKey d) {
		this.indexId = d.getIndexId();
		this.columnIds = d.getColumnIds();
		this.isClustered = d.isClustered();
	}
	
	public PrimaryKey toDomain() {
		return new PrimaryKey(indexId, columnIds, isClustered);
	}
}
