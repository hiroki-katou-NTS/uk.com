package nts.uk.cnv.dom.td.tabledesign;

import java.util.List;

import lombok.Getter;
import nts.arc.time.GeneralDateTime;
import nts.uk.cnv.dom.td.alteration.Feature;

@Getter
public class Snapshot extends TableDesign {
	Feature feature;
	GeneralDateTime time;

	public Snapshot(
			Feature feature, GeneralDateTime time,
			String id, String name, String jpName,
			GeneralDateTime createDate, GeneralDateTime updateDate,
			List<ColumnDesign> columns, List<Indexes> indexes) {

		super(id, name, jpName, createDate, updateDate, columns, indexes);
		this.feature = feature;
		this.time = time;
	}

	public Snapshot(String feature, GeneralDateTime time, TableDesign domain) {
		super( domain.getId(),
				domain.getName(),
				domain.getJpName(),
				domain.getCreateDate(),
				domain.getUpdateDate(),
				domain.getColumns(),
				domain.getIndexes());
		this.feature = new Feature(feature);
		this.time = time;
	}
}
