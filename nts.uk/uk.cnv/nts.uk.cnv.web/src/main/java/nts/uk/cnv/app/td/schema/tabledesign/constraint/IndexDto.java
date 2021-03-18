package nts.uk.cnv.app.td.schema.tabledesign.constraint;

import java.util.List;

import nts.uk.cnv.dom.td.schema.tabledesign.constraint.TableIndex;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.UniqueConstraint;

public class IndexDto {

	String suffix;
	List<String> columnIds;
	boolean isClustered;
	
	public IndexDto(UniqueConstraint d) {
		this.suffix = d.getSuffix();
		this.columnIds = d.getColumnIds();
		this.isClustered = d.isClustered();
	}
	
	public IndexDto(TableIndex d) {
		this.suffix = d.getSuffix();
		this.columnIds = d.getColumnIds();
		this.isClustered = d.isClustered();
	}
	
	public UniqueConstraint toDomainUnique() {
		return new UniqueConstraint(suffix, columnIds, isClustered);
	}
	
	public TableIndex toDomainIndex() {
		return new TableIndex(suffix, columnIds, isClustered);
	}
}
