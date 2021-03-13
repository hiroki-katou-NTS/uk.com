package nts.uk.cnv.dom.td.schema.snapshot;

import java.util.List;

import nts.uk.cnv.dom.td.schema.tabledesign.TableDesign;

public interface SnapshotRepository {

	Snapshot getNewest(String tableId);

	List<TableDesign> getNewestAll();

}
