package nts.uk.cnv.dom.td.tabledesign;

import java.util.List;
import java.util.Optional;

public interface SnapshotRepository {

	Optional<TableDesign> getNewest(String tableId);

	List<TableDesign> getNewestAll();

}
