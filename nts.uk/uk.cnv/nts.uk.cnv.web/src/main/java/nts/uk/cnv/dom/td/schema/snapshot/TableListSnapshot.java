package nts.uk.cnv.dom.td.schema.snapshot;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import lombok.Value;
import lombok.val;
import nts.uk.cnv.dom.td.alteration.schema.SchemaAlteration;
import nts.uk.cnv.dom.td.schema.TableIdentity;
import nts.uk.cnv.dom.td.schema.prospect.list.TableListProspect;

/**
 * テーブル一覧のスナップショット
 */
@Value
public class TableListSnapshot {

	/** スナップショットID */
	String snapshotId;
	
	/** テーブルリスト */
	List<TableIdentity> list;
	
	public TableListProspect apply(List<SchemaAlteration> alters) {
		
		List<TableIdentity> resultList = new ArrayList<>(list);
		
		for (val alter : alters) {
			resultList = alter.apply(resultList);
		}
		
		String lastAlterId = "";
		if (!alters.isEmpty()) {
			val lastAlter = alters.get(alters.size() - 1);
			lastAlterId = lastAlter.getAlterId();
		}
		
		return new TableListProspect(lastAlterId, resultList);
	}
	
	public static TableListSnapshot empty() {
		return new TableListSnapshot(null, Collections.emptyList());
	}
}
