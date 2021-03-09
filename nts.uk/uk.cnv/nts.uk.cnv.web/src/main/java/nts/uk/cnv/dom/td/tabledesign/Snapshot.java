package nts.uk.cnv.dom.td.tabledesign;

import java.util.List;

import lombok.Getter;
import nts.arc.time.GeneralDateTime;

@Getter
public class Snapshot extends TableDesign {
	String featureId;
	GeneralDateTime datetime;

	public Snapshot(
			String featureId, GeneralDateTime datetime,
			String id, String name, String jpName,
			GeneralDateTime createDate, GeneralDateTime updateDate,
			List<ColumnDesign> columns, List<Indexes> indexes) {

		super(id, name, jpName, columns, indexes);
		this.featureId = featureId;
		this.datetime = datetime;
	}

	public Snapshot(String featureId, GeneralDateTime datetime, TableDesign domain) {
		super( domain.getId(),
				domain.getName(),
				domain.getJpName(),
				domain.getColumns(),
				domain.getIndexes());
		this.featureId = featureId;
		this.datetime = datetime;
	}
}
