package nts.uk.cnv.dom.td.schema.snapshot;

import lombok.Value;
import nts.arc.time.GeneralDateTime;

/**
 * スキーマのスナップショット
 */
@Value
public class SchemaSnapshot {

	/** スナップショットID */
	private final String snapshotId;
	
	/** 生成した日時 */
	private final GeneralDateTime generatedAt;
	
	/** 生成元イベントのID */
	private final String sourceEventId;
}
