package nts.uk.cnv.screen.app.query.dto;

import lombok.AllArgsConstructor;
import lombok.Value;

@AllArgsConstructor
@Value
public class RecordDto {
	int recordNo;

	String tableName;

	String explanation;

	String sourceId;

	boolean isRemoveDuplicate;
}
