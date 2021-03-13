package nts.uk.cnv.dom.td.tabledesign;

import java.util.List;

import lombok.Getter;
import nts.arc.time.GeneralDateTime;

@Getter
public class Snapshot extends TableDesign {
	String snapshotId;
	GeneralDateTime time;
	String eventId;

	public Snapshot(
			String snapshotId, String eventId,
			String id, String name, String jpName,
			GeneralDateTime createDate, GeneralDateTime updateDate,
			List<ColumnDesign> columns, List<Indexes> indexes) {

		super(id, name, jpName, columns, indexes);
		this.snapshotId = snapshotId;
		this.eventId = eventId;
	}

	public Snapshot(String snapshotId, String eventId, TableDesign domain) {
		super( domain.getId(),
				domain.getName(),
				domain.getJpName(),
				domain.getColumns(),
				domain.getIndexes());
		this.snapshotId = snapshotId;
		this.time = GeneralDateTime.now();
		this.eventId = eventId;
	}
}
