package nts.uk.cnv.app.td.schema.tabledesign.constraint;

import java.util.List;

import nts.uk.cnv.dom.td.schema.tabledesign.constraint.TableIndex;
import nts.uk.cnv.dom.td.schema.tabledesign.constraint.UniqueConstraint;

public class IndexDto {

	String indexId;
	String suffix;
	List<String> columnIds;
	boolean isClustered;
	
	public IndexDto(UniqueConstraint d) {
		this.indexId = d.getIndexId();
		this.suffix = d.getSuffix();
		this.columnIds = d.getColumnIds();
		this.isClustered = d.isClustered();
	}
	
	public IndexDto(TableIndex d) {
		this.indexId = d.getIndexId();
		this.suffix = d.getSuffix();
		this.columnIds = d.getColumnIds();
		this.isClustered = d.isClustered();
	}
	
	public UniqueConstraint toDomainUnique() {
		return new UniqueConstraint(indexId, suffix, columnIds, isClustered);
	}
	
	public TableIndex toDomainIndex() {
		return new TableIndex(indexId, suffix, columnIds, isClustered);
	}
}
